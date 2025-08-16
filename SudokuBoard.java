public class SudokuBoard {
    private int[][] board;
    private int[][] solution;
    private boolean[][] fixed; 
    
    public SudokuBoard() {
        board = new int[9][9];
        solution = new int[9][9];
        fixed = new boolean[9][9];
    }
    
    
    public void display() {
        System.out.println("\n  1 2 3   4 5 6   7 8 9");
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("  ------+-------+------");
            }
            System.out.print((i + 1) + " ");
            
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }
                
                if (board[i][j] == 0) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    
    public boolean placeNumber(int row, int col, int num) {
        if (fixed[row][col]) {
            System.out.println("Esta célula não pode ser alterada!");
            return false;
        }
        
        if (num < 0 || num > 9) {
            System.out.println("Número deve estar entre 0-9 (0 para apagar)");
            return false;
        }
        
        board[row][col] = num;
        return true;
    }
    
    public boolean isValidMove(int row, int col, int num) {
        if (num == 0) return true; 
        
        for (int j = 0; j < 9; j++) {
            if (j != col && board[row][j] == num) {
                return false;
            }
        }
        
        for (int i = 0; i < 9; i++) {
            if (i != row && board[i][col] == num) {
                return false;
            }
        }
        
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if ((i != row || j != col) && board[i][j] == num) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public boolean isComplete() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int[][] getBoard() { return board; }
    public void setBoard(int[][] board) { this.board = board; }
    public boolean[][] getFixed() { return fixed; }
    public void setFixed(boolean[][] fixed) { this.fixed = fixed; }
    public int[][] getSolution() { return solution; }
    public void setSolution(int[][] solution) { this.solution = solution; }
}