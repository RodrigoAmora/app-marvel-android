package br.com.rodrigoamora.marvellapp.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import br.com.rodrigoamora.marvellapp.R

class FragmentUtil {

    companion object {
        fun changeFragment(
            id: Int,
            fragment: Fragment,
            fragmentManager: FragmentManager,
            backstack: Boolean,
            bundle: Bundle?
        ) {
            val transaction = fragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.slide_out  // popExit
                )

            transaction.replace(id, fragment)

            if (backstack) {
                transaction.addToBackStack(null)
            }
            if (bundle != null) {
                fragment.arguments = bundle
            }

            transaction.commit()
        }
    }

}