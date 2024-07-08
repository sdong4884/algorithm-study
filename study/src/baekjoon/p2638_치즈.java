package baekjoon;

import java.io.*;
import java.util.*;

public class p2638_치즈 {
	static class Node {
		int x, y;
		
		public Node(int x, int  y) {
			this.x = x;
			this.y = y;
		}
	}
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M;
	static int[][] map;
	static boolean[][] visited;
	static int cheeseCnt;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		cheeseCnt = 0;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()); // 1-치즈, 0-빈칸 
				if (map[i][j] == 1) {
					cheeseCnt++;
				}
			}
		}
		
		int time = 0;
		while (cheeseCnt > 0) {
			visited = new boolean[N][M];
			dfs(0, 0); // 외부 공기 -1로 변경
			time++;
			// 치즈 녹이기 
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if (map[i][j] == 1) {
						melting(i, j);
					}
				}
			}
			
		}
		
		System.out.println(time);
	}
	
	private static void melting(int i, int j) {
		int count = 0;
		
		for (int k = 0; k < 4; k++) {
			int x = i + dx[k];
			int y = j + dy[k];
			
			if (map[x][y] == -1) {
				count++;
			}
		}
		
		if (count >= 2) {
			map[i][j] = 0;
			cheeseCnt--;
		}
	}

	private static void dfs(int i, int j) {
		visited[i][j] = true;
		map[i][j] = -1;
		
		for (int k = 0; k < 4; k++) {
			int x = i + dx[k];
			int y = j + dy[k];
			
			if (x >= 0 && y >= 0 && x < N && y < M) {
				if (map[x][y] != 1 && !visited[x][y]) {
					dfs(x, y);
				}
			}
		}
	}

	private static void bfs(int i, int j) {
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(i, j));
		visited[i][j] = true;
		map[i][j] = -1;
		
		while (!q.isEmpty()) {
			Node now = q.poll();
			
			for (int k = 0; k < 4; k++) {
				int x = now.x + dx[k];
				int y = now.y + dy[k];
				
				if (x >= 0 && y >= 0 && x < N && y < M) {
					if (map[x][y] != 1 && !visited[x][y]) {
						q.add(new Node(x, y));
						visited[x][y] = true;
						map[x][y] = -1;
					}
				}
			}
		}
	}

}
