package baekjoon;

import java.io.*;
import java.util.*;

public class p17135_캐슬디팬스 {
	static int N, M, D;
    static int[][] map;
    static int[][] copyMap;
    static ArrayList<Integer> archer = new ArrayList<>();
    static int result;
 
    public static void main(String[] args) throws IOException {
    	System.setIn(new FileInputStream("input.txt"));
    	
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 행의 수 
        M = Integer.parseInt(st.nextToken()); // 열의 수 
        D = Integer.parseInt(st.nextToken()); // 궁수 공격 제한거리 
 
        map = new int[N + 1][M + 1];
        copyMap = new int[N + 1][M + 1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                copyMap[i][j] = map[i][j];
            }
        }
 
        result = 0;
        Combination(1, M, 3); // 조합을 이용하여 궁수의 x좌표를 뽑아냄
 
        System.out.println(result);
    }
    
    public static void Combination (int start, int n, int r) {
        if (r == 0) {
        	Init();
            Attack();
            return;
        }
 
        for (int i = start; i <= n; i++) {
            archer.add(i);
            Combination(i + 1, n, r - 1);
            archer.remove(archer.size() - 1);
        }
    }
 
    public static void Attack() {
        int count = 0;
 
        for (int n = 1; n <= N; n++) {
            boolean[][] visited = new boolean[N + 1][M + 1];
            
            for (int k = 0; k < archer.size(); k++) {
                int temp = archer.get(k); // 궁수의 x좌표
                int minD = Integer.MAX_VALUE; // 최소 거리
                int minX = Integer.MAX_VALUE; // 최소 거리의 x좌표
                int minY = Integer.MAX_VALUE; // 최소 거리의 y좌표
 
                for (int i = 1; i <= N; i++) {
                    for (int j = 1; j <= M; j++) {
                    	
                    	// 적이 있을 경우
                        if (map[i][j] == 1) {
                            if (minD >= distance(i, j, N + 1, temp)) { 
                                if (minD > distance(i, j, N + 1, temp)) { // 최소 거리보다 더 짧은 거리일 경우 
                                    minD = distance(i, j, N + 1, temp);
                                    minX = i;
                                    minY = j;
                                } else { // 최소 거리와 같은 거리일 경우 > 가장 왼쪽에 있는 적을 찾기 위해 y좌표 확인 
                                    if (minY > j) { 
                                        minX = i;
                                        minY = j;
                                    }
                                }
                            }
                        }
                        
                    }
                }
 
                // 최소 거리가 궁수 공격 제한거리보다 작을 경우 
                // 궁수가 같은 적을 공격할 수도 있기 때문에 바로 map[minX][minY] = 0하지 않고 좌표에 visited 처리 
                if (minD <= D) {
                    visited[minX][minY] = true;
                }
            }
 
            // visited가 true인 좌표에 적을 제외 
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= M; j++) {
                    if (visited[i][j]) {
                        map[i][j] = 0;
                        count++;
                    }
                }
            }
 
            // 적은 아래로 한 칸 이동하며, 성이 있는 칸으로 이동한 경우에는 게임에서 제외
            for (int i = N; i >= 1; i--) {
                for (int j = 1; j <= M; j++) {
                    map[i][j] = map[i - 1][j];
                }
            }
        }
 
        result = Math.max(result, count);
    }
    
    public static void Init() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                map[i][j] = copyMap[i][j];
            }
        }
    }
    
    public static int distance(int r1, int c1, int r2, int c2) {
    	// 격자판의 두 위치 (r1, c1), (r2, c2)의 거리 |r1-r2| + |c1-c2|
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);
    }

}
