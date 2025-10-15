package co.algorand.app

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.michaeltchuang.walletsdk.core.network.domain.AndroidContextHolder

class AndroidApp : Application() {
    companion object {
        lateinit var instance: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AndroidContextHolder.applicationContext = applicationContext
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App() }
    }
}

internal actual fun openUrl(url: String?) {
    val uri = url?.let { Uri.parse(it) } ?: return
    val intent =
        Intent().apply {
            action = Intent.ACTION_VIEW
            data = uri
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    AndroidApp.instance.startActivity(intent)
}
