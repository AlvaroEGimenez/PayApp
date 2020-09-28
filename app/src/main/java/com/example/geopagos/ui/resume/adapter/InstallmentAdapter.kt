package com.example.geopagos.ui.resume.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.geopagos.R
import com.example.geopagos.model.PayerCosts
import java.text.NumberFormat
import java.util.*

class InstallmentAdapter(
    private val list: ArrayList<PayerCosts>,
    private val listener: (PayerCosts) -> Unit
) :
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


        fun bind(payerCosts: PayerCosts) {
            tvInstallments.text =
                "${payerCosts.installments} ".plus(itemView.context.getString(R.string.total_installments))
            tvInstallmentsRate.text = itemView.context.getString(R.string.interes).plus("${payerCosts.installment_rate}%")
            tvLabel.text = payerCosts.labels[0]
            tvInstallmentAmount.text = numberFormat(payerCosts.installment_amount)
            tvRecommendedMessage.text = payerCosts.recommended_message
        }

         private fun numberFormat(amount : String) : String{
            val parsed = amount.toDouble()
            val numberFormat = NumberFormat.getCurrencyInstance()
            numberFormat.currency = Currency.getInstance(Locale.getDefault())
            return numberFormat.format((parsed))
        }

    }
}

