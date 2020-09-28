package com.example.geopagos.ui.checkout

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.geopagos.R
import com.example.geopagos.utils.invisible
import com.example.geopagos.utils.visible
import kotlinx.android.synthetic.main.fragment_checkout.*
import kotlin.random.Random


/**
 * A simple [Fragment] subclass.
 * Use the [CheckoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val success = Random.nextBoolean()
        if (success) {
            checkout.setAnimation("payment-success.json")
            tv_success_payment.text = getString(R.string.pago_realizado)
        } else {
            checkout.setAnimation("payment-failed.json")
            tv_success_payment.text = getString(R.string.error_message)
        }

        val run = Runnable {
            progressBar_checkout.invisible()
            checkout.visible()
            tv_success_payment.visible()
            btn_finish.visible()
            checkout.playAnimation()
            btn_finish.visible()
        }
        Handler(Looper.getMainLooper()).postDelayed(run, 2500)

        btn_finish.setOnClickListener {
            findNavController().navigate(R.id.action_checkout_fragment_to_main)
        }
    }

}