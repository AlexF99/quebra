package quebra;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // Csv.lerHistorico("./csvs/historico.csv");
     
        ListaOfertadas.leOfertadas("./csvs/2019_2.csv");
        ListaCursadas.leCursadas("./csvs/historico.csv");
        
        for (Ofertada ofertada : ListaOfertadas.listaOfertadas) {
            System.out.println(ofertada.getNomeDisciplina());
        }

        System.out.println();

        for (Cursada cursada : ListaCursadas.listaCursadas) {
            System.out.println(cursada.getNomeDisciplina());
        }
        
    }
}
