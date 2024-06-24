package baekjoon;

import java.io.*;
import java.util.*;

public class p17472_다리만들기2 {
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
	
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M;
	static int[][] map;
	static boolean[][] visited;
	static int island;
	static ArrayList<Node>[] list;
	static int total;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()); // 바다 0 
				if (map[i][j] == 1) {
					map[i][j] = -1; // 땅 -1
				}
			}
		}
		
		// 연결된 땅에 섬이라고 번호 메기기 
		int idx = 1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == -1) {
					makeIsland(i, j, idx);
					idx++;
				}
			}
		}
		island = idx - 1;
		
		list = new ArrayList[island + 1];
		for (int i = 1; i <= island; i++) {
			list[i] = new ArrayList<>();
		}
		
		// 건설할 수 있는 다리 모두 건설 
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] != 0) {
					buildBridges(i, j, map[i][j]);
				}
			}
		}
		
		// 모든 섬을 연결하는 다리 길이의 최솟값 구하기 
		total = 0;
		prim(1);
		
		System.out.println(total);
	}

	private static void makeIsland(int i, int j, int idx) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] { i, j });
		map[i][j] = idx;
		
		while (!q.isEmpty()) {
			int[] now = q.poll();
			
			for (int k = 0; k < 4; k++) {
				int x = now[0] + dx[k];
				int y = now[1] + dy[k];
				
				if (x >= 0 && y >= 0 && x < N && y < M) {
					if (map[x][y] == -1) {
						q.add(new int[] { x, y });
						map[x][y] = idx;
					}
				}
 			}
		}
	}

	private static void buildBridges(int i, int j, int num) {
		/* 다리의 조건 
		 1. 다리의 양 끝은 섬과 인접한 바다 위에 있어야 한다.
		 2. 한 다리의 방향이 중간에 바뀌면 안된다.
		 3. 다리의 길이는 2 이상이어야 한다. */
		
		Queue<int[]> q = new LinkedList<>();
		visited = new boolean[N][M];
		
		for (int k = 0; k < 4; k++) {
			q.add(new int[] { i, j, 0 });
			visited[i][j] = true;
			
			while (!q.isEmpty()) {
				int[] now = q.poll();
				int x = now[0] + dx[k];
				int y = now[1] + dy[k];
				int len = now[2];
				
				if (x < 0 || y < 0 || x >= N || y >= M || visited[x][y] || map[x][y] == num) {
					continue;
				}
				
				if (map[x][y] == 0) {
					q.add(new int[] { x, y, len + 1 });
					visited[x][y] = true;
				} else {
					if (len >= 2) {
						// 다리 건설 
						list[num].add(new Node(map[x][y], len));
						list[map[x][y]].add(new Node(num, len));
						break;
					}
				}
			}
		}
	}

	private static void prim(int start) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(start, 0));
		
		boolean[] visited = new boolean[island + 1];
		
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
		
		// 만약 모든 섬을 연결하지 못할 경우 
		for (int i = 1; i <= island; i++) {
			if (!visited[i]) {
				total = -1;
			}
		}
	}
}
