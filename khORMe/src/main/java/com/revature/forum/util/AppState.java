package com.revature.forum.util;

import com.revature.forum.repos.UserRepository;
import com.revature.forum.screens.HomeScreen;
import com.revature.forum.screens.LoginScreen;
import com.revature.forum.screens.RegistrationScreen;
import com.revature.forum.services.UserService;
import com.revature.util.Session;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {

    /** user input reading */
    private BufferedReader console;
    /** contains viewable screens */
    private ScreenRouter router;
    /** users connection to the database */
    private Session currentSession;
    /** true until user quits */
    private boolean appRunning;

    /**
     *
     */
    public AppState() {

        System.out.println("[LOG] - Initializing application...");

        this.appRunning = true;
        this.console = new BufferedReader(new InputStreamReader(System.in));

        final UserRepository userRepo = new UserRepository();

        final UserService userService = new UserService(userRepo);

        router = new ScreenRouter();
        router.addScreen(new HomeScreen())
                .addScreen(new RegistrationScreen(userService))
                .addScreen(new LoginScreen(userService));
//                .addScreen(new DashboardScreen(userService))
//                .addScreen(new TransactionScreen(userService))
//                .addScreen(new AccountSettingsScreen(userService));

        System.out.println("[LOG] - Application initialized");

    }

    public BufferedReader getConsole() {
        return console;
    }

    public ScreenRouter getRouter() {
        return router;
    }

    public boolean isAppRunning() {
        return appRunning;
    }

    public void setAppRunning(boolean appRunning) {
        this.appRunning = appRunning;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public void invalidateCurrentSession() {
        this.currentSession = null;
    }

    public boolean isSessionValid() {
        return (this.currentSession != null);
    }
}
