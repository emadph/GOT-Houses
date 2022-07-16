package ir.pourahmadi.got.presentation.ui.main.home.adapter

import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.khorasannews.latestnews.presentation.ui.main.home.adapter.onClicks.HousesClickListener
import ir.pourahmadi.got.domain.model.HousesModel
import ir.pourahmadi.got.presentation.components.CustomItemHouses
import ir.pourahmadi.got.presentation.ui.main.home.adapter.viewHolders.HousesViewHolder


class HousesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var mListener: HousesClickListener
    private val differCallback = object : DiffUtil.ItemCallback<HousesModel>() {
        override fun areItemsTheSame(oldItem: HousesModel, newItem: HousesModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HousesModel, newItem: HousesModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    fun setListener(listener: HousesClickListener) {
        mListener = listener
    }

    override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        val data = differ.currentList[position]

        when (holder) {
            is HousesViewHolder -> {
                holder.bind(data, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = CustomItemHouses(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return HousesViewHolder(view, mListener)
    }


}