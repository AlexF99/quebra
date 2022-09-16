package quebra;

public class Ofertada extends Disciplina {
    int periodoIdeal;
    int versaoDescSituacao;
    int numHoras;

    public Ofertada(
            String nomeDisciplina,
            String codDisciplina,
            int cargaHoraria,
            int numVersao,
            // int tipoDisciplina,
            int periodoIdeal,
            // int versaoDescSituacao,
            int numHoras) {
        super(nomeDisciplina, codDisciplina, cargaHoraria, numVersao /*tipoDisciplina*/);
        this.periodoIdeal = periodoIdeal;
        // this.versaoDescSituacao = versaoDescSituacao;
        this.numHoras = numHoras;
    }

    // getters
    public int getperiodoIdeal() {
        return this.periodoIdeal;
    }

    public int getversaoDescSituacao() {
        return this.versaoDescSituacao;
    }

    public int getnumHoras() {
        return this.numHoras;
    }

    // setters
    public void setPeriodoIdeal(int periodoIdeal) {
        this.periodoIdeal = periodoIdeal;
    }

    public void setVersaoDescSituacao(int versaoDescSituacao) {
        this.versaoDescSituacao = versaoDescSituacao;
    }

    public void setNumHoras(int numHoras) {
        this.numHoras = numHoras;
    }
}
