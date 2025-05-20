package entidades;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class Turma {
    private int id;
    private Modalidade modalidade;
    private Professor professor;
    private List<Aluno> alunos;
    private DayOfWeek diaSemana;
    private LocalTime horario;
    private int capacidadeMaxima;

    public Turma() {
    }

    public Turma(int id, Modalidade modalidade, Professor professor,
                 DayOfWeek diaSemana, LocalTime horario, int capacidadeMaxima) {
        this.id = id;
        this.modalidade = modalidade;
        this.professor = professor;
        this.diaSemana = diaSemana;
        this.horario = horario;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DayOfWeek diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public boolean adicionarAluno(Aluno aluno) {
        if (alunos.size() < capacidadeMaxima) {
            alunos.add(aluno);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return modalidade.getNome() + " - " + diaSemana + " " + horario +
                " (Prof. " + professor.getNome() + ")";
    }
}