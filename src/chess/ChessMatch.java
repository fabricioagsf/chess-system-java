package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	
	
	
	

	public ChessMatch() {
		
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.BRANCO;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);

			}

		}
		return mat;
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validadeSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validadeSourcePosition(source);
		validadeTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		nextTurn();
		return (ChessPiece) capturedPiece;

	}

	private void validadeSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("Nao existe peca na posicao de origem");
		}
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("A peca escolhida nao e sua");
		}

		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("Nao extiste movimentos possiveis para a peca escolhida");
		}
	}

	private void validadeTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("A peca escolhida nao pode ser movida para a posicao de destino");
		}
	}

	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiecePosition(source);
		Piece capturedPiece = board.removePiecePosition(target);
		board.placePiece(p, target);
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.BRANCO) ? Color.PRETO : Color.BRANCO;
	}

	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.BRANCO));
		placeNewPiece('c', 2, new Rook(board, Color.BRANCO));
		placeNewPiece('d', 2, new Rook(board, Color.BRANCO));
		placeNewPiece('e', 2, new Rook(board, Color.BRANCO));
		placeNewPiece('e', 1, new Rook(board, Color.BRANCO));
		placeNewPiece('d', 1, new King(board, Color.BRANCO));

		placeNewPiece('c', 7, new Rook(board, Color.PRETO));
		placeNewPiece('c', 8, new Rook(board, Color.PRETO));
		placeNewPiece('d', 7, new Rook(board, Color.PRETO));
		placeNewPiece('e', 7, new Rook(board, Color.PRETO));
		placeNewPiece('e', 8, new Rook(board, Color.PRETO));
		placeNewPiece('d', 8, new King(board, Color.PRETO));
	}

}
