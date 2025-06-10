package com.example.contentprovider.data

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.example.contentprovider.domain.ImageMedia

class MediaRepo(private val context: Context) {

    suspend fun getAllMDiaImage(): List<ImageMedia> {
        val mediaList = mutableListOf<ImageMedia>()

        /**
         *  from media store database we are making request for these two column.
         *
         */
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME)

        /**
         * uri is the same like in SQL "FROM table_name" . we define the database
         */
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        /**
         * behave like "WHERE col = value" in SQL
         */
        val selection = null

        /**
         * val  selection = "rahul
         * used for custom argument for deleting or updating or replacing some value in DB which contain "rahul"
         */
        val selectionArgs = null

        /**
         * behave like ORDER BY col, col...
         */
        val sortOrder = null


        // below line are already expalined in blog[https://rahulyadav07.github.io]

        // content resolve return the data in form of cursor
        context.contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)?.use { cursor ->
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idIndex)
                val name = cursor.getString(nameIndex)
                val contentUri = ContentUris.withAppendedId(uri, id)
                mediaList.add(ImageMedia(contentUri, name))
            }
        }
        return mediaList
    }
}