package com.github.doomsdayrs.apps.shosetsu.backend

import android.app.Activity
import android.content.Context
import android.os.MemoryFile
import android.util.Log
import android.widget.Toast.LENGTH_LONG
import com.github.doomsdayrs.apps.shosetsu.R
import com.github.doomsdayrs.apps.shosetsu.backend.database.Database
import com.github.doomsdayrs.apps.shosetsu.backend.services.DownloadService
import com.github.doomsdayrs.apps.shosetsu.variables.DownloadItem
import com.github.doomsdayrs.apps.shosetsu.variables.HandledReturns
import com.github.doomsdayrs.apps.shosetsu.variables.ext.toast
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader
import java.lang.reflect.Member

/*
 * This file is part of Shosetsu.
 *
 * Shosetsu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Shosetsu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Shosetsu.  If not, see <https://www.gnu.org/licenses/>.
 * ====================================================================
 */
/**
 * Shosetsu
 * 16 / 06 / 2019
 *
 * @author github.com/doomsdayrs
 * Manages downloading and downloaded chapters
 */
object DownloadManager {

    /**
     * Adds to download list
     *
     * @param downloadItem download item to add
     */
    fun addToDownload(activity: Activity?, downloadItem: DownloadItem) {
        if (!Database.DatabaseDownloads.inDownloads(downloadItem)) {
            Database.DatabaseDownloads.addToDownloads(downloadItem)
            activity?.let { DownloadService.start(it) }
        }
    }

    /**
     * delete downloaded chapter
     *
     * @param context      context to work with
     * @param downloadItem download item to remove
     * @return if downloaded
     */
    fun delete(context: Context?, downloadItem: DownloadItem): Boolean {
        Log.d("DeletingChapter", downloadItem.toString())
        val file = File(Utilities.shoDir + "/download/" + downloadItem.formatter.formatterID + "/" + downloadItem.novelName + ".tar")
        if (file.exists()) {
            val tar = TarArchive(file)
            tar.deleteTar(downloadItem.chapterName + ".txt")
        }
        Database.DatabaseChapter.removePath(downloadItem.chapterID)
       /* if (file.exists()) if (!file.delete()) if (context != null) {
            context.toast(R.string.download_fail_delete, duration = LENGTH_LONG)
            return false
        }*/
        return true
    }

    /**
     * Get saved text
     *
     * @param path path of saved chapter
     * @return Passage of saved chapter
     */
    @JvmStatic
    fun getChapterText(path: String): HandledReturns<String> {
        try {
val t = File(path.substringBeforeLast("/"))
           val ta =TarArchive(t)
           val r = ta.readTar(path.substringAfterLast("/"))


            BufferedReader(InputStreamReader(r)).use { br ->
                val sb = StringBuilder()
                var line = br.readLine()
                while (line != null) {
                    sb.append(line)
                    sb.append(System.lineSeparator())
                    line = br.readLine()
                }
                return HandledReturns(true, value = sb.toString())
            }
        } catch (e: Exception) {
            return HandledReturns(false, "Exception Occurred", e)
        }
    }

}