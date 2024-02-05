package baekjoon;

import java.io.*;
import java.util.*;

public class p2629_양팔저울 {
	static int weightCount, circleCount;
    static int[] weightArr, circleArr;
    static boolean[][] dp;

	public static void main(String[] args) throws IOException {	
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		weightCount = Integer.parseInt(br.readLine());
        dp = new boolean[weightCount + 1][40001];
        weightArr = new int[weightCount];
 
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < weightCount; i++) {
        	weightArr[i] = Integer.parseInt(st.nextToken());
        }
 
        circleCount = Integer.parseInt(br.readLine());
        circleArr = new int[circleCount];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < circleCount; i++) {
        	circleArr[i] = Integer.parseInt(st.nextToken());
        }
        
        solve(0, 0);
 
        for (int j = 0; j < circleCount; j++) {
            if (dp[weightCount][circleArr[j]]){
                System.out.print("Y ");
            } else{
                System.out.print("N ");
            }
        }
	}

	private static void solve(int cnt, int sum) {
        if (dp[cnt][sum]) {
            return;
        }
        
        dp[cnt][sum] = true;
 
        if (cnt == weightCount) {
            return;
        }
 
        solve(cnt + 1, sum + weightArr[cnt]);
        solve(cnt + 1, sum);
        solve(cnt + 1, Math.abs(sum - weightArr[cnt]));
    }

}
