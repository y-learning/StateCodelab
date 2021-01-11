package com.why.codelabs.state

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import com.why.codelabs.state.theme.StateCodelabTheme

class MainActivity : AppCompatActivity() {
//    val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateCodelabTheme {
                Surface {
                    // TODO: build the screen in compose
                }
            }
        }
    }
}
