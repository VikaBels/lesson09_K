package com.example.lesson09_k

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {
    companion object {
        private const val KEY_CURRENT_DIGIT = "currentDigit"
        private const val EMPTY_LINE =""

        private const val OPERAND_EQUAL: String = "="
        private const val OPERAND_PLUS: String = "+"
        private const val OPERAND_MINUS: String = "-"
        private const val OPERAND_MULTIPLY: String = "*"
        private const val OPERAND_DIVIDE: String = "/"
    }

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

    private val number = StringBuilder()

    private var num1 = 0
    private var num2 = 0
    private var operand: String? = null
    private var error = false

    private var helpOperand: String? = null
    private var helpInt = 0
    private var helperSum = 0

    private fun findViewsById() {
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

    private fun setOnClickListener(allButton: View.OnClickListener?) {
        btnZero?.setOnClickListener(allButton)
        btnOne?.setOnClickListener(allButton)
        btnTwo?.setOnClickListener(allButton)
        btnThree?.setOnClickListener(allButton)
        btnFour?.setOnClickListener(allButton)
        btnFive?.setOnClickListener(allButton)
        btnSix?.setOnClickListener(allButton)
        btnSeven?.setOnClickListener(allButton)
        btnEight?.setOnClickListener(allButton)
        btnNine?.setOnClickListener(allButton)
        btnPlus?.setOnClickListener(allButton)
        btnMinus?.setOnClickListener(allButton)
        btnDivide?.setOnClickListener(allButton)
        btnMultiply?.setOnClickListener(allButton)
        btnEqual?.setOnClickListener(allButton)
        btnClear?.setOnClickListener(allButton)
        btnOk?.setOnClickListener(allButton)
    }

    private fun clickOnNumberBtn(selectedButton: Button) {
        number.append(selectedButton.text.toString().trim { it <= ' ' })
        txtViewResult?.text = number
    }

    private fun parseStringBuilderToInt(number: StringBuilder): Int {
       return number.toString().toInt()
    }

    private fun workWithOperand( selectedButton: Button) {
        if (selectedButton.text == OPERAND_EQUAL && operand == OPERAND_EQUAL) {
            num1 = helperSum
            operand = helpOperand
        }

        val selectedNumber: Int = parseStringBuilderToInt(number)
        if (num1 == 0) {
            num1 = selectedNumber
            operand = selectedButton.text.toString()
        } else if (num2 == 0) {
            try {
                num2 = selectedNumber
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
                OPERAND_PLUS -> num1 += num2
                OPERAND_MINUS -> num1 -= num2
                OPERAND_DIVIDE ->
                    if (num2 == 0) {
                        error = true
                    } else {
                        num1 /= num2
                    }
                OPERAND_MULTIPLY -> num1 *= num2
            }
            operand = selectedButton.text.toString()
            num2 = 0
        }
        if (selectedButton.text.toString() == OPERAND_EQUAL) {
            txtViewResult?.text =
                if (error) resources.getString(R.string.error) else num1.toString()
            number.setLength(0)
            helperSum = num1
        } else {
            number.setLength(0)
        }
    }

    private fun clearVariables() {
        operand = EMPTY_LINE
        num1 = 0
        helpInt = 0
        helperSum = 0
        helpOperand = EMPTY_LINE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        findViewsById()

        val allButton = View.OnClickListener { v ->
            when (v.id) {
                R.id.btnZero, R.id.btnOne, R.id.btnTwo, R.id.btnThree,
                R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnSeven,
                R.id.btnEight, R.id.btnNine -> clickOnNumberBtn(findViewById(v.id))
                R.id.btnPlus, R.id.btnMinus,
                R.id.btnDivide, R.id.btnMultiply -> workWithOperand(findViewById(v.id))
                R.id.btnClear -> {
                    txtViewResult?.text = resources.getString(R.string.zero)
                    number.setLength(0)
                    clearVariables()
                }
                R.id.btnOk -> {
                    val intent = Intent()
                    intent.putExtra(KEY_CURRENT_DIGIT, txtViewResult?.text.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                }
                R.id.btnEqual -> workWithOperand(findViewById(v.id))
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