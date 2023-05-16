package com.example.letmeremember.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letmeremember.R
import com.example.letmeremember.databinding.FragmentHomeBinding
import com.example.letmeremember.models.Group
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var grupoAdapter: GrupoAdapter

    private lateinit var dataList: List<Group>
    private lateinit var searchView: SearchView
    private lateinit var buttonAgree: View
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var query: com.google.firebase.database.Query =  refGrupos.orderByChild("userId").equalTo(auth.currentUser?.uid)
    // This property is only valid between onCreateView and onDestroyView.
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        dataList = ArrayList()
        val root: View = binding.root

        return root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.searchView)
        buttonAgree = view.findViewById(R.id.fab_agregar)

        buttonAgree.setOnClickListener {
            val navController = (context as AppCompatActivity).findNavController(R.id.nav_host_fragment_content_activity_menu_grupos)
            val bundle = Bundle()
            bundle.putString("accion","a")
            navController.navigate(R.id.nav_createHome, bundle)
        }

        searchView.setOnQueryTextListener(this)


        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listaGrupos: MutableList<Group> = ArrayList()
                for (snapshot in dataSnapshot.children) {
                    val Grupos = snapshot.getValue(Group::class.java)
                    val id = snapshot.key
                    Grupos?.setId(id)
                    if (Grupos != null) {
                        if (Grupos.getIsActive() == true) {
                            listaGrupos.add(Grupos)
                        }
                    }
                }
                if(context == null){
                    return
                }
                recyclerView = binding.recyclerViewGroupList
                recyclerView.layoutManager = LinearLayoutManager(activity)
                grupoAdapter = GrupoAdapter(requireContext(), listaGrupos)
                recyclerView.adapter = grupoAdapter
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
        grupoAdapter.filter(newText)
        return true
    }

    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refGrupos: DatabaseReference = database.getReference("groups")
        var refAlarms: DatabaseReference = database.getReference("alarms")
    }
}
