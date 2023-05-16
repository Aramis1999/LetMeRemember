package com.example.letmeremember.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letmeremember.R
import com.example.letmeremember.databinding.FragmentRecordatorioBinding
import com.example.letmeremember.models.Reminder
import com.example.letmeremember.ui.Recordatorio.RecordatorioAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RecordatorioFragment : Fragment(){

    private var _binding: FragmentRecordatorioBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var RecordatorioAdapter: RecordatorioAdapter
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var searchView: SearchView
    private lateinit var dataList: List<Reminder>
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var idGrupo: String

    var query: com.google.firebase.database.Query =  refReminders.orderByChild("userId").equalTo(auth.currentUser?.uid)


    // This property is only valid between onCreateView and onDestroyView.

    private lateinit var binding: FragmentRecordatorioBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentRecordatorioBinding.inflate(inflater, container, false)
        dataList = ArrayList()
        idGrupo = arguments?.getString("idGrupo") ?: ""
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.searchView)

        binding.btnAgregarRecordatorio.setOnClickListener{
            val navController = (context as AppCompatActivity).findNavController(R.id.nav_host_fragment_content_activity_menu_grupos)
            val bundle = Bundle()
            bundle.putString("accion", "add")
            bundle.putString("idGrupo",idGrupo)
            navController.navigate(R.id.nav_AddRecordatorios,bundle)
        }
        val argumentsString = "";

        if (binding==null){
            return
        }
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listaRecordarorio: MutableList<Reminder> = ArrayList()
                for (snapshot in dataSnapshot.children) {
                    val Recordatorio = snapshot.getValue(Reminder::class.java)
                    val id = snapshot.key
                    Recordatorio?.setKey(id)
                    if (Recordatorio != null) {
                        if (Recordatorio.getIsActive() == true && Recordatorio.getGroupId() == idGrupo) {
                            listaRecordarorio.add(Recordatorio)
                        }
                    }
                }
                if (listaRecordarorio.size < 1){
                    return
                }
                if (context == null){
                    return
                }
                recyclerView = binding.recyclerViewRecordatorioList
                recyclerView.layoutManager = LinearLayoutManager(activity)
                RecordatorioAdapter = RecordatorioAdapter(context!!,listaRecordarorio)
                recyclerView.adapter = RecordatorioAdapter
            }

            override fun onCancelled(databaseError: com.google.firebase.database.DatabaseError) {
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    override fun onDestroyView() {
        Log.d("SALir", "si sale")
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        super.onCreateOptionsMenu(menu, inflater)
    }

//    override fun onQueryTextSubmit(query: String?): Boolean {
//        // Perform search query here
//
//        return false
//    }

//    override fun onQueryTextChange(newText: String?): Boolean {
//        // Update search results as user types
//        RecordatorioAdapter.filter(newText)
//        return true
//    }
    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refReminders: DatabaseReference = database.getReference("reminders")
    }
}
