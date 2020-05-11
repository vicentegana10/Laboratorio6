package com.example.lab6

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.fragment_shop.textViewArticleCountFragment
import kotlinx.android.synthetic.main.fragment_shop.textViewTotalAmountFragment
import java.io.File


class CarroActivity : AppCompatActivity(),OnItemClickListener2,dialogListener2 {
    //private var productlistfragment = ArrayList<Item5>()
    private var productlistcarrofinal = mutableListOf<Item5final>()
    private var itemCarroList = mutableListOf<ItemCarro>()
    private var itemCounter2 = 0
    private var total2 = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro)
        //productlistcarrofinal = intent.getParcelableArrayListExtra("value3")

        val file3 = File(this.filesDir, "dataCompradoLista.txt")
        val br3 = file3.bufferedReader()
        val text3:List<String> = br3.readLines()
        for (line in text3){
            var lines = line.split(",")
            productlistcarrofinal.add(Item5final(lines[0],lines[1],lines[2],lines[3],lines[4].toInt()))
        }

        products_recycler_view_fragment.adapter = ProductAdapterFragment(productlistcarrofinal as ArrayList<Item5final>,this) //productlistfragment
        products_recycler_view_fragment.layoutManager = LinearLayoutManager(this)

        itemCounter2 = intent.getStringExtra("value1").toInt()
        total2 = intent.getStringExtra("value2").toInt()
        //textViewArticleCountFragment.text = " Articulos: "+ itemCounter2.toString()
        //textViewTotalAmountFragment.text = "$ "+ total2.toString()
        actualizarCarro()
/*
        val file = applicationContext.assets.open("products.txt") // aca abrir dataCompradoLista

        val br = file.bufferedReader()
        val text:List<String> = br.readLines()
        for (line in text){
            var lines = line.split(",")
            //productlistfragment.add(Item5(lines[0], lines[1], lines[2], lines[3]))
        }
*/
        val file2 = openFileInput("dataCompradoLista.txt")
        val br2 = file2.bufferedReader()
        val text2:List<String> = br2.readLines()
        for (line2 in text2){
            var lines2 = line2.split(",")
            var nameItemCarro : String = lines2[0]
            //item5.price.substring(1,item5.price.length).toInt()
            val priceItemCarro : Int = lines2[1].substring(1,lines2[1].length).toInt()
            var counterExists : Int=0
            itemCarroList.forEach{
                if (it.name==nameItemCarro){
                    counterExists++
                    val cc:Int = it.cantidad.toInt() + 1
                    val pp:Int = it.price.toInt() + priceItemCarro
                    it.cantidad = cc.toString()
                    it.price = pp.toString()
                }
            }
            if (counterExists<1){
                itemCarroList.add(ItemCarro(nameItemCarro,lines2[1].substring(1,lines2[1].length),"1",lines2[2],lines2[3]))
            }
            counterExists=0


        }

    }

    override fun onItemClicked2(item5: Item5final) {
        //Toast.makeText(this, "item apretado"+item5.name, Toast.LENGTH_LONG).show()
        dialogProduct2(item5)
    }
    fun dialogProduct2(item5: Item5final) {
        val dialogProduct2 = DialogProduct2(item5)
        dialogProduct2.show(supportFragmentManager, "dialogProduct2")
    }
    override fun throwToast(item5: Item5final,amount:Int){
        Toast.makeText(this, "item apretado"+item5.name, Toast.LENGTH_LONG).show()
    }
    override fun change(item5: Item5final,amount:Int){
        Toast.makeText(this, "cambiar: "+item5.name+" en "+amount.toString(), Toast.LENGTH_LONG).show()

        //val file = File(this.filesDir, "dataComprado.txt")
        var newAmount= item5.count - amount
        //file.createNewFile()
        itemCounter2-=newAmount
        total2-= (item5.price.toInt()*newAmount)
        //textViewArticleCountFragment.text = " Articulos: "+ itemCounter2.toString()
        //textViewTotalAmountFragment.text = "$ "+ total2.toString()
        //file.writeText(itemCounter2.toString() + "," + total2.toString())


        var ind=0
        for (x in productlistcarrofinal){
            if (x.name==item5.name){
                var newItem = Item5final(x.name,x.price,x.urlName,x.description,amount)
                productlistcarrofinal.set(ind,newItem)
                products_recycler_view_fragment.adapter?.notifyItemChanged(ind)
                //productlistcarrofinal.removeAt(ind)
                //products_recycler_view_fragment.adapter?.notifyItemRemoved(ind)
                break
            }
            ind++
        }
        actualizarCarro()
        // ver cantidad anterior y nueva y ver que hacer

    }
    override fun deleteItem(item5: Item5final){
        Toast.makeText(this, "borrar: "+item5.name, Toast.LENGTH_LONG).show()

        val file = File(this.filesDir, "dataComprado.txt")
        file.createNewFile()
        itemCounter2-=item5.count
        total2-= (item5.price.toInt()*item5.count)
        //textViewArticleCountFragment.text = " Articulos: "+ itemCounter2.toString()
        //textViewTotalAmountFragment.text = "$ "+ total2.toString()
        file.writeText(itemCounter2.toString() + "," + total2.toString())


        var ind=0
        for (x in productlistcarrofinal){
            if (x.name==item5.name){
                productlistcarrofinal.removeAt(ind)
                products_recycler_view_fragment.adapter?.notifyItemRemoved(ind)
                break
            }
            ind++
        }
        actualizarCarro()

    }

    fun saveChanges(view: View){
        val file1 = File(this.filesDir, "dataCompradoLista.txt")
        file1.createNewFile()
        file1.writeText("")
        for (x in productlistcarrofinal){
            file1.appendText(x.name + "," + x.price + ","+x.urlName+","+x.description+","+x.count.toString()+"\n")
        }
    }

    fun actualizarCarro(){
        var itemC= 0
        var totalC= 0
        for (x in productlistcarrofinal){
            itemC+= x.count
            totalC+=x.count*x.price.toInt()
        }
        textViewArticleCountFragment.text =itemC.toString() + " Articulos"
        textViewTotalAmountFragment.text = "$ "+ totalC.toString()
    }





}
@Parcelize
data class ItemCarro(var name:String, var price: String, var cantidad:String, var urlName: String, var description: String): Parcelable

