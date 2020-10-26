package ru.niku.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.niku.criminalintent.database.CrimeDatabase
import java.util.*


private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context){

    /*val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE Crime ADD COLUMN NewColumn INTEGER DEFAULT 0 NOT NULL")
        }
    }*/
    private val database : CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME)
        //.addMigrations(MIGRATION_1_2)
        .fallbackToDestructiveMigration()
        .build()

    private val crimeDao = database.crimeDao()

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()

    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)

    companion object {

        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE ?:
                    throw IllegalStateException("CrimeRepository must be first initialized")
        }

    }

}