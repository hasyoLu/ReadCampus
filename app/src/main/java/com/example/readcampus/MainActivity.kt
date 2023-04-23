package com.example.readcampus
import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.readcampus.databinding.ActivityMainBinding
import com.example.readcampus.db.querySpIsLogin
import com.example.readcampus.utils.SystemUtil
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navView: BottomNavigationView

    private var appBarConfiguration: AppBarConfiguration? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFindView()

        if( querySpIsLogin()  ){
            gotoMainAPP()
        }
    }

    private fun initFindView() {
        navView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main)
    }

    fun gotoMainAPP() {
//        navView.visibility = View.VISIBLE
//        navController.navigate(R.id.action_loginFragment_to_navigation_library)
//        requestPermission()
        gotoReader()
    }

    private fun gotoReader() {
        val intent = Intent(Intent.ACTION_VIEW)
        val packageName = "com.woodnoisu.reader"
        val className = "com.woodnoisu.reader.ui.main.MainActivity"
        intent.setClassName(packageName, className)
        startActivity(intent)
    }

    private fun requestPermission(){
        //权限要求
        val permission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(!it) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show()
            }
        }
        //申请权限
        permission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun destinationChangedListener() {
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.loginFragment -> {
//                    navView.visibility = View.GONE
//                }
//                else -> {
//                    navView.visibility = View.VISIBLE
//                }
//            }
//        }
    }
}