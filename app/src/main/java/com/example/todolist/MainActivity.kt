package com.example.todolist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var editTextText :EditText
    lateinit var button:Button
    lateinit var listView: ListView
    var itemList = ArrayList<String>()
    var fileHelper = FileHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextText = findViewById(R.id.editTextText)
        button = findViewById(R.id.button)
        listView = findViewById(R.id.listView)
        itemList = fileHelper.readData(this)

        var arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,itemList)

        listView.adapter = arrayAdapter

        button.setOnClickListener{
            var itemName : String = editTextText.text.toString()
            itemList.add(itemName)
            editTextText.setText("")
            fileHelper.writeData(itemList,applicationContext)
            arrayAdapter.notifyDataSetChanged()
        }

        listView.setOnItemClickListener{adapterView,view,position,l ->
            var alert = AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Do you want to delete this item from the list?")
            alert.setCancelable(false)
            alert.setNegativeButton("No",DialogInterface.OnClickListener{ dialogInterface , i ->
                dialogInterface.cancel()
            })
            alert.setPositiveButton("Yes",DialogInterface.OnClickListener{ dialogInterface, i ->
                itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(itemList,applicationContext)
            })
            alert.create().show()
        }
    }
}