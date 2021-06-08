package uz.devosmon.postdogs.model

data class AllUserProfileModel(
    val id: String,
    val phone: String,
    val firstName: String,
    val lastName: String,
    val location: UserAddressModel,
    val email: String,
    val gender: String,
    val title: String,
    val registerDate: String,
    val picture: String,
    val dateOfBirth: String
)