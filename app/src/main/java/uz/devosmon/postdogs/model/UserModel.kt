package uz.devosmon.postdogs.model

import java.io.Serializable

data class UserModel(
    val id: String,
    val firstName: String,
    var lastName: String,
    val picture: String,
    val phone: String,
    val email: String,
    val gender:String,
    val title:String,
    val dateOfBirth:String,
    val registerDate:String,
    val location:UserAddressModel
) : Serializable