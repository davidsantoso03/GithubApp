package com.hfad.githubapp.adapter



import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hfad.githubapp.model.User
import com.hfad.githubapp.databinding.ItemUserBinding


class UserAdapter:RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    private val userData = ArrayList<User>()


    fun setUserData(users : ArrayList<User>){

        userData.clear()
        userData.addAll(users)
        notifyDataSetChanged()

    }


    fun setOnItemCLickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback =onItemClickCallback
    }


    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }


    override fun onCreateViewHolder(viewgroup: ViewGroup,i: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(viewgroup.context),viewgroup,false)
        return ListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) { holder.bind(userData[position]) }

    override fun getItemCount(): Int = userData.size

   inner class ListViewHolder (private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){

       fun bind(user: User) {

            with(binding){
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .apply(RequestOptions().override(55,55))
                    .into(imgPhoto)
                tvName.text = user.login
                tvCompany.text = user.company
                tvLocation.text = user.location

                itemView.setOnClickListener{ onItemClickCallback?.onItemClicked(user) }

            }
       }


   }


}