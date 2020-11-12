package com.example.androidmidterm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Volley uses internet permissions which needs to be enabled in the AndroidManifest.xml
        val queue = Volley.newRequestQueue(this)

        val url = "https://csfinal-295218.uc.r.appspot.com/"

        // TODO: change this to your url after you have endpoints
        button.setOnClickListener {
            val postParameters = JSONObject()
            try {
                val size: Int = sizeEntry.text.toString().toInt()
                val eaf: Int = eafEntry.text.toString().toInt()
                postParameters.put("size", size)
                postParameters.put("EAF", eaf)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            // OU calendar returns a Json Array, if your website returns a Json Object then use JsonObjectRequest
            val request = JsonObjectRequest(Request.Method.POST, url, postParameters,
                    Response.Listener<JSONObject> { response ->
                        val event = response
                        // eventTitle is the id for the textbox in activity_main.xml
                        modeResponse.text = " Mode: ${event.getString("mode")}"
                        effortResponse.text = "Effort: ${event.getString("effort")}"
                        timeResponse.text = "Time: ${event.getString("time")}"
                        staffResponse.text = "Staff: ${event.getString("staff")}"
                    },
                    Response.ErrorListener { staffResponse.text = "That didn't work" })
            queue.add(request)
        }
    }
}


/*
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Volley uses internet permissions which needs to be enabled in the AndroidManifest.xml
        val queue = Volley.newRequestQueue(this)

        // OU Calendar url
        // TODO: change this to your url after you have endpoints
        val url = "https://calendar.ou.edu/live/json/events"

        // OU calendar returns a Json Array, if your website returns a Json Object then use JsonObjectRequest
        val stringRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                val event = response[0] as JSONObject
                // eventTitle is the id for the textbox in activity_main.xml
                eventTitle.text = "Event title is : ${event.getString("title")}"
            },
            Response.ErrorListener { eventTitle.text = "That didn't work" })

        // button is the id for the button in activity_main.xml
        button.setOnClickListener {
            queue.add(stringRequest)
        }
    }
}
 */
