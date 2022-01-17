package com.example.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlincountries.model.Country

@Dao
interface CountryDAO {


    //Data Access Object
    //Veritabanımıza ulaşmak istediğimiz metotları yazıyoruz.
    //Insert -> INSERT INTO
    //suspend -> coroutine, pause & resume
    //vararg -> multiple country objects
    //List<Long> -> primary keys


    //Database veri ekleneceğini söylüyoruz.
    //Tekil objeyi farklı sayıda vereceğimiz gibi düşünelim ama tek tek veriyoruz.
    //Primary ıd döndürecektir buda Long tipindedir.
    @Insert
    suspend fun insertAll(vararg countries : Country) : List<Long>


    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>


    //iki nokta üst üste koyarak aldığımız countryIdyi verebiliriz.
    @Query("SELECT * FROM country WHERE uuid= :countryId")
    suspend fun getCountry(countryId : Int ) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()



}