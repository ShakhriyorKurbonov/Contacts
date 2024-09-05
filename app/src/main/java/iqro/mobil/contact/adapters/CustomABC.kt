package iqro.mobil.contact.adapters

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import iqro.mobil.contact.ContactsButtonListener
import iqro.mobil.contact.ItemClick
import iqro.mobil.contact.dataClass.ProfileBData
import iqro.mobil.contact.databinding.CustomAbcBinding

class CustomABC(val map: Map<Char, List<ProfileBData>>, val symbolList: List<Char>) :
    Adapter<CustomABC.ViewHolderABC>() {
    private var phoneNumber = "0"
    private var position0: Int? = null
    private var itemClick: ItemClick? = null

    inner class ViewHolderABC(val binding: CustomAbcBinding) : ViewHolder(binding.root) {
        val context = binding.root.context

        fun bind(dataList: List<ProfileBData>, symbol: Char) {
            binding.charTv.text = symbol.toString()
            val customAdapterB = CustomAdapterB(dataList)
            binding.recyclerView.adapter = customAdapterB
            binding.recyclerView.layoutManager = LinearLayoutManager(context)

            customAdapterB.setListener2(object :ContactsButtonListener{
                override fun buttonPosition(position: Int, number: String) {
                    when(position){
                        0->{
                            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
                            itemClick?.intentCall(intent)
                        }
                        1->{
                            val intent= Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$number"))
                            itemClick?.intentSms(intent)
                        }
                    }
                }
            })

            customAdapterB.setListener(object :ItemClick{
                override fun profileClick(adapterPosition: Int) {

                }

                override fun intentCall(intent: Intent) {

                }

                override fun intentSms(intent: Intent) {

                }

                override fun getData(name: String, number: String, image: String) {
                    itemClick?.getData(name,number,image)
                }
            })

            val itemTouchHelper = ItemTouchHelper(object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    position0 = viewHolder.adapterPosition
                    phoneNumber = dataList[position!!].number
                    if (direction == ItemTouchHelper.RIGHT) {
                        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
                        itemClick?.intentCall(intent)
                        customAdapterB.notifyItemChanged(position!!)
                    } else {
                       val intent= Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$phoneNumber"))
                        itemClick?.intentSms(intent)
                        customAdapterB.notifyItemChanged(position!!)
                    }
                }
            })

            itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        }



    }

    fun setListener(listener: ItemClick) {
        itemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderABC {
        return ViewHolderABC(
            CustomAbcBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return map.size
    }

    override fun onBindViewHolder(holder: ViewHolderABC, position: Int) {
        val symbol = symbolList[position]
        val contactList = map[symbol] ?: emptyList()
        holder.bind(contactList, symbol)
    }


}