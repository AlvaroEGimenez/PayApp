package com.example.geopagos.ui.card.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.geopagos.R
import com.example.geopagos.model.PaymentsMethod

class PaymentMethodsAdapter (private val list : ArrayList<PaymentsMethod>, val listener : (PaymentsMethod) -> Unit): RecyclerView.Adapter<PaymentMethodsAdapter.PaymentMethodsViewHolder>() {


    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: PaymentMethodsViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            listener(list[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return PaymentMethodsViewHolder(
            view
        )
    }


    class PaymentMethodsViewHolder internal constructor (view: View) : RecyclerView.ViewHolder(view){
        private val ivCard : ImageView = itemView.findViewById(R.id.iv_secure_thumbnail)
        private val tvName : TextView = itemView.findViewById(R.id.tv_card_id)
        private val tvCardType : TextView = itemView.findViewById(R.id.tv_payment_type_id)
        private val tvStatus: TextView = itemView.findViewById(R.id.tv_status)

        fun bind(paymentMethod : PaymentsMethod){
            Glide.with(itemView).load(paymentMethod.secure_thumbnail)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(ivCard)
            tvName.text = paymentMethod.name
            tvCardType.text = paymentMethod.payment_type_id
            tvStatus.text = paymentMethod.status
        }
    }
}