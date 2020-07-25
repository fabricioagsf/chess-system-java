package boardgame;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;

	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardExcepition("Erro ao criar o tabuleiro: é necessario ter ao menos 1 linha e 1 coluna");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) {
		if(!posictionExists(row, column)) {
			throw new BoardExcepition("Posição não existe no tabuleiro");
		}
		return pieces[row][column];
	}

	public Piece piece(Position position) {
		if(!posictionExists(position)) {
			throw new BoardExcepition("Posição não existe no tabuleiro");
		}
		return pieces[position.getRow()][position.getColumn()];
	}

	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) {
			throw new BoardExcepition("Já existe uma peça nesta posição "+position);
		} 		
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

	private Boolean posictionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	public Boolean posictionExists(Position position) {
		return posictionExists(position.getRow(), position.getColumn());
	}

	public Boolean thereIsAPiece(Position position) {
		if(!posictionExists(position)) {
			throw new BoardExcepition("Posição não existe no tabuleiro");
		}
		return piece(position) != null;
	}

}
