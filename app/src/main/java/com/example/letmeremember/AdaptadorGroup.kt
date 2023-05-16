package com.example.letmeremember
import android.app.Activity
import com.example.letmeremember.models.Group
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.letmeremember.R

class AdaptadorGroup(private val context: Activity, var notas: List<Group>):
    ArrayAdapter<Group?>(context, R.layout.group_layout, notas)
{
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
// Método invocado tantas veces como elementos tenga la coleccion personas
// para formar a cada item que se visualizara en la lista personalizada
        val layoutInflater = context.layoutInflater
        var rowview: View? = null
// optimizando las diversas llamadas que se realizan a este método
// pues a partir de la segunda llamada el objeto view ya viene formado
// y no sera necesario hacer el proceso de "inflado" que conlleva tiempo y
// desgaste de bateria del dispositivo
        rowview = view ?: layoutInflater.inflate(R.layout.group_layout, null)
        val tvNombre = rowview!!.findViewById<TextView>(R.id.tvNombre)

        tvNombre.text = notas[position].getNombre()
        return rowview
    }
}