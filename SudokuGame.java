import java.util.Scanner;

public class SudokuGame {
    private SudokuBoard board;
    private SudokuGenerator generator;
    private Scanner scanner;
    
    public SudokuGame() {
        board = new SudokuBoard();
        generator = new SudokuGenerator();
        scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("Bem-vindo ao Sudoku!");
        
        while (true) {
            showMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    startNewGame();
                    break;
                case 2:
                    showRules();
                    break;
                case 3:
                    System.out.println("Obrigado por jogar!");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    
    private void showMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Novo Jogo");
        System.out.println("2. Como Jogar");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void startNewGame() {
        System.out.println("\nEscolha a dificuldade:");
        System.out.println("1. Fácil");
        System.out.println("2. Médio");
        System.out.println("3. Difícil");
        System.out.print("Dificuldade: ");
        
        int difficulty = getChoice();
        if (difficulty < 1 || difficulty > 3) {
            difficulty = 2; // Padrão médio
        }
        
        System.out.println("Gerando puzzle...");
        generator.generatePuzzle(board, difficulty);
        
        playGame();
    }
    
    private void playGame() {
        while (true) {
            board.display();
            
            if (board.isComplete()) {
                System.out.println("🎉 PARABÉNS! Você completou o Sudoku! 🎉");
                break;
            }
            
            System.out.println("Comandos:");
            System.out.println("- Digite 'linha coluna número' (ex: 1 1 5)");
            System.out.println("- Digite 'dica' para ver a solução de uma célula");
            System.out.println("- Digite 'resolver' para ver a solução completa");
            System.out.println("- Digite 'menu' para voltar ao menu");
            System.out.print("Seu movimento: ");
            
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equals("menu")) {
                break;
            } else if (input.equals("dica")) {
                giveHint();
            } else if (input.equals("resolver")) {
                showSolution();
                break;
            } else {
                processMove(input);
            }
        }
    }
    
    private void processMove(String input) {
        String[] parts = input.split(" ");
        
        if (parts.length != 3) {
            System.out.println("Formato inválido! Use: linha coluna número");
            return;
        }
        
        try {
            int row = Integer.parseInt(parts[0]) - 1;
            int col = Integer.parseInt(parts[1]) - 1;
            int num = Integer.parseInt(parts[2]);
            
            if (row < 0 || row > 8 || col < 0 || col > 8) {
                System.out.println("Posição inválida! Use números de 1 a 9.");
                return;
            }
            
            if (!board.isValidMove(row, col, num)) {
                System.out.println("Movimento inválido! Este número já existe na linha, coluna ou quadrante.");
                return;
            }
            
            if (board.placeNumber(row, col, num)) {
                System.out.println("Movimento realizado!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite apenas números!");
        }
    }
    
    private void giveHint() {
        System.out.print("Digite a posição para a dica (linha coluna): ");
        String input = scanner.nextLine().trim();
        String[] parts = input.split(" ");
        
        if (parts.length != 2) {
            System.out.println("Formato inválido!");
            return;
        }
        
        try {
            int row = Integer.parseInt(parts[0]) - 1;
            int col = Integer.parseInt(parts[1]) - 1;
            
            if (row < 0 || row > 8 || col < 0 || col > 8) {
                System.out.println("Posição inválida!");
                return;
            }
            
            int solution = board.getSolution()[row][col];
            System.out.println("Dica: A resposta para (" + (row+1) + "," + (col+1) + ") é " + solution);
            
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite apenas números!");
        }
    }
    
    private void showSolution() {
        System.out.println("\n=== SOLUÇÃO COMPLETA ===");
        int[][] solution = board.getSolution();
        
        System.out.println("  1 2 3   4 5 6   7 8 9");
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("  ------+-------+------");
            }
            System.out.print((i + 1) + " ");
            
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }
                System.out.print(solution[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    private void showRules() {
        System.out.println("\n=== COMO JOGAR SUDOKU ===");
        System.out.println("1. Preencha o tabuleiro 9x9 com números de 1 a 9");
        System.out.println("2. Cada linha deve conter todos os números de 1 a 9");
        System.out.println("3. Cada coluna deve conter todos os números de 1 a 9");
        System.out.println("4. Cada quadrante 3x3 deve conter todos os números de 1 a 9");
        System.out.println("5. Use 0 para apagar um número");
        System.out.println("\nBoa sorte! 🍀");
    }
}