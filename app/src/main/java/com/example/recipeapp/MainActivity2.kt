package com.example.recipeapp
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import HorizontalAdapter
import VerticalAdaptor
import android.util.Log
import android.widget.Toast
import com.hashdroid.recipeapp.RetrofitClient
import com.hashdroid.recipeapp.RecipeResponse
import com.hashdroid.recipeapp.RecipeResponse2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {

    private lateinit var horizontalAdapter: HorizontalAdapter
    private lateinit var verticalAdapter: VerticalAdaptor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Initialize RecyclerView
        val recyclerView1 = findViewById<RecyclerView>(R.id.recycler_view1)
        val recyclerView2= findViewById<RecyclerView>(R.id.recycler_view2)

        recyclerView1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Fetch random recipes
        fetchALLRecipes()
        fetchRandomRecipes()

    }

    private fun fetchALLRecipes() {
        val apiKey = "195f87d5a199467797f27b34555430e1"
        val retrofit = RetrofitClient.retrofit
        val call = retrofit.getSearchRecipes( 100,apiKey)
        call.enqueue(object : Callback<RecipeResponse2> {
            override fun onResponse(call: Call<RecipeResponse2>, response: Response<RecipeResponse2>) {
                if (response.isSuccessful) {

                    Log.d("Ashu", response.body().toString())
                    response.body()?.results?.let { results ->
                        verticalAdapter = VerticalAdaptor(results)
                        findViewById<RecyclerView>(R.id.recycler_view2).adapter = verticalAdapter
                    }
                }
                else{
                    Toast.makeText(this@MainActivity2, "Error: ${response.code()}", Toast.LENGTH_LONG).show()

                }
            }



            override fun onFailure(call: Call<RecipeResponse2>, t: Throwable) {
                Toast.makeText(this@MainActivity2, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun fetchRandomRecipes() {
        val apiKey = "195f87d5a199467797f27b34555430e1"
        val retrofit = RetrofitClient.retrofit
        val call = retrofit.getRandomRecipes(10, apiKey)
        call.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                if (response.isSuccessful) {
                    response.body()?.recipes?.let { recipes ->
                        horizontalAdapter = HorizontalAdapter(recipes)
                        findViewById<RecyclerView>(R.id.recycler_view1).adapter = horizontalAdapter
                    }
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                // Handle API call failure
            }
        })
    }
}




