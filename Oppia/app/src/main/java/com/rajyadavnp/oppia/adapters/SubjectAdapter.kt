package com.rajyadavnp.oppia.adapters


import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rajyadavnp.oppia.R
import com.rajyadavnp.oppia.models.Subject


/**
 * @author Raj Yadav
 * @date Feb 13, 2018 21:43
 * @link www.rajyadav-np.blogspot.com
 * @since 1.0
 */
class SubjectAdapter(var jobs: ArrayList<Subject>, private val context: Context, val is_horizontal: Boolean = false) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemView: View? = null

    inner class MyView(view: View) : RecyclerView.ViewHolder(view) {
        var iImage: ImageView = view.findViewById(R.id.image)
        var tTitle: TextView = view.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.subject_single, parent, false)
        return MyView(itemView!!)
    }

    override fun onBindViewHolder(mainHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = mainHolder as MyView


//        Glide.with(context).load(jobs[position].image).into(holder.iImage)
        holder.tTitle.text = jobs[position].title

        itemView?.setOnClickListener {
            //            val intent = Intent(context, SingleJobActivity::class.java)
//            context.startActivity(intent)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if (is_horizontal) {
            val manager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
            recyclerView.layoutManager = manager
        } else {
            val manager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            recyclerView.layoutManager = manager
        }
    }

    override fun getItemCount(): Int {
        return jobs.size
    }

}