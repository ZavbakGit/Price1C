package `fun`.gladkikh.app.price1c.usecase

import `fun`.gladkikh.app.price1c.App
import `fun`.gladkikh.app.price1c.entity.ItemPrice
import `fun`.gladkikh.app.price1c.util.file.downloadFile
import `fun`.gladkikh.app.price1c.util.file.unzip
import android.content.Context
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

val urlZip = "http://1c.ru/ftp/pub/pricelst/price_1c_.zip"
val fileXls = "price_1c.xls"
val fileZip = "price_1c.zip"
val TAG = "anit"


fun downLoadPrice(context: Context): Single<WrapData> {
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

            //TODO Надо удалять таблицу полностью, что бы id с нуля начинался
            App.database.getItemPriceDao().dellALL()
            App.database.getItemPriceDao().insert(it.listItem)
        }

}


fun clearDir(dir: File) {
    var file = File(dir, fileXls)
    file.delete()

    file = File(dir, fileZip)
    file.delete()
}