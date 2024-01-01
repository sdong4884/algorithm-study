package baekjoon;

import java.io.*;
import java.util.*;

public class p13460_구슬탈출2 {
	static int[] dx = { 1, -1, 0, 0 };
	static int[] dy = { 0, 0, 1, -1 };
	static int N, M;
	static char[][] map;
	static boolean[][][][] visited;
	
	static class Node {
		int rx, ry, bx, by, cnt;
		
		public Node (int rx, int ry, int bx, int by, int cnt) {
			this.rx = rx;
			this.ry = ry;
			this.bx = bx;
			this.by = by;
			this.cnt = cnt;
		}
	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new char[N][M];
		visited = new boolean[N][M][N][M];
		
		int rx = 0, ry = 0, bx = 0, by = 0;
		for (int i = 0; i < N; i++) {
			String line = br.readLine();
			for (int j = 0; j < M; j++) {
				// . 빈칸, # 벽, O 구멍, R 빨간구슬, B 파란구슬 
				map[i][j] = line.charAt(j);
				
				if (map[i][j] == 'R') {
					rx = i;
					ry = j;
				} else if  (map[i][j] == 'B') {
					bx = i;
					by = j;
				}
			}
		}
		
		// 최소 몇 번 만에 빨간 구슬을 구멍을 통해 빼낼 수 있는지 출력
		System.out.println(BFS(rx, ry, bx, by));
	}

	private static int BFS(int rx, int ry, int bx, int by) {
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(rx, ry, bx, by, 0));
		visited[rx][ry][bx][by] = true;
		
		while (!q.isEmpty()) {
			Node now = q.poll();
			
			// 10번 이하로 움직여서 빨간 구슬을 빼낼 수 없으면 실패 
			if (now.cnt > 10) {
				return -1;
			}
			// 파란 구슬이 구멍에 빠질 경우 실패 
			if (map[now.bx][now.by] == 'O') {
				continue;
			}
			// 빨간 구슬만 구멍에 빠질 경우 성공 
			if (map[now.rx][now.ry] == 'O') {
				return now.cnt;
			}
			
			for (int k = 0; k < 4; k++) {
				// 구슬이동 - 벽이나 구멍을 만날 때까지 굴러가기, 벽이면 한 칸 뒤로
				
				// 빨간구슬 이동
				int n_rx = now.rx;
				int n_ry = now.ry;
				while (true) {
					n_rx += dx[k];
					n_ry += dy[k];
					if (map[n_rx][n_ry] == '#' || map[n_rx][n_ry] == 'O') {
						break;
					}
				}
				if (map[n_rx][n_ry] == '#') {
					n_rx -= dx[k];
					n_ry -= dy[k];
				}
				
				// 파란구슬 이동
				int n_bx = now.bx;
				int n_by = now.by;
				while (true) {
					n_bx += dx[k];
					n_by += dy[k];
					if (map[n_bx][n_by] == '#' || map[n_bx][n_by] == 'O') {
						break;
					}
				}
				if (map[n_bx][n_by] == '#') {
					n_bx -= dx[k];
					n_by -= dy[k];
				}
				
				// 빨간구슬과 파란구슬이 같은 위치에 있고 그 위치가 구멍은 아닌 경 - 이동거리가 더 큰 구슬 한 칸 뒤로 
				if (n_rx == n_bx && n_ry == n_by && map[n_rx][n_ry] != 'O') {
					int move_r = Math.abs(now.rx - n_rx) + Math.abs(now.ry - n_ry);
					int move_b = Math.abs(now.bx - n_bx) + Math.abs(now.by - n_by);
					if (move_r > move_b) {
						n_rx -= dx[k];
						n_ry -= dy[k];
					} else {
						n_bx -= dx[k];
						n_by -= dy[k];
					}
				}
				
				// 빨간구슬, 파란구슬 위치 큐에 저장
				if (!visited[n_rx][n_ry][n_bx][n_by]) {
					q.add(new Node(n_rx, n_ry, n_bx, n_by, now.cnt + 1));
					visited[n_rx][n_ry][n_bx][n_by] = true;
				}
			}
		}
		
		return -1;
	}
}
