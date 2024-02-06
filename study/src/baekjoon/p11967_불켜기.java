package baekjoon;

import java.io.*;
import java.util.*;

public class p11967_불켜기 {
	static int[] dx = { 1, 0, -1, 0 };
	static int[] dy = { 0, 1, 0, -1 };
	static int N, M;
	static ArrayList<Node>[][] rooms; // 각 방에 있는 스위치 저장
	static boolean[][] light; // 방의 불 켜짐 여부
	
	static class Node {
		int x;
		int y;
		
		public Node (int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public static void main(String[] args) throws IOException {	
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		rooms = new ArrayList[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				rooms[i][j] = new ArrayList<>();
			}
		}
		light = new boolean[N][N];
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			rooms[x][y].add(new Node(a, b));
		}
		
		System.out.println(BFS() + 1);
	}

	private static int BFS() {
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(0, 0));
		light[0][0] = true;
		
		boolean[][] visited = new boolean[N][N];
		visited[0][0] = true;
		
		int count = 0;
		boolean turnOn = false;
		
		while (!q.isEmpty()) {
			Node now = q.poll();
			
			// 현재 방에 스위치로 켤 수 있는 모든 방의 불 켜기
			for (Node room : rooms[now.x][now.y]) {
				if (!light[room.x][room.y]) {
					light[room.x][room.y] = true;
					count++;
					turnOn = true;
				}
			}
			
			// 상하좌우 중에서 갈 수 있는 곳(불이 켜져 있고 방문하지 않은 곳) 큐에 넣기
			for (int i = 0; i < 4; i++) {
				int x = now.x + dx[i];
				int y = now.y + dy[i];
				
				if (x >= 0 && y >= 0 && x < N && y < N) {
					if (light[x][y] && !visited[x][y]) {
						q.add(new Node(x, y));
						visited[x][y] = true;
					}
				}
			}
		}
		
		// 혹시 불이 나중에 켜져서 방문하지 못했을 수도 있으니 turn 플래그가 true이면 BFS 한 번 더 돌기
		if (turnOn) {
			count += BFS();
		}
		
		return count;
	}

}
