package com.example.lab6




import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*


class MainActivity : AppCompatActivity(),OnItemClickListener,dialogListener{
    private val FILE_NAME = "example.txt"

    private var productlist = ArrayList<Item5>()
    private var soldproduct = ArrayList<Item5>()
    private var itemCounter = 0
    private var total = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        products_recycler_view.adapter = ProductAdapter(productlist,this)
        products_recycler_view.layoutManager = LinearLayoutManager(this)
        val file1 = File(this.filesDir, "dataCompradoLista.txt")
        file1.createNewFile()

        val file = applicationContext.assets.open("products.txt")
        val br = file.bufferedReader()
        val text:List<String> = br.readLines()
        for (line in text){
            var lines = line.split(",")

            productlist.add(Item5(lines[0],lines[1],lines[2]))

        }
    }

    override fun onResume() {
        super.onResume()
        println("on resumeee")
        val file = File(this.filesDir, "dataComprado.txt")
        file.createNewFile()
        FileInputStream(file).bufferedReader().use {
            it.forEachLine {
                var value = it.split(",")
                itemCounter =  value[0].toInt()
                total = value[1].toInt()
                textViewArticleCount.text = value[0] + " Articulos"
                textViewTotalAmount.text = "$ "+ value[1]
            }
        }

    }


    override fun onItemClicked(item5: Item5) {
        dialogProduct(item5)
    }

    fun dialogProduct(item5: Item5) {
        val dialogProduct = DialogProduct(item5)
        dialogProduct.show(supportFragmentManager, "dialogProduct")
    }

    override fun addProduct(item5: Item5,amount:Int){
        soldproduct.add(item5)
        itemCounter+= amount
        total += item5.price.substring(1,item5.price.length).toInt()*amount
        textViewArticleCount.text = " Articulos: "+itemCounter.toString()
        textViewTotalAmount.text = "$ "+ total.toString()
        var counter :Int=0
        while(counter<amount) {

            if (item5 != null) {
                val file = File(this.filesDir, "dataComprado.txt")
                file.createNewFile()
                file.writeText(itemCounter.toString() + "," + total.toString())
                val file2 = File(this.filesDir, "dataCompradoLista.txt")

                file2.appendText(item5.name + "," + item5.price + ","+item5.urlName+"\n")

                Toast.makeText(this, "Agregado al carro", Toast.LENGTH_LONG).show()
                counter++

            }
        }
    }
    fun save() {
        val text: String = "ejemplo"
        var fos: FileOutputStream? = null
        try {
            fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            fos.write(text.toByteArray())
            //mEditText.getText().clear()
            //Toast.makeText(
              //  this, "Saved to $filesDir/$FILE_NAME",
                //Toast.LENGTH_LONG
            //).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
    fun load() {
        var fis: FileInputStream? = null
        try {
            fis = openFileInput(FILE_NAME)
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            val sb = StringBuilder()
            var text: String?
            while (br.readLine().also { text = it } != null) {
                sb.append(text).append("\n")
            }
           // mEditText.setText(sb.toString())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fis != null) {
                try {
                    fis.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun openFragment(view: View){
        //val fm : FragmentManager = supportFragmentManager
        //val fragment : ShopFragment = ShopFragment.newInstance()
        //fm.beginTransaction().replace(R.id.fragment,fragment).commit()

        //supportFragmentManager.beginTransaction()
          //  .add(R.id.root_layout, ProductFragment.newInstance(), "productList")
            //.commit()
        val intent = Intent(view.context, CarroActivity::class.java)
        intent.putExtra("value1", itemCounter.toString());
        intent.putExtra("value2", total.toString());
        view.context.startActivity(intent)

    }
}

@Parcelize
data class Item5(val name:String, val price: String, val urlName:String):Parcelable

data class Product(val name:String,val price:String,val url: String) {}
