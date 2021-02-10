package com.revature.forum.util;
import com.revature.forum.screens.Screen;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages all of the screens and loads screens on request
 */
public class ScreenRouter {
    /** a set to hold screen objects */
    private Set<Screen> screens = new HashSet<>();

    /**
     * @return the set of screens
     */
    public Set<Screen> getScreens() {
        return screens;
    }

    /**
     * adds all of the screens to the screen set
     * @param screen new screen to add
     * @return the set of screens
     */
    public ScreenRouter addScreen(Screen screen) {
        System.out.println("[LOG] - Loading " + screen.getName() + " into router");
        screens.add(screen);
        return this;
    }

    /**
     * changes user interface to the designated screen
     * @param route the screen to load
     */
    public void navigate(String route) {
        for (Screen screen: screens) {
            if (screen.getRoute().equals(route)) {
                screen.render();
            }
        }
    }


}