package quebra;

import java.awt.event.*;
import javax.swing.*;
public class App 
{
    public static void main( String[] args )
    {
        ListaCursadas listaCursadas = ListaCursadas.getInstance();
        ListaOfertadas listaOfertadas = ListaOfertadas.getInstance();

        listaCursadas.leCursadas("./csvs/historico.csv");
        listaOfertadas.leOfertadas("./csvs/2019_2.csv");

        JFrame janela = new FrontEnd();
		janela.setVisible(true);
		WindowListener x = new WindowAdapter ()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		};
		janela.addWindowListener(x);
        
    }
}
