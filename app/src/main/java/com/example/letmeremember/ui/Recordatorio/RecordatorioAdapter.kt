package com.example.letmeremember.ui.Recordatorio

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.letmeremember.R
import com.example.letmeremember.databinding.ItemlistRecordatorioBinding
import com.example.letmeremember.models.Reminder
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date

class RecordatorioAdapter(private val context: Context, private val listaRecordatorio: MutableList<Reminder>) : RecyclerView.Adapter<RecordatorioAdapter.ViewHolder>(), Filterable {
    private var filteredDataList = listaRecordatorio
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemlistRecordatorioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recordatorio = listaRecordatorio[position]
        holder.bind(recordatorio, position)
    }

    override fun getItemCount(): Int {
        return filteredDataList.size
    }

    fun obtenerFechaYHoraActual(): String {
        val formato = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val fechaHoraActual = Date()
        return formato.format(fechaHoraActual)
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemlistRecordatorioBinding.bind(itemView)

        fun bind(recordatorio: Reminder, position: Int) {

            binding.nombreRecordatorio.text = recordatorio.getTitle()
            binding.eliminarRecordatorio.setOnClickListener {
                val ref = RecordatorioAdapter.refReminders.child(recordatorio.getKey() ?: "")
                val fechaHoraActual = obtenerFechaYHoraActual()
                ref.child("isActive").setValue(false)
                ref.child("deletedAt").setValue(fechaHoraActual.toString())
            }
            binding.editraRecordatorio.setOnClickListener{
                val navController = (context as AppCompatActivity).findNavController(R.id.nav_host_fragment_content_activity_menu_grupos)
                val bundle = Bundle()
                bundle.putString("key", recordatorio.getKey())
                bundle.putString("titulo", recordatorio.getTitle())
                bundle.putString("contenido", recordatorio.getBody())
                bundle.putString("accion", "edit")
                navController.navigate(R.id.nav_AddRecordatorios, bundle)
            }
            itemView.setOnClickListener {
                val navController = (context as AppCompatActivity).findNavController(R.id.nav_host_fragment_content_activity_menu_grupos)
                val bundle = Bundle()
                bundle.putString("key", recordatorio.getKey())
                bundle.putString("titulo", recordatorio.getTitle())
                bundle.putString("contenido", recordatorio.getBody())
                bundle.putString("accion", "edit")
                navController.navigate(R.id.nav_AddRecordatorios, bundle)
            }
        }
    }

    fun filter(query: String?) {
        Log.d("filtro", query!!)
        filteredDataList = if (query.isNullOrEmpty()) {
            listaRecordatorio
        } else {
            listaRecordatorio.filter {
                it.getTitle()?.contains(query)?:false
            }.toMutableList()
        }

        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint.toString()
                filteredDataList = if (query.isEmpty()) {
                    listaRecordatorio
                } else {
                    listaRecordatorio.filter {
                        it.getTitle()?.contains(query)?:false
                    }.toMutableList()
                }
                val filterResults = FilterResults()
                filterResults.values = filteredDataList
                filterResults.count = filteredDataList.size

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredDataList = results?.values as MutableList<Reminder>
                notifyDataSetChanged()
            }
        }
    }

    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refReminders: DatabaseReference = database.getReference("reminders")
    }
}
