package `in`.indianmeme.testproject

import `in`.indianmeme.testproject.Data.ImagesItem
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_layout.view.*

class AdapterImage(
    private var context: Context,
    var data: MutableList<ImagesItem>,
    var callBack: ItemCallBack
) :
    RecyclerView.Adapter<AdapterImage.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item = data[position]
        Glide.with(context).load(item.xtImage).into(holder.itemView.recycler_image)
    }

    fun updateData(list: List<ImagesItem>) {
        val size = data.size
        data.addAll(list)
        notifyItemInserted(size)
    }

    interface ItemCallBack {

        fun onBackActivity(item: ImagesItem)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                callBack.onBackActivity(data[adapterPosition])
            }
        }
    }
}