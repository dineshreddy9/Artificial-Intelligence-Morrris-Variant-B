import java.util.*;

import java.io.*;
public class ABOpening {
	class OutputClass
	{
		public int val, numNodes;
		public MorrisPositionList b;
		public String toString()
		{
			return 	"Output Board: " + b + "\n" +
					"Positions evaluated by static estimation: " + numNodes + "\n" +
					"MINIMAX estimate: " + val;
		}
	}
	
	public static List<Character> getGamePattern(String brd)
	{
	
			ArrayList<Character> out = new ArrayList<Character>();
			for (char a : brd.toCharArray())
			{
				out.add(a);
			}
			return out;
	}

	
	public OutputClass ABMiniMax(int depth, boolean isWhite, MorrisPositionList board, int alpha, int beta)
	{
		OutputClass out = new OutputClass();

		
		if (depth == 0)
		{
			out.val = MorrisGame.statEstOpening(board);
			out.numNodes++;
			return out;
		}

		OutputClass in = new OutputClass();
		List<MorrisPositionList> nextMoves = (isWhite) ? MorrisGame.generateMovesOpening(board) : MorrisGame.generateMovesOpeningBlack(board);
		for (MorrisPositionList b : nextMoves)
		{
			if (isWhite)
			{
				in = ABMiniMax(depth - 1, false, b, alpha, beta);
				out.numNodes += in.numNodes;
				if (in.val > alpha)
				{
					alpha = in.val;
					out.b = b;
				}
			}
			else
			{
				in = ABMiniMax(depth - 1, true, b, alpha, beta);
				out.numNodes += in.numNodes;
				//out.numNodes++;
				if (in.val < beta)
				{
					beta = in.val;
					out.b = b;
				}
			}
			if (alpha >= beta)
			{
				break;
			}
		}
		
		out.val = (isWhite) ? alpha : beta;
		return out;
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		
		if (args.length != 3) {
			System.out.println("Pass 3 arguments");
		} else {
			ABOpening obj = new ABOpening();
			File inputFile = new File(args[0]); // board1.txt
			File outputFile = new File(args[1]); // board2.txt
			int depth = Integer.parseInt(args[2]);
			Scanner sc = new Scanner(inputFile);
			String b = sc.nextLine();
			char[] board = b.toCharArray();
			MorrisPositionList initBoard = new MorrisPositionList(getGamePattern(b));
			OutputClass output = obj.ABMiniMax(depth, true, initBoard,Integer.MIN_VALUE, Integer.MAX_VALUE);
			System.out.println("Input Board: " + new String(board));
			System.out.println(output);
			PrintWriter board2 = new PrintWriter(outputFile);
			board2.println(output);
			sc.close();
			board2.close();
		}
	}
}
