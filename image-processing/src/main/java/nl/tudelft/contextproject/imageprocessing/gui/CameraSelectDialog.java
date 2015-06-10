package nl.tudelft.contextproject.imageprocessing.gui;

import org.opencv.highgui.VideoCapture;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
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
    private JLabel videoSourceLabel;
    private JComboBox<Integer> cameraList;
    private CameraSelect selectCallback;
    private List<VideoCapture> videoCaptureList = new ArrayList<>();

    /**
     * Create a new CameraSelectDialog window.
     */
    public CameraSelectDialog() {
        setupUI();

        setModal(true);
        setResizable(false);
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

    /**
     * Method called if the user presses the ok button.
     */
    private void onOK() {
        int selectedCamera = cameraList.getItemAt(cameraList.getSelectedIndex());
        selectCallback.setSelectedCamera(videoCaptureList.get(selectedCamera));
        for (int i = 0; i < videoCaptureList.size(); i++) {
            if (i == selectedCamera) {
                continue;
            }
            videoCaptureList.get(i).release();
        }
        dispose();
    }

    /**
     * Method called if the user presses the cancel button.
     */
    private void onCancel() {
        dispose();
    }

    /**
     * Show a select Camera dialog and return the result.
     *
     * @param cameraSelect The camera select callback interface
     */
    public void selectCamera(CameraSelect cameraSelect) {
        int amount = getAmountOfCameras();
        if (amount == 0) {
            return;
        }
        if (amount == 1) {
            cameraSelect.setSelectedCamera(videoCaptureList.get(0));
            return;
        }
        if (cameraSelect == null) {
            System.err.println("No callback specified");
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

    /**
     * Get the amount of available cameras in the system.
     *
     * @return The amount of cameras
     */
    protected int getAmountOfCameras() {
        int maxTested = 20;
        VideoCapture temp;
        for (int i = 0; i < maxTested; i++) {
            temp = new VideoCapture(i);
            if (!temp.isOpened()) {
                temp.release();
                return i;
            }
            videoCaptureList.add(temp);
        }
        return maxTested;
    }

    /**
     * Setup the CameraSelectDialog Layout.
     */
    protected void setupUI() {
        setupContentPane();
        setupButtonsAndCombobox();

        final JPanel panel1 = addPanel(contentPane, createGridBagConstraints(0, 1, 1.0, 0.0));
        final JPanel panel2 = addPanel(panel1, createGridBagConstraints(1, 0, 0.0, 1.0));
        final JPanel panel3 = addPanel(contentPane, createGridBagConstraints(0, 0, 1.0, 1.0));

        panel2.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0), null)
        );
        panel2.add(buttonOK, createGridBagConstraints(0, 0, 1.0, 1.0));

        GridBagConstraints gbc;
        gbc = createGridBagConstraints(1, 0, 1.0, 1.0);
        gbc.insets = new Insets(0, 5, 0, 0);
        panel2.add(buttonCancel, gbc);

        gbc = createGridBagConstraints(0, 1, 1.0, 1.0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(cameraList, gbc);

        panel3.add(videoSourceLabel, createGridBagConstraints(0, 0, 1.0, 1.0));
    }

    /**
     * Create a new GridBagConstraints instance with specified properties set to passed values.
     *
     * @param gridX   The gridX value of the GridBagConstraints instance
     * @param gridY   The gridY value of the GridBagConstraints instance
     * @param weightX The weightX value of the GridBagConstraints instance
     * @param weightY The weightY value of the GridBagConstraints instance
     * @return The newly created GridBagConstraints instance
     */
    protected GridBagConstraints createGridBagConstraints(int gridX, int gridY,
                                                          double weightX, double weightY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.weightx = weightX;
        gbc.weighty = weightY;

        return gbc;
    }

    /**
     * Setup the main ContentPane.
     */
    protected void setupContentPane() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10), null
                )
        );
        setContentPane(contentPane);
    }

    /**
     * Setup the buttons and combobox.
     */
    protected void setupButtonsAndCombobox() {
        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Cancel");
        cameraList = new JComboBox<>();
        videoSourceLabel = new JLabel("Select video source number to use as input.");
        videoSourceLabel.setToolTipText("0 is usually the internal camera (if present)");
    }

    /**
     * Create a panel using the GridBagLayout and add it to the parent panel with specified
     * GridBagConstraints.
     *
     * @param parent The parent panel to add the newly created panel to
     * @param gbc    The GridBagConstraints to use when adding the new panel to the parent
     * @return The newly created JPanel
     */
    protected JPanel addPanel(JPanel parent, GridBagConstraints gbc) {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        parent.add(panel, gbc);
        return panel;
    }

    public interface CameraSelect {
        /**
         * Set the selected camera.
         *
         * @param camera The the selected camera
         */
        void setSelectedCamera(VideoCapture camera);
    }
}
