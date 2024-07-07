package baekjoon;

import java.io.*;
import java.util.*;

public class p17142_연구소3 {
	static class Node {
		int x, y, time;

		public Node(int x, int y, int time) {
			this.x = x;
			this.y = y;
			this.time = time;
		}
	}

	static int[] dx = { 1, -1, 0, 0 };
	static int[] dy = { 0, 0, 1, -1 };
	static int N, M, blankCnt;
	static int[][] map, copyMap;
	static boolean[][] visited;
	static ArrayList<Node> virus; // 처음 모든 비활성 바이러스 위치
	static Node[] out; // 조합으로 M개만큼 뽑아낸 바이러스
	static int min;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 연구소 크기 
		M = Integer.parseInt(st.nextToken()); // 바이러스 개수 
		map = new int[N][N];
		copyMap = new int[N][N];
		virus = new ArrayList<>();
		blankCnt = 0;
		out = new Node[M];
		min = Integer.MAX_VALUE;
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()); // 0-빈칸, 1-벽, 2-바이러스
				if (map[i][j] == 0) {
					blankCnt++; // 초기 빈 칸 개수
				} else if (map[i][j] == 2) {
					virus.add(new Node(i, j, 0));
				}
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				copyMap[i][j] = map[i][j];
			}
		}
		
		combination(0, 0);
	
		System.out.println(min == Integer.MAX_VALUE ? -1 : min);
	}

	private static void combination(int start, int depth) {
		if (depth == M) {
			init();
			min = Math.min(min, solve());
			return;
		}
		
		for (int i = start; i < virus.size(); i++) {
			out[depth] = virus.get(i);
			combination(i + 1, depth + 1);
		}
	}

	private static int solve() {
		// 조합으로 뽑아낸 M개의 활성바이러스가 모두 퍼지는 시간 res 
		int res = 0;
		
		Queue<Node> q = new LinkedList<>();
		visited = new boolean[N][N];
		for (int i = 0; i < M; i++) {
			q.add(out[i]);
			visited[out[i].x][out[i].y] = true;
		}
		
		int cnt = 0; // 바이러스가 퍼진 빈 칸 
		while (!q.isEmpty()) {
			Node now = q.poll();
			
			if (cnt == blankCnt) {
				break;
			}
			
			for (int k = 0; k < 4; k++) {
				int x = now.x + dx[k];
				int y = now.y + dy[k];
				
				if (x >= 0 && y >= 0 && x < N && y < N) {
					if (map[x][y] != 1 && !visited[x][y]) {
						if (map[x][y] == 0) {
							cnt++;
						}
						q.add(new Node(x, y, now.time + 1));
						visited[x][y] = true;
						res = Math.max(res, now.time + 1);
					}
				}
			}
		}
		
		if (cnt < blankCnt) {
			return Integer.MAX_VALUE;
		} else {
			return res;
		}
	}
	
	private static void init() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = copyMap[i][j];
			}
		}
	}
}
