package com.spotlike.yan.spotlike.YoutubeModule

/**
 * Created by yan on 2017-08-17.
 */
data class YoutubeObject (
    val nextPageToken: String,
    val items: ArrayList<YoutubeItem>
)

data class YoutubeItem (
        val id: YoutubeItemId,
        val snippet: YoutubeItemSnippet
)

data class YoutubeItemId(
        val kind: String,
        val channelId: String,
        val videoId: String
)

data class YoutubeItemSnippet(
        val publishedAt: String,
        val title: String,
        val description: String,
        val thumbnails: YoutubeItemThumbnail,
        val channelTitle: String
)

data class YoutubeItemThumbnail(
        val default: YoutubeItemThumbnailUrl,
        val medium: YoutubeItemThumbnailUrl,
        val high: YoutubeItemThumbnailUrl
)

data class YoutubeItemThumbnailUrl(
        val url: String
)