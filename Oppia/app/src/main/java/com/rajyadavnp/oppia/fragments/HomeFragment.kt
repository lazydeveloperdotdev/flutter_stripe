package com.rajyadavnp.oppia.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.rajyadavnp.oppia.LoginActivity
import com.rajyadavnp.oppia.R
import com.rajyadavnp.oppia.adapters.SubjectAdapter
import com.rajyadavnp.oppia.models.Subject
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * @author Raj Yadav
 * @date Feb 13, 2018 21:40
 * @link www.rajyadav-np.blogspot.com
 * @since 1.0
 */

class HomeFragment : Fragment() {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val job_list = ArrayList<Subject>()
        job_list.add(Subject(1, "https://www.infosys.com/SiteCollectionImages/media-resources/infosys-logo-JPEG.jpg", "Physics"))
        job_list.add(Subject(2, "https://img-prod-cms-rt-microsoft-com.akamaized.net/cms/api/am/imageFileData/RE1Mu3b?ver=5c31", "Mathematics"))
        job_list.add(Subject(3, "https://www.finextra.com/finextra-images/top_pics/xl/IBM-feature-480x270-1.jpg", "Chemistry"))

//        val layoutManager = LinearLayoutManager(activity!!, LinearLayout.HORIZONTAL, false)
//        view.subjectListView.layoutManager = layoutManager

//        Glide.with(activity!!).load("https://static.independent.co.uk/s3fs-public/thumbnails/image/2017/08/09/08/avatar.jpg").into(view.avatar)

        view.subjectListView.adapter = SubjectAdapter(context = activity!!, jobs = job_list, is_horizontal = true)
        view.subjectListView2.adapter = SubjectAdapter(context = activity!!, jobs = job_list, is_horizontal = true)

        view.user_avatar.setOnClickListener {
            startActivity(Intent(activity!!, LoginActivity::class.java))
        }
        return view
    }
}