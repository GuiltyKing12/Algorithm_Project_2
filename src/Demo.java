public class Demo {
	public final static int LEFT = 0;
	public final static int UP = 1;
	public final static int DIAG = 2;
	
	public int n = 8;
	public int L = 10;
	public Cell[][] cost_table = new Cell[n + 1][n + 1]; 
	public int P = 5;
	public int c = 2;
	public int[] opt = {LEFT, UP, DIAG};
	public int[] gas_amounts = {1, 2, 3, 4, 5, 6, 7, 8};
	public int C =   1;
	
	public Demo() {
		for(int i = 0; i <= n; i++) {
			for(int j = 0; j <= n; j++) {
				cost_table[i][j] = new Cell();
			}
		}
	}
	
	public void cost_calculate(int[] gas_amounts) {
		// initialize the Day 1 to Day 1
		cost_table[1][1].cost = P;
		cost_table[1][1].parent_day = -1;
		cost_table[1][1].parent_dayto = -1;
		cost_table[1][1].order = true;
		
		// initialize the Day 1 to Day n where the amounts of gas is less than L
		cost_table[1][1].current_L = gas_amounts[0];
		for(int initial = 2; initial <= n; initial++) {
			cost_table[1][initial].current_L = gas_amounts[initial - 1];
			if(cost_table[1][initial].current_L + cost_table[1][initial - 1].current_L <= L) {
				cost_table[1][initial].cost = cost_table[1][initial -1].cost + (initial-1) * c * gas_amounts[initial - 1];
				cost_table[1][initial].order = true;
				cost_table[1][initial].current_L += cost_table[1][initial -1].current_L;
				cost_table[1][initial].parent_day = -1;
				cost_table[1][initial].parent_dayto = -1;
			}
			else break;
		}
		
		// For the rest of the table we use the left, top, diagonal cells to caclualte the current cell
		for(int day = 2; day <= n; day++) {
			for(int dayto = day; dayto <= n; dayto++) {
				// On the day we make sure to get the current gas in order to increment as we go through
				if(day == dayto) cost_table[day][dayto].current_L = gas_amounts[dayto - 1];
				else cost_table[day][dayto].current_L = cost_table[day][dayto - 1].current_L + gas_amounts[dayto -1];
				
				// break if current_L is greater than L
				if(cost_table[day][dayto].current_L > L) continue;
				
				opt[LEFT] = P + cost_table[day][dayto - 1].cost;
				opt[UP] = cost_table[day - 1][dayto].cost;
				opt[DIAG] = P + cost_table[day - 1][dayto - 1].cost;
				
				cost_table[day][dayto].cost = opt[LEFT];
				cost_table[day][dayto].parent_day = day;
				cost_table[day][dayto].parent_dayto = dayto - 1;
				cost_table[day][dayto].order = true;
				
				if(cost_table[day][dayto].cost > opt[UP]) {
					cost_table[day][dayto].cost = opt[UP];
					cost_table[day][dayto].parent_day = day - 1;
					cost_table[day][dayto].parent_dayto = dayto;
					cost_table[day][dayto].order = false;
				}
				if(cost_table[day][dayto].cost >= opt[DIAG]) {
					cost_table[day][dayto].cost = opt[DIAG];
					cost_table[day][dayto].parent_day = day - 1;
					cost_table[day][dayto].parent_dayto = dayto - 1;
					cost_table[day][dayto].order = true;
				}
			}
		}
	}

	public void traceback() {
		Cell cell = new Cell();
		cell = cost_table[n][n];
		int day = n;
		while(cell.parent_day != -1) {
			if(cell.order) System.out.println("Ordered on day " + day);
			day = cell.parent_day;
			cell = cost_table[cell.parent_day][cell.parent_dayto];
		}
		System.out.println("Ordered on day 1");
	}
	
	public void print() {
		for(int i = 1; i <= n; i++) {
			for(int k = 1; k <= n; k++) {
				if(k >= i) {
					if(cost_table[i][k].cost != 99999) System.out.format("%4d", cost_table[i][k].cost);
					else System.out.format("%4d", -1);
				}
				else System.out.format("%4d", -1);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		Demo demo = new Demo();
		demo.cost_calculate(demo.gas_amounts);
		demo.print();
		demo.traceback();
	}
}
