package com.borisov.movies.ui.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.borisov.movies.R
import com.borisov.movies.domain.models.MoviesResponse

/**
 * @author Borisov Andrey on 28.06.2022
 **/
class MovieAdapter(private val delegate: Delegate?) :
    RecyclerView.Adapter<MovieViewHolder?>() {

    interface Delegate {
        /**
         * Событие наступает при выборе
         * фильма из списка.
         * @param movie Фильм
         */
        fun onItemClick(movie: MoviesResponse.Movie)

        /**
         * Получить следующую порцию фильмов
         */
        fun getMoreMovies()
    }

    private val data = ArrayList<MoviesResponse.Movie>()

    fun setItems(newList: List<MoviesResponse.Movie>) {
        val result = DiffUtil.calculateDiff(
            DiffUtilCallback(
                data,
                newList as ArrayList<MoviesResponse.Movie>
            )
        )
        result.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newList)
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(data[position], delegate, position, data.size)

    inner class DiffUtilCallback(
        private var oldItems: ArrayList<MoviesResponse.Movie>,
        private var newItems: ArrayList<MoviesResponse.Movie>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition].id == newItems[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}