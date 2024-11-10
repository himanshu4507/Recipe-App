package com.example.recipeapp

import IngredientsAdaptor
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hashdroid.recipeapp.Ingredient
import com.hashdroid.recipeapp.Recipie_View
import com.hashdroid.recipeapp.RetrofitClient
import com.hashdroid.recipeapp.Step
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Recipie_view : Fragment() {

    private var recipeId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeId = it.getInt(ARG_RECIPE_ID)
        }
    }

    private lateinit var ingredientAdapter: IngredientsAdaptor

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_view, container, false)

        // Initialize RecyclerView
        val ingredientsRv = view.findViewById<RecyclerView>(R.id.ingredients_rv)
        ingredientsRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        fetchRecipieView(view);
        // Here you can use recipeId to fetch and display data or call an API
        return view
    }

    private fun fetchRecipieView(view: View) {
        val apiKey = "195f87d5a199467797f27b34555430e1"
        val retrofit = RetrofitClient.retrofit
        Log.d("TAG", recipeId.toString())
        val call = recipeId?.let { retrofit.getRecipieView(it, apiKey) }

        call?.enqueue(object : Callback<Recipie_View> {
            override fun onResponse(
                call: Call<Recipie_View>,
                response: Response<Recipie_View>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val list = mutableListOf<Ingredient>()
                        it.analyzedInstructions.forEach {
                            it.steps.forEach {
                                list.addAll(it.ingredients)
                            }
                        }
                        Log.d("TAG", "onResponse: $it")

                        ingredientAdapter = IngredientsAdaptor(list)
                        view.findViewById<RecyclerView>(R.id.ingredients_rv).adapter =
                            ingredientAdapter

                        //recipie image
                        Glide.with(requireContext())
                            .load(it.image)
                            .into(view.findViewById(R.id.fragement_dish_image))

                        //recipie name
                        val dishNameTextView = view.findViewById<TextView>(R.id.fragement_dish_name)
                        dishNameTextView.text = it.title

                        //ready in min
                        val readyInTime=view.findViewById<TextView>(R.id.ready_in_time)
                        readyInTime.text= it.readyInMinutes.toString()

                        //serving
                        val servingVal=view.findViewById<TextView>(R.id.serving_value)
                        servingVal.text= it.servings.toString()

                        //price pre serving
                        val pricePerServe=view.findViewById<TextView>(R.id.price_servings_value)
                        pricePerServe.text= it.pricePerServing.toString()

                        //instructions

                        val instruction = mutableListOf<Step>()
                        it.analyzedInstructions.forEach {
                                instruction.addAll(it.steps)
                        }
                        val instructions=view.findViewById<TextView>(R.id.instructions)
                        val builder = StringBuilder()
                        for (star in instruction) {
                            builder.append(star.step)
                            builder.append("\n\n")
                        }
                        instructions.text=builder



                    }
                }
            }

            override fun onFailure(p0: Call<Recipie_View>, p1: Throwable) {
                Toast.makeText(requireContext(), p1.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        private const val ARG_RECIPE_ID = "recipe_id"

        fun newInstance(recipeId: Int) = Recipie_view().apply {
            arguments = Bundle().apply {
                putInt(ARG_RECIPE_ID, recipeId)
            }
        }
    }
}
