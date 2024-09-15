package iqro.mobil.contact.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Contacts")
data class ContactsEntity(
  @PrimaryKey(true)  val Id:Int?=null,
    val name:String,
    val number:String,
    val image:String
)
