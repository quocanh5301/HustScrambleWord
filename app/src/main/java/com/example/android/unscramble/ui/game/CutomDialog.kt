package com.example.android.unscramble.ui.game

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.android.unscramble.R

class CutomDialog(private val dialogBtn: DialogBtn) {
    val alertDialog: AlertDialog? = null

    fun show(context: Context, title: String, message: String) {

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)

        val titleTv = dialog.findViewById(R.id.tv_title) as TextView
        titleTv.text = title

        val messTv = dialog.findViewById(R.id.tv_msg) as TextView
        messTv.text = message

        val yesBtn = dialog.findViewById(R.id.button_confirm) as Button
        yesBtn.setOnClickListener {
            dialogBtn.onConfirm()
            dialog.dismiss()
        }

        val noBtn = dialog.findViewById(R.id.button_cancel) as Button
        noBtn.setOnClickListener {
            dialogBtn.onDeny()
            dialog.dismiss()
        }

//        dialog.setOnDismissListener(DialogInterface.OnDismissListener {
//            //TODO ondismiss logic
//        })

        dialog.show()

//    val dialog = AlertDialog.Builder(context)

//    dialog.apply {
//        //setIcon(R.drawable.ic_hello)
//        setTitle("Hello")
//        setMessage("I just wanted to greet you. I hope you are doing great!")
//        setPositiveButton("Positive") { _: DialogInterface?, h: Int ->
//            h.
//            Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
//        }
//        setNegativeButton("Negative") { _, _ ->
//            Toast.makeText(context, "Negative", Toast.LENGTH_SHORT).show()
//        }
//        setNeutralButton("Neutral") { _, _ ->
//            Toast.makeText(context, "Neutral", Toast.LENGTH_SHORT).show()
//        }
//        setOnDismissListener {
//            Toast.makeText(context, "Hello!!!", Toast.LENGTH_SHORT).show()
//        }
//
//    }.create().show()
    }
}