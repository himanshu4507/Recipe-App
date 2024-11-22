package com.example.recipeapp
import HorizontalAdapter
import VerticalAdaptor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hashdroid.recipeapp.RecipeResponse
import com.hashdroid.recipeapp.RecipeResponse2
import com.hashdroid.recipeapp.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var horizontalAdapter: HorizontalAdapter
    private lateinit var verticalAdapter: VerticalAdaptor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //change fragment on click on search bar
        val search: TextView= view.findViewById(R.id.search_bar)

        search.setOnClickListener {
            activity?.findViewById<View>(R.id.bottomNavBar)?.visibility = View.GONE
            // Navigate to the new fragment
            val newFragment = SearchView()
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, newFragment) // Replace 'fragment_container' with your actual container ID
                .addToBackStack(null) // Add to backstack for back navigation
                .commit()
        }

        // Initialize RecyclerView
        val recyclerView1 = view.findViewById<RecyclerView>(R.id.recycler_view1)
        recyclerView1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val recyclerView2 = view.findViewById<RecyclerView>(R.id.recycler_view2)
        recyclerView2.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        // Fetch random recipes
        fetchRandomRecipes(view)
        fetchAllRecipes(view)

        return view
    }

    private fun fetchRandomRecipes(view: View) {
        val apiKey = "6511024c4bb146f09491fe45f612b0ab"
            //"7e09bf0f61914144b91065b5d90803ea"
        //"195f87d5a199467797f27b34555430e1"
        val retrofit = RetrofitClient.retrofit
        val call = retrofit.getRandomRecipes(10, apiKey)
        call.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                if (response.isSuccessful) {
                    response.body()?.recipes?.let { recipes ->
                        horizontalAdapter = HorizontalAdapter(recipes)
                        view.findViewById<RecyclerView>(R.id.recycler_view1).adapter = horizontalAdapter
                    }
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                // Handle API call failure
            }
        })
    }

    private fun fetchAllRecipes(view: View) {
        val apiKey = "6511024c4bb146f09491fe45f612b0ab"
            //"7e09bf0f61914144b91065b5d90803ea"
        //"195f87d5a199467797f27b34555430e1"
        val retrofit = RetrofitClient.retrofit
        val call = retrofit.getSearchRecipes(50, apiKey)
        call.enqueue(object : Callback<RecipeResponse2> {
            override fun onResponse(call: Call<RecipeResponse2>, response: Response<RecipeResponse2>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { recipes ->
                        verticalAdapter = VerticalAdaptor(recipes){ recipeId ->
                            openRecipeViewFragment(recipeId)
                        }
                        view.findViewById<RecyclerView>(R.id.recycler_view2).adapter = verticalAdapter
                    }
                }
            }

            override fun onFailure(call: Call<RecipeResponse2>, t: Throwable) {
                // Handle API call failure
            }
        })
    }
    override fun onResume() {
        super.onResume()
        // Show BottomNavigationView when returning to HomeFragment
        activity?.findViewById<View>(R.id.bottomNavBar)?.visibility = View.VISIBLE
    }


    private fun openRecipeViewFragment(recipieId: Int){
        val fragment = Recipie_view.newInstance(recipieId)

        activity?.findViewById<View>(R.id.bottomNavBar)?.visibility = View.GONE

        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}