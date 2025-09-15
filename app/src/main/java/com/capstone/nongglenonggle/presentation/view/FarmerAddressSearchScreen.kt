package com.capstone.nongglenonggle.presentation.view

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import android.util.Log
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.presentation.view.signup.SignupContract
import com.capstone.nongglenonggle.presentation.view.signup.SignupViewModel

class JsBridge(private val onData: (String) -> Unit) {
    @JavascriptInterface
    fun processDATA(data: String) {
        onData(data)
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun AddressSearchWebViewScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()

    val webView = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            addJavascriptInterface(JsBridge(onData = { address ->
                goBackFromAddressSearch(selectAddress = address, navController)
            }), "Android")
            webViewClient = object: WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    view.evaluateJavascript("javascript:sample2_execDaumPostcode();", null)
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    Log.e("TAG", "웹뷰 로드중 에러 발생 ${error}")
                }
            }
            loadUrl("https://nonggle.web.app/")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {webView}
        )
    }

    // 정리
    DisposableEffect(Unit) {
        onDispose {
            try { webView.removeJavascriptInterface("Android") } catch (_: Throwable) {}
            webView.stopLoading()
            webView.destroy()
        }
    }
}

fun goBackFromAddressSearch(selectAddress: String, navigatorController: NavHostController) {
    navigatorController.previousBackStackEntry
        ?.savedStateHandle
        ?.set("selectAddress", selectAddress)

    navigatorController.popBackStack()
}
