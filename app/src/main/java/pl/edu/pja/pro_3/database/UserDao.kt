package pl.edu.pja.pro_3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("Select * from UserDto;")
    fun getall(): List<UserDto>

    @Query("Select * from UserDto where id=:id;")
    fun getWithId(id: Long): List<UserDto>
    @Query("Select * from UserDto where email=:email;")
    fun getWithEmail(email: String): List<UserDto>
    @Insert
    fun insert(user: UserDto)

    @Update
    fun update(user: UserDto): Int
}