package cn.afternode.csi.utils

object ProcessUtils {
    @JvmStatic
    fun waitForOutput(process: Process, content: String, output: Boolean = true) {
        val reader = process.inputReader(Charsets.UTF_8)
        lateinit var line: String
        while (reader.readLine().also { line = it } != null) {
            if (line.contains(content)) {
                break
            }
        }
    }
}