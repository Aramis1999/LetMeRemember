package com.example.letmeremember.ui.papelera

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letmeremember.R
import com.example.letmeremember.databinding.FragmentPapeleraBinding
import com.example.letmeremember.databinding.FragmentRecordatorioBinding
import com.example.letmeremember.models.Trash
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PapeleraFragment : Fragment(), SearchView.OnQueryTextListener  {

    private var _binding: FragmentPapeleraBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var PapeleraAdapter: PapeleraAdapter
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var searchView: SearchView
    private lateinit var dataList: List<Trash>
//    get the reminders for the current user and only with isActive false and order them by date
    var query: com.google.firebase.database.Query =  refReminders.orderByChild("userId").equalTo(auth.currentUser?.uid)
    // This property is only valid between onCreateView and onDestroyView.
    private lateinit var binding: FragmentPapeleraBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPapeleraBinding.inflate(inflater, container, false)
        dataList = ArrayList()
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(this)

        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listaPapelera: MutableList<Trash> = ArrayList()
                for (snapshot in dataSnapshot.children) {
                    val Papelera = snapshot.getValue(Trash::class.java)
                    val id = snapshot.key
                    Papelera?.setId(id)
                    if (Papelera != null) {
                        if (Papelera.getIsActive() == false) {
                            listaPapelera.add(Papelera)
                        }
                    }
                }
                if (context == null){
                    return
                }
                recyclerView = binding.recyclerViewPapeleraList
                recyclerView.layoutManager = LinearLayoutManager(activity)
                PapeleraAdapter = PapeleraAdapter(requireContext(),listaPapelera)
                recyclerView.adapter = PapeleraAdapter
            }

            override fun onCancelled(databaseError: com.google.firebase.database.DatabaseError) {
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        // Perform search query here

        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        // Update search results as user types
        PapeleraAdapter.filter(newText)
        return true
    }

    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refReminders: DatabaseReference = database.getReference("reminders")
    }
}
