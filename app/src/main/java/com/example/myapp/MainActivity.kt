package com.example.myapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var spinner1 : Spinner
    lateinit var spinner2 : Spinner
    lateinit var edit1 : EditText
    lateinit var edit2 : EditText
    lateinit var textView : TextView

    val currName = mutableListOf(
        "Viet Nam - Dong",
        "United States - Dollar",
        "China - Yuan",
        "Japan - Yen",
        "Europe - Euro")

    val currVal = mutableListOf(
        1.0,
        25620.0,
        3532.2827,
        170.0743,
        27673.3636
    )

    val currShortName = mutableListOf(
        "VND", "USD", "CNY", "JPY", "EUR"
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



        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)
        edit1 = findViewById(R.id.editTextNumberDecimal)
        edit2 = findViewById(R.id.editTextNumberDecimal2)
        textView = findViewById(R.id.textView)

        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            currName
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinner1.adapter = adapter
        spinner2.adapter = adapter
        spinner2.setSelection(1)

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                calc1()
                changeText()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                calc2()
                changeText()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }


        var edit1Focused = false;

        edit1.onFocusChangeListener = object: View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                edit1Focused = hasFocus;
            }

        }

        edit1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edit1Focused)
                    calc2()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        edit2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!edit1Focused)
                    calc1()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    fun calc1(){
        val text2 = edit2.text.toString()
        val money2 = if (text2 == "") 0.0 else text2.toDouble()

        val curr2 = currVal[spinner2.selectedItemId.toInt()]
        val curr1 = currVal[spinner1.selectedItemId.toInt()]

        val money1 = money2*curr2/curr1
        edit1.setText("%.2f".format(Locale.ROOT, money1))
    }

    fun calc2(){
        val text1 = edit1.text.toString()
        val money1 = if (text1 == "") 0.0 else text1.toDouble()

        val curr1 = currVal[spinner1.selectedItemId.toInt()]
        val curr2 = currVal[spinner2.selectedItemId.toInt()]

        val money2 = money1*curr1/curr2
        edit2.setText("%.2f".format(Locale.ROOT, money2))
    }

    fun changeText(){
        val id1 = spinner1.selectedItemId.toInt()
        val id2 = spinner2.selectedItemId.toInt()
        textView.text = "1 " + currShortName[id1] + " = " + "%.5f".format(Locale.ROOT,(currVal[id1]/currVal[id2])) + " " + currShortName[id2]
    }
}