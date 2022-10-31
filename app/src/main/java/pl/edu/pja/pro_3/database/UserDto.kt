package pl.edu.pja.pro_3.database

import android.text.Editable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
class UserDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val email: String,
    var readedArticles: String
) {
    fun setread(string: String){
        readedArticles=string
    }
}