package pl.edu.pja.pro_3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATABASE_FILENAME = "FeedList"

@Database(
    entities = [FeedDTo::class,UserDto::class],
    version = 1
)
abstract class FeedDb :RoomDatabase(){
    abstract val feed:FeedDao
    abstract val user:UserDao

    companion object {
        fun open(context: Context) = Room.databaseBuilder(
            context, FeedDb::class.java, DATABASE_FILENAME
        ).build()
    }
}