package com.example.mathbanoclone2.apps

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mathbanoclone2.R
import com.example.mathbanoclone2.ui.theme.Mathbanoclone2Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun DiceApp() {
    Mathbanoclone2Theme(false) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            DiceAppMain()
        }
    }
}

@Composable
fun DiceAppMain(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
) {
//    var isDiceRolling = remember { mutableStateOf(false) }
    var result = remember { mutableStateOf(1) }
    val imageResource = when (result.value) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    // starting coroutine on user button click
    val diceRollCoroutine = rememberCoroutineScope()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = "dice with ${result.value} at the top"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            diceRollCoroutine.launch {
                try {
                    for (i in 1..10){
                        result.value = (1..6).random()
                        delay(100)
                    }
                } catch (e: Exception){
                    Log.d("diceApp", "Exception ${e.message}")
                }
            }
        }) {
            Text(stringResource(R.string.roll))
        }
    }
}