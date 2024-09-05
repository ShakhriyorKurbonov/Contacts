package iqro.mobil.contact.dataClass

 class ProfileAData{
    var image: Int=0
    var name:String
    var time:Long
    var number:String

    constructor(image:Int,name:String,time:Long,number:String){
        this.image=image
        this.name=name
        this.time=time
        this.number=number
    }
    constructor(name:String,time:Long,number: String){
        this.name=name
        this.time=time
        this.number=number
    }
}

