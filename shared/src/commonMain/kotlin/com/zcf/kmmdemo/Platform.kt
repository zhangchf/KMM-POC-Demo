package com.zcf.kmmdemo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform