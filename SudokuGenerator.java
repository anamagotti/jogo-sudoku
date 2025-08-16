import java.util.*;

public class SudokuGenerator {
    private Random random;
    
    public SudokuGenerator() {
        random = new Random();
    }
    
    
    public void generatePuzzle(SudokuBoard sudokuBoard, int difficulty) {
        int[][] board = sudokuBoard.getBoard();
        boolean[][] fixed = sudokuBoard.getFixed();
        
       
        clearBoard(board);
        clearFixed(fixed);
        
        
        generateCompleteSolution(board);
        
        
        int[][] solution = new int[9][9];
        copyBoard(board, solution);
        sudokuBoard.setSolution(solution);
        
        
        int cellsToRemove = getCellsToRemove(difficulty);
        removeCells(board, fixed, cellsToRemove);
    }
    
    private void generateCompleteSolution(int[][] board) {
       
        fillDiagonal(board);
        
        SudokuSolver.solve(board);
    }
    
    private void fillDiagonal(int[][] board) {
        for (int i = 0; i < 9; i += 3) {
            fillBox(board, i, i);
        }
    }
    
    private void fillBox(int[][] board, int row, int col) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(numbers, random);
        
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[row + i][col + j] = numbers.get(index++);
            }
        }
    }
    
    private void removeCells(int[][] board, boolean[][] fixed, int count) {
        List<int[]> positions = new ArrayList<>();
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                positions.add(new int[]{i, j});
            }
        }
        
        Collections.shuffle(positions, random);
        
        for (int i = 0; i < count && i < positions.size(); i++) {
            int[] pos = positions.get(i);
            board[pos[0]][pos[1]] = 0;
        }
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fixed[i][j] = (board[i][j] != 0);
            }
        }
    }
    
    private int getCellsToRemove(int difficulty) {
        switch (difficulty) {
            case 1: return 35; 
            case 2: return 45; 
            case 3: return 55; 
            default: return 40;
        }
    }
    
    private void clearBoard(int[][] board) {
        for (int i = 0; i < 9; i++) {
            Arrays.fill(board[i], 0);
        }
    }
    
    private void clearFixed(boolean[][] fixed) {
        for (int i = 0; i < 9; i++) {
            Arrays.fill(fixed[i], false);
        }
    }
    
    private void copyBoard(int[][] source, int[][] destination) {
        for (int i = 0; i < 9; i++) {
            System.arraycopy(source[i], 0, destination[i], 0, 9);
        }
    }
}