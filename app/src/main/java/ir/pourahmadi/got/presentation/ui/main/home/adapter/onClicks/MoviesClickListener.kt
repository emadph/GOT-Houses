package com.khorasannews.latestnews.presentation.ui.main.home.adapter.onClicks

import ir.pourahmadi.got.domain.model.HousesModel


interface HousesClickListener {
    fun onItemClick(click: HousesModel)
}