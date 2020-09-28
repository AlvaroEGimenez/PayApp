package com.example.geopagos.ui.resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geopagos.R
import com.example.geopagos.model.CardIssuers
import com.example.geopagos.model.Installments
import com.example.geopagos.model.PayDataKey
import com.example.geopagos.ui.resume.adapter.InstallmentAdapter
import com.example.geopagos.ui.resume.viewmodel.InstallmentsViewModel
import com.example.geopagos.utils.invisible
import com.example.geopagos.utils.toastShort
import kotlinx.android.synthetic.main.fragment_resume.*


/**
 * A simple [Fragment] subclass.
 * Use the [ResumeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResumeFragment : Fragment() {

    private lateinit var installmentsViewModel: InstallmentsViewModel
    private lateinit var cardIssuers: CardIssuers
    private lateinit var id: String
    private lateinit var amountRaw: String
    private lateinit var card: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_resume, container, false)

        arguments.let {
            cardIssuers = arguments?.getSerializable(PayDataKey.KEY_ISSUER)!! as CardIssuers
            id = arguments?.getString(PayDataKey.KEY_ID).toString()
            card = arguments?.getString(PayDataKey.KEY_CARD_NAME)!!
            amountRaw = arguments?.getString(PayDataKey.KEY_AMOUNT).toString()
        }

        installmentsViewModel = ViewModelProvider(this).get(InstallmentsViewModel::class.java)
        installmentsViewModel.getInstallments(cleanAmount(amountRaw), id, cardIssuers.id.toString())
        observeInstallments()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar()
        tv_resume_amount.text = amountRaw
        tv_resume_bank.text = cardIssuers.name
        tv_resume_card.text = card
        btn_confirm.setOnClickListener {
            findNavController().navigate(R.id.action_resume_fragment_to_checkout_fragment)
        }
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar_resume)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        setHasOptionsMenu(true)
    }

    private fun observeInstallments() {
        installmentsViewModel.data.observe(viewLifecycleOwner, Observer {
            if (it != null && it.size > 0)
                initRecyclerview(it)
            else
                context?.toastShort(getString(R.string.error_installmetns))
        })

        installmentsViewModel.loaging.observe(viewLifecycleOwner, Observer {
            if (!it)
                progressBar_resume.invisible()
        })

        installmentsViewModel.error.observe(viewLifecycleOwner, Observer {
            if (it)
                context?.toastShort(getString(R.string.error_message))
        })
    }

    private fun initRecyclerview(it: ArrayList<Installments>) {
        recyclerview_resume.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =
                InstallmentAdapter(it[0].payer_costs) {
                    btn_confirm.isEnabled = true
                }
        }
    }

    private fun cleanAmount(amount: String): String {
        var clean = amount.replace("$", "").trim()
        val cut = clean.substringAfter(",")
        clean = if (cut == "00")
            clean.substringBefore(",").replace(".", "")
        else
            clean.replace(".", "").replace(",", ".")

        return clean
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            activity?.onBackPressed()

        return true
    }
}