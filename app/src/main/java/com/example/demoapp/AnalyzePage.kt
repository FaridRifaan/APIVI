package com.example.demoapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View

import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import com.example.demoapp.User.UserApi
import com.google.android.material.bottomsheet.BottomSheetDialog


import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream


class AnalyzePage : AppCompatActivity() {
    private var our_request_code: Int = 123
    companion object{     val IMAGE_REQUEST_CODE = 100

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyze_page)

        val scanning = findViewById<ImageButton>(R.id.icon_qr_scan)

        scanning.setOnClickListener{

            val imageView: ImageView = findViewById(R.id.gambar_analisa)
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap


        val file = File(this.cacheDir, "image.jpg")
        file.createNewFile()
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()

            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)

//            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
//
            val image = MultipartBody.Part.createFormData("file", file.name, requestFile)

        //retrofit dengan hhtp tunnel (ngrok)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://c5fa-2407-0-3002-2c1-c8d4-42ba-45b0-2c06.ap.ngrok.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(UserApi::class.java)

        api.UserRequest(image).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.e("berhasil yeayy", "hahahah" )

                    val hasilRespon = response.body()

                    val getInputStream = hasilRespon?.byteStream()

                    val bitmap = BitmapFactory.decodeStream(getInputStream)


                    val gambar  = findViewById<ImageView>(R.id.gambar_analisa)
                    gambar.setImageBitmap(bitmap)
                    // proses response
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("gagal :(", t.toString())
                    // proses error
                }
            })

            val view: View = layoutInflater.inflate(R.layout.bottomsheet_layout, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()

        }



        // untuk membuka kamera
        val icon_image = findViewById<ImageView>(R.id.icon_image)
        icon_image.isEnabled = true
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                100
            )
        } else {
            icon_image.isEnabled = true
        }
        icon_image.setOnClickListener {
            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i, 101)
        }
            // untuk mengambil gambar dari galeri
        val galeri = findViewById<ImageButton>(R.id.galeri_btn)
        galeri.isEnabled = true
        galeri.setOnClickListener {
            pickImageGalery()
        }
    }

//    private fun File(gambarAnalisa: ImageView?): Any {
//
//    }

    private fun pickImageGalery() {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            var pic: Bitmap? = data?.getParcelableExtra<Bitmap>("data")
            val gambar_analisa = findViewById<ImageView>(R.id.gambar_analisa)
            gambar_analisa.setImageBitmap(pic)
        }
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            val gambar_analisa = findViewById<ImageView>(R.id.gambar_analisa)
            gambar_analisa.setImageURI(data?.data)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val icon_image = findViewById<ImageView>(R.id.icon_image)
            icon_image.isEnabled = true
        }
    }

}

//private fun RequestBody.Companion.create(parse: MediaType?, file: Any): RequestBody {
//
//}


