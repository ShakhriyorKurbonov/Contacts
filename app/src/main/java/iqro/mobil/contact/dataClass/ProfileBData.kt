package iqro.mobil.contact.dataClass

class ProfileBData{
  var image:String="0"
  var name:String
  var number:String

  constructor(image:String,name:String,number:String){
   this.image=image
   this.name=name
   this.number=number
  }
  constructor(name:String,number:String){
   this.name=name
   this.number=number
  }
}
