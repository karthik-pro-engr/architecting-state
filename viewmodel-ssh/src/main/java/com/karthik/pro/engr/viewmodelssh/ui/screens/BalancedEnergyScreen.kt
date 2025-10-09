package com.karthik.pro.engr.viewmodelssh.ui.screens

/***
 * In a town, each house either produces electricity (producer house) or consumes electricity (consumer house).
 * You want to find the longest continuous stretch of houses where the total electricity balances out (no surplus, no deficit).
 */
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.viewmodelssh.ui.viewmodel.BalancedEnergyViewModel
import com.karthik.pro.engr.devtools.AllVariantsPreview
import com.karthik.pro.engr.res.R
import kotlin.collections.isNotEmpty
import kotlin.collections.joinToString
import kotlin.let
import kotlin.text.all
import kotlin.text.isEmpty
import kotlin.text.isLetter
import kotlin.text.isNotEmpty

@Composable
fun BalancedEnergyScreen(
    modifier: Modifier = Modifier,
    viewModel: BalancedEnergyViewModel = viewModel()
) {
    var input by rememberSaveable { mutableStateOf("") }
    var enableAddButton by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            InputTextField(
                modifier
                    .weight(1f)
                    .padding(end = 10.dp),
                input,
                onValueChange = { value ->
                    if (value.all { it.isLetter() }) input = value
                },
                viewModel.errorMessage
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    viewModel.addHouseType(input)
                    input = ""
                },
                enabled = enableAddButton
            ) {
                Text(stringResource(R.string.button_add_house_type))
            }
        }

        if (viewModel.errorMessage.isEmpty()) {
            Text(
                text = when {
                    viewModel.houseTypes.isNotEmpty() -> {
                        "[ ${viewModel.houseTypes.joinToString(", ")} ]"
                    }

                    else -> stringResource(
                        R.string.text_no_house_types_added
                    )
                }
            )
        } else {
            Text(
                text = viewModel.errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (viewModel.houseTypes.size > 1) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                enableAddButton = false
                viewModel.computeResult()
            }, enabled = enableAddButton) {
                Text(text = stringResource(R.string.button_find_longest_stretch))
            }
            viewModel.stretchResult?.let {
                Text(
                    "The Longest Stretch Houses Starts from" +
                            " ${it.startIndex + 1} to ${it.endIndex + 1}"
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
        Button(onClick = {
            enableAddButton = true
            viewModel.reset()
        }) {
            Text(stringResource(R.string.button_reset))
        }
    }
}

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.label_house_type)) },
        placeholder = { Text(stringResource(R.string.placeholder_input)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        isError = errorMessage.isNotEmpty(),
        modifier = modifier
    )
}

@AllVariantsPreview
@Composable
private fun BalancedEnergyScreenPreview() {
    BalancedEnergyScreen()
}