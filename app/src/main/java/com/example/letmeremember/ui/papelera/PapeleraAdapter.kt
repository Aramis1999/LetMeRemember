package com.example.letmeremember.ui.papelera

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.letmeremember.R
import com.example.letmeremember.databinding.ItemlistPapeleraBinding
import com.example.letmeremember.models.Trash
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PapeleraAdapter(private val context: Context, private val listaPapelera: MutableList<Trash>) : RecyclerView.Adapter<PapeleraAdapter.ViewHolder>(),Filterable {
    private var filteredDataList = listaPapelera
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemlistPapeleraBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Papelera = filteredDataList[position]
        holder.bind(Papelera, position)
    }

    override fun getItemCount(): Int {
        return filteredDataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemlistPapeleraBinding.bind(itemView)
        fun bind(Papelera: Trash, position: Int) {
            binding.PapeleraFechadia.text = Papelera.getDeletedAt()
            binding.papeleraDescripcion.text = context.getString(R.string.recycleCardName) + " " +Papelera.getTitle()
            binding.recuperarBtn.setOnClickListener {
                val ref = refReminders.child(Papelera.getId() ?: "")
                ref.child("isActive").setValue(true)
                ref.child("deletedAt").setValue("")
            }
            binding.eliminarBtn.setOnClickListener {
                val ref = refReminders.child(Papelera.getId() ?: "")
                ref.removeValue()
            }
        }
    }

    fun filter(query: String?) {
        filteredDataList = if (query.isNullOrEmpty()) {
            listaPapelera
        } else {
            listaPapelera.filter {
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
                    listaPapelera
                } else {
                    listaPapelera.filter {
                        it.getTitle()?.contains(query)?:false
                    }.toMutableList()
                }
                val filterResults = FilterResults()
                filterResults.values = filteredDataList
                filterResults.count = filteredDataList.size
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredDataList = results?.values as MutableList<Trash>
                notifyDataSetChanged()
            }
        }
    }

    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refReminders: DatabaseReference = database.getReference("reminders")
    }

}
