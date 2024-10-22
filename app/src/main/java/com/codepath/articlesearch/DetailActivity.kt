package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

class DetailActivity : AppCompatActivity() {
    private lateinit var profileImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var knownForTextView: TextView
    private lateinit var biographyTextView: TextView
    private lateinit var popularityTextView: TextView
    private lateinit var placeOfBirthTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        profileImageView = findViewById(R.id.profileImage)
        nameTextView = findViewById(R.id.personName)
        knownForTextView = findViewById(R.id.knownFor)
        biographyTextView = findViewById(R.id.biography)
        popularityTextView = findViewById(R.id.popularity)
        placeOfBirthTextView = findViewById(R.id.placeOfBirth)

        val person = intent.getSerializableExtra(PERSON_EXTRA) as Person

        if (person != null) {
            nameTextView.text = person.name
            biographyTextView.text = "Biography: ${person.biography ?: "N/A"}"
            popularityTextView.text = "Popularity: ${person.popularity ?: "N/A"}"
            placeOfBirthTextView.text = "Place of Birth: ${person.placeOfBirth ?: "Unknown"}"


            val knownForTitles = person.knownFor?.joinToString(", ") { it.displayTitle }
            knownForTextView.text = knownForTitles

            val profileImageUrl = "https://image.tmdb.org/t/p/w500/${person.profilePath}"
            Glide.with(this)
                .load(profileImageUrl)
                .into(profileImageView)
        } else {
            nameTextView.text = "No data available"
        }
    }
    private fun fetchPersonDetails(personId: Int) {
        val client = AsyncHttpClient()
        val personDetailUrl = "https://api.themoviedb.org/3/person/64?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"

        client.get(personDetailUrl, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e("DetailActivity", "Failed to fetch person details: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                try {
                    val biography = json.jsonObject.getString("biography")
                    val placeOfBirth = json.jsonObject.getString("place_of_birth")

                    biographyTextView.text = biography ?: "Biography not available"
                    placeOfBirthTextView.text = placeOfBirth ?: "Place of birth not available"
                } catch (e: JSONException) {
                    Log.e("DetailActivity", "Failed to parse person details: $e")
                }
            }
        })
    }
}
