package com.revature.forum;

import com.revature.forum.util.AppState;
import com.revature.model.ClassInspector;
import com.revature.model.ClassScraper;

public class ForumDriver {
    private static AppState app = new AppState();
    private static ClassScraper cs=new ClassScraper();
    public static void main(String[] args) {

        while (app.isAppRunning()) {
            app.getRouter().navigate("/home");
        }
    }

    public static AppState app() {
        return app;
    }

}
