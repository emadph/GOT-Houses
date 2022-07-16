package ir.pourahmadi.got.presentation.ui.base

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class MainBaseFragment(
    layoutId: Int
) : BaseFragment(layoutId) {
    lateinit var navController: NavController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navController = findNavController()
    }
}