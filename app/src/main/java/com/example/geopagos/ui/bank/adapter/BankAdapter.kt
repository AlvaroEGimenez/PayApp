package com.example.geopagos.ui.bank.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.geopagos.R
import com.example.geopagos.model.CardIssuers

class BankAdapter (private val list : ArrayList<CardIssuers>, val lister : (CardIssuers) -> Unit):RecyclerView.Adapter<BankAdapter.BankViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.bank_item, parent, false)
        return BankViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            lister(list[position])
        }
    }

    class BankViewHolder(view:View) : RecyclerView.ViewHolder(view){
        private val ivBank : ImageView = itemView.findViewById(R.id.iv_bank)

        fun bind(bank: CardIssuers){
            Glide.with(itemView).load(bank.secure_thumbnail)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(ivBank)
        }
    }


}