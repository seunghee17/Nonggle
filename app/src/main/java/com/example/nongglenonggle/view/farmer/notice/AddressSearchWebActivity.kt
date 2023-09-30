package com.example.nongglenonggle.view.farmer.notice

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.nongglenonggle.R
import com.example.nongglenonggle.base.BaseActivity
import com.example.nongglenonggle.databinding.ActivityAddressSearchWebBinding
import com.example.nongglenonggle.viewModel.farmer.FarmerNoticeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddressSearchWebActivity : BaseActivity<ActivityAddressSearchWebBinding>(R.layout.activity_address_search_web) {
    private val viewModel: FarmerNoticeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        binding.backBtn.setOnClickListener{
            moveToNext()
        }

        val webview = binding.webview
        webview.settings.javaScriptEnabled = true
        webview.addJavascriptInterface(BridgeInterface(),"Android")
        webview.setWebViewClient(object: WebViewClient(){
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
        runOnUiThread {
//            val intent = Intent(this@AddressSearchWebActivity, NoticeActivity::class.java)
//            val addressData = data
//            intent.putExtra("addressData", addressData)
            saveData(data)
            moveToNext()
        }
    }
    }
    private fun saveData(data:String) {
        val pref:SharedPreferences = getSharedPreferences("pref", Activity.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = pref.edit()
        editor.putString("addressData",data)
        editor.apply()
        Log.e("love1","${data}")
    }
    fun moveToNext()
    {
        finish()
    }
}