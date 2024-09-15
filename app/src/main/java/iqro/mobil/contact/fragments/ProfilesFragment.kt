package iqro.mobil.contact.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iqro.mobil.contact.adapters.CustomABC
import iqro.mobil.contact.adapters.CustomAdapterA
import iqro.mobil.contact.adapters.CustomAdapterB
import iqro.mobil.contact.ItemClick
import iqro.mobil.contact.dataClass.ProfileAData
import iqro.mobil.contact.dataClass.ProfileBData
import iqro.mobil.contact.databinding.ProfilesFragmentBinding
import iqro.mobil.contact.roomdb.ContactsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class ProfilesFragment : Fragment() {
    private var _binding: ProfilesFragmentBinding? = null
    private val binding get() = _binding!!
    private var listB=ArrayList<ProfileBData>()
    private lateinit var customAdapterB: CustomAdapterB

    val requestCallLogPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted -> }
    val requestSmsPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}
    val requestCallPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    @SuppressLint("SuspiciousIndentation")
    override fun onAttach(context: Context) {
        super.onAttach(context)



    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = ProfilesFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("Recycle", "Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photoUri="content://com.android.contacts/display_photo/1"
        binding.imageView.setImageURI(Uri.parse(photoUri))
        val list= mutableListOf<ProfileAData>()

        val listABC = mutableListOf<Char>()

        for (i in 'A'..'Z') {
            listABC.add(i)
        }




//                val cursor = contactDbManager.fitch()
//        if (cursor != null) {
//            if(cursor.moveToFirst()) {
//                val namePosition = cursor.getColumnIndex("name")
//                val numberPosition = cursor.getColumnIndex("number")
//                do {
//                    val name = cursor.getString(namePosition)
//                    val number = cursor.getString(numberPosition)
//                    val imageUri=cursor.getString(cursor.getColumnIndex("image"))
//                    listB.add(ProfileBData(imageUri,name,number))
//                } while (cursor.moveToNext())
//            }
//        }



        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CALL_LOG
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val cursor = context?.contentResolver?.query(CallLog.Calls.CONTENT_URI, null, null, null, null)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    val numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER)
                    val typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE)
                    val dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE)
                    val nameIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)
                    val durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION)

                    val tlist= mutableListOf<ProfileAData>()
                    do {
                        val number = cursor.getString(numberIndex)
                        val type = cursor.getInt(typeIndex)
                        val date = cursor.getLong(dateIndex)
                        val duration = cursor.getLong(durationIndex)
                        var name = cursor.getString(nameIndex)
                        if (name==null){
                            name=number
                        }
                        tlist.add(ProfileAData(name,date,number))
                    } while (cursor.moveToNext())

                    if (tlist.isNotEmpty()){
                        if (tlist[0].time<tlist[1].time||tlist.size<2){
                            var index=tlist.size-1
                            for (i in 0..<tlist.size){
                                list.add(tlist[index])
                                index--
                            }
                        }else{
                            for (i in 0..<tlist.size){
                                list.add(tlist[i])
                            }
                        }
                    }

                }
            }
        } else {
            requestCallLogPermission.launch(Manifest.permission.READ_CALL_LOG)
        }







        val customAdapterA = context?.let { CustomAdapterA(it, list) }
        val layM = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewA.apply {
            adapter = customAdapterA
            layoutManager = layM
        }




        lifecycleScope.launch {
            val symbolList = contactsRead1().keys.toList()
            val customABC = CustomABC(contactsRead1(),symbolList)
            binding.conTv.text=contactsRead1().size.toString()+" ta kontaktlar"
            withContext(Dispatchers.Main){
                binding.recyclerViewABC.adapter=customABC
                binding.recyclerViewABC.layoutManager=LinearLayoutManager(requireContext())
                customABC.setListener(object :ItemClick{
                    override fun profileClick(adapterPosition: Int) {

                    }

                    override fun intentCall(intent: Intent) {
                        if (ContextCompat.checkSelfPermission(
                                requireContext(),
                                Manifest.permission.CALL_PHONE
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            startActivity(intent)
                        } else {
                            requestCallPermission.launch(Manifest.permission.CALL_PHONE)

                        }
                    }

                    override fun intentSms(intent: Intent) {
                        if (ContextCompat.checkSelfPermission(
                                requireContext(),
                                Manifest.permission.SEND_SMS
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            startActivity(intent)
                        } else {
                            requestSmsPermission.launch(Manifest.permission.SEND_SMS)
                        }
                    }

                    override fun getData(name: String, number: String, image: String) {
                        val args=ProfilesFragmentDirections.actionProfilesFragmentToProfileFragment(name,number,image)
                        findNavController().navigate(args)
                    }
                })
            }
        }














        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filterList(p0)
                return true
            }
        })


        binding.moreBtn.setOnClickListener {
            val args=ProfilesFragmentDirections.actionProfilesFragmentToCallLogFragment()
            findNavController().navigate(args)
        }


    }
   private fun filterList(query:String?){
        if (query!=null){
            val filteredList=ArrayList<ProfileBData>()
            for (i in 0..listB.size-1){
                val image=listB[i].image
                val name=listB[i].name
                val number=listB[i].number
                Log.d("err", "name:$name ")
                if ((name).lowercase(Locale.ROOT).contains(query)){
                    if(listB[i].image=="0"){
                        filteredList.add(ProfileBData(name,number))
                    }else{
                        filteredList.add(ProfileBData(image=image,name=name,number=number))
                    }
                }

            }

            if (filteredList.isEmpty()){
                Toast.makeText(context, "Malumot topilmadi", Toast.LENGTH_SHORT).show()
            }else{
                customAdapterB.setFilteredList(filteredList)
            }
        }

    }


    private fun checkCallPermission(number: String) {

    }

    private fun checkSmsPermission(number: String) {

    }



    private fun readCallLog() {

    }



    @RequiresApi(Build.VERSION_CODES.N)
    private suspend fun contactsRead1():Map<Char,List<ProfileBData>>{

            val map = LinkedHashMap<Char, List<ProfileBData>>()

          val  database=ContactsDatabase.getInstance(requireContext())
            val contactList = database.contactsDao().getContacts()
            for (i in contactList) {
                val name = i.name
                val number = i.number
                val imageUri = i.image
                val firstChar = (name.trim())[0].uppercaseChar()
                if (firstChar in 'A'..'Z') {
                    val list = map.getOrDefault(firstChar, mutableListOf())
                    (list as MutableList).add(ProfileBData(imageUri, name, number))
                    map[firstChar] = list
                }
            }

            return map
    }




}