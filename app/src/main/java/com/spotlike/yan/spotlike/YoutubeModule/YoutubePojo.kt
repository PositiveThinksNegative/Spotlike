package com.spotlike.yan.spotlike.YoutubeModule

/**
 * Created by yan on 2017-08-17.
 */
data class YoutubePojo (
    val nextPageToken: String,
    val items: ArrayList<YoutubePojoItem>
)

data class YoutubePojoItem (
        val id: YoutubePojoItemId,
        val snippet: YoutubePojoItemSnippet
)

data class YoutubePojoItemId (
        val kind: String,
        val channelId: String,
        val videoId: String
)

data class YoutubePojoItemSnippet (
        val publishedAt: String,
        val title: String,
        val description: String,
        val thumbnails: YoutubePojoItemThumbnail,
        val channelTitle: String
)

data class YoutubePojoItemThumbnail (
        val default: YoutubePojoItemThumbnailUrl,
        val medium: YoutubePojoItemThumbnailUrl,
        val high: YoutubePojoItemThumbnailUrl
)

data class YoutubePojoItemThumbnailUrl (
        val url: String
)