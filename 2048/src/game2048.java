
import java.util.Arrays;
import java.util.Scanner;
import java.lang.Math;

public class game2048 {

    private static Scanner scan = new Scanner(System.in);
    private static int[][] board;
    private static int vSize = 4;
    private static int hSize = 4;

    public static void main(String[] args) {
        int moves = 0;
        int tileNumber = 1;
        System.out.println("Do you want custom settings?");
        String custom  = scan.nextLine();
        System.out.println();
        if (custom.toLowerCase().equals("yes")) {
            System.out.println("Enter board vertical length");
            vSize = Integer.parseInt(scan.nextLine());
            System.out.println();
            System.out.println("Enter board horizontal length");
            hSize = Integer.parseInt(scan.nextLine());
            System.out.println();
            System.out.println("Enter amount of tiles generated per move");
            tileNumber = Integer.parseInt(scan.nextLine());
            System.out.println();
        }

        board = new int[hSize][vSize];
        boolean canMoveDirection = true;

        while (emptySpace()) {
            if (canMoveDirection) generateTile(tileNumber);
            for (int i = 0; i < vSize; i++) {
                for (int j = 0; j < hSize; j++) {
                    if (board[j][i] == 0) System.out.print("-\t");
                    else System.out.print(board[j][i] + "\t");
                }
                System.out.println();
            }
            System.out.println();
            canMoveDirection = turn();
            moves++;
        }
        System.out.print("You lost, you made " + moves + " moves.");
    }

    private static boolean turn() {
        int direction = 0;
        System.out.println("Enter direction");
        switch(scan.nextLine()) {
            case "w":
                break;

            case "s":
                direction = 1;
                break;

            case "a":
                direction = 2;
                break;

            case "d":
                direction = 3;
                break;

            default:
                System.out.println("Enter w, s, a, or d for directions");
                turn();
                return false;
        }

        boolean canMoveDirection = false;
        for (int i = 0; i < vSize; i++) {
            for (int j = 0; j < hSize; j++) {
                int[] posCanMove = canMove(new int[] {i, j}, direction);
                if (!Arrays.equals(posCanMove , new int[] {-1, -1}) && !Arrays.equals(posCanMove , new int[] {-2, -2})) canMoveDirection = true;
                switch(direction) {
                    case 0:
                        if (i > 0 && board[j][i] != 0 && board[j][i] == board[j][i - 1]) {
                            canMoveDirection = true;
                        }
                        break;

                    case 1:
                        if (i < vSize - 1 && board[j][i] != 0 && board[j][i] == board[j][i + 1]) {
                            canMoveDirection = true;
                        }
                        break;

                    case 2:
                        if (j > 0 && board[j][i] != 0 && board[j][i] == board[j - 1][i]) {
                            canMoveDirection = true;
                        }
                        break;

                    case 3:
                        if (j < hSize - 1 && board[j][i] != 0 && board[j][i] == board[j + 1][i]) {
                            canMoveDirection = true;
                        }
                        break;
                }
            }
        }

        moveBoard(direction);
        moveBoard(direction);

        switch(direction) {
            case 0:
                for (int i = 1; i < vSize; i++) {
                    for (int j = 0; j < hSize; j++) {
                        if (board[j][i] != 0 && board[j][i] == board[j][i - 1]) {
                            board[j][i - 1] *= 2;
                            board[j][i] = 0;
                        }
                    }
                }
                break;

            case 1:
                for (int i = vSize - 2; i >= 0; i--) {
                    for (int j = 0; j < hSize; j++) {
                        if (board[j][i] != 0 && board[j][i] == board[j][i + 1]) {
                            board[j][i + 1] *= 2;
                            board[j][i] = 0;
                        }
                    }
                }
                break;

            case 2:
                for (int i = 0; i < vSize; i++) {
                    for (int j = 1; j < hSize; j++) {
                        if (board[j][i] != 0 && board[j][i] == board[j - 1][i]) {
                            board[j - 1][i] *= 2;
                            board[j][i] = 0;
                        }
                    }
                }
                break;

            case 3:
                for (int i = 0; i < vSize; i++) {
                    for (int j = hSize - 2; j >= 0; j--) {
                        if (board[j][i] != 0 && board[j][i] == board[j + 1][i]) {
                            board[j + 1][i] *= 2;
                            board[j][i] = 0;
                        }
                    }
                }
                break;
        }

        moveBoard(direction);
        moveBoard(direction);

        /*
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[j][i] == 0) System.out.print("-\t");
                else System.out.print(board[j][i] + "\t");
            }
            System.out.println();
        }
        System.out.println();
        */

        return canMoveDirection;
    }

    private static void moveBoard(int direction) {
        int[] posToMove;
        for(int i = 0; i < vSize; i++) {
            for (int j = 0; j < hSize; j++) {
                posToMove = canMove(new int[] {i, j}, direction);

                //System.out.println(Arrays.toString(posToMove));

                if (!Arrays.equals(posToMove, new int[]{-1, -1}) && !Arrays.equals(posToMove, new int[]{-2, -2})) {
                     board[posToMove[0]][posToMove[1]] = board[j][i];
                     board[j][i] = 0;
                }
            }
        }
    }

    private static void generateTile(int tileNumber) {
        for (int i = 0; i < tileNumber; i++) {
            if(emptySpace()) {
                int row = (int)(Math.random() * vSize);
                int column = (int)(Math.random() * hSize);
                while (board[column][row] != 0) {
                    row = (int)(Math.random() * vSize);
                    column = (int)(Math.random() * hSize);
                }
                if ((int)(Math.random() * 10) == 0) board[column][row] = 4;
                else board[column][row] = 2;
            }
            else break;
        }
    }

    private static int[] canMove(int[] pos, int direction) {
        int spaces = 1;
        switch (direction) {
            case 0:
                if (board[pos[1]][pos[0]] == 0) return new int[] {-2, -2};
                else if(pos[0] - 1 >= 0 && board[pos[1]][pos[0] - 1] == 0) {
                    for (int i = 2; i < vSize; i++) {
                        if (pos[0] - i >= 0 && board[pos[1]][pos[0] - i] == 0) spaces++;
                    }
                    return new int[] {pos[1], pos[0] - spaces};
                }
                else return new int[] {-1,-1};

            case 1:
                if (board[pos[1]][pos[0]] == 0) return new int[] {-2, -2};
                else if(pos[0] + 1 < vSize && board[pos[1]][pos[0] + 1] == 0) {
                    for (int i = 2; i < vSize; i++) {
                        if (pos[0] + i < vSize && board[pos[1]][pos[0] + i] == 0) spaces++;
                    }
                    return new int[] {pos[1], pos[0] + spaces};
                }
                else return new int[] {-1,-1};

            case 2:
                if (board[pos[1]][pos[0]] == 0) return new int[] {-2, -2};
                else if(pos[1] - 1 >= 0 && board[pos[1] - 1][pos[0]] == 0) {
                    for (int i = 2; i < hSize; i++) {
                        if (pos[1] - i >= 0 && board[pos[1] - i][pos[0]] == 0) spaces++;
                    }
                    return new int[] {pos[1] - spaces, pos[0]};
                }
                else return new int[] {-1,-1};

            case 3:
                if (board[pos[1]][pos[0]] == 0) return new int[] {-2, -2};
                else if(pos[1] + 1 < hSize && board[pos[1] + 1][pos[0]] == 0) {
                    for (int i = 2; i < hSize; i++) {
                        if (pos[1] + i < hSize && board[pos[1] + i][pos[0]] == 0) spaces++;
                    }
                    return new int[] {pos[1] + spaces, pos[0]};
                }
                else return new int[] {-1,-1};

            default:
                return new int[] {-1, -1};
        }
    }

    private static boolean emptySpace() {
        boolean emptySpace = false;
        for (int i = 0; i < vSize; i++) {
            for(int j = 0; j < hSize; j++) {
                for(int k = 0; k < 4; k++) {
                    if (!Arrays.equals(canMove(new int[] {i, j}, k), new int[] {-1, -1})) emptySpace = true;
                }
            }
        }
        return emptySpace;
    }

}