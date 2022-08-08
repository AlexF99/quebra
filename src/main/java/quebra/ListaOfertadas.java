package quebra;

public class ListaOfertadas {
    class Ofertada extends Disciplina {
        int periodoIdeal;
        int versaoDescSituacao;
        int numHoras;

        public Ofertada(String codCurso,
                String nomeCurso,
                String nomeDisciplina,
                String codDisciplina,
                int cargaHoraria,
                int numVersao,
                int tipo_disciplina,
                int periodoIdeal,
                int versaoDescSituacao,
                int numHoras) {
            super(codCurso, nomeCurso, nomeDisciplina, codDisciplina, cargaHoraria, numVersao, tipo_disciplina);
            this.periodoIdeal = periodoIdeal;
            this.versaoDescSituacao = versaoDescSituacao;
            this.numHoras = numHoras;
        }

        // getters
        public int getperiodoIdeal(){return this.periodoIdeal;}
        public int getversaoDescSituacao(){return this.versaoDescSituacao;}
        public int getnumHoras(){return this.numHoras;}
        
        //setters
        public void setPeriodoIdeal(int periodoIdeal){this.periodoIdeal = periodoIdeal;}
        public void setVersaoDescSituacao(int versaoDescSituacao){this.versaoDescSituacao = versaoDescSituacao;}
        public void setNumHoras(int numHoras){this.numHoras = numHoras;}
    }
}
