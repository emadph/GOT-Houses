package ir.pourahmadi.got.presentation.ui.main.home.adapter.viewHolders

import com.khorasannews.latestnews.presentation.ui.main.home.adapter.onClicks.HousesClickListener
import ir.pourahmadi.got.domain.model.HousesModel
import ir.pourahmadi.got.presentation.components.CustomItemHouses
import ir.pourahmadi.got.presentation.ui.base.BaseViewHolder

class HousesViewHolder(
    private val itemViews: CustomItemHouses,
    private var onItemClickListenerSimple: HousesClickListener
) : BaseViewHolder<HousesModel>(itemViews) {

    override fun bind(item: HousesModel?, position: Int) {
        item?.let {
            itemViews.apply {
                setFlagDescText(item.flagDesc)
                setTitleText(item.name)
                setRegionText(item.region)
                setOnClickListener {
                    onItemClickListenerSimple.onItemClick(item)
                }

            }
        }
    }


}