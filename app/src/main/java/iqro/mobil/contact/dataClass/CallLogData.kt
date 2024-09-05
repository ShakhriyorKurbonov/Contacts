package iqro.mobil.contact.dataClass

data class CallLogData(
    val name:String,
    val number:String,
    val type:Int,
    val date: Long,
    val duration:Long,
    val simId:String)