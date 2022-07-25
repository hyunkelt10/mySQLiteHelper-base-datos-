package com.example.mysqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class mySQLiteHelper (context: Context) :
        SQLiteOpenHelper(context, "addressBook.db", null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val commandCreate = "CREATE TABLE friends(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT)"
        db!!.execSQL(commandCreate)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {




    }

    fun addData (name: String, email: String) {
        val data = ContentValues ()
        data.put("name",name)
        data.put("email", email)
        //ABRO LA DB EN MODO ESCRITURA

        val db = this.writableDatabase
        db.insert("friends", null, data)
        db.close()
    }

    fun deleteData (id:Int) : Int {
        val args = arrayOf(id.toString())
        val db = this.writableDatabase
        val affectedRows = db.delete("friends", "_id = ?" , args)
        db.close()
        return affectedRows
    }


    fun updateData (id:Int, name: String, email: String) {
        val args = arrayOf(id.toString())

        // ContentValues tiene una estructura de tipo Map()
        val data = ContentValues()
        data.put("name", name)
        data.put("email", email)
        // Abro la DB en modo ESCRITURA
        val db = this.writableDatabase
        // La ejecución de este comando devuelve el número de registros afectados
        db.update("friends", data, "_id = ?",args)
        db.close()
    }


}
