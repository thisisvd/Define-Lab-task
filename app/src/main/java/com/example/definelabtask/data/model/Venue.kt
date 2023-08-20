package com.example.definelabtask.data.model

data class Venue(
    val allowMenuUrlEdit: Boolean,
    val beenHere: BeenHere,
    val categories: List<Category>,
    val contact: Contact,
    val createdAt: Int,
    val hasMenu: Boolean,
    val hasPerk: Boolean,
    val hereNow: HereNow,
    val id: String,
    val location: Location,
    val menu: Menu,
    val name: String,
    val referralId: String,
    val stats: Stats,
    val storeId: String,
    val url: String,
    val venueChains: List<Any>,
    val venuePage: VenuePage,
    val venueRatingBlacklisted: Boolean,
    val verified: Boolean
)