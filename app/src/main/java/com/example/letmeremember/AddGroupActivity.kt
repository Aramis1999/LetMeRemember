package com.example.letmeremember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.letmeremember.models.Group
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddGroupActivity : AppCompatActivity() {

    lateinit var nombre : EditText
    private var key = ""
    private var accion = ""
    lateinit var title : TextView

    private lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)
        inicializar()
    }

    private fun inicializar() {
        nombre = findViewById<EditText>(R.id.nombre)

        title = findViewById((R.id.groupCreateTitle))


        val nombre = findViewById<EditText>(R.id.nombre)

// Obtención de datos que envia actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            key = datos.getString("key").toString()
        }
        if (datos != null) {
            nombre.setText(intent.getStringExtra("nombre").toString())
        }
        if (datos != null) {
            accion = datos.getString("accion").toString()
            if(accion != "a"){
                //title.text = R.string.groupEditTitle.toString()
                title.text = "Editar grupo";
            }
        }
    }

    fun guardar(v: View?) {

        val nombre: String = nombre?.text.toString()


        database=FirebaseDatabase.getInstance().getReference("groups")
// Se forma objeto persona
        val persona = Group(nombre)

        if (accion == "a") { //Agregar registro
            database.child(nombre).setValue(persona).addOnSuccessListener {
                Toast.makeText(this,"Se guardo con exito", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed ", Toast.LENGTH_SHORT).show()
            }
        } else // Editar registro
        {
            val key = database.child("nombre").push().key
            if (key == null) {
                Toast.makeText(this,"Llave vacia", Toast.LENGTH_SHORT).show()
            }
            val personasValues = persona.toMap()
            val childUpdates = hashMapOf<String, Any>(
                "$nombre" to personasValues
            )
            database.updateChildren(childUpdates)
            Toast.makeText(this,"Se actualizo con exito", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    fun cancelar(v: View?) {
        finish()
    }

}