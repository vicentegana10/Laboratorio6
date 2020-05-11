package com.example.lab6




import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*


class MainActivity : AppCompatActivity(),OnItemClickListener,dialogListener{

    //private var productlist = ArrayList<Item5>()
    private var productlistfinal = ArrayList<Item5final>()
    private var soldproductfinal = ArrayList<Item5final>()
    private var itemCounter = 0
    private var total = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        products_recycler_view.adapter = ProductAdapter(productlistfinal,this)
        products_recycler_view.layoutManager = LinearLayoutManager(this)



        val file = applicationContext.assets.open("products.txt")
        val br = file.bufferedReader()
        val text:List<String> = br.readLines()
        for (line in text){
            var lines = line.split(",")

            //productlist.add(Item5(lines[0],lines[1],lines[2],lines[3]))
            val precioBien = lines[1].substring(1,lines[1].length)
            productlistfinal.add(Item5final(lines[0],precioBien,lines[2],lines[3],0))

        }
    }

    override fun onResume() {
        super.onResume()
        soldproductfinal.clear()
        val file1 = File(this.filesDir, "dataCompradoLista.txt")
        file1.createNewFile()
        var br1 = file1.bufferedReader()
        val text1 :List<String> = br1.readLines()
        for (line1 in text1){
            var lines1 = line1.split(",")
            soldproductfinal.add(Item5final(lines1[0],lines1[1],lines1[2],lines1[3],lines1[4].toInt()))
        }

        println("on resumeee")
        actualizar()

    }

    fun actualizar(){
        var itemC= 0
        var totalC= 0
        for (x in soldproductfinal){
            itemC+= x.count
            totalC+=x.count*x.price.toInt()
        }
        textViewArticleCount.text =itemC.toString() + " Articulos"
        textViewTotalAmount.text = "$ "+ totalC.toString()
    }


    override fun onItemClicked(item5: Item5final) {
        dialogProduct(item5)
    }

    fun dialogProduct(item5: Item5final) {
        val dialogProduct = DialogProduct(item5)
        dialogProduct.show(supportFragmentManager, "dialogProduct")
    }

    override fun addProductfinal(item5: Item5final,amount:Int){
        itemCounter+= amount
        total += item5.price.toInt()*amount
        textViewArticleCount.text = " Articulos: "+itemCounter.toString()
        textViewTotalAmount.text = "$ "+ total.toString()
        if (item5 != null) {
            for (x in productlistfinal) {
                if (x.name == item5.name) {
                    x.count += amount
                }
            }
            Toast.makeText(this, "Agregado al carro", Toast.LENGTH_LONG).show()
            var esta=0
            for (x in soldproductfinal){
                if (x.name==item5.name){
                    esta++
                    x.count+=amount
                }
            }
            if (esta==0){
                soldproductfinal.add(Item5final(item5.name,item5.price,item5.urlName,item5.description,amount))
            }
        }
    }



     fun openCarro(view: View){
        val file1 = File(this.filesDir, "dataCompradoLista.txt")
        file1.createNewFile()
         file1.writeText("")
         for (x in soldproductfinal){
             file1.appendText(x.name + "," + x.price + ","+x.urlName+","+x.description+","+x.count.toString()+"\n")
         }
        val intent = Intent(view.context, CarroActivity::class.java)
        view.context.startActivity(intent)
    }
}
/*
@Parcelize
data class Item5(val name:String, val price: String, val urlName:String,val description: String):Parcelable
*/
@Parcelize
data class Item5final(val name:String, val price: String, val urlName:String, val description: String, var count:Int):Parcelable

