package AIProjectThree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	private static Square[][] puzzle;
	private static java.util.Scanner input;

	// main( String args[] ) - runs the program  
	public static void main( String args[] ) throws FileNotFoundException 
	{
		// Prompt the user to enter a file name
		System.out.println("Welcome to the Sudoku Solver!");
		System.out.println("Please enter a file name: ");
		input = new java.util.Scanner(System.in);	
		String fileName = input.nextLine();
		
		// Open the file and read in the puzzle 
		File file = openFile( fileName );
		ArrayList<Integer> puzzleNumbers = readFile( file );
		fillPuzzle(puzzleNumbers);
		
		// Display the initial puzzle
		System.out.println("The puzzle you entered is: ");
		printPuzzle();
		
		// Solve the puzzle
		solve();
		
		// Display final solution
		System.out.println("The solved puzzle is:");
		printPuzzle();
		
	}

	// openFile( String fileName ) - opens a file 
	public static File openFile( String fileName )
	{
		File file = null;
		try
		{
			file = new File( fileName );
			if( !file.exists() )
			{
				System.out.println("This fine does not exist.");
				System.exit(0);
			}
		}
		catch( Exception e ){ }	
		
		return file;
	}

	// readFile( File file ) - read through the file and sets the puzzle
	public static ArrayList<Integer> readFile( File file ) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));
			
		///Creates an ArrayList<Integer> of numbers for the puzzle from file
		ArrayList<Integer> puzzleNumbers = new ArrayList<Integer>();
		while( scanner.hasNext() )
		{
			if( scanner.hasNextInt() )
			{
				puzzleNumbers.add(scanner.nextInt());
			}
			else
			{
				scanner.next();
			}
		}
		scanner.close();
		return puzzleNumbers;
	}
	
	// fillPuzzle( ArrayList<Integer> gridNumbers ) - creates the grid out of ArrayList
	public static  void fillPuzzle( ArrayList<Integer> puzzleNumbers )
	{
		puzzle = new Square[9][9];
		for( int i = 0; i <9; i++ )
		{
			for( int j = 0; j < 9; j++ )
			{
				int currentNum = puzzleNumbers.get(0);
				if(currentNum > 0)
				{
					puzzle[i][j] = new Square(currentNum);
				}
				else
				{
					puzzle[i][j] = new Square();
				}
				puzzleNumbers.remove( 0 );
			}
		}
	}
	
	// printPuzzle() - prints the contents of the puzzle 
	public static void printPuzzle()
	{
		for (int i = 0; i < 9; ++i) {
            if (i % 3 == 0)
                System.out.println(" -----------------------");
            for (int j = 0; j < 9; ++j) {
                if (j % 3 == 0) System.out.print("| ");
                System.out.print(puzzle[i][j].getValue() == 0
                                 ? " "
                                 : Integer.toString(puzzle[i][j].getValue()));

                System.out.print(' ');
            }
            System.out.println("|");
        }
        System.out.println(" -----------------------");
        System.out.println();
	}
	
	// solve() - solves the Sudoku puzzle
	public static void solve()
	{
		while(true)
		{
			for(int i = 0; i < 9; i++)
			{
				checkRow(i);
				checkColumn(i);
				checkSquare(i);
			}
			int count = 0;
			for(int i = 0; i < 9; i++)
			{
				for(int j = 0; j < 9; j++)
				{
					if(puzzle[i][j].getValue() != 0)
					{
						count++;
					}
				}
			}
			if(count == 81)
			{
				break;
			}
		}
	}
	
	// checkRow(int index) - checks the row based on the index
	public static void checkRow(int index)
	{
		ArrayList<Integer> row = new ArrayList<Integer>();
		for(int i = 0; i < 9; i++)
		{
			int num = puzzle[index][i].getValue();
			if(num != 0)
			{
				row.add(num);
			}
		}
		for(int i = 0; i < 9; i++)
		{
			if(puzzle[index][i].getValue() == 0)
			{
				for(int j = 0; j < row.size(); j++)
				{
					puzzle[index][i].removeFromList(row.get(j));
				}
			}
		}
		int start = 1;
		for(int i = 0; i < 8; i++)
		{
			ArrayList<Integer> list = puzzle[index][i].getList();
			for(int j = start; j < 9; j++)
			{
				if(list.equals( puzzle[index][j].getList()) && list.size() == 2)
				{
					for(int p = 0; p < 9; p++)
					{
						if(p != i && p != j)
						{
							puzzle[index][p].removeFromList(list.get(0));
							puzzle[index][p].removeFromList(list.get(1));
						}
					}
				}		
			}
			start++;
		}
	}
	
	// checkColumn(int index) - checks the column based on the index
	public static void checkColumn(int index)
	{
		ArrayList<Integer> column = new ArrayList<Integer>();
		for(int i = 0; i < 9; i++)
		{
			int num = puzzle[i][index].getValue();
			if(num != 0)
			{
				column.add(num);
			}
		}
		for(int i = 0; i < 9; i++)
		{
			if(puzzle[i][index].getValue() == 0)
			{
				for(int j = 0; j < column.size(); j++)
				{
					puzzle[i][index].removeFromList(column.get(j));
				}
			}
		}
		int start = 1;
		for(int i = 0; i < 8; i++)
		{
			ArrayList<Integer> list = puzzle[i][index].getList();
			for(int j = start; j < 9; j++)
			{
				if(list.equals( puzzle[j][index].getList()) && list.size() == 2)
				{
					for(int p = 0; p < 9; p++)
					{
						if(p != i && p != j)
						{
							puzzle[p][index].removeFromList(list.get(0));
							puzzle[p][index].removeFromList(list.get(1));
						}
					}
				}		
			}
			start++;
		}
	}
	
	// checkSquare(int n) -  checks the square based on the parametere
	public static void checkSquare(int n)
	{
		int startRow;
		int endRow;
		int startColumn;
		int endColumn;
		if(n == 0)
		{
			startRow = 0;
			endRow = 2;
			startColumn = 0;
			endColumn = 2;
			
		}
		else if(n == 1)
		{
			startRow = 3;
			endRow = 5;
			startColumn = 0;
			endColumn = 2;
		}
		else if(n == 2)
		{
			startRow = 6;
			endRow = 8;
			startColumn = 0;
			endColumn = 2;
		}
		else if(n == 3)
		{
			startRow = 0;
			endRow = 2;
			startColumn = 3;
			endColumn = 5;
		}
		else if(n == 4)
		{
			startRow = 3;
			endRow = 5;
			startColumn = 3;
			endColumn = 5;
		}
		else if(n == 5)
		{
			startRow = 6;
			endRow = 8;
			startColumn = 3;
			endColumn = 5;
		}
		else if(n == 6)
		{
			startRow = 0;
			endRow = 2;
			startColumn = 6;
			endColumn = 8;
		}
		else if(n == 7)
		{
			startRow = 3;
			endRow = 5;
			startColumn = 6;
			endColumn = 8;
		}
		else
		{
			startRow = 6;
			endRow = 8;
			startColumn = 6;
			endColumn = 8;
		}
			
		ArrayList<Integer> square = new ArrayList<Integer>();
		for(int i = startRow; i <= endRow; i++)
		{
			for(int j = startColumn; j <= endColumn; j++)
			{
				int num = puzzle[i][j].getValue();
				if(num != 0)
				{
					square.add(num);
				}
			}
		}
		for(int i = startRow; i <= endRow; i++)
		{
			for(int j = startColumn; j <= endColumn; j++)
			{
				if(puzzle[i][j].getValue() == 0)
				{
					for(int s = 0; s < square.size(); s++)
					{
						puzzle[i][j].removeFromList(square.get(s));
					}
				}
			}
		}
		
		for(int i = startRow; i <= endRow; i++)
		{
			for(int j = startColumn; j <= endColumn; j++)
			{
				ArrayList<Integer> list = puzzle[i][j].getList();
				if(list.size() == 2)
				{
					for(int r = startRow; r <= endRow; r++)
					{
						for(int c = startColumn; c <= endColumn; c++)
						{
							if((i != r || c != j) && list.equals(puzzle[r][c].getList()))
							{
								for(int p = startRow; p <= endRow; p++)
								{
									for(int q = startColumn; q <= endColumn; q++)
									{
										if( i != p && r != p && j != q && c != q)
										{
											puzzle[p][q].removeFromList(list.get(0));
											puzzle[p][q].removeFromList(list.get(1));
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
}
