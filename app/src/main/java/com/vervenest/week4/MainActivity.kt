package com.vervenest.week4

import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView()
    }

    fun listView() {
        supportFragmentManager.beginTransaction().replace(R.id.frame1, FragmentListView())
            .commit()
    }

    fun listView(userList: List<UserInfo?>?) {
        supportFragmentManager.beginTransaction().replace(R.id.frame1, FragmentListView())
            .commit()
    }

    fun display(user: UserInfo?) {
        val fdv = FragmentProfileView()
        val b = Bundle()
        b.putParcelable("user", user)
        fdv.arguments = b
        supportFragmentManager.beginTransaction().replace(R.id.frame1, fdv)
            .commit()
    }

    fun replace() {
        supportFragmentManager.beginTransaction().replace(R.id.frame1, FragmentRegistration())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_items, menu)
        return true
    }

    fun saveUsersListSp(userInfoList: List<UserInfo?>?) {
        val pref = getSharedPreferences("com.vervenest.week4", MODE_PRIVATE)
        val editor = pref.edit()
        val usersListJson = Gson().toJson(userInfoList)
        editor.putString("userList", usersListJson)
        editor.commit()
    }

    fun getUsersListSp(): ArrayList<UserInfo?>? {
        val sh = getSharedPreferences("com.vervenest.week4", MODE_PRIVATE)
        val usersListJson = sh.getString("userList", "")
        //println("@@@@@@@@@@@@@@@@::::::::::"+usersListJson)
        val listType = object : TypeToken<ArrayList<UserInfo?>?>() {}.type
        return Gson().fromJson(usersListJson, listType)
    }

    fun deleteUsersSp(userInfo: UserInfo?): ArrayList<UserInfo?>? {
        val userInfoList: ArrayList<UserInfo?>? = getUsersListSp()
        //println("Delete1:"+userInfoList)
        val userfinalList: ArrayList<UserInfo?> = ArrayList()
        if (userInfoList != null) {
            for (user in userInfoList) {
                if (userInfo != null) {
                    if (!user?.userName.equals(userInfo.userName, ignoreCase = true)) {
                        userfinalList.add(user)
                    }
                }
            }
        }
        saveUsersListSp(userfinalList)
        //println("Delete2:"+userfinalList)
        return userfinalList
    }

    fun addUsersSp(userInfo: UserInfo): ArrayList<UserInfo?>? {
        val userInfoList = getUsersListSp()
        if (userInfoList != null) {
            userInfoList.add(userInfo)
        }
        saveUsersListSp(userInfoList)
        return userInfoList
    }

}