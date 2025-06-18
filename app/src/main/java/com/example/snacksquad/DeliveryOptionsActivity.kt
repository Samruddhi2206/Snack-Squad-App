package com.example.snacksquad

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class DeliveryOptionsActivity : AppCompatActivity() {

    private lateinit var deliveryTypeGroup: RadioGroup
    private lateinit var dateText: TextView
    private lateinit var timeText: TextView
    private lateinit var selectDateBtn: Button
    private lateinit var selectTimeBtn: Button
    private lateinit var proceedBtn: Button

    private var selectedDate: String = ""
    private var selectedTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_options)

        deliveryTypeGroup = findViewById(R.id.deliveryTypeGroup)
        dateText = findViewById(R.id.dateText)
        timeText = findViewById(R.id.timeText)
        selectDateBtn = findViewById(R.id.selectDateBtn)
        selectTimeBtn = findViewById(R.id.selectTimeBtn)
        proceedBtn = findViewById(R.id.proceedToPaymentBtn)

        selectDateBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this,
                { _, year, month, dayOfMonth ->
                    selectedDate = "$dayOfMonth/${month + 1}/$year"
                    dateText.text = "Selected Date: $selectedDate"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        selectTimeBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePicker = TimePickerDialog(this,
                { _, hour, minute ->
                    selectedTime = String.format("%02d:%02d", hour, minute)
                    timeText.text = "Selected Time: $selectedTime"
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePicker.show()
        }

        proceedBtn.setOnClickListener {
            val selectedTypeId = deliveryTypeGroup.checkedRadioButtonId
            if (selectedTypeId == -1 || selectedDate.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(this, "Please select all delivery details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val type = findViewById<RadioButton>(selectedTypeId).text.toString()
            Toast.makeText(this, "Delivery: $type at $selectedDate $selectedTime", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("deliveryType", type)
            intent.putExtra("deliveryDate", selectedDate)
            intent.putExtra("deliveryTime", selectedTime)
            startActivity(intent)
        }
    }
}
