package quebra;

import java.io.FileReader;

import com.opencsv.CSVReader;

public class Aluno {
    String grr;
    String nomeAluno;
    String codCurso;
    String nomeCurso;

    public Aluno() {
    }

    public Aluno(String grr, String nomeAluno, String codCurso, String nomeCurso) {
        this.grr = grr;
        this.nomeAluno = nomeAluno;
        this.codCurso = codCurso;
        this.nomeCurso = nomeCurso;
    }

    public void leAluno(String file) {
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReader(filereader);

            String[] nextRecord;

            // Joga "fora" o cabeçalho da tabela
            nextRecord = csvReader.readNext();
            nextRecord = csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
                if (nextRecord.length > 0) {

                    this.grr = nextRecord[0];
                    this.nomeAluno = nextRecord[1];
                    this.codCurso = nextRecord[2];
                    this.nomeCurso = nextRecord[3];
                }
            }

            csvReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getGrr() {
        return this.grr;
    }

    public String getNomeAluno() {
        return this.nomeAluno;
    }

    public void setGrr(String grr) {
        this.grr = grr;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }
}
