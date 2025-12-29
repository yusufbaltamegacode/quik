package com.moez.QKSMS.manager

import android.content.Context
import com.scottyab.rootbeer.RootBeer

class SecurityHelper(private val context: Context) {
    fun isDeviceSafe(): Boolean {
        val rootBeer = RootBeer(context)
        // Root tespiti yapılırsa false döner
        return !rootBeer.isRooted
    }
}