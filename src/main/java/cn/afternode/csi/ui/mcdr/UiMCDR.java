/*
 * Created by JFormDesigner on Tue Oct 03 16:26:19 CST 2023
 */

package cn.afternode.csi.ui.mcdr;

import java.awt.event.*;
import cn.afternode.csi.CSI;
import cn.afternode.csi.file.FileManager;
import cn.afternode.csi.utils.MCDRConfiguration;
import cn.afternode.csi.utils.ProcessUtils;
import cn.afternode.csi.utils.ThreadRunner;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import javax.swing.*;

/**
 * @author Admin
 */
public class UiMCDR extends JPanel {

    public UiMCDR() throws IOException {
        initComponents();
        initPython();
    }

    private void initPython() {
        cbPypiMirror.addItem("None");
        cbPypiMirror.addItem("Tsinghua");

        tfPythonBin.setText(CSI.INSTANCE.getMcdrConfig().pythonBin);
        cbPypiMirror.setSelectedIndex(CSI.INSTANCE.getMcdrConfig().mirror);
    }

    private void savePython(MouseEvent e) {
        try {
            CSI.INSTANCE.getMcdrConfig().pythonBin = tfPythonBin.getText();
            CSI.INSTANCE.getMcdrConfig().mirror = cbPypiMirror.getSelectedIndex();
            CSI.INSTANCE.getFileManager().saveMCDRConfig(CSI.INSTANCE.getMcdrConfig());

            CSI.INSTANCE.getMainWindow().lbInformation.setText("Python configuration saved");
        } catch (Throwable t) {
            t.printStackTrace();
            JOptionPane.showMessageDialog(this, "Unable to save python configuration: " + t, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createUIComponents() {

    }

    private void initPypiMirror(ComponentEvent e) {
        cbPypiMirror.addItem("None");
        cbPypiMirror.addItem("Tsinghua");
    }

    private void installMcdr(MouseEvent e) {
        new ThreadRunner("CSI-MCDR-Installation", () -> {
            try {
                StringBuilder cmd = new StringBuilder();
                cmd.append(CSI.INSTANCE.getMcdrConfig().pythonBin);
                cmd.append(" -m pip install");
                switch (CSI.INSTANCE.getMcdrConfig().mirror) {
                    case 1:
                        cmd.append(" -i https://pypi.tuna.tsinghua.edu.cn/simple");
                    case 0:
                    default:
                        break;
                }
                cmd.append(" -U mcdreforged");

                CSI.INSTANCE.getMainWindow().lbInformation.setText("Executing command...");
                System.out.println("Executing command...");
                Process proc = ProcessUtils.exec(cmd.toString(), CSI.INSTANCE.getConfig().getInstallDir());
                proc.waitFor();
                CSI.INSTANCE.getMainWindow().lbInformation.setText("Process exited with code " + proc.exitValue());
                ProcessUtils.printOutputs(proc);
                System.out.println("Process exited with code " + proc.exitValue());
                JOptionPane.showMessageDialog(this, "Process exited with code " + proc.exitValue(), "Completed", JOptionPane.PLAIN_MESSAGE);
            } catch (Throwable t) {
                t.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error installing MCDR: " + t, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).run();
    }

    private void setupMcdr(MouseEvent e) {
        new ThreadRunner("CSI-MCDR-Setup", () -> {
            try {
                String cmd = CSI.INSTANCE.getMcdrConfig().pythonBin + " -m mcdreforged init";
                CSI.info("Executing command");
                Process proc = ProcessUtils.exec(cmd, CSI.INSTANCE.getConfig().getInstallDir());
                proc.waitFor();
                ProcessUtils.printOutputs(proc);
                CSI.info("Process exited with code " + proc.exitValue());
                JOptionPane.showMessageDialog(this, "Process exited with code " + proc.exitValue(), "Completed", JOptionPane.PLAIN_MESSAGE);
            } catch (Throwable t) {
                t.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error setting up MCDR: " + t, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).run();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ResourceBundle bundle = ResourceBundle.getBundle("lang.mcdr");
        tp = new JTabbedPane();
        pnInstallation = new JPanel();
        pnMcdrSetup = new JPanel();
        btnInstallMcdr = new JButton();
        btnSetupMcdr = new JButton();
        pnPython = new JPanel();
        kvPythonBin = new JPanel();
        lbPythonBin = new JLabel();
        tfPythonBin = new JTextField();
        kvPypiMirror = new JPanel();
        lbPypiMirror = new JLabel();
        cbPypiMirror = new JComboBox();
        btnSavePython = new JButton();

        //======== this ========
        setLayout(new BorderLayout());

        //======== tp ========
        {

            //======== pnInstallation ========
            {
                pnInstallation.setLayout(new BorderLayout());

                //======== pnMcdrSetup ========
                {
                    pnMcdrSetup.setLayout(new GridLayout(1, 2));

                    //---- btnInstallMcdr ----
                    btnInstallMcdr.setText(bundle.getString("btnInstallMcdr.text"));
                    btnInstallMcdr.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            installMcdr(e);
                        }
                    });
                    pnMcdrSetup.add(btnInstallMcdr);

                    //---- btnSetupMcdr ----
                    btnSetupMcdr.setText(bundle.getString("btnSetupMcdr.text"));
                    btnSetupMcdr.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            setupMcdr(e);
                        }
                    });
                    pnMcdrSetup.add(btnSetupMcdr);
                }
                pnInstallation.add(pnMcdrSetup, BorderLayout.SOUTH);
            }
            tp.addTab(bundle.getString("pnInstallation.tab.title"), pnInstallation);

            //======== pnPython ========
            {
                pnPython.setLayout(new GridLayout(6, 1));

                //======== kvPythonBin ========
                {
                    kvPythonBin.setLayout(new GridLayout(1, 2));

                    //---- lbPythonBin ----
                    lbPythonBin.setText(bundle.getString("lbPythonBin.text"));
                    kvPythonBin.add(lbPythonBin);
                    kvPythonBin.add(tfPythonBin);
                }
                pnPython.add(kvPythonBin);

                //======== kvPypiMirror ========
                {
                    kvPypiMirror.setLayout(new GridLayout(1, 2));

                    //---- lbPypiMirror ----
                    lbPypiMirror.setText(bundle.getString("lbPypiMirror.text"));
                    kvPypiMirror.add(lbPypiMirror);

                    //---- cbPypiMirror ----
                    cbPypiMirror.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentShown(ComponentEvent e) {
                            initPypiMirror(e);
                        }
                    });
                    kvPypiMirror.add(cbPypiMirror);
                }
                pnPython.add(kvPypiMirror);

                //---- btnSavePython ----
                btnSavePython.setText(bundle.getString("btnSavePython.text"));
                btnSavePython.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        savePython(e);
                    }
                });
                pnPython.add(btnSavePython);
            }
            tp.addTab(bundle.getString("pnPython.tab.title"), pnPython);
        }
        add(tp, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JTabbedPane tp;
    private JPanel pnInstallation;
    private JPanel pnMcdrSetup;
    private JButton btnInstallMcdr;
    private JButton btnSetupMcdr;
    private JPanel pnPython;
    private JPanel kvPythonBin;
    private JLabel lbPythonBin;
    private JTextField tfPythonBin;
    private JPanel kvPypiMirror;
    private JLabel lbPypiMirror;
    private JComboBox cbPypiMirror;
    private JButton btnSavePython;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
