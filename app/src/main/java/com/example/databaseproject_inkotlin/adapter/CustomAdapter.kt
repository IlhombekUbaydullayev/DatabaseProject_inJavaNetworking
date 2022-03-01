package com.example.databaseproject_inkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.databaseproject_inkotlin.R
import com.example.databaseproject_inkotlin.helper.ItemTouchHelperAdapter
import com.example.databaseproject_inkotlin.manager.RealmManager
import com.example.databaseproject_inkotlin.model.Post
import java.util.*
import kotlin.collections.ArrayList


class CustomAdapter(private val c: Context, var list: ArrayList<Post>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() , ItemTouchHelperAdapter {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_image_image, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemModel = list[position]
        if (holder is ViewHolder) {
            val textView = holder.textView
            textView.text = itemModel.caption
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(list, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(list, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        var posts = RealmManager.instance!!.loadPosts()
        posts.removeAt(position)
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView
    init {
        textView = itemView.findViewById(R.id.tv_user)
    }
}
