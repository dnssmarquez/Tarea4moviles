package com.example.ejer2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.tarea4_2.R
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        // OpenStreetMap Fragment
        adapter.addFragment(MapFragment.newInstance("openstreetmap"), "OpenStreetMap")

        // Placeholder fragment for Google Maps
        adapter.addFragment(MapFragment.newInstance("googlemaps"), "Google Maps")

        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Optional: Code to run when the page is scrolled
            }

            override fun onPageSelected(position: Int) {
                Log.d("MainActivity", "Page selected: $position")

                // Launch appropriate activity based on tab
                if (adapter.getPageTitle(position) == "OpenStreetMap") {
                    val intent =
                        Intent(this@MainActivity, OpenStreetMapActivity::class.java)
                    startActivity(intent)
                } else if (adapter.getPageTitle(position) == "Google Maps") {
                    val intent = Intent(this@MainActivity, GoogleMapsActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Optional: Code to run when the scroll state changes
            }
        })
    }

    internal class ViewPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }
}

// MapFragment to handle both Google Maps and OpenStreetMap
class MapFragment : Fragment() {

    private var mapType: String? = null
    private var webViewGoogleMaps: WebView? = null // Make WebView nullable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mapType = it.getString(ARG_MAP_TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // We don't need to inflate any layout here, as the activities will handle the map display
        return View(context)
    }

    companion object {
        private const val ARG_MAP_TYPE = "map_type"

        @JvmStatic
        fun newInstance(mapType: String) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_MAP_TYPE, mapType)
                }
            }
    }
}