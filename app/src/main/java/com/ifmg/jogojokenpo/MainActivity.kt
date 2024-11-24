package com.ifmg.jogojokenpo

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ifmg.jogojokenpo.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var pontuacaoJogador:Int = 0
    private var pontuacaoCPU:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // inflar os componentes da interface
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        registrarEventos()

        // Adicionar evento de clique no botão para iniciar uma nova partida
        binding.novaPartida.setOnClickListener(View.OnClickListener {
            limparCampos()
        })
    }

    // Registrar eventos de clique do jogador
    private fun registrarEventos(){
        jogar(binding.imgTesoura, binding.selecaoTesoura, "Tesoura")
        jogar(binding.imgPedra, binding.selecaoPedra, "Pedra")
        jogar(binding.imgPapel, binding.selecaoPapel, "Papel")
    }

    // Pegar escolha do jogador e iniciar um jogo
    private fun jogar(imagem: ImageView, selecao: View, escolhaJogador: String){

        // Jogo começa quando jogador clica em uma das imagens
        imagem.setOnClickListener(View.OnClickListener {

            // Colorir a opção escolhida do jogador
            limparSelecao()
            selecao.setBackgroundColor(getColor(R.color.selecao))

            // Mostrar opção escolhidda de jogador e CPU na tela
            val escolhaCPU = calcularEscolhaCPU()
            binding.resultadoSuaEscolha.text = escolhaJogador
            binding.resultadoEsolhaCpu.text = escolhaCPU

            marcarPontosVencedor(escolhaJogador, escolhaCPU)
        })
    }

    // CPU escolhe sua opção aleatoriamente
    private fun calcularEscolhaCPU():String{
        val escolha = Random.nextInt(0, 3)
        return when (escolha) {
            0 -> "Tesoura"
            1 -> "Pedra"
            2 -> "Papel"
            else -> ""
        }
    }

    // Determinar quem é vencedor para marcar pontos no placar
    private fun marcarPontosVencedor(escolhaJogador: String, escolhaCPU: String){
        when{
            escolhaJogador == escolhaCPU -> {null}
            (escolhaJogador == "Tesoura" && escolhaCPU == "Papel") ||
                    (escolhaJogador == "Pedra" && escolhaCPU == "Tesoura") ||
                    (escolhaJogador == "Papel" && escolhaCPU == "Pedra")
                    -> binding.seuResultadoPlacar.text = "${++pontuacaoJogador}"

            else -> binding.cpuResultadoPlacar.text = "${++pontuacaoCPU}"
        }
    }

    // Limpar seleção do jogador
    private fun limparSelecao(){
        binding.selecaoTesoura.setBackgroundColor(getColor(R.color.light_gray))
        binding.selecaoPedra.setBackgroundColor(getColor(R.color.light_gray))
        binding.selecaoPapel.setBackgroundColor(getColor(R.color.light_gray))
    }

    // Resetar todos os campos para começar uma nova partida
    private fun limparCampos(){
        pontuacaoJogador = 0
        pontuacaoCPU = 0
        binding.resultadoSuaEscolha.text = "..."
        binding.resultadoEsolhaCpu.text = "..."
        binding.seuResultadoPlacar.text = "0"
        binding.cpuResultadoPlacar.text = "0"
        limparSelecao()
    }
}