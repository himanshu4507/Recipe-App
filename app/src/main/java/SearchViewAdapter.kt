import android.annotation.SuppressLint
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.hashdroid.recipeapp.Recipe

class SearchViewAdapter(
    private var recipes: List<Recipe>, // Mutable list for updating recipes
    private val onItemClick: (Int) -> Unit // Callback for handling item clicks
) : RecyclerView.Adapter<SearchViewAdapter.SearchViewHolder>() {

    private var query: String = "" // Variable to store the search query

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_view5, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val recipe = recipes[position]
        val highlightedTitle = highlightQuery(recipe.title, query)
        holder.dishTitle.text = highlightedTitle

        holder.root.setOnClickListener {
            onItemClick(recipe.id) // Trigger the callback with the recipe ID
        }
    }

    // Method to update the recipes and search query
    @SuppressLint("NotifyDataSetChanged")
    fun updateRecipes(newRecipes: List<Recipe>, searchQuery: String) {
        recipes = newRecipes
        query = searchQuery
        notifyDataSetChanged() // Notify adapter to refresh the RecyclerView
    }

    // Method to highlight the query in the recipe title
    private fun highlightQuery(text: String, query: String): CharSequence {
        if (query.isEmpty()) {
            return text // If the query is empty, return the plain text
        }

        val spannable = SpannableString(text)
        val startIndex = text.lowercase().indexOf(query.lowercase()) // Find the start index of the query
        if (startIndex >= 0) {
            val endIndex = startIndex + query.length
            spannable.setSpan(
                StyleSpan(android.graphics.Typeface.BOLD), // Make the text bold
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannable
    }

    // ViewHolder class for the RecyclerView
    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dishTitle: TextView = itemView.findViewById(R.id.dishTitle_searchView) // TextView for the recipe title
        val root: View = itemView // Root view of the item
    }
}
