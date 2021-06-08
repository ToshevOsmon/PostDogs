package uz.devosmon.postdogs.model

import uz.devosmon.postdogs.model.UserModel
import java.io.Serializable

data class CommentModel(
    val owner:UserModel,
    val message:String,
    val publishDate:String,
    val id:String,

):Serializable