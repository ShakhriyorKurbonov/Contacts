package iqro.mobil.contact.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import iqro.mobil.contact.dataClass.CallLogData
import iqro.mobil.contact.databinding.CallLogAbcBinding

class CallLogABC(val map: LinkedHashMap<Long, MutableList<CallLogData>>, val dataList: List<Long>):Adapter<CallLogABC.CallLogViewHolder>() {
    inner class CallLogViewHolder(val binding: CallLogAbcBinding):ViewHolder(binding.root){
        fun setBind(contactsList:List<CallLogData>, dataList: Long){
            binding.dateTv.text= dataList.toString()
            val callLogAdapter=CallLogAdapter(contactsList)
            binding.recyclerViewCallLog.apply {
                adapter=callLogAdapter
                layoutManager=LinearLayoutManager(binding.root.context)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallLogViewHolder {
        return CallLogViewHolder(CallLogAbcBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return map.size
    }

    override fun onBindViewHolder(holder: CallLogViewHolder, position: Int) {
        val data = dataList[position]
        val contactList = map[data] ?: emptyList()
        holder.setBind(contactList, data)
    }
}