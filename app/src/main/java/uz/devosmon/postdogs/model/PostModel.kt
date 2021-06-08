package uz.devosmon.postdogs.model

import java.io.Serializable

data class PostModel(val id:String,
                     val owner: UserModel,
                     val text:String,
                     val image:String,
                     val publishDate:String,
                     val link:String,
                     val likes:Int):Serializable