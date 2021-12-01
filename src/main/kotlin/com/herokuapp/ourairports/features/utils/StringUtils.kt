package com.herokuapp.ourairports.features.utils

fun correctIcao(code: String?) =
    if (code != null && code.contains("^[a-zA-Z0-9]{4}".toRegex())) {
        code.uppercase().take(4)
    } else null