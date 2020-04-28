package com.example.lab6


import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_shop.*


class CarroActivity : AppCompatActivity() {
    private var productlistfragment = ArrayList<Item5>()
    private var itemCarroList = mutableListOf<ItemCarro>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro)
        products_recycler_view_fragment.adapter = ProductAdapterFragment(itemCarroList as ArrayList<ItemCarro>) //productlistfragment
        products_recycler_view_fragment.layoutManager = LinearLayoutManager(this)

        val savedExtra = intent.getStringExtra("value1")
        val savedExtra2 = intent.getStringExtra("value2")
        textViewArticleCountFragment.text = " Articulos: "+ savedExtra
        textViewTotalAmountFragment.text = "$ "+ savedExtra2

        val file = applicationContext.assets.open("products.txt") // aca abrir dataCompradoLista

        val br = file.bufferedReader()
        val text:List<String> = br.readLines()
        for (line in text){
            var lines = line.split(",")
            productlistfragment.add(Item5(lines[0],lines[1],lines[2]))
        }

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
                itemCarroList.add(ItemCarro(nameItemCarro,lines2[1].substring(1,lines2[1].length),"1",lines2[2]))
            }
            counterExists=0

            //productlistfragment.add(Item5(lines[0],lines[1],lines[2]))
        }

    }

}
@Parcelize
data class ItemCarro(var name:String, var price: String, var cantidad:String, var urlName: String): Parcelable

