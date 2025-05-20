package interfaces.repository;

import java.util.List;

import entidades.Aluno;

public interface IAlunoRepository {
    void salvar(Aluno aluno);
    void remover(String cpf);
    void alterar(Aluno aluno);
    List<Aluno> listarTodos();
    Aluno buscarPorCpf(String cpf);
}