package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {
    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static ChessPosition readChessPosition(Scanner scan) {
        try {
            String s = scan.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);

        } catch (RuntimeException e) {

            throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
        }
    }

    public static void printBoard(ChessPiece[][] pieces) {

        for (int r = 0; r < pieces.length; r += 2) {

            System.out.print(ColorPalette.CYAN_BACKGROUND.label + " " + (8 - r) + " " + ColorPalette.RESET.label);

            for (int c = 0; c < pieces.length; c++) {
                if((c+1) % 2 == 0) {
                    System.out.print(ColorPalette.BLACK_BACKGROUND_BRIGHT.label);
                }
                printPiece(pieces[r][c], false);
            }

            System.out.println();

            System.out.print(ColorPalette.BLUE_BACKGROUND.label + " " + (8 - r - 1) + " " + ColorPalette.RESET.label);

            for (int c = 0; c < pieces.length; c++) {
                if(c % 2 == 0) {
                    System.out.print(ColorPalette.BLACK_BACKGROUND_BRIGHT.label);
                }
                printPiece(pieces[r + 1][c], false);
            }

            System.out.println();
        }

        // System.out.println(" a b c d e f g h");

        generateColumnsId();
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {

        printBoard(chessMatch.getPieces());
        printCapturedPieces(captured);
        System.out.println();
        System.out.println("Turn: " + chessMatch.getTurn());

        if (!chessMatch.isCheckMate()) {

            System.out.println("Waiting player " + chessMatch.getCurrentPlayer());

            if (chessMatch.isCheck()) {
                System.out.println("CHECK!!!");
            }

        } else {
            System.out.println("CHECKMATE!!!");
            System.out.println("Winner: " + chessMatch.getCurrentPlayer());
        }
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {

        for (int r = 0; r < pieces.length; r += 2) {

            System.out.print(ColorPalette.CYAN_BACKGROUND.label + " " + (8 - r) + " " + ColorPalette.RESET.label);

            for (int c = 0; c < pieces.length; c++) {
                if((c+1) % 2 == 0) {
                    System.out.print(ColorPalette.BLACK_BACKGROUND_BRIGHT.label);
                }
                printPiece(pieces[r][c], possibleMoves[r][c]);
            }

            System.out.println();

            System.out.print(ColorPalette.BLUE_BACKGROUND.label + " " + (8 - r - 1) + " " + ColorPalette.RESET.label);

            for (int c = 0; c < pieces.length; c++) {
                if(c % 2 == 0) {
                    System.out.print(ColorPalette.BLACK_BACKGROUND_BRIGHT.label);
                }
                printPiece(pieces[r + 1][c], possibleMoves[r + 1][c]);
            }

            System.out.println();
        }

        // System.out.println(" a b c d e f g h");
        generateColumnsId();
    }

    private static void printPiece(ChessPiece piece, boolean background) {

        if (background) {
            System.out.print(ColorPalette.RED_BACKGROUND.label);
        }
        if (piece == null) {
            System.out.print(" - " + ColorPalette.RESET.label);

        } else {
            if (piece.getColor() == Color.WHITE) {

                System.out.print(ColorPalette.WHITE.label + " " + piece + " "
                        + ColorPalette.RESET.label);
            } else {

                System.out.print(ColorPalette.YELLOW.label + " " + piece
                        + " " + ColorPalette.RESET.label);
            }
        }

        // System.out.print(" ");
    }

    private static void printCapturedPieces(List<ChessPiece> captured) {
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE)
                .collect(Collectors.toList());
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK)
                .collect(Collectors.toList());

        System.out.println();
        System.out.println("Captured pieces:");

        System.out.print("White: ");
        System.out.print(ColorPalette.WHITE.label);
        System.out.println(Arrays.toString(white.toArray()));
        System.out.print(ColorPalette.RESET.label);

        System.out.print("Black: ");
        System.out.print(ColorPalette.YELLOW.label);
        System.out.println(Arrays.toString(black.toArray()));
        System.out.print(ColorPalette.RESET.label);
    }

    private static void generateColumnsId() {
        String[] col = "a b c d e f g h".split(" ");

        System.out.print("   ");
        for (int i = 0; i < col.length; i += 2) {
            System.out.print(ColorPalette.CYAN_BACKGROUND.label + " " + col[i] + " " + ColorPalette.RESET.label);
            System.out.print(ColorPalette.BLUE_BACKGROUND.label + " " + col[i + 1] + " " + ColorPalette.RESET.label);
        }
        System.out.println();
    }
}
