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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
                //Calling main app logic
                LieDetectorApp()
                }
            }
        }
    }
//Today's date is 27 March
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
                Button(onClick = { isLieTarget.value = false },
                    modifier = Modifier.alpha(0.1f) //Hides the button
                ) {
                    Text("Truth Button")
                }
                Button(onClick = { isLieTarget.value = true },
                    modifier = Modifier.alpha(0.1f) //Hides the button
                ) {
                    Text("Lie Button")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            //CONDITIONAL UI
            if (isScanning.value) {
                ScanningScreen()
            } else if (showResult.value) {
                ResultScreen(
                    isLieTarget,
                    onReset = {
                        showResult.value = false
                        isScanning.value = false
                    })
            }  else {
                Button(onClick = { isScanning.value = true }) {
                    Text("Scan Fingerprint")
                }
            }
        }
    }

@Composable
fun ScanningScreen() {
    Text("Scanning... Hold still.")
}

@Composable
fun ResultScreen(isLieTarget: MutableState<Boolean>, //Uses boolean parameter isLieTarget
                 onReset: () -> Unit //Passes the reset button press to the main app
) {
    Button(onClick = { onReset() }) { //Produces the reset button
        Text("Reset")
    }
    if (isLieTarget.value) { //Shows result based on the current state of isLieTarget
        Text("LIE DETECTED", color = Color.Red)
    } else {
        Text("TRUTH CONFIRMED", color = Color.Green)
    }
}

@Preview(showBackground = true)
@Composable
fun LieDetectorAppPreview   () {
    LieDetectFreshTheme {
        LieDetectorApp()
    }
}