package com.codepath.articlesearch

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PopularPeopleResponse(
    @SerialName("results")
    val results: List<Person>?
)

@Keep
@Serializable
data class Person(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String?,
    @SerialName("known_for")
    val knownFor: List<KnownFor>?,
    @SerialName("profile_path")
    val profilePath: String?,
    @SerialName("biography")
    val biography: String? = null,
    @SerialName("popularity")
    val popularity: Double?,
    @SerialName("place_of_birth")
    val placeOfBirth: String? = null
) : java.io.Serializable {
    val profileImageUrl = "https://image.tmdb.org/t/p/w500/$profilePath"
}

@Keep
@Serializable
data class KnownFor(
    @SerialName("title")
    val title: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("poster_path")
    val poster_path: String? = null
) : java.io.Serializable{
    val displayTitle: String
        get() = title ?: name ?: "Unknown"
}
