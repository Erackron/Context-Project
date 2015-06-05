package nl.tudelft.contextproject.imageprocessing.gui;

import com.sun.istack.internal.NotNull;
import org.opencv.highgui.VideoCapture;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class CameraSelectDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<Integer> cameraList;
    private JLabel videoSourceLabel;
    private CameraSelect selectCallback;
    private List<VideoCapture> videoCaptureList;

    /**
     * Create a new CameraSelectDialog window.
     */
    public CameraSelectDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void onOK() {
        int selectedCamera = cameraList.getItemAt(cameraList.getSelectedIndex());
        selectCallback.setSelectedCamera(selectedCamera);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    /**
     * Show a select Camera dialog and return the result.
     *
     * @param cameraSelect The camera select callback interface
     */
    public void selectCamera(@NotNull CameraSelect cameraSelect) {
        int amount = getAmountOfCameras();
        if (amount == 0) {
            return;
        }
        if (amount == 1) {
            cameraSelect.setSelectedCamera(0);
            return;
        }

        selectCallback = cameraSelect;

        cameraList.removeAllItems();
        for (int i = 0; i < amount; i++) {
            cameraList.addItem(i);
        }

        pack();
        setVisible(true);
    }

    protected int getAmountOfCameras() {
        int maxTested = 20;
        VideoCapture temp;
        for (int i = 0; i < maxTested; i++) {
            temp = new VideoCapture(i);
            if (!temp.isOpened()) {
                temp.release();
                return i;
            }
        }
        return maxTested;
    }

    public interface CameraSelect {
        /**
         * Set the selected camera.
         *
         * @param cameraId The id of the selected camera
         */
        void setSelectedCamera(int cameraId);
    }
}
