package dev.tsquaredapps.doit.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun upsert(task: Task)

    @Query("select * from task")
    fun getAllFLow(): Flow<List<Task>>

    @Delete
    suspend fun delete(task: Task)
}