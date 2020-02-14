package ru.niku.criminalintent.database

import androidx.room.TypeConverter
import java.util.Date
import java.util.UUID

class CrimeTypeConverters {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinsEpoch: Long?): Date? {
        return millisSinsEpoch?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

}