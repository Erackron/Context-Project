package nl.tudelft.contextproject.desktop;

import com.badlogic.gdx.Files;
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
        config.addIcon("logo-256x256.png", Files.FileType.Internal);
        config.addIcon("logo-128x128.png", Files.FileType.Internal);
        config.addIcon("logo-32x32.png", Files.FileType.Internal);
        config.addIcon("logo-16x16.png", Files.FileType.Internal);
        LwjglApplication humanPencils = new LwjglApplication(new Main(), config);
        // Start image processing
        ImageProcessingThread imageProcessingThread = new ImageProcessingThread();

        ExitListener imageProcessingExitListener = new ExitListener() {
            @Override
            public void onExit() {
                humanPencils.exit();
            }
        };

        ExitListener libgdxExitListener = new ExitListener() {
            @Override
            public void onExit() {
                imageProcessingThread.removeExitListener(imageProcessingExitListener);
                imageProcessingThread.stop();
            }
        };

        imageProcessingThread.addExitListener(imageProcessingExitListener);
        humanPencils.addLifecycleListener(libgdxExitListener);

        new Thread(imageProcessingThread).start();
    }
}
