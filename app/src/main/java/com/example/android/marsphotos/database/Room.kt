package com.example.android.marsphotos.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Database



@Dao
interface MarsDao{
    @Query("SELECT * FROM DatabaseMars")
    fun getMarsProperties(): LiveData<List<DatabaseMars>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(marsProperty: List<DatabaseMars>)

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