package quebra;

import com.opencsv.CSVReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

public class ListaOfertadas {
    static List<Ofertada> listaOfertadas = new ArrayList<>();

    public static void leOfertadas(String file) {
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReader(filereader);
            
            String[] nextRecord;
            
            // Joga "fora" o cabeçalho da tabela
            nextRecord = csvReader.readNext();
            
            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    String[] data = cell.split(";", -1);

                    if (data.length > 0) {
                        
                        //TODO: Adicionar campos faltantes
                        String codCurso = data[0];
                        String nomeCurso = data[4];
                        String nomeDisciplina = data[5];
                        String codDisciplina = data[3];
                        int cargaHoraria = !data[9].equals("") ? Integer.parseInt(data[9]) : 0;
                        int numVersao = !data[1].equals("") ? Integer.parseInt(data[1]) : 0;
                        int periodoIdeal = !data[6].equals("") ? Integer.parseInt(data[6]) : 0;
                        int numHoras = !data[7].equals("") ? Integer.parseInt(data[7]) : 0;
    
                        Ofertada ofertada = new Ofertada(
                            codCurso,
                            nomeCurso,
                            nomeDisciplina,
                            codDisciplina, 
                            cargaHoraria, 
                            numVersao,  
                            periodoIdeal,  
                            numHoras
                        );

                        listaOfertadas.add(ofertada);
                    }
                }
            }
    
            csvReader.close();            
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
