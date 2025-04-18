package com.example.testevagaimgur.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testevagaimgur.R
import com.example.testevagaimgur.databinding.ItemImagemBinding
import com.example.testevagaimgur.model.Image
import com.squareup.picasso.Picasso

class ImagemAdapter : RecyclerView.Adapter<ImagemAdapter.ImagemViewHolder>() {

    private var listaImagens: List<Image> = listOf()

    fun adicinarLista(lista: List<Image>) {
        this.listaImagens = lista
        notifyDataSetChanged()  // Atualiza a UI
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagemViewHolder {
        val binding = ItemImagemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagemViewHolder, position: Int) {
        val imagem = listaImagens[position]
        holder.bind(imagem)
    }

    override fun getItemCount(): Int = listaImagens.size

    inner class ImagemViewHolder(val binding: ItemImagemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imagem: Image) {
            // Carregar a imagem com Picasso
            Picasso.get()
                .load(imagem.link)  // link da imagem
                .placeholder(R.drawable.capa)  // Placeholder enquanto carrega
                .error(R.drawable.ic_launcher_background)  // Imagem de erro
                .into(binding.imageView)
        }
    }
}
