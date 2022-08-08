package quebra;

public class ListaCursadas {
    class Cursada extends Disciplina {
        String grr;
        String nomeAluno;
        String sigla;
        int periodo;
        int ano;
        int situacao;
        int media;
        int frequencia;

        public Cursada(String codCurso,
                String nomeCurso,
                String nomeDisciplina,
                String codDisciplina,
                int cargaHoraria,
                int numVersao,
                int tipo_disciplina,
                String grr,
                String nomeAluno,
                int periodo,
                int ano,
                int situacao,
                int media,
                int frequencia,
                String sigla) {
            super(codCurso, nomeCurso, nomeDisciplina, codDisciplina, cargaHoraria, numVersao, tipo_disciplina);
            this.grr = grr;
            this.nomeAluno = nomeAluno;
            this.periodo = periodo;
            this.ano = ano;
            this.situacao = situacao;
            this.media = media;
            this.frequencia = frequencia;
            this.sigla = sigla;
        }

        // getters
        public String getGrr() {return this.grr;}
        public String getNomeAluno() {return this.nomeAluno;}
        public String getSigla() {return this.sigla;}
        public int getPeriodo() {return this.periodo;}
        public int getAno() {return this.ano;}
        public int getSituacao() {return this.situacao;}
        public int getMedia() {return this.media;}
        public int getFrequencia() {return this.frequencia;}

        //setters
        public void setGrr(String grr) {this.grr = grr;}
        public void setNomeAluno(String nomeAluno) {this.nomeAluno = nomeAluno;}
        public void setSigla(String sigla) {this.sigla = sigla;}
        public void setPeriodo(int periodo) {this.periodo = periodo;}
        public void setAno(int ano) {this.ano = ano;}
        public void setSituacao(int situacao) {this.situacao = situacao;}
        public void setMedia(int media) {this.media = media;}
        public void setFrequencia(int frequencia) {this.frequencia = frequencia;}
    }
}
