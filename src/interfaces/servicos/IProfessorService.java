package interfaces.servicos;

import entidades.Professor;

public interface IProfessorService {
    void cadastrar(Professor professor);
    Professor buscarPorId(int id);
    void atualizar(Professor professor);
    void remover(int id);
}