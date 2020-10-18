package com.utsman.unsplashcompose.model

sealed class Result {
    object Loading : Result()
    data class Photo(
        var id: String = "",
        var urls: Url = Url(),
        var user: User = User(),
        var color: String = "",
        var tags: List<Tag> = emptyList()
    ) : Result() {

        data class Url(
            var raw: String = "",
            var full: String = "",
            var regular: String = "",
            var small: String = "",
            var thumb: String = ""
        )

        data class User(
            var id: String = "",
            var name: String = ""
        )

        data class Tag(
            var title: String = ""
        )
    }
}