package br.com.rodrigoamora.marvellapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.databinding.FragmentAboutBinding
import br.com.rodrigoamora.marvellapp.util.PackageInfoUtil

class AboutFragment: Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvVersionApp: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        tvVersionApp = binding.tvVersionApp

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvVersionApp.text = context?.let { getString(R.string.version_app, PackageInfoUtil.getVersionName(it)) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}