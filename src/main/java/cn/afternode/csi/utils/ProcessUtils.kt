package cn.afternode.csi.utils

import java.io.File
import java.io.IOException

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

    @JvmStatic
    fun printOutputs(proc: Process) {
        try {
            val reader = proc.inputReader()
            lateinit var line: String
            while (reader.readLine().also { line = it } != null) {
                println(line)
            }
        } catch (t: Throwable) {
            println("Unable to print stdout of process ${proc.pid()}: ${t.stackTraceToString()}")
        }
    }

    @JvmStatic
    @Throws(IOException::class)
    fun exec(command: String, dir: File = File(System.getProperty("user.dir"))): Process {
        if (!dir.exists() || !dir.isDirectory) throw IOException("Invalid directory ${dir.absolutePath}")
        println("Executing command: $command")
        return Runtime.getRuntime().exec(command, emptyArray(), dir)
    }
}