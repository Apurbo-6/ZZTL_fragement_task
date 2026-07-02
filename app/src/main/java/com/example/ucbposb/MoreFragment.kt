package com.example.ucbposb

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ucbposb.adapter.HomeMenuAdapter
import com.example.ucbposb.databinding.FragmentMoreBinding
import com.example.ucbposb.viewmodel.SharedViewModel

class MoreFragment : Fragment(R.layout.fragment_more) {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMoreBinding.bind(view)

        // Back Button
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        observeMenu()
    }

    private fun observeMenu() {

        viewModel.menuData.observe(viewLifecycleOwner) { menuList ->

            // All visible menu items
            val visibleMenu = menuList.filter {
                it.isShow && it.isEnable
            }

            // Home items
            val homeItems = visibleMenu.filter {
                it.isShowInHome
            }

            // Remaining Home items after first 3
            val remainingHomeItems = homeItems.drop(3)

            // Items only for More Services
            val moreOnlyItems = visibleMenu.filter {
                !it.isShowInHome
            }

            // Final list
            val moreMenu = remainingHomeItems + moreOnlyItems

            binding.rvMoreMenu.layoutManager =
                GridLayoutManager(requireContext(), 2)

            binding.rvMoreMenu.adapter =
                HomeMenuAdapter(
                    requireContext(),
                    moreMenu
                ) { menu ->

                    // Future click actions

                }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}