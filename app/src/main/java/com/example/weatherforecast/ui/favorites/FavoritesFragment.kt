package com.example.weatherforecast.ui.favorites

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.data.entity.Coordinates
import com.example.data.utils.Const
import com.example.weatherforecast.R
import com.example.weatherforecast.utils.collectFlow
import com.example.weatherforecast.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorite) {

    private val binding by viewBinding(FragmentFavoriteBinding::class.java)

    private val viewModel by viewModels<FavoritesViewModel>()

    private val adapter = FavoritesAdapter(object : FavoritesListener {

        override fun showDetails(coordinates: Coordinates) {
            viewModel.loadWeatherByCoordinates(coordinates)
            showDetails()
            Const
        }
    })

    private val itemTouchHelper = ItemTouchHelper(object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.deleteFromFavorites(viewHolder.adapterPosition)
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
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        observeFavoritesState()
    }

    private fun showDetails() {
        findNavController().navigate(R.id.action_favoritesFragment_to_weather_graph)
    }

    private fun observeFavoritesState() = with(binding) {
        collectFlow(viewModel.uiState) { state ->
            adapter.items = state.favorites
            refreshLayout.isRefreshing = state.isLoading
            recyclerView.isInvisible = state.favorites.isEmpty()

            state.userMessage.get()?.let {
                Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
            }
        }
    }
}