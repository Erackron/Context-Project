package nl.tudelft.contextproject.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import nl.tudelft.contextproject.core.Main;

public class MainDesktop {

    /**
     * Main entry point of the desktop client.
     * @param args The program arguments
     */
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Human Pencils";
        new LwjglApplication(new Main(), config);
    }
}
