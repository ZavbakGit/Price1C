package `fun`.gladkikh.app.price1c.util.file

import java.io.BufferedInputStream
import java.io.File
import java.util.zip.ZipFile


fun unzip(zipFile: File, path: String? = null) {

    val targetPath = path?:(zipFile.parentFile.absolutePath)

    val zip = ZipFile(zipFile)
    val enumeration = zip.entries()
    while (enumeration.hasMoreElements()) {
        val entry = enumeration.nextElement()
        val destFilePath = File(targetPath, entry.name)
        destFilePath.parentFile.mkdirs()

        if (entry.isDirectory)
            continue

        val bufferedIs = BufferedInputStream(zip.getInputStream(entry))

        bufferedIs.use {
            destFilePath.outputStream().buffered(1024).use { bos ->
                bufferedIs.copyTo(bos)
            }
        }
    }
}