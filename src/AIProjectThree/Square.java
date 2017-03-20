package AIProjectThree;

import java.util.ArrayList;

public class Square {
	
	private int value;
	private ArrayList<Integer> list;
	
	public Square()
	{
		value = 0;
		list = new ArrayList<Integer>();
		startList();
	}
	
	public Square(int value)
	{
		this.value = value; 
		list = new ArrayList<Integer>();
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	public ArrayList<Integer> getList()
	{
		return list;
	}
	
	public void setList(ArrayList<Integer> list)
	{
		this.list = list;
	}
	
	public void startList()
	{
		for(int i = 1; i < 10; i++)
		{
			list.add(i);
		}
	}
	
	public void removeFromList(int n)
	{
		list.remove(Integer.valueOf(n));
		if(list.size() == 1)
		{
			setValue(list.get(0));
		}
			
	}

}
