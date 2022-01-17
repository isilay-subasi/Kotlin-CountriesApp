package com.example.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincountries.R
import com.example.kotlincountries.databinding.ItemCounrtyBinding
import com.example.kotlincountries.model.Country
import com.example.kotlincountries.util.downloadFromUrl
import com.example.kotlincountries.util.placeHolderProgressBar
import com.example.kotlincountries.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_counrty.view.*

class CountryAdapter (val countryList : ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(){



    class CountryViewHolder(var view : ItemCounrtyBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_counrty,parent,false)
        val view = DataBindingUtil.inflate<ItemCounrtyBinding>(inflater,R.layout.item_counrty,parent,false)
        return CountryViewHolder(view)


    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {


        holder.view.country=countryList[position]


        /*
                //ekranda gösterilecek yer
        holder.view.name.text=countryList[position].countryName
        holder.view.region.text=countryList[position].countryRegion

        holder.view.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)

            Navigation.findNavController(it).navigate(action)
        }

        holder.view.imageView.downloadFromUrl(countryList[position].imageUrl, placeHolderProgressBar(holder.view.context))
         */




    }

    override fun getItemCount(): Int {
        return countryList.size
    }


    fun updateCountryList(newCountryList : List<Country>){
      countryList.clear()
      countryList.addAll(newCountryList)
      notifyDataSetChanged()


    }


}