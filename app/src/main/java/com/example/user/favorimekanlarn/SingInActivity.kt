package com.example.user.favorimekanlarn

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.parse.LogInCallback
import com.parse.ParseException
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_sing_in.*

class SingInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setTitle("Favori Mekanlarım")
        setContentView(R.layout.activity_sing_in)
    }

    fun signIn (view: View){
        ParseUser.logInInBackground(txtUserName.text.toString(),txtPassword.text.toString(), LogInCallback { user, e ->
            if(e!=null){
                Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_LONG).show()
            } else{
                Toast.makeText(applicationContext,"Hoşgeldin "+user.username.toString(), Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext,LocationsActivity::class.java)
                startActivity(intent)
            }
        })
    }

    fun signUp (view: View){
        val user = ParseUser()
        user.setUsername(txtUserName.text.toString())
        user.setPassword(txtPassword.text.toString())
        user.signUpInBackground { e: ParseException? ->
            if(e!=null){
                Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_LONG).show()
            } else{
                Toast.makeText(applicationContext,"Kayıt Başarıyla Oluşturuldu", Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext,LocationsActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
