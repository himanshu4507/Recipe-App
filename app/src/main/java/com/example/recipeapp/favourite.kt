package com.example.recipeapp

import FavouritesViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavouriteFragment : Fragment() {

    private lateinit var favouritesAdapter: FavouritesAdapter
    private lateinit var favouritesViewModel: FavouritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)

        // Initialize RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_viewH)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize Adapter with an empty list
        favouritesAdapter = FavouritesAdapter(emptyList()) { favourite ->
            // Handle item click here (e.g., navigate to a details page)
            // Example: Toast.makeText(context, "Clicked: ${favourite.title}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = favouritesAdapter

        // Initialize ViewModel
        favouritesViewModel = ViewModelProvider(this)[FavouritesViewModel::class.java]

        // Observe LiveData from ViewModel and update RecyclerView when data changes
        favouritesViewModel.getAllFavourites().observe(viewLifecycleOwner) { favourites ->
            favouritesAdapter.updateData(favourites)
        }

        return view
    }
}
