package com.nikitagorbatko.testhammersystems.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import com.nikitagorbatko.testhammersystems.databinding.FragmentMenuBinding
import com.nikitagorbatko.testhammersystems.entity.Category
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuViewModel by viewModel()

    private lateinit var dishesAdapter: DishesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dishesAdapter = DishesAdapter()

        //val categoryes = Retrofit().getCategoriesService().getCategories()
        val category = object : Category {
            override val id: Int = 1
            override val name: String = "Пекарни и выпечка"
            override val imageUrl: String = "url"
        }
        binding.recyclerCategories.adapter =
            CategoriesAdapter(categories = listOf(category, category, category, category))

        bind()
        observe()
    }

    private fun bind() {
        binding.recyclerDishes.adapter = dishesAdapter.withLoadStateFooter(CommonLoadStateAdapter())
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest {
                dishesAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            dishesAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.progressBar.visibility =
                    if (loadStates.refresh is LoadState.Loading) View.VISIBLE else View.GONE
                binding.recyclerDishes.visibility =
                    if (loadStates.refresh is LoadState.NotLoading) View.VISIBLE else View.GONE
                binding.textError.visibility =
                    if (loadStates.refresh is LoadState.Error) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}