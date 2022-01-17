package com.example.kotlincountries.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountries.model.Country
import com.example.kotlincountries.service.CountryAPIService
import com.example.kotlincountries.service.CountryDatabase
import com.example.kotlincountries.util.CustomSharedPreferences
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application){


    //servis objemizi oluşturmamız gerekiyor.
    private val countryApiService =CountryAPIService()
    //disponsablemızı oluşturacağız.
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())

    private var resfreshTime = 10*60*1000*1000*1000L // 10 dknın nanosaniye cinsinden değeri

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){




        val updateTime = customPreferences.getTime()

        //10 dakikadan az bir süre geçmişse
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < resfreshTime){
                getDataFromSQLite()

        }else getDataFromAPI()



        /*
                val country = Country("Turkey","Asia","Ankara","TRY","Turkish","www.ss.com")
        val country2 = Country("France","Europe","Paris","EUR","French","www.ss.com")
        val country3 = Country("German","Europe","Berlin","EUR","German","www.ss.com")

        //bunları bir listeye alalım.

        val countryList = arrayListOf<Country>(country,country2,country3)

        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
         */





    }


    fun refreshFromAPI(){
        getDataFromAPI()
    }


    private fun getDataFromSQLite(){

        countryLoading.value=true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries From SQLite",Toast.LENGTH_LONG).show()
        }
    }



    private fun getDataFromAPI(){

        countryLoading.value=true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {

                        storeInSQLite(t) // Burda yapacağımız işlemi arka planda yapacağız.
                        Toast.makeText(getApplication(),"Countries From API",Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value=false
                        countryError.value=true
                        e.printStackTrace()

                    }

                })
        )



    }

    private fun showCountries(countryList: List<Country>){
        countries.value=countryList
        countryLoading.value=false
        countryError.value=false
    }


    //Aldığımız verileri kaydetmek için oluşturulan metot
    private fun storeInSQLite(list : List<Country>){

        //Bunun içerisinde coroutinlere kullanacağız.
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray()) // list -> individual
            var i = 0
            while (i<list.size){
                //Bana döndürülen long değerini gerçekten uuid olarak tanımlayabileceğiz.
                list[i].uuid=listLong[i].toInt()
                i=i+1
            }

            showCountries(list)


        }

        customPreferences.saveTime(System.nanoTime()) // bize bir long değeri verecektir. Hangi zamanda kaydedildiğini tutacaktır.



    }




}