import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nfc.R
import com.example.nfc.hospital.patientdata.medicalReport.Photos

class ImageAdapterString(private val imageList: ArrayList<Photos>, private val context: Context) :
    RecyclerView.Adapter<ImageAdapterString.ViewHolder>() {

    private val selectedItems = HashSet<Int>()

    // Method to remove an item from the imageList
    fun removeItem(position: Int) {
        imageList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var images = imageList.get(position)
        // loading the images from the position
        Glide.with(holder.itemView.context).load(images.url).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView

        init {
            imageView = itemView.findViewById<ImageView>(R.id.item)
            // Set a click listener to handle item removal
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    toggleSelection(adapterPosition)
                }
            }
        }

        private fun toggleSelection(position: Int) {
            if (selectedItems.contains(position)) {
                selectedItems.remove(position)
            } else {
                selectedItems.add(position)
            }
            notifyItemChanged(position)
        }
    }

    fun getSelectedItems(): Set<Int> {
        return selectedItems
    }

    fun clearSelection() {
        selectedItems.clear()
        notifyDataSetChanged()
    }
}
