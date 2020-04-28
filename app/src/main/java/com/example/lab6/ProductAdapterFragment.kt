package com.example.lab6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab6.R.layout.recycler_view_list_row
import com.example.lab6.R.layout.recycler_view_list_row_fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_view_list_row.view.*
import kotlinx.android.synthetic.main.recycler_view_list_row_fragment.view.*

class ProductAdapterFragment(private val productlist: ArrayList<ItemCarro>):
    RecyclerView.Adapter<ProductAdapterFragment.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(recycler_view_list_row_fragment,parent,false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productlist[position]
        holder.bindContact(currentItem)
    }

    override fun getItemCount() = productlist.size

    class ProductViewHolder(v: View): RecyclerView.ViewHolder(v){
        private var view: View = v
        private var item5: ItemCarro? = null

        init {

        }

        fun bindContact(item5: ItemCarro){
            this.item5 = item5
            view.textViewNameFragment.text = item5.name
            view.textViewCostFragment.text = "$" + (item5.price.toInt()/item5.cantidad.toInt())
            view.textViewCantidadFragment.text = "Cantidad: " + item5.cantidad
            view.textViewtCostoTotalFragment.text = "Costo total: $" + item5.price
            Picasso.get()
                .load(item5.urlName)    //"https://i.imgur.com/DvpvklR.png"
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(view.imageViewPictureFragment)

            //itemView.setOnClickListener {
              //  clickListener.onItemClicked(item5)
            //}
        }
    }

}

interface OnItemClickListener2{
    fun onItemClicked(item5: ItemCarro)
}