package ru.niku.criminalintent

import androidx.lifecycle.ViewModel
import android.util.Log

private const val TAG = "CrimeListViewModel"

class CrimeListViewModel : ViewModel() {

    //val crimes = mutableListOf<Crime>()

    /*init {
        for (i in 0 until 100) {
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.isSolved = i % 2 == 0
            //crime.requiresPolice = i % 2
            crimes += crime
        }

    }*/

    private val crimeRepository = CrimeRepository.get()
    //val crimes = crimeRepository.getCrimes()
    val crimesListLiveData = crimeRepository.getCrimes()

    //Log.i(TAG, "crimes")

}