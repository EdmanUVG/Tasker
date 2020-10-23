package com.example.tasker.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.tasker.R
import com.example.tasker.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.layoutAppName.setOnClickListener {
            findNavController().navigate(R.id.action_about_fragment_to_brand_fragment)
        }

        binding.layoutOfficialWebsite.setOnClickListener {
            val url = "https://www.uvg.edu.gt/"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        binding.layoutTermsOfService.setOnClickListener {
            val url = "https://www.uvg.edu.gt/nosotros/campus/"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        binding.layoutPrivacyPolicy.setOnClickListener {
            val url = "https://www.uvg.edu.gt"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        binding.layoutAcknowledgments.setOnClickListener {
            findNavController().navigate(R.id.action_about_fragment_to_acknowledgements_fragment)
        }
    }
}

// [OR-IEH-01]