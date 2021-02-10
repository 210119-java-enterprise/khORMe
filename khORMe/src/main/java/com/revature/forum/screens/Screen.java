package com.revature.forum.screens;

public abstract class Screen {

    /** name of the screen */
    protected String name;
    /** route to the screen */
    protected String route;

    /** constructor */
    public Screen(String name, String route) {
        this.name = name;
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public String getRoute() {
        return route;
    }

    public abstract void render();

}
