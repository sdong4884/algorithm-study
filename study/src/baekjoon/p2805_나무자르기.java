package baekjoon;

import java.io.*;
import java.util.*;

public class p2805_나무자르기 {
	static int N, M;
	static int[] trees;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 나무의 수 
		M = Integer.parseInt(st.nextToken()); // 집으로 가져가려고 하는 나무의 길이
		
		trees = new int[N];
		int max = 0;
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			trees[i] = Integer.parseInt(st.nextToken());
			max = Math.max(max, trees[i]);
		}
		
		int left = 0;
		int right = max;
		while (left < right) {
			int mid = (left + right) / 2;
			
			long total = 0;
			for (int tree: trees) {
				int length = tree - mid;
				if (length > 0) {
					total += length;
				}
			}
			
			if (total >= M) {
				left = mid + 1;
			} else {
				right = mid;
			}
		}
		
		System.out.println(left - 1);
	}

}
