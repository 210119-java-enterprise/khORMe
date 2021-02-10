package com.revature.forum.services;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.forum.models.AppUser;
import com.revature.forum.models.UserRole;
import com.revature.forum.repos.UserRepository;
import com.revature.forum.util.ConnectionFactory;
import com.revature.forum.util.Session;

import static com.revature.forum.ForumDriver.app;


/**
 * intermediary between user and user repository. Does some validity checking on the provided data
 * and calls the correct userRepo methods
 */
public class UserService {
    /** userRepository declaration */
    private UserRepository userRepo;

    /** constructor */
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * checks validity of username and password for a logging in user
     * then proceeds to get users data and start a new session
     * @param username username provided by the user
     * @param password password provided by the user
     */
    public void authenticate(String username, String password) {

        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid credentials provided (null or empty strings)!");
        }

        AppUser authUser = userRepo.findUserByUsernameAndPassword(username, password);

        if (authUser == null) { throw new AuthenticationException(); }
        //userRepo.getAccounts(authUser);
        app().setCurrentSession(new Session(authUser, ConnectionFactory.getInstance().getConnection()));

    }


    /**
     * checks that new user contains valid data, and that the new username is available
     * before finalizing registration
     * @param newUser the new registrants entered data
     */
    public void register(AppUser newUser) {

        if (!isUserValid(newUser)){
            throw new InvalidRequestException("Invalid new user provided!");
        }

        if (userRepo.findUserByUsername(newUser.getUsername()) != null) {
            throw new ResourcePersistenceException("The provided username is already in use!");
        }

        newUser.setUserRole(UserRole.BASIC_USER);
        userRepo.save(newUser);

    }

    /**
     * makes sure the appUser object contains all pertinent data
     * @param user user whose data is to be checked
     * @return true if all data is valid; false if null or empty
     */
    public boolean isUserValid(AppUser user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        if (user.getPassword() == null || user.getPassword().trim().equals("")) return false;
        return true;
    }


}
