package com.example.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountries.model.Country
import com.example.kotlincountries.service.CountryAPIService
import com.example.kotlincountries.service.CountryDatabase
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

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        getDataFromAPI()

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


    private fun getDataFromAPI(){

        countryLoading.value=true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {

                        storeInSQLite(t) // Burda yapacağımız işlemi arka planda yapacağız.


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


    }




}