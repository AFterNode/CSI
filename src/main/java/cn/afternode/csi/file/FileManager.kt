package cn.afternode.csi.file

import cn.afternode.csi.utils.Configuration
import com.google.gson.GsonBuilder
import java.io.File

class FileManager {
    companion object {
        @JvmStatic val gson = GsonBuilder().setPrettyPrinting().create()

        @JvmStatic val configFile = File("config.json")
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
        configFile.writeText(gson.toJson(config))
    }
}