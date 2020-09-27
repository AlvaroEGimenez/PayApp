package com.example.geopagos.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.geopagos.R
import com.example.geopagos.utils.BundleKeys
import com.example.geopagos.viewmodel.PaymentMethodViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_main.*
import java.text.NumberFormat



/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    private lateinit var etAmount : TextInputEditText
    private lateinit var btnNext : MaterialButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etAmount = view.findViewById(R.id.textinput_amount)
        btnNext = view.findViewById(R.id.btn_next)

        etAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == "$0.0")
                    btnNext.isEnabled = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val current = ""
                if (s.toString() != current) {
                    btnNext.isEnabled = true
                    etAmount.removeTextChangedListener(this)
                    val cleanString = s.toString().replace("[$,.]".toRegex(), "").trim()
                    val parsed = cleanString.toDouble()
                    val formatted = NumberFormat.getCurrencyInstance().format((parsed / 100))

                    etAmount.setText(formatted)
                    etAmount.setSelection(formatted.length)
                    etAmount.addTextChangedListener(this)
                }

            }

        })

        btnNext.setOnClickListener {
            val amount = etAmount.text.toString()
            val bundle = bundleOf(BundleKeys.KEY_AMOUNT to amount)
            findNavController().navigate(R.id.action_fragment_card, bundle)
        }

        return view
    }



}