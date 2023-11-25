package com.example.mathbanoclone2.apps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mathbanoclone2.R
import com.example.mathbanoclone2.ui.theme.Mathbanoclone2Theme
import java.text.NumberFormat

@Preview
@Composable
fun TipCalculator() {
    Mathbanoclone2Theme(false) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(1f),
            color = MaterialTheme.colorScheme.background
        ) {
            TipCalculatorMain()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculatorMain(
    modifier: Modifier = Modifier
) {
    val billValue = remember { mutableStateOf("") }
    val amount = billValue.value.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "Calculate Tip", textAlign = TextAlign.Center)
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = billValue.value,
            singleLine = true,
            onValueChange = {
                billValue.value = it
            }, label = {
                Text(text = "Bill Amount")
            },
            modifier = modifier
        )

        // getting the 15% of the bill amount which is equal to the product of 0.15 by bill amount
        Text(text = stringResource(R.string.tip_amount, tip), style = MaterialTheme.typography.displaySmall)
    }
}

private fun calculateTip(amount: Double, tipPercent: Double = 15.0): String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}