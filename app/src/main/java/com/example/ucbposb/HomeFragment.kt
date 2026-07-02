package com.example.ucbposb

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ucbposb.adapter.HomeMenuAdapter
import com.example.ucbposb.adapter.SliderAdapter
import com.example.ucbposb.databinding.FragmentHomeBinding
import com.example.ucbposb.model.MenuItem
import com.example.ucbposb.viewmodel.SharedViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        observeHome()

        observeMenu()
    }

    private fun observeHome() {

        viewModel.homeData.observe(viewLifecycleOwner) { home ->

            binding.txtCompany.text = home.appName

            binding.txtSubTitle.text = home.appNameSubTitle

            val logoId = resources.getIdentifier(
                home.logo,
                "drawable",
                requireContext().packageName
            )

            binding.imgLogo.setImageResource(logoId)

            binding.viewPagerSlider.adapter =
                SliderAdapter(
                    requireContext(),
                    home.sliderMedia
                )

        }

    }

    private fun observeMenu() {

        viewModel.menuData.observe(viewLifecycleOwner) { menuList ->

            val visibleHome = menuList.filter {

                it.isShow &&
                        it.isEnable &&
                        it.isShowInHome

            }

            val homeMenu = visibleHome
                .take(3)
                .toMutableList()

            if (visibleHome.size > 3) {

                homeMenu.add(

                    MenuItem(

                        id = -1,

                        title = "More",

                        subTitle = "",

                        icon = "more",

                        isShowInHome = true,

                        isShow = true,

                        isEnable = true

                    )

                )

            }

            binding.rvHomeMenu.layoutManager =
                GridLayoutManager(requireContext(), 2)

            binding.rvHomeMenu.adapter =
                HomeMenuAdapter(
                    requireContext(),
                    homeMenu
                ) { menu ->

                    if (menu.id == -1) {

                        findNavController().navigate(
                            R.id.action_home_to_more
                        )

                    }

                }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}