package baekjoon;

import java.io.*;
import java.util.*;

public class p17472_다리만들기2 {
	static int[] dx = { 1, 0, -1, 0 };
	static int[] dy = { 0, 1, 0, -1 };
	static int N, M, island;
	static int[][] map;
	static boolean[][] visited;
	static ArrayList<Node>[] list;

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
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 섬에 번호 메기기 
		island = 2;
		visited = new boolean[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 1 && !visited[i][j]) {
					bfs(i, j, island);
					island++;
				}
			}
		}
		island--;
		list = new ArrayList[island];
		for (int i = 0; i < island; i++) {
			list[i] = new ArrayList<>();
		}
		
		// 연결할 수 있는 섬 모두 다리 연결하기 
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] != 0) {
					makeBridges(i, j, map[i][j]);
				}
			}
		}
		
		// 최소신장트리로 모든 섬을 연결하는 다리 길이의 최솟값 구하기 - 프림 알고리즘 
		int min = prim(1);
		System.out.println(min == 0 ? -1 : min);
	}

	
	private static int prim(int start) {
		int sum = 0;
		boolean[] visited = new boolean[island];
		
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(start, 0));
		
		while(!pq.isEmpty()) {
			Node now = pq.poll();
			
			if (visited[now.node]) continue;
			
			visited[now.node] = true;
			sum += now.value;
			
			for (Node next : list[now.node]) {
				if (!visited[next.node]) {
					pq.add(next);
				}
			}
		}
		
		// 연결되지 않은 섬이 있을 경우 
		for (int i = 1; i < island; i++) {
			if (!visited[i]) {
				return 0;
			}
		}
		
		return sum;
	}


	private static void makeBridges(int i, int j, int num) {
		Queue<int[]> q = new LinkedList<>();
		visited = new boolean[N][M];
		
		for (int k = 0; k < 4; k++) {
			q.add(new int[] { i, j, 0 });
			visited[i][j] = true;
			
			while (!q.isEmpty()) {
				int[] now = q.poll();
				int x = now[0] + dx[k];
				int y = now[1] + dy[k];
				int length = now[2];
				
				if (x < 0 || y < 0 || x >= N || y >= M || map[x][y] == num || visited[x][y]) {
					continue;
				}
				
				if (map[x][y] == 0) {
					// 바다이면 
					q.add(new int[] { x, y, length + 1 });
					visited[x][y] = true;
				} else {
					// 다른 섬이면 
					if (length >= 2) {
						int start = num - 1;
						int end = map[x][y] - 1;
						list[start].add(new Node(end, length));
						list[end].add(new Node(start, length));
						break;
					}
				}
			}
		}
	}


	private static void bfs(int i, int j, int num) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] { i, j });
		visited[i][j] = true;
		map[i][j] = num;
		
		while(!q.isEmpty()) {
			int[] now = q.poll();
			
			for (int k = 0; k < 4; k++) {
				int x = now[0] + dx[k];
				int y = now[1] + dy[k];
				
				if (x < 0 || y < 0 || x >= N || y >= M) continue;
				
				if (map[x][y] == 1 && !visited[x][y]) {
					q.add(new int[] { x, y });
					visited[x][y] = true;
					map[x][y] = num;
				}
			}
		}
	}


	static class Node implements Comparable<Node> {
		int node, value;
		
		public Node (int node, int value) {
			this.node = node;
			this.value = value;
		}
		
		@Override
		public int compareTo(Node o) {
			return this.value - o.value;
		}
		
	}
}
