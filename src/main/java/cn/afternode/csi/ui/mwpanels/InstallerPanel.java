/*
 * Created by JFormDesigner on Fri Sep 29 13:29:13 CST 2023
 */

package cn.afternode.csi.ui.mwpanels;

import java.util.*;
import cn.afternode.csi.CSI;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

/**
 * @author Admin
 */
public class InstallerPanel extends JPanel {
    public InstallerPanel() {
        initComponents();
    }

    private void refreshPaper(MouseEvent e) {

    }

    private void btnRefreshPaperMouseClicked(MouseEvent e) {
        System.out.println("Fetching paper versions...");
        List<String> version = CSI.INSTANCE.getInstallerManager().get("paper").getVersions();
        cbPaperVersion.removeAllItems();
        for (String v: version) cbPaperVersion.addItem(v);
        cbPaperVersion.setSelectedIndex(version.size()-1);
        System.out.println("Done");
    }

    private void installPaper(MouseEvent e) {
        CSI.INSTANCE.getInstallerManager().get("paper").installVersion(
                cbPaperVersion.getSelectedItem().toString()
        );
    }

    private void refreshMohist(MouseEvent e) {
        for (String ver: CSI.INSTANCE.getInstallerManager().get("Mohist").getVersions()) {
            cbMohistVersions.addItem(ver);
        }
    }

    private void installMohist(MouseEvent e) {
        Thread th = new Thread(() -> {
            CSI.INSTANCE.getInstallerManager().get("Mohist").installVersion(cbMohistVersions.getSelectedItem().toString());
        });
        th.setName("CSI-Mohist-Installer");
        th.start();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ResourceBundle bundle = ResourceBundle.getBundle("lang.main");
        installer = new JPanel();
        tpInstaller = new JTabbedPane();
        tpPaper = new JPanel();
        btnRefreshPaper = new JButton();
        pnPaper = new JPanel();
        cbPaperVersion = new JComboBox();
        btnInstall = new JButton();
        tpForge = new JPanel();
        tpMohist = new JPanel();
        cbMohistVersions = new JComboBox();
        btnMohistRefresh = new JButton();
        btnMohistInstall = new JButton();

        //======== this ========
        setLayout(new BorderLayout());

        //======== installer ========
        {
            installer.setLayout(new BorderLayout());

            //======== tpInstaller ========
            {

                //======== tpPaper ========
                {
                    tpPaper.setLayout(new BorderLayout());

                    //---- btnRefreshPaper ----
                    btnRefreshPaper.setText(bundle.getString("InstallerPanel.btnRefreshPaper.text"));
                    btnRefreshPaper.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            refreshPaper(e);
                            btnRefreshPaperMouseClicked(e);
                        }
                    });
                    tpPaper.add(btnRefreshPaper, BorderLayout.SOUTH);

                    //======== pnPaper ========
                    {
                        pnPaper.setLayout(new GridLayout(4, 2));
                        pnPaper.add(cbPaperVersion);

                        //---- btnInstall ----
                        btnInstall.setText(bundle.getString("InstallerPanel.btnInstall.text"));
                        btnInstall.setActionCommand("Install");
                        btnInstall.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                installPaper(e);
                            }
                        });
                        pnPaper.add(btnInstall);
                    }
                    tpPaper.add(pnPaper, BorderLayout.CENTER);
                }
                tpInstaller.addTab("Paper", tpPaper);

                //======== tpForge ========
                {
                    tpForge.setLayout(new BorderLayout());
                }
                tpInstaller.addTab("Forge", tpForge);

                //======== tpMohist ========
                {
                    tpMohist.setLayout(new GridLayout(4, 1));
                    tpMohist.add(cbMohistVersions);

                    //---- btnMohistRefresh ----
                    btnMohistRefresh.setText(bundle.getString("InstallerPanel.btnMohistRefresh.text"));
                    btnMohistRefresh.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            refreshMohist(e);
                        }
                    });
                    tpMohist.add(btnMohistRefresh);

                    //---- btnMohistInstall ----
                    btnMohistInstall.setText(bundle.getString("InstallerPanel.btnMohistInstall.text"));
                    btnMohistInstall.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            installMohist(e);
                        }
                    });
                    tpMohist.add(btnMohistInstall);
                }
                tpInstaller.addTab(bundle.getString("InstallerPanel.tpMohist.tab.title"), tpMohist);
            }
            installer.add(tpInstaller, BorderLayout.CENTER);
        }
        add(installer, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel installer;
    private JTabbedPane tpInstaller;
    private JPanel tpPaper;
    private JButton btnRefreshPaper;
    private JPanel pnPaper;
    private JComboBox cbPaperVersion;
    private JButton btnInstall;
    private JPanel tpForge;
    private JPanel tpMohist;
    private JComboBox cbMohistVersions;
    private JButton btnMohistRefresh;
    private JButton btnMohistInstall;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
