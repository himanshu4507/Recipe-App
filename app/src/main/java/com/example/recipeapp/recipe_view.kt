package com.example.recipeapp
import EquipmentsAdaptor
import IngredientsAdaptor
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.media3.database.DatabaseProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hashdroid.recipeapp.Equipment
import com.hashdroid.recipeapp.Ingredient
import com.hashdroid.recipeapp.Recipie_View
import com.hashdroid.recipeapp.RetrofitClient
import com.hashdroid.recipeapp.Step
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Recipie_view : Fragment() {

    private var recipeId: Int? = null
    private lateinit var database: FavouritesDB
    private lateinit var favouritesImg: ImageView
    private var imgTitle: String? = null
    private var imgUrl: String? = null
    private var cookingtime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeId = it.getInt(ARG_RECIPE_ID)
        }
    }

    private lateinit var ingredientAdapter: IngredientsAdaptor
    private lateinit var equipmentAdaptor: EquipmentsAdaptor

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_view, container, false)

        // Initialize Ingredients RecyclerView
        val ingredientsRv = view.findViewById<RecyclerView>(R.id.ingredients_rv)
        ingredientsRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        //Initialize Equipment Recycler View
        val equipmentRv=view.findViewById<RecyclerView>(R.id.equipments_rv)
        equipmentRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Initialize database
        database = FavouritesDB.getDatabase(requireContext())

        // Reference to the heart image view
        favouritesImg = view.findViewById(R.id.favourites_img)


        fetchRecipieView(view);
        // Here you can use recipeId to fetch and display data or call an API
        return view
    }

    private fun fetchRecipieView(view: View) {
        val apiKey = "6511024c4bb146f09491fe45f612b0ab"
            //"7e09bf0f61914144b91065b5d90803ea"
        //"195f87d5a199467797f27b34555430e1"
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

                        // Ingredients RV
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

                        //Equipment RV
                        val list2= mutableListOf<Equipment>()
                        it.analyzedInstructions.forEach {
                            it.steps.forEach {
                                list2.addAll(it.equipment)
                            }
                        }

                        equipmentAdaptor=EquipmentsAdaptor(list2)
                        view.findViewById<RecyclerView>(R.id.equipments_rv).adapter =
                            equipmentAdaptor

                        // Quick Summary
                        val summary: String = it.summary
                        val decoded: String = Html
                            .fromHtml(summary, Html.FROM_HTML_MODE_COMPACT)
                            .toString()
                        val Summary=view.findViewById<TextView>(R.id.summary)
                        Summary.text= decoded

                        //for favourite fragment

                        imgTitle = it.title
                        imgUrl = it.image
                        cookingtime = it.readyInMinutes

                        // Set up favorite handling
                        handleFavoriteClick()


                    }
                }
            }

            override fun onFailure(p0: Call<Recipie_View>, p1: Throwable) {
                Toast.makeText(requireContext(), p1.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun handleFavoriteClick() {
        if (recipeId == null || imgTitle == null || imgUrl == null) {
            return
        }

        val currentRecipe = FavouritesEntity(
            id= recipeId!!,
            title = imgTitle!!,
            image = imgUrl!!,
            cookingTime = cookingtime
        )

        // Check initial state and set image accordingly
        CoroutineScope(Dispatchers.IO).launch {
            val isFavorite = database.favouritesDAO().isFavorite(recipeId!!)
            withContext(Dispatchers.Main) {
                favouritesImg.setImageResource(if (isFavorite) R.drawable.addedfav else R.drawable.addfav)
            }
        }

        // Set up click listener
        favouritesImg.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val isAlreadyFavorite = database.favouritesDAO().isFavorite(recipeId!!)

                if (isAlreadyFavorite) {
                    // Remove from favorites
                    database.favouritesDAO().deleteFromFavourites(currentRecipe)
                    withContext(Dispatchers.Main) {
                        favouritesImg.setImageResource(R.drawable.addfav)
                        Toast.makeText(context, "Removed from Favorites", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Add to favorites
                    database.favouritesDAO().addToFavourites(currentRecipe)
                    withContext(Dispatchers.Main) {
                        favouritesImg.setImageResource(R.drawable.addedfav)
                        Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
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
