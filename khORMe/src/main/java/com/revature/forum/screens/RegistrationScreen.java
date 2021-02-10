package com.revature.forum.screens;

import com.revature.forum.models.AppUser;
import com.revature.forum.services.UserService;

import static com.revature.forum.ForumDriver.app;

/**
 * new account creation
 */
public class RegistrationScreen extends Screen {

    private UserService userService;

    /** constructor */
    public RegistrationScreen(UserService userService) {
        super("RegisterScreen", "/register");
        this.userService = userService;
    }

    /**
     * user interface to create a new account
     */
    @Override
    public void render() {

        String firstName;
        String lastName;
        String username;
        String password;

        try {

            System.out.println("+---------------------+");
            System.out.println("Sign up for a new account!");
            System.out.print("First name: ");
            firstName = app().getConsole().readLine();
            System.out.print("Last name: ");
            lastName = app().getConsole().readLine();
            System.out.print("Username: ");
            username = app().getConsole().readLine();
            System.out.print("Password: ");
            password = app().getConsole().readLine();

            AppUser newUser = new AppUser(firstName, lastName, username, password);

            userService.register(newUser);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
