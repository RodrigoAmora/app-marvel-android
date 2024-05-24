package br.com.rodrigoamora.marvellapp.ui.activity

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.rodrigoamora.marvellapp.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    fun showError(responseCode: Int) {
        val messageError = when (responseCode) {
            403 -> {
                getString(R.string.error_access_denied)
            }

            in 500..599 -> {
                getString(R.string.error_service_unavailable)
            }

            else -> {
                getString(R.string.error_cant_was_possible_perform_operation)
            }
        }

        this.showToast(messageError)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showSnackBar(message: String, rootView: View) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
    }

}
