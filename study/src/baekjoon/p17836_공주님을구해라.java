package baekjoon;

import java.io.*;
import java.util.*;

public class p17836_공주님을구해라 {
	static class Soldier {
		int x, y, dis;
		boolean isGram;
		
		public Soldier(int x, int y, int dis, boolean isGram) {
			this.x = x;
			this.y = y;
			this.dis = dis;
			this.isGram = isGram;
		}
	}
	
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, T;
	static int[][] map;
	static boolean[][][] visited;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken()); // 제한시간 
		
		map = new int[N + 1][M + 1];
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()); // 0-빈공간, 1-벽, 2-그람 
			}
		}
		
		visited = new boolean[N + 1][M + 1][2]; // 3index: 0-그람X, 1-그람O 
		
		int res = solve();
		System.out.println(res == -1 ? "Fail" : res);
	}

	private static int solve() {
		Queue<Soldier> q = new LinkedList<>();
		q.add(new Soldier(1, 1, 0, false));
		visited[1][1][0] = true;
		
		while (!q.isEmpty()) {
			Soldier now = q.poll();
			
			if (now.dis > T) {
				break;
			}
			
			if (now.x == N && now.y == M) {
				return now.dis;
			}
			
			for (int k = 0; k < 4; k++) {
				int x = now.x + dx[k];
				int y = now.y + dy[k];
				
				if (x > 0 && y > 0 && x <= N && y <= M) {
					if (!now.isGram && !visited[x][y][0]) {
						if (map[x][y] == 0) {
							q.add(new Soldier(x, y, now.dis + 1, now.isGram));
						} else if (map[x][y] == 2) {
							q.add(new Soldier(x, y, now.dis + 1, true));
						}
						visited[x][y][0] = true;
					} else if (now.isGram && !visited[x][y][1]) {
						q.add(new Soldier(x, y, now.dis + 1, now.isGram));
						visited[x][y][1] = true;
					}
				}
			}
		}
		
		return -1;
	}

}
