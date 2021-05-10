package com.hfad.githubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hfad.githubapp.databinding.ItemFollowBinding

import com.hfad.githubapp.model.Following

class FollowingAdapter :RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>(){

    private val mData =ArrayList<Following>()


    fun setFollowing(followingList : ArrayList<Following>){

        mData.clear()

        mData.addAll(followingList)

        notifyDataSetChanged()

    }



   inner class FollowingViewHolder(private val binding: ItemFollowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(following: Following){
            with(binding){
                Glide.with(itemView)
                        .load(following.avatar_url)
                        .apply(RequestOptions().override(55,55))
                        .into(imgPhoto)
                tvName.text = following.login
                tvId.text = following.id.toString()
                tvType.text = following.type

            }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding = ItemFollowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FollowingViewHolder(binding)
    }



    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(mData[position])
    }


    override fun getItemCount(): Int =mData.size

}