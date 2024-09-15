package iqro.mobil.contact.roomdb

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database([ContactsEntity::class], version = 2, autoMigrations = [AutoMigration(1,2)])
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactsDao(): ContactsDao

    companion object {
        val INSTANCE: ContactsDatabase? = null
        fun getInstance(context: Context): ContactsDatabase {
            return INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                ContactsDatabase::class.java,
                "Contacts.db"
            ).build()
        }
    }

}