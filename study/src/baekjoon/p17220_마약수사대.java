package baekjoon;

import java.io.*;
import java.util.*;

public class p17220_마약수사대 {
	static int N, M;
	static ArrayList<ArrayList<Integer>> graph;
	static int[] arr;
	static Queue<Integer> q;
	static int res;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 마약 공급책의 수 
		M = Integer.parseInt(st.nextToken()); // 마약 공급책의 관계 수 
		
		graph = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			graph.add(new ArrayList<>());
		}
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = st.nextToken().charAt(0) - 65;
			int b = st.nextToken().charAt(0) - 65;
			graph.get(a).add(b);
		}
		
		arr = new int[N];
		st = new StringTokenizer(br.readLine());
		int K = Integer.parseInt(st.nextToken());
		for (int i = 0; i < K; i++) {
			int k = st.nextToken().charAt(0) - 65;
			arr[k] = 1;
			dfs(k);
		}
		
		res = 0;
		q = new LinkedList<>();
		for (int i = 0; i < N; i++) {
			if (graph.get(i).size() == 0 || arr[i] != 0) {
				continue;
			}
			arr[i] = 1;
			q.add(i);
			bfs();
		}

		System.out.println(res);
	}

	private static void dfs(int k) {
		for (int i = 0; i < graph.get(k).size(); i++) {
			int node = graph.get(k).get(i);
			if (arr[node] == 0) {
				arr[node] = 2;
				dfs(node);
			}
		}
	}

	private static void bfs() {
		while (!q.isEmpty()) {
			int now = q.poll();
			
			for (int i = 0; i < graph.get(now).size(); i++) {
				int node = graph.get(now).get(i);
				if (arr[node] != 1) {
					arr[node] = 1;
					q.add(node);
					res++;
				}
			}
		}
	}
}
