package com.example.gotalk.data.storage

interface FragmentNavigator {

    fun navigateTo(vararg params: String)
    fun navigateToBack()
}