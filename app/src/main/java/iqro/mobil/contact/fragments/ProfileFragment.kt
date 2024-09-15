package iqro.mobil.contact.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import iqro.mobil.contact.R
import iqro.mobil.contact.databinding.ProfileFragmentBinding

class ProfileFragment : androidx.fragment.app.Fragment() {
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!
    val requestSmsPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}
    val requestCallPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = ProfileFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = arguments?.getString("name")
        binding.pearsonName.text = name
        val num = arguments?.getString("number")
        binding.pearsonNumber.text = num
        binding.pearsonNum.text = num
        if (arguments?.getString("image") == "0") {
            try {
                val a = ((name?.trim())?.split(" ")?.get(0))?.get(0)
                val b = ((name?.trim())?.split(" ")?.get(1))?.get(0)
                binding.textH.text = "$a$b"
                binding.pearsonImage.setImageResource(R.drawable.rectan_black)
            } catch (e: Exception) {
                binding.textH.text = (name?.trim())?.get(0).toString()
                binding.pearsonImage.setImageResource(R.drawable.rectan_black)
            }
        } else {
            binding.textH.text = ""

            binding.pearsonImage.setImageURI(Uri.parse(arguments?.getString("image")))
        }

        binding.phone.setOnClickListener {
            if (num != null) {
                checkCallPermission(num)
            }
        }

        binding.message.setOnClickListener {
            if (num != null) {
                checkSmsPermission(num)
            }
        }

        binding.imageView3.setOnClickListener {
            if (num != null) {
                checkSmsPermission(num)
            }
        }

        binding.imageView5.setOnClickListener {
            if (num != null) {
                checkCallPermission(num)
            }
        }

        binding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }

    private fun checkCallPermission(number: String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            makeCall(number)
        } else {
            requestCallPermission.launch(Manifest.permission.CALL_PHONE)
        }
    }

    private fun checkSmsPermission(number: String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            makeSms(number)
        } else {
            requestSmsPermission.launch(Manifest.permission.SEND_SMS)
        }
    }

    private fun makeCall(number: String) {
        Intent(Intent.ACTION_CALL, Uri.parse("tel:$number")).apply {
            startActivity(this)
        }
    }

    private fun makeSms(number: String) {
        Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$number")).apply {
            startActivity(this)
        }
    }

}

