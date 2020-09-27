package com.example.geopagos.ui.resume.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.geopagos.R
import com.example.geopagos.model.PayerCosts

class InstallmentAdapter(private val list: ArrayList<PayerCosts>, private val listener : (PayerCosts) -> Unit) :
    RecyclerView.Adapter<InstallmentAdapter.InstallmentsViewHolder>() {

    private var lastChecked: RadioButton? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstallmentsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.installments_item, parent, false)
        return InstallmentsViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: InstallmentsViewHolder, position: Int) {
        holder.bind(list[position])

        holder.radioButton.setOnCheckedChangeListener { compoundButton, b ->
            val checkedRb = compoundButton as RadioButton
            lastChecked?.isChecked = false
            lastChecked = checkedRb
            listener(list[position])
        }

    }


    class InstallmentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val radioButton: RadioButton = itemView.findViewById(R.id.radioButton)
        private val tvInstallments: TextView = itemView.findViewById(R.id.tv_installments)
        private val tvInstallmentsRate: TextView = itemView.findViewById(R.id.tv_installment_rate)
        private val tvLabel: TextView = itemView.findViewById(R.id.tv_labels)
        private val tvInstallmentAmount: TextView =
            itemView.findViewById(R.id.tv_installment_amount)
        private val tvRecommendedMessage: TextView =
            itemView.findViewById(R.id.tv_recommended_message)

        @SuppressLint("SetTextI18n")
        fun bind(payerCosts: PayerCosts) {
            tvInstallments.text = "${payerCosts.installments} cuotas de"
            tvInstallmentsRate.text = "interes ${payerCosts.installment_rate} %"
            tvLabel.text = payerCosts.labels[0]
            tvInstallmentAmount.text = "$${payerCosts.installment_amount}"
            tvRecommendedMessage.text = payerCosts.recommended_message
        }


    }
}

