package ut.ee.cs.mapapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback ,GoogleMap.OnMyLocationClickListener {
    override fun onMyLocationClick(location: Location) {

        var latitude=location.latitude
      var   longitude=location.longitude
        var nLocation=LatLng(latitude,longitude)
        val geocoder = Geocoder(this)
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        val loc=addresses[0].getAddressLine(0)
        mMap.addMarker(MarkerOptions().position(nLocation).title("You are here $loc"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nLocation,15F))

    }

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions( this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1 )
            return
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun getCurrentLocation(): Location? {
      val location= TrackLocation(this).getCurrentLocation()

        return location
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMyLocationEnabled(true)
        mMap.setOnMyLocationClickListener (this)

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))

        val delta = LatLng(58.385254, 26.725064)
        mMap.addMarker(MarkerOptions().position(delta).title("Delta Centre"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(delta,12F))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        val myLocation=TrackLocation(this).getCurrentLocation()
        if (myLocation!=null){
            val locationCoord=LatLng(myLocation.latitude,myLocation.longitude)
            mMap.addMarker(MarkerOptions().position(locationCoord).title("My GPS Position"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationCoord,12F))

                mMap.addPolyline(PolylineOptions().add(locationCoord,delta))

        }




    }
}
