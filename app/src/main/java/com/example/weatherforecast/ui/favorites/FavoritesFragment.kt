package com.example.weatherforecast.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
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

    private val adapter = FavoritesAdapter(object : FavoritesListener {
        override fun delete(id: Long) {
            viewModel.deleteFromFavorites(id)
        }

        override fun showDetails(id: Long) {
            viewModel.showDetails(id)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        refreshLayout.setColorSchemeResources(R.color.main_text_color)
        refreshLayout.setProgressBackgroundColorSchemeResource(R.color.main_color)
        refreshLayout.setOnRefreshListener { viewModel.refreshFavorites() }

        observeFavoritesState()
    }

    private fun observeFavoritesState() = with(binding) {
        collectFlow(viewModel.state) { state ->
            adapter.items = state.favorites
            recyclerView.isInvisible = state.emptyListState
            refreshLayout.isRefreshing = state.isRefreshing
        }
    }
}