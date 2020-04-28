package com.example.lab6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab6.R.layout.recycler_view_list_row
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_view_list_row.view.*

class ProductAdapter(private val productlist: ArrayList<Item5>, val itemClickListener: MainActivity):
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(recycler_view_list_row,parent,false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productlist[position]
        holder.bindContact(currentItem,itemClickListener)
    }

    override fun getItemCount() = productlist.size

    class ProductViewHolder(v: View): RecyclerView.ViewHolder(v){
        private var view: View = v
        private var item5: Item5? = null

        init {

        }

        fun bindContact(item5: Item5, clickListener: OnItemClickListener){
            this.item5 = item5
            view.textViewName.text = item5.name
            view.textViewCost.text = "$" + item5.price
            Picasso.get()
                .load(item5.urlName)    //"http://i.imgur.com/DvpvklR.png" de ejemplo
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(view.imageViewPicture)

            itemView.setOnClickListener {
                clickListener.onItemClicked(item5)
            }
        }
    }

}

interface OnItemClickListener{
    fun onItemClicked(item5: Item5)
}