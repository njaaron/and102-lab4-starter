package com.codepath.articlesearch

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

const val PERSON_EXTRA = "PERSON_EXTRA"

class PersonAdapter(private val context: Context, private val people: List<Person>) :
    RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = people[position]
        holder.bind(person)
    }

    override fun getItemCount() = people.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val profileImageView = itemView.findViewById<ImageView>(R.id.profileImage)
        private val nameTextView = itemView.findViewById<TextView>(R.id.personName)
        private val knownForTextView = itemView.findViewById<TextView>(R.id.knownFor)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val person = people[absoluteAdapterPosition]
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(PERSON_EXTRA, person)
            context.startActivity(intent)
        }

        fun bind(person: Person) {
            nameTextView.text = person.name
            val knownForTitles = person.knownFor?.joinToString(", ") { it.displayTitle }
            knownForTextView.text = knownForTitles
            val profileImageUrl = "https://image.tmdb.org/t/p/w500/${person.profilePath}"
            Glide.with(context)
                .load(person.profileImageUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
                .into(profileImageView)
        }
    }
}
