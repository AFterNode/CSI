package cn.afternode.csi

import cn.afternode.csi.file.FileManager
import cn.afternode.csi.installer.InstallerManager
import cn.afternode.csi.installer.impl.Mohist
import cn.afternode.csi.installer.impl.Paper
import cn.afternode.csi.ui.MainWindow
import cn.afternode.csi.utils.Configuration
import kotlin.system.exitProcess

object CSI {
    lateinit var config: Configuration
        private set
    lateinit var fileManager: FileManager
        private set
    lateinit var installerManager: InstallerManager
        private set

    lateinit var mainWindow: MainWindow
        private set

    fun launch(args: Array<String>) {
        fileManager = FileManager()
        installerManager = InstallerManager()
            .register(Paper())
            .register(Mohist())

        config = fileManager.loadConfig()

        mainWindow = MainWindow()
        mainWindow.isVisible = true
    }

    fun handleExit() {

        exitProcess(0)
    }
}