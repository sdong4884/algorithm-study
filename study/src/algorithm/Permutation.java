package algorithm;

public class Permutation {
	static int n, r;
	static int[] arr = { 1, 2, 3 };
	static boolean[] visited;
	static int[] out;

	public static void main(String[] args) {
		// 순열 : n개 중에서 r개를 뽑아 나열하는 경우의 수 
		n = arr.length;
		r = 2;
		
		visited = new boolean[n];
		out = new int[2];
		
		permutation(0);
		System.out.println();
		permutation2(0); // 중복 순열 
	}

	private static void permutation(int depth) {
		if (depth == r) {
			print();
			return;
		}
		
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				visited[i] = true;
				out[depth] = arr[i];
				permutation(depth + 1);
				visited[i] = false;
			}
		}
	}

	private static void permutation2(int depth) {
		if (depth == r) {
			print();
			return;
		}
		
		for (int i = 0; i < n; i++) {
			out[depth] = arr[i];
			permutation(depth + 1);
		}
	}

	private static void print() {
		for (int i = 0; i < r; i++) {
			System.out.print(out[i] + " ");
		}
		System.out.println();
	}

}
