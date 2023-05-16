package com.example.letmeremember.ui.home

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.NavigatorState
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.letmeremember.GroupActivity
import com.example.letmeremember.R
import com.example.letmeremember.alarms.AlarmClass
import com.example.letmeremember.databinding.ItemlistGrupoBinding
import com.example.letmeremember.databinding.ItemlistPapeleraBinding
import com.example.letmeremember.helper.Helper
import com.example.letmeremember.models.Group
import com.example.letmeremember.models.Trash
import com.example.letmeremember.ui.papelera.PapeleraAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.Objects

class GrupoAdapter(private val context: Context, private val listaGrupos: MutableList<Group>) : RecyclerView.Adapter<GrupoAdapter.ViewHolder>(), Filterable {
    private var filteredDataList = listaGrupos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemlistGrupoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grupo = filteredDataList[position]
        holder.bind(grupo, position)
    }

    override fun getItemCount(): Int {
        return listaGrupos.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemlistGrupoBinding.bind(itemView)

        fun bind(grupo: Group, position: Int) {
            binding.nombreGrupo.text = grupo.getNombre()
            binding.Retencion.text = "${grupo.getPercent()}%"
            binding.eliminarBtn.setOnClickListener {

                // Preparando cuadro de dialogo para preguntar al usuario
                // Si esta seguro de eliminar o no el registro
                val ad = AlertDialog.Builder(context)
                ad.setMessage("Está seguro de eliminar registro?")
                    .setTitle("Confirmación")
                ad.setPositiveButton("Si"
                ) { dialog, id ->
                    listaGrupos!![position]?.let {
                        if (it != null){
                            GroupActivity.refAlarms.orderByChild("groupId").equalTo(it.getId()).addListenerForSingleValueEvent(object :
                                ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    for (snapshot in dataSnapshot.children) {

                                        if (snapshot.getValue(AlarmClass::class.java) != null) {
                                            var alarm = snapshot.getValue(AlarmClass::class.java)
                                            var helper = Helper()
                                            if (alarm != null) {
                                                alarm.id1?.let { it1 ->
                                                    helper.cancelAlarm(context,
                                                        it1.toInt())
                                                }
                                                alarm.id2?.let { it1 ->
                                                    helper.cancelAlarm(context,
                                                        it1.toInt())
                                                }
                                                alarm.id3?.let { it1 ->
                                                    helper.cancelAlarm(context,
                                                        it1.toInt())
                                                }

                                                alarm.groupId?.let { it1 ->

                                                    val ref = refGrupos.child(grupo.getId() ?: "")
                                                    ref.removeValue()
                                                }
                                            }
                                        }
                                    }
                                }
                                override fun onCancelled(databaseError: DatabaseError) {
                                    // Getting Post failed, log a message
                                    Toast.makeText(context,"Error al obtener el ultimo id de alarma", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    }
                    Toast.makeText(
                        context,
                        "Registro borrado!", Toast.LENGTH_SHORT
                    ).show()
                }
                ad.setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {
                        Toast.makeText(
                            context,
                            "Operación de borrado cancelada!", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                ad.show()

                //val ref = refGrupos.child(grupo.getId() ?: "")
                //ref.removeValue()
            }

            binding.modificarBtn.setOnClickListener {
                val navController = (context as AppCompatActivity).findNavController(R.id.nav_host_fragment_content_activity_menu_grupos)
                val bundle = Bundle()
                bundle.putString("key",grupo.getId())
                bundle.putString("nombre",grupo.getNombre())
                navController.navigate(R.id.nav_createHome, bundle)
            }

            if(grupo.getPercent() == "0")  itemView.setBackgroundResource(R.drawable.round_shape_red)
            if(grupo.getPercent() == "60")  itemView.setBackgroundResource(R.drawable.round_shape_yellow)
            if(grupo.getPercent() == "100")  itemView.setBackgroundResource(R.drawable.round_shape)

            // Set click listener on the item view
            itemView.setOnClickListener {
                val navController = (context as AppCompatActivity).findNavController(R.id.nav_host_fragment_content_activity_menu_grupos)
                //val bundle = bundleOf("listaGrupos" to listaGrupos.toTypedArray())
                val bundle = Bundle()
                bundle.putString("idGrupo",grupo.getId())
                navController.navigate(R.id.nav_recordatorios, bundle)
            }

        }
    }

    fun filter(query: String?) {
        filteredDataList = if (query.isNullOrEmpty()) {
            listaGrupos
        } else {
            listaGrupos.filter {
                it.getNombre()?.contains(query)?:false
            }.toMutableList()
        }
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint.toString()
                filteredDataList = if (query.isEmpty()) {
                    listaGrupos
                } else {
                    listaGrupos.filter {
                        it.getNombre()?.contains(query)?:false
                    }.toMutableList()
                }
                val filterResults = FilterResults()
                filterResults.values = filteredDataList
                filterResults.count = filteredDataList.size
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredDataList = results?.values as MutableList<Group>
                notifyDataSetChanged()
            }
        }
    }
    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refGrupos: DatabaseReference = database.getReference("groups")
    }

}
