import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.hashdroid.recipeapp.Ingredient
import com.hashdroid.recipeapp.Recipie_View


class IngredientsAdaptor(private val ingredients:List<Ingredient>): RecyclerView.Adapter<IngredientsAdaptor.IViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.ingredients_rv, parent, false)
        return IViewHolder(view);
    }

    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        val ingredient=ingredients[position];
        holder.ingredient_name.text=ingredient.name;

        // Load the image using Glide
        Glide.with(holder.ingredient_image.context)
            .load("https://img.spoonacular.com/ingredients_100x100/" + ingredient.image)
            .into(holder.ingredient_image)
    }


    class IViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
       var ingredient_image:ImageView=itemView.findViewById(R.id.ingredient_image)
       var ingredient_name:TextView=itemView.findViewById(R.id.ingredient_name);
    }

    override fun getItemCount(): Int {
        return ingredients.size;
    }


}