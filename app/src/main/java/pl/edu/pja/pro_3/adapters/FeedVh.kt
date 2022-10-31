package pl.edu.pja.pro_3.adapters

import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.pro_3.database.FeedDTo
import pl.edu.pja.pro_3.database.UserDto
import pl.edu.pja.pro_3.databinding.NewsItemCardBinding
import java.net.URL
import java.util.concurrent.Executors

private val executor by lazy { Executors.newSingleThreadExecutor() }

class FeedVh(private val view: NewsItemCardBinding) : RecyclerView.ViewHolder(view.root) {

    fun bind(feedDto: FeedDTo, userDto: UserDto) {
        with(view) {
            if (userDto.readedArticles != "") {
                val list: List<String> = userDto.readedArticles.split("~~")
                if (list.contains(feedDto.link)) {
                    wholeCard.setBackgroundColor(Color.LTGRAY)
                }else{
                    wholeCard.setBackgroundColor(Color.WHITE)
                }
            }
            newsName.text = feedDto.title
            newsDate.text = feedDto.pubDate
            newsDesc.text = feedDto.description
            executor.submit {

                val url = URL(feedDto.thumbnail).openConnection().getInputStream()
                newsTumbnail.setImageBitmap(BitmapFactory.decodeStream(url))
            }
        }
    }
}