package baekjoon;

import java.io.*;
import java.util.*;

public class p19238_스타트택시 {
	static int[] dx = { 1, -1, 0, 0 };
	static int[] dy = { 0, 0, 1, -1 };
	static int N, M, F;
	static int[][] map;
	static Taxi taxi;
	static ArrayList<Position> destination;
	
	static class Taxi {
		int x, y, num;
		
		public Taxi(int x, int y, int num) {
			this.x = x;
			this.y = y;
			this.num = num;
		}
	}
	
	static class Position implements Comparable<Position> {
		int x, y, d;
		
		public Position(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
		
		@Override
		public int compareTo(Position o) { // 정렬조건 : 거리 > 행 번호 > 열 번호  
			if (this.d == o.d) {
				if (this.x == o.x) {
					return this.y - o.y;
				} else {
					return this.x - o.x;
				}
			}
			return this.d - o.d;
		}
		
	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); 
		M = Integer.parseInt(st.nextToken()); // 목표 승객 수 
		F = Integer.parseInt(st.nextToken()); // 초기 연료의 양 
		
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()); // 0-빈칸, 1-벽 
				if (map[i][j] == 1) {
					map[i][j] = -1; // 벽을 -1로 저장하기 
				}
			}
		}
		
		// 처음 택시의 위치 
		st = new StringTokenizer(br.readLine());
		int x = Integer.parseInt(st.nextToken()) - 1;
		int y = Integer.parseInt(st.nextToken()) - 1;
		taxi = new Taxi(x, y, 0);
		
		// 승객의 출발지와 목적지 
		destination = new ArrayList<>();
		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine());
			int sx = Integer.parseInt(st.nextToken()) - 1;
			int sy = Integer.parseInt(st.nextToken()) - 1;
			int ex = Integer.parseInt(st.nextToken()) - 1;
			int ey = Integer.parseInt(st.nextToken()) - 1;
			map[sx][sy] = i; // 지도에 승객의 출발지점을 승객번호로 저장 
			destination.add(new Position(ex, ey, 0)); // 리스트에 승객번호 순서대로 목적지 저장 
		}
		
		int count = 0;
		while (count < M) {
			// 가장 가까운 승객을 찾아 태우기 
			int distance1 = findPassenger();
			if (F - distance1 <= 0 || distance1 == -1) {
				// 승객을 태우면서 연료가 0이하거나 승객을 찾지 못할 경우 
				break;
			}
			// 가장 가까운 경로로 이동 시키기 
			int distance2 = findDestination();
			if (F - (distance1 + distance2) < 0 || distance2 == -1) {
				// 승객을 목적지에 데려다주기 전에 연료가 바닥나거나 목적지로 이동할 수 없는 경우 
				break;
			}
			
			// 승객 찾아서 목적지까지 잘 이동시킨 경우 
			F = F - distance1 + distance2;
			count++;
		}
		
		if (count == M) {
			System.out.println(F);
		} else {
			System.out.println(-1);
		}
	}

	private static int findPassenger() {
		PriorityQueue<Position> pq = new PriorityQueue<>();
		pq.add(new Position(taxi.x, taxi.y, 0));
		
		boolean[][] visited = new boolean[N][N];
		visited[taxi.x][taxi.y] = true;
		
		while (!pq.isEmpty()) {
			Position now = pq.poll();
			
			if (map[now.x][now.y] > 0) { // 현재 위치에 승객의 번호가 저장되어 있는 경우 
				taxi = new Taxi(now.x, now.y, map[now.x][now.y]); // taxi에 현재위치와 승객번호 저장 
				map[now.x][now.y] = 0; // 지도 빈 곳으로 표시 
				return now.d;
			}
			
			for (int k = 0; k < 4; k++) {
				int x = now.x + dx[k];
				int y = now.y + dy[k];
				
				if (x >= 0 && y >= 0 && x < N && y < N) {
					if (map[x][y] != -1 && !visited[x][y]) {
						pq.add(new Position(x, y, now.d + 1));
						visited[x][y] = true;
					}
				}
			}
		}
		
		return -1;
	}

	private static int findDestination() {
		PriorityQueue<Position> pq = new PriorityQueue<>();
		pq.add(new Position(taxi.x, taxi.y, 0));
		
		boolean[][] visited = new boolean[N][N];
		visited[taxi.x][taxi.y] = true;
		
		// 현재 승객번호를 가지고 리스트에서 목적지 가져오기 
		int num = taxi.num;
		int end_x = destination.get(num - 1).x;
		int end_y = destination.get(num - 1).y;
		
		while (!pq.isEmpty()) {
			Position now = pq.poll();
			
			if (now.x == end_x && now.y == end_y) { // 현재 위치가 목적지인 경우 
				taxi = new Taxi(now.x, now.y, 0); // taxi에 현재위치 저장, 승객 초기화 
				return now.d;
			}
			
			for (int k = 0; k < 4; k++) {
				int x = now.x + dx[k];
				int y = now.y + dy[k];
				
				if (x >= 0 && y >= 0 && x < N && y < N) {
					if (map[x][y] != -1 && !visited[x][y]) {
						pq.add(new Position(x, y, now.d + 1));
						visited[x][y] = true;
					}
				}
			}
		}
		
		return -1;
	}
}
