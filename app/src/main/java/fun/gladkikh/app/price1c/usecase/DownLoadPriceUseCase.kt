package `fun`.gladkikh.app.price1c.usecase

import `fun`.gladkikh.app.price1c.intity.Item
import `fun`.gladkikh.app.price1c.util.PreferencesDelegate
import `fun`.gladkikh.app.price1c.util.file.downloadFile
import `fun`.gladkikh.app.price1c.util.file.unzip
import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Single
import java.io.File

val urlZip = "http://1c.ru/ftp/pub/pricelst/price_1c_.zip"
val fileXls = "price_1c.xls"
val fileZip = "price_1c.zip"
val TAG = "anit"


fun downLoadPrice(context: Context): Single<List<Item>> {


    val cacheDir = context.cacheDir
    return Single.just(urlZip)
        //Загрузка
        .map {
            clearDir(cacheDir)
            downloadFile(url = it, dir = cacheDir, name = fileZip)
        }
        //Разархивировали
        .flatMap {
            val throwable = Throwable()

            var file = it
            if (!file.exists()) {
                return@flatMap Single.error<Throwable>(throwable)
            }
            unzip(file)

            file = File(cacheDir, fileXls)
            if (!file.exists()) {
                return@flatMap Single.error<Throwable>(throwable)
            }

            return@flatMap Single.just(file)
        }
        .flatMap {
            Single.just(it as File)
        }
        //Парсим
        .flatMap {
            parse(context, it)
        }
        .doOnSuccess {
            clearDir(cacheDir)
        }
}


fun clearDir(dir: File) {
    var file = File(dir, fileXls)
    file.delete()

    file = File(dir, fileZip)
    file.delete()
}