import java.util.Scanner;
import java.io.IOException;
import java.io.Console;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

public class TicTacToe {
    public static void main(String[] args) {
        String[][] board = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
        try {
            DrawBoard(board);
            String player1Symbol = "X";
            String player2Symbol = "O";
            String playerSymbol = player1Symbol;
            while (FindWinner(board).equals("NONE")) {
                int[] placement = GetPlacement(board);
                board[placement[0]][placement[1]] = playerSymbol;
                DrawBoard(board);
                if (playerSymbol.equals(player1Symbol)) {
                    playerSymbol = player2Symbol;
                } else {
                    playerSymbol = player1Symbol;
                }
            }
            System.out.print("\nWINNER: " + FindWinner(board));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void DrawBoard(String[][] board) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.print("\n");
        for (int i = 0; i < 3; i++) {
            for (int y = 0; y < 3; y++) {
                if (y > 0) { System.out.print(" | "); }
                System.out.print(board[i][y]);
            }
            if (i < 2) { System.out.print("\n--|---|--\n"); }
        }
    }

    private static String FindWinner(String[][] board) {
        // Horizontal
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && board[i][0] != " ") {
                return board[i][0];
            }
        }

        // Vertical
        for (int i = 0; i < 3; i++) {
            if (board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) && board[0][i] != " ") {
                return board[0][i];
            }
        }

        // Diagonal
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && board[0][0] != " ") {
            return board[0][0];
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && board[0][2] != " ") {
            return board[0][2];
        }
        return "NONE";
    }

    private static int[] GetPlacement(String[][] board) throws Exception {
        System.out.print("\nUse WASD keys to change your move");
        String input = "NULL";
        int x = 0;
        int y = 0;
        String[][] b = new String[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(board[i], 0, b[i], 0, 3);
        }
        while (!input.equals("QUIT_GAME")) {
            input = readWASD();
            for (int i = 0; i < 3; i++) {
                System.arraycopy(board[i], 0, b[i], 0, 3);
            }
            switch (input) {
                case "UP": x--;break;
                case "DOWN": x++;break;
                case "LEFT": y--;break;
                case "RIGHT": y++;break;
            }
            x = Math.max(0, Math.min(2, x));
            y = Math.max(0, Math.min(2, y));
            if (!b[x][y].equals(" ")) {
                b[x][y] = "\033[31m#\033[0m";
            } else {
                b[x][y] = "\033[44m \033[0m";
            }
            DrawBoard(b);
            if (!board[x][y].equals(" ")) {
                System.out.print("\nPlease Play Somewhere Else!");
                input = "NULL";
                continue;
            }
            if (input.equals("RETURN")) {
                int[] placement = {x, y};
                return placement;
            }
        }
        int[] placement = {x, y};
        return placement;
    }

    public static String readWASD() throws Exception {
        Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build();
        terminal.enterRawMode();
        NonBlockingReader reader = terminal.reader();

        while (true) {
            int ch = reader.read(100);
            if (ch == -2) continue;

            switch (ch) {
                case 'w': case 'W': reader.close(); terminal.close(); return "UP";
                case 's': case 'S': reader.close(); terminal.close(); return "DOWN";
                case 'a': case 'A': reader.close(); terminal.close(); return "LEFT";
                case 'd': case 'D': reader.close(); terminal.close(); return "RIGHT";
                case 10: case 13: reader.close(); terminal.close(); return "RETURN";
            }
        }
    }
}