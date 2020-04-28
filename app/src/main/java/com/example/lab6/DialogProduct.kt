package com.example.lab6

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_dialog_product.*
import kotlinx.android.synthetic.main.fragment_dialog_product.view.*


class DialogProduct(item5: Item5) : DialogFragment() {

    var item5:Item5 = item5
    private var listener:dialogListener? = null
    //var dialogAmount : Int = editTextAmount.toString().toInt()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            //new

            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            var view: View = inflater.inflate(R.layout.fragment_dialog_product,null)
            view.dialogTextName.text = "Nombre: " + item5.name
            val priceMul : Int = item5.price.substring(1,item5.price.length).toInt() // * dialogAmount
            view.dialogTextPrice.text = "Precio: $" + priceMul.toString()
            Picasso.get()
                .load(item5.urlName)    //"http://i.imgur.com/DvpvklR.png" de ejemplo
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(view.dialogImageView)
            val editText  = view.findViewById<EditText>(R.id.editTextAmount)
            //val txtt : EditText = view.editTextAmount
            //val amountEditText : Int = txtt.text.toString().toInt()


            builder.setView(view)
                .setPositiveButton("Confirmar") { dialog, id ->
                    //Toast.makeText(context,editText.text.toString(),Toast.LENGTH_SHORT).show()
                    var amountInDialog :Int = editText.text.toString().toInt()
                    if (amountInDialog>10){amountInDialog=10}
                    listener!!.addProduct(item5,amountInDialog) // dialogAmount en vez de 1
                    var a:Int=0
                }
                .setNegativeButton("Cancelar") { dialog, id ->
                    getDialog()?.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) { // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
        super.onAttach(context)
        if (context is dialogListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " implementar listener del fragment")
        }
    }

    fun onemore(){

        //val inflater = requireActivity().layoutInflater;
        //var view: View = inflater.inflate(R.layout.fragment_dialog_product,null)
        //val actualNumber : Int = view.buttonOneMore.text.toString().toInt() +1
        //view.buttonOneMore.text = actualNumber.toString()
        //dialogAmount= actualNumber

    }

}

interface dialogListener{
    fun addProduct(item5: Item5,amount:Int)
}