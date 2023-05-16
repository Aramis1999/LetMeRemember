package com.example.letmeremember.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.letmeremember.AddGroupActivity
import com.example.letmeremember.R
import com.google.firebase.database.*
import java.util.*
import com.example.letmeremember.alarms.AlarmClass
import com.example.letmeremember.alarms.Alarm
import com.example.letmeremember.helper.Helper
import com.example.letmeremember.models.Group
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.Context
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateHomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var key: String? = null
    private var nombre: String? = null
    private var accion: String? = null
    private var alarm :AlarmClass? = null
    var lastId by Delegates.notNull<Long>()
    private lateinit var nombreET: EditText
    private lateinit var titleET: TextView
    private lateinit var SaveBTN: Button
    private var userId: String? = null
    private var percent = "0"
    //private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        //INTERFACES
        val key = arguments?.getString("key")
        val nombre =arguments?.getString("nombre")
        val accion =arguments?.getString("accion")

        this.key = key
        this.nombre = nombre
        this.accion = accion

        Log.d("key", key.toString())
        Log.d("nombre", nombre.toString())
        Log.d("accion", accion.toString())

        userId = auth.currentUser?.uid
        inicializar(view)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_home, container, false)
    }

    private fun inicializar(view: View) {
        //Params
        nombreET = view.findViewById(R.id.nombre)
        //Params Text
        titleET = view.findViewById((R.id.groupCreateTitle))
        //Params Button
        SaveBTN = view.findViewById(R.id.buttCalcular)

        SaveBTN.setOnClickListener {
            guardar()
        }

// Obtención de datos que envia actividad anterior

        if (arguments != null) {
            if(accion != "a"){
                titleET.text = "Editar grupo";
                nombreET.setText(nombre)

                // obtener alarmas por el key del grupo
                refAlarms.orderByChild("groupId").equalTo(key).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.children) {
                            if (snapshot.getValue(AlarmClass::class.java) != null) {
                                alarm = snapshot.getValue(AlarmClass::class.java)
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

//        //Consulta de el ultimo Id de las alarmas para generar el siguiente (la tabla contiene 3 id de alarmas)
        refAlarms.orderByKey().limitToLast(1).addListenerForSingleValueEvent(object :
            ValueEventListener {
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
                Toast.makeText(activity,"Error al obtener el ultimo id de alarma", Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun guardar() {

        val nombre: String = nombreET?.text.toString()


// Se forma objeto persona
        val persona = Group(nombre,userId,true,percent)
        //val persona = Group(nombre,nombre,true,nombre)
        var helper = Helper()
        if (accion == "a") { //Agregar registro

            var key = helper.generateUniqueCode(10)
            refGroup.child(key).setValue(persona).addOnSuccessListener {

                helper.createAlarm(requireContext(),1,nombre,key,(lastId+1).toInt())
                helper.createAlarm(requireContext(),2,nombre,key,(lastId+2).toInt())
                helper.createAlarm(requireContext(),3,nombre,key,(lastId+3).toInt())
                val AlarmObject = Alarm(key,lastId+1,lastId+2,lastId+3)
                AddGroupActivity.refAlarms.child(key).setValue(AlarmObject)
                Toast.makeText(activity,"Se guardo con exito", Toast.LENGTH_SHORT).show()
                val fragmentManager = requireActivity().supportFragmentManager
                fragmentManager.popBackStack()
            }.addOnFailureListener{
                Toast.makeText(activity,"Failed ", Toast.LENGTH_SHORT).show()
            }
        } else // Editar registro
        {
            if (key == null) {
                Toast.makeText(activity,"Llave vacia", Toast.LENGTH_SHORT).show()
            }
            //cancelar alarmas para crear nuevas
            helper.cancelAlarm(requireContext(),alarm!!.id1!!.toInt())
            helper.cancelAlarm(requireContext(),alarm!!.id2!!.toInt())
            helper.cancelAlarm(requireContext(),alarm!!.id3!!.toInt())
            val refGroups=refGroup.child(key.toString())
            refGroups.child("nombre").setValue(nombre)
            refGroups.child("percent").setValue("0")
            helper.createAlarm(requireContext(),1,nombre,key.toString(),(lastId+1).toInt())
            helper.createAlarm(requireContext(),5,nombre,key.toString(),(lastId+2).toInt())
            helper.createAlarm(requireContext(),10,nombre,key.toString(),(lastId+3).toInt())
            Toast.makeText(activity,"Se actualizo con exito", Toast.LENGTH_SHORT).show()
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }
    }




    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refAlarms: DatabaseReference = database.getReference("alarms")
        var refGroup: DatabaseReference = database.getReference("groups")
    }
}