package com.example.sho.myportalapp.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import com.example.sho.myportalapp.BuildConfig
import com.example.sho.myportalapp.R
import com.example.sho.myportalapp.adapter.NewsPagerAdapter
import com.example.sho.myportalapp.fragment.CardContentFragment
import com.example.sho.myportalapp.googleNewsApi.TabNameSources

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    private var viewPager: ViewPager? = null
    private var tabPosition: Int = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.news_toolber)
        setSupportActionBar(toolbar) // activityのアクションバーとして機能するようツールバーを設定

        viewPager = findViewById(R.id.news_view_pager)
        viewPager?.offscreenPageLimit = TabNameSources.tabNameList.size - 1
        val newsPagerAdapter = NewsPagerAdapter(supportFragmentManager)

        for (keyword in TabNameSources.tabNameList) {
            newsPagerAdapter.addFragment(CardContentFragment.newInstance(keyword), keyword)
        }
        viewPager?.adapter = newsPagerAdapter

        val tabLayout = findViewById<TabLayout>(R.id.news_tab_layout)
        tabLayout.addOnTabSelectedListener(this)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        tabPosition = tab.position
        if (BuildConfig.DEBUG) {
            Log.d("tapi", "タブ" + tabPosition.toString() + "が選択されました")
        }
        viewPager?.currentItem = tab.position
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {}

    override fun onTabReselected(tab: TabLayout.Tab) {}

}
