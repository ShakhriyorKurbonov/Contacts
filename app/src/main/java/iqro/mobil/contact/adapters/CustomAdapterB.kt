package iqro.mobil.contact.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import iqro.mobil.contact.ContactsButtonListener
import iqro.mobil.contact.ItemClick
import iqro.mobil.contact.dataClass.ProfileBData
import iqro.mobil.contact.R
import iqro.mobil.contact.databinding.CustomBBinding

class CustomAdapterB(var listB: List<ProfileBData>) :
    Adapter<CustomAdapterB.ViewHolderB>() {
        private var itemClick: ItemClick?=null
        private var contactsButtonListener: ContactsButtonListener?=null
    inner class ViewHolderB(val binding: CustomBBinding) : ViewHolder(binding.root) {

        fun setBind(image: String, name: String, Pnumber: String) {
            if (image == "0") {
                try {
                    val a = ((name.trim()).split(" ".toRegex())[0])[0]
                    val b = ((name.trim()).split(" ".toRegex())[1])[0]
                    binding.textH.text = "$a$b"
                    binding.image.setImageResource(R.drawable.rectan_black)
                } catch (e: Exception) {
                    val a = (name.trim())[0]
                    binding.textH.text = "$a"
                    binding.image.setImageResource(R.drawable.rectan_black)
                }

            } else {
                binding.textH.text=""
                binding.image.setImageURI(Uri.parse(image))
            }
            binding.nameB.text = name
            binding.number.text = Pnumber
            var imageList: MutableList<Int>
            var  num=0
            binding.root.setOnClickListener {
                if (num==1){
                    imageList= mutableListOf()
                    num=0
                }else{
                    imageList=mutableListOf(R.drawable.phone_icon,R.drawable.message_icon,R.drawable.about_icon)
                    num=1
                }
                val contactBtnAdapter=ContactBtnAdapter(imageList)
                binding.recyclerView.apply {
                    adapter=contactBtnAdapter
                    layoutManager=LinearLayoutManager(binding.root.context,LinearLayoutManager.HORIZONTAL,false)

                }
                contactBtnAdapter.setListener(object :ContactsButtonListener{
                    override fun buttonPosition(position: Int, number: String) {
                        if (position==2){
                            itemClick?.getData(name,Pnumber,image)
                        }else{
                            contactsButtonListener?.buttonPosition(position,Pnumber)
                        }
                    }
                })
            }

        }
    }

    fun setFilteredList(listB:List<ProfileBData>){
        this.listB=listB
        notifyDataSetChanged()
    }

    fun setListener(listener: ItemClick){
        itemClick=listener
    }
    fun setListener2(listener: ContactsButtonListener){
        contactsButtonListener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderB {
        return ViewHolderB(CustomBBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return listB.size
    }

    override fun onBindViewHolder(holder: ViewHolderB, position: Int) {
        holder.setBind(listB[position].image, listB[position].name, listB[position].number)
    }
}