import java.util.Arrays;

//Aluno: Luiz Gustavo Klitzke

public class Grafos {
    private static boolean ehDirigido(int[][] matrizAdjacencia)
    {
        final int ordem = matrizAdjacencia.length;

        for (int i = 0; i < ordem; ++i)
        {
            for (int j = 0; j < i; ++j)
            {
                if (matrizAdjacencia[i][j] != matrizAdjacencia[j][i])
                {
                    return true;
                }
            }
        }

        return false;
    }

    private static void getGrausNaoDirigido(int[][] matrizAdjacencia, int[] graus)
    {
        final int ordem = matrizAdjacencia.length;

        for (int i = 0; i < ordem; ++i)
        {
            int grauVerticeNaoDirigido = 0;

            for (int j = 0; j < ordem; ++j)
            {
                if (i == j)
                {
                    grauVerticeNaoDirigido += matrizAdjacencia[i][j] * 2;
                }
                else
                {
                    grauVerticeNaoDirigido += matrizAdjacencia[i][j];
                }
            }

            graus[i] = grauVerticeNaoDirigido;
        }
    }

    private static void getGrausDirigido(int[][] matrizAdjacencia, int[] grausEntrada, int[] grausSaida)
    {
        final int ordem = matrizAdjacencia.length;

        for (int i = 0; i < ordem; ++i)
        {
            int grauEntrada = 0;
            int grauSaida = 0;

            for (int j = 0; j < ordem; ++j)
            {
                grauEntrada += matrizAdjacencia[j][i];
                grauSaida += matrizAdjacencia[i][j];
            }

            grausEntrada[i] = grauEntrada;
            grausSaida  [i] = grauSaida;
        }
    }

    //Qual  é  o  tipo  do  grafo  (dirigido  ou  não,  simples  ou  multigrafo,  regular,  completo,  nulo  ou bipartido) 
    public static String tipoDoGrafo(int[][] matrizAdjacencia)
    {
        String tipoGrafo = "";

        final int ordem = matrizAdjacencia.length;

        boolean dirigido = ehDirigido(matrizAdjacencia);

        boolean nulo = true;
        boolean simples = true;
        boolean completo = true;
        boolean bipartido = true;

        for (int i = 0; i < ordem; ++i)
        {
            for (int j = 0; j < ordem; ++j)
            {
                int valor = matrizAdjacencia[i][j];

                if (valor != 0)
                {
                    nulo = false;

                    if (valor > 1)
                    {
                        simples = false;
                    }

                    if (i == j) // Laço
                    {
                        simples = false;
                        bipartido = false;
                    }
                }
                else if (simples && i != j) //Zerado fora da diagonal principal
                {
                    completo = false;
                }
            }
        }

        tipoGrafo += (dirigido ? "" : "não ") + "dirigido";

        boolean regular = true;

        completo = simples && (completo || ordem < 2);

        bipartido = bipartido && !dirigido;

        if (dirigido)
        {
            int[] grausEntrada = new int[ordem];
            int[] grausSaida = new int[ordem];

            getGrausDirigido(matrizAdjacencia, grausEntrada, grausSaida);

            for (int i = 1; i < ordem; ++i)
            {
                if (grausEntrada[i] != grausEntrada[i - 1] || grausSaida[i] != grausSaida[i - 1])
                {
                    regular = false;
                    break;
                }
            }
        }
        else
        {
            int[] graus = new int[ordem];
            getGrausNaoDirigido(matrizAdjacencia, graus);

            for (int i = 1; i < ordem; ++i)
            {
                if (graus[i] != graus[i - 1])
                {
                    regular = false;
                    break;
                }
            }
        }

        if (regular)
        {
            tipoGrafo += ", regular";
        }

        if (nulo)
        {
            tipoGrafo += ", nulo";
        }

        tipoGrafo += ", " + (simples ? "simples" : "multigrafo");
        tipoGrafo += completo ? ", completo" : "";

        int[] separacaoGrupos = new int[ordem];
        separacaoGrupos[0] = 1; //Primeiro vertice sempre comeca no meu grupo 1

        if (bipartido && ordem > 1)
        {
            for (int i = 1; i < ordem; ++i)
            {
                for (int j = 0; j < i; ++j)
                {
                    if (matrizAdjacencia[i][j] != 0)
                    {
                        if (separacaoGrupos[i] != 0 && separacaoGrupos[j] == separacaoGrupos[i])
                        {
                            bipartido = false;
                            break;
                        }
                        
                        if (separacaoGrupos[j] == 1)
                        {
                            separacaoGrupos[i] = 2;
                        }
                        else if (separacaoGrupos[j] == 2)
                        {
                            separacaoGrupos[i] = 1; 
                        }
                    }
                }

                if (bipartido == false)
                {
                    break;
                }
            }
        }

        tipoGrafo += bipartido ? ", bipartido" : "";

        return tipoGrafo;
    }

    //Quantas arestas esse grafo possui? Liste o conjunto de arestas.
    public static String arestasDoGrafo(int[][] matrizAdjacencia)
    {
        final int ordem = matrizAdjacencia.length;

        boolean dirigido = ehDirigido(matrizAdjacencia);

        int qtdArestas = 0;

        String arestas = "E = { ";
        int qtdLigacoes = 0;

        if (dirigido)
        {
            for (int i = 0; i < ordem; ++i)
            {
                for (int j = 0; j < ordem; ++j)
                {
                    qtdLigacoes = matrizAdjacencia[i][j];

                    for (int k = 0; k < qtdLigacoes; ++k)
                    {
                        arestas += String.format("%s(v%d, v%d)", (qtdArestas == 0 ? "" : ", "), i + 1, j + 1);
                        ++qtdArestas;
                    }
                }
            }
        }
        else
        {
            for (int i = 0; i < ordem; ++i)
            {
                for (int j = i; j < ordem; ++j) // só acima e incluindo a diagonal principal, para ficar melhor ordenado
                {
                    qtdLigacoes = matrizAdjacencia[i][j];

                    for (int k = 0; k < qtdLigacoes; ++k)
                    {
                        arestas += String.format("%s(v%d, v%d)", (qtdArestas == 0 ? "" : ", "),  i + 1, j + 1);
                        ++qtdArestas;
                    }
                }
            }
        }

        arestas += " }";

        return String.format("Quantidade Arestas: %d. %s", qtdArestas, arestas);
    }

    //Qual é o graude cada vértice. Liste a sequência de graus
    public static String grausDoVertice(int[][] matrizAdjacencia)
    {
        final int ordem = matrizAdjacencia.length;
        
        boolean dirigido = ehDirigido(matrizAdjacencia);
        
        String retorno = "";

        if (dirigido)
        {
            int[] grausEntrada = new int[ordem];
            int[] grausSaida = new int[ordem];

            getGrausDirigido(matrizAdjacencia, grausEntrada, grausSaida);

            int listaGrausEntradaSaida[][] = new int[ordem][2];

            retorno += "Graus de Entrada: ";
            for (int i = 0; i < ordem; ++i)
            {
                retorno += String.format("%sv%d : %d", (i == 0 ? "" : ", "), i + 1, grausEntrada[i]);

                listaGrausEntradaSaida[i][0] = grausEntrada[i];
            }

            retorno += "\nGraus de Saída: ";
            for (int i = 0; i < ordem; ++i)
            {
                retorno += String.format("%sv%d : %d", (i == 0 ? "" : ", "), i + 1, grausSaida[i]);
                listaGrausEntradaSaida[i][1] = grausSaida[i];
            }

            Arrays.sort(listaGrausEntradaSaida, (grupoA, grupoB) -> Integer.compare(grupoA[0], grupoB[0])); // Compara baseado no grau de Entrada
            
            retorno += "\nSequência de graus de Entrada: ";
            for (int i = 0; i < ordem; ++i)
            {
                retorno += String.format("%s%d", (i == 0 ? "" : ", " ), listaGrausEntradaSaida[i][0]);
            }
            retorno += "\nSequência de graus de Saída: ";
            for (int i = 0; i < ordem; ++i)
            {
                retorno += String.format("%s%d", (i == 0 ? "" : ", " ), listaGrausEntradaSaida[i][1]);
            }
        }
        else
        {
            int[] graus = new int[ordem];

            retorno += "Graus dos vértices: ";

            getGrausNaoDirigido(matrizAdjacencia, graus);

            for (int i = 0; i < ordem; ++i)
            {
                retorno += String.format("%sv%d : %d", (i == 0 ? "" : ", "), i + 1, graus[i]);
            }

            Arrays.sort(graus);
            retorno += "\nSequência de graus: ";
            for (int i = 0; i < ordem; ++i)
            {
                retorno += String.format("%s%d", (i == 0 ? "" : ", " ), graus[i]);
            }
        }

        return retorno;
    }

    public static void main(String[] args) throws Exception 
    {
        int[][] matrizAdjacencia = new int[3][3];
        matrizAdjacencia[0][0] = 1;
        matrizAdjacencia[0][1] = 1;
        matrizAdjacencia[0][2] = 1;
        matrizAdjacencia[1][0] = 1;
        matrizAdjacencia[1][1] = 0;
        matrizAdjacencia[1][2] = 0;
        matrizAdjacencia[2][0] = 1;
        matrizAdjacencia[2][1] = 0;
        matrizAdjacencia[2][2] = 0;

        System.out.println(tipoDoGrafo(matrizAdjacencia));
        System.out.println(arestasDoGrafo(matrizAdjacencia));
        System.out.println(grausDoVertice(matrizAdjacencia));
    }
}
