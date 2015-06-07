package nl.tudelft.contextproject.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import nl.tudelft.contextproject.core.Main;

public class MainDesktop {

    /**
     * Main entry point of the desktop client.
     *
     * @param args The program arguments
     */
    public static void main(String[] args) {
        // Start game
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Human Pencils";
        LwjglApplication humanPencils = new LwjglApplication(new Main(), config);

        // Start image processing
        /*
        ImageProcessingThread imageProcessingThread = new ImageProcessingThread();
        new Thread(imageProcessingThread).start();

        humanPencils.addLifecycleListener(new ExitListener() {
            @Override
            public void onExit() {
                imageProcessingThread.stop();
            }
        });
        */
    }
}
