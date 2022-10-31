package pl.edu.pja.pro_3.models

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.edu.pja.pro_3.R

class NewsCard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_item_card)
    }
}