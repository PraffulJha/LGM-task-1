package com.example.covidtrackerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Request.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var worldcases:TextView
    lateinit var worldRecoverd:TextView
    lateinit var worldDeaths:TextView
    lateinit var IndiaCases:TextView
    lateinit var IndiaRecovered:TextView
    lateinit var IndiaDeaths:TextView
    lateinit var stateRV:RecyclerView
    lateinit var stateAdapter: Adapter
    lateinit var stateList: List<StateModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        worldcases=findViewById(R.id.WorldCases)
        worldRecoverd=findViewById(R.id.WorldRecovered)
        worldDeaths=findViewById(R.id.WorldDeaths)
        IndiaCases=findViewById(R.id.IndiaCases)
        IndiaDeaths=findViewById(R.id.IndiaDeaths)
        IndiaRecovered=findViewById(R.id.IndiaRecovered)
        stateRV=findViewById(R.id.idRVStates)
        stateList= ArrayList<StateModel>()
        getStateInfo()
        getWorldInfo()
    }
    private fun getStateInfo() {
        val url = "https://api.rootnet.in/covid19-in/stats/latest"
        val queue = Volley.newRequestQueue(this@MainActivity);
        val request =
                JsonObjectRequest(Method.GET, url, null, {

                    response ->
                    try {
                        val dataObj = response.getJSONObject("data")
                        val summaryobj = dataObj.getJSONObject("summary")
                        val cases: Int = summaryobj.getInt("total")
                        val recovered: Int = summaryobj.getInt("discharged")
                        val deaths: Int = summaryobj.getInt("deaths")

                        IndiaCases.text = cases.toString()
                        IndiaRecovered.text = recovered.toString()
                        IndiaDeaths.text = deaths.toString()

                        val regionalArray = dataObj.getJSONArray("regional")
                        for (i in 0 until regionalArray.length()) {
                            val regionalObj = regionalArray.getJSONObject(i)
                            val stateName: String = regionalObj.getString("loc")
                            val cases: Int = regionalObj.getInt("totalConfirmed")
                            val deaths: Int = regionalObj.getInt("deaths")
                            val recovered: Int = regionalObj.getInt("discharged")
                            val Model = StateModel(stateName, recovered, deaths, cases)
                            stateList = stateList + Model
                        }

                        stateAdapter = Adapter(stateList)
                        stateRV.layoutManager = LinearLayoutManager(this)
                        stateRV.adapter = stateAdapter
                    } catch (e: JSONException) {
                        e.printStackTrace();
                    }
                }, { error ->
                    {

                        Toast.makeText(this, "Fail to fetch data", Toast.LENGTH_SHORT).show()

                    }
                })
        queue.add(request)
    }

    private fun getWorldInfo(){
        val url = "https://corona.lmao.ninja/v3/covid-19/all"
        val queue= Volley.newRequestQueue(this@MainActivity)
        val request =
                JsonObjectRequest(Request.Method.GET, url,null,{
                    response->
                    try{
                        val worldcases : Int = response.getInt("cases")
                        val worldDeaths : Int = response.getInt("deaths")
                        val worldRecoverd : Int = response.getInt("recovered")
                       WorldCases.text = worldcases.toString()
                        WorldRecovered.text = worldRecoverd.toString()
                        WorldDeaths.text = worldDeaths.toString()
                    }
                    catch(e:JSONException){
                        e.printStackTrace()
                    }
                },
                        { error ->

                            Toast.makeText(this, "Fail to get data", Toast.LENGTH_SHORT).show()
                        }
                )
        queue.add(request)
    }
}