package interfaces.repository;

import java.util.List;

import entidades.Turma;

public interface ITurmaRepository {
    void salvar(Turma turma);
    void remover(int id);
    void alterar(Turma turma);
    List<Turma> listarTodos();
    Turma buscarPorId(int id);
    boolean matricularAluno(int idTurma, String cpfAluno);
}