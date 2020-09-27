package com.example.geopagos.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geopagos.R
import com.example.geopagos.adapter.InstallmentAdapter
import com.example.geopagos.model.CardIssuers
import com.example.geopagos.utils.BundleKeys
import com.example.geopagos.viewmodel.InstallmentsViewModel
import kotlinx.android.synthetic.main.fragment_resume.*


/**
 * A simple [Fragment] subclass.
 * Use the [ResumeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResumeFragment : Fragment() {

    private lateinit var installmentsViewModel: InstallmentsViewModel
    private lateinit var cardIssuers: CardIssuers
    private lateinit var recyclerView: RecyclerView
    private lateinit var confirButton: Button
    private lateinit var tvResumeAmount : TextView
    private lateinit var tvResumeCard : TextView
    private lateinit var tvResumeBank : TextView
    private lateinit var id: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_resume, container, false)
        recyclerView = view.findViewById(R.id.recyclerview_resume)
        confirButton = view.findViewById(R.id.btn_confirm)
        tvResumeAmount = view.findViewById(R.id.tv_resume_amount)
        tvResumeCard = view.findViewById(R.id.tv_resume_card)
        tvResumeBank = view.findViewById(R.id.tv_resume_bank)

        var amount = ""
        if (arguments != null) {
            cardIssuers = arguments?.getParcelable(BundleKeys.KEY_ISSUER)!!
            id = arguments?.getString(BundleKeys.KEY_ID).toString()
            val amountRaw = arguments?.getString(BundleKeys.KEY_AMOUNT).toString()
            val card = arguments?.getString(BundleKeys.KEY_CARD_NAME)
            tvResumeAmount.text = amountRaw
            tvResumeBank.text = cardIssuers.name
            tvResumeCard.text = card
            amount = amountRaw.replace(",","").replace("$","")
        }

        installmentsViewModel = ViewModelProvider(this).get(InstallmentsViewModel::class.java)
        installmentsViewModel.getInstallments(amount, id, cardIssuers.id.toString())
        observeInstallments()
        return view
    }

    private fun observeInstallments() {
        installmentsViewModel.data.observe(viewLifecycleOwner, Observer {
            if (it != null && it.size > 0) {
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = InstallmentAdapter(it[0].payer_costs){
                    confirButton.isEnabled = true
                }

            }else{
                Toast.makeText(context, "Error al calcular las cuotas ", Toast.LENGTH_SHORT).show()
            }
        })
    }


}