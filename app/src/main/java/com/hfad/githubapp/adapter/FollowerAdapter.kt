package com.hfad.githubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hfad.githubapp.databinding.ItemFollowBinding

import com.hfad.githubapp.model.Follower


class FollowerAdapter: RecyclerView.Adapter<FollowerAdapter.FollowersViewHolder>() {


    private val mData = ArrayList<Follower>()

    fun setFollower(followers : ArrayList<Follower>){
        mData.clear()
        mData.addAll(followers)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int):FollowersViewHolder {
      val binding = ItemFollowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FollowersViewHolder(binding)
    }


    override fun onBindViewHolder(holder:FollowersViewHolder , position: Int) {
       holder.bind(mData[position])
    }


    override fun getItemCount(): Int = mData.size



    inner class FollowersViewHolder(private val binding: ItemFollowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(follower: Follower) {
            with(binding){

                Glide.with(itemView)
                        .load(follower.avatar_url)
                        .apply(RequestOptions().override(55,55))
                        .into(imgPhoto)
                tvName.text = follower.login
                tvId.text = follower.id.toString()
                tvType.text =follower.type

            }
        }


    }
}