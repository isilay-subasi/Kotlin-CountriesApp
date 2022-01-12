package com.example.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincountries.R
import com.example.kotlincountries.model.Country
import kotlinx.android.synthetic.main.item_counrty.view.*

class CountryAdapter (val countryList : ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(){



    class CountryViewHolder(var view : View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        TODO("CountryViewHolder dödürüyoruz." +
                "Oluşturduğumuz item_country layoutu ile burdaki adaptörü birbirine bağlıyoruz." +
                "Bunun için inflater kullanacağız.")

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_counrty,parent,false)
        return CountryViewHolder(view)


    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        TODO("Not yet implemented")

        holder.view.name.text=countryList[position].countryName
        holder.view.region.text=countryList[position].countryRegion
    }

    override fun getItemCount(): Int {
        TODO("Kaç tane row oluşturacağını söylüyoruz")
        countryList.size
    }


    fun updateCountryList(newCountryList : List<Country>){
      countryList.clear()
      countryList.addAll(newCountryList)
      notifyDataSetChanged()


    }


}