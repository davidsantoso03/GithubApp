package com.hfad.githubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hfad.githubapp.data.Favorite
import com.hfad.githubapp.databinding.ItemFavoriteBinding



class FavoriteAdapter(private val favList: ArrayList<Favorite>,private  var onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {

    fun setFavList(fav: List<Favorite>) {
        if (fav.size > 0) favList.clear()
        favList.addAll(fav)
    }


    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): FavViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) { holder.bind(favList[position]) }

    override fun getItemCount(): Int = favList.size


    interface OnItemClickCallback {
        fun onDeleteFav(favorite: Favorite)
        fun setOnItemClickCallback(favorite: Favorite)
    }


    inner class FavViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favorite: Favorite) {

            with(binding) {
                Glide.with(itemView.context)
                        .load(favorite.avatar_url)
                        .apply(RequestOptions().override(55, 55))
                        .into(imgPhoto)
                tvName.text = favorite.login
                tvLocation.text = favorite.location

                itemView.setOnClickListener {
                    onItemClickCallback.setOnItemClickCallback(favorite)
                }
                icon.setOnClickListener{
                    onItemClickCallback.onDeleteFav(favorite)
                }
            }
        }
    }
}











