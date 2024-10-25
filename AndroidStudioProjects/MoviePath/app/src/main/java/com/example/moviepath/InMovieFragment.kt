package com.example.moviepath

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class InMovieFragment : Fragment(), OnListFragmentInteractionListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_in_movie_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context

        recyclerView.layoutManager = LinearLayoutManager(context)
        updateAdapter(progressBar, recyclerView)
        return view
    }
    private fun updateAdapter(progressBar: ProgressBar, recyclerView: RecyclerView){
        progressBar.visibility = View.VISIBLE

        val client = AsyncHttpClient()
        val url = "https://api.themoviedb.org/3/movie/now_playing?"

        val params = RequestParams().apply {
            put("api_key", API_KEY)
            put("language", "en-US")
            put("page", "1")
        }

        client[url, params, object : JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                progressBar.visibility = View.GONE

                Log.d("RESPONSE", json.toString())
                val resultsJSON: JSONArray = json?.jsonObject?.get("results") as JSONArray
                val moviesRawJSON: String = resultsJSON.toString()

                Log.d("MOVIES", moviesRawJSON)
                val gson = Gson()
                val arrayMovieType = object : TypeToken<List<InMovie>>() {}.type
                val models : List<InMovie> = gson.fromJson(moviesRawJSON, arrayMovieType)
                recyclerView.adapter = InMovieRecyclerViewAdapter(models, this@InMovieFragment)

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e("InMovieFragment", "Failed to fetch movies: StatusCode = $statusCode, Response = $response")

                Toast.makeText(context, "Error fetching movie data.", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        }]

    }
    override fun onItemClick(item: InMovie){
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }
}