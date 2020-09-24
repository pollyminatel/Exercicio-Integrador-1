/*Criado por Pollyana Gomes Minatel e Vinicius Trapia*/

fun main() {
    var livroteca = Livraria("livroteca", "23/09/2020")
    livroteca.cadastrarLivro(123, "Livro teste", "Bruno", 2020, 15.50, 2.00, "Disponivel")
    livroteca.cadastrarLivro(1, "Livro1", "Autor1", 2020, 15.0, 1.0, "Disponivel")
    livroteca.cadastrarLivro(2, "Livro2", "Autor2", 2020, 15.0, 1.0, "Disponivel")
    livroteca.cadastrarLivro(3, "Livro3", "Autor3", 2020, 15.0, 1.0, "Disponivel")
    println(livroteca.estoque)
    livroteca.consultar("Livro teste")
    livroteca.verificarEstoque()
    var cliente1 = Cliente("Cliente1", 123456)
    var funcionario1 = Funcionario("Funcionario1", 456789)
    livroteca.alugar("Livro1", cliente1)
    cliente1.historicoAlugueis()
    livroteca.efetuarVenda(3, funcionario1)
    livroteca.verificarEstoque()
    funcionario1.historicoVendas()

}

class Livraria(nome: String, dataCriacao: String){
    var estoque = mutableListOf<Livro>()
    var colecoes = mutableMapOf<Any,List<Livro>>()
    fun cadastrarLivro(codigo: Int,titulo: String,autor: String,anoLancamento: Int,precoVenda: Double,precoAluguel: Double,estadoAtual: String){
        var livro = Livro(codigo,titulo,autor,anoLancamento,precoVenda,precoAluguel,estadoAtual)
        estoque.add(livro)
    }
    fun cadastrarColecao(nomeColecao: String, lista: List<Livro>){
        colecoes[nomeColecao] = lista
    }
    fun consultar(entrada: Any){
        var entrada = entrada
        var existe: Boolean = false
        if (entrada is String) {
            estoque.forEach{
                if (it.titulo == entrada){
                    existe = true
                    entrada = it
                }
            }
            if (existe){
                println("Esse livro existe: $entrada")
            } else {
                println("Esse livro não existe")
            }
        } else if (entrada is Int){
            estoque.forEach{
                if (it.codigo == entrada) {
                    existe = true
                    entrada = it
                }
            }
            if (existe){
                println("Esse livro existe: $entrada")
            } else {
                println("Esse livro não existe")
            }
        } else {
            println("Entrada inválida")
        }
    }
    fun alugar(livro: String, cliente: Cliente){
        estoque.forEach{
            if(livro == it.titulo) {
                if (it.estadoAtual == "Disponivel") {
                    it.estadoAtual = "Alugado"
                    cliente.historicoAlugueis.add(it)
                    println("Livro alugado com sucesso")
                } else {
                    println("Esse livro não está disponível")
                }
            }
        }

    }
    fun efetuarVenda(codigo: Int, nome: Funcionario){
        var existe: Boolean = false
        estoque.forEach{
            if (it.codigo == codigo){
                existe = true
                it.estadoAtual = "Vendido"
                nome.historicoVendas.add(it)
                println("Venda com sucesso")
            }
        }

        if (!existe){
            println("Livro não existe")
        }
    }
    fun verificarEstoque(){
        var disponivel = 0
        var alugado = 0
        var vendido = 0
        var total = 0.0
        estoque.forEach{
            if (it.estadoAtual == "Disponivel"){
                disponivel++
            } else if (it.estadoAtual == "Alugado") {
                alugado++
            } else {
                vendido++
                total += it.precoVenda
            }
        }
        println("Disponíveis: $disponivel - Alugados: $alugado - Vendidos: $vendido - Total arrecadado nas vendas: $total")
    }
}
class Livro(var codigo: Int, var titulo: String, var autor: String, var anoLancamento: Int, var precoVenda: Double, var precoAluguel: Double, var estadoAtual: String ){
    override fun toString(): String {
        return "Código: $codigo Titulo: $titulo"
    }
}
interface Pessoa{
    var nome: String
    var rg: Long
}
class Cliente(override var nome: String, override var rg: Long) : Pessoa{
    var historicoAlugueis = mutableListOf<Livro>()
    var historicoCompras = mutableListOf<Livro>()
    fun historicoAlugueis(){
        println(historicoAlugueis)
    }
    fun historicoCompras(){
        println(historicoCompras)
    }
}
class Funcionario(override var nome: String, override var rg: Long) : Pessoa{
    var historicoAlugueis = mutableListOf<Livro>()
    var historicoVendas = mutableListOf<Livro>()
    fun historicoAlugueis(){
        println(historicoAlugueis)
    }
    fun historicoVendas(){
        println(historicoVendas)
    }
}
