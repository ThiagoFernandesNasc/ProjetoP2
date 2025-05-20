package servicos;

import entidades.Professor;
import interfaces.repository.IProfessorRepository;
import interfaces.servicos.IProfessorService;

public class ProfessorService implements IProfessorService {

    private IProfessorRepository repositorio;

    public ProfessorService(IProfessorRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void cadastrar(Professor professor) {
        repositorio.salvar(professor);
    }

    @Override
    public Professor buscarPorId(int id) {
        for (Professor p : repositorio.listar()) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    @Override
    public void atualizar(Professor professor) {
        repositorio.alterar(professor);
    }

    @Override
    public void remover(int id) {
        repositorio.remover(id);
    }
}