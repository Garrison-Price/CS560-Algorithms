import java.util.Arrays;


public class CutRod {
	static int[] pricePerLength = new int[10];
	static int[][] optimalCuts = new int[10][9];
	static final int INFINITY = 9999;
	
	public static int memoizedCutRod(int n) {
		int[] bestPriceForLength = new int[n+1];
		
		bestPriceForLength[0] = 0;
		
		for(int currentTotalRodLength = 1; currentTotalRodLength <= n; currentTotalRodLength++) {
			int bestPrice = -INFINITY;
			
			for(int currentPartialRodLength = 1; currentPartialRodLength <= currentTotalRodLength; currentPartialRodLength++) {
				int currentCut = 0;
				if(pricePerLength[currentPartialRodLength] + bestPriceForLength[currentTotalRodLength-currentPartialRodLength] > bestPrice) {
					System.arraycopy(optimalCuts[currentTotalRodLength-currentPartialRodLength], 0, optimalCuts[currentTotalRodLength], 0, 9);
					while(optimalCuts[currentTotalRodLength][currentCut++] != 0);
					optimalCuts[currentTotalRodLength][--currentCut] = currentPartialRodLength;
				}
				bestPrice = Math.max(bestPrice, pricePerLength[currentPartialRodLength] + bestPriceForLength[currentTotalRodLength-currentPartialRodLength]);
			}
			bestPriceForLength[currentTotalRodLength] = bestPrice;
		}
		return bestPriceForLength[n];
	}
	
	public static int memoizedGlueRod(int n) {
		int[] bestPriceForLength = new int[n+1];
		
		bestPriceForLength[0] = 0;
		
		for(int currentTotalRodLength = 1; currentTotalRodLength <= n; currentTotalRodLength++) {
			int bestPrice = INFINITY;
			
			for(int currentPartialRodLength = currentTotalRodLength; currentPartialRodLength >= 1; currentPartialRodLength--) {
				int currentCut = 0;
				if(pricePerLength[currentPartialRodLength] + bestPriceForLength[currentTotalRodLength-currentPartialRodLength] < bestPrice) {
					System.arraycopy(optimalCuts[currentTotalRodLength-currentPartialRodLength], 0, optimalCuts[currentTotalRodLength], 0, 9);
					while(optimalCuts[currentTotalRodLength][currentCut++] != 0);
					optimalCuts[currentTotalRodLength][--currentCut] = currentPartialRodLength;
				}
				bestPrice = Math.min(bestPrice, pricePerLength[currentPartialRodLength] + bestPriceForLength[currentTotalRodLength-currentPartialRodLength]);
			}
			bestPriceForLength[currentTotalRodLength] = bestPrice;
		}
		return bestPriceForLength[n];
	}
	
	public static void main(String args[]) {
		pricePerLength[1] = 6;
		pricePerLength[2] = 9;
		pricePerLength[3] = 14;
		pricePerLength[4] = 20;
		pricePerLength[5] = 26;
		pricePerLength[6] = 29;
		pricePerLength[7] = 35;
		pricePerLength[8] = 40;
		pricePerLength[9] = 43;
		
		System.out.println(memoizedGlueRod(9));
		System.out.println(Arrays.toString(optimalCuts[9]));
	}
}
