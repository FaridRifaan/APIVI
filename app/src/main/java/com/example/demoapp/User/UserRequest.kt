package com.example.demoapp.User

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.File

class UserRequest {
    @SerializedName ("file")
    @Expose
    var img : File? = null

}