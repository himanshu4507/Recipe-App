import com.hashdroid.recipeapp.RecipeResponse
import com.hashdroid.recipeapp.RecipeResponse2
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("recipes/complexSearch")
    fun getSearchRecipes(
        @Query("number") number: Int,
        @Query("apikey") apiKey:String
    ): Call<RecipeResponse2>

    @GET("recipes/random")
    fun getRandomRecipes(
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String
    ): Call<RecipeResponse>


}