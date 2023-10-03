package cn.afternode.csi.file

import cn.afternode.csi.utils.Configuration
import cn.afternode.csi.utils.MCDRConfiguration
import com.google.gson.GsonBuilder
import java.io.File

class FileManager {
    companion object {
        @JvmStatic val gson = GsonBuilder().setPrettyPrinting().create()

        @JvmStatic val configFile = File("config.json")
        @JvmStatic val mcdrConfigFile = File("csi-mcdr.json")
    }

    fun loadConfig(): Configuration {
        return if (!configFile.exists()) {
            val cfg = Configuration()
            configFile.writeText(gson.toJson(cfg))
            cfg
        } else {
            gson.fromJson(configFile.readText(Charsets.UTF_8), Configuration::class.java)
        }
    }

    fun saveConfig(config: Configuration) {
        configFile.writeText(gson.toJson(config), Charsets.UTF_8)
    }

    fun loadMCDRConfig(): MCDRConfiguration {
        return if (!mcdrConfigFile.exists()) {
            val cfg = MCDRConfiguration()
            mcdrConfigFile.writeText(gson.toJson(cfg))
            cfg
        } else {
            gson.fromJson(mcdrConfigFile.readText(Charsets.UTF_8), MCDRConfiguration::class.java)
        }
    }

    fun saveMCDRConfig(config: MCDRConfiguration) {
        mcdrConfigFile.writeText(gson.toJson(config), Charsets.UTF_8)
    }
}