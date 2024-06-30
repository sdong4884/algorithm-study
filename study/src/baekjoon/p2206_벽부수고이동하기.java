package baekjoon;

import java.io.*;
import java.util.*;

public class p2206_벽부수고이동하기 {
	static class Node {
		int x, y, dis;
		boolean flag;
		
		public Node(int x, int y, int dis, boolean flag) {
			this.x = x;
			this.y = y;
			this.dis = dis;
			this.flag = flag;
		}
	}
	
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M;
	static int[][] map;
	static boolean[][][] visited;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		visited = new boolean[N][M][2]; // 인덱스 -> 0:벽 부수지 않고 이동, 1:벽 부수고 이동 
		
		for (int i = 0; i < N; i++) {
			String line = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(line.substring(j, j + 1));
			}
 		}
		
		int res = BFS(0, 0);
		System.out.println(res);
	}

	private static int BFS(int i, int j) {
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(i, j, 1, false));
		visited[i][j][0] = true;
		
		while (!q.isEmpty()) {
			Node now = q.poll();
			
			if (now.x == N - 1 && now.y == M - 1) {
				return now.dis;
			}
			
			for (int k = 0; k < 4; k++) {
				int x = now.x + dx[k];
				int y = now.y + dy[k];
				
				if (x >= 0 && y >= 0 && x < N && y < M) {
					// 벽이 아닌 경우
					if (map[x][y] == 0) {
						// 벽을 부수지 않고 이동 중인 경우 
						if (!now.flag && !visited[x][y][0]) {
							visited[x][y][0] = true;
							q.add(new Node(x, y, now.dis + 1, now.flag));
						}
						// 벽을 부수고 이동 중인 경우 
						else if (now.flag && !visited[x][y][1]) {
							visited[x][y][1] = true;
							q.add(new Node(x, y, now.dis + 1, now.flag));
						}
					}
					// 벽인 경우 
					else {
						// 아직까지 벽을 부수지 않은 경우 부수고 이동 가능 
						if (!now.flag) {
							q.add(new Node(x, y, now.dis + 1, true));
							visited[x][y][1] = true;
						}
					}
				}
			}
		}
		
		return -1;
	}

}
