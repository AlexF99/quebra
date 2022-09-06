package quebra;

import com.opencsv.CSVReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

public class ListaOfertadas {
    private static ListaOfertadas uniqueInstance;

    private ListaOfertadas() {
    }

    public static synchronized ListaOfertadas getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ListaOfertadas();
        }
        return uniqueInstance;
    }

    List<Ofertada> lista = new ArrayList<>();

    public void leOfertadas(String file) {
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReader(filereader);

            String[] nextRecord;

            nextRecord = csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
                if (nextRecord.length > 0) {
                    String codCurso = nextRecord[0];
                    String nomeCurso = nextRecord[4];
                    String nomeDisciplina = nextRecord[5];
                    String codDisciplina = nextRecord[3];
                    int cargaHoraria = !nextRecord[9].equals("") ? Integer.parseInt(nextRecord[9]) : 0;
                    int numVersao = !nextRecord[1].equals("") ? Integer.parseInt(nextRecord[1]) : 0;
                    int periodoIdeal = !nextRecord[6].equals("") ? Integer.parseInt(nextRecord[6]) : 0;
                    int numHoras = !nextRecord[7].equals("") ? Integer.parseInt(nextRecord[7]) : 0;
                    Ofertada ofertada = new Ofertada(
                            codCurso,
                            nomeCurso,
                            nomeDisciplina,
                            codDisciplina,
                            cargaHoraria,
                            numVersao,
                            periodoIdeal,
                            numHoras);
                    lista.add(ofertada);
                }
            }

            csvReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
