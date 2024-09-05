package iqro.mobil.contact.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import iqro.mobil.contact.ItemClick
import iqro.mobil.contact.dataClass.ProfileAData
import iqro.mobil.contact.R
import iqro.mobil.contact.databinding.CustomABinding
import java.util.concurrent.TimeUnit

class CustomAdapterA(private val context: Context, val listA: List<ProfileAData>) :
    Adapter<CustomAdapterA.ViewHolder>() {
    private var itemClick: ItemClick? = null

    inner class ViewHolder(private val binding: CustomABinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemClick?.profileClick(adapterPosition)
            }
        }

        fun setBind(image: Int, name: String, time: Long) {
            if (image == 0) {
                try {
                    val a = ((name.trim()).split(" ".toRegex())[0])[0]
                    val b = ((name.trim()).split(" ".toRegex())[1])[0]
                    binding.textH.text = "$a$b"
                    binding.image.setImageResource(R.drawable.rectan_black)
                } catch (e: Exception) {
                    val a = name[0]
                    binding.textH.text = "$a"
                    binding.image.setImageResource(R.drawable.rectan_black)
                }

            } else {
                binding.textH.text = ""
                binding.image.setImageResource(image)
            }


            if (name.count() >= 9) {
                binding.nameA.text = "${(name.trim()).substring(0, 8)}..."
            } else {
                binding.nameA.text = name
            }

            val resultDate=System.currentTimeMillis()-time
            val hour= TimeUnit.MILLISECONDS.toHours(resultDate)
            val minute= TimeUnit.MILLISECONDS.toMinutes(resultDate)%60
            binding.time.text =if (hour==0L&&minute==0L){
                "Hozir"
            }else if(hour==0L){
                "$minute daqiqa oldin"
            }else{
                "$hour soat \n $minute daqiqa oldin"
            }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CustomABinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return listA.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setBind(listA[position].image, listA[position].name, listA[position].time)
    }
}