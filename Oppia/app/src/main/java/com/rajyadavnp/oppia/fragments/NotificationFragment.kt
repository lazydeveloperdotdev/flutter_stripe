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

class NotificationFragment : Fragment() {
    companion object {
        fun newInstance(): NotificationFragment {
            return NotificationFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)

        return view
    }
}