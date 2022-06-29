package com.example.lesson09_k

import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity

class InfoActivity : AppCompatActivity() {
    companion object {
        const val KEY_LIST_VALUE = "list"
        const val KEY_CURRENT_DIGIT = "currentDigit"
        const val EMPTY_LINE = ""
        const val SLASH_N: String = "\n"
        const val MAX_COUNT_NUMBER: Int = 5
    }

    private var allDigits: TextView? = null
    private var currentDigit: TextView? = null

    private var btnSave: Button? = null
    private var btnCalculator: Button? = null

    private val listNumber: MutableList<Int> = mutableListOf()

    private var digit: String? = null
    private var refactorLine: String? = null

    private var calculatorActivityResultLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            checkingResultOfCode(result)
        }


    private fun findViewsById() {
        allDigits = findViewById(R.id.allDigit)
        currentDigit = findViewById(R.id.currentDigit)
        btnSave = findViewById(R.id.btnSave)
        btnCalculator = findViewById(R.id.btnCalculator)
    }

    private fun addingNumberToArray(digit: String?) {
        if (listNumber.size == MAX_COUNT_NUMBER) {
            listNumber.remove(0)
        }
        if (!digit.isNullOrEmpty()) {
            digit.toIntOrNull()?.let { listNumber.add(it) }
        }
        refactorLine = TextUtils.join(SLASH_N, listNumber)
        updateListNumbers(refactorLine)
    }

    private fun savePreviousList() {
        if (listNumber.isEmpty()) {
            val txtNumbers: String? = getListNumbers()
            if (!txtNumbers.isNullOrEmpty()) {
                for (num in txtNumbers.split(SLASH_N)) {
                    num.toIntOrNull()?.let { listNumber.add(it) }
                }
            }
        }
    }

    private fun getListNumbers(): String? {
        val sp = getSharedPreferences(EMPTY_LINE, Context.MODE_PRIVATE)
        println(sp.getString(KEY_LIST_VALUE, EMPTY_LINE))
        return sp.getString(KEY_LIST_VALUE, EMPTY_LINE)
    }

    private fun updateListNumbers(refactorLine: String?) {
        val sp = getSharedPreferences(EMPTY_LINE, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(KEY_LIST_VALUE, refactorLine)
        editor.apply()
    }

    private fun onClickBtnCalculator() {
        val intent = Intent(this, CalculatorActivity::class.java)
        calculatorActivityResultLauncher.launch(intent)
    }

    private fun checkingResultOfCode(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            if (data != null) {
                digit = data.getStringExtra(KEY_CURRENT_DIGIT)
                currentDigit?.text = digit
                allDigits?.text = getListNumbers()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        findViewsById()

        savePreviousList()

        allDigits?.text = getListNumbers()
        allDigits?.movementMethod = ScrollingMovementMethod()

        btnSave?.setOnClickListener {
            if (digit.isNullOrEmpty() || digit == EMPTY_LINE) {
                val toast = Toast.makeText(
                    applicationContext,
                    R.string.dontInputNumber,
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                addingNumberToArray(digit)
            }
        }

        btnCalculator!!.setOnClickListener {
            onClickBtnCalculator()
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