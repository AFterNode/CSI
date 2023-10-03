/*
 * Created by JFormDesigner on Fri Sep 29 14:26:09 CST 2023
 */

package cn.afternode.csi.ui.config;

import java.awt.event.*;
import java.beans.*;
import java.util.*;
import cn.afternode.csi.CSI;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import javax.swing.*;

/**
 * @author Admin
 */
public class DefaultServerConfig extends JPanel {
    private final File file = new File(CSI.INSTANCE.getConfig().installDir, "server.properties");
    private final Properties prop = new Properties();

    public DefaultServerConfig() throws Exception {
        initComponents();

        prop.load(Files.newInputStream(file.toPath()));
        cbOnlineMode.setSelected(Boolean.parseBoolean(prop.getProperty("online-mode", "false")));
        sliderMaxPlayers.setValue(Integer.parseInt(prop.getProperty("max-players", "20")));
    }

    private void btnSaveMouseClicked(MouseEvent e) {
        prop.setProperty("online-mode", String.valueOf(cbOnlineMode.isSelected()));
        prop.setProperty("max-players", String.valueOf(sliderMaxPlayers.getValue()));

        try {
            prop.store(Files.newOutputStream(file.toPath()), "Minecraft server properties");
            JOptionPane.showMessageDialog(CSI.INSTANCE.getMainWindow(), "Configuration already saved", "Done", JOptionPane.PLAIN_MESSAGE);
        } catch (Throwable t) {
            JOptionPane.showMessageDialog(CSI.INSTANCE.getMainWindow(), "Error saving configuration: " + t, "Error", JOptionPane.ERROR_MESSAGE);
            t.printStackTrace();
        }
    }

    private void onMaxPlayersChange(PropertyChangeEvent e) {
        String text = lbMaxPlayers.getText();
        if (!text.contains(e.getOldValue().toString())) {
            text += " (" + e.getNewValue() + ")";
        } else {
            text = text.replace(" (" + e.getOldValue() + ")", " (" + e.getNewValue() + ")");
        }
        lbMaxPlayers.setText(text);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ResourceBundle bundle = ResourceBundle.getBundle("lang.main");
        kvOnlineMode = new JPanel();
        lbOnlineMode = new JLabel();
        cbOnlineMode = new JCheckBox();
        kvMaxPlayers = new JPanel();
        lbMaxPlayers = new JLabel();
        sliderMaxPlayers = new JSlider();
        btnSave = new JButton();

        //======== this ========
        setLayout(new GridLayout(5, 1));

        //======== kvOnlineMode ========
        {
            kvOnlineMode.setLayout(new GridLayout(1, 2));

            //---- lbOnlineMode ----
            lbOnlineMode.setText(bundle.getString("DefaultServerConfig.lbOnlineMode.text"));
            kvOnlineMode.add(lbOnlineMode);

            //---- cbOnlineMode ----
            cbOnlineMode.setText(bundle.getString("DefaultServerConfig.cbOnlineMode.text"));
            kvOnlineMode.add(cbOnlineMode);
        }
        add(kvOnlineMode);

        //======== kvMaxPlayers ========
        {
            kvMaxPlayers.setLayout(new GridLayout(1, 2));

            //---- lbMaxPlayers ----
            lbMaxPlayers.setText(bundle.getString("DefaultServerConfig.lbMaxPlayers.text"));
            kvMaxPlayers.add(lbMaxPlayers);

            //---- sliderMaxPlayers ----
            sliderMaxPlayers.setMinimum(1);
            sliderMaxPlayers.addPropertyChangeListener("value", e -> onMaxPlayersChange(e));
            kvMaxPlayers.add(sliderMaxPlayers);
        }
        add(kvMaxPlayers);

        //---- btnSave ----
        btnSave.setText(bundle.getString("DefaultServerConfig.btnSave.text"));
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnSaveMouseClicked(e);
            }
        });
        add(btnSave);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel kvOnlineMode;
    private JLabel lbOnlineMode;
    private JCheckBox cbOnlineMode;
    private JPanel kvMaxPlayers;
    private JLabel lbMaxPlayers;
    private JSlider sliderMaxPlayers;
    private JButton btnSave;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
