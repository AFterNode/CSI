package cn.afternode.csi.utils

object MapUtils {
    @JvmStatic
    fun getAsMap(map: Map<String, Any>, key: String): Map<String, Any> {
        return map[key] as Map<String, Any>
    }
}