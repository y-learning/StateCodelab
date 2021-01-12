package com.why.codelabs.state.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.why.codelabs.state.theme.StateCodelabTheme
import com.why.codelabs.state.viewmodels.HelloViewModel

@Composable
fun ReactiveInput(input: String, onValueChange: (String) -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        ) {
            Text(text = "Hello")
            Text(text = " ${input}!", fontWeight = FontWeight.Bold)
        }

        TextField(
            value = input,
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            backgroundColor = Color.Transparent,
            onValueChange = onValueChange
        )
    }
}

@Composable
fun Screen(vm: HelloViewModel) {
    val input by vm.name.observeAsState("")
    ReactiveInput(input, vm::onNameChanged)
}

@Preview(showBackground = true)
@Composable
fun ReactiveInputPreview() {
    Screen(HelloViewModel())
}

class HelloActivity : AppCompatActivity() {
    private val helloViewModel by viewModels<HelloViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateCodelabTheme {
                Surface {
                    Screen(helloViewModel)
                }
            }
        }
    }
}
