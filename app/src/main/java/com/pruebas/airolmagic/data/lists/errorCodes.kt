package com.pruebas.airolmagic.data.lists

import com.pruebas.airolmagic.R

fun getErrorCodeDesc(code: Int): Int{
    return when(code){
        1 -> R.string.err_creating_game
        2 -> R.string.err_joining_game
        3 -> R.string.err_starting_game
        4 -> R.string.err_duplicate_user_game
        5 -> R.string.err_max_players
        6 -> R.string.err_room_not_found
        else -> R.string.err_unknown
    }
}