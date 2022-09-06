package quebra;

import com.opencsv.CSVReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

public class ListaCursadas {
    private static ListaCursadas uniqueInstance;

    private ListaCursadas() {
    }

    public static synchronized ListaCursadas getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ListaCursadas();
        }
        return uniqueInstance;
    }

    List<Cursada> lista = new ArrayList<>();

    public void leCursadas(String file) {
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReader(filereader);

            String[] nextRecord;

            // Joga "fora" o cabeçalho da tabela
            nextRecord = csvReader.readNext();
            nextRecord = csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
                if (nextRecord.length > 0) {

                    String codCurso = nextRecord[2];
                    String nomeCurso = nextRecord[3];
                    String nomeDisciplina = nextRecord[11];
                    String codDisciplina = nextRecord[10];
                    int cargaHoraria = !nextRecord[12].equals("") ? Integer.parseInt(nextRecord[12]) : 0;
                    int numVersao = !nextRecord[4].equals("") ? Integer.parseInt(nextRecord[4]) : 0;

                    String grr = nextRecord[0];
                    String nomeAluno = nextRecord[1];

                    String[] stringPeriodo = nextRecord[8].split("o");
                    Integer periodo = Integer.parseInt((stringPeriodo[0]));
                    int ano = !nextRecord[5].equals("") ? Integer.parseInt(nextRecord[5]) : 0;
                    Integer situacao = !nextRecord[7].equals("") ? Integer.parseInt(nextRecord[7]) : 0;
                    Integer media = !nextRecord[6].equals("") ? Integer.parseInt(nextRecord[6]) : 0;
                    int frequencia = !nextRecord[14].equals("") ? Integer.parseInt(nextRecord[14]) : 0;
                    String sigla = nextRecord[15];

                    Cursada cursada = new Cursada(
                            codCurso,
                            nomeCurso,
                            nomeDisciplina,
                            codDisciplina,
                            cargaHoraria,
                            numVersao,
                            grr,
                            nomeAluno,
                            periodo,
                            ano,
                            situacao,
                            media,
                            frequencia,
                            sigla);

                    lista.add(cursada);
                }
            }

            csvReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
