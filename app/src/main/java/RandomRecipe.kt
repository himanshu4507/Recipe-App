import com.hashdroid.recipeapp.Recipe
import com.hashdroid.recipeapp.RecipeResponse
import com.hashdroid.recipeapp.RecipeResponse2
import com.hashdroid.recipeapp.Recipie_View
import com.hashdroid.recipeapp.SimilarRecipes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {
    @GET("recipes/complexSearch")
    fun getSearchRecipes(
        @Query("number") number: Int,
        @Query("apiKey") apiKey:String
    ): Call<RecipeResponse2>

    @GET("recipes/random")
    fun getRandomRecipes(
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String
    ): Call<RecipeResponse>

    @GET("recipes/{id}/information")
    fun getRecipieView(
        @Path("id") id:Int,
        @Query("apiKey") apiKey: String
    ): Call<Recipie_View>

    @GET("recipes/autocomplete")
    fun getAutocompleteRecipes(
        @Query("query") query: String?,
        @Query("apiKey") apiKey: String
    ): Call<List<Recipe>>

    @GET("recipes/{id}/similar")
    fun getSimilarRecipes(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ): Call<List<SimilarRecipes>>
}