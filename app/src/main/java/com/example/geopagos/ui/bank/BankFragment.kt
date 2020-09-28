package com.example.geopagos.ui.bank

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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.geopagos.R
import com.example.geopagos.model.CardIssuers
import com.example.geopagos.model.PayDataKey
import com.example.geopagos.ui.bank.adapter.BankAdapter
import com.example.geopagos.ui.card.viewmodel.CardsViewModel
import com.example.geopagos.utils.invisible
import com.example.geopagos.utils.toastShort
import com.example.geopagos.utils.visible
import kotlinx.android.synthetic.main.fragment_bank.*
import java.io.Serializable


/**
 * A simple [Fragment] subclass.
 * Use the [BankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class BankFragment : Fragment() {

    private lateinit var cardsViewModel: CardsViewModel
    private lateinit var paymentId: String
    private lateinit var amount: String
    private lateinit var card: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bank, container, false)

        cardsViewModel = ViewModelProvider(this).get(CardsViewModel::class.java)

        arguments.let {
            amount = arguments?.getString(PayDataKey.KEY_AMOUNT).toString()
            paymentId = arguments?.getString(PayDataKey.KEY_ID).toString()
            card = arguments?.getString(PayDataKey.KEY_CARD_NAME).toString()
            cardsViewModel.getCardIsuuers(paymentId)
        }

        observeCardViewModel()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar()
        tv_cardname_bank.text = card
        tv_amount_bank.text = amount
    }

    private fun observeCardViewModel() {
        cardsViewModel.cards.observe(viewLifecycleOwner, Observer { list ->
            if (list != null && list.size > 0)
                initRecyclerview(list)
            else
                context?.toastShort("${getString(R.string.bank_not_found)} $card")
        })
        cardsViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (!it) {
                progressBar_bank.invisible()
            }
        })

        cardsViewModel.error.observe(viewLifecycleOwner, Observer {
            if (it)
                context?.toastShort(getString(R.string.error_message))
        })
    }

    private fun initRecyclerview(list: ArrayList<CardIssuers>) {
        recyclerView_bank.visible()
        recyclerView_bank.apply {
            layoutManager = GridLayoutManager(
                context, 3,
                GridLayoutManager.VERTICAL, false
            )
            adapter =
                BankAdapter(list) {
                    navigateToResume(it)
                }
        }
    }

    private fun navigateToResume(it: CardIssuers) {
        val bundle = bundleOf(
            PayDataKey.KEY_ISSUER to it as Serializable,
            PayDataKey.KEY_ID to paymentId,
            PayDataKey.KEY_AMOUNT to amount,
            PayDataKey.KEY_CARD_NAME to card
        )
        findNavController().navigate(R.id.resume_fragment, bundle)
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar_bank)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.select_bank)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
        }
        return true
    }

}

