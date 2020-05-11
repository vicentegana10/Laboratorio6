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

class ProductAdapterFragment(private val productlist: ArrayList<Item5final>, val itemClickListener2: CarroActivity):
    RecyclerView.Adapter<ProductAdapterFragment.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(recycler_view_list_row_fragment,parent,false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productlist[position]
        holder.bindContact(currentItem,itemClickListener2)
    }

    override fun getItemCount() = productlist.size

    class ProductViewHolder(v: View): RecyclerView.ViewHolder(v){
        private var view: View = v
        private var item5: Item5final? = null

        init {

        }

        fun bindContact(item5: Item5final,clickListener2: OnItemClickListener2){
            this.item5 = item5
            view.textViewNameFragment.text = item5.name
            view.textViewCostFragment.text = "$" + item5.price.toInt()
            view.textViewCantidadFragment.text = "Cantidad: " + item5.count
            view.textViewtCostoTotalFragment.text = "Costo total: $" + (item5.price.toInt()*item5.count).toString()
            Picasso.get()
                .load(item5.urlName)    //"https://i.imgur.com/DvpvklR.png"
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(view.imageViewPictureFragment)

            itemView.buttonEdit.setOnClickListener {
                clickListener2.onItemClicked2(item5)
            }
        }
    }

}

interface OnItemClickListener2{
    fun onItemClicked2(item5: Item5final)
}