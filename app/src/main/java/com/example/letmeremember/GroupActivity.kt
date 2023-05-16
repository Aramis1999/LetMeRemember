package com.example.letmeremember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.DownloadManager.Query
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.letmeremember.AdaptadorGroup
import com.example.letmeremember.alarms.AlarmClass
import com.example.letmeremember.helper.Helper
import com.example.letmeremember.models.Group
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.*

class GroupActivity : AppCompatActivity() {
    var consultaOrdenada: com.google.firebase.database.Query =  refNotas.orderByChild("nombre")
    var personas: MutableList<Group>? = null
    var listaPersonas: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        inicializar()
    }

    private fun inicializar() {
        val fab_agregar: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab_agregar)
        listaPersonas = findViewById<ListView>(R.id.ListaPersonas)

        // Cuando el usuario haga clic en la lista (para editar registro)
        listaPersonas!!.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                val intent = Intent(getBaseContext(), AddGroupActivity::class.java)
                intent.putExtra("accion", "e") // Editar
                intent.putExtra("key", personas!![i].getId().toString())
                intent.putExtra("nombre", personas!![i].getNombre())
                startActivity(intent)
            }
        })

        // Cuando el usuario hace un LongClic (clic sin soltar elemento por mas de 2 segundos)
        // Es por que el usuario quiere eliminar el registro
        listaPersonas!!.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ): Boolean {
                // Preparando cuadro de dialogo para preguntar al usuario
                // Si esta seguro de eliminar o no el registro
                val ad = AlertDialog.Builder(this@GroupActivity)
                ad.setMessage("Está seguro de eliminar registro?")
                    .setTitle("Confirmación")
                ad.setPositiveButton("Si"
                ) { dialog, id ->
                    personas!![position]?.let {
                        if (it != null){
                            refAlarms.orderByChild("groupId").equalTo(it.getId()).addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    for (snapshot in dataSnapshot.children) {

                                        if (snapshot.getValue(AlarmClass::class.java) != null) {
                                            var alarm = snapshot.getValue(AlarmClass::class.java)
                                            var helper = Helper()
                                            if (alarm != null) {
                                                alarm.id1?.let { it1 ->
                                                    helper.cancelAlarm(this@GroupActivity,
                                                        it1.toInt())
                                                }
                                                alarm.id2?.let { it1 ->
                                                    helper.cancelAlarm(this@GroupActivity,
                                                        it1.toInt())
                                                }
                                                alarm.id3?.let { it1 ->
                                                    helper.cancelAlarm(this@GroupActivity,
                                                        it1.toInt())
                                                }

                                                alarm.groupId?.let { it1 ->

                                                    refNotas.child(it1).removeValue()
                                                }
                                            }
                                        }
                                    }
                                }
                                override fun onCancelled(databaseError: DatabaseError) {
                                    // Getting Post failed, log a message
                                    Toast.makeText(applicationContext,"Error al obtener el ultimo id de alarma", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    }
                    Toast.makeText(
                        this@GroupActivity,
                        "Registro borrado!", Toast.LENGTH_SHORT
                    ).show()
                }
                ad.setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {
                        Toast.makeText(
                            this@GroupActivity,
                            "Operación de borrado cancelada!", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                ad.show()
                return true
            }
        }
        fab_agregar.setOnClickListener(View.OnClickListener { // Cuando el usuario quiere agregar un nuevo registro
            val i = Intent(getBaseContext(), AddGroupActivity::class.java)
            i.putExtra("accion", "a") // Agregar
            i.putExtra("key", "")
            i.putExtra("nombre", "")
            startActivity(i)
        })
        personas = ArrayList<Group>()
        print(consultaOrdenada)
        // Cambiarlo refProductos a consultaOrdenada para ordenar lista
        consultaOrdenada.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Procedimiento que se ejecuta cuando hubo algun cambio
                // en la base de datos
                // Se actualiza la coleccion de personas
                personas!!.removeAll(personas!!)
                for (dato in dataSnapshot.getChildren()) {
                    val persona: Group? = dato.getValue(Group::class.java)
                    persona?.setId(dato.key)
                    if (persona != null) {
                        personas!!.add(persona)
                    }
                }
                val adapter = AdaptadorGroup(
                    this@GroupActivity,
                    personas as ArrayList<Group>
                )
                listaPersonas!!.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refNotas: DatabaseReference = database.getReference("groups")
        var refAlarms: DatabaseReference = database.getReference("alarms")
    }
}