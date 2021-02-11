package com.revature.forum.screens;

import com.revature.forum.models.Board;
import com.revature.util.AccessDB;

import static com.revature.forum.ForumDriver.app;

/**
 * initial user interface on program start - HomeScreen of application
 */
public class HomeScreen extends Screen {

    /** constructor */
    public HomeScreen() {
        super("HomeScreen", "/home");
    }


    AccessDB db=new AccessDB();
    /**
     * initial user interface on program start, where user can either create an account, or
     * log into an existing one.
     */
    @Override
    public void render() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("_______________________________________________________________________________________");
        System.out.println("        --BOARDS--                                              [L]ogin  [R]egister\n");

        db.selectStar(Board.class);
//        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
//            String sql = "select name, description " +
//                    "from boards b; ";
//
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            ResultSet rs = pstmt.executeQuery();
//            while(rs.next()) {
//                System.out.print(rs.getString("name")+":   "+rs.getString("description")+"\n" );
//            }
//
//        } catch (SQLException e) { e.printStackTrace(); }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("0) Exit application");


        try {

            System.out.print("> ");
            String userSelection = app().getConsole().readLine();

            switch (userSelection) {
                case "l":
                    System.out.println("Navigating to login screen");
                    app().getRouter().navigate("/login");
                    break;
                case "r":
                    System.out.println("Navigating to register screen");
                    app().getRouter().navigate("/register");
                    break;
                case "0":
                    System.out.println("Exiting application");
                    app().setAppRunning(false);
                    break;
                default:
                    System.out.println("Invalid selection!");

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Shutting down application");
            app().setAppRunning(false);
        }

    }

}
