package `fun`.gladkikh.app.price1c.usecase

import `fun`.gladkikh.app.price1c.intity.Item
import `fun`.gladkikh.app.price1c.intity.Valuta
import `fun`.gladkikh.app.price1c.intity.Vat
import `fun`.gladkikh.app.price1c.util.PreferencesDelegate
import android.content.Context
import io.reactivex.Single
import io.reactivex.rxkotlin.toFlowable
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat

fun parse(context: Context, file: File): Single<List<Item>> {

    val myInput: InputStream
    // initialize asset manager
    val assetManager = context.getAssets()
    //  open excel sheet
    //myInput = assetManager.open(filename);

    myInput = file.inputStream()
    //myInput = context.openFileInput(fileName)
    // Create a POI File System object
    val myFileSystem = POIFSFileSystem(myInput)
    // Create a workbook using the File System
    val myWorkBook = HSSFWorkbook(myFileSystem)
    // Get the first sheet from workbook
    val mySheet = myWorkBook.getSheetAt(0)
    // We now need something to iterate through the cells.
    val rowIter = mySheet.rowIterator()
    var rowno = 0

    var hashMapValuta = mutableMapOf<String, Valuta>()
    var hashMapVat = mutableMapOf<String, Vat>()


    return mySheet.toFlowable()
        .filter {
            it.rowNum > 4
        }.map {
            listOf(
                (it.getCell(0) ?: "").toString(),
                (it.getCell(1) ?: "").toString(),
                (it.getCell(2) ?: "").toString(),
                (it.getCell(3) ?: "").toString(),
                (it.getCell(4) ?: "").toString(),
                (it.getCell(5) ?: "").toString(),
                (it.getCell(6) ?: "").toString(),
                (it.getCell(7) ?: "").toString(),
                (it.getCell(8) ?: "").toString(),
                (it.getCell(9) ?: "").toString()
            )
        }
        .filter {
            it[0].isNotEmpty()
                    && it[1].isNotEmpty()
                    && it[2].isNotEmpty()
        }
        .map {

            val valuta = hashMapValuta.getOrPut(it[2].trim(), { Valuta(it[2].trim()) })
            val vat = hashMapVat.getOrPut(it[6].trim(), { Vat(it[6].trim()) })

            var startDate:Long = 0

            if (it[8] != null){

                try {
                    startDate = SimpleDateFormat("dd.MM.yyyy").parse(it[8].trim()).time
                } catch (e: Exception) {
                }

            }


            Item(
                code = it[0].trim(),
                name = it[1].trim(),
                valuta = valuta,
                storePrice = it[3].toFloatOrNull() ?: 0f,
                dealerPrice = it[4].toFloatOrNull() ?: 0f,
                partnerPrice = it[5].toFloatOrNull() ?: 0f,
                vat = vat,
                storage = it[7].trim(),
                startSale = startDate,
                comment = it[9].trim()
            )

        }
        .map {
            it
        }
        .toList()
        .map {
            myInput.close()
            return@map it
        }
}