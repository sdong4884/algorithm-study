package baekjoon;

public class Permutation {
	static int n, r;
	static int[] arr = { 1, 2, 3 };
	static int[] out;
	static boolean[] visited;

	public static void main(String[] args) {
		// 순열 - n개에서 r개를 뽑아서 정렬하는 경우의 수 -> [1, 2]와 [2, 1]은 다른 것으로 취급

		n = arr.length;
		r = 2;
		out = new int[n];
		visited = new boolean[n];
		
		perm1(0); // 순열 
		System.out.println();
		perm2(0); // 중복 순열 
	}

	private static void perm1(int depth) {
		if (depth == r) {
			for (int i = 0; i < r; i++) {
				System.out.print(out[i] + " ");
			}
			System.out.println();
			return;
		}
		
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				visited[i] = true;
				out[depth] = arr[i];
				perm1(depth + 1);
				visited[i] = false;
			}
		}
	}

	private static void perm2(int depth) {
		if (depth == r) {
			for (int i = 0; i < r; i++) {
				System.out.print(out[i] + " ");
			}
			System.out.println();
			return;
		}
		
		for (int i = 0; i < n; i++) {
			out[depth] = arr[i];
			perm2(depth + 1);
		}
	}

}
