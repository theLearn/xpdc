package com.example.hongcheng.common.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by hongcheng on 17/5/30.
 */
class FragmentAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {

    var mFragments : List<Fragment>? = null
    var mTitles : List<String>? = null

    constructor(fm : FragmentManager, fragments : List<Fragment>, titles : List<String>) : this(fm)
    {
        this.mFragments = fragments
        this.mTitles = titles
    }

    override fun getItem(position: Int): Fragment? = if(mFragments == null) null else mFragments!![position]

    override fun getCount(): Int = if(mFragments == null) 0 else mFragments!!.size

    override fun getPageTitle(position: Int) : String? = if(mTitles == null) null else mTitles!![position]
}