package com.example.user.favorimekanlarn

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import java.io.ByteArrayOutputStream

class MapsActivity2 : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var locationManager : LocationManager? = null
    var locationListener: LocationListener? = null
    var latitude=""
    var longitude=""

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.save_place,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.save_place){
            saveToParse()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Konum Seç")
        setContentView(R.layout.activity_maps2)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap)
    {
        mMap = googleMap
        mMap.setOnMapLongClickListener(myListener)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(p0: Location?)
            {
                if (p0 != null) {
                    var userLocation = LatLng(p0.latitude,p0.longitude)
                    mMap.clear()
                    //mMap.addMarker(MarkerOptions().position(userLocation).title("Your Location"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,17f))
                }
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onProviderDisabled(provider: String?) {
            }

        }

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
        }
        else
        {//LocationManager'i başlat
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,4f,locationListener)

            mMap.clear()
            var lastLocation = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if(lastLocation!=null)
            {
                var lastUserLocation = LatLng(lastLocation.latitude,lastLocation.longitude)
                //mMap.addMarker(MarkerOptions().position(lastUserLocation).title("Your Location"))

                googleMap.isMyLocationEnabled=true
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,17f))
            }
        }

    }

    fun saveToParse()
    {
        if (latitude == "" || longitude == "")
        {
            Toast.makeText(applicationContext,"Lokasyon seçmek için basılı tutunuz.", Toast.LENGTH_LONG).show()
        }
        else
        {
            val parseObj = ParseObject("Locations")

            //İmage dosyasını byte'a çevirme
            try {
                val byteArrayOutputStream = ByteArrayOutputStream()
                globalImage!!.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream)
                val bytes = byteArrayOutputStream.toByteArray()
                val parsefile = ParseFile("image.png", bytes)
                parseObj.put("image", parsefile)
                parseObj.put("image_exists", true)
            } catch (exception_image: Exception)
            {
                parseObj.put("image_exists", false)
            }
            parseObj.put("name", globalName)
            parseObj.put("type", globalType)
            parseObj.put("description", globalDescription)
            parseObj.put("latitude", latitude)
            parseObj.put("longitude", longitude)
            parseObj.put("username", ParseUser.getCurrentUser().username.toString())
            parseObj.saveInBackground { e ->
                if (e != null) {
                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Mekan Kaydedildi", Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext, LocationsActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }



    val myListener = object : GoogleMap.OnMapLongClickListener {
        override fun onMapLongClick(p0: LatLng?) {
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(p0!!).title(globalName))

            latitude = p0.latitude.toString()
            longitude = p0.longitude.toString()
            Toast.makeText(applicationContext,"Mekanı Üst Menüden Kaydedebilirsiniz", Toast.LENGTH_LONG).show()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        if(grantResults.size>0)
        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                //LocationManager'i başlat
                locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,10,4f,locationListener)

                mMap.clear()
                var lastLocation = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if(lastLocation!=null)
                {
                    var lastUserLocation = LatLng(lastLocation.latitude,lastLocation.longitude)
                    mMap.isMyLocationEnabled=true
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,17f))
                }
            }
            else
            {
                var userLocation1 = LatLng(39.537110, 34.304152)
                mMap.clear()
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation1,4f))
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
