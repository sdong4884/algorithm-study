package baekjoon;

import java.io.*;
import java.util.*;

public class p13905_세부 {
	static class Node implements Comparable<Node> {
		int v1, v2, w;
		
		public Node(int v1, int v2, int w) {
			this.v1 = v1;
			this.v2 = v2;
			this.w = w;
		}

		@Override
		public int compareTo(Node o) {
			return o.w - w; // 오름차순 정렬 
		}
	}
	
	static int N, M, s, e;
	static int[] parents;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 집의 수 
		M = Integer.parseInt(st.nextToken()); // 다리의 수 
		st = new StringTokenizer(br.readLine());
		s = Integer.parseInt(st.nextToken()); // 숭이의 출발 위치
		e = Integer.parseInt(st.nextToken()); // 혜빈이의 위치
		
		PriorityQueue<Node> pq = new PriorityQueue<>();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken());
			int v2 = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			pq.add(new Node(v1, v2, w));
		}
		
		parents = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			parents[i] = i;
		}
		
		int min = Integer.MAX_VALUE;
		while (!pq.isEmpty()) {
			Node now = pq.poll();
			
			if (find(now.v1) != find(now.v2)) {
				union(now.v1, now.v2);
				min = Math.min(min, now.w);
			}
			
			if (find(s) == find(e)) {
				System.out.println(min);
				return;
			}
		}
		
		System.out.println(0);
	}

	private static void union(int x, int y) {
		x = find(x);
		y = find(y);
		
		if (x < y) {
			parents[y] = x;
		} else {
			parents[x] = y;
		}
	}

	private static int find(int x) {
		if (parents[x] == x) {
			return x;
		}
		return parents[x] = find(parents[x]);
	}

}
