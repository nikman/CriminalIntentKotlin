package ru.niku.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        crimeRecyclerView =
            view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter

        //updateUI()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimesListLiveData.observe(
            viewLifecycleOwner,
            Observer {
                    crimes -> crimes?.let {
                        Log.i(TAG, "Got crimes ${crimes.size}")
                        updateUI(crimes)}
            }
        )
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    private inner class CrimeHolder(view: View)
            : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var crime: Crime
        
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            //dateTextView.text = this.crime.date.toString()
            //dateTextView.text = this.crime.date
            //val dateFormatter = DateTimeFormatter.ofPattern("MM-dd, YYYY")
            val df = SimpleDateFormat("MMM dd, YYYY")
            dateTextView.text = df.format(this.crime.date)
            solvedImageView.visibility = if (this.crime.isSolved) {View.VISIBLE} else {View.GONE}
        }

        override fun onClick(v: View?) {

            Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_LONG).show()
            //crimeRecyclerView.adapter?.notifyItemMoved(0, 5)

        }

    }

    private inner class CrimeAdapter(var crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {

            val crimeViewType = getItemViewType(viewType)
            val resId: Int

            if (crimeViewType == 0) {
                resId = R.layout.list_item_crime
            } else {
                resId = R.layout.list_item_crime_police
            }

            val view = layoutInflater.inflate(resId, parent, false)

            return CrimeHolder(view)

        }

        override fun getItemCount() = crimes.count()

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            /*holder.apply {
                /*titleTextView.text = crime.title
                dateTextView.text = crime.date.toString()*/
            }*/
            holder.bind(crime)
        }

        override fun getItemViewType(position: Int): Int {
            val crime = crimes[position]
            return crime.requiresPolice
        }
    }

    private fun updateUI(crimes: List<Crime>) {

        //val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter

    }

}