package com.example.lesson09_k

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity

class InfoActivity : AppCompatActivity() {
    private val keyListValue = "list"
    private val currentDigitKey="currentDigit"
    private val emptyLine =""
    private val slashN: String ="\n"
    private val maxCountNumbers: Int = 5

    private var allDigits: TextView? = null
    private var currentDigit: TextView? = null

    private var btnSave: Button? = null
    private var btnCalculator: Button? = null

    private val listNumber: MutableList<Int> = mutableListOf()

    private var digit: String? = null
    private var refactorLine: String? = null

    private val txtNoDigit = R.string.dontInputNumber

    private fun findViewById() {
        allDigits = findViewById(R.id.allDigit)
        currentDigit = findViewById(R.id.currentDigit)
        btnSave = findViewById(R.id.btnSave)
        btnCalculator = findViewById(R.id.btnCalculator)
    }

    private fun validate(digit: String?) {
        if (listNumber.size == maxCountNumbers) {
            listNumber.remove(0)
        }
        if (digit != null) {
            listNumber.add(digit.toInt())
        }
        refactorLine = TextUtils.join(slashN, listNumber)
        updateValues(refactorLine)
    }

    private fun savePreviousList() {
        if (listNumber.size == 0) {
            val txtNumbers: String? = getValues()
            if (txtNumbers != "") {
                for (num in txtNumbers!!.split(slashN)) {
                    listNumber.add(num.toInt())
                }
            }
        }
    }

    private fun getValues(): String? {
        val sp = getSharedPreferences(emptyLine, Context.MODE_PRIVATE)
        return sp.getString(keyListValue, emptyLine)
    }

    private fun updateValues(refactorLine: String?) {
        val sp = getSharedPreferences(emptyLine, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(keyListValue, refactorLine)
        editor.apply()
    }

    private fun openCalculatorActivityForResult() {
        val intent = Intent(this, CalculatorActivity::class.java)
        calculatorActivityResultLauncher.launch(intent)
    }

    private var calculatorActivityResultLauncher = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            if (data != null) {
                digit = data.getStringExtra(currentDigitKey)
                currentDigit?.text = digit
                allDigits?.text = getValues()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        findViewById()

        savePreviousList()

        allDigits?.text = getValues()
        allDigits?.movementMethod = ScrollingMovementMethod()

        btnSave?.setOnClickListener {
            if (digit == null || digit == emptyLine) {
                val toast = Toast.makeText(
                    applicationContext,
                    txtNoDigit,
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                validate(digit)
            }
        }

        btnCalculator!!.setOnClickListener {
            openCalculatorActivityForResult()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        allDigits = null
        currentDigit = null
        btnSave = null
        btnCalculator = null
    }
}