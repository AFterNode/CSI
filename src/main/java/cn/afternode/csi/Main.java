package cn.afternode.csi;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class Main {
    public static void main(String[] args) {
        FlatMacDarkLaf.setup();

        CSI.INSTANCE.launch(args);
    }
}
