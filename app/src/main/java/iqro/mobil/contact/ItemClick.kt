package iqro.mobil.contact

import android.content.Intent
import android.media.Image

interface ItemClick {
    fun profileClick(adapterPosition: Int)
    fun intentCall(intent: Intent)
    fun intentSms(intent: Intent)
    fun getData(name:String,number: String,image: String)
}