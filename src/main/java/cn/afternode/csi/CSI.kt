package cn.afternode.csi

import cn.afternode.csi.file.FileManager
import cn.afternode.csi.installer.InstallerManager
import cn.afternode.csi.installer.impl.Mohist
import cn.afternode.csi.installer.impl.Paper
import cn.afternode.csi.ui.MainWindow
import cn.afternode.csi.utils.Configuration
import com.google.gson.GsonBuilder
import javax.swing.JOptionPane
import kotlin.system.exitProcess

object CSI {
    @JvmStatic val GSON = GsonBuilder().setPrettyPrinting().create()

    lateinit var config: Configuration
        private set
    lateinit var fileManager: FileManager
        private set
    lateinit var installerManager: InstallerManager
        private set

    lateinit var mainWindow: MainWindow
        private set

    fun launch(args: Array<String>) {
        try {
            fileManager = FileManager()
            installerManager = InstallerManager()
                .register(Paper())
                .register(Mohist())

            config = fileManager.loadConfig()

            mainWindow = MainWindow()
            mainWindow.isVisible = true
        } catch (t: Throwable) {
            JOptionPane.showMessageDialog(null, "Critical error in main process: ${t.stackTraceToString()}", "CSI", JOptionPane.ERROR_MESSAGE)
            handleExit(1)
        }
    }

    fun handleExit(status: Int) {
        try {
            FileManager.configFile.writeText(GSON.toJson(config), Charsets.UTF_8)
        } catch (t: Throwable) {
            t.printStackTrace()
        }

        exitProcess(status)
    }

    fun handleExit() {
        handleExit(0)
    }
}