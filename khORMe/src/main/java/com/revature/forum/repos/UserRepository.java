package com.revature.forum.repos;

import com.revature.forum.models.AppUser;
import com.revature.util.ConnectionFactory;


import java.sql.*;
import java.util.LinkedList;

import static com.revature.forum.ForumDriver.app;

/**
 * Contains implementations to interact with the database,
 * including Creating users and user interactions with their account
 */
public class UserRepository implements CrudRepository<AppUser>{
    /** simple base query */
    private final String base = "SELECT * " +
            "FROM users u ";// +
            //"JOIN users_accounts ur;"

    /**
     * Finds a user from database based on username and password, then stores the data in an AppUser Object
     * @param username the username of the person searched for
     * @param password the password associated with the username
     * @return new AppUser Object
     */
    public AppUser findUserByUsernameAndPassword(String username, String password) {

        AppUser user = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = base + "WHERE username = ? AND pw = ? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);


            ResultSet rs = pstmt.executeQuery();
            user = mapResultSet(rs).pop();

        } catch (SQLException e) { e.printStackTrace(); }

        return user;
    }

    /**
     * Finds a user from database based on username and stores the data in an AppUser Object
     * @param username the username of the person searched for
     * @return new AppUser Object
     */
    public AppUser findUserByUsername(String username) {

        AppUser user = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = base + "WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            //user = mapResultSet(rs).pop();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


    /**
     * gets all accounts associated with a user, and stores in a LinkedList
     * @param user the AppUser who's accounts are to be queried
     */
    public void getAccounts(AppUser user) {

        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
            String sql = "select account_id " +
                    "from users_accounts ua " +
                    "where ua.user_id=?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user.getId());

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                //user.setAccounts(rs.getInt("account_id"));
            }

        } catch (SQLException e) { e.printStackTrace(); }
    }


    /**
     * fetches and prints to console the balances of the checkings and savings for all accounts owned by a user
     * @param username username of the AppUser whose accounts are to be queried
     */
    public void balanceInquiry(String username) {

        AppUser user = findUserByUsername(username);
        Connection conn = app().getCurrentSession().getConnection();
        try {

            String sql = "select a.id, a.checkings, a.savings\n" +
                    "from accounts a, users_accounts ua\n" +
                    "where ua.user_id=? and ua.account_id=a.id\n" +
                    "order by a.id asc;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user.getId());

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                System.out.println(
                        String.format("%1$-8s %2$-12s %3$.2f",
                                rs.getInt("id"),
                                String.format("%.2f",rs.getDouble("checkings")),
                                rs.getDouble("savings")
                                )
                );

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * For deposits, withdraws, and transfers
     * @param account_id id of account to be queried
     * @param account_type checkings or savings
     * @param amount positive or negative amount to be applied to account balance
     */
    public void transferFunds(int account_id, String account_type, Double amount) {
        Connection conn = app().getCurrentSession().getConnection();
        try {
            //checks to see if over drafting
            String sql = "select (a."+account_type+" + ?)\n" +
                    "from accounts a\n" +
                    "where id = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, account_id);

            ResultSet rs=pstmt.executeQuery();
            rs.next();

            if(rs.getDouble(1)<0) {
                //throw new InsufficientFundsException();
            }

            sql = "update accounts " +
                    "set "+account_type+" = ("+account_type+" + ?) " +
                    "where id = ?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, account_id);

            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates transaction table-- used in conjunction with transferFunds()
     *
     * @param account_id id of account to update
     * @param account_type 'checkings' or savings account associated with id
     * @param amount +/- value to add to account balance
     * @param transaction_type Descriptor - 'Transfer In', 'Transfer Out', 'Withdraw' or 'Deposit'
     */
    public void updateTransactions(int account_id, String account_type, Double amount, String transaction_type){
        Connection conn = app().getCurrentSession().getConnection();
        try  {

            String sql = "insert into transactions(account_id, amount, account_type, transaction_type, transaction_date, transaction_time) " +
                    "values(?, ?,'"+account_type+"', ?, current_date, current_time);";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, account_id);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, transaction_type);


            pstmt.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * fetches and print the names of all user with access to a particular account
     * @param account_id the id of the account to be queried
     */
    public void findUsersOnAccount(int account_id){
        Connection conn = app().getCurrentSession().getConnection();
        try  {

            String sql = "select u.first_name, u.last_name\n" +
                    "from users u, users_accounts ua\n" +
                    "where ua.account_id=? and ua.user_id=u.id;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, account_id);

            ResultSet rs=pstmt.executeQuery();
            while(rs.next()){
                System.out.print(rs.getString("first_name"));
                System.out.print(" ");
                System.out.print(rs.getString("last_name"));
                System.out.print("\n");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds an existing user in the database to an account of the current session user
     * @param account_id the account id of the account to add a new user
     * @param new_id the id of the new user
     */
    public void addUserToAccountByUsername(int account_id, int new_id){
        Connection conn = app().getCurrentSession().getConnection();
        try  {

            String sql = "insert into users_accounts (user_id, account_id) \n" +
                    "values (?, ?);";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, new_id);
            pstmt.setDouble(2, account_id);

            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * queries and prints all transactions performed by one account
     * @param account_id the account history to be queried
     */
    public void transactionHistory(int account_id) {
        Connection conn = app().getCurrentSession().getConnection();
        try  {
            String sql = "select t.account_id, t.account_type, t.amount, t.transaction_type, t.transaction_date, t.transaction_time\n" +
                    "from transactions t\n" +
                    "where t.account_id=?\n" +
                    "order by transaction_date, transaction_time;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, account_id);

            System.out.print("\n___________________________________________\n");
            ResultSet rs=pstmt.executeQuery();
            while(rs.next()){
                System.out.print(rs.getString("account_id"));
                System.out.print(" ");
                System.out.print(rs.getString("account_type"));
                System.out.print(" ");
                System.out.print( rs.getString("transaction_type"));
                System.out.print(" ");
                System.out.printf("%.2f", rs.getDouble("amount"));
                System.out.print(" ");
                System.out.print(rs.getDate("transaction_date"));
                System.out.print(" ");
                System.out.print(rs.getTime("transaction_time"));
                System.out.print("\n___________________________________________\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * opens a new account for a registered user
     * @param user the AppUser to receive a new account
     */
    public void createNewAccount(AppUser user) {

        Connection conn = app().getCurrentSession().getConnection();
        try {

            String sql = "insert into accounts (checkings, savings) \n" +
                    "values (0.00, 0.00) " +
                    "returning id;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.execute();

            ResultSet rs = pstmt.getResultSet();
            rs.next();
            int account_id = rs.getInt("id");

            sql = "insert into users_accounts (user_id, account_id) \n" +
                    "values (?, ?);";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user.getId());
            pstmt.setInt(2, account_id);

            pstmt.executeUpdate();

            //user.setAccounts(account_id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * saves the newly registered AppUser to the database
     * @param newObj AppUser containing basic registration data
     */
    @Override
    public void save(AppUser newObj) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "INSERT INTO users (username, pw, first_name, last_name) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"id"});
            pstmt.setString(1, newObj.getUsername());
            pstmt.setString(2, newObj.getPassword());
            pstmt.setString(3, newObj.getFirstName());
            pstmt.setString(4, newObj.getLastName());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    newObj.setId(rs.getInt("id"));
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * queries all users on the user table
     * @return a linked list containing all users
     */
    @Override
    public LinkedList<AppUser> findAll() {
//    public LinkedList<AppUser> findAll() {
//
//        Connection conn = app().getCurrentSession().getConnection();
//        LinkedList<AppUser> users = new LinkedList<>();
//
//        try {
//
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(base);
//            users = mapResultSet(rs);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return users;
        return null;
    }

    @Override
    public AppUser findById(int id) {
        System.err.println("Not implemented");
        return null;
    }

    @Override
    public boolean update(AppUser updatedObj) {
        System.err.println("Not implemented");
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        System.err.println("Not implemented");
        return false;
    }

    /**
     * unloads resultset of a sql query into one or more AppUser objects,
     * then storing those object into a linkedlist
     * @param rs result set of a sql query of the 'user' table
     * @return Linked list of AppUser objects containing a users data
     * @throws SQLException
     */
    private LinkedList<AppUser> mapResultSet(ResultSet rs) throws SQLException {
        LinkedList<AppUser> users = new LinkedList<>();
        while(rs.next()) {
            AppUser user = new AppUser();
            user.setId(rs.getInt("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("pw"));
            user.setAddress(rs.getString("address"));
            user.setCity(rs.getString("city"));
            user.setState(rs.getString("state"));
            user.setZip(rs.getString("zip"));
            user.setPhone(rs.getString("phone"));
            user.setRegistrationDate(rs.getString("registration_date"));
            users.add(user);
        }

        return users;

    }
}
