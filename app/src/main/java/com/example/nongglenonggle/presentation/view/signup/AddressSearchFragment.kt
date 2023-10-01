package com.example.nongglenonggle.presentation.view.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.databinding.FragmentAddressSearchBinding
import com.example.nongglenonggle.presentation.viewModel.signup.SignupViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
class AddressSearchFragment : BaseFragment<FragmentAddressSearchBinding>(R.layout.fragment_address_search) {
    private val viewModel: SignupViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= super.onCreateView(inflater, container, savedInstanceState)
        return view
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
             activity?.runOnUiThread() {
                 viewModel.updateAddress(data)
                 moveToNext()
             }
         }
        }

    fun moveToNext(){
        replaceFragment(SignupDFragment(), R.id.signup_fragmentcontainer)
    }

    }

