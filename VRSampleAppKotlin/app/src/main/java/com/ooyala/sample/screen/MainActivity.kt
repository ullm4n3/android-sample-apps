package com.ooyala.sample.screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ooyala.sample.R
import com.ooyala.sample.VideoFragment
import com.ooyala.sample.interfaces.ItemClickedInterface
import com.ooyala.sample.utils.VideoItemType
import com.ooyala.sample.utils.VideoData
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), ItemClickedInterface {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    setupToolbar()
    showRecyclerFragment()
    supportFragmentManager.addOnBackStackChangedListener { onBackStackChanged() }
  }

  override fun onBackPressed() {
    if (supportFragmentManager.backStackEntryCount >= 1) {
      supportFragmentManager.popBackStack()
    } else {
      super.onBackPressed()
    }
  }

  override fun onItemClicked(data: VideoData) {
    if (data.type == VideoItemType.VIDEO) {
      openVideoFragment(data)
    }
  }

  private fun openVideoFragment(data: VideoData) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.container, VideoFragment(data), VideoFragment.TAG).addToBackStack(null).commit()
    toolbar.title = data.title
  }

  private fun showRecyclerFragment() {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.container, VideoRecyclerFragment(), VideoRecyclerFragment.TAG).commit()
  }

  private fun setupToolbar() {
    setSupportActionBar(toolbar)
    toolbar?.bringToFront()
    toolbar.setNavigationOnClickListener { onBackPressed() }
  }

  private fun onBackStackChanged() {
    val isBackStackNonEmpty = supportFragmentManager.backStackEntryCount > 0
    supportActionBar?.setDisplayHomeAsUpEnabled(isBackStackNonEmpty)
    if (!isBackStackNonEmpty) {
      toolbar.title = getString(R.string.app_name)
    }
  }
}
