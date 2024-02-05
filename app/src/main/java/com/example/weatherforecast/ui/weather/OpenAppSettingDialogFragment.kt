package com.example.weatherforecast.ui.weather

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.weatherforecast.R

class OpenAppSettingDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val listener = DialogInterface.OnClickListener { _, which ->
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(RESPONSE_KEY to which))
        }

        val builder = AlertDialog.Builder(requireContext())
            .setPositiveButton(R.string.button_open_settings, listener)
            .setNeutralButton(R.string.button_cancel, listener)
            .create()
        builder.setView(layoutInflater.inflate(R.layout.dialog_gps_settings, null))
        builder.show()
        return builder
    }

    companion object {
        val TAG = OpenAppSettingDialogFragment::class.java.simpleName
        val REQUEST_KEY = "$TAG:defaultRequestKey"
        const val RESPONSE_KEY = "RESPONSE"

        fun show(manager: FragmentManager){
            OpenAppSettingDialogFragment().show(manager, TAG)
        }

        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (Int) -> Unit){
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner) { _, result ->
                listener.invoke(result.getInt(RESPONSE_KEY))
            }
        }
    }
}