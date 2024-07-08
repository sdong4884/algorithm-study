package baekjoon;

import java.io.*;
import java.util.*;

public class p7576_토마토 {
	static class Node {
		int x, y, time;
		
		public Node(int x, int y, int time) {
			this.x = x;
			this.y = y;
			this.time = time;
		}
	}
	
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M;
	static int[][] map;
	static Queue<Node> tomato;
	static int count, time;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[M][N];
		tomato = new LinkedList<>();
		count = 0;
		time = 0;
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				// 1:익은 토마토, 0:익지 않은 토마토, -1:토마토가 들어있지 않은 칸
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 1) {
					tomato.add(new Node(i, j, 1));
				} else if(map[i][j] == 0) {
					count++;
				}
			}
		}
		
		while (!tomato.isEmpty()) {
			Node now = tomato.poll();
			
			for (int k = 0; k < 4; k++) {
				int x = now.x + dx[k];
				int y = now.y + dy[k];
				
				if (x >= 0 && y >= 0 && x < M && y < N) {
					if (map[x][y] == 0) {
						tomato.add(new Node(x, y, now.time + 1));
						map[x][y] = now.time + 1;
						count--;
					}
				}
			}
		}
		
		if (count > 0) {
			System.out.println(-1);
			return;
		}
		
		int max = 0;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				max = Math.max(max, map[i][j] - 1);
			}
		}
		System.out.println(max);
	}

}
