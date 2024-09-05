package iqro.mobil.contact.fragments

import android.os.Build
import android.os.Bundle
import android.provider.CallLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import iqro.mobil.contact.adapters.CallLogABC
import iqro.mobil.contact.dataClass.CallLogData
import iqro.mobil.contact.databinding.CallLogFragmentBinding
import java.text.SimpleDateFormat
import java.util.Locale

class CallLogFragment : Fragment() {
    private var _binding: CallLogFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = CallLogFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val map = LinkedHashMap<Long, MutableList<CallLogData>>() // MutableList ishlatamiz
        val cursor = context?.contentResolver?.query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC") // Qo'ng'iroqlarni sanasi bo'yicha tartiblash
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE)
                val numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER)
                val nameIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)
                val typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE)
                val durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION)
                val simIndex = cursor.getColumnIndex(CallLog.Calls.PHONE_ACCOUNT_ID)

                do {
                    val dateA = cursor.getLong(dateIndex)
                    val day = SimpleDateFormat("dd", Locale.getDefault()).format(dateA).toLong()
                    val number = cursor.getString(numberIndex)
                    val name = cursor.getString(nameIndex) ?: number
                    val type = cursor.getInt(typeIndex)
                    val duration = cursor.getLong(durationIndex)
                    val simId = cursor.getString(simIndex)
                    val simCard = if (simId?.endsWith("1F") == true) {
                        "0"
                    } else if (simId?.endsWith("2F") == true) {
                        "1"
                    } else {
                        "2"
                    }

                    val callLogData = CallLogData(name, number, type, dateA, duration, simCard)
                    if (map.containsKey(day)) {
                        map[day]?.add(callLogData) // add funksiyasi MutableList uchun ishlaydi
                    } else {
                        map[day] = mutableListOf(callLogData)
                    }
                } while (cursor.moveToNext())
            }
            cursor.close()
        }



//        val map = LinkedHashMap<String, MutableList<CallLogData>>() // MutableList ishlatamiz
//        val dateList= mutableListOf<String>()
//        val dList= mutableListOf<CallLogData>()
//        val cursor = context?.contentResolver?.query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC") // Qo'ng'iroqlarni sanasi bo'yicha tartiblash
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                val dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE)
//                val numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER)
//                val nameIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)
//                val typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE)
//                val durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION)
//                val simIndex = cursor.getColumnIndex(CallLog.Calls.PHONE_ACCOUNT_ID)
//
//                do {
//                    val date=cursor.getLong(dateIndex)
//                    val formatDate=SimpleDateFormat("dd",Locale.getDefault()).format(Date(date))
//                    dateList.add(formatDate)
//                }while (cursor.moveToNext())
//                cursor.moveToFirst()
//                do {
//                    val data=cursor.getLong(dateIndex)
//                    val duration=cursor.getLong(durationIndex)
//                    val number=cursor.getString(numberIndex)
//                    val name=cursor.getString(nameIndex)?:number
//                    val type=cursor.getInt(typeIndex)
//                    val simId=cursor.getString(simIndex)
//                    dList.add(CallLogData(name,number,type,data,duration,simId))
//                }while (cursor.moveToNext())
//
//                for (i in 0..<dateList.size){
//                    val list= mutableListOf<CallLogData>()
//                    for (j in 0..<dList.size){
//                        if (dateList[i]==SimpleDateFormat("dd",Locale.getDefault()).format(Date(dList[j].date))){
//                            list.add(CallLogData(dList[j].name,dList[j].number,dList[j].type,dList[j].date,dList[j].duration,dList[j].simId))
//                        }
//                    }
//                    map[dateList[i]]=list
//                }
//
//            }
//            cursor.close()
//        }





        val dataList = map.keys.toList()
        val callLogABC=CallLogABC(map,dataList)
        binding.callLogRecyclerView.apply {
            adapter=callLogABC
            layoutManager=LinearLayoutManager(requireContext())
        }


    }

}