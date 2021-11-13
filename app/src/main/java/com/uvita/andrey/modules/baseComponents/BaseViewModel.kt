package com.uvita.andrey.modules.baseComponents

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(){
    fun onResume() {}
    fun onStart() {}
    fun onStop() {}
}