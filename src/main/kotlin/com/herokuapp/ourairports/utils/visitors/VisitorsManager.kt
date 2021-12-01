package com.herokuapp.ourairports.utils.visitors

interface VisitorsManager<T, S> {
    fun registerVisitor(type: S, visitor: Visitor<T>)
    fun getVisitor(itemType: S) : Visitor<T>
}