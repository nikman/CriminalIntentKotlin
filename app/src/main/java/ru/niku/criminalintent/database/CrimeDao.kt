package ru.niku.criminalintent.database

import androidx.room.Dao
import androidx.room.Query
import ru.niku.criminalintent.Crime
import java.util.*

@Dao
interface CrimeDao {

    @Query("SELECT * FROM crime")
    fun getCrimes(): List<Crime>

    @Query("SELECT * FROM Crime WHERE id=(:id)")
    fun getCrime(id: UUID): Crime?

}