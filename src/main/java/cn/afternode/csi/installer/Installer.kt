package cn.afternode.csi.installer

interface Installer {
    val name: String

    fun getVersions(): List<String>
    fun installVersion(name: String)
}