package com.example.simplecalculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    // here we make two variables to check if the last number enter in the interface of
    // the calculator was a number or a dot.
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }
    fun onDigit(view: View){
        // here we coded a message that is shown if any button in the calculator is pressed.
        // Toast.makeText(this, "Button has been clicked", Toast.LENGTH_LONG).show()
        tvInput?.append((view as Button).text)
        // here we are using view to only append
        // the button when they are clicked and post the text within the button

        lastNumeric = true
        lastDot = false
    }
    // Clear Button
    fun onClear(view: View){
        tvInput?.text = ""

    }
    fun onDecimalPoint(view: View){
        if (lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false // flag
            lastDot = true //flag
            // flag are used to check if something is active or inactive.
        }
    }
     fun onOperator(view:View){
        // nullable channing, let is used for nullables.
        tvInput?.text?.let{
            //
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }
    // equal method
    fun onEqual(view: View){ // using view:View helps to check which button was last pressed.
        //create a prefix
        var preFix = ""
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            try{
                if(tvValue.startsWith("-")){
                    preFix = "-"
                    tvValue = tvValue.substring(1)
                    // we are using the tvValue and by using the subString at level one were getting rid
                    // of the first entry within the string
                }
                // subtract
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-") // we are using to char - to split the text
                    // Ex. if we have "99-2" the text will be split from the minus sign and the first entry will be 99 and the second
                    // entry will be 2 in a form of a array.
                    var firstEntry = splitValue[0]
                    val secondEntry = splitValue[1]
                    if(preFix.isNotEmpty()){
                        firstEntry = preFix + firstEntry
                    }
                    // have to convert it to a string.
                    tvInput?.text = removeZeroAfterDot((firstEntry.toDouble() - secondEntry.toDouble()).toString())
                }else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+") // we are using to char - to split the text
                    // Ex. if we have "99-2" the text will be split from the minus sign and the first entry will be 99 and the second
                    // entry will be 2 in a form of a array.
                    var firstEntry = splitValue[0]
                    val secondEntry = splitValue[1]
                    if(preFix.isNotEmpty()){
                        firstEntry = preFix + firstEntry
                    }
                    // have to convert it to a string.
                    tvInput?.text = removeZeroAfterDot((firstEntry.toDouble() + secondEntry.toDouble()).toString())
                // Division
                } else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/") // we are using to char - to split the text
                    // Ex. if we have "99-2" the text will be split from the minus sign and the first entry will be 99 and the second
                    // entry will be 2 in a form of a array.
                    var firstEntry = splitValue[0]
                    val secondEntry = splitValue[1]
                    if(preFix.isNotEmpty()){
                        firstEntry = preFix + firstEntry
                    }
                    // have to convert it to a string.
                    tvInput?.text = removeZeroAfterDot((firstEntry.toDouble() / secondEntry.toDouble()).toString())
                // Multiplication
                }else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*") // we are using to char - to split the text
                    // Ex. if we have "99-2" the text will be split from the minus sign and the first entry will be 99 and the second
                    // entry will be 2 in a form of a array.
                    var firstEntry = splitValue[0]
                    val secondEntry = splitValue[1]
                    if(preFix.isNotEmpty()){
                        firstEntry = preFix + firstEntry
                    }
                    // have to convert it to a string.
                    tvInput?.text = removeZeroAfterDot((firstEntry.toDouble() * secondEntry.toDouble()).toString())
                }


            }catch(e: ArithmeticException) // ArithmeticException error is an error base math logic error like
            // for example 2/0 = undefined.
            {
                e.printStackTrace()
            }

        }
    }
    // eliminating the .0
    private fun removeZeroAfterDot(result: String): String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0, result.length - 2)
        }
        return  value
    }
    // the function takes in a string a one of its arguments and returns a boolean as the result.
    private  fun isOperatorAdded(value: String):Boolean {
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

}