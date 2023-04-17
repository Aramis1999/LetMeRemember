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
import com.example.letmeremember.databinding.FragmentRecordatorioBinding
import com.example.letmeremember.ui.Recordatorio.RecordatorioAdapter
import com.example.letmeremember.ui.Recordatorio.RecordatorioObject

class RecordatorioFragment : Fragment() {

    private var _binding: FragmentRecordatorioBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recordatorioAdapter: RecordatorioAdapter

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentRecordatorioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listaRecordatorio = mutableListOf<RecordatorioObject>()


        listaRecordatorio.add(RecordatorioObject("Recordatorio 1","10","hola","PL01"))
        listaRecordatorio.add(RecordatorioObject("Recordatorio 2","10","hola","PL02"))
        listaRecordatorio.add(RecordatorioObject("Recordatorio 3","10","hola","PL03"))
        listaRecordatorio.add(RecordatorioObject("Recordatorio 4","10","hola","PL04"))
        recyclerView = binding.recyclerViewRecordatorioList
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recordatorioAdapter = RecordatorioAdapter(requireContext(),listaRecordatorio)
        recyclerView.adapter = recordatorioAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
