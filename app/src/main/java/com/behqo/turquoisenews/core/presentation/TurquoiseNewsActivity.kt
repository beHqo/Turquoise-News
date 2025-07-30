package com.behqo.turquoisenews.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.behqo.turquoisenews.core.presentation.root.RootScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TurquoiseNewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent { RootScreen() }
    }
}