package com.hfad.githubapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.hfad.githubapp.adapter.FollowerAdapter
import com.hfad.githubapp.databinding.FragmentFollowersBinding
import com.hfad.githubapp.viewModel.FollowerViewModel


class FollowersFragment : Fragment() {

    private lateinit var adapter: FollowerAdapter

    private lateinit var binding: FragmentFollowersBinding

    private lateinit var followerModel: FollowerViewModel



    companion object{
        private const val EXTRA_FOLLOW = "extra_follow"
        fun newInstance(username: String?): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_FOLLOW, username)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentFollowersBinding.inflate(inflater,container,false)

        binding.rvFollowers.setHasFixedSize(true)

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null) {

            val username = arguments?.getString(EXTRA_FOLLOW)
            followerModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)


            if (username != null) {
                followerModel.setListFollower(username)
            }

        }

        followerModel.getFollower().observe(viewLifecycleOwner,{followersList ->
            if (followersList != null){
                adapter.setFollower(followersList)
                binding.progressBar.visibility = View.INVISIBLE
            }
        })


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowerAdapter()
        adapter.notifyDataSetChanged()
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowers.adapter =adapter

    }






}