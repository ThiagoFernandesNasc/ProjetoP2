package servicos;

import interfaces.repository.IAlunoRepository;
import interfaces.repository.IModalidadeRepository;
import interfaces.repository.IProfessorRepository;
import interfaces.repository.ITurmaRepository;
import interfaces.servicos.ITurmaService;

import java.util.List;

import entidades.Aluno;
import entidades.Turma;

/**
 * Implementação do serviço de gerenciamento de turmas.
 * Responsável por implementar a lógica de negócio relacionada às turmas.
 */
public class TurmaService implements ITurmaService {
    private final ITurmaRepository turmaRepository;
    private final IAlunoRepository alunoRepository;
    private final IProfessorRepository professorRepository;
    private final IModalidadeRepository modalidadeRepository;

    /**
     * Construtor do serviço de turmas.
     * @param turmaRepository Repositório de turmas
     * @param alunoRepository Repositório de alunos
     * @param professorRepository Repositório de professores
     * @param modalidadeRepository Repositório de modalidades
     * @throws IllegalArgumentException se algum dos repositórios for nulo
     */
    public TurmaService(ITurmaRepository turmaRepository,
                        IAlunoRepository alunoRepository,
                        IProfessorRepository professorRepository,
                        IModalidadeRepository modalidadeRepository) {
        if (turmaRepository == null || alunoRepository == null || 
            professorRepository == null || modalidadeRepository == null) {
            throw new IllegalArgumentException("Nenhum dos repositórios pode ser nulo");
        }
        this.turmaRepository = turmaRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.modalidadeRepository = modalidadeRepository;
    }

    /**
     * Cria uma nova turma no sistema.
     * @param turma Turma a ser criada
     * @throws IllegalArgumentException se a turma for nula, já existir, ou se professor/modalidade não forem encontrados
     */
    @Override
    public void criarTurma(Turma turma) {
        if (turma == null) {
            throw new IllegalArgumentException("Turma não pode ser nula");
        }
        if (turma.getProfessor() == null) {
            throw new IllegalArgumentException("Professor não pode ser nulo");
        }
        if (turma.getModalidade() == null) {
            throw new IllegalArgumentException("Modalidade não pode ser nula");
        }
        
        if (turmaRepository.buscarPorId(turma.getId()) != null) {
            throw new IllegalArgumentException("Turma com este ID já existe");
        }
        if (professorRepository.buscarPorId(turma.getProfessor().getId()) == null) {
            throw new IllegalArgumentException("Professor não encontrado");
        }
        if (modalidadeRepository.buscarPorId(turma.getModalidade().getId()) == null) {
            throw new IllegalArgumentException("Modalidade não encontrada");
        }
        turmaRepository.salvar(turma);
    }

    /**
     * Busca uma turma pelo ID.
     * @param id ID da turma a ser buscada
     * @return Turma encontrada ou null se não existir
     */
    @Override
    public Turma buscarTurmaPorId(int id) {
        return turmaRepository.buscarPorId(id);
    }

    /**
     * Atualiza os dados de uma turma.
     * @param turma Turma com os dados atualizados
     * @throws IllegalArgumentException se a turma for nula ou não existir
     */
    @Override
    public void atualizarTurma(Turma turma) {
        if (turma == null) {
            throw new IllegalArgumentException("Turma não pode ser nula");
        }
        if (turmaRepository.buscarPorId(turma.getId()) == null) {
            throw new IllegalArgumentException("Turma não encontrada");
        }
        turmaRepository.alterar(turma);
    }

    /**
     * Encerra uma turma.
     * @param id ID da turma a ser encerrada
     * @throws IllegalArgumentException se a turma não existir
     */
    @Override
    public void encerrarTurma(int id) {
        if (turmaRepository.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Turma não encontrada");
        }
        turmaRepository.remover(id);
    }

    /**
     * Lista todas as turmas cadastradas.
     * @return Lista de todas as turmas
     */
    @Override
    public List<Turma> listarTodasTurmas() {
        return turmaRepository.listarTodos();
    }

    /**
     * Matricula um aluno em uma turma.
     * @param idTurma ID da turma
     * @param cpfAluno CPF do aluno
     * @return true se a matrícula foi realizada com sucesso, false caso contrário
     * @throws IllegalArgumentException se o CPF do aluno for nulo ou vazio
     */
    @Override
    public boolean matricularAluno(int idTurma, String cpfAluno) {
        if (cpfAluno == null || cpfAluno.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF do aluno não pode ser nulo ou vazio");
        }

        Turma turma = turmaRepository.buscarPorId(idTurma);
        Aluno aluno = alunoRepository.buscarPorCpf(cpfAluno);

        if (turma == null) {
            throw new IllegalArgumentException("Turma não encontrada");
        }
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não encontrado");
        }

        return turma.adicionarAluno(aluno);
    }
}