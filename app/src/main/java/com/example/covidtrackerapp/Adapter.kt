package com.example.covidtrackerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val statelist:List<StateModel>) : RecyclerView.Adapter<Adapter.StateRVViewHolder>() {
    class StateRVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val stateNameTV: TextView = itemView.findViewById(R.id.idRVStates);
        val casesTV: TextView=itemView.findViewById(R.id.idTVCases);
        val deathsTV: TextView=itemView.findViewById(R.id.idTVDeaths);
        val recoverdTV: TextView=itemView.findViewById(R.id.idTVRecovered);

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateRVViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.activity_second,parent,false)
        return StateRVViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return statelist.size
    }

    override fun onBindViewHolder(holder: StateRVViewHolder, position: Int) {
        val stateData = statelist[position]
        holder.casesTV.text= stateData.cases.toString()
        holder.deathsTV.text = stateData.deaths.toString()
        holder.recoverdTV.text = stateData.recovered.toString()
    }

}