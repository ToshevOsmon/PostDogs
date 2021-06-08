package uz.devosmon.postdogs.model

import java.io.Serializable

data class Users(
    val page:Int,
    val total:Int,
    val limit:Int,
    val offset:Int,
    val data:List<UserModel>
):Serializable