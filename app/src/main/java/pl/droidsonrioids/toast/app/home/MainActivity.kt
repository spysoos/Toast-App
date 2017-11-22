package pl.droidsonrioids.toast.app.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu

import kotlinx.android.synthetic.main.activity_main.*
import pl.droidsonrioids.toast.R

const val EVENTS_TAB_INDEX = 0
const val LECTURERS_TAB_INDEX = 1
const val CONTACT_TAB_INDEX = 2

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupViewPager()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupViewPager() {
        homeViewPager.adapter = HomePagerAdapter(supportFragmentManager)
        with(homeTabLayout) {
            setupWithViewPager(homeViewPager)
            getTabAt(EVENTS_TAB_INDEX)?.setIcon(R.drawable.ic_tab_events_selector)
            getTabAt(LECTURERS_TAB_INDEX)?.setIcon(R.drawable.ic_tab_lecturers_selector)
            getTabAt(CONTACT_TAB_INDEX)?.setIcon(R.drawable.ic_tab_contact_selector)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}
