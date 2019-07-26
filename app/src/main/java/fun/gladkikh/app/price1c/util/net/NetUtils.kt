package `fun`.gladkikh.app.price1c.util.net

import android.webkit.MimeTypeMap
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


fun downloadFile(url: String, dir: File, name: String? = null, fileExt: String? = null): File {
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    val response = client.newCall(request).execute()
    val contentType = response.header("content-type", null)
    var ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentType)
    ext = if (ext == null) {
        fileExt
    } else {
        ".$ext"
    }

    // use provided name or generate a temp file
    var file: File? = null
    file = if (name != null) {
        val filename = String.format("%s%s", name, ext)
        File(dir.absolutePath, filename)
    } else {

        val dateFormatWithZone =  SimpleDateFormat("yyyyMMdd-kkmmss", Locale.getDefault())
        File.createTempFile(dateFormatWithZone.format(Date()), ext, dir)
    }

    val body = response.body

    //val sink = Okio.buffer(Okio.sink(file))
    val sink = file.sink().buffer()
    /*
    sink.writeAll(body!!.source())
    sink.close()
    body.close()
     */

    body?.let {
        body?.source().use { input ->
            sink.use { output ->
                output.writeAll(input)
            }
        }
    }

    return file
}