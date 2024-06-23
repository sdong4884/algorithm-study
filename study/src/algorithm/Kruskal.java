package algorithm;

import java.util.*;

public class Kruskal {
	static class Node implements Comparable<Node> {
		int v1, v2, w;
		
		public Node(int v1, int v2, int w) {
			this.v1 = v1;
			this.v2 = v2;
			this.w = w;
		}

		@Override
		public int compareTo(Node o) {
			return w - o.w;
		}
	}
	
	static int n;
	static int[] parents;
	static int total;

	public static void main(String[] args) {
		n = 7; 
		int[][] graph = {{1,2,29},{1,5,75},{2,3,35},{2,6,34},{3,4,7},{4,6,23},{4,7,13},{5,6,53},{6,7,25}};

		parents = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			parents[i] = i;
		}
		
		PriorityQueue<Node> pq = new PriorityQueue<>();
		for (int i = 0; i < graph.length; i++) {
			int v1 = graph[i][0];
			int v2 = graph[i][1];
			int w = graph[i][2];
			pq.add(new Node(v1, v2, w));
		}
		
		total = 0;
		while (!pq.isEmpty()) {
			Node now = pq.poll();
			
			if (find(now.v1) != find(now.v2)) {
				total += now.w;
				union(now.v1, now.v2);
			}
		}
		
		System.out.println("Kruskal " + total);
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
