package com.example.letmeremember.ui.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.letmeremember.R
import com.example.letmeremember.databinding.ItemlistGrupoBinding

class GrupoAdapter(private val mContext: Context, private val listaGrupos: List<GrupoObject>) : RecyclerView.Adapter<GrupoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemlistGrupoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grupo = listaGrupos[position]
        holder.bind(grupo, position)
    }

    override fun getItemCount(): Int {
        return listaGrupos.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemlistGrupoBinding.bind(itemView)

        fun bind(grupo: GrupoObject, position: Int) {
            binding.nombreGrupo.text = grupo.nombre
            binding.Retencion.text = "${grupo.retencion}%"

            // Calculate color based on position
            val color = Color.HSVToColor(floatArrayOf((position * 120f / itemCount) % 360f, 1f, 1f))

            // Set background color
            itemView.setBackgroundColor(color)

            // Set click listener on the item view
            itemView.setOnClickListener {
                val navController = (mContext as AppCompatActivity).findNavController(R.id.nav_host_fragment_content_activity_menu_grupos)
                val bundle = bundleOf("listaGrupos" to listaGrupos.toTypedArray())
                navController.navigate(R.id.nav_recordatorios, bundle)
            }

        }
    }
}
