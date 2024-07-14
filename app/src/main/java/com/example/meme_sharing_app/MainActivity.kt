package com.example.meme_sharing_app

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.meme_sharing_app.databinding.ActivityMainBinding
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    var url:String?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        loadmeme()

        binding.next.setOnClickListener{
            loadmeme()
        }
        binding.share.setOnClickListener{
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val bundle = Bundle()
            bundle.putString(Intent.EXTRA_TEXT, "Check out this funny meme: $url")
            shareIntent.putExtras(bundle)
            startActivity(Intent.createChooser(shareIntent, "Share Meme"))

        }


    }
    private fun loadmeme(){
        binding.progressBar.visibility = View.VISIBLE
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
       url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { responce->
                url=responce.getString("url")
                Glide.with(this@MainActivity).load(url).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility= View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility= View.GONE
                        return false
                    }

                }).into(binding.imageView)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@MainActivity, "Error loading meme: ${error.message}", Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        queue.add(JsonObjectRequest)
    }
}