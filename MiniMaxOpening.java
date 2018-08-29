import java.io.*;
import java.util.*;
public class MiniMaxOpening {
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
	
	public OutputClass MiniMax(int depth, boolean isWhite, MorrisPositionList board)
	{
		 OutputClass output= new OutputClass();
		
		if (depth == 0)
		{
			output.val = MorrisGame.statEstOpening(board);
			output.numNodes++;
			return output;
		}

		OutputClass input = new OutputClass();
		List<MorrisPositionList> nextMoves = (isWhite) ? MorrisGame.generateMovesOpening(board) : MorrisGame.generateMovesOpeningBlack(board);
		output.val = (isWhite) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		for (MorrisPositionList b : nextMoves)
		{
			if (isWhite)
			{
				input = MiniMax(depth - 1, false, b);
				output.numNodes += input.numNodes;
				if (input.val > output.val)
				{
					output.val = input.val;
					output.b = b;
				}
			}
			else
			{
				input = MiniMax(depth - 1, true, b);
				output.numNodes += input.numNodes;
				if (input.val < output.val)
				{
					output.val = input.val;
					output.b = b;
				}
			}
		}
		
		return output;
	}
	 
	public static void main(String[] args) throws FileNotFoundException
	{	
		if (args.length != 3) {
			System.out.println("Pass 3 arguments");
		} else {
			MiniMaxOpening obj = new MiniMaxOpening();
			File inputFile = new File(args[0]); // board1.txt
			File outputFile = new File(args[1]); // board2.txt
			int depth = Integer.parseInt(args[2]);
			Scanner sc = new Scanner(inputFile);
			String b = sc.nextLine();
			char[] board = b.toCharArray();
			MorrisPositionList initBoard = new MorrisPositionList(getGamePattern(b));
			OutputClass output = obj.MiniMax(depth, true, initBoard);
			System.out.println("Input Board: " + new String(board));
			System.out.println(output);
			PrintWriter board2 = new PrintWriter(outputFile);
			board2.println(output);
			sc.close();
			board2.close();
		}
	}
}
