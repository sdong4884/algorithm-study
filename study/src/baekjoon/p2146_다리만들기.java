package baekjoon;

import java.io.*;
import java.util.*;

public class p2146_다리만들기 {

	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N;
	static int[][] map;
	static boolean[][] visited;
	static int res;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		visited = new boolean[N][N];
		StringTokenizer st;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()); // 0-바다, 1-육지 
			}
		}
		
		// 섬에 번호 메기기 
		int idx = 2;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == 1 && !visited[i][j]) {
					bfs(i, j, idx);
					idx++;
				}
			}
		}
		
		// 섬과 섬 최소 거리로 연결하기 
		res = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] != 0) {
					buildBridge(i, j, map[i][j]);
				}
			}
		}
		
		System.out.println(res);
	}

	private static void buildBridge(int i, int j, int num) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] { i, j, 0 });
		visited = new boolean[N][N];
		visited[i][j] = true;
		
		while (!q.isEmpty()) {
			int[] now = q.poll();
			int nx = now[0];
			int ny = now[1];
			int dis = now[2];
			
			if (map[nx][ny] != 0 && map[nx][ny] != num) {
				res = Math.min(res, dis - 1);
				return;
			}
			
			for (int k = 0; k < 4; k++) {
				int x = nx + dx[k];
				int y = ny + dy[k];
				
				if (x < 0 || y < 0 || x >= N || y >= N || map[x][y] == num || visited[x][y]) {
					continue;
				}
				
				q.add(new int[] { x, y, dis + 1 });
				visited[x][y] = true;
			}
		}
	}

	private static void bfs(int i, int j, int idx) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] { i, j });
		visited[i][j] = true;
		map[i][j] = idx;
		
		while (!q.isEmpty()) {
			int[] now = q.poll();
			
			for (int k = 0; k < 4; k++) {
				int x = now[0] + dx[k];
				int y = now[1] + dy[k];
				
				if (x >= 0 && y >= 0 && x < N && y < N) {
					if (map[x][y] == 1 && !visited[x][y]) {
						q.add(new int[] { x, y });
						visited[x][y] = true;
						map[x][y] = idx;
					}
				}
			}
		}
	}

}
