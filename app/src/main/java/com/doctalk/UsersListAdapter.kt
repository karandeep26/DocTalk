package com.doctalk

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.user_item.view.*

/**
 * Created by karan on 21/03/18.
 */
class UsersListAdapter(var userList:MutableList<Model.Item>) : RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false))
    }

    override fun getItemCount()=userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text=userList[position].login
        Glide.with(holder.image).load(userList[position].avatar_url)
                .apply(RequestOptions().circleCrop().centerCrop().error(R.drawable.ic_person_black_24dp)).into(holder.image)
        holder.followers.text=userList[position].url
    }


    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var name=itemView.name
        var followers=itemView.followers
        var image=itemView.image
    }

}