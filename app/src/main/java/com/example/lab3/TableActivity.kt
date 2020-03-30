package com.example.lab3

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class TableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = FeedReaderDbHelper(this)
        val db = dbHelper.readableDatabase

        val cursor = db.query(
            FeedReaderContract.FeedEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val table = TableLayout(this)
        table.isStretchAllColumns = true
        table.isShrinkAllColumns = true

        val titleRow = TableRow(this)
        val titleId = TextView(this)
        val titleSurname = TextView(this)
        val titleName = TextView(this)
        val titleTime = TextView(this)
        val titleFathername = TextView(this)

        titleId.text = resources.getString(R.string.titleIdStr)
        titleId.typeface = Typeface.create(null as String?, Typeface.BOLD)
        titleSurname.text = resources.getString(R.string.titleSurnameStr)
        titleSurname.typeface = Typeface.create(null as String?, Typeface.BOLD)
        titleName.text = resources.getString(R.string.titleNameStr)
        titleName.typeface = Typeface.create(null as String?, Typeface.BOLD)
        titleFathername.text = resources.getString(R.string.titleFathernameStr)
        titleFathername.typeface = Typeface.create(null as String?, Typeface.BOLD)
        titleTime.text = resources.getString(R.string.titleTimeStr)
        titleTime.typeface = Typeface.create(null as String?, Typeface.BOLD)

        titleRow.addView(titleId)
        titleRow.addView(titleSurname)
        titleRow.addView(titleName)
        titleRow.addView(titleFathername)
        titleRow.addView(titleTime)

        table.addView(titleRow)

        cursor.moveToFirst()
        do {
            val row = TableRow(this)

            val id = TextView(this)
            val surname = TextView(this)
            val name = TextView(this)
            val fathername = TextView(this)
            val time = TextView(this)

            id.text = cursor.getString(0)
            surname.text = cursor.getString(1)
            name.text = cursor.getString(2)
            fathername.text = cursor.getString(3)
            time.text = cursor.getString(4)

            row.addView(id)
            row.addView(surname)
            row.addView(name)
            row.addView(fathername)
            row.addView(time)

            table.addView(row)
        } while (cursor.moveToNext())


        val scroll = ScrollView(this)
        val linear = LinearLayout(this)
        linear.orientation = LinearLayout.VERTICAL

        linear.addView(table)
        scroll.addView(linear)
        setContentView(scroll)
    }
}