package com.example.mysqlite

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.mysqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding
    private lateinit var friendsDBHelper : mySQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        friendsDBHelper = mySQLiteHelper(this)
        b.btnguardado.setOnClickListener {
            if (b.etName.text.isNotBlank() &&
                b.etMail.text.isNotBlank())    {
                friendsDBHelper.addData(b.etName.text.toString(),
                                        b.etMail.text.toString())
                b.etName.text.clear()
                b.etMail.text.clear()
                toast("guardado")
            } else {
                toast("no se ha podido guardar, ", Toast.LENGTH_SHORT )
            }

        }

        b.btnConsultar.setOnClickListener {
            b.tvConsultar.text = ""
            val db : SQLiteDatabase = friendsDBHelper.readableDatabase
            val cursor = db.rawQuery(
                "SELECT * FROM friends", null)

            if (cursor.moveToFirst()) {
                do {
                    b.tvConsultar.append(cursor.getInt(0).toString()+ ":")
                    b.tvConsultar.append(cursor.getString(1).toString()+ ",")
                    b.tvConsultar.append(cursor.getString(2).toString() + "\n")

                } while (cursor.moveToNext())
            }
        }

        b.btnBorrar.setOnClickListener {
           var affected = 0
            if (b.etId.text.isNotBlank()) {
                affected = friendsDBHelper.deleteData(
                            b.etId.text.toString().toInt())
                b.etId.text.clear()
                b.etName.text.clear()
                b.etMail.text.clear()
                hideKeyBoard()
                toast("¡Borrado!")
            } else {
                 toast("Datos borrados: $affected", Toast.LENGTH_LONG)
        }
            b.btnModificar.setOnClickListener {
                if (b.etId.text.isNotBlank() &&
                    b.etName.text.isNotBlank() &&
                    b.etMail.text.isNotBlank()){
                    friendsDBHelper.updateData(
                        b.etId.text.toString().toInt(),
                        b.etName.text.toString(),
                        b.etMail.text.toString())
                    b.etId.text.clear()
                    b.etName.text.clear()
                    b.etMail.text.clear()
                    hideKeyBoard()
                    toast("¡Modificado!")

                } else {
                    toast("No permitido campos vacíos", Toast.LENGTH_LONG)
                }
            }
        }

    }
    fun hideKeyBoard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(b.constraintLayoutMain.windowToken, 0)
    }


}