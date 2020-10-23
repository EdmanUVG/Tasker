package com.example.tasker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.tasker.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        if (currentUser != null) {
            textName.text = currentUser.displayName
            textEmail.text = currentUser.email
            Glide.with(this)
                .load(currentUser.photoUrl)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .fallback(R.drawable.profile)
                .transform(CircleCrop())
                .into(imageProfile)
        }

        layoutProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settings_fragment_to_profile_fragment)
        }

        layoutPremium.setOnClickListener {
            findNavController().navigate(R.id.action_settings_fragment_to_premium_fragment)
        }

        layoutSupport.setOnClickListener {
            findNavController().navigate(R.id.action_settings_fragment_to_support_fragment)
        }

        layoutAbout.setOnClickListener {
            findNavController().navigate(R.id.action_settings_fragment_to_about_fragment)
        }
    }

}