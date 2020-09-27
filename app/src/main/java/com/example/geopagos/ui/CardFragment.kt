package com.example.geopagos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geopagos.R
import com.example.geopagos.adapter.PaymentMethodsAdapter
import com.example.geopagos.utils.BundleKeys
import com.example.geopagos.viewmodel.PaymentMethodViewModel
import kotlinx.android.synthetic.main.fragment_card.*


class CardFragment : Fragment() {

    lateinit var paymentMethodViewModel: PaymentMethodViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var amount: String
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card, container, false)

        toolbar = view.findViewById(R.id.toolbar_card)
        recyclerView = view.findViewById(R.id.recyclerview_payment_methods)


        if (arguments != null)
            amount = arguments?.getString(BundleKeys.KEY_AMOUNT).toString()

        setupToolbar()

        paymentMethodViewModel = ViewModelProvider(this).get(PaymentMethodViewModel::class.java)
        paymentMethodViewModel.getPaymentMethod()
        observePaymentMethod()



        return view
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Monto $amount"
        setHasOptionsMenu(true)
    }

    private fun observePaymentMethod() {
        paymentMethodViewModel.paymentsMethod.observe(viewLifecycleOwner, Observer { arrayList ->
            if (arrayList != null) {
                recyclerView.visibility = View.VISIBLE
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = PaymentMethodsAdapter(arrayList) {
                    val bundle = bundleOf(BundleKeys.KEY_ID to it.id,
                        BundleKeys.KEY_AMOUNT to amount,
                        BundleKeys.KEY_CARD_NAME to it.name
                    )
                    findNavController().navigate(
                        R.id.action_card_fragment_to_bank_fragment2,
                        bundle
                    )
                }
            }
        })

        paymentMethodViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (!it)
                progressBar.visibility = View.GONE
        })

        paymentMethodViewModel.error.observe(viewLifecycleOwner, Observer {
            if (!it)
                Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_SHORT).show()

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            activity?.onBackPressed()
        }
        return true
    }

}