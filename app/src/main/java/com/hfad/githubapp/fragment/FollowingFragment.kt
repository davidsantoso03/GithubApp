package com.hfad.githubapp.fragment

import com.hfad.githubapp.adapter.FollowingAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.githubapp.viewModel.FollowingViewModel

import com.hfad.githubapp.databinding.FragmentFollowingBinding


class FollowingFragment : Fragment() {

    private lateinit var adapter: FollowingAdapter
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var followingModel: FollowingViewModel

    companion object{
        private const val ARG_USERNAME = "username"

        fun newInstance(username:String?): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFollowingBinding.inflate(inflater,container,false)
        binding.rvFollowing.setHasFixedSize(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null) {

            val username = arguments?.getString(ARG_USERNAME)
            followingModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)


            if (username != null) {
                followingModel.setListFollowing(username)
            }

        }

        followingModel.getFollowing().observe(viewLifecycleOwner,{following ->
            if (following != null){
                adapter.setFollowing(following)
                binding.progressBar.visibility = View.INVISIBLE
            }
        })


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowingAdapter()
        adapter.notifyDataSetChanged()
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.adapter =adapter

    }



}