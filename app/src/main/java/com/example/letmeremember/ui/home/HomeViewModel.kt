package com.example.letmeremember.ui.home

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.ListView
import com.example.letmeremember.R

class HomeViewModel : ViewModel() {
    fun onCreate(activity: AppCompatActivity, savedInstanceState: Bundle?){
        val listaGrupos= mutableListOf<GrupoObject>()

        listaGrupos.add(GrupoObject("grupo 1","10","hola","GP01"))
        listaGrupos.add(GrupoObject("grupo 2","10","hola","GP02"))
        listaGrupos.add(GrupoObject("grupo 3","10","hola","GP03"))
        listaGrupos.add(GrupoObject("grupo 4","10","hola","GP04"))

        val adapter = GrupoAdapter(activity, listaGrupos)
        val RecyclerView =activity.findViewById<ListView>(R.id.recyclerView_groupList)

    }
}