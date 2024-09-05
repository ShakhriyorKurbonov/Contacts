package iqro.mobil.contact.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import iqro.mobil.contact.ContactsButtonListener
import iqro.mobil.contact.ItemClick
import iqro.mobil.contact.R
import iqro.mobil.contact.dataClass.ProfileBData
import iqro.mobil.contact.databinding.ContactButtonBinding

class ContactBtnAdapter(val imageList:List<Int>):Adapter<ContactBtnAdapter.BtnViewHolder>() {
    var contactsButtonListener:ContactsButtonListener?=null
    inner class BtnViewHolder(val binding: ContactButtonBinding):ViewHolder(binding.root){
        fun bind(image:Int){
            if (image==R.drawable.phone_icon){
                binding.imageBtn.setImageResource(image)
                binding.imageBtn.setBackgroundResource(R.drawable.rectan_blue)
            }else if (image==R.drawable.message_icon){
                binding.imageBtn.setImageResource(image)
                binding.imageBtn.setBackgroundResource(R.drawable.rectan_green)
            }else if(image==R.drawable.about_icon){
                binding.imageBtn.setImageResource(image)
                binding.imageBtn.setBackgroundResource(R.drawable.rectan_grey)
            }
            binding.imageBtn.setOnClickListener {
                contactsButtonListener?.buttonPosition(adapterPosition,"0")
            }
        }
    }

    fun setListener(listener:ContactsButtonListener){
        contactsButtonListener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BtnViewHolder {
        return BtnViewHolder(ContactButtonBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return imageList.size
    }

    override fun onBindViewHolder(holder: BtnViewHolder, position: Int) {
        holder.bind(imageList[position])
    }
}