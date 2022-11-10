package com.zcf.kmmdemo

class PlatformInfo {
    private val platform: Platform = getPlatform()

    fun info(): String {
        return "Hello, ${platform.name}!"
    }
}