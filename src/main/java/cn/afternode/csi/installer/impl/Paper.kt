package cn.afternode.csi.installer.impl

import cn.afternode.csi.CSI
import cn.afternode.csi.installer.Installer
import cn.afternode.csi.utils.HttpUtils
import cn.afternode.csi.utils.SubConfig
import com.google.gson.Gson
import java.io.File
import java.math.BigDecimal
import javax.swing.JOptionPane

class Paper: Installer {
    override val name: String = "Paper"
    override fun getVersions(): List<String> {
        return try {
            val get = HttpUtils.get("https://api.papermc.io/v2/projects/paper")
            println(get)
            val result = Gson().fromJson(get, HashMap::class.java) as HashMap<String, Any>
            return result["versions"] as List<String>
        } catch (t: Throwable) {
            JOptionPane.showMessageDialog(CSI.mainWindow, "Unable to fetch Paper versions: $t", "Error", JOptionPane.ERROR_MESSAGE)
            t.printStackTrace()
            emptyList()
        }
    }

    override fun installVersion(name: String) {
        Thread {
            try {
                println("Trying to install version $name")
                val gson = Gson()

                // Fetch
                println("Fetching builds...")
                CSI.mainWindow.lbInformation.text = "Fetching builds..."
                val buildsGet = HttpUtils.get("https://api.papermc.io/v2/projects/paper/versions/$name/builds")
                val builds = (gson.fromJson(buildsGet, HashMap::class.java) as Map<String, Any>)["builds"] as List<Any>
                println("Total builds: ${builds.size}")

                // Download
                val selected = builds[builds.size - 1] as Map<String, Any>
                val buildId = BigDecimal(selected["build"] as Double).intValueExact()
                println("Downloading Paper-$name-${selected["build"]}")
                CSI.mainWindow.lbInformation.text = "Downloading Paper-$name-${selected["build"]}"
                val targetFile = File(CSI.config.getInstallDir(), "paper.jar")
                HttpUtils.download("https://api.papermc.io/v2/projects/paper/versions/$name/builds/$buildId/downloads/paper-$name-$buildId.jar", targetFile)
                println("Download completed")
                CSI.mainWindow.lbInformation.text = "Download completed"

                // BMCLAPI
                if (CSI.config.useBmclApi) {
                    val vanillaJar = File(CSI.config.getInstallDir(), "cache/mojang_$name.jar")
                    println("Downloading vanilla jar from BMCLAPI...")
                    CSI.mainWindow.lbInformation.text = "Downloading vanilla jar from BMCLAPI..."
                    HttpUtils.download("https://bmclapi2.bangbang93.com/version/$name/server", vanillaJar)
                    println("Download completed")
                    CSI.mainWindow.lbInformation.text = "Download completed"
                }

                // Launch Script & Paperclip
                val eula = File("eula.text")
                if (eula.exists()) eula.delete()
                println("Generating launch script...")
                CSI.mainWindow.lbInformation.text = "Generating launch script..."
                val launchScript = if (System.getProperty("os.name").lowercase().contains("windows")) {
                    File(CSI.config.getInstallDir(), "start.cmd")
                } else {
                    File(CSI.config.getInstallDir(), "start.sh")
                }
                launchScript.writeText("\"${CSI.config.javaExec}\" -Xms1G -Xmx${CSI.config.ram} -Dfile.encoding=UTF-8 -jar paper.jar nogui", Charsets.UTF_8)
                println("Running Paperclip...")
                CSI.mainWindow.lbInformation.text = "Running Paperclip..."
                val proc = Runtime.getRuntime().exec(launchScript.readText(Charsets.UTF_8))
                println("Paperclip started as ${proc.pid()}")
                proc.waitFor()
                println("Paperclip exited with code ${proc.exitValue()}")
                println(String(proc.inputStream.readAllBytes(), Charsets.UTF_8))
                CSI.mainWindow.lbInformation.text = "Paperclip exit with code ${proc.exitValue()}"
                if (proc.exitValue() != 0) {
                    JOptionPane.showMessageDialog(CSI.mainWindow, "Paperclip exited with non-zero code ${proc.exitValue()}, please check", "Warning", JOptionPane.WARNING_MESSAGE)
                    return@Thread
                }

                JOptionPane.showMessageDialog(CSI.mainWindow, "Paper $name installed successfully, please confirm the EULA by your self", "Done", JOptionPane.PLAIN_MESSAGE)

                println("Generating sub configuration...")
                val sub = File(CSI.config.getInstallDir(), ".csi")
                val cfg = SubConfig()
                cfg.serverType = "paper"
                sub.writeText(gson.toJson(cfg), Charsets.UTF_8)
            } catch (t: Throwable) {
                JOptionPane.showMessageDialog(CSI.mainWindow, "Error installing Paper $name: $t", "Error", JOptionPane.ERROR_MESSAGE)
                t.printStackTrace()
            }
        }.run {
            setName("Paper-Installer-$name")
            start()
        }
    }
}