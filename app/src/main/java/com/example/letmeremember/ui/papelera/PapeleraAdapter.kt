package com.example.letmeremember.ui.papelera

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.letmeremember.databinding.ItemlistPapeleraBinding

class PapeleraAdapter(private val mContext: Context, private val listaPapelera:List<PapeleraObject>) : RecyclerView.Adapter<PapeleraAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemlistPapeleraBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Papelera = listaPapelera[position]
        holder.bind(Papelera, position)
    }

    override fun getItemCount(): Int {
        return listaPapelera.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemlistPapeleraBinding.bind(itemView)

        fun bind(Papelera: PapeleraObject, position: Int) {
            binding.PapeleraFechadia.text = Papelera.fecha
            binding.papeleraDescripcion.text = Papelera.descripcion
        }
    }
}
