package com.example.testevagaimgur

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testevagaimgur.adapter.ImagemAdapter
import com.example.testevagaimgur.api.RetrofitHelper
import com.example.testevagaimgur.databinding.ActivityMainBinding
import com.example.testevagaimgur.model.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)

    }
    private val imagemAPI by lazy {
        RetrofitHelper.imagemAPI

    }

    private lateinit var imagemAdapter: ImagemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Inicializa o RecyclerView e o Adapter
        val recyclerView = binding.rvImagens


        val gridLayoutManager = GridLayoutManager(this, 3)  // Aqui você define o número de colunas
        recyclerView.layoutManager = gridLayoutManager


        //recyclerView.layoutManager = LinearLayoutManager(this)  // ou outro layout manager que preferir


        imagemAdapter = ImagemAdapter()  // Inicializa o adapter
        recyclerView.adapter = imagemAdapter  // Associa o adapter ao RecyclerView

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



    }

    private fun recupererImagens() {
        lifecycleScope.launch {


            try {
                val response = imagemAPI.buscarImagem(
                    authHeader = RetrofitHelper.ACCESS_TOKEN,
                    sort = RetrofitHelper.sort,
                    window = RetrofitHelper.window,
                    page = RetrofitHelper.page,
                    oqueBuscar = RetrofitHelper.oqueBuscar,
                    tipoArquivo = RetrofitHelper.tipoArquivo
                )

                if (response.isSuccessful) {
                    val imagemResposta = response.body()
                    val listaImagem = imagemResposta?.data
                    if (listaImagem != null && listaImagem.isNotEmpty()){

                        val listaImagens: List<Image> = listaImagem
                            .mapNotNull { it.images } // pega apenas os que têm lista imagens
                            .flatten() // transforma List<List<Image>> em List<Image> ou seja tranforma em um lista unica

                        withContext(Dispatchers.Main) {
                            imagemAdapter.adicinarLista(listaImagens)  // Atualizando o adapter com a nova lista
                        }

//                        listaImagens.forEach {imagem ->
//
//                            val linkImagem = imagem.link    // Pega o link da imagem
//
//
//
//
//                            // Exibindo no log
//                            Log.d("info_filmes_api", "Link: $linkImagem")
//
//                        }





                    }

                } else {
                    Log.e("IMGUR", "Erro: ${response.code()} ")
                }
            } catch (e: Exception) {
                Log.e("IMGUR", "Exception: ${e.message}")
            }


        }
    }

    override fun onStart() {
        super.onStart()
        recupererImagens()

    }
}