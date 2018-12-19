package com.example.user.favorimekanlarn

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_place.*

var globalName=""
var globalType=""
var globalDescription=""
var globalImage : Bitmap? = null

class PlaceActivity : AppCompatActivity() { //344

    var choosenImage : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)
    }

    fun selectImage(view:View){
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){ //İzin yoksa
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),2) //izin iste
        } else{ //izin alınmışsa
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode==2){
            if(grantResults.size>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,1)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode==1 && resultCode== Activity.RESULT_OK&&data!=null){
            val selected = data.data

            try {
                choosenImage = MediaStore.Images.Media.getBitmap(this.contentResolver,selected)
                imageView.setImageBitmap(choosenImage)

            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun addPlace(view:View){

        globalName = txtPlaceName.text.toString()
        globalType = txtPlaceType.text.toString()
        globalDescription = txtDescription.text.toString()
        globalImage = choosenImage

        val intent = Intent(applicationContext,MapsActivity2::class.java)
        startActivity(intent)

    }
}
