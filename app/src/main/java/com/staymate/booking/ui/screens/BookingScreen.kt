package com.staymate.booking.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.staymate.booking.data.Property
import com.staymate.booking.data.RoomOption
import com.staymate.booking.ui.rupees
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    property: Property,
    room: RoomOption,
    onBack: () -> Unit,
    onConfirm: (guestName: String, phone: String, moveInDate: String, months: Int) -> Unit
) {
    var guestName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var months by remember { mutableIntStateOf(6) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    val moveInDate = dateFormat.format(
        Date(datePickerState.selectedDateMillis ?: System.currentTimeMillis())
    )

    val nameError = guestName.isNotEmpty() && guestName.trim().length < 3
    val phoneError = phone.isNotEmpty() && phone.length != 10
    val canConfirm = guestName.trim().length >= 3 && phone.length == 10

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirm booking") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(Modifier.padding(14.dp)) {
                    Text(property.name, fontWeight = FontWeight.Bold)
                    Text(
                        text = "${room.type.label} · ${property.address}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
            OutlinedTextField(
                value = guestName,
                onValueChange = { guestName = it },
                label = { Text("Full name") },
                singleLine = true,
                isError = nameError,
                supportingText = if (nameError) {
                    { Text("Enter at least 3 characters") }
                } else null,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = { input -> phone = input.filter { it.isDigit() }.take(10) },
                label = { Text("Phone number") },
                singleLine = true,
                isError = phoneError,
                supportingText = if (phoneError) {
                    { Text("Enter a 10 digit number") }
                } else null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))
            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.DateRange, contentDescription = null)
                Spacer(Modifier.fillMaxWidth(0.05f))
                Text("Move-in date: $moveInDate")
            }

            Spacer(Modifier.height(20.dp))
            Text("Duration", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(onClick = { if (months > 1) months-- }, enabled = months > 1) {
                    Text("-")
                }
                Text(
                    text = "$months month${if (months > 1) "s" else ""}",
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedButton(onClick = { if (months < 24) months++ }, enabled = months < 24) {
                    Text("+")
                }
            }

            Spacer(Modifier.height(20.dp))
            Text("Payment summary", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            PriceRow("Rent (${rupees(room.monthlyRent)} × $months)", rupees(room.monthlyRent * months))
            PriceRow("Refundable deposit", rupees(property.securityDeposit))
            HorizontalDivider(Modifier.padding(vertical = 8.dp))
            PriceRow(
                label = "Total payable",
                value = rupees(room.monthlyRent * months + property.securityDeposit),
                emphasize = true
            )

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { onConfirm(guestName.trim(), phone, moveInDate, months) },
                enabled = canConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Confirm booking")
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PriceRow(label: String, value: String, emphasize: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = if (emphasize) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            style = if (emphasize) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            fontWeight = if (emphasize) FontWeight.Bold else FontWeight.Normal
        )
    }
}
