package com.example.letmeremember

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.letmeremember.databinding.FragmentAddReminderBinding
import com.example.letmeremember.helper.Helper
import com.example.letmeremember.models.Reminder
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date


class AddReminder : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentAddReminderBinding? = null
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var key = ""
    private var title = ""
    private var body = ""
    private var accion = ""
    private var idGrupo= ""


    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        key = arguments?.getString("key").toString()
        title = arguments?.getString("titulo").toString()
        body = arguments?.getString("contenido").toString()
        accion = arguments?.getString("accion").toString()
        idGrupo = arguments?.getString("idGrupo").toString()
        Log.d("Accion",accion)
        Log.d("Grupo",idGrupo)
        Log.d("key",key)
        _binding = FragmentAddReminderBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (accion == "edit"){
            binding.titulo.setText(title)
            binding.descripcion.setText(body)
        }

        binding.btnEnviar.setOnClickListener{

            if (accion == "add"){
                var helper = Helper()
                var llave = helper.generateUniqueCode(10)
                val reminder = Reminder(llave,idGrupo, binding.titulo.text.toString(), binding.descripcion.text.toString(), auth.currentUser?.uid, true, "")
                refReminders.child(llave).setValue(reminder).addOnSuccessListener {
                    Toast.makeText(activity,"Se guardo con exito", Toast.LENGTH_SHORT).show()
                }
                Log.d("btnEnviar", "agrega")
            }
            if (accion == "edit"){
                val reminder = Reminder(key,idGrupo, binding.titulo.text.toString(), binding.descripcion.text.toString(), auth.currentUser?.uid, true, "")
                refReminders.child(key).setValue(reminder).addOnSuccessListener {
                    //Toast.makeText(activity,"Se edito con exito", Toast.LENGTH_SHORT).show()
                    Log.d("btnEnviar", "succes")
                }

                Log.d("btnEnviar", "edita")
            }
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }

    }



    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refReminders: DatabaseReference = database.getReference("reminders")
    }
}