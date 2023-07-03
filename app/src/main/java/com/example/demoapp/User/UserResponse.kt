package com.example.demoapp.User

import android.widget.ImageView
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.File

class UserResponse {
    @SerializedName("file")
    @Expose
    var img : File? = null


}