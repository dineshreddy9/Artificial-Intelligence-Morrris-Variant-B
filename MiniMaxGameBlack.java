import java.util.*;


import java.io.*;

public class MiniMaxGameBlack {
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
	
	
	public  OutputClass MiniMax(int depth, boolean isWhite, MorrisPositionList board)
	{
		OutputClass out = new OutputClass();

		if (depth == 0)
		{
			out.val = MorrisGame.statEstMidgameEndgame(board);
			out.numNodes++;
			return out;
		}

		OutputClass in = new OutputClass();
		List<MorrisPositionList> nextMoves = (isWhite) ? MorrisGame.generateMovesMidgameEndgame(board) : MorrisGame.generateMovesMidgameEndgameBlack(board);
		out.val = (isWhite) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		for (MorrisPositionList b : nextMoves)
		{
			if (isWhite)
			{
				in = MiniMax(depth - 1, false, b);
				out.numNodes += in.numNodes;
				if (in.val > out.val)
				{
					out.val = in.val;
					out.b = b;
				}
			}
			else
			{
				in = MiniMax(depth - 1, true, b);
				out.numNodes += in.numNodes;
				//out.numNodes++;
				if (in.val < out.val)
				{
					out.val = in.val;
					out.b = b;
				}
			}
		}
		return out;
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		
		if (args.length != 3) {
			System.out.println("Pass 3 arguments");
		} else {
			MiniMaxGameBlack obj = new MiniMaxGameBlack();
			File inputFile = new File(args[0]); // board1.txt
			File outputFile = new File(args[1]); // board2.txt
			int depth = Integer.parseInt(args[2]);
			Scanner sc = new Scanner(inputFile);
			String b = sc.nextLine();
			char[] board = b.toCharArray();
			MorrisPositionList initBoard = new MorrisPositionList(getGamePattern(b));
			MorrisPositionList swap = initBoard.getFlipBoard();
			OutputClass output = obj.MiniMax(depth, true, swap);
			output.b = output.b.getFlipBoard();
			System.out.println("Input Board: " + new String(board));
			System.out.println(output);
			PrintWriter board2 = new PrintWriter(outputFile);
			board2.println(output);
			sc.close();
			board2.close();
		}
		}
	}
