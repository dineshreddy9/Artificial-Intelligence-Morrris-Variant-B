import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MorrisPositionList {
	public List<PositionName> positionList;

	public MorrisPositionList()
	{
		positionList = Arrays.asList(PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X, PositionName.X);
	}

	
	public MorrisPositionList(List<Character> inputBoard)
	{
		positionList = new ArrayList<PositionName>();
		for (char c : inputBoard)
		{
			PositionName pos = (c == 'W') ? PositionName.W : ((c == 'B') ? PositionName.B : PositionName.X);
			positionList.add(pos);
		}
	}

	public List<Character> getCharList()
	{
		ArrayList<Character> out = new ArrayList<Character>();
		for (PositionName pos : positionList)
		{
			out.add(pos.val);
		}
		return out;
	}

	public char[] getCharArr()
	{
		char[] out = new char[positionList.size()];
		for (int i = 0; i < positionList.size(); i++)
		{
			out[i] = positionList.get(i).val;
		}
		return out;
	}

	public MorrisPositionList getCopy()
	{
		List<Character> boardState = getCharList();
		return (new MorrisPositionList(boardState));
	}

	public MorrisPositionList getFlipBoard()
	{
		MorrisPositionList out = new MorrisPositionList();
		for (int i = 0; i < positionList.size(); i++)
		{
			PositionName val = positionList.get(i);
			if (val == PositionName.B)
			{
				out.set(i, PositionName.W);
			}
			else if (val == PositionName. W)
			{
				out.set(i, PositionName.B);
			}
			else
			{
				out.set(i, PositionName.X);
			}
		}
		return out;
	}

	public int getNumPieces(PositionName piecePos)
	{
		int counter = 0;
		for (PositionName pos : positionList)
		{
			if (pos == piecePos)
			{
				counter++;
			}
		}
		return counter;
	}

	
	public PositionName get(int i)
	{
		return positionList.get(i);
	}

	public int size()
	{
		return positionList.size();
	}

	public void add(PositionName val)
	{
		positionList.add(val);
	}

	public void set(int i, PositionName val)
	{
		positionList.set(i, val);
	}

	public String toString()
	{
		return (new String(getCharArr()));
	}
}
