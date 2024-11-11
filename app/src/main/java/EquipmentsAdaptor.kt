import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.hashdroid.recipeapp.Equipment

class EquipmentsAdaptor (private val equipments:List<Equipment>):RecyclerView.Adapter<EquipmentsAdaptor.EViewHolder>(){
    class EViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var equipment_image:ImageView=view.findViewById(R.id.equipment_image)
        var equipment_name:TextView=view.findViewById(R.id.equipment_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.equipments_rv,parent,false)
        return EViewHolder(view)
    }



    override fun onBindViewHolder(holder: EViewHolder, position: Int) {
        val equipment=equipments[position]
        holder.equipment_name.text=equipment.name

        Glide.with(holder.equipment_image.context)
            .load(equipment.image)
            .into(holder.equipment_image)
    }

    override fun getItemCount(): Int {
        return equipments.size
    }

}