package algorithm;

import java.util.*;

public class Prim {
	static class Node implements Comparable<Node> {
		int v, w;
		
		public Node(int v, int w) {
			this.v = v;
			this.w = w;
		}

		@Override
		public int compareTo(Node o) {
			return w - o.w;
		}
	}
	
	static int n;
	static ArrayList<Node>[] list;
	static boolean[] visited;
	static int total;

	public static void main(String[] args) {
		n = 7; 
		int[][] graph = {{1,2,29},{1,5,75},{2,3,35},{2,6,34},{3,4,7},{4,6,23},{4,7,13},{5,6,53},{6,7,25}};
		
		list = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++) {
			list[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < graph.length; i++) {
			int v1 = graph[i][0];
			int v2 = graph[i][1];
			int w = graph[i][2];
			list[v1].add(new Node(v2, w));
			list[v2].add(new Node(v1, w));
		}
		
		visited = new boolean[n + 1];
		total = 0;
		prim(1);

		System.out.println("Prim " + total);
	}

	private static void prim(int start) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(start, 0));
		
		while (!pq.isEmpty()) {
			Node now = pq.poll();
			
			if (visited[now.v]) {
				continue;
			}
			
			visited[now.v] = true;
			total += now.w;
			
			for (Node next : list[now.v]) {
				if (!visited[next.v]) {
					pq.add(next);
				}
			}
		}
	}

}
