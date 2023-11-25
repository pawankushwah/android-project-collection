package com.example.mathbanoclone2.apps

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mathbanoclone2.R
import com.example.mathbanoclone2.ui.theme.Mathbanoclone2Theme
import java.text.NumberFormat

@Preview
@Composable
fun TipCalculator2() {
    Mathbanoclone2Theme(false) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(1f),
            color = MaterialTheme.colorScheme.background
        ) {
            TipCalculator2Main()
        }
    }
}

@Composable
fun TipCalculator2Main() {
    var billValue by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }
//    var url by remember { mutableStateOf("http://www.google.com/") }
    val amount = billValue.toDoubleOrNull() ?: 0.00
    val tipPercentNumber = tipPercent.toDoubleOrNull() ?: 0.00
    val tip = calculateTip2(amount, tipPercentNumber, roundUp)

    Column(
        modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(text = "Calculate Tip", textAlign = TextAlign.Center)
        EditNumberField(
            label = R.string.bill_amount,
            numberValue = billValue,
            onValueChange = {
                billValue = it
            },
            keyBoardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            leadingIcon = R.drawable.baseline_price_change_24,
            modifier = Modifier.fillMaxWidth()
        )

        EditNumberField(
            label = R.string.how_was_the_service,
            numberValue = tipPercent,
            onValueChange = {
                tipPercent = it
            },
            keyBoardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            leadingIcon = R.drawable.baseline_percent_24,
            modifier = Modifier.fillMaxWidth()
        )

        RoundTheTipRow(roundUp, { roundUp = it })
//        TextField(
//            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Uri, imeAction = ImeAction.Go),
//            value = url,
//            singleLine = true,
//            onValueChange = { url = it },
//            label = { Text(stringResource(R.string.bill_amount)) },
//        )

        // getting the 15% of the bill amount which is equal to the product of 0.15 by bill amount
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNumberField(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int = R.drawable.baseline_attach_money_24,
    keyBoardOptions: KeyboardOptions,
    numberValue: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = numberValue,
        onValueChange = onValueChange,
        keyboardOptions = keyBoardOptions,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null) },
        singleLine = true,
        label = { Text(stringResource(id = label)) },
        modifier = modifier
    )
}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.round_up_tip))
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChange,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}

private fun calculateTip2(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean): String {
    var tip = tipPercent / 100 * amount
    if(roundUp) tip = kotlin.math.ceil(tip)
    return NumberFormat.getCurrencyInstance().format(tip)
}