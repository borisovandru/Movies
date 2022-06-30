package com.borisov.movies.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.borisov.movies.R
import com.borisov.movies.domain.models.ActorsResponse

/**
 * @author Borisov Andrey on 29.06.2022
 **/
class ActorAdapter(private val delegate: Delegate?) :
    RecyclerView.Adapter<ActorViewHolder?>() {

    interface Delegate {
        /**
         * Событие наступает при выборе
         * актера из списка.
         * @param actor Актер
         */
        fun onItemClick(actor: ActorsResponse.Cast)
    }

    private val data = ArrayList<ActorsResponse.Cast>()

    fun setItems(newList: List<ActorsResponse.Cast>) {
        val result = DiffUtil.calculateDiff(
            DiffUtilCallback(
                data,
                newList as ArrayList<ActorsResponse.Cast>
            )
        )
        result.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newList)
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder =
        ActorViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.actor_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) =
        holder.bind(data[position], delegate)

    inner class DiffUtilCallback(
        private var oldItems: ArrayList<ActorsResponse.Cast>,
        private var newItems: ArrayList<ActorsResponse.Cast>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition].id == newItems[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}