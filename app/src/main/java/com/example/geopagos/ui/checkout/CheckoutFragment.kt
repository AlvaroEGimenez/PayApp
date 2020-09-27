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
import kotlinx.android.synthetic.main.fragment_checkout.*


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
        val run = Runnable {
            progressBar_checkout.visibility = View.GONE
            checkout.visibility = View.VISIBLE
            checkout.setAnimation("payment-success.json")
            checkout.playAnimation()
            tv_success_payment.visibility = View.VISIBLE
            btn_finish.visibility = View.VISIBLE
        }
        Handler(Looper.getMainLooper()).postDelayed(run,2500)

        btn_finish.setOnClickListener {
            findNavController().navigate(R.id.action_checkout_fragment_to_main)
        }
    }

}