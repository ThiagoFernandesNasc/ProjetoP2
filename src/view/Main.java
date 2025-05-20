package view;

import entidades.Aluno;
import entidades.Modalidade;
import entidades.Professor;
import entidades.Turma;
import interfaces.repository.IAlunoRepository;
import interfaces.repository.IModalidadeRepository;
import interfaces.repository.IProfessorRepository;
import interfaces.repository.ITurmaRepository;
import interfaces.servicos.IAlunoService;
import interfaces.servicos.IModalidadeService;
import interfaces.servicos.IProfessorService;
import interfaces.servicos.ITurmaService;
import repositorys.AlunoRepository;
import repositorys.ModalidadeRepository;
import repositorys.ProfessorRepository;
import repositorys.TurmaRepository;
import servicos.AlunoService;
import servicos.ModalidadeService;
import servicos.ProfessorService;
import servicos.TurmaService;
import database.Conexao;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * Classe principal que implementa a interface do usuário para gerenciamento do sistema.
 */
public class Main {
    public static void main(String[] args) {
        if (!Conexao.testarConexao()) {
            System.out.println("Não foi possível conectar ao banco de dados. Encerrando aplicação...");
            return;
        }

        Scanner sc = new Scanner(System.in);
        
        // Inicialização dos repositórios
        IProfessorRepository professorRepo = new ProfessorRepository();
        IAlunoRepository alunoRepo = new AlunoRepository();
        IModalidadeRepository modalidadeRepo = new ModalidadeRepository();
        ITurmaRepository turmaRepo = new TurmaRepository();
        
        // Inicialização dos serviços
        IProfessorService professorService = new ProfessorService(professorRepo);
        IAlunoService alunoService = new AlunoService(alunoRepo);
        IModalidadeService modalidadeService = new ModalidadeService(modalidadeRepo);
        ITurmaService turmaService = new TurmaService(turmaRepo, alunoRepo, professorRepo, modalidadeRepo);

        while (true) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Gerenciar Professores");
            System.out.println("2. Gerenciar Alunos");
            System.out.println("3. Gerenciar Modalidades");
            System.out.println("4. Gerenciar Turmas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = sc.nextInt();
            sc.nextLine();

            try {
                switch (opcao) {
                    case 1 -> menuProfessores(sc, professorService, professorRepo);
                    case 2 -> menuAlunos(sc, alunoService);
                    case 3 -> menuModalidades(sc, modalidadeService);
                    case 4 -> menuTurmas(sc, turmaService, alunoService, professorService, modalidadeService);
                    case 0 -> {
                        System.out.println("Encerrando...");
                        return;
                    }
                    default -> System.out.println("Opção inválida!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }

    private static void menuProfessores(Scanner sc, IProfessorService servico, IProfessorRepository repositorio) {
        while (true) {
            System.out.println("\n===== MENU PROFESSORES =====");
            System.out.println("1. Cadastrar Professor");
            System.out.println("2. Buscar Professor por ID");
            System.out.println("3. Atualizar Professor");
            System.out.println("4. Remover Professor");
            System.out.println("5. Listar Professores");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = sc.nextInt();
            sc.nextLine();

            try {
                switch (opcao) {
                    case 1 -> {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Disciplina: ");
                        String disciplina = sc.nextLine();
                        servico.cadastrar(new Professor(id, nome, disciplina));
                        System.out.println("Professor cadastrado com sucesso!");
                    }
                    case 2 -> {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        Professor p = servico.buscarPorId(id);
                        System.out.println(p != null ? p : "Professor não encontrado.");
                    }
                    case 3 -> {
                        System.out.print("ID do professor a atualizar: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Novo nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Nova disciplina: ");
                        String disciplina = sc.nextLine();
                        servico.atualizar(new Professor(id, nome, disciplina));
                        System.out.println("Professor atualizado com sucesso!");
                    }
                    case 4 -> {
                        System.out.print("ID do professor a remover: ");
                        int id = sc.nextInt();
                        servico.remover(id);
                        System.out.println("Professor removido com sucesso!");
                    }
                    case 5 -> {
                        System.out.println("\nLista de Professores:");
                        for (Professor p : repositorio.listar()) {
                            System.out.println(p);
                        }
                    }
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Opção inválida!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }

    private static void menuAlunos(Scanner sc, IAlunoService servico) {
        while (true) {
            System.out.println("\n===== MENU ALUNOS =====");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Buscar Aluno por CPF");
            System.out.println("3. Atualizar Aluno");
            System.out.println("4. Remover Aluno");
            System.out.println("5. Listar Alunos");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = sc.nextInt();
            sc.nextLine();

            try {
                switch (opcao) {
                    case 1 -> {
                        System.out.print("CPF: ");
                        String cpf = sc.nextLine();
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Telefone: ");
                        String telefone = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Data de Nascimento (YYYY-MM-DD): ");
                        LocalDate dataNascimento = LocalDate.parse(sc.nextLine());
                        System.out.print("Responsável: ");
                        String responsavel = sc.nextLine();
                        System.out.print("Observações de Saúde: ");
                        String observacoesSaude = sc.nextLine();
                        servico.cadastrarAluno(new Aluno(nome, cpf, telefone, email, dataNascimento, responsavel, observacoesSaude));
                        System.out.println("Aluno cadastrado com sucesso!");
                    }
                    case 2 -> {
                        System.out.print("CPF: ");
                        String cpf = sc.nextLine();
                        Aluno a = servico.buscarAlunoPorCpf(cpf);
                        System.out.println(a != null ? a : "Aluno não encontrado.");
                    }
                    case 3 -> {
                        System.out.print("CPF do aluno a atualizar: ");
                        String cpf = sc.nextLine();
                        System.out.print("Novo nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Novo telefone: ");
                        String telefone = sc.nextLine();
                        System.out.print("Novo email: ");
                        String email = sc.nextLine();
                        System.out.print("Nova data de nascimento (YYYY-MM-DD): ");
                        LocalDate dataNascimento = LocalDate.parse(sc.nextLine());
                        System.out.print("Novo responsável: ");
                        String responsavel = sc.nextLine();
                        System.out.print("Novas observações de saúde: ");
                        String observacoesSaude = sc.nextLine();
                        servico.atualizarAluno(new Aluno(nome, cpf, telefone, email, dataNascimento, responsavel, observacoesSaude));
                        System.out.println("Aluno atualizado com sucesso!");
                    }
                    case 4 -> {
                        System.out.print("CPF do aluno a remover: ");
                        String cpf = sc.nextLine();
                        servico.removerAluno(cpf);
                        System.out.println("Aluno removido com sucesso!");
                    }
                    case 5 -> {
                        System.out.println("\nLista de Alunos:");
                        for (Aluno a : servico.listarTodosAlunos()) {
                            System.out.println(a);
                        }
                    }
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Opção inválida!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }

    private static void menuModalidades(Scanner sc, IModalidadeService servico) {
        while (true) {
            System.out.println("\n===== MENU MODALIDADES =====");
            System.out.println("1. Cadastrar Modalidade");
            System.out.println("2. Buscar Modalidade por ID");
            System.out.println("3. Atualizar Modalidade");
            System.out.println("4. Remover Modalidade");
            System.out.println("5. Listar Modalidades");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = sc.nextInt();
            sc.nextLine();

            try {
                switch (opcao) {
                    case 1 -> {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Descrição: ");
                        String descricao = sc.nextLine();
                        System.out.print("Idade Mínima: ");
                        int idadeMinima = sc.nextInt();
                        System.out.print("Idade Máxima: ");
                        int idadeMaxima = sc.nextInt();
                        servico.cadastrarModalidade(new Modalidade(id, nome, descricao, idadeMinima, idadeMaxima));
                        System.out.println("Modalidade cadastrada com sucesso!");
                    }
                    case 2 -> {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        Modalidade m = servico.buscarModalidadePorId(id);
                        System.out.println(m != null ? m : "Modalidade não encontrada.");
                    }
                    case 3 -> {
                        System.out.print("ID da modalidade a atualizar: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Novo nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Nova descrição: ");
                        String descricao = sc.nextLine();
                        System.out.print("Nova idade mínima: ");
                        int idadeMinima = sc.nextInt();
                        System.out.print("Nova idade máxima: ");
                        int idadeMaxima = sc.nextInt();
                        servico.atualizarModalidade(new Modalidade(id, nome, descricao, idadeMinima, idadeMaxima));
                        System.out.println("Modalidade atualizada com sucesso!");
                    }
                    case 4 -> {
                        System.out.print("ID da modalidade a remover: ");
                        int id = sc.nextInt();
                        servico.removerModalidade(id);
                        System.out.println("Modalidade removida com sucesso!");
                    }
                    case 5 -> {
                        System.out.println("\nLista de Modalidades:");
                        for (Modalidade m : servico.listarTodasModalidades()) {
                            System.out.println(m);
                        }
                    }
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Opção inválida!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }

    private static void menuTurmas(Scanner sc, ITurmaService servico, IAlunoService alunoService, 
                                 IProfessorService professorService, IModalidadeService modalidadeService) {
        while (true) {
            System.out.println("\n===== MENU TURMAS =====");
            System.out.println("1. Criar Turma");
            System.out.println("2. Buscar Turma por ID");
            System.out.println("3. Atualizar Turma");
            System.out.println("4. Encerrar Turma");
            System.out.println("5. Listar Turmas");
            System.out.println("6. Matricular Aluno");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = sc.nextInt();
            sc.nextLine();

            try {
                switch (opcao) {
                    case 1 -> {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("ID do Professor: ");
                        int idProfessor = sc.nextInt();
                        sc.nextLine();
                        System.out.print("ID da Modalidade: ");
                        int idModalidade = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Dia da Semana (1-7): ");
                        int dia = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Horário (HH:mm): ");
                        String[] horario = sc.nextLine().split(":");
                        System.out.print("Capacidade Máxima: ");
                        int capacidadeMaxima = sc.nextInt();
                        sc.nextLine();
                        
                        Professor professor = professorService.buscarPorId(idProfessor);
                        Modalidade modalidade = modalidadeService.buscarModalidadePorId(idModalidade);
                        DayOfWeek diaSemana = DayOfWeek.of(dia);
                        LocalTime horarioAula = LocalTime.of(Integer.parseInt(horario[0]), Integer.parseInt(horario[1]));
                        
                        Turma turma = new Turma(id, modalidade, professor, diaSemana, horarioAula, capacidadeMaxima);
                        servico.criarTurma(turma);
                        System.out.println("Turma criada com sucesso!");
                    }
                    case 2 -> {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        Turma t = servico.buscarTurmaPorId(id);
                        System.out.println(t != null ? t : "Turma não encontrada.");
                    }
                    case 3 -> {
                        System.out.print("ID da turma a atualizar: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Novo ID do Professor: ");
                        int idProfessor = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Novo ID da Modalidade: ");
                        int idModalidade = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Novo dia da semana (1-7): ");
                        int dia = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Novo horário (HH:mm): ");
                        String[] horario = sc.nextLine().split(":");
                        System.out.print("Nova capacidade máxima: ");
                        int capacidadeMaxima = sc.nextInt();
                        sc.nextLine();
                        
                        Professor professor = professorService.buscarPorId(idProfessor);
                        Modalidade modalidade = modalidadeService.buscarModalidadePorId(idModalidade);
                        DayOfWeek diaSemana = DayOfWeek.of(dia);
                        LocalTime horarioAula = LocalTime.of(Integer.parseInt(horario[0]), Integer.parseInt(horario[1]));
                        
                        Turma turma = new Turma(id, modalidade, professor, diaSemana, horarioAula, capacidadeMaxima);
                        servico.atualizarTurma(turma);
                        System.out.println("Turma atualizada com sucesso!");
                    }
                    case 4 -> {
                        System.out.print("ID da turma a encerrar: ");
                        int id = sc.nextInt();
                        servico.encerrarTurma(id);
                        System.out.println("Turma encerrada com sucesso!");
                    }
                    case 5 -> {
                        System.out.println("\nLista de Turmas:");
                        for (Turma t : servico.listarTodasTurmas()) {
                            System.out.println(t);
                        }
                    }
                    case 6 -> {
                        System.out.print("ID da turma: ");
                        int idTurma = sc.nextInt();
                        sc.nextLine();
                        System.out.print("CPF do aluno: ");
                        String cpfAluno = sc.nextLine();
                        if (servico.matricularAluno(idTurma, cpfAluno)) {
                            System.out.println("Aluno matriculado com sucesso!");
                        } else {
                            System.out.println("Não foi possível matricular o aluno.");
                        }
                    }
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Opção inválida!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }
}
