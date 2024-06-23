package baekjoon;

import java.io.*;
import java.util.*;

public class p18352_특정거리의도시찾기 {
	static class Node {
		int city, dis;
		
		public Node(int city, int dis) {
			this.city = city;
			this.dis = dis;
		}
	}
	
	static int N, M, K, X;
	static boolean[] visited;
	static ArrayList<Integer>[] list;
	static ArrayList<Integer> res;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 도시 개수 
		M = Integer.parseInt(st.nextToken()); // 도로 개수 
		K = Integer.parseInt(st.nextToken()); // 거리 정보 
		X = Integer.parseInt(st.nextToken()); // 출발 도시의 정보 

		visited = new boolean[N + 1];
		list = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			list[a].add(b);
		}
		
		// 도시 X로부터 출발하여 도달할 수 있는 모든 도시 중에서, 최단 거리가 '정확히 K'인 모든 도시들의 번호를 출력
		
		res = new ArrayList<>();
		solve(X);
		
		int len = res.size();
		if (len == 0) {
			System.out.println(-1);
			return;
		}
		Collections.sort(res);
		for (int i = 0; i < len; i++) {
			System.out.println(res.get(i));
		}
	}

	private static void solve(int start) {
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(start, 0));
		visited[start] = true;
		
		while (!q.isEmpty()) {
			Node now = q.poll();
			
			if (now.dis == K) {
				res.add(now.city);
			}
			
			for (int next : list[now.city]) {
				if (!visited[next]) {
					q.add(new Node(next, now.dis + 1));
					visited[next] = true;
				}
			}
		}
	}

}
