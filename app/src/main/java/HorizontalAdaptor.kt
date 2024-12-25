import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.hashdroid.recipeapp.Recipe
import kotlin.random.Random

class HorizontalAdapter(private val recipes: List<Recipe>,private val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<HorizontalAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_view1, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.dish_name.text = recipe.title
        val randomCookTime = Random.nextInt(10, 31) // Generates a random number between 10 and 30
        holder.cook_time.text = "Ready in $randomCookTime min" // Sets the random cook time

        // Load the image using Glide
        Glide.with(holder.itemView.context)
            .load(recipe.image)
            .into(holder.dish_image)

        // Handle click event
        holder.root.setOnClickListener {
            onItemClick(recipe.id) // Pass the ID to the listener
        }
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dish_name: TextView = itemView.findViewById(R.id.dish_name)
        var cook_time: TextView = itemView.findViewById(R.id.cook_time)
        var dish_image: ImageView = itemView.findViewById(R.id.dish_image)
        var root=itemView
    }
}

