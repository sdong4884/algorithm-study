package baekjoon;

import java.io.*;
import java.util.*;

public class p5427_불 {
	static class Node {
		int x, y, time;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public Node(int x, int y, int time) {
			this.x = x;
			this.y = y;
			this.time = time;
		}
	}
	
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int h, w;
	static char[][] map;
	static Queue<Node> person, fire;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for (int t = 0; t < T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			w = Integer.parseInt(st.nextToken());
			h = Integer.parseInt(st.nextToken());
			map = new char[h][w];
			person = new LinkedList<>();
			fire = new LinkedList<>();
			for (int i = 0; i < h; i++) {
				String line = br.readLine();
				for (int j = 0; j < w; j++) {
					map[i][j] = line.charAt(j); // #-벽, *-불, @-상근, .-빈칸 
					if (map[i][j] == '@') {
						person.add(new Node(i, j, 0));
					} else if (map[i][j] == '*') {
						fire.add(new Node(i, j));
					}
				}
			}
			
			int time = solve();
			
			System.out.println(time == 0 ? "IMPOSSIBLE" : time);
		}
	}

	private static int solve() {
		while (!person.isEmpty()) {
			// 불 확산 
			int size = fire.size();
			for (int i = 0; i < size; i++) {
				Node now = fire.poll();
				
				for (int k = 0; k < 4; k++) {
					int x = now.x + dx[k];
					int y = now.y + dy[k];
					
					if (x >= 0 && y >= 0 && x < h && y < w) {
						if (map[x][y] == '.' || map[x][y] == '@') {
							fire.add(new Node(x, y));
							map[x][y] = '*';
						}
					}
				}
			}
			
			// 상근 이동 
			size = person.size();
			for (int i = 0; i < size; i++) {
				Node now = person.poll();
				
				for (int k = 0; k < 4; k++) {
					int x = now.x + dx[k];
					int y = now.y + dy[k];
					
					if (x < 0 || y < 0 || x >= h || y >= w) {
						return now.time + 1;
					}
					
					if (map[x][y] == '.') {
						person.add(new Node(x, y, now.time + 1));
						map[x][y] = '@';
					}
				}
			}
		}
		
		return 0;
	}

}
