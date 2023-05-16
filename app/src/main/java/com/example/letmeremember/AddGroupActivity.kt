package com.example.letmeremember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.letmeremember.alarms.Alarm
import com.example.letmeremember.alarms.AlarmClass
import com.example.letmeremember.models.Group
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.letmeremember.helper.Helper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlin.properties.Delegates

class AddGroupActivity : AppCompatActivity() {

    lateinit var nombre : EditText
    private var key = ""
    private var accion = ""
    lateinit var title : TextView
    var lastId by Delegates.notNull<Long>()
    private var alarm :AlarmClass? = null
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

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

                // obtener alarmas por el key del grupo
                refAlarms.orderByChild("groupId").equalTo(key).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.children) {

                            if (snapshot.getValue(AlarmClass::class.java) != null) {
                                alarm = snapshot.getValue(AlarmClass::class.java)
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

//        //Consulta de el ultimo Id de las alarmas para generar el siguiente (la tabla contiene 3 id de alarmas)
        refAlarms.orderByKey().limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {

                    if (snapshot.getValue(AlarmClass::class.java) != null) {
                        var alarm =snapshot.getValue(AlarmClass::class.java)
                        lastId = alarm!!.id1!!
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Toast.makeText(applicationContext,"Error al obtener el ultimo id de alarma", Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun guardar(v: View?) {

        val nombre: String = nombre?.text.toString()


        database=FirebaseDatabase.getInstance().getReference("groups")
// Se forma objeto persona
        val persona = Group(nombre,auth.currentUser?.uid.toString(),true,nombre)
        var helper = Helper()
        if (accion == "a") { //Agregar registro

            var key = helper.generateUniqueCode(10)
            database.child(key).setValue(persona).addOnSuccessListener {

                helper.createAlarm(this,1,nombre,key,(lastId+1).toInt())
                helper.createAlarm(this,2,nombre,key,(lastId+2).toInt())
                helper.createAlarm(this,3,nombre,key,(lastId+3).toInt())
                val AlarmObject = Alarm(key,lastId+1,lastId+2,lastId+3)
                refAlarms.child(key).setValue(AlarmObject)
                Toast.makeText(this,"Se guardo con exito", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed ", Toast.LENGTH_SHORT).show()
            }
        } else // Editar registro
        {
            if (key == null) {
                Toast.makeText(this,"Llave vacia", Toast.LENGTH_SHORT).show()
            }
            //cancelar alarmas para crear nuevas
            helper.cancelAlarm(this,alarm!!.id1!!.toInt())
            helper.cancelAlarm(this,alarm!!.id2!!.toInt())
            helper.cancelAlarm(this,alarm!!.id3!!.toInt())
            val refGroups=database.child(key)
            refGroups.child("nombre").setValue(nombre)
            helper.createAlarm(this,1,nombre,key,(lastId+1).toInt())
            helper.createAlarm(this,5,nombre,key,(lastId+2).toInt())
            helper.createAlarm(this,10,nombre,key,(lastId+3).toInt())
            Toast.makeText(this,"Se actualizo con exito", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    fun cancelar(v: View?) {
        finish()
    }
    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refAlarms: DatabaseReference = database.getReference("alarms")
    }
}