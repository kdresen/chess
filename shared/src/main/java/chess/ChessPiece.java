package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    ChessGame.TeamColor pieceColor;
    ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> possibleMoves = new HashSet<>();
        int col = myPosition.getColumn();
        int row = myPosition.getRow();


        // calculations for all moves possible for each piece type
        if (this.type == PieceType.BISHOP) { // diagonal movements until edge or another piece is found
            for (int i = 1; i < 9; i++) { // up and left
                int columnLeft = col - i;
                int rowUp = row + i;
                if (columnLeft > 0 && rowUp < 9) {
                    ChessPosition newPosition = new ChessPosition(rowUp, columnLeft);
                    if (board.getPiece(newPosition) != null) {
                        ChessPiece foundPiece = board.getPiece(newPosition);
                        if (foundPiece.getTeamColor() != this.pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                        }
                        break; // edge or piece was found
                    }

                    possibleMoves.add(new ChessMove(myPosition, newPosition, null)); // empty space on board found
                } else {
                    break;
                }
            }
            for (int i = 1; i < 9; i++) { // up and right
                int columnRight = col + i;
                int rowUp = row + i;
                if (columnRight < 9 && rowUp < 9) {
                    ChessPosition newPosition = new ChessPosition(rowUp, columnRight);
                    if (board.getPiece(newPosition) != null) {
                        ChessPiece foundPiece = board.getPiece(newPosition);
                        if (foundPiece.getTeamColor() != this.pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                        }
                        break;
                    }

                    possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                } else {
                    break;
                }

            }
            for (int i = 1; i < 9; i++) { // down and left
                int columnLeft = col - i;
                int rowDown = row - i;
                if (columnLeft > 0 && rowDown > 0) {
                    ChessPosition newPosition = new ChessPosition(rowDown, columnLeft);
                    if (board.getPiece(newPosition) != null) {
                        ChessPiece foundPiece = board.getPiece(newPosition);
                        if (foundPiece.getTeamColor() != this.pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                        }
                        break;
                    }

                    possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                } else {
                    break;
                }
            }
            for (int i = 1; i < 9; i++) { // down and right
                int columnRight = col + i;
                int rowDown = row - i;
                if (columnRight > 0 && rowDown > 0) {
                    ChessPosition newPosition = new ChessPosition(rowDown, columnRight);
                    if (board.getPiece(newPosition) != null) {
                        ChessPiece foundPiece = board.getPiece(newPosition);
                        if (foundPiece.getTeamColor() != this.pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                        }
                        break;
                    }
                    possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                } else {
                    break;
                }
            }
        }
        else if (this.type == PieceType.ROOK) { // x and y axis until edge or piece found
            for (int i = 1; i < 9; i++) { // straight up
                int rowUp = row + i;
                if (rowUp < 9) {
                    ChessPosition newPosition = new ChessPosition(rowUp, col);
                    if (board.getPiece(newPosition) != null) {
                        ChessPiece foundPiece = board.getPiece(newPosition);
                        if (foundPiece.getTeamColor() != this.pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                        }
                        break;
                    }
                    possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                } else {
                    break;
                }
            }
            for (int i = 1; i < 9; i++) { // straight down
                int rowDown = row - i;
                if (rowDown > 0) {
                    ChessPosition newPosition = new ChessPosition(rowDown, col);
                    if (board.getPiece(newPosition) != null) {
                        ChessPiece foundPiece = board.getPiece(newPosition);
                        if (foundPiece.getTeamColor() != this.pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                        }
                        break;
                    }
                    possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                } else {
                    break;
                }
            }
            for (int i = 1; i < 9; i++) { // straight left
                int columnLeft = col - i;
                if (columnLeft > 0) {
                    ChessPosition newPosition = new ChessPosition(row, columnLeft);
                    if (board.getPiece(newPosition) != null) {
                        ChessPiece foundPiece = board.getPiece(newPosition);
                        if (foundPiece.getTeamColor() != this.pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                        }
                        break;
                    }
                    possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                } else {
                    break;
                }
            }
            for (int i = 1; i < 9; i++) { // straight right
                int columnRight = col + i;
                if (columnRight < 9) {
                    ChessPosition newPosition = new ChessPosition(row, columnRight);
                    if (board.getPiece(newPosition) != null) {
                        ChessPiece foundPiece = board.getPiece(newPosition);
                        if (foundPiece.getTeamColor() != this.pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                        }
                        break;
                    }
                    possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                } else {
                    break;
                }
            }
        }
        else if (this.type == PieceType.KING) { // one tile in each direction
            int rowUp = row + 1;
            int rowDown = row - 1;
            int columnLeft = col - 1;
            int columnRight = col + 1;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if ( i == 1 && j == 1 ) {
                    } else {
                        int rowChange = i - 1;
                        int colChange = j - 1;
                        int currentRow = row + rowChange;
                        int currentCol = col + colChange;
                        if (currentRow > 0 && currentRow < 9 && currentCol > 0 && currentCol < 9) {
                            ChessPosition newPosition = new ChessPosition(currentRow, currentCol);
                            if (board.getPiece(newPosition) != null) {
                                ChessPiece foundPiece = board.getPiece(newPosition);
                                if (foundPiece.getTeamColor() != this.pieceColor) {
                                    possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                                }
                            } else {
                                possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                            }
                        }
                    }
                }
            }
        }
        else if (this.type == PieceType.QUEEN) {throw new RuntimeException("Not implemented");}
        else if (this.type == PieceType.KNIGHT) {throw new RuntimeException("Not implemented");}
        else {throw new RuntimeException("Not implemented");}

        return possibleMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}
