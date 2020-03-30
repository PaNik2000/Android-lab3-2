package com.example.lab3

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

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
        val titleName = TextView(this)
        val titleTime = TextView(this)

        titleId.text = resources.getString(R.string.titleIdStr)
        titleId.typeface = Typeface.create(null as String?, Typeface.BOLD)
        titleName.text = resources.getString(R.string.titleNameStr)
        titleName.typeface = Typeface.create(null as String?, Typeface.BOLD)
        titleTime.text = resources.getString(R.string.titleTimeStr)
        titleTime.typeface = Typeface.create(null as String?, Typeface.BOLD)

        titleRow.addView(titleId)
        titleRow.addView(titleName)
        titleRow.addView(titleTime)

        table.addView(titleRow)

        cursor.moveToFirst()
        do {
            val row = TableRow(this)

            val id = TextView(this)
            val name = TextView(this)
            val time = TextView(this)

            id.text = cursor.getString(0)
            name.text = cursor.getString(1)
            time.text = cursor.getString(2)

            row.addView(id)
            row.addView(name)
            row.addView(time)

            table.addView(row)
        } while (cursor.moveToNext())

        setContentView(table)
    }
}