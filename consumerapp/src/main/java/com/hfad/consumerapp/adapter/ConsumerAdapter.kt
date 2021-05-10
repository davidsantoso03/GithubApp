package com.hfad.consumerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hfad.consumerapp.R
import com.hfad.consumerapp.data.Favorite
import kotlinx.android.synthetic.main.item_consumer.view.*


class ConsumerAdapter(private val favList : List<Favorite>) : RecyclerView.Adapter<ConsumerAdapter.ListViewHolder> () {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_consumer,parent,false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val fav  = favList[position]
        holder.itemView.apply {
            Glide.with(this)
                .load(fav.avatar_url)
                .apply(RequestOptions().override(55,55))
                .into(img_photo)
            tv_name.text = fav.login
            tv_location.text = fav.location


        }
    }

    class ListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return favList.size
    }
}