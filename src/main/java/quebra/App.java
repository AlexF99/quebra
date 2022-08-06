package quebra;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Csv.readDataLineByLine("./csvs/historico.csv");
        System.out.println("worked");
    }
}
