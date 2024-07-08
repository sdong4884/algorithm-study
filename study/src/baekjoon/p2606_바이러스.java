package baekjoon;

import java.io.*;
import java.util.*;

public class p2606_바이러스 {
	static boolean[] visited;
	static ArrayList<Integer>[] list;
	static int cnt;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int N = Integer.parseInt(br.readLine());
		visited = new boolean[N + 1];
		list = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}
		
		int K = Integer.parseInt(br.readLine());
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			list[s].add(e);
			list[e].add(s);
		}
		
		cnt = 0;
		solve(1);
		
		System.out.println(cnt - 1);
	}

	private static void solve(int start) {
		Queue<Integer> q = new LinkedList<>();
		q.add(start);
		
		while (!q.isEmpty()) {
			Integer now = q.poll();
			
			if (visited[now]) {
				continue;
			}
			
			visited[now] = true;
			cnt++;
			
			for (Integer i : list[now]) {
				if (!visited[i]) {
					q.add(i);
				}
			}
		}
	}

}
