package quebra;

public class Disciplina {
    private String codCurso;
    private String nomeCurso;
    private String nomeDisciplina;
    private String codDisciplina;
    private int cargaHoraria;
    private int numVersao;
    private int tipoDisciplina; // enum

    public Disciplina(String codCurso,
            String nomeCurso,
            String nomeDisciplina,
            String codDisciplina,
            int cargaHoraria,
            int numVersao,
            int tipoDisciplina) {
        this.codCurso = codCurso;
        this.nomeCurso = nomeCurso;
        this.nomeDisciplina = nomeDisciplina;
        this.codDisciplina = codDisciplina;
        this.cargaHoraria = cargaHoraria;
        this.numVersao = numVersao;
        this.tipoDisciplina = tipoDisciplina;
    }

    // getters
    public String getCodCurso() {
        return this.codCurso;
    }

    public String getNomeCurso() {
        return this.nomeCurso;
    }

    public String getNomeDisciplina() {
        return this.nomeDisciplina;
    }

    public String getCodDisciplina() {
        return this.codDisciplina;
    }

    public int getCargaHoraria() {
        return this.cargaHoraria;
    }

    public int getNumVersao() {
        return this.numVersao;
    }

    public int getTipoDisciplina() {
        return this.tipoDisciplina;
    }

    // setters
    public void getCodCurso(String codCurso) {
        this.codCurso = codCurso;
    }

    public void getNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public void getNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public void getCodDisciplina(String codDisciplina) {
        this.codDisciplina = codDisciplina;
    }

    public void getCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public void getNumVersao(int numVersao) {
        this.numVersao = numVersao;
    }

    public void getTipoDisciplina(int tipoDisciplina) {
        this.tipoDisciplina = tipoDisciplina;
    }
}
