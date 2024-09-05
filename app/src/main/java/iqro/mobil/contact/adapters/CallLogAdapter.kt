package iqro.mobil.contact.adapters

import android.provider.CallLog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import iqro.mobil.contact.R
import iqro.mobil.contact.dataClass.CallLogData
import iqro.mobil.contact.databinding.CallLogItemLayoutBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class CallLogAdapter(val list: List<CallLogData>) : Adapter<CallLogAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: CallLogItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            name: String,
            number: String,
            type: Int,
            date: Long,
            duration: Long,
            simId: String
        ) {
            binding.nameTv.text = name
            binding.phoneNumber.text = number
            when (type) {
                CallLog.Calls.INCOMING_TYPE -> binding.callStatusIv.setImageResource(R.drawable.kiruvchi_q)
                CallLog.Calls.OUTGOING_TYPE -> binding.callStatusIv.setImageResource(R.drawable.chiquvchi_q)
                CallLog.Calls.MISSED_TYPE -> binding.callStatusIv.setImageResource(R.drawable.javobsiz_q)
                CallLog.Calls.VOICEMAIL_TYPE -> binding.callStatusIv.setImageResource(R.drawable.voicemail_ic)
                CallLog.Calls.REJECTED_TYPE -> binding.callStatusIv.setImageResource(R.drawable.rad_etilgan_q)
                CallLog.Calls.BLOCKED_TYPE -> binding.callStatusIv.setImageResource(R.drawable.bloklangan_q)
                CallLog.Calls.ANSWERED_EXTERNALLY_TYPE -> binding.callStatusIv.setImageResource(R.drawable.phone_icon)
                else -> "Noma'lum"
            }
            Log.d("TAG", "bind: $simId")
            val time=Date(date)
            val dateFormat=SimpleDateFormat("HH:mm",Locale.getDefault())
            val formatedDate=dateFormat.format(time)
            binding.timeTv.text=formatedDate
            binding.durationTv.text= duration.toString()
            val sec = TimeUnit.MILLISECONDS.toSeconds(duration)
            val durMin = sec / 60
            val qSec = sec % 60

            binding.durationTv.text = when {
                durMin > 0 && qSec > 0 -> "$durMin minut $qSec sekond"
                durMin > 0 -> "$durMin minut"
                qSec > 0 -> "$qSec sekond"
                else -> "Muloqot qilinmagan"
            }
            Log.d("TAG", "bind: $simId")
            when(simId){
                "0"->{
                    binding.simIdIv.setImageResource(R.drawable.sim_1)
                }
                "1"->{
                    binding.simIdIv.setImageResource(R.drawable.sim_2)
                }
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CallLogItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            list[position].name,
            list[position].number,
            list[position].type,
            list[position].date,
            list[position].duration,
            list[position].simId
        )
    }
}