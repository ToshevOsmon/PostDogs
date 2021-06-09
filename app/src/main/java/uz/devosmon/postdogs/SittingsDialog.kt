package uz.devosmon.postdogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDialogFragment
import kotlinx.android.synthetic.main.settings_layout.*


public class SittingsDialog : AppCompatDialogFragment() {
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder: AlertDialog.Builder = AlertDialog.Builder(activity)

//        var inflater: LayoutInflater = requireActivity().layoutInflater
//        var view: View = inflater.inflate(R.layout.settings_layout, null)
//
//        builder.setView(view)
//
//

        builder.setTitle("Sittings")
        builder.setMessage("This is sittings message")
            .setIcon(R.drawable.dog)

        return builder.create()
    }
}