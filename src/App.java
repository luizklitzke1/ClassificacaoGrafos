import java.util.Collections;
import java.util.Vector;

//Aluno: Luiz Gustavo Klitzke

public class App {
    private static boolean ehDirigido(Integer[][] matrizAdjacencia) //Criei separado para facilitar, porque tem que ser usado em todos os outros metodos
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

    //Qual  é  o  tipo  do  grafo  (dirigido  ou  não,  simples  ou  multigrafo,  regular,  completo,  nulo  ou bipartido) 
    public static String tipoDoGrafo(Integer[][] matrizAdjacencia)
    {
        String tipoGrafo = "";

        final int ordem = matrizAdjacencia.length;

        boolean dirigido = ehDirigido(matrizAdjacencia);

        boolean nulo = true;
        boolean simples = true;
        boolean completo = true;

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
                }
                else if (simples && i != j) //Zerado fora da diagonal principal
                {
                    completo = false;
                }
            }
        }

        tipoGrafo += dirigido ? "" : "não " + "dirigido";

        if (nulo)
        {
            tipoGrafo += ", nulo";
        }
        else
        {
            tipoGrafo += ", " + (simples ? "simples" : "multigrafo");
            tipoGrafo += completo ? ", completo" : "";
        }

        int[] separacaoGrupos = new int[ordem];
        separacaoGrupos[0] = 1; //Primeiro vertice sempre comeca no meu grupo 1

        boolean bipartido = true;

        for (int i = 1; i < ordem; ++i) // Descobrir bipartido separado
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
        }

        tipoGrafo += bipartido ? ", bipartido" : "";

        return tipoGrafo;
    }

    //Quantas arestas esse grafo possui? Liste o conjunto de arestas.
    public static String arestasDoGrafo(Integer[][] matrizAdjacencia)
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
                        arestas += String.format("%s(v%d, v%d)", (qtdArestas == 0 ? "" : " ,"), i + 1, j + 1);
                        ++qtdArestas;
                    }
                }
            }
        }
        else
        {
            for (int i = 0; i < ordem; ++i)
            {
                for (int j = i; j < ordem; ++j) // só acima da principal, para ficar melhor ordenado
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
    public static String grausDoVertice(Integer[][] matrizAdjacencia)
    {
        final int ordem = matrizAdjacencia.length;
        
        boolean dirigido = ehDirigido(matrizAdjacencia);
        
        String retorno = "";

        if (dirigido)
        {
            Vector<Integer> grausEntrada = new Vector<Integer>();
            Vector<Integer> grausSaida = new Vector<Integer>();

            for (int i = 0; i < ordem; ++i)
            {
                Integer grauEntrada = 0;
                Integer grauSaida = 0;

                for (int j = 0; j < ordem; ++j)
                {
                    grauEntrada += matrizAdjacencia[j][i];
                    grauSaida += matrizAdjacencia[i][j];
                }

                grausEntrada.add(grauEntrada);
                grausSaida.add(grauSaida);
            }

            retorno += "\nGraus de Entrada: ";
            for (int i = 0; i < ordem; ++i)
            {
                retorno += String.format("%sv%d : %d", (i == 0 ? "" : ", "), i + 1, grausEntrada.get(i));
            }

            Collections.sort(grausEntrada);
            retorno += "\nSequência de graus de Entrada: ";
            for (int i = 0; i < ordem; ++i)
            {
                retorno += String.format("%s%d", (i == 0 ? "" : ", " ), grausEntrada.get(i));
            }

            retorno += "\nGraus de Saída: ";
            for (int i = 0; i < ordem; ++i)
            {
                retorno += String.format("%sv%d : %d", (i == 0 ? "" : ", "), i + 1, grausSaida.get(i));
            }

            Collections.sort(grausSaida);
            retorno += "\nSequência de graus de Saída: ";
            for (int i = 0; i < ordem; ++i)
            {
                retorno += String.format("%s%d", (i == 0 ? "" : ", " ), grausSaida.get(i));
            }
        }
        else
        {
            Vector<Integer> graus = new Vector<Integer>();

            retorno += "Graus dos vértices: ";

            for (int i = 0; i < ordem; ++i)
            {
                Integer grauVerticeNaoDirigido = 0;
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

                retorno += String.format("%sv%d : %d", (i == 0 ? "" : ", "), i + 1, grauVerticeNaoDirigido);

                graus.add(grauVerticeNaoDirigido);
            }

            Collections.sort(graus);
            retorno += "\nSequência de graus: ";
            for (int i = 0; i < ordem; ++i)
            {
                retorno += String.format("%s%d", (i == 0 ? "" : ", " ), graus.get(i));
            }
        }

        return retorno;
    }

    public static void main(String[] args) throws Exception 
    {
        Integer[][] matrizAdjacencia = 
        {
            {0, 1, 1},
            {1, 0, 1},
            {1, 1, 0}
        };

        System.out.println(arestasDoGrafo(matrizAdjacencia));
        System.out.println(grausDoVertice(matrizAdjacencia));
        System.out.println("");

        Integer[][] matrizAdjacencia2 = 
        {
            {0, 1, 1, 1},
            {1, 0, 0, 1},
            {1, 0, 0, 1},
            {1, 1, 1, 0}
        };

        System.out.println(arestasDoGrafo(matrizAdjacencia2));
        System.out.println(grausDoVertice(matrizAdjacencia2));
        System.out.println("");

        Integer[][] matrizAdjacencia3 = 
        {
            {0, 0, 0, 1},
            {1, 0, 0, 2},
            {1, 0, 1, 1},
            {0, 0, 1, 0}
        };

        System.out.println(arestasDoGrafo(matrizAdjacencia3));
        System.out.println(grausDoVertice(matrizAdjacencia3));
        System.out.println("");

        Integer[][] matrizAdjacencia4 = 
        {
            {0, 1, 2, 1},
            {1, 0, 0, 1},
            {2, 0, 0, 0},
            {1, 1, 0, 1}
        };

        System.out.println(arestasDoGrafo(matrizAdjacencia4));
        System.out.println(grausDoVertice(matrizAdjacencia4));
        System.out.println("");
    }
}
