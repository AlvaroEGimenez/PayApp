package com.example.geopagos.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geopagos.R
import com.example.geopagos.adapter.BankAdapter
import com.example.geopagos.utils.BundleKeys
import com.example.geopagos.viewmodel.CardsViewModel
import kotlinx.android.synthetic.main.fragment_bank.*


/**
 * A simple [Fragment] subclass.
 * Use the [BankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val TAG = "BankFragment"

class BankFragment : Fragment() {

    private lateinit var cardsViewModel: CardsViewModel
    private lateinit var id: String
    private lateinit var amount: String
    private lateinit var card: String
    private lateinit var toolbar: Toolbar
    private lateinit var tvAmount: TextView
    private lateinit var tvCardName: TextView
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bank, container, false)

        toolbar = view.findViewById(R.id.toolbar_bank)
        recyclerView = view.findViewById(R.id.recyclerView_bank)
        tvAmount = view.findViewById(R.id.tv_amount_bank)
        tvCardName = view.findViewById(R.id.tv_cardname_bank)

        if (arguments != null) {
            amount = arguments?.getString(BundleKeys.KEY_AMOUNT).toString()
            id = arguments?.getString(BundleKeys.KEY_ID).toString()
            card = arguments?.getString(BundleKeys.KEY_CARD_NAME).toString()
            tvAmount.text = amount
            tvCardName.text = card
        }

        setupToolbar()

        cardsViewModel = ViewModelProvider(this).get(CardsViewModel::class.java)
        cardsViewModel.getCardIsuuers(id)
        observeCardViewModel()

        return view
    }



    private fun observeCardViewModel() {
        cardsViewModel.cards.observe(viewLifecycleOwner, Observer { list ->
            if (list != null && list.size > 0) {
                recyclerView.visibility = View.VISIBLE
                recyclerView.layoutManager =
                    GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
                recyclerView.adapter = BankAdapter(list){
                    val bundle = bundleOf(BundleKeys.KEY_ISSUER to it,
                    BundleKeys.KEY_ID to id,
                    BundleKeys.KEY_AMOUNT to amount,
                    BundleKeys.KEY_CARD_NAME to card)
                    findNavController().navigate(R.id.resume_fragment, bundle)
                }
            }else{
                Toast.makeText(context, "No se encontro banco para la tarjeta $card", Toast.LENGTH_SHORT).show()
            }

        })
        cardsViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (!it) {
                progressBar_bank.visibility = View.GONE
            }
        })

        cardsViewModel.error.observe(viewLifecycleOwner, Observer {
            if (it) {
                Log.e(TAG, "Viewmodel error")
                Toast.makeText(context, "ocurrio un error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.select_bank)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            activity?.onBackPressed()
        }
        return true
    }
}

