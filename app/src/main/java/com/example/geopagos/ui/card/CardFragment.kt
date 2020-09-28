package com.example.geopagos.ui.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geopagos.R
import com.example.geopagos.model.PayDataKey
import com.example.geopagos.model.PaymentsMethod
import com.example.geopagos.ui.bank.viewmodel.PaymentMethodViewModel
import com.example.geopagos.ui.card.adapter.PaymentMethodsAdapter
import com.example.geopagos.utils.toastShort
import com.example.geopagos.utils.visible
import kotlinx.android.synthetic.main.fragment_card.*


class CardFragment : Fragment() {

    private lateinit var paymentMethodViewModel: PaymentMethodViewModel
    private lateinit var amount: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card, container, false)

        paymentMethodViewModel = ViewModelProvider(this).get(PaymentMethodViewModel::class.java)

        arguments.let {
            amount = arguments?.getString(PayDataKey.KEY_AMOUNT).toString()
        }

        paymentMethodViewModel.getPaymentMethod()
        observePaymentMethod()


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar_card)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title =
            "${getString(R.string.monto)} $amount"
        setHasOptionsMenu(true)
    }

    private fun observePaymentMethod() {
        paymentMethodViewModel.paymentsMethod.observe(viewLifecycleOwner, Observer { arrayList ->
            if (arrayList != null)
                initRecyclerview(arrayList)
        })

        paymentMethodViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (!it)
                progressBar_card.visibility = View.GONE
        })

        paymentMethodViewModel.error.observe(viewLifecycleOwner, Observer {
            if (!it)
                context?.toastShort(getString(R.string.error_message))
        })
    }

    private fun initRecyclerview(arrayList: ArrayList<PaymentsMethod>) {
        recyclerview_card.visible()
        recyclerview_card.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =
                PaymentMethodsAdapter(
                    arrayList
                ) {
                    navigateToBankFragment(it)
                }
        }
    }

    private fun navigateToBankFragment(it: PaymentsMethod) {
        val bundle = bundleOf(
            PayDataKey.KEY_ID to it.id,
            PayDataKey.KEY_AMOUNT to amount,
            PayDataKey.KEY_CARD_NAME to it.name
        )
        findNavController().navigate(
            R.id.action_card_fragment_to_bank_fragment2,
            bundle
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            activity?.onBackPressed()

        return true
    }


}