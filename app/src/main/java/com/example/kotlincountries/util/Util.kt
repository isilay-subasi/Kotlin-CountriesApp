package com.example.kotlincountries.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlincountries.R

//Extension
//Hangi sınıfa extend edeceksem onu yazıyorum.
//Mesela String sınıfına bir eklenti oluşturmak istiyorum. Aşağıdaki gibi yapabilirim ;
//gelen veriyi yazdıracak bir extension yazmış olduk.
/*
fun String.myExtensions(myParameter : String){
println(myParameter)

}
 */


fun ImageView.downloadFromUrl(url : String? , progressDrawable: CircularProgressDrawable){

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)


    Glide.with(context)
        .setDefaultRequestOptions(options)
         .load(url)
         .into(this)

}

fun placeHolderProgressBar(context : Context) : CircularProgressDrawable{

    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }

}