package com.example.readcampus
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.readcampus.base.BaseFragment
import com.example.readcampus.databinding.FragmentMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : BaseFragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        val navView: BottomNavigationView = binding.navView

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_bookCity, R.id.navigation_bookshelf, R.id.navigation_my
            )
        )
        navView.setupWithNavController(findNavController())
        return binding.root
    }

}