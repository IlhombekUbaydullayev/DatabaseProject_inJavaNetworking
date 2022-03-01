package com.example.databaseproject_inkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.databaseproject_inkotlin.adapter.CustomAdapter
import com.example.databaseproject_inkotlin.helper.SimpleItemTouchHelperCallback
import com.example.databaseproject_inkotlin.manager.RealmManager
import com.example.databaseproject_inkotlin.model.Post
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var tv_size: FloatingActionButton
    lateinit var cancel : TextView
    lateinit var recyclerView: RecyclerView
    lateinit var et_dialog: EditText
    lateinit var tv_save : TextView
    lateinit var dialog : AlertDialog
    var boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        boolean = false
        initViews()
    }

    fun initViews() {
        tv_size = findViewById(R.id.b_realm)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this,1)
        tv_size.setOnClickListener {
            val view = View.inflate(this,R.layout.item_dialog_view,null)
            et_dialog = view.findViewById(R.id.et_dialog)
            tv_save = view.findViewById(R.id.tv_save)
            cancel = view.findViewById(R.id.cancel)
            val builder = AlertDialog.Builder(this)
            builder.setView(view)
            dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            boolean = true
            cancel.setOnClickListener {
                dialog.dismiss()
            }
            getAllChats()
        }
        if (!boolean) {
            getAllChatsTwo()
        }
    }

    private fun getAllChats() : ArrayList<Post> {
        var posts = RealmManager.instance!!.loadPosts()
        var id = UUID.randomUUID()
        tv_save.setOnClickListener {
            if (et_dialog.text.toString() != "") {
                var post2 = Post(posts.size.toLong(), et_dialog.text.toString())
                RealmManager.instance!!.savePost(post2)
                posts.add(post2)
                dialog.dismiss()
                val adapter = CustomAdapter(this,posts)
                recyclerView.adapter = adapter
                val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
                val touchHelper = ItemTouchHelper(callback)
                touchHelper.attachToRecyclerView(recyclerView)
            }else {
                dialog.dismiss()
            }

        }

        return posts
    }

    private fun getAllChatsTwo() : ArrayList<Post> {
        var posts = RealmManager.instance!!.loadPosts()
        val adapter = CustomAdapter(this,posts)
        recyclerView.adapter = adapter
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)
        return posts
    }
}