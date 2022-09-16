package quebra;

public class Cursada extends Disciplina {

    String sigla;
    Integer periodo;
    int ano;
    Integer situacao;
    int media;
    int frequencia;

    public Cursada(
            String nomeDisciplina,
            String codDisciplina,
            int cargaHoraria,
            int numVersao,
            // int tipoDisciplina,
            String grr,
            String nomeAluno,
            Integer periodo,
            int ano,
            Integer situacao,
            Integer media,
            int frequencia,
            String sigla) {
        super(nomeDisciplina, codDisciplina, cargaHoraria, numVersao);

        this.periodo = periodo;
        this.ano = ano;
        this.situacao = situacao;
        this.media = media;
        this.frequencia = frequencia;
        this.sigla = sigla;
    }

    // getters
    public String getSigla() {
        return this.sigla;
    }

    public Integer getPeriodo() {
        return this.periodo;
    }

    public int getAno() {
        return this.ano;
    }

    public Integer getSituacao() {
        return this.situacao;
    }

    public String getStrSituacao() {
        switch (this.situacao) {
            case 1:
                return "aprovado";
            case 2:
                return "reprovado";
            case 3:
                return "reprovado";
            case 10:
                return "matricula";

            default:
                return "matricula";
        }
    }

    public Integer getMedia() {
        return this.media;
    }

    public int getFrequencia() {
        return this.frequencia;
    }

    // setters
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setSituacao(Integer situacao) {
        this.situacao = situacao;
    }

    public void setMedia(Integer media) {
        this.media = media;
    }

    public void setFrequencia(int frequencia) {
        this.frequencia = frequencia;
    }
}
