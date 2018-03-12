package com.example.roy.habittrainer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.roy.habittrainer.db.HabitsDbTable
import kotlinx.android.synthetic.main.activity_create_habit.*
import kotlinx.android.synthetic.main.card_single_habit.*
import java.io.IOException

class CreateHabitActivity : AppCompatActivity() {

    private val TAG = CreateHabitActivity::class.java.simpleName
    private val CHOOSE_IMAGE_REQUEST = 1;
    private var choosenBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habit)
    }

    fun chooseImage(v: View) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        val chooser = Intent.createChooser(intent, "Choose image for habit")
        startActivityForResult(chooser, CHOOSE_IMAGE_REQUEST)
    }

    fun saveHabit(v: View) {
        if (et_title.isBlank() || et_description.isBlank()) {
            Log.d(TAG, "Missing title or description")
            displayError("Your habit needs a title and description ")
            return
        } else if (choosenBitmap == null) {
            Log.d(TAG, "Missing image")
            displayError("add a picture for you habit")
            return
        }

        val title = et_title.text.toString()
        val description = et_description.text.toString()
        val habit = Habit(title, description, choosenBitmap!!)

        val id = HabitsDbTable(this).store(habit)

        if (id == -1L) {
            displayError("Habit could not be stored")
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun displayError(errorDescription: String) {
        tv_error.text = errorDescription
        tv_error.visibility = View.VISIBLE
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.data != null) {

            val bitmap = tryReadBitmap(data.data)


            bitmap?.let {
                choosenBitmap = bitmap
                iv_image_preview.setImageBitmap(bitmap)
            }
        }
    }

    private fun tryReadBitmap(data: Uri): Bitmap? {
        return try {
            MediaStore.Images.Media.getBitmap(contentResolver, data)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

}

private fun EditText.isBlank() = this.text.toString().isBlank()
