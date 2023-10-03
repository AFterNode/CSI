package cn.afternode.csi.installer

class InstallerManager {
    private val installers = HashMap<String, Installer>()

    operator fun get(name: String): Installer? {
        return installers[name.lowercase()]
    }

    fun register(inst: Installer): InstallerManager {
        installers[inst.name.lowercase()] = inst
        return this
    }
}