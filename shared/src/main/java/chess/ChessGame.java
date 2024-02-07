package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor teamTurn;
    private ChessBoard currentBoard;

    private ChessBoard clonedBoard;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;
        currentBoard = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();

        // starting position
        ChessPiece piece = currentBoard.getPiece(startPosition);
        // get possible moves for piece
        Collection<ChessMove> possibleChessMoves = piece.pieceMoves(currentBoard, startPosition);

        // test all moves to see if king is put into check
        // if move results in friendly king in check, don't add to validmoves
        for (ChessMove move : possibleChessMoves) {
            // make clone of current board to make moves on
            clonedBoard = (ChessBoard) currentBoard.clone();

            // make move
            ChessPosition endPos = move.getEndPosition();
            ChessPiece.PieceType promotionPiece = move.getPromotionPiece();

            // assume since the move is in possibleChessMoves, it won't go out of bounds
            int row = startPosition.getRow() - 1;
            int col = startPosition.getColumn() - 1;
            int endRow = endPos.getRow() - 1;
            int endCol = endPos.getColumn() - 1;

            // promote piece if applicable
            if (promotionPiece != null) {
                piece.type = promotionPiece;
            }
            // move piece to new position
            clonedBoard.boardPieces[endRow][endCol] = piece;
            clonedBoard.boardPieces[row][col] = null;

            // check if king is in check
            boolean invalidMove = isInCheck(piece.getTeamColor());
            // if not in check, move is valid
            if (!invalidMove) {
                validMoves.add(move);
            }

            clonedBoard = (ChessBoard) currentBoard.clone();
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // find piece in starting position
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece.PieceType promotionPiece = move.getPromotionPiece();
        TeamColor currentTeam = getTeamTurn();

        ChessPiece movingPiece = currentBoard.getPiece(startPosition);
        TeamColor pieceTeam = movingPiece.getTeamColor();
        // check if move is valid

        if (teamTurn == pieceTeam) {
            Collection<ChessMove> validMoves = validMoves(startPosition);
            boolean isValidMove = false;
            for (ChessMove checkMove : validMoves) {
                if (checkMove.equals(move)) {
                    isValidMove = true;
                    break;
                }
            }

            // move piece
            if (isValidMove) {
                // assume since the move is in possibleChessMoves, it won't go out of bounds
                int row = startPosition.getRow() - 1;
                int col = startPosition.getColumn() - 1;
                int endRow = endPosition.getRow() - 1;
                int endCol = endPosition.getColumn() - 1;

                // promote piece if applicable
                if (promotionPiece != null) {
                    movingPiece.type = promotionPiece;
                }
                // move piece to new position
                currentBoard.boardPieces[endRow][endCol] = movingPiece;
                currentBoard.boardPieces[row][col] = null;

                // re clone board to account for move
                clonedBoard = (ChessBoard) currentBoard.clone();

                if (teamTurn == TeamColor.WHITE) {
                    teamTurn = TeamColor.BLACK;
                } else {
                    teamTurn = TeamColor.WHITE;
                }
            } else {
                throw new InvalidMoveException();
            }
        } else {
            throw new InvalidMoveException();
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = findKing(teamColor);

        // iterate through board
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = clonedBoard.getPiece(position);

                if (piece != null && piece.getTeamColor() != teamColor) {
                    Collection<ChessMove> otherTeamMoves = piece.pieceMoves(clonedBoard, position);
                    for (ChessMove move : otherTeamMoves) {
                        if (move.getEndPosition().equals(kingPosition)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        boolean checkStatusOfKing = isInCheck(teamColor);
        if (checkStatusOfKing) {
            for (int row = 1; row <= 8; row++) {
                for (int col = 1; col <= 8; col++) {
                    ChessPosition position = new ChessPosition(row, col);
                    ChessPiece piece = currentBoard.getPiece(position);

                    if (piece != null && piece.getTeamColor() == teamColor) {
                        Collection<ChessMove> currentTeamMoves = validMoves(position);

                        if (currentTeamMoves == null) {
                            return false;
                        }
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {

        boolean checkStatusOfKing = isInCheck(teamColor);
        Collection<ChessMove> validMoves = new HashSet<>();

        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = currentBoard.getPiece(position);

                if (piece != null && piece.getTeamColor() == teamColor) {
                    validMoves.addAll(validMoves(position));
                }
            }
        }
        if (!checkStatusOfKing && validMoves.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        currentBoard = board;
        clonedBoard = (ChessBoard) currentBoard.clone();
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return currentBoard;
    }

    public ChessPosition findKing(TeamColor teamColor) {
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = clonedBoard.getPiece(position);

                if (piece != null && piece.getTeamColor() == teamColor && piece.getPieceType() == ChessPiece.PieceType.KING) {
                    return position;
                }
            }
        }
        return null;
    }
}
