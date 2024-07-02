package baekjoon;

import java.io.*;
import java.util.*;

public class p1697_숨바꼭질 {
	static class Node {
		int pos, time;
		
		public Node(int pos, int time) {
			this.pos = pos;
			this.time = time;
		}
	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		int res = BFS(N, K);
		System.out.println(res);
	}

	private static int BFS(int start, int end) {
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(start, 0));
		
		boolean[] visited = new boolean[100001];
		visited[start] = true;
		
		while (!q.isEmpty()) {
			Node now = q.poll();
			
			if (now.pos == end) {
				return now.time;
			}
			
			int next = 0;
			next = now.pos - 1;
			if (next >= 0 && !visited[next]) {
				q.add(new Node(next, now.time + 1));
				visited[next] = true;
			}
			next = now.pos + 1;
			if (next <= 100000 && !visited[next]) {
				q.add(new Node(next, now.time + 1));
				visited[next] = true;
			}
			next = now.pos * 2;
			if (next <= 100000 && !visited[next]) {
				q.add(new Node(next, now.time + 1));
				visited[next] = true;
			}
		}
		
		return 0;
	}
}
