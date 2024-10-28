package com.example.moneyconverter

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.log

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, OnClickListener {

    var selected: Int = 0
    lateinit var firstAmount: TextView
    lateinit var secondAmount: TextView
    lateinit var firstCurrency: Spinner
    lateinit var secondCurrency: Spinner
    var currency1 = ""
    var currency2 = ""

    val conversionRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.85,
        "GBP" to 0.75,
        "JPY" to 110.0,
        "INR" to 74.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firstAmount = findViewById(R.id.firstAmount)
        secondAmount = findViewById(R.id.secondAmount)
        firstCurrency = findViewById(R.id.firstCurrency)
        secondCurrency = findViewById(R.id.secondCurrency)

        firstAmount.setOnClickListener(this)
        secondAmount.setOnClickListener(this)

        firstAmount.text = "0"
        secondAmount.text = "0"

        findViewById<Button>(R.id.button0).setOnClickListener(this)
        findViewById<Button>(R.id.button1).setOnClickListener(this)
        findViewById<Button>(R.id.button2).setOnClickListener(this)
        findViewById<Button>(R.id.button3).setOnClickListener(this)
        findViewById<Button>(R.id.button4).setOnClickListener(this)
        findViewById<Button>(R.id.button5).setOnClickListener(this)
        findViewById<Button>(R.id.button6).setOnClickListener(this)
        findViewById<Button>(R.id.button7).setOnClickListener(this)
        findViewById<Button>(R.id.button8).setOnClickListener(this)
        findViewById<Button>(R.id.button9).setOnClickListener(this)
        findViewById<Button>(R.id.buttonDot).setOnClickListener(this)
        findViewById<Button>(R.id.buttonC).setOnClickListener(this)
        findViewById<Button>(R.id.buttonCE).setOnClickListener(this)


        ArrayAdapter.createFromResource(
            this,
            R.array.currencies,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            firstCurrency.adapter = adapter
            secondCurrency.adapter = adapter
        }

        firstCurrency.onItemSelectedListener = this
        secondCurrency.onItemSelectedListener = this

        firstAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val amount = s.toString().toDoubleOrNull()
                if(amount != null && selected == 0) {
                    val fromRate = conversionRates[currency1]
                    val toRate = conversionRates[currency2]
                    if(fromRate != null && toRate != null) {
                        val converted = amount * fromRate / toRate
                        secondAmount.text = String.format("%.2f", converted)
                    }
                }
            }
        })
        secondAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val amount = s.toString().toDoubleOrNull()
                if(amount != null && selected == 1) {
                    val fromRate = conversionRates[currency2]
                    val toRate = conversionRates[currency1]
                    if(fromRate != null && toRate != null) {
                        val converted = amount * fromRate / toRate
                        firstAmount.text = String.format("%.2f", converted)
                    }
                }
            }
        })
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.i("Currency", parent?.getItemAtPosition(position).toString())
        if(parent?.id == R.id.firstCurrency) {
            currency1 = parent.getItemAtPosition(position).toString()
            val amount = firstAmount.text.toString().toDoubleOrNull()
            if(amount != null) {
                val fromRate = conversionRates[currency1]
                val toRate = conversionRates[currency2]
                if(fromRate != null && toRate != null) {
                    val converted = amount * fromRate / toRate
                    secondAmount.text = String.format("%.2f", converted)
                }
            }
        } else {
            currency2 = parent?.getItemAtPosition(position).toString()
            val amount = secondAmount.text.toString().toDoubleOrNull()
            if(amount != null) {
                val fromRate = conversionRates[currency2]
                val toRate = conversionRates[currency1]
                if(fromRate != null && toRate != null) {
                    val converted = amount * fromRate / toRate
                    firstAmount.text = String.format("%.2f", converted)
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.firstAmount -> {
                selected = 0
                firstAmount.setTypeface(firstAmount.typeface, Typeface.BOLD)
                secondAmount.setTypeface(secondAmount.typeface, Typeface.NORMAL)
                Log.i("Amount", "First amount")
            }
            R.id.secondAmount -> {
                selected = 1
                firstAmount.setTypeface(firstAmount.typeface, Typeface.NORMAL)
                secondAmount.setTypeface(secondAmount.typeface, Typeface.BOLD)
            }
            R.id.button0 -> addDigit("0")
            R.id.button1 -> addDigit("1")
            R.id.button2 -> addDigit("2")
            R.id.button3 -> addDigit("3")
            R.id.button4 -> addDigit("4")
            R.id.button5 -> addDigit("5")
            R.id.button6 -> addDigit("6")
            R.id.button7 -> addDigit("7")
            R.id.button8 -> addDigit("8")
            R.id.button9 -> addDigit("9")
            R.id.buttonDot -> addDigit(".")
            R.id.buttonCE -> {
                if(selected == 0) {
                    firstAmount.text = "0"
                } else {
                    secondAmount.text = "0"
                }
            }
            R.id.buttonC -> {
                if (selected == 0) {
                    firstAmount.text = firstAmount.text.toString().dropLast(1)
                    if(firstAmount.text.toString() == "") firstAmount.text = "0"
                } else {
                    secondAmount.text = secondAmount.text.toString().dropLast(1)
                    if(secondAmount.text.toString() == "") secondAmount.text = "0"
                }
            }
        }
    }

    private fun addDigit(digit: String) {
        if(selected == 0) {
            firstAmount.text = if(firstAmount.text.toString() != "0") firstAmount.text.toString() + digit else digit
        } else {
            secondAmount.text = if (secondAmount.text.toString() != "0") secondAmount.text.toString() + digit else digit
        }
    }
}