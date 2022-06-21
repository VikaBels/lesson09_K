package com.example.lesson09_k

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {
    private var btnZero: Button? = null
    private var btnOne: Button? = null
    private var btnTwo: Button? = null
    private var btnThree: Button? = null
    private var btnFour: Button? = null
    private var btnFive: Button? = null
    private var btnSix: Button? = null
    private var btnSeven: Button? = null
    private var btnEight: Button? = null
    private var btnNine: Button? = null
    private var btnPlus: Button? = null
    private var btnMinus: Button? = null
    private var btnDivide: Button? = null
    private var btnMultiply: Button? = null
    private var btnEqual: Button? = null
    private var btnClear: Button? = null
    private var btnOk: Button? = null

    private var txtViewResult: TextView? = null

    private val line = StringBuilder()
    private val number = StringBuilder()

    private var num1 = 0
    private var num2 = 0
    private var operand: String? = null
    private var error = false

    private var helpOperand: String? = null
    private var helpInt = 0
    private var helperSum = 0

    fun findViewById() {
        btnZero = findViewById(R.id.btnZero)
        btnOne = findViewById(R.id.btnOne)
        btnTwo = findViewById(R.id.btnTwo)
        btnThree = findViewById(R.id.btnThree)
        btnFour = findViewById(R.id.btnFour)
        btnFive = findViewById(R.id.btnFive)
        btnSix = findViewById(R.id.btnSix)
        btnSeven = findViewById(R.id.btnSeven)
        btnEight = findViewById(R.id.btnEight)
        btnNine = findViewById(R.id.btnNine)
        btnPlus = findViewById(R.id.btnPlus)
        btnMinus = findViewById(R.id.btnMinus)
        btnDivide = findViewById(R.id.btnDivide)
        btnMultiply = findViewById(R.id.btnMultiply)
        btnEqual = findViewById(R.id.btnEqual)
        btnClear = findViewById(R.id.btnClear)
        btnOk = findViewById(R.id.btnOk)
        txtViewResult = findViewById(R.id.txtResult)
    }

    fun setOnClickListener(allButton: View.OnClickListener?) {
        btnZero!!.setOnClickListener(allButton)
        btnOne!!.setOnClickListener(allButton)
        btnTwo!!.setOnClickListener(allButton)
        btnThree!!.setOnClickListener(allButton)
        btnFour!!.setOnClickListener(allButton)
        btnFive!!.setOnClickListener(allButton)
        btnSix!!.setOnClickListener(allButton)
        btnSeven!!.setOnClickListener(allButton)
        btnEight!!.setOnClickListener(allButton)
        btnNine!!.setOnClickListener(allButton)
        btnPlus!!.setOnClickListener(allButton)
        btnMinus!!.setOnClickListener(allButton)
        btnDivide!!.setOnClickListener(allButton)
        btnMultiply!!.setOnClickListener(allButton)
        btnEqual!!.setOnClickListener(allButton)
        btnClear!!.setOnClickListener(allButton)
        btnOk!!.setOnClickListener(allButton)
    }

    fun selectedButton(selectedButton: Button) {
        number.append(selectedButton.text.toString().trim { it <= ' ' })
        txtViewResult!!.text = number
        line.append(selectedButton.text.toString().trim { it <= ' ' })
    }

    fun workWithOperand(selectedOperand: String, selectedButton: Button) {
        if (selectedOperand == "=" && operand == "=") {
            num1 = helperSum
            operand = helpOperand
        }
        if (num1 == 0) {
            num1 = number.toString().toInt()
            operand = selectedOperand
        } else if (num2 == 0) {
            try {
                num2 = number.toString().toInt()
                helpInt = num2
                helpOperand = operand
            } catch (e: Exception) {
                if (helpInt == 0) {
                    helpInt = num1
                    helpOperand = operand
                }
                num2 = helpInt
            }
            when (operand) {
                "+" -> num1 = num1 + num2
                "-" -> num1 = num1 - num2
                "/" -> if (num2 == 0) {
                    error = true
                } else {
                    num1 = num1 / num2
                }
                "*" -> num1 = num1 * num2
            }
            operand = selectedOperand
            num2 = 0
        }
        if (selectedOperand == "=") {
            txtViewResult!!.text = if (error) "ERROR" else num1.toString()
            number.setLength(0)
            helperSum = num1
            //num1 = 0;
        } else {
            number.setLength(0)
            line.append(selectedButton.text.toString().trim { it <= ' ' })
        }
    }

    fun clearVariables() {
        operand = ""
        num1 = 0
        helpInt = 0
        helperSum = 0
        helpOperand = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        findViewById()

        val allButton = View.OnClickListener { v ->
            when (v.id) {
                R.id.btnZero -> selectedButton(btnZero!!)
                R.id.btnOne -> selectedButton(btnOne!!)
                R.id.btnTwo -> selectedButton(btnTwo!!)
                R.id.btnThree -> selectedButton(btnThree!!)
                R.id.btnFour -> selectedButton(btnFour!!)
                R.id.btnFive -> selectedButton(btnFive!!)
                R.id.btnSix -> selectedButton(btnSix!!)
                R.id.btnSeven -> selectedButton(btnSeven!!)
                R.id.btnEight -> selectedButton(btnEight!!)
                R.id.btnNine -> selectedButton(btnNine!!)
                R.id.btnPlus -> workWithOperand("+", btnPlus!!)
                R.id.btnMinus -> workWithOperand("-", btnMinus!!)
                R.id.btnDivide -> workWithOperand("/", btnDivide!!)
                R.id.btnMultiply -> workWithOperand("*", btnMultiply!!)
                R.id.btnClear -> {
                    txtViewResult!!.text = "0"
                    number.setLength(0)
                    clearVariables()
                }
                R.id.btnOk -> {
                    val intent = Intent()
                    intent.putExtra("currentDigit", txtViewResult!!.text.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                }
                R.id.btnEqual -> workWithOperand("=", btnEqual!!)
            }
        }

        setOnClickListener(allButton)
    }

    override fun onDestroy() {
        super.onDestroy()
        btnZero = null
        btnOne = null
        btnTwo = null
        btnThree = null
        btnFour = null
        btnFive = null
        btnSix = null
        btnSeven = null
        btnEight = null
        btnNine = null
        btnPlus = null
        btnMinus = null
        btnDivide = null
        btnMultiply = null
        btnEqual = null
        btnClear = null
        btnOk = null
        txtViewResult = null
    }
}