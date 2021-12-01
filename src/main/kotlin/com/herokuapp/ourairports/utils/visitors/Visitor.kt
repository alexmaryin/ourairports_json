package com.herokuapp.ourairports.utils.visitors

interface Visitor<T> {
    fun accept(item: T): Boolean
    operator fun invoke()
}