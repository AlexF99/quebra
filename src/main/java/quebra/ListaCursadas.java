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
                for (String cell : nextRecord) {
                    String[] data = cell.split(";", -1);

                    if (data.length > 0) {

                        // TODO: Adicionar campos faltantes
                        String codCurso = data[2];
                        String nomeCurso = data[3];
                        String nomeDisciplina = data[11];
                        String codDisciplina = data[10];
                        int cargaHoraria = !data[12].equals("") ? Integer.parseInt(data[12]) : 0;
                        int numVersao = !data[4].equals("") ? Integer.parseInt(data[4]) : 0;

                        String grr = data[0];
                        String nomeAluno = data[1];

                        String[] stringPeriodo = data[8].split("o");
                        int periodo = Integer.parseInt((stringPeriodo[0]));
                        int ano = !data[5].equals("") ? Integer.parseInt(data[5]) : 0;
                        int situacao = !data[7].equals("") ? Integer.parseInt(data[7]) : 0;
                        int media = !data[6].equals("") ? Integer.parseInt(data[6]) : 0;
                        int frequencia = !data[14].equals("") ? Integer.parseInt(data[14]) : 0;
                        String sigla = data[15];

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
            }

            csvReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
