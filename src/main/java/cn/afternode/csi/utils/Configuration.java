package cn.afternode.csi.utils;


import java.io.File;
import java.util.Locale;

public class Configuration {
    public String installDir = System.getProperty("user.dir");
    public boolean useBmclApi = false;

    public String javaExec;
    public String ram = "4G";

    public Configuration() {
        if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("windows")) {
            javaExec = System.getProperty("java.home") + "\\bin\\java.exe";
        } else {
            javaExec = System.getProperty("java.home") + "/bin/java";
        }
    }

    public File getInstallDir() {
        return new File(installDir);
    }
}
