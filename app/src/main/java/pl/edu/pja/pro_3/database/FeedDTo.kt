package pl.edu.pja.pro_3.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FeedDTo(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    val link: String,
    val thumbnail: String,
    val pubDate: String,
) {
}