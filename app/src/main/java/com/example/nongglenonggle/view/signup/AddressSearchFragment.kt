package com.example.nongglenonggle.view.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.databinding.FragmentAddressSearchBinding
import com.example.nongglenonggle.viewModel.signup.SignupViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddressSearchFragment : Fragment() {
    private val viewModel: SignupViewModel by activityViewModels()
    private lateinit var binding: FragmentAddressSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddressSearchBinding.inflate(inflater, container, false)
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
    }
     inner class BridgeInterface{//javascript->android
        @JavascriptInterface
        fun processDATA(data:String){
         //데이터값 유무 상태 변화 호출 동기적으로
         activity?.runOnUiThread()
         {
             viewModel.updateAddress(data)
             viewModel.navigateToDFragment()
         }
         }
        }
    }

