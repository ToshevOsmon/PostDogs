package uz.devosmon.postdogs.model

import java.io.Serializable

data class Posts(
    val page:Int,
    val total:Int,
    val limit:Int,
    val offset:Int,
    val data:List<PostModel>
):Serializable