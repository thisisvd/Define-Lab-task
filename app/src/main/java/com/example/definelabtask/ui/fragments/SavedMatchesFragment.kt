package com.example.definelabtask.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.definelabtask.adapter.SavedMatchAdapter
import com.example.definelabtask.data.room.MatchData
import com.example.definelabtask.databinding.FragmentSavedMatchesBinding
import com.example.definelabtask.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class SavedMatchesFragment : Fragment() {

    // TAG
    private val TAG = "SavedMatchesFragment"

    // view binding
    private var _binding: FragmentSavedMatchesBinding? = null
    private val binding get() = _binding!!

    // view model
    private val viewModel: MainViewModel by activityViewModels()

    // adapter
    private lateinit var savedMatchAdapter: SavedMatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            // recycler view
            setupRecyclerView()

            // match data observer
            viewModel.getMatchDataFromDB().observe(viewLifecycleOwner) {
                if (!it.isNullOrEmpty()) {
                    savedMatchAdapter.differ.submitList(it)
                    noDataText.visibility = View.GONE
                } else {
                    savedMatchAdapter.differ.submitList(listOf())
                    noDataText.visibility = View.VISIBLE
                }
                progressBar.visibility = View.GONE
            }
        }
    }

    // recycler view setup
    private fun setupRecyclerView() {
        // adapters init
        savedMatchAdapter = SavedMatchAdapter()

        binding.apply {
            recyclerView.apply {
                adapter = savedMatchAdapter
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            }
        }

        // view click item
        savedMatchAdapter.setOnItemClickListener { matchData ->
            confirmRemoval(matchData)
        }
    }

    // show alert dialog to delete all item from table
    private fun confirmRemoval(matchData: MatchData) {
        AlertDialog.Builder(requireContext())
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteMatchDataFromDB(matchData)
                Snackbar.make(requireView(), "Successfully removed!", Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ -> }
            .setTitle("Delete Data?")
            .setMessage("Are you sure you want to delete this item?")
            .create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}