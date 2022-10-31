package pl.edu.pja.pro_3.models

import android.graphics.BitmapFactory
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import pl.edu.pja.pro_3.database.FeedDTo
import java.util.*

@Entity
class RSSFeedModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val feed: Any,
    val items: List<FeedDTo>,
) {}

