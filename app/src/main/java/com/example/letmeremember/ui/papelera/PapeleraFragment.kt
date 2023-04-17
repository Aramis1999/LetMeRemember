package com.example.letmeremember.ui.papelera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letmeremember.databinding.FragmentPapeleraBinding
import com.example.letmeremember.ui.papelera.PapeleraObject
import com.example.letmeremember.ui.papelera.PapeleraAdapter

class PapeleraFragment : Fragment() {

    private var _binding: FragmentPapeleraBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var PapeleraAdapter: PapeleraAdapter

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPapeleraBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listaPapelera = mutableListOf<PapeleraObject>()
        listaPapelera.add(PapeleraObject("Papelera 1","11/03/2023 4:00pm","Eliminaste: Tarjeta-Cuantos dias tiene un siglo","RC01"))
        listaPapelera.add(PapeleraObject("Papelera 2","11/03/2023 4:00pm","Eliminaste: Tarjeta-tabla del 5","RC02"))
        listaPapelera.add(PapeleraObject("Papelera 3","11/03/2023 4:00pm","Eliminaste: Tarjeta-integrales","RC03"))
        listaPapelera.add(PapeleraObject("Papelera 4","11/03/2023 4:00pm","Eliminaste: Tarjeta-Derivadas","RC04"))


        recyclerView = binding.recyclerViewPapeleraList
        recyclerView.layoutManager = LinearLayoutManager(activity)
        PapeleraAdapter = PapeleraAdapter(requireContext(),listaPapelera)
        recyclerView.adapter = PapeleraAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
