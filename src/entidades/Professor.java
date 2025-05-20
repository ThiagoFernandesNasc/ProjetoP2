package entidades;

/**
 * Classe que representa um Professor no sistema.
 * Contém informações básicas como ID, nome e disciplina.
 */
public final class Professor {
    private int id;
    private String nome;
    private String disciplina;

    /**
     * Construtor padrão.
     */
    public Professor() {}

    /**
     * Construtor com todos os parâmetros.
     * @param id Identificador único do professor
     * @param nome Nome do professor
     * @param disciplina Disciplina lecionada pelo professor
     * @throws IllegalArgumentException se nome ou disciplina forem nulos ou vazios
     */
    public Professor(int id, String nome, String disciplina) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        if (disciplina == null || disciplina.trim().isEmpty()) {
            throw new IllegalArgumentException("Disciplina não pode ser nula ou vazia");
        }
        this.id = id;
        this.nome = nome.trim();
        this.disciplina = disciplina.trim();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do professor.
     * @param nome Nome do professor
     * @throws IllegalArgumentException se o nome for nulo ou vazio
     */
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        this.nome = nome.trim();
    }

    public String getDisciplina() {
        return disciplina;
    }

    /**
     * Define a disciplina do professor.
     * @param disciplina Disciplina lecionada
     * @throws IllegalArgumentException se a disciplina for nula ou vazia
     */
    public void setDisciplina(String disciplina) {
        if (disciplina == null || disciplina.trim().isEmpty()) {
            throw new IllegalArgumentException("Disciplina não pode ser nula ou vazia");
        }
        this.disciplina = disciplina.trim();
    }

    @Override
    public String toString() {
        return "Professor{id=" + id + ", nome='" + nome + "', disciplina='" + disciplina + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professor professor = (Professor) o;
        return id == professor.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}