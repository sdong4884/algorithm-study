package algorithm;

public class Combination {
	static int n, r;
	static int[] arr = { 1, 2, 3 };
	static int[] out;

	public static void main(String[] args) {
		// 조합 : n개 중에서 r개를 뽑는 경우의 수 
		n = arr.length;
		r = 2;
		
		out = new int[r];
		
		combination(0, 0);
		System.out.println();
		combination2(0, 0); // 중복 조합 
	}

	private static void combination(int start, int depth) {
		if (depth == r) {
			print();
			return;
		}
		
		for (int i = start; i < n; i++) {
			out[depth] = arr[i];
			combination(i + 1, depth + 1);
		}
	}

	private static void combination2(int start, int depth) {
		if (depth == r) {
			print();
			return;
		}
		
		for (int i = start; i < n; i++) {
			out[depth] = arr[i];
			combination(i, depth + 1);
		}
	}

	private static void print() {
		for (int i = 0; i < r; i++) {
			System.out.print(out[i] + " ");
		}
		System.out.println();
	}

}
