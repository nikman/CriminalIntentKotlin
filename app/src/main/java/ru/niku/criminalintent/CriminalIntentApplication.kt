package ru.niku.criminalintent

import android.app.Application
import android.os.Parcel
import android.os.Parcelable

class CriminalIntentApplication() : Application() {

    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}