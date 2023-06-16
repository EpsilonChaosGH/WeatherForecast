package com.example.weatherforecast.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherforecast.R
import com.example.weatherforecast.collectFlow
import com.example.weatherforecast.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorite) {

    private val binding by viewBinding(FragmentFavoriteBinding::class.java)

    private val viewModel by viewModels<FavoritesViewModel>()

    private val adapter = FavoritesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            refreshLayout.setColorSchemeResources(R.color.main_text_color)
            refreshLayout.setProgressBackgroundColorSchemeResource(R.color.main_color)
            refreshLayout.setOnRefreshListener { viewModel.refreshFavorites() }
        }

        observeFavoritesState()
    }

    private fun observeFavoritesState() {
        collectFlow(viewModel.state) { state ->
            with(binding) {
                adapter.items = state.favorites
                binding.recyclerView.visibility =
                    if (state.emptyListState) View.GONE else View.VISIBLE
                binding.refreshLayout.isRefreshing = state.isRefreshing
                refreshLayout.isRefreshing = state.isRefreshing
            }
        }
    }
}