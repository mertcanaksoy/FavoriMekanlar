package com.example.user.favorimekanlarn

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity(), OnMapReadyCallback
{
    var choosenPlace=""
    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapDetails) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val intent = intent
        choosenPlace = intent.getStringExtra("name")
    }

    override fun onMapReady(p0: GoogleMap)
    {
        mMap = p0

        val query = ParseQuery<ParseObject>("Locations")
        query.whereEqualTo("name",choosenPlace)
        query.findInBackground { objects, e ->
            if(e!=null)
            {
                Toast.makeText(applicationContext,e.localizedMessage,Toast.LENGTH_LONG).show()
            }
            else
            {
                if(objects.size>0)
                {
                    for(parseObject in objects)
                    {
                        try
                        {
                            val name = parseObject.get("name") as String
<<<<<<< HEAD
                            val type = parseObject.get("type") as String
                            val description = parseObject.get("description") as String

                            setTitle(name)
                            textViewName.text=name
                            textViewType.text= type
                            textViewDescription.text= description
=======

                            setTitle(name)
                            textViewType.text= parseObject.get("type") as String
                            textViewDescription.text= parseObject.get("description") as String
>>>>>>> 8f2c307c08921d8234ff0c8b63a233c1ecb33b2e
                            val latitude = parseObject.get("latitude") as String
                            val longitude = parseObject.get("longitude") as String
                            val image_exists = parseObject.get("image_exists") as Boolean

                            if(image_exists)
                            {
                                val image = parseObject.get("image") as ParseFile
                                image.getDataInBackground { data, e ->
                                    if (e != null)
                                    {
                                        Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                                    }
                                    else
                                    {
                                        val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                                        imageViewDetails.setImageBitmap(bitmap)
                                    }
                                }
                            }
                            else
                            {
                                //imageViewDetails.visibility= View.INVISIBLE
                            }
                            val latitudeDouble = latitude.toDouble()
                            val longitudeDouble = longitude.toDouble()
                            val choosenLocation = LatLng(latitudeDouble,longitudeDouble)
                            mMap.addMarker(MarkerOptions().position(choosenLocation).title(name))
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(choosenLocation,17f))
                        }
                        catch (exception_main:Exception)
                        {

                        }
                    }
                }
            }
        }
    }
}
