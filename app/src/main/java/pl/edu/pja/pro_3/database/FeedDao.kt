package pl.edu.pja.pro_3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface FeedDao {
    @Query("Select * from FeedDTo;")
    fun getall(): List<FeedDTo>

    @Query("Select title from FeedDTo;")
    fun getalltitles(): List<String>

    @Insert
    fun insert(feed: FeedDTo)

    @Query("SELECT * FROM FeedDTo where title=:title")
    fun findByName(title: String): FeedDTo

    @Query("Delete from FeedDTo where id= :id ")
    fun delete(id: Long)

    @Update
    fun update(feed: FeedDTo): Int
}