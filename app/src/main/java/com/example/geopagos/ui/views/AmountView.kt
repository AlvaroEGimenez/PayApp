package com.example.geopagos.ui.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.geopagos.R
import kotlinx.android.synthetic.main.amount_input.view.*
import java.text.NumberFormat
import java.util.*

class AmountView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    lateinit var callback: InputCallback

    init {
        View.inflate(context, R.layout.amount_input, this)
    }

    fun setInputCallback(inputCallback: InputCallback) {
        callback = inputCallback
    }

    fun setInputHandler() {
        textinput_amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == "$0.0") {
                    callback.onInputEmpty()
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val current = ""
                if (s.toString() != current) {

                    textinput_amount.removeTextChangedListener(this)
                    val cleanString = s.toString().replace("[$,.]".toRegex(), "").trim()
                    val parsed = cleanString.toDouble()
                    val numberFormat = NumberFormat.getCurrencyInstance()
                    numberFormat.currency = Currency.getInstance(Locale.getDefault())
                    val formatted = numberFormat.format((parsed / 100))

                    textinput_amount.setText(formatted)
                    textinput_amount.setSelection(formatted.length)
                    textinput_amount.addTextChangedListener(this)
                    callback.onInputValid(formatted)
                }

            }

        })
    }

    interface InputCallback {
        fun onInputEmpty()
        fun onInputValid(amount: String)
    }


}