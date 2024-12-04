import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.recipeapp.FavouritesDB
import com.example.recipeapp.FavouritesEntity

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {
    private val database = FavouritesDB.getDatabase(application)
    private val favouriteDao = database.favouritesDAO()

    fun getAllFavourites(): LiveData<List<FavouritesEntity>> {
        return favouriteDao.getAllFavourites()
    }
}
