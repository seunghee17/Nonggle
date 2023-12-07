package com.capstone.nongglenonggle.presentation.view.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.capstone.nongglenonggle.databinding.FragmentAddressSearchBinding
import com.capstone.nongglenonggle.presentation.viewModel.signup.SignupViewModel
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
                GlobalScope.launch(Dispatchers.Main) {
                    webview.loadUrl("javascript:sample2_execDaumPostcode();")
                }
            }
        })
        webview.loadUrl("https://capstoneproject-11911.web.app")
    }
     inner class BridgeInterface{
        @JavascriptInterface
        fun processDATA(data:String){
             activity?.runOnUiThread() {
                 viewModel.updateAddress(data)
                 moveToNext()
             }
         }
        }

    fun moveToNext(){
        findNavController().popBackStack()
    }

    }

