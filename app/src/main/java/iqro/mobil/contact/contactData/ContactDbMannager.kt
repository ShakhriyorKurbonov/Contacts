package iqro.mobil.contact.contactData

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room

class ContactDbManager(val context: Context) {

    private lateinit var contactSQLiteHelper: ContactSQLiteHelper
    private lateinit var database: SQLiteDatabase


    fun onCreate() {
        contactSQLiteHelper = ContactSQLiteHelper(context)
        database = contactSQLiteHelper.writableDatabase
    }

    fun insert(name: String, number: String,imageUri:String) {
        val contentValues = ContentValues()
        contentValues.put(ContactSQLiteHelper.name, name)
        contentValues.put(ContactSQLiteHelper.number, number)
        contentValues.put(ContactSQLiteHelper.imageUri,imageUri)
        database.insert(ContactSQLiteHelper.contact, null, contentValues)
    }

    fun fitch(): Cursor? {
        val cursor = database.query(
            ContactSQLiteHelper.contact,
            arrayOf( ContactSQLiteHelper.name, ContactSQLiteHelper.number,ContactSQLiteHelper.imageUri),
            null,
            null,
            null,
            null,
            null,
            null,

            )
        return if (cursor.moveToFirst()) {
            cursor
        } else {
            null
        }
    }


    fun update(name: String, number: Int): Int {
        val contentValues = ContentValues()
        contentValues.put(ContactSQLiteHelper.name, name)
        contentValues.put(ContactSQLiteHelper.number, number)

        return database.update(
            ContactSQLiteHelper.contact,
            contentValues,
            "${ContactSQLiteHelper.name}=$name",
            arrayOf(ContactSQLiteHelper.name, ContactSQLiteHelper.number)
        )
    }

    fun delete(name: String){
        database.delete(ContactSQLiteHelper.contact,"${ContactSQLiteHelper.name}=$name",null)
    }


}