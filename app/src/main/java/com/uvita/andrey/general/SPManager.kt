package com.uvita.andrey.general

import android.content.Context
import android.content.SharedPreferences


class SPManager(val context: Context) {
// TODO could implement a storage for last search
    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("some_app_preferences", Context.MODE_PRIVATE)

}