package com.example.snacksquad

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class PaymentActivity : AppCompatActivity() {

    private lateinit var cardOption: RadioButton
    private lateinit var upiOption: RadioButton
    private lateinit var codOption: RadioButton
    private lateinit var paymentMethodGroup: RadioGroup

    // Card inputs
    private lateinit var cardNumberInput: TextInputEditText
    private lateinit var expiryInput: TextInputEditText
    private lateinit var cvvInput: TextInputEditText
    private lateinit var cardLayout: TextInputLayout
    private lateinit var expiryLayout: TextInputLayout
    private lateinit var cvvLayout: TextInputLayout
    private lateinit var cardLayoutContainer: LinearLayout

    // UPI input
    private lateinit var upiInput: TextInputEditText
    private lateinit var upiLayout: TextInputLayout
    private lateinit var upiLayoutContainer: LinearLayout

    private lateinit var payBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Payment method selection
        paymentMethodGroup = findViewById(R.id.paymentMethodGroup)
        cardOption = findViewById(R.id.cardOption)
        upiOption = findViewById(R.id.upiOption)
        codOption = findViewById(R.id.codOption)

        // Card views
        cardLayoutContainer = findViewById(R.id.cardLayoutContainer)
        cardNumberInput = findViewById(R.id.cardNumberEditText)
        expiryInput = findViewById(R.id.expiryEditText)
        cvvInput = findViewById(R.id.cvvEditText)
        cardLayout = findViewById(R.id.cardNumberLayout)
        expiryLayout = findViewById(R.id.expiryLayout)
        cvvLayout = findViewById(R.id.cvvLayout)

        // UPI views
        upiLayoutContainer = findViewById(R.id.upiLayoutContainer)
        upiInput = findViewById(R.id.upiEditText)
        upiLayout = findViewById(R.id.upiLayout)

        payBtn = findViewById(R.id.payBtn)

        // Format expiry field MM/YY
        expiryInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (text.length == 2 && !text.contains("/")) {
                    expiryInput.setText("$text/")
                    expiryInput.setSelection(3)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Show/hide fields based on selected option
        paymentMethodGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.cardOption -> {
                    cardLayoutContainer.visibility = View.VISIBLE
                    upiLayoutContainer.visibility = View.GONE
                }
                R.id.upiOption -> {
                    cardLayoutContainer.visibility = View.GONE
                    upiLayoutContainer.visibility = View.VISIBLE
                }
                R.id.codOption -> {
                    cardLayoutContainer.visibility = View.GONE
                    upiLayoutContainer.visibility = View.GONE
                }
            }
        }

        payBtn.setOnClickListener {
            when {
                cardOption.isChecked -> handleCardPayment()
                upiOption.isChecked -> handleUpiPayment()
                codOption.isChecked -> simulatePayment("Cash on Delivery selected")
                else -> Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleCardPayment() {
        val card = cardNumberInput.text.toString().replace(" ", "")
        val expiry = expiryInput.text.toString()
        val cvv = cvvInput.text.toString()

        var valid = true

        if (card.length != 16 || !card.all { it.isDigit() }) {
            cardLayout.error = "Invalid card number"
            valid = false
        } else cardLayout.error = null

        if (!expiry.matches(Regex("\\d{2}/\\d{2}"))) {
            expiryLayout.error = "Invalid expiry format (MM/YY)"
            valid = false
        } else expiryLayout.error = null

        if (cvv.length != 3 || !cvv.all { it.isDigit() }) {
            cvvLayout.error = "Invalid CVV"
            valid = false
        } else cvvLayout.error = null

        if (valid) simulatePayment("Card payment successful")
    }

    private fun handleUpiPayment() {
        val upi = upiInput.text.toString().trim()
        if (upi.isEmpty() || !upi.contains("@")) {
            upiLayout.error = "Invalid UPI ID"
        } else {
            upiLayout.error = null
            simulatePayment("UPI payment successful")
        }
    }

    private fun simulatePayment(message: String) {
        val dialog = ProgressDialog(this)
        dialog.setMessage("Processing payment...")
        dialog.setCancelable(false)
        dialog.show()

        Handler().postDelayed({
            dialog.dismiss()
            CartManager.clearCart()
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, PaymentSuccessActivity::class.java))
            finish()
        }, 3000)
    }
}
