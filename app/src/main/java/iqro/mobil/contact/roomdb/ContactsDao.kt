package iqro.mobil.contact.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertContacts(contactsEntity: ContactsEntity)

    @Delete
  suspend  fun contactsDelete(contactsEntity: ContactsEntity)

    @Update
   suspend fun contactsUpdate(contactsEntity: ContactsEntity)

    @Query("select * from Contacts")
   suspend fun getContacts():List<ContactsEntity>


}