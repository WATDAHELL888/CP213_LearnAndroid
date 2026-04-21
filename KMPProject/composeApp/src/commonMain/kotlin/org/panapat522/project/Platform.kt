package org.panapat522.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform