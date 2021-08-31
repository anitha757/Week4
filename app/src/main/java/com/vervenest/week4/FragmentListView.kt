package com.vervenest.week4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class FragmentListView : Fragment() {
    private var lv: ListView? = null
    private var fab: FloatingActionButton? = null
    private var list: ArrayList<UserInfo?>? = null
    private var usersAdapter: UsersAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_list_view, container, false)
        lv = v.findViewById(R.id.list1)
        fab = v.findViewById<View>(R.id.fab) as FloatingActionButton
        list = ArrayList<UserInfo?>()
        val ma1 = activity as MainActivity?
        list = ma1!!.getUsersListSp()
        if (list==null || list!!.isEmpty()) {
            list = ArrayList()
            list!!.add(UserInfo("Ramkumar", "ram@gmail", "Male", "30", "12-08-2021", "12:23"))
            list!!.add(UserInfo("Shanu", "shanu@gmail", "Female", "2", "12-08-2021", "12:23"))
            ma1.saveUsersListSp(list)
        }
        usersAdapter = UsersAdapter(activity, list)
        lv!!.adapter = usersAdapter

        lv!!.onItemClickListener = OnItemClickListener { adapterView, view, position, id ->
            val user: UserInfo? = list!![position]
            val ma = activity as MainActivity?
            ma!!.display(user)
        }
        fab!!.setOnClickListener {
            val ma = activity as MainActivity?
            ma!!.replace()
        }
        return v
    }

}