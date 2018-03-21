package com.doctalk

/**
 * Created by karan on 21/03/18.
 */
class Model(var items:MutableList<Item>) {
    class Item(var login:String,var id:Int,var avatar_url:String?,var url:String)
}