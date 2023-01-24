package com.example.marvel.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.marvel.R
import com.example.marvel.ui.characters.CharactersListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment: CharactersListFragment = CharactersListFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_frame_layout, fragment)
        transaction.commit()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            backPressedCallback.handleOnBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
         onBackPressedDispatcher.onBackPressed()

        }
    }
}