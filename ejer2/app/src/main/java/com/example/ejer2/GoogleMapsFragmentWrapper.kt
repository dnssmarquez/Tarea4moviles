package com.example.ejer2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class GoogleMapsFragmentWrapper : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Start the GoogleMapsActivity
        val intent = Intent(activity, GoogleMapsActivity::class.java)
        startActivity(intent)

        // Return a placeholder view (important to prevent errors)
        return View(context)
    }
}