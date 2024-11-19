package br.com.rodrigoamora.marvellapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.databinding.FragmentAboutBinding
import br.com.rodrigoamora.marvellapp.ui.activity.MainActivity
import br.com.rodrigoamora.marvellapp.util.PackageInfoUtil

class AboutFragment: Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvVersionApp: TextView

    private val mainActivity: MainActivity by lazy {
        activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this._binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = this.binding.root

        this.tvVersionApp = this.binding.tvVersionApp

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val versionName = PackageInfoUtil.getVersionName(this.mainActivity)
        this.tvVersionApp.text = getString(R.string.version_app, versionName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

}
