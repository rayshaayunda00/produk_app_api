package com.raysha.produkappapi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raysha.produkappapi.DetailActivity
import com.raysha.produkappapi.R
import com.raysha.produkappapi.model.ModelProduk


class Produkadapter (
    private val onClick: (ModelProduk) -> Unit
) : ListAdapter<ModelProduk, Produkadapter.ProdukViewHolder>(ProdukCallBack) {

    class ProdukViewHolder(itemview: View, val onClick: (ModelProduk) -> Unit) :
        RecyclerView.ViewHolder(itemview) {
        private val imgProduk: ImageView = itemview.findViewById(R.id.imgProduk)
        private val title: TextView = itemview.findViewById(R.id.title)
        private val brand: TextView = itemview.findViewById(R.id.brand)
        private val price: TextView = itemview.findViewById(R.id.price)

        //cek produk yang saat ini
        private var currentProduct: ModelProduk? = null
        init {
            itemview.setOnClickListener() {
                currentProduct?.let {
                    onClick(it)
                }
            }
        }

        fun bind(produk: ModelProduk){
            currentProduct = produk
            //set ke widget
            title.text = produk.title
            brand.text = produk.brand
            price.text = produk.price.toString()

            //untuk menampilkan gambar pada widget (fetching gambar dg glide)
            Glide.with(imgProduk).load(produk.thumbnail).centerCrop()
                .into(imgProduk)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdukViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_product,
            parent, false
        )
        return ProdukViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ProdukViewHolder, position: Int) {
        val produk = getItem(position)
        holder.bind(produk)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("title", produk.title)
                putExtra("brand", produk.brand)
                putExtra("description",produk.description)
                putExtra("price", produk.price)
                putExtra("thumbnail", produk.thumbnail)
                putExtra("stok",produk.stock)
            }
            context.startActivity(intent)
        }
    }

}


object ProdukCallBack : DiffUtil.ItemCallback<ModelProduk>() {
    override fun areItemsTheSame(oldItem: ModelProduk, newItem: ModelProduk): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ModelProduk, newItem: ModelProduk): Boolean {
        return oldItem.id == newItem.id
        }
}

