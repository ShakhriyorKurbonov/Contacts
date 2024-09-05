package iqro.mobil.contact

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.util.getColumnIndex
import iqro.mobil.contact.contactData.ContactDbManager
import iqro.mobil.contact.databinding.ActivityMainBinding
import iqro.mobil.contact.databinding.ProfilesFragmentBinding
import java.util.Locale
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var myDbManager: ContactDbManager
    private lateinit var binding: ActivityMainBinding



    val requestContactsReadPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                contactsRead()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDbManager = ContactDbManager(this)
        myDbManager.onCreate()



        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            contactsRead()
        } else {
            requestContactsReadPermission.launch(Manifest.permission.READ_CONTACTS)
        }





    }


    @SuppressLint("Range")
    fun contactsRead() {
        val contacts = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (contacts != null) {
            if (contacts.moveToFirst()) {
                do {
                    val name =
                        contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val number =
                        contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val photoUri =
                        contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))

                    if (photoUri != null) {
                        myDbManager.insert(name, number, photoUri)
                    } else {
                        myDbManager.insert(name, number, "0")
                    }
                } while (contacts.moveToNext())
                contacts.close()
            }
        }
    }


}