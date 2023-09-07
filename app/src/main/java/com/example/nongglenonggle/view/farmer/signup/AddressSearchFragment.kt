package com.example.nongglenonggle.view.farmer.signup

import android.os.Bundle
import android.os.Handler
import android.provider.Settings.Global
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentAddressSearchBinding
import com.example.nongglenonggle.viewModel.farmer.signup.AddressSearchViewModel
import com.example.nongglenonggle.viewModel.farmer.signup.SignupViewModel
import com.example.nongglenonggle.viewModel.farmer.signup.SignupDViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AddressSearchFragment : Fragment() {
    private lateinit var viewModel: AddressSearchViewModel
    //private lateinit var viewModelTransaction: SignupDViewModel
    private lateinit var binding: FragmentAddressSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddressSearchBinding.inflate(inflater, container, false)
        //viewModelTransaction = ViewModelProvider(this).get(SignupDViewModel::class.java)
        viewModel = ViewModelProvider(this).get(AddressSearchViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webview = binding.webview
        webview.settings.javaScriptEnabled = true
        webview.addJavascriptInterface(BridgeInterface(),"Android")
        webview.setWebViewClient(object:WebViewClient(){
            override fun onPageFinished(view: WebView, url: String) {
                //android->javascript 함수 호출
                GlobalScope.launch(Dispatchers.Main) {
                    webview.loadUrl("javascript:sample2_execDaumPostcode();")
                }
            }
        })
        //최초 로드
        webview.loadUrl("https://capstoneproject-11911.web.app")


        //화면 변화 옵저빙
        viewModel.navigateToframentD.observe(viewLifecycleOwner){
            //livedata의 값을 나타내는 변수
                navigate->
            if(navigate){
                //false로 변경하여 다음번에 livedata값이 변경되었을때 이 블록 실행 안됨 즉 값의 초기화 기능
                viewModel.DoneNavigating()
                val fragmentD = SignupDFragment()
                val signupViewModel : SignupViewModel by activityViewModels()
                signupViewModel.navigateTo(fragmentD)
            }
        }
        viewModel.isdata.observe(viewLifecycleOwner){
            istrue->
            println("${viewModel.addressData.value}")
        }

    }
     inner class BridgeInterface{//javascript->android
        @JavascriptInterface
        fun processDATA(data:String){
         //데이터값 유무 상태 변화 호출 동기적으로
         activity?.runOnUiThread()
         {
             viewModel.updateAddressData(data)
             viewModel.updateisData()
             viewModel.onMoveTo()
         }
            //viewModel.onMoveTo()
         }
        }
    }

