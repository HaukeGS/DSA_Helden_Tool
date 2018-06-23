package skills;

import static skills.LevelCostCalculator.Column.A;
import static skills.LevelCostCalculator.Column.A_STAR;
import static skills.LevelCostCalculator.Column.B;
import static skills.LevelCostCalculator.Column.C;
import static skills.LevelCostCalculator.Column.D;
import static skills.LevelCostCalculator.Column.E;
import static skills.LevelCostCalculator.Column.F;
import static skills.LevelCostCalculator.Column.G;
import static skills.LevelCostCalculator.Column.H;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by Hauke on 12.07.2017.
 */
public class LevelCostCalculator {

	private final Map<Column, List<Integer>> map = new HashMap<>();
	
	public enum Column {
		A_STAR,
		A,
		B,
		C,
		D,
		E,
		F,
		G,
		H
	}

	public LevelCostCalculator() {
		map.put(A_STAR, Arrays.asList(0, 1, 1, 1, 2, 4, 5, 6, 8, 9, 11, 12, 14, 15,
				17, 19, 20, 22, 24, 25, 27, 29, 31, 32, 34, 36, 38, 40, 42, 43,
				45, 48));
		map.put(A, Arrays.asList(0, 1, 2, 3, 4, 6, 7, 8, 10, 11, 13, 14, 16,
				17, 19, 21, 22, 24, 26, 27, 29, 31, 33, 34, 36, 38, 40, 42, 44,
				45, 47, 50));
		map.put(B, Arrays.asList(0, 2, 4, 6, 8, 11, 14, 17, 19, 22, 25, 28, 32,
				35, 38, 41, 45, 48, 51, 55, 58, 62, 65, 69, 73, 76, 80, 84, 87,
				91, 95, 100));
		map.put(C, Arrays.asList(0, 2, 6, 9, 13, 17, 21, 25, 29, 34, 38, 43,
				47, 51, 55, 60, 65, 70, 75, 80, 85, 95, 100, 105, 110, 115,
				120, 125, 130, 135, 140, 150));
		map.put(D, Arrays.asList(0, 3, 7, 12, 17, 22, 27, 33, 39, 45, 50, 55,
				65, 70, 75, 85, 90, 95, 105, 110, 115, 125, 130, 140, 145, 150,
				160, 165, 170, 180, 190, 200));
		map.put(E, Arrays.asList(0, 4, 9, 15, 21, 28, 34, 41, 48, 55, 65, 70,
				80, 85, 95, 105, 110, 120, 130, 135, 145, 155, 165, 170, 180,
				190, 200, 210, 220, 230, 240, 250));
		map.put(F, Arrays.asList(0, 6, 14, 22, 32, 41, 50, 60, 75, 85, 95, 105,
				120, 130, 140, 155, 165, 180, 195, 210, 220, 230, 250, 260,
				270, 290, 300, 310, 330, 340, 350, 375));
		map.put(G, Arrays.asList(0, 8, 18, 30, 42, 55, 70, 85, 95, 110, 125,
				140, 160, 175, 190, 210, 220, 240, 260, 270, 290, 310, 330,
				340, 360, 380, 400, 420, 440, 460, 480, 500));
		map.put(H, Arrays.asList(0, 16, 35, 60, 85, 110, 140, 165, 195, 220,
				250, 280, 320, 350, 380, 410, 450, 480, 510, 550, 580, 620,
				650, 690, 720, 760, 800, 830, 870, 910, 950, 1000));
	}

	public int getCost(int from, int to, Column column) {
		if (from > to)
			throw new IllegalArgumentException("startlevel must be lower than targetlevel. For refund call getRefund()");
		if (from < 0 || from > 31 || to < 0 || to > 31)
			throw new IllegalArgumentException("level must be between 0 and 31");

		final int lowerCost = stream(column).limit(from + 1).sum();
		final int higherCost = stream(column).limit(to + 1).sum();
		return higherCost - lowerCost;
	}
	
	public int getRefund(int from, int to, Column column) {
		if (to > from)
			throw new IllegalArgumentException("startlevel must be higher than targetlevel. For cost call getCost()");
		if (from < 0 || from > 31 || to < 0 || to > 31)
			throw new IllegalArgumentException("level must be between 0 and 31");
		
		return getCost(to, from, column);
	}

	private IntStream stream(Column c) {
		return map.get(c).stream().mapToInt(Integer::intValue);
	}
}
