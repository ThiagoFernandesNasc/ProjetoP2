package entidades;

import java.time.LocalDate;

public class Aluno extends Pessoa {
    private LocalDate dataNascimento;
    private String responsavel;
    private String observacoesSaude;

    public Aluno() {
    }

    public Aluno(String nome, String cpf, String telefone, String email,
                 LocalDate dataNascimento, String responsavel, String observacoesSaude) {
        super(nome, cpf, telefone, email);
        this.dataNascimento = dataNascimento;
        this.responsavel = responsavel;
        this.observacoesSaude = observacoesSaude;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Aluno: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Data Nascimento: " + dataNascimento);
        System.out.println("Responsável: " + responsavel);
    }

    // Getters e Setters específicos
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getObservacoesSaude() {
        return observacoesSaude;
    }

    public void setObservacoesSaude(String observacoesSaude) {
        this.observacoesSaude = observacoesSaude;
    }
}