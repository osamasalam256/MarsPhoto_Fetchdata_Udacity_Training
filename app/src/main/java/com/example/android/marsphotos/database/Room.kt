package com.example.android.marsphotos.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Database
import com.example.android.marsphotos.network.MarsApiFilter
import retrofit2.http.DELETE


@Dao
interface MarsDao{
    @Query("SELECT * FROM DatabaseMars")
    fun getMarsProperties(): LiveData<List<DatabaseMars>>

    @Query("SELECT * FROM DatabaseMars WHERE type= :filter")
    fun getFilteredProperties(filter: String): LiveData<List<DatabaseMars>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(marsProperty: List<DatabaseMars>)

    @Query("DELETE FROM DatabaseMars")
    fun clear()

}

@Database(entities = [DatabaseMars::class], version = 1, exportSchema = false)
abstract class MarsDatabase: RoomDatabase(){
    abstract val marsDao: MarsDao
}

private lateinit var INSTANCE: MarsDatabase

fun getDatabase(context: Context): MarsDatabase{
    synchronized(MarsDatabase::class.java){
        if (!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            MarsDatabase::class.java, "marsdatabase").build()
        }
    }
    return INSTANCE
}