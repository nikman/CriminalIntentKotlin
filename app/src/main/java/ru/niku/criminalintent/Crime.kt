package ru.niku.criminalintent

import java.util.Date
import java.util.UUID

data class Crime(val id: UUID = UUID.randomUUID(),
                 var title: String = "",
                 var date: Date = Date(),
                 var isSolved: Boolean = false,
                 var requiresPolice: Int = 0)

