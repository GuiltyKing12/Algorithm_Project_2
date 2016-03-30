// cells that make up the cost table
public class Cell {
	public int cost = 99999;	// cost of reaching this cell
	boolean order = false;
	public int parent_day;
	public int parent_dayto;
	public int current_L = -1;
}
