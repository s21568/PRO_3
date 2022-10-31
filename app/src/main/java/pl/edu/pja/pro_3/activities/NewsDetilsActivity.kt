package pl.edu.pja.pro_3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import pl.edu.pja.pro_3.adapters.mAuth
import pl.edu.pja.pro_3.database.FeedDb
import pl.edu.pja.pro_3.database.UserDto
import pl.edu.pja.pro_3.databinding.ActivityNewsDetilsBinding
import java.util.concurrent.Executors

private val executor by lazy { Executors.newSingleThreadExecutor() }
private var bundle: Bundle? = null
private var urlToOpen = ""

class NewsDetilsActivity : AppCompatActivity() {
    private val view by lazy { ActivityNewsDetilsBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)
        setUpWebView()

        view.floatingActionButton.setOnClickListener { finish() }
    }

    private fun setUpWebView() {
        bundle = intent.extras
        urlToOpen = bundle!!.getString("url").toString()
        view.webView.loadUrl(urlToOpen)
        executor.submit {
            val db = FeedDb.open(view.root.context)
            val curUser: UserDto = db.user.getWithEmail(mAuth.currentUser?.email.toString()).first()
            curUser.readedArticles += "~~$urlToOpen"
            db.user.update(curUser)
            db.close()
        }
    }

}