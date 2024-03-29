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
public class ChessPiece implements Cloneable{

    ChessGame.TeamColor pieceColor;
    ChessPiece.PieceType type;

    @Override
    public Object clone() {
        try {
            return (ChessPiece) super.clone();
        } catch (CloneNotSupportedException e) {
            return new ChessPiece(this.getTeamColor(), this.getPieceType());
        }
    }

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

        // calculations for all moves possible for each piece type
        switch(this.type) {
            case BISHOP:
                findBishopMoves(board, myPosition, possibleMoves);
                break;
            case ROOK:
                findRookMoves(board, myPosition, possibleMoves);
                break;
            case KING:
                findKingMoves(board, myPosition, possibleMoves);
                break;
            case QUEEN:
                findQueenMoves(board, myPosition, possibleMoves);
                break;
            case KNIGHT:
                findKnightMoves(board, myPosition, possibleMoves);
                break;
            default:
                findPawnMoves(board, myPosition, possibleMoves);
        }

        return possibleMoves;
    }

    void findBishopMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
        int col = myPosition.getColumn();
        int row = myPosition.getRow();

        boolean upRightEndFound = false;
        boolean upLeftEndFound = false;
        boolean downRightEndFound = false;
        boolean downLeftEndFound = false;

        for ( int i = 1; i < 9; i++) { // all 4 directions in one loop
            int columnLeft = col - i;
            int columnRight = col + i;
            int rowUp = row + i;
            int rowDown = row - i;

            if (!upRightEndFound) {
                if (columnRight < 9 && rowUp < 9 ) {
                    ChessPosition upRightPosition = new ChessPosition(rowUp, columnRight);
                    upRightEndFound = checkNewSpace(board, myPosition, upRightPosition, possibleMoves);
                } else {
                    upRightEndFound = true;
                }

            }
            if (!upLeftEndFound) {
                if (columnLeft > 0 && rowUp < 9) {
                    ChessPosition upLeftPosition = new ChessPosition(rowUp, columnLeft);
                    upLeftEndFound = checkNewSpace(board, myPosition, upLeftPosition, possibleMoves);
                } else {
                    upLeftEndFound = true;
                }
            }
            if (!downRightEndFound) {
                if (columnRight < 9 && rowDown > 0) {
                    ChessPosition downRightPosition = new ChessPosition(rowDown, columnRight);
                    downRightEndFound = checkNewSpace(board, myPosition, downRightPosition, possibleMoves);
                } else {
                    downRightEndFound = true;
                }
            }
            if (!downLeftEndFound) {
                if (columnLeft > 0 && rowDown > 0) {
                    ChessPosition downLeftPosition = new ChessPosition(rowDown, columnLeft);
                    downLeftEndFound = checkNewSpace(board, myPosition, downLeftPosition, possibleMoves);
                } else {
                    downLeftEndFound = true;
                }
            }
            if (upRightEndFound && upLeftEndFound && downLeftEndFound && downRightEndFound) {
                break;
            }
        }
    }
    void findRookMoves (ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
        int col = myPosition.getColumn();
        int row = myPosition.getRow();

        boolean upEndFound = false;
        boolean downEndFound = false;
        boolean rightEndFound = false;
        boolean leftEndFound = false;

        for (int i = 1; i < 9; i++) { // all four directions in one loop
            int rowUp = row + i;
            int rowDown = row - i;
            int columnLeft = col - i;
            int columnRight = col + i;

            if (!upEndFound) {
                if (rowUp < 9) {
                    ChessPosition upPosition = new ChessPosition(rowUp, col);
                    upEndFound = checkNewSpace(board, myPosition, upPosition, possibleMoves);
                } else {
                    upEndFound = true;
                }
            }
            if (!downEndFound) {
                if (rowDown > 0) {
                    ChessPosition downPosition = new ChessPosition(rowDown, col);
                    downEndFound = checkNewSpace(board, myPosition, downPosition, possibleMoves);
                } else {
                    downEndFound = true;
                }
            }
            if (!leftEndFound) {
                if (columnLeft > 0) {
                    ChessPosition leftPosition = new ChessPosition(row, columnLeft);
                    leftEndFound = checkNewSpace(board, myPosition, leftPosition, possibleMoves);
                } else {
                    leftEndFound = true;
                }
            }
            if (!rightEndFound) {
                if (columnRight < 9) {
                    ChessPosition rightPosition = new ChessPosition(row, columnRight);
                    rightEndFound = checkNewSpace(board, myPosition, rightPosition, possibleMoves);
                } else {
                    rightEndFound = true;
                }
            }
            if (upEndFound && downEndFound && rightEndFound && leftEndFound) {
                break;
            }
        }
    }
    void findKingMoves (ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
        int col = myPosition.getColumn();
        int row = myPosition.getRow();

        int rowUp = row + 1;
        int rowDown = row - 1;
        int columnLeft = col - 1;
        int columnRight = col + 1;

        if (rowUp < 9) {
            ChessPosition upPosition = new ChessPosition(rowUp, col);
            boolean notUsed = checkNewSpace(board, myPosition, upPosition, possibleMoves);
        }
        if (rowUp < 9 && columnLeft > 0) {
            ChessPosition upLeftPosition = new ChessPosition(rowUp, columnLeft);
            boolean notUsed = checkNewSpace(board, myPosition, upLeftPosition, possibleMoves);
        }
        if (rowUp < 9 && columnRight < 9) {
            ChessPosition upRightPosition = new ChessPosition(rowUp, columnRight);
            boolean notUsed = checkNewSpace(board, myPosition, upRightPosition, possibleMoves);
        }
        if (columnLeft > 0) {
            ChessPosition leftPosition = new ChessPosition(row, columnLeft);
            boolean notUsed = checkNewSpace(board, myPosition, leftPosition, possibleMoves);
        }
        if (columnRight < 9) {
            ChessPosition rightPosition = new ChessPosition(row, columnRight);
            boolean notUsed = checkNewSpace(board, myPosition, rightPosition, possibleMoves);
        }
        if (rowDown > 0) {
            ChessPosition downPosition = new ChessPosition(rowDown, col);
            boolean notUsed = checkNewSpace(board, myPosition, downPosition, possibleMoves);
        }
        if (rowDown > 0 && columnLeft > 0) {
            ChessPosition downLeftPosition = new ChessPosition(rowDown, columnLeft);
            boolean notUsed = checkNewSpace(board, myPosition, downLeftPosition, possibleMoves);
        }
        if (rowDown > 0 && columnRight < 9) {
            ChessPosition downRightPosition = new ChessPosition(rowDown, columnRight);
            boolean notUsed = checkNewSpace(board, myPosition, downRightPosition, possibleMoves);
        }
    }
    void findQueenMoves (ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
        int col = myPosition.getColumn();
        int row = myPosition.getRow();

        // combination of both rook and bishop
        boolean upEndFound = false;
        boolean upLeftEndFound = false;
        boolean upRightEndFound = false;
        boolean downEndFound = false;
        boolean downLeftEndFound = false;
        boolean downRightEndFound = false;
        boolean leftEndFound = false;
        boolean rightEndFound = false;

        for ( int i = 1; i < 9; i++) { // all 4 directions in one loop
            int columnLeft = col - i;
            int columnRight = col + i;
            int rowUp = row + i;
            int rowDown = row - i;

            if (!upRightEndFound) {
                if (columnRight < 9 && rowUp < 9 ) {
                    ChessPosition upRightPosition = new ChessPosition(rowUp, columnRight);
                    upRightEndFound = checkNewSpace(board, myPosition, upRightPosition, possibleMoves);
                } else {
                    upRightEndFound = true;
                }

            }
            if (!upLeftEndFound) {
                if (columnLeft > 0 && rowUp < 9) {
                    ChessPosition upLeftPosition = new ChessPosition(rowUp, columnLeft);
                    upLeftEndFound = checkNewSpace(board, myPosition, upLeftPosition, possibleMoves);
                } else {
                    upLeftEndFound = true;
                }
            }
            if (!downRightEndFound) {
                if (columnRight < 9 && rowDown > 0) {
                    ChessPosition downRightPosition = new ChessPosition(rowDown, columnRight);
                    downRightEndFound = checkNewSpace(board, myPosition, downRightPosition, possibleMoves);
                } else {
                    downRightEndFound = true;
                }
            }
            if (!downLeftEndFound) {
                if (columnLeft > 0 && rowDown > 0) {
                    ChessPosition downLeftPosition = new ChessPosition(rowDown, columnLeft);
                    downLeftEndFound = checkNewSpace(board, myPosition, downLeftPosition, possibleMoves);
                } else {
                    downLeftEndFound = true;
                }
            }
            if (!upEndFound) {
                if (rowUp < 9) {
                    ChessPosition upPosition = new ChessPosition(rowUp, col);
                    upEndFound = checkNewSpace(board, myPosition, upPosition, possibleMoves);
                } else {
                    upEndFound = true;
                }
            }
            if (!downEndFound) {
                if (rowDown > 0) {
                    ChessPosition downPosition = new ChessPosition(rowDown, col);
                    downEndFound = checkNewSpace(board, myPosition, downPosition, possibleMoves);
                } else {
                    downEndFound = true;
                }
            }
            if (!leftEndFound) {
                if (columnLeft > 0) {
                    ChessPosition leftPosition = new ChessPosition(row, columnLeft);
                    leftEndFound = checkNewSpace(board, myPosition, leftPosition, possibleMoves);
                } else {
                    leftEndFound = true;
                }
            }
            if (!rightEndFound) {
                if (columnRight < 9) {
                    ChessPosition rightPosition = new ChessPosition(row, columnRight);
                    rightEndFound = checkNewSpace(board, myPosition, rightPosition, possibleMoves);
                } else {
                    rightEndFound = true;
                }
            }
            if (upEndFound && downEndFound && rightEndFound && leftEndFound && upLeftEndFound && upRightEndFound && downLeftEndFound && downRightEndFound) {
                break;
            }
        }
    }
    void findKnightMoves (ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
        int col = myPosition.getColumn();
        int row = myPosition.getRow();

        // L shape
        int twoDown = row - 2;
        int oneDown = row - 1;
        int twoUp = row + 2;
        int oneUp = row + 1;
        int twoLeft = col - 2;
        int oneLeft = col - 1;
        int twoRight = col + 2;
        int oneRight = col + 1;

        if (twoUp < 9 && oneLeft > 0) {
            ChessPosition newPosition = new ChessPosition(twoUp, oneLeft);
            boolean notUsed = checkNewSpace(board, myPosition, newPosition, possibleMoves);
        }
        if (twoUp < 9 && oneRight < 9) {
            ChessPosition newPosition = new ChessPosition(twoUp, oneRight);
            boolean notUsed = checkNewSpace(board, myPosition, newPosition, possibleMoves);
        }
        if (twoLeft > 0 && oneUp < 9) {
            ChessPosition newPosition = new ChessPosition(oneUp, twoLeft);
            boolean notUsed = checkNewSpace(board, myPosition, newPosition, possibleMoves);
        }
        if (twoLeft > 0 && oneDown > 0) {
            ChessPosition newPosition = new ChessPosition(oneDown, twoLeft);
            boolean notUsed = checkNewSpace(board, myPosition, newPosition, possibleMoves);
        }
        if (twoRight < 9 && oneUp < 9) {
            ChessPosition newPosition = new ChessPosition(oneUp, twoRight);
            boolean notUsed = checkNewSpace(board, myPosition, newPosition, possibleMoves);
        }
        if (twoRight < 9 && oneDown > 0) {
            ChessPosition newPosition = new ChessPosition(oneDown, twoRight);
            boolean notUsed = checkNewSpace(board, myPosition, newPosition, possibleMoves);
        }
        if (twoDown > 0 && oneLeft > 0) {
            ChessPosition newPosition = new ChessPosition(twoDown, oneLeft);
            boolean notUsed = checkNewSpace(board, myPosition, newPosition, possibleMoves);
        }
        if (twoDown > 0 && oneRight < 9) {
            ChessPosition newPosition = new ChessPosition(twoDown, oneRight);
            boolean notUsed = checkNewSpace(board, myPosition, newPosition, possibleMoves);
        }
    }
    void findPawnMoves (ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
        // one space towards opposite team, optional two if in starting position
        // diagonal forward if capturing piece
        int col = myPosition.getColumn();
        int row = myPosition.getRow();

        int oneUp = row + 1;
        int twoUp = row + 2;
        int oneDown = row - 1;
        int twoDown = row - 2;
        int oneLeft = col - 1;
        int oneRight = col + 1;

        boolean firstMove = false;
        boolean diagonal = false;

        // check if is in starting position
        if (this.pieceColor == ChessGame.TeamColor.BLACK) {
            boolean teamColor = false;
            // normal move
            if (oneDown > 0) {
                ChessPosition newPosition = new ChessPosition(oneDown, col);
                if (row == 7) {
                    firstMove = true;
                }
                checkNewSpacePawn(board, myPosition, newPosition, possibleMoves, firstMove, teamColor, diagonal);

                // diagonal capture left
                if (oneLeft > 0) {
                    diagonal = true;
                    ChessPosition diagonalLeft = new ChessPosition(oneDown, oneLeft);
                    checkNewSpacePawn(board, myPosition, diagonalLeft, possibleMoves, firstMove, teamColor, diagonal);
                }
                if (oneRight < 9) {
                    diagonal = true;
                    ChessPosition diagonalRight = new ChessPosition(oneDown, oneRight);
                    checkNewSpacePawn(board, myPosition, diagonalRight, possibleMoves, firstMove, teamColor, diagonal);
                }
            }

        } else {
            boolean teamColor = true;
            // normal move
            if (oneUp < 9) {
                ChessPosition newPosition = new ChessPosition(oneUp, col);
                // include optional 2 square move
                if (row == 2) {
                    firstMove = true;
                }
                checkNewSpacePawn(board, myPosition, newPosition, possibleMoves, firstMove, teamColor, diagonal);

                // diagonal capture
                if (oneLeft > 0) {
                    diagonal = true;
                    ChessPosition diagonalLeft = new ChessPosition(oneUp, oneLeft);
                    checkNewSpacePawn(board, myPosition, diagonalLeft, possibleMoves, firstMove, teamColor, diagonal);
                }
                if (oneRight < 9) {
                    diagonal = true;
                    ChessPosition diagonalRight = new ChessPosition(oneUp, oneRight);
                    checkNewSpacePawn(board, myPosition, diagonalRight, possibleMoves, firstMove, teamColor, diagonal);
                }
            }

        }
    }


    boolean checkNewSpace(ChessBoard board, ChessPosition myPosition, ChessPosition newPosition, Collection<ChessMove> possibleMoves) {
        if (board.getPiece(newPosition) != null) {
            ChessPiece foundPiece = board.getPiece(newPosition);
            if (foundPiece.getTeamColor() != this.pieceColor) {
                possibleMoves.add(new ChessMove(myPosition, newPosition, null));
            }
            return true;
        }

        possibleMoves.add(new ChessMove(myPosition, newPosition, null)); // empty space on board found
        return false;
    }

    void checkNewSpacePawn(ChessBoard board, ChessPosition myPosition, ChessPosition newPosition,
                           Collection<ChessMove> possibleMoves, boolean firstMove, boolean teamColor, boolean diagonal) {

        if (board.getPiece(newPosition) == null) {
            if (diagonal) {
                return;
            }
            // check if promoting
            if (newPosition.getRow() == 1 || newPosition.getRow() == 8) {
                possibleMoves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                possibleMoves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                possibleMoves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                possibleMoves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
            } else {
                possibleMoves.add(new ChessMove(myPosition, newPosition, null));
            }

            if (firstMove) { // optional double move
                if (!teamColor) {
                    int twoDown = myPosition.getRow() - 2;
                    int col = myPosition.getColumn();
                    ChessPosition doubleMove = new ChessPosition(twoDown, col);
                    if (board.getPiece(doubleMove) == null) {
                        possibleMoves.add(new ChessMove(myPosition, doubleMove, null));
                    }
                } else {
                    int twoUp = myPosition.getRow() + 2;
                    int col = myPosition.getColumn();
                    ChessPosition doubleMove = new ChessPosition(twoUp, col);
                    if (board.getPiece(doubleMove) == null) {
                        possibleMoves.add(new ChessMove(myPosition, doubleMove, null));
                    }
                }
            }
        }
        // diagonal capture
        if (diagonal) {
            ChessPiece foundPiece = board.getPiece(newPosition);
            if (foundPiece.getTeamColor() != this.pieceColor) {
                // check if promoting
                if (newPosition.getRow() == 1 || newPosition.getRow() == 8) {
                    possibleMoves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                    possibleMoves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                    possibleMoves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                    possibleMoves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                } else {
                    possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                }

            }
        }
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
