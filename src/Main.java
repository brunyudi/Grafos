/**
 * Aluno: Bruno Yudi Mino Okada
 *
 * Sua  tarefa  será  construir  um  grafo,  com  100  vértices  cujos  valores  serão  aleatoriamente
 * selecinados em um conjunto de inteiros contendo números inteiros entre 1 e 1000. Cada vértice terá
 * um número aleatório de arestas menor ou igual a três.
 * Você  deverá  criar  este  grafo,  armazenando  estes  vértices  e  arestas  em  uma  tabela  de
 * adjacências e em uma lista de adjacências, medindo o tempo necessário para criar estas estruturas de
 * dados. Estas duas tabelas deverão ser salvas em arquivos de texto chamados de tabela_adjacencias.txt
 * e lista_adjacencias.txt respectivamente. Estes arquivos devem ser salvos no site repl.it
 * Para que seja possível verificar as diferenças de tempos de criação destas estruturas, uma vez
 * que o algoritmo esteja pronto, você deverá mudar o tamanho do gráfico para 10.000 de itens e repetir
 * o processo de ciração no mínimo 50 vezes, anotando os tempos de criação apresentando estas médias
 * para a tabela de adjacencias e para a lista de adjacencias.
 *
 */

import java.util.*;
import java.io.*;

class GrafoMatriz{
    boolean matriz[][];
    int numVertices;

    // Construtor do GrafoMatriz
    public GrafoMatriz(int numVertices){
        this.numVertices = numVertices;
        matriz = new boolean[numVertices][numVertices];
    }

    // Função para adicionar arestas na matriz
    public void adicionarArestas(int i, int j){
        matriz[i][j] = true;
        matriz[j][i] = true;
    }

    // Função para remover as arestas da matriz
    public void removerArestas(int i, int j){
        matriz[i][j] = false;
        matriz[j][i] = false;
    }

    // Função para imprimir a matriz
    public String imprimirMatriz(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numVertices; i++){
            stringBuilder.append(i + ": ");
            for (boolean j : matriz[i]){
                stringBuilder.append((j ? 1 : 0) + " ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}

class ArestaLista{
    int inicio;
    int destino;
    int peso;

    // Construtor das arestas da lista de adjacencias
    public ArestaLista(int inicio, int destino, int peso){
        this.inicio = inicio;
        this.destino = destino;
        this.peso = peso;
    }
}

class GrafoLista{
    static class NoListaAdj{
        int valor;
        int peso;

        // Construtor do vertice da lista de adjacencias
        public NoListaAdj(int valor, int peso){
            this.valor = valor;
            this.peso = peso;
        }
    }
    List<List<NoListaAdj>> listaAdj = new ArrayList<>();

    // Construtor do grafo para lista de adjacencias
    public GrafoLista(List<ArestaLista> verticeLista){
        for (int i = 0; i < verticeLista.size()+1000; i++){
            listaAdj.add(i, new ArrayList<>());
        }
        for (ArestaLista verticelist : verticeLista){
            listaAdj.get(verticelist.inicio).add(new NoListaAdj(verticelist.destino, verticelist.peso));
        }
    }

    // Função para imprimir a lista de adjacencias
    // Também imprime a lista em um arquivo texto externo
    public void imprimirLista (GrafoLista grafoLista) throws FileNotFoundException, UnsupportedEncodingException {

        PrintWriter writer = new PrintWriter("lista_adjacencias.txt", "UTF-8");
        int verticeInicio = 0;
        int tamanhoLista = grafoLista.listaAdj.size();

        while(verticeInicio < tamanhoLista){
            for(NoListaAdj vertice : grafoLista.listaAdj.get(verticeInicio)){
                System.out.println("Vertice: " + verticeInicio + " ==> " + vertice.valor + " (" + vertice.peso + ")\t");
                writer.println("Vertice: " + verticeInicio + " ==> " + vertice.valor + " (" + vertice.peso + ")\t");
            }
            //System.out.println();
            verticeInicio++;
        }
        writer.close();
    }

}

public class Main {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        GrafoMatriz grafoMatriz = new GrafoMatriz(1001);
        List<ArestaLista> verticesLista = new ArrayList<>();
        List<Integer> listaDeVertices = new ArrayList<>();
        int valorMin = 1;
        int valorMax = 1000;

        // Gerador de 10.000 numeros aleatórios (conforme enunciado) entre 1 e 1000
        for(int i = 0; i < 10000; i++){
            int vert = (int)Math.floor(Math.random()*(valorMax-valorMin+1)+valorMin);
            listaDeVertices.add(vert);
        }

        long tempoInicioLista = System.currentTimeMillis();
        long tempoInicioMatriz = System.currentTimeMillis();


        // Geração das arestas entre os vertices para a lista e matriz de adjacencia
        for(int x = 1; x < 10000; x++){
            int vertice1 = listaDeVertices.get(x-1);
            int vertice2 = listaDeVertices.get(x);
            verticesLista.add(new ArestaLista(vertice1,vertice2,1));
            grafoMatriz.adicionarArestas(vertice1,vertice2);
        }

        long tempoFinalMatriz = System.currentTimeMillis();
        long tempoMatriz = tempoFinalMatriz - tempoInicioMatriz;

        // Criação do grafo para lista de adjacencias
        GrafoLista grafoLista = new GrafoLista(verticesLista);

        long tempoFinalLista = System.currentTimeMillis();
        long tempoLista = tempoFinalLista - tempoInicioLista;

        // Chamada das funções para a impressão da lista e da matriz de adjacencias
        // A função de impressão da lista também faz a impressão da lista em arquivo texto externo
        grafoLista.imprimirLista(grafoLista);
        System.out.println(grafoMatriz.imprimirMatriz());

        // Impressão da matriz de adjacencia em um arquivo texto externo
        PrintWriter writer = new PrintWriter("tabela_adjacencias.txt", "UTF-8");
        writer.println(grafoMatriz.imprimirMatriz());
        writer.close();

        System.out.println("Tempo de criacao da lista: " + tempoLista + "ms");
        System.out.println("Tempo de criacao da matriz: " + tempoMatriz + "ms");
    }
}
