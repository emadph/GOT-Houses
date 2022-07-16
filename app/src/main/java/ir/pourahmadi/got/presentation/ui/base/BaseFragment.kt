package ir.pourahmadi.got.presentation.ui.base

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment(
    layoutId: Int
) : Fragment(layoutId)