package com.example.moviepath

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class InMovieRecyclerViewAdapter (private val movies: List<InMovie>, private val listener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<InMovieRecyclerViewAdapter.MovieViewHolder>()
{
    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView){
        var item: InMovie? = null
        val movieTitle: TextView = mView.findViewById<View>(R.id.movie_title) as TextView
        val movieOverview: TextView = mView.findViewById<View>(R.id.movie_overview) as TextView
        val moviePoster: ImageView = mView.findViewById<View>(R.id.movie_poster) as ImageView

        override fun toString(): String{
            return movieTitle.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_in_my_movie, parent, false)

        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.item = movie
        holder.movieTitle.text = movie.title
        holder.movieOverview.text = movie.overview

        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500/" + movie.poster)
            .centerInside()
            .placeholder(R.drawable.reel)
            .into(holder.moviePoster)


        holder.mView.setOnClickListener{
            holder.item?.let { movie ->
                listener?.onItemClick(movie)
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }


}