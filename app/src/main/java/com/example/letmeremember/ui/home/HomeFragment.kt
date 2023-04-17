package com.example.letmeremember.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letmeremember.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var grupoAdapter: GrupoAdapter

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listaGrupos = mutableListOf<GrupoObject>()

        listaGrupos.add(GrupoObject("grupo 1","10","hola","GP01"))
        listaGrupos.add(GrupoObject("grupo 2","10","hola","GP02"))
        listaGrupos.add(GrupoObject("grupo 3","10","hola","GP03"))
        listaGrupos.add(GrupoObject("grupo 4","10","hola","GP04"))

        recyclerView = binding.recyclerViewGroupList
        recyclerView.layoutManager = LinearLayoutManager(activity)
        grupoAdapter = GrupoAdapter(requireContext(), listaGrupos)
        recyclerView.adapter = grupoAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
