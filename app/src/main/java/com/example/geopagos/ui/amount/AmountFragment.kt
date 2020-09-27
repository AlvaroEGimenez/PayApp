package com.example.geopagos.ui.amount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.geopagos.R
import com.example.geopagos.model.PayDataKey
import com.example.geopagos.ui.views.AmountView
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 * Use the [AmountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AmountFragment : Fragment(), AmountView.InputCallback{

    private lateinit var amount: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        amountView.setInputCallback(this)
        amountView.setInputHandler()
        btn_next.setOnClickListener {
            val bundle = bundleOf(PayDataKey.KEY_AMOUNT to amount)
            findNavController().navigate(R.id.action_fragment_card, bundle)
        }
    }

    override fun onInputEmpty() {
       btn_next.isEnabled = false
    }

    override fun onInputValid(amount: String) {
        btn_next.isEnabled = true
        this.amount = amount
    }

    companion object {
        fun newInstance() = AmountFragment()
    }

}