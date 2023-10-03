package cn.afternode.csi.installer.impl

import cn.afternode.csi.CSI
import cn.afternode.csi.installer.Installer
import cn.afternode.csi.utils.HashUtil
import cn.afternode.csi.utils.HttpUtils
import cn.afternode.csi.utils.ProcessUtils
import cn.afternode.csi.utils.SubConfig
import cn.afternode.csi.utils.mohist.MohistVersionMeta
import com.google.gson.Gson
import org.apache.hc.core5.http.Chars
import java.io.File
import java.util.concurrent.TimeUnit
import javax.swing.JOptionPane

class Mohist: Installer {
    override val name: String = "Mohist"

    override fun getVersions(): List<String> {
        val get = """
            {"versions": ${HttpUtils.get("https://mohistmc.com/api/versions")}}
        """.trimIndent()
        val map = Gson().fromJson(get, HashMap::class.java) as Map<String, Any>
        return map["versions"] as List<String>
    }

    override fun installVersion(name: String) {
        try {
            val state = CSI.mainWindow.lbInformation
            val gson = Gson()
            val serverFile = File(CSI.config.getInstallDir(), "mohist.jar")

            // Fetch
            println("Fetching version $name")
            state.text = "Fetching version $name"
            val versionGet = HttpUtils.get("https://mohistmc.com/api/$name/latest")
            println(versionGet)
            val version = gson.fromJson(versionGet, MohistVersionMeta::class.java)

            // Download
            println("Downloading ${version.name}")
            state.text = "Downloading ${version.name}"
            HttpUtils.download(version.url, serverFile)
            println("Downloaded successfully, executing checksum")
            val md5 = HashUtil.md5(serverFile)
            println("Excepted ${version.hash}, get $md5")
            if (!md5.equals(version.hash)) {
                val opt = JOptionPane.showConfirmDialog(CSI.mainWindow, "Checksum mismatched, excepted ${version.hash}, get $md5, do you want to continue?", "Checksum Mismatched", JOptionPane.YES_NO_OPTION)
                println("Checksum mismatched")
                if (opt != JOptionPane.YES_OPTION) {
                    println("Operation cancelled")
                    state.text = "Operation cancelled"
                    return
                }
            }

            // Launch Script
            println("Generating launch script")
            state.text = "Generating launch script"
            val launchScript = File(CSI.config.getInstallDir(), "launch.cmd")
            launchScript.writeText("""
                "${CSI.config.javaExec}" -Dfile.encoding=UTF-8 -Xms1G -Xmx${CSI.config.ram} -jar mohist.jar nogui
            """.trimIndent(), Charsets.UTF_8)

            // Generate sub config
            val sc = SubConfig()
            val scFile = File(CSI.config.getInstallDir(), ".csi")
            sc.serverType = "mohist"
            scFile.writeText(gson.toJson(sc), Charsets.UTF_8)

            state.text = "Completed"
            println("Installation successful")
            JOptionPane.showMessageDialog(CSI.mainWindow, "Downloaded server and generated launch script.\nFor Mohist, please run the launch script to install", "Done", JOptionPane.PLAIN_MESSAGE)
        } catch (t: Throwable) {
            JOptionPane.showMessageDialog(CSI.mainWindow, "Unable to install Mohist $name:\n $t", "Error", JOptionPane.ERROR_MESSAGE)
            t.printStackTrace()
        }
    }
}