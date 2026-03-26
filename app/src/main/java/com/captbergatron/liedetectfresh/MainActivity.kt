package com.captbergatron.liedetectfresh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.captbergatron.liedetectfresh.ui.theme.LieDetectFreshTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LieDetectFreshTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
//Today's date is 24 March
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun LieDetectorApp() {
    //1. State Declaration (the brain)
    val isLieTarget = remember { mutableStateOf(false) }
    val isScanning = remember { mutableStateOf(false) }
    val showResult = remember { mutableStateOf(false) }

    LaunchedEffect(isScanning.value) {
        if (isScanning.value) {
            delay(3000)
            isScanning.value = false
            showResult.value = true
        }
    }

        //2. Layout (the body)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Admin Row (secret buttons)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { isLieTarget.value = false }) {
                    Text("Truth Button")
                }
                Button(onClick = { isLieTarget.value = true }) {
                    Text("Lie Button")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                isScanning.value = true //Start the fake sci-fi stuff
            }) {
                Text("Scan Fingerprint")
            }

            //CONDITIONAL UI
            if (isScanning.value) {
                Text("Scanning... Hold still.")
            } else if (showResult.value) {
                Button(onClick = {
                    showResult.value = false
                    isScanning.value = false
                   }) {
                    Text("Reset")
                }
                // This is where the prank's Reveal happens
                if (isLieTarget.value) {
                    Text("LIE DETECTED", color = Color.Red)
                } else {
                    Text("TRUTH CONFIRMED", color = Color.Green)
                }
            } else {
                Button(onClick = { isScanning.value = true }) {
                    Text("Scan Fingerprint")
                }
            }
        }
    }





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LieDetectFreshTheme {
        Greeting("Android")
    }
}