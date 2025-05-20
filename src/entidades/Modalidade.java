package entidades;

public class Modalidade {
    private int id;
    private String nome;
    private String descricao;
    private int idadeMinima;
    private int idadeMaxima;

    public Modalidade() {
    }

    public Modalidade(int id, String nome, String descricao, int idadeMinima, int idadeMaxima) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.idadeMinima = idadeMinima;
        this.idadeMaxima = idadeMaxima;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdadeMinima() {
        return idadeMinima;
    }

    public void setIdadeMinima(int idadeMinima) {
        this.idadeMinima = idadeMinima;
    }

    public int getIdadeMaxima() {
        return idadeMaxima;
    }

    public void setIdadeMaxima(int idadeMaxima) {
        this.idadeMaxima = idadeMaxima;
    }

    @Override
    public String toString() {
        return nome + " (" + idadeMinima + "-" + idadeMaxima + " anos)";
    }
}