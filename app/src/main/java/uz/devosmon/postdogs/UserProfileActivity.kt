package uz.devosmon.postdogs

import android.icu.text.DateFormat
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_user_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.devosmon.postdogs.api.ApiService
import uz.devosmon.postdogs.model.AllUserProfileModel
import uz.devosmon.postdogs.model.UserModel
import java.text.SimpleDateFormat
import java.util.*

class UserProfileActivity : AppCompatActivity() {

    lateinit var userProfile: UserModel

    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap
    lateinit var city: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        supportActionBar?.hide()

        userProfile = intent.getSerializableExtra("id") as UserModel

        loadUserPorfile(userProfile.id)

  //      Handler().postDelayed({ loadGeoLocation() }, 5000)


    }

    private fun loadGeoLocation() {

        mapFragment = supportFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment

        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it


                city = userProfile.location.city

            var gc = Geocoder(this, Locale.getDefault())
            var address = gc.getFromLocationName(city, 2)
            var locition = address.get(0)
            var lat = locition.latitude
            var long = locition.longitude

            val location = LatLng(lat, long)
            googleMap.addMarker(MarkerOptions().position(location).title("Location"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14f))


        })


    }

    fun loadUserPorfile(id: String) {
        Log.d("TTTT", userProfile.id)

        ApiService.apiClient().getUsersById(id)
            .enqueue(object : Callback<AllUserProfileModel> {
                override fun onResponse(
                    call: Call<AllUserProfileModel>,
                    response: Response<AllUserProfileModel>
                ) {

                    var user: AllUserProfileModel = response.body()!!

                    Log.d("TTTT", response.body()!!.toString())

                    fullName.text = user.title + ": " + user.firstName + " " + user.lastName

                    Glide.with(this@UserProfileActivity).load(response.body()?.picture)
                        .centerCrop()
                        .into(imgItem)


                    var myDate: String = user.dateOfBirth
                    var dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    var date: Date = dateFormat.parse(myDate)

                    var formatDate: String =
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            DateFormat.getDateInstance(DateFormat.DEFAULT).format(date)
                        } else ({

                        }).toString()


                    var myDate1: String = user.registerDate
                    var dateFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    var date1: Date = dateFormat1.parse(myDate1)

                    var formatDate1: String =
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            DateFormat.getDateInstance(DateFormat.DEFAULT).format(date1)
                        } else ({

                        }).toString()


                    userInfo.text =
                        "Phone: " + user.phone + "\n" + "Email: " + user.email + "\n" + "Gender: " + user.gender +
                                "\n" + "Birthday: " + formatDate + "\n" + "Register Date: " + formatDate1

                    address.text =
                        "Street: " + user.location.street + "\n" + "City: " + user.location.city + "\n" + "Country: " + user.location.country


                }

                override fun onFailure(
                    call: Call<AllUserProfileModel>,
                    t: Throwable
                ) {

                    Toast.makeText(this@UserProfileActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            })

    }

}
