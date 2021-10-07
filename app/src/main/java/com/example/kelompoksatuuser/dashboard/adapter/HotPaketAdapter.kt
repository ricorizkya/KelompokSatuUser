package com.example.kelompoksatuuser.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kelompoksatuuser.model.Paket
import com.example.kelompoksatuuser.databinding.ListHotPaketBinding

class HotPaketAdapter(private val listPaket: ArrayList<Paket>): RecyclerView.Adapter<HotPaketAdapter.HotPaketViewHolder>() {

    inner class HotPaketViewHolder(private val binding: ListHotPaketBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(paket: Paket) {
            with(binding) {
                tvNamaPaket.text = paket.namaPaket
                tvDomisiliPaket.text = paket.domisili
                Glide.with(itemView.context)
                    .load(paket.imagePoster)
                    .into(imgPoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotPaketViewHolder {
        val listHotPaketBinding = ListHotPaketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HotPaketViewHolder(listHotPaketBinding)
    }

    override fun onBindViewHolder(holder: HotPaketViewHolder, position: Int) {
        val hotPaket = listPaket[position]
        holder.bind(hotPaket)
    }

    override fun getItemCount(): Int {
        return 5
    }
}