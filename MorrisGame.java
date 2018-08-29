
import java.util.*;

public class MorrisGame {
	public static List<MorrisPositionList> generateMovesOpening(MorrisPositionList board) {
		return generateAdd(board);
	}
	
	public static List<MorrisPositionList> generateMovesOpeningBlack(MorrisPositionList board) {
		MorrisPositionList tempb = board.getFlipBoard();
		List<MorrisPositionList> moves = generateMovesOpening(tempb);
		for (int i = 0; i < moves.size(); i++) {
			MorrisPositionList b = moves.get(i);
			moves.set(i, b.getFlipBoard());
		}
		return moves;
	}
	
	public static List<MorrisPositionList> generateMovesMidgameEndgame(MorrisPositionList board) {
		if (board.getNumPieces(PositionName.W) == 3) {
			return generateHopping(board);
		} else {
			return generateMove(board);
		}
	}
	
	public static List<MorrisPositionList> generateMovesMidgameEndgameBlack(MorrisPositionList board) {
		MorrisPositionList tempb = board.getFlipBoard();
		List<MorrisPositionList> moves = generateMovesMidgameEndgame(tempb);
		ArrayList<MorrisPositionList> out = new ArrayList<MorrisPositionList>();
		for (int i = 0; i < moves.size(); i++) {
			MorrisPositionList b = moves.get(i);
			out.add(b.getFlipBoard());
		}
		return out;
	}
	
	public static List<MorrisPositionList> generateAdd(MorrisPositionList board) {
		ArrayList<MorrisPositionList> l = new ArrayList<MorrisPositionList>();
		for (int i = 0; i < board.size(); i++) {
			if (board.get(i) == PositionName.X) {
				MorrisPositionList b = board.getCopy();
				b.set(i, PositionName.W);
				if (closeMill(i, b)) {
					int s = l.size();
					l = generateRemove(b, l);
				} else {
					l.add(b);
				}
			}
		}
		return l;
	}
	
	public static List<MorrisPositionList> generateHopping(MorrisPositionList board) {
		ArrayList<MorrisPositionList> l = new ArrayList<MorrisPositionList>();
		for (int i = 0; i < board.size(); i++) {
			if (board.get(i) == PositionName.W) {
				for (int j = 0; j < board.size(); j++) {
					if (board.get(j) == PositionName.X) {
						MorrisPositionList b = board.getCopy();
						b.set(i, PositionName.X);
						b.set(j, PositionName.W);
						if (closeMill(j, b)) {
							generateRemove(b, l);
						} else {
							l.add(b);
						}
					}
				}
			}
		}
		return l;
	}
	
	public static List<MorrisPositionList> generateMove(MorrisPositionList board) {
		ArrayList<MorrisPositionList> l = new ArrayList<MorrisPositionList>();
		for (int i = 0; i < board.size(); i++) {
			if (board.get(i) == PositionName.W) {
				List<Integer> n = neighbors(i);
				for (int j : n) {
					if (board.get(j) == PositionName.X) {
						MorrisPositionList b = board.getCopy();
						b.set(i, PositionName.X);
						b.set(j, PositionName.W);
						if (closeMill(j, b)) {
							l = generateRemove(b, l);
						} else {
							l.add(b);
						}
					}
				}
			}
		}
		return l;
	}

	public static ArrayList<MorrisPositionList> generateRemove(MorrisPositionList board, ArrayList<MorrisPositionList> l) {
		for (int i = 0; i < board.size(); i++) {
			if (board.get(i) == PositionName.B) {
				if (!closeMill(i, board)) {
					MorrisPositionList b = board.getCopy();
					b.set(i, PositionName.X);
					l.add(b);
				}
			}
		}
		return l;
	}
	
	public static List<Integer> neighbors(int j) {
		switch (j) {
		case 0:
			return Arrays.asList(1, 2, 6);
		case 1:
			return Arrays.asList(0, 11);
		case 2:
			return Arrays.asList(0, 3, 4, 7);
		case 3:
			return Arrays.asList(2, 10);
		case 4:
			return Arrays.asList(2, 5, 8);
		case 5:
			return Arrays.asList(4, 9);
		case 6:
			return Arrays.asList(0, 7, 18);
		case 7:
			return Arrays.asList(2, 6, 8, 15);
		case 8:
			return Arrays.asList(4, 7, 12);
		case 9:
			return Arrays.asList(5, 10, 14);
		case 10:
			return Arrays.asList(3, 9, 11, 17);
		case 11:
			return Arrays.asList(1, 10, 20);
		case 12:
			return Arrays.asList(8, 13);
		case 13:
			return Arrays.asList(12, 14, 16);
		case 14:
			return Arrays.asList(9, 13);
		case 15:
			return Arrays.asList(7, 16);
		case 16:
			return Arrays.asList(13, 15, 17, 19);
		case 17:
			return Arrays.asList(10, 16);
		case 18:
			return Arrays.asList(6, 19);
		case 19:
			return Arrays.asList(16, 18, 20);
		case 20:
			return Arrays.asList(11, 19);
		default:
			return (new ArrayList<Integer>());
		}
	}
	
	public static boolean closeMill(int j, MorrisPositionList b) {
		PositionName C = b.get(j);
		if (C == PositionName.X) {
			return false;
		} else {
			return checkAllMills(j, C, b);
		}
	}
	
	public static boolean checkAllMills(int j, PositionName C, MorrisPositionList b) {
		switch (j) {
		case 0:
			return (checkMill(b, C, 2, 4) || checkMill(b, C, 6, 18));
		case 1:
			return (checkMill(b, C, 11, 20));
		case 2:
			return (checkMill(b, C, 0, 4) || checkMill(b, C, 7, 15));
		case 3:
			return (checkMill(b, C, 10, 17));
		case 4:
			return (checkMill(b, C, 0, 2) || checkMill(b, C, 8, 12));
		case 5:
			return (checkMill(b, C, 9, 14));
		case 6:
			return (checkMill(b, C, 0, 18) || checkMill(b, C, 7, 8));
		case 7:
			return (checkMill(b, C, 6, 8) || checkMill(b, C, 2, 15));
		case 8:
			return (checkMill(b, C, 6, 7) || checkMill(b, C, 4, 12));
		case 9:
			return (checkMill(b, C, 5, 14) || checkMill(b, C, 10, 11));
		case 10:
			return (checkMill(b, C, 3, 17) || checkMill(b, C, 9, 11));
		case 11:
			return (checkMill(b, C, 1, 20) || checkMill(b, C, 9, 10));
		case 12:
			return (checkMill(b, C, 4, 8) || checkMill(b, C, 13, 14));
		case 13:
			return (checkMill(b, C, 12, 14) || checkMill(b, C, 16, 19));
		case 14:
			return (checkMill(b, C, 12, 13) || checkMill(b, C, 5, 9));
		case 15:
			return (checkMill(b, C, 2, 7) || checkMill(b, C, 16, 17));
		case 16:
			return (checkMill(b, C, 15, 17) || checkMill(b, C, 13, 19));
		case 17:
			return (checkMill(b, C, 3, 10) || checkMill(b, C, 15, 16));
		case 18:
			return (checkMill(b, C, 0, 6) || checkMill(b, C, 19, 20));
		case 19:
			return (checkMill(b, C, 13, 16) || checkMill(b, C, 18, 20));
		case 20:
			return (checkMill(b, C, 1, 11) || checkMill(b, C, 18, 19));
		default:
			return false;
		}
	}
	
	private static boolean checkMill(MorrisPositionList b, PositionName C, int v1, int v2) {
		return (b.get(v1) == C && b.get(v2) == C);
	}

	public static int statEstOpening(MorrisPositionList board) {
		return (board.getNumPieces(PositionName.W) - board.getNumPieces(PositionName.B));
	}
	
	public static int statEstMidgameEndgame(MorrisPositionList board) {
		int bPieces = board.getNumPieces(PositionName.B);
		int wPieces = board.getNumPieces(PositionName.W);
		List<MorrisPositionList> l = generateMovesMidgameEndgame(board);
		int numBMoves = l.size();
		if (bPieces <= 2) {
			return 10000;
		} else if (wPieces <= 2) {
			return -10000;
		} else if (bPieces == 0) {
			return 10000;
		} else {
			return 1000 * (wPieces - bPieces) - numBMoves;
		}
	}
	
	public static int statEstOpeningImproved(MorrisPositionList board) {
		return (board.getNumPieces(PositionName.W) + numPossibleMills(PositionName.W, board)
				- board.getNumPieces(PositionName.B));
	}
	
	public static int statEstMidgameEndgameImproved(MorrisPositionList board) {
		int bPieces = board.getNumPieces(PositionName.B);
		int wPieces = board.getNumPieces(PositionName.W);
		int numPosMills = numPossibleMills(PositionName.W, board);
		List<MorrisPositionList> l = generateMovesMidgameEndgame(board);
		int numBMoves = l.size();
		if (bPieces <= 2) {
			return 10000;
		} else if (wPieces <= 2) {
			return -10000;
		} else if (bPieces == 0) {
			return 10000;
		} else {
			return 1000 * (numPosMills + wPieces - bPieces) - numBMoves;
		}
	}
	
	public static int numPossibleMills(PositionName pos, MorrisPositionList board) {
		int counter = 0;
		for (int i = 0; i < board.size(); i++) {
			PositionName bPos = board.get(i);
			if (bPos == PositionName.X) {
				if (checkAllMills(i, bPos, board)) {
					counter++;
				}
			}
		}
		return counter;
	}
}
