package com.sandbox.androidsandbox.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sandbox.androidsandbox.R
import com.sandbox.androidsandbox.dto.AffirmationsResponse
import com.sandbox.androidsandbox.rest.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        API()
            .affirmationsApi()
            .getAffirmations()
            .enqueue(object : Callback<AffirmationsResponse> {
                override fun onResponse(call: Call<AffirmationsResponse>,
                                        response: Response<AffirmationsResponse>) {
                    Log.e(this.javaClass.simpleName, call.request().toString())
                    textView.text = response.body()?.affirmation
                }

                override fun onFailure(call: Call<AffirmationsResponse>, throwable: Throwable) {
                    Log.e(this.javaClass.simpleName, throwable.toString())
                    throwable.printStackTrace()
                }
            })
        val button: Button = root.findViewById(R.id.button_home)
        var clickedTimes = 0
        button.setOnClickListener {
            Toast.makeText(
                this.context,
                "Button has been clicked ${++clickedTimes} times",
                Toast.LENGTH_SHORT
            ).show()
        }

        return root
    }
}


