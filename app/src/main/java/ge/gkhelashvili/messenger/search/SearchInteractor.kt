package ge.gkhelashvili.messenger.search

import ge.gkhelashvili.messenger.model.User

class SearchInteractor {

    fun getAllUsers(): List<User> {
        // TODO: Get users from firebase realtime database
        return listOf(
            User(username = "Giorgi", profession = "Developer"),
            User(username = "Saba", profession = "Analyst"),
            User(username = "David", profession = "Manager"),
            User(username = "Alexander", profession = "CEO"),
            User(username = "Nick", profession = "Journalist"),
            User(username = "John", profession = "IT"),
        )
    }
}