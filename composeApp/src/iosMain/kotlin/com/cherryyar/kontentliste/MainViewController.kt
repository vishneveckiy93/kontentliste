package com.cherryyar.kontentliste

import androidx.compose.ui.window.ComposeUIViewController
import app.App
import platform.UIKit.UIViewController
import core.di.Di

fun MainViewController(): UIViewController {
    Di.init()
    return ComposeUIViewController { App() }
}