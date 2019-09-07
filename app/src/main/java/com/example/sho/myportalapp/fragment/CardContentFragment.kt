package com.example.sho.myportalapp.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sho.myportalapp.BuildConfig
import com.example.sho.myportalapp.R
import com.example.sho.myportalapp.adapter.NewsRecyclerAdapter
import com.example.sho.myportalapp.googleNewsApi.ArticleSource
import com.example.sho.myportalapp.googleNewsApi.NewsService
import com.example.sho.myportalapp.googleNewsApi.NewsSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * CardContent for NewsArticles in RecyclerView
 *
 * Created by sho on 2019/09/07.
 */

class CardContentFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    var arrayList = ArrayList<ArticleSource>()

    companion object {
        @JvmStatic
        fun newInstance(name: String) = CardContentFragment().apply {
            arguments = Bundle(1).apply {
                putString("TAB_NAME", name)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recycler_view, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val targetTabName = arguments.getString("TAB_NAME")
        fetchData(targetTabName)
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(context.getDrawable(R.drawable.divider))

        view?.let {
            recyclerView = it.findViewById<RecyclerView>(R.id.my_recycler_view).apply {
                adapter = NewsRecyclerAdapter(context, arrayList)
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                addItemDecoration(itemDecoration)
            }
        }
    }

    /**
     * @param tabName: Query keyword for tab names
     */
    private fun fetchData(tabName: String) {
        val service = NewsService.createService().addQuery(tabName,
                "3b007aa115c24b6e8f05844c7c33c621", "popularity", 100)

        val item = object : Callback<NewsSource> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<NewsSource>?, t: Throwable?) {
                if (BuildConfig.DEBUG) {
                    Log.d("tapi", t.toString())
                }
            }

            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(call: Call<NewsSource>?, response: Response<NewsSource>?) {
                response?.body()?.articles?.forEach { arrayList.add(it) }
                recyclerView.adapter.notifyDataSetChanged()
            }

        }
        service.enqueue(item)
    }
}


