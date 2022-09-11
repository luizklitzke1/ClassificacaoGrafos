//Aluno: Luiz Gustavo Klitzke

public class ProcessadorGrafo 
{
    //Qual  é  o  tipo  do  grafo  (dirigido  ou  não,  simples  ou  multigrafo,  regular,  completo,  nulo  ou bipartido) 
    public String tipoDoGrafoparametro(Integer[][] matrizAdjacencia)
    {
        String tipoGrafo = "";

        final int ordem = matrizAdjacencia.length;

        boolean dirigido = false;
        boolean nulo = true;
        boolean simples = true;
        boolean completo = true;

        for (int i = 0; i < ordem; ++i)
        {
            for (int j = 0; j < ordem; ++j)
            {
                int valor = matrizAdjacencia[i][j];

                if ( j < i) //Abaixo da diagonal principal
                {
                    if (matrizAdjacencia[i][j] != matrizAdjacencia[j][i])
                    {
                        dirigido = true;
                    }
                }

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
    public String arestasDoGrafoparametro(Integer[][] matrizAdjacencia)
    {
        String retorno = "";


        return retorno;
    }

    //Qual é o graude cada vértice. Liste a sequência de graus
    public String grausDoVerticeparâmetro(Integer[][] matrizAdjacencia)
    {
        String retorno = "";


        return retorno;
    }
}
