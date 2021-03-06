package com.example.lab3

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = FeedReaderDbHelper(this)

        val db = dbHelper.writableDatabase

        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, null, null)

        for (i in 0..4) {
            val str : Array<String> = Array(3, {""})
            val r : Random = Random()
            var num : Int = r.nextInt(6)
            str[0] = resources.getStringArray(R.array.surnames)[num] + " "
            num = r.nextInt(6)
            str[1] = resources.getStringArray(R.array.names)[num] + " "
            num = r.nextInt(6)
            str[2] = resources.getStringArray(R.array.fathernames)[num]

            val dateFormat = SimpleDateFormat("HH:mm:ss dd.MM.yyyy")

            val values = ContentValues().apply {
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_SURNAME, str[0])
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME, str[1])
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_FATHERNAME, str[2])
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME, dateFormat.format(Date()))
            }
            db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
        }
    }

    fun tableShow(view : View) {
        val intent = Intent(this, TableActivity::class.java)
        startActivity(intent)
    }

    fun addRecord(view : View) {
        val editText = findViewById(R.id.editText) as EditText
        val str = editText.text.toString()
        editText.setText("")

        if (!str.isEmpty()) {
            val dbHelper = FeedReaderDbHelper(this)
            val db = dbHelper.writableDatabase

            val match = Regex("\\w+").find(str)

            val dateFormat = SimpleDateFormat("HH:mm:ss dd.MM.yyyy")
            val values = ContentValues().apply {
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_SURNAME, match?.value)
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME, match?.next()?.value)
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_FATHERNAME, match?.next()?.next()?.value)
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME, dateFormat.format(Date()))
            }
            db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)

            val toast : Toast = Toast.makeText(this, "Добавлена запись: ${str}", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    fun addDefaultRecord(view : View) {
        val dbHelper = FeedReaderDbHelper(this)
        val db = dbHelper.writableDatabase

        val cursor = db.query(
            FeedReaderContract.FeedEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        cursor.moveToLast()
        val time = cursor.getString(4)

        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, "${FeedReaderContract.FeedEntry.COLUMN_NAME_NAME}" +
                "= \'${cursor.getString(1)}\'", null)

        val dateFormat = SimpleDateFormat("HH:mm:ss dd.MM.yyyy")
        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_SURNAME, "Иванов")
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME, "Иван")
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_FATHERNAME, "Иванович")
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME, time)
        }
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)

        val toast : Toast = Toast.makeText(this, "Последняя запись заменена на \"Иванов Иван Иванович\"", Toast.LENGTH_SHORT)
        toast.show()
    }
}