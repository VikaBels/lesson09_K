package com.example.lesson09_k

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity

class InfoActivity : AppCompatActivity() {
    private val keyListValue = "list"

    private var allDigits: TextView? = null
    private var currentDigit: TextView? = null

    private var btnSave: Button? = null
    private var btnCalculator: Button? = null

    private val listNumber: MutableList<Int> = mutableListOf()

    private var digit: String? = null
    private var refactorLine: String? = null
    private val txtNoDigit = "Вы не ввели число"

    private fun findViewById() {
        allDigits = findViewById(R.id.allDigit)
        currentDigit = findViewById(R.id.currentDigit)
        btnSave = findViewById(R.id.btnSave)
        btnCalculator = findViewById(R.id.btnCalculator)
    }

    private fun validate(digit: String) {
        if (listNumber.size == 5) {
            listNumber.remove(0)

        }
        listNumber.add(digit.toInt())
        refactorLine = TextUtils.join("\n", listNumber)
        updateValues(refactorLine!!)
    }

    private fun savePreviousList() {
        if (listNumber.size == 0) {
            val txtNumbers: String? = getValues()
            if (txtNumbers != null) {
                for (num in txtNumbers.split("\n")) {
                    listNumber.add(num.toInt())
                }
            }
        }
    }

    private fun getValues(): String? {
        val sp = PreferenceManager
            .getDefaultSharedPreferences(this)
        return sp.getString(keyListValue, "")
    }

    private fun updateValues(refactorLine: String) {
        val sp = PreferenceManager
            .getDefaultSharedPreferences(this)
        val editor = sp.edit()
        editor.putString(keyListValue, refactorLine)
        editor.apply()
    }

    private fun openSomeActivityForResult() {
        val intent = Intent(this, CalculatorActivity::class.java)
        someActivityResultLauncher.launch(intent)
    }

    private var someActivityResultLauncher = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data!!
            digit = data.getStringExtra("currentDigit")
            currentDigit!!.text = digit
            allDigits!!.text = getValues()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        findViewById()

        savePreviousList()

        allDigits!!.text=getValues()
        allDigits!!.movementMethod =ScrollingMovementMethod()

        btnSave!!.setOnClickListener {
            if (digit == null || digit == "") {
                val toast = Toast.makeText(
                    applicationContext,
                    txtNoDigit,
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                validate(digit!!)
            }
        }

        btnCalculator!!.setOnClickListener {
            openSomeActivityForResult()
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