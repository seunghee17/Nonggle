package com.capstone.nongglenonggle.presentation.view.signup

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
import com.capstone.nongglenonggle.core.design_system.NonggleTheme

class JsBridge(private val onData: (String) -> Unit) {
    @JavascriptInterface
    fun processDATA(data: String) {
        Log.d("TTAG", "고른 주소는 ${data}")
        onData(data)
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun FarmerAddressSearchScreen(
    navController: NavHostController,
    viewModel: SignupViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val effectFlow = viewModel.effect

   LaunchedEffect(true) {
        effectFlow.collect { effect ->
            when(effect) {
                is SignupContract.Effect.NavigateToBackScreen -> {
                    navController.popBackStack()
                }
                else -> {}
            }
        }
    }

    val webView = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            addJavascriptInterface(JsBridge(viewModel::getAddress), "Android")
            webViewClient = object: WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    view.evaluateJavascript("javascript:sample2_execDaumPostcode();", null)
                    viewModel.setLoading(false)
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    Log.e("TAG", "웹뷰 로드중 에러 발생 ${error}")
                }
            }
            viewModel.setLoading(true)
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
        if(uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(64.dp)
                    .align(Alignment.Center),
                color = NonggleTheme.colors.g1,
                trackColor = NonggleTheme.colors.m1,
            )
        }
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
