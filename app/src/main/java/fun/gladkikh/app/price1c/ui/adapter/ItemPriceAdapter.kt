package `fun`.gladkikh.app.price1c.ui.adapter

import `fun`.gladkikh.app.price1c.R
import `fun`.gladkikh.app.price1c.entity.ItemPrice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat
import java.text.NumberFormat


class ItemPriceAdapter : RecyclerView.Adapter<ItemPriceAdapter.ViewHolder>() {

    private var list: List<ItemPrice> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_price, parent, false)
        return ViewHolder(itemView)
    }

    fun updateData(newList: List<ItemPrice>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.tvName?.text = list[position].name
        holder?.tvCode?.text = list[position].code


        val format = DecimalFormat("#,###")

        holder?.tvPrice?.text =
            format.format(list[position].storePrice)
                .plus(" ")
                .plus(list[position].valuta)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView? = null
        var tvCode: TextView? = null
        var tvPrice: TextView? = null

        init {
            tvName = itemView.findViewById(R.id.name)
            tvCode = itemView.findViewById(R.id.code)
            tvPrice = itemView.findViewById(R.id.price)
        }

    }

}