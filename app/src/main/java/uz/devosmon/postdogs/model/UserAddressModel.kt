package uz.devosmon.postdogs.model

import java.io.Serializable

data class UserAddressModel(
    val state:String,
    val street:String,
    val city:String,
    val country:String,
    val timezone:String
): Serializable