package com.cherryyar.kontentliste

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform