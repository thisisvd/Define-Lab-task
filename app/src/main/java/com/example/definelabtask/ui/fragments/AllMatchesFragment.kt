package com.example.definelabtask.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.definelabtask.R
import com.example.definelabtask.adapter.MatchAdapter
import com.example.definelabtask.data.room.MatchData
import com.example.definelabtask.databinding.FragmentAllMatchesBinding
import com.example.definelabtask.ui.viewmodels.MainViewModel
import com.example.definelabtask.utils.Resource

class AllMatchesFragment : Fragment() {

    // TAG
    private val TAG = "AllMatchesFragment"

    // view binding
    private var _binding: FragmentAllMatchesBinding? = null
    private val binding get() = _binding!!

    // view models
    private val viewModel: MainViewModel by activityViewModels()

    // adapter
    private lateinit var matchAdapter: MatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            // init recycler
            setupRecyclerView()

            // fetch the data
            viewModel.getDataFromAPI()

            // view model observers
            viewModelObservers()
        }
    }

    // view model observers
    private fun viewModelObservers() {
        viewModel.apply {
            getAPIDataValue.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { value ->
                            Log.d(TAG, value.response.venues.size.toString())
                            matchAdapter.differ.submitList(value.response.venues)
                            binding.progressBar.visibility = View.GONE
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Log.d(
                                TAG, "Error -> $message"
                            )
                            binding.apply {
                                progressBar.visibility = View.GONE
                                errorText.visibility = View.VISIBLE
                            }
                        }
                    }

                    is Resource.Loading -> {
                        Log.d(
                            TAG, "Error -> Loading..."
                        )
                        binding.apply {
                            progressBar.visibility = View.VISIBLE
                            errorText.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    // recycler view setup
    private fun setupRecyclerView() {
        // adapters init
        matchAdapter = MatchAdapter(requireContext())

        binding.apply {
            recyclerView.apply {
                adapter = matchAdapter
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            }
        }

        // view click item
        matchAdapter.setOnItemClickListener { venue, img ->
            img.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.baseline_star_24
                )
            )

            viewModel.addMatchesToDB(MatchData(venue.id, venue.name))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}