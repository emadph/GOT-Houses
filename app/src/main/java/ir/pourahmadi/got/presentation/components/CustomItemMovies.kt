package ir.pourahmadi.got.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import ir.pourahmadi.got.R
import ir.pourahmadi.got.utils.showIf
import ir.pourahmadi.got.utils.showIfInvis
import kotlinx.android.synthetic.main.item_houses.view.*

class CustomItemHouses @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.item_houses, this, true)

        attrs?.let {

            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.CustomItemHouses, 0, 0
            )

            typedArray.recycle()
        }
    }


    fun setTitleText(text: String?) {
        tvHousesTitle.text = text
        tvHousesTitle.showIf { !text.isNullOrEmpty() }
    }

    fun setFlagDescText(text: String?) {
        tvHousesFlagDesc.text = text
        tvHousesFlagDesc.showIfInvis { !text.isNullOrEmpty() }
    }

    fun setRegionText(text: String?) {
        tvHousesRegion.text = text
        tvHousesRegion.showIf { !text.isNullOrEmpty() }
    }

}