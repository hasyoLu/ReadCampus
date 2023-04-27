package com.example.readcampus.ui.login
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.haa_roh.db.INPUTRIGHT
import com.example.haa_roh.db.NOTFWTHEAUTOCODE
import com.example.readcampus.MainActivity
import com.example.readcampus.R
import com.example.readcampus.base.BaseFragment
import com.example.readcampus.databinding.FragmentLoginBinding
import com.example.readcampus.utils.afterTextChanged

class LoginFragment : BaseFragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var phone : EditText
    private lateinit var code : EditText
    private lateinit var login : Button
    private lateinit var loading : ProgressBar
    private lateinit var authCode : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        phone = binding.username
        code = binding.password
        login = binding.login
        loading = binding.loading
        authCode = binding.authCode

        binding.toMainFragment.setOnClickListener {
            (activity as MainActivity).gotoMainAPP()
        }
    }

    override fun initData() {
        initInputData()
        initAuthCodeData()
        initVerifyData()
    }

    private fun initAuthCodeData() {
        authCode.setOnClickListener{
            NOTFWTHEAUTOCODE = false
            loginViewModel.loginSendCode(phone.text.toString().trim() )
        }
        loginViewModel.loginGetAutoCode.observe(viewLifecycleOwner, Observer {
            val loginAutoCode = it ?: return@Observer
            if( loginAutoCode.error != null ){
                Toast.makeText(activity,"发送失败",Toast.LENGTH_LONG).show()
            }else{
                login.isEnabled = true
            }
        })
        loginViewModel.loginCountNumber.observe(viewLifecycleOwner, Observer {
            val countNumber = it ?: return@Observer
            if(countNumber.textCountNumber != null){
                authCode.text = countNumber.textCountNumber
            }
            authCode.setBackgroundResource(countNumber.textColor)
            authCode.isEnabled = countNumber.isEnable
        })
    }

    private fun initInputData() {
        //输入发生改变后，调用ViewModel中的方法区判断输入是否有异常
        phone.afterTextChanged {
            loginViewModel.loginDataChanged(
                phone.text.toString().trim(),
                code.text.toString().trim()
            )
        }
        code.afterTextChanged {
            loginViewModel.loginDataChanged(
                phone.text.toString().trim(),
                code.text.toString().trim()
            )
        }
        loginViewModel.loginFormState.observe(this, Observer {
            val loginState = it ?:  return@Observer
            //根据登录状态 设置 login按钮是否可以点击
            //login.isEnabled = loginState.isDataValid
            if (loginState.usernameError != null) {
                phone.error = getString(loginState.usernameError)
            }
            else {
                //可以发验证码了
                if(NOTFWTHEAUTOCODE){
                    authCode.isEnabled = loginState.isUserNameValid
                    authCode.setBackgroundResource(R.color.authCode)
                }
                INPUTRIGHT = loginState.isUserNameValid && loginState.isPasswordValid
            }
        })
    }

    private fun initVerifyData() {

        login.setOnClickListener{
            if(!INPUTRIGHT){
                showErrorToast(activity,getString(R.string.inputError))
                return@setOnClickListener
            }else{
                //点击登录后设置 等待 可见
                loading.visibility = View.VISIBLE
                loginViewModel.loginVerificationResult(phone.text.toString().trim(),
                    code.text.toString().trim())
            }
        }
        loginViewModel.loginResult.observe(this, Observer {
            val result = it ?: return@Observer
            if(result.success){
                showSuccessToast(activity,"登录成功")
                (activity as MainActivity).gotoMainAPP()
            }else{
                showErrorToast(activity,getString(R.string.sendAutoCodeError))
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}