package iqro.mobil.contact.contactData

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContactSQLiteHelper(context: Context):SQLiteOpenHelper(context,"MyContactDb",null,2) {

    companion object{
        const val name="name"
        const val number="number"
        const val contact="contact"
        const val imageUri="image"

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table $contact(name text,number text,image text);")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("drop table if exists $contact;")
        onCreate(p0)
    }

}