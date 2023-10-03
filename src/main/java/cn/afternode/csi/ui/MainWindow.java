/*
 * Created by JFormDesigner on Fri Sep 29 12:38:07 CST 2023
 */

package cn.afternode.csi.ui;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import cn.afternode.csi.CSI;
import cn.afternode.csi.ui.config.DefaultServerConfig;
import cn.afternode.csi.ui.mwpanels.InstallerPanel;
import cn.afternode.csi.utils.SubConfig;
import com.google.gson.Gson;
import kotlin.text.Charsets;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Admin
 */
public class MainWindow extends JFrame {
    public MainWindow() {
        initComponents();

        installer.add(new InstallerPanel());

        // region:Settings
        tfInstallDir.setText(CSI.INSTANCE.getConfig().installDir);
        tbUseBmclapi.setSelected(CSI.INSTANCE.getConfig().useBmclApi);
        tfJavaExec.setText(CSI.INSTANCE.getConfig().javaExec);
        // endregion
    }

    private void thisWindowOpened(WindowEvent e) {
    }

    private void thisWindowClosed(WindowEvent e) {
    }

    private void thisWindowClosing(WindowEvent e) {
        CSI.INSTANCE.handleExit();
    }

    private void SaveMouseClicked(MouseEvent e) {
        CSI.INSTANCE.getConfig().installDir = tfInstallDir.getText();
        CSI.INSTANCE.getConfig().useBmclApi = tbUseBmclapi.isSelected();
        CSI.INSTANCE.getConfig().javaExec = tfJavaExec.getText();

        CSI.INSTANCE.getFileManager().saveConfig(CSI.INSTANCE.getConfig());
    }

    private void btnRefreshMouseClicked(MouseEvent e) {
        try {
            File subConfig = new File(CSI.INSTANCE.getConfig().installDir, ".csi");
            SubConfig sub;
            if (subConfig.exists()) {
                 sub = new Gson().fromJson(Files.readString(subConfig.toPath(), Charsets.UTF_8), SubConfig.class);
            } else {
                sub = new SubConfig();
            }

            tpServerOptions.removeAll();
            switch (sub.serverType) {
                case "default":
                default:
                    tpServerOptions.addTab("server.properties", new DefaultServerConfig());
            }
        } catch (Throwable t) {
            JOptionPane.showMessageDialog(this, "Error refreshing configurations: " + t, "Error", JOptionPane.ERROR_MESSAGE);
            t.printStackTrace();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ResourceBundle bundle = ResourceBundle.getBundle("lang.main");
        lbInformation = new JLabel();
        tp = new JTabbedPane();
        installer = new JPanel();
        serverOptions = new JPanel();
        btnRefresh = new JButton();
        tpServerOptions = new JTabbedPane();
        settings = new JPanel();
        kvInstallDir = new JPanel();
        lbInstallDir = new JLabel();
        tfInstallDir = new JTextField();
        kvJavaExec = new JPanel();
        lbJavaExec = new JLabel();
        tfJavaExec = new JTextField();
        tbUseBmclapi = new JToggleButton();
        Save = new JButton();

        //======== this ========
        setTitle(bundle.getString("MainWindow.title"));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                thisWindowClosed(e);
            }
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
            @Override
            public void windowOpened(WindowEvent e) {
                thisWindowOpened(e);
            }
        });
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //---- lbInformation ----
        lbInformation.setText("Information");
        contentPane.add(lbInformation, BorderLayout.SOUTH);

        //======== tp ========
        {

            //======== installer ========
            {
                installer.setLayout(new BorderLayout());
            }
            tp.addTab(bundle.getString("MainWindow.installer.tab.title"), installer);

            //======== serverOptions ========
            {
                serverOptions.setLayout(new BorderLayout());

                //---- btnRefresh ----
                btnRefresh.setText(bundle.getString("MainWindow.btnRefresh.text"));
                btnRefresh.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnRefreshMouseClicked(e);
                    }
                });
                serverOptions.add(btnRefresh, BorderLayout.SOUTH);
                serverOptions.add(tpServerOptions, BorderLayout.CENTER);
            }
            tp.addTab(bundle.getString("MainWindow.serverOptions.tab.title"), serverOptions);

            //======== settings ========
            {
                settings.setLayout(new GridLayout(5, 1));

                //======== kvInstallDir ========
                {
                    kvInstallDir.setLayout(new GridLayout(1, 2));

                    //---- lbInstallDir ----
                    lbInstallDir.setText(bundle.getString("MainWindow.lbInstallDir.text"));
                    kvInstallDir.add(lbInstallDir);
                    kvInstallDir.add(tfInstallDir);
                }
                settings.add(kvInstallDir);

                //======== kvJavaExec ========
                {
                    kvJavaExec.setLayout(new GridLayout(1, 2));

                    //---- lbJavaExec ----
                    lbJavaExec.setText(bundle.getString("MainWindow.lbJavaExec.text"));
                    kvJavaExec.add(lbJavaExec);
                    kvJavaExec.add(tfJavaExec);
                }
                settings.add(kvJavaExec);

                //---- tbUseBmclapi ----
                tbUseBmclapi.setText(bundle.getString("MainWindow.tbUseBmclapi.text"));
                settings.add(tbUseBmclapi);

                //---- Save ----
                Save.setText(bundle.getString("MainWindow.Save.text"));
                Save.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        SaveMouseClicked(e);
                    }
                });
                settings.add(Save);
            }
            tp.addTab(bundle.getString("MainWindow.settings.tab.title"), settings);
        }
        contentPane.add(tp, BorderLayout.CENTER);
        setSize(785, 490);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    public JLabel lbInformation;
    private JTabbedPane tp;
    private JPanel installer;
    private JPanel serverOptions;
    private JButton btnRefresh;
    private JTabbedPane tpServerOptions;
    private JPanel settings;
    private JPanel kvInstallDir;
    private JLabel lbInstallDir;
    private JTextField tfInstallDir;
    private JPanel kvJavaExec;
    private JLabel lbJavaExec;
    private JTextField tfJavaExec;
    private JToggleButton tbUseBmclapi;
    private JButton Save;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
