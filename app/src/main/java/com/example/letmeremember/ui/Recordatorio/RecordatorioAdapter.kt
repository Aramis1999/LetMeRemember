package com.example.letmeremember.ui.Recordatorio

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.letmeremember.databinding.ItemlistRecordatorioBinding

class RecordatorioAdapter(private val mContext: Context, private val listaRecordatorio:List<RecordatorioObject>) : RecyclerView.Adapter<RecordatorioAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemlistRecordatorioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recordatorio = listaRecordatorio[position]
        holder.bind(recordatorio, position)
    }

    override fun getItemCount(): Int {
        return listaRecordatorio.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemlistRecordatorioBinding.bind(itemView)

        fun bind(recordatorio: RecordatorioObject, position: Int) {
            binding.nombreRecordatorio.text = recordatorio.nombre
            binding.Retencion.text = "${recordatorio.retencion}%"

            // Calculate color based on position
            val color = Color.HSVToColor(floatArrayOf((position * 120f / itemCount) % 360f, 1f, 1f))

            // Set background color
            itemView.setBackgroundColor(color)
        }
    }
}
