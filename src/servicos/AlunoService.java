package servicos;

import interfaces.repository.IAlunoRepository;
import interfaces.servicos.IAlunoService;

import java.util.List;

import entidades.Aluno;

/**
 * Implementação do serviço de gerenciamento de alunos.
 * Responsável por implementar a lógica de negócio relacionada aos alunos.
 */
public class AlunoService implements IAlunoService {
    private final IAlunoRepository repository;

    /**
     * Construtor do serviço de alunos.
     * @param repository Repositório de alunos a ser utilizado
     * @throws IllegalArgumentException se o repositório for nulo
     */
    public AlunoService(IAlunoRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repositório não pode ser nulo");
        }
        this.repository = repository;
    }

    /**
     * Cadastra um novo aluno no sistema.
     * @param aluno Aluno a ser cadastrado
     * @throws IllegalArgumentException se o aluno for nulo ou já existir um aluno com o mesmo CPF
     */
    @Override
    public void cadastrarAluno(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não pode ser nulo");
        }
        if (aluno.getCpf() == null || aluno.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF do aluno não pode ser nulo ou vazio");
        }
        if (repository.buscarPorCpf(aluno.getCpf()) != null) {
            throw new IllegalArgumentException("Aluno já cadastrado com este CPF");
        }
        repository.salvar(aluno);
    }

    /**
     * Busca um aluno pelo CPF.
     * @param cpf CPF do aluno a ser buscado
     * @return Aluno encontrado ou null se não existir
     * @throws IllegalArgumentException se o CPF for nulo ou vazio
     */
    @Override
    public Aluno buscarAlunoPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }
        return repository.buscarPorCpf(cpf);
    }

    /**
     * Atualiza os dados de um aluno.
     * @param aluno Aluno com os dados atualizados
     * @throws IllegalArgumentException se o aluno for nulo ou não existir no sistema
     */
    @Override
    public void atualizarAluno(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não pode ser nulo");
        }
        if (aluno.getCpf() == null || aluno.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF do aluno não pode ser nulo ou vazio");
        }
        if (repository.buscarPorCpf(aluno.getCpf()) == null) {
            throw new IllegalArgumentException("Aluno não encontrado");
        }
        repository.alterar(aluno);
    }

    /**
     * Remove um aluno do sistema.
     * @param cpf CPF do aluno a ser removido
     * @throws IllegalArgumentException se o CPF for nulo ou vazio
     */
    @Override
    public void removerAluno(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }
        if (repository.buscarPorCpf(cpf) == null) {
            throw new IllegalArgumentException("Aluno não encontrado");
        }
        repository.remover(cpf);
    }

    /**
     * Lista todos os alunos cadastrados no sistema.
     * @return Lista de todos os alunos
     */
    @Override
    public List<Aluno> listarTodosAlunos() {
        return repository.listarTodos();
    }

    /**
     * Obtém o repositório de alunos.
     * @return Repositório de alunos
     */
    public IAlunoRepository getRepository() {
        return repository;
    }

    public void cadastrar(Aluno aluno) {

    }

    public void atualizarAluno() {
    }
}