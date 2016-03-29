public class Demo {
	int n = 8;
	int L = 10;
	Cell[][] cost_table = new Cell[n + 1][L + 1]; // 
	int P = 5;
	int c = 2;
	int current_L = 0;
	int[] gas_amounts = {4, 2, 3, 5, 7, 9 ,2, 6};
	int C = 1;
	
	public void cost_calculate(int[] gas_amounts) {
		int n = gas_amounts.length;
		cost_table[0][0] = new Cell();
		cost_table[0][0].cost = 0;
				
		for(int i = 0; i < L; i ++) { 
					cost_table[0][i] = new Cell();
					cost_table[0][i].cost = 999999;
		}

		for(int j = 1; j < n; j ++) {
			for(int k = 0; k < L; k++) {
				cost_table[j][k] = new Cell();
				cost_table[j][k].order = false;
				cost_table[j][k].cost = 999999;
				for(int X = max(0, k + gas_amounts[j] - L); X < k + gas_amounts[j]; X++) {
					int temp_cost = order(X) + C*(X) + cost_table[j -1][k + gas_amounts[j] - X].cost + c * (k + gas_amounts[j] - X);
					if(temp_cost < cost_table[j][k].cost) {
						cost_table[j][k].cost = temp_cost;
							cost_table[j][k].order = true;
					}
				}
			}
		}
	}
	
	private int order(int x) {
		// TODO Auto-generated method stub
		if(x == 0) return 0;
		else return P;
	}

	private int max(int i, int j) {
		// TODO Auto-generated method stub
		if( i > j) return i;
		else return j;
	}

	public static void main(String[] args) {
		Demo demo = new Demo();
		demo.cost_calculate(demo.gas_amounts);
		int j = demo.L;
		for(int i = demo.n; i > 0; i++) {
			if(demo.cost_table[i][j].order) {
				System.out.println("Ordered on Day: " + i);
			}
			j += demo.gas_amounts[i];
		}
	}
}
