package quebra;

public class App 
{
    public static void main( String[] args )
    {
        ListaCursadas.leCursadas("./csvs/historico.csv");
        ListaOfertadas.leOfertadas("./csvs/2019_2.csv");
        
        for (Ofertada ofertada : ListaOfertadas.listaOfertadas) {
            System.out.println(ofertada.getNomeDisciplina());
        }

        System.out.println();

        for (Cursada cursada : ListaCursadas.listaCursadas) {
            System.out.println(cursada.getNomeDisciplina());
        }
        
    }
}
