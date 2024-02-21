package baekjoon;

public class Combination {
	static int n, r;
	static int[] arr = { 1, 2, 3 };
	static int[] out;
	static boolean[] visited;

	public static void main(String[] args) {
		// 조합 - n개에서 순서 없이 r개를 뽑는 경우의 수 -> [1, 2]와 [2, 1] 같은 것으로 취급

		n = arr.length;
		r = 2;
		out = new int[n];
		visited = new boolean[n];
		
		comb1(0, 0); // 조합 
		System.out.println();
		comb2(0, 0); // 중복 조합 
	}

	private static void comb1(int start, int depth) {
		if (depth == r) {
			for (int i = 0; i < r; i++) {
				System.out.print(out[i] + " ");
			}
			System.out.println();
			return;
		}
		
		for (int i = start; i < n; i++) {
			out[depth] = arr[i];
			comb1(i + 1, depth + 1);
		}
	}

	private static void comb2(int start, int depth) {
		if (depth == r) {
			for (int i = 0; i < r; i++) {
				System.out.print(out[i] + " ");
			}
			System.out.println();
			return;
		}
		
		for (int i = start; i < n; i++) {
			out[depth] = arr[i];
			comb2(i, depth + 1);
		}
	}
	
}
