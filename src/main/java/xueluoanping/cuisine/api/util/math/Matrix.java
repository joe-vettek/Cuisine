package xueluoanping.cuisine.api.util.math;

import java.text.DecimalFormat;

public class Matrix {

	//初始化一个随机nxn阶矩阵
	public static int[][] initializationMatrix(int n){
		int[][] result = new int[n][n];
		for(int i = 0;i < n;i++){
			for(int j = 0;j < n;j++){
				result[i][j] = (int)(Math.random()*10); //采用随机函数随机生成1~10之间的数
			}
		}
		return result;
	}

	//蛮力法求解两个nxn和nxn阶矩阵相乘
	public static int[][] BruteForce(int[][] p,int[][] q,int n){
		int[][] result = new int[n][n];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				result[i][j] = 0;
				for(int k=0;k<n;k++){
					result[i][j] += p[i][k]*q[k][j];
				}
			}
		}
		return result;
	}

	//分治法求解两个nxn和nxn阶矩阵相乘
	public static int[][] DivideAndConquer(int[][] p,int[][] q,int n){
		int[][] result = new int[n][n];
		//当n为2时，返回矩阵相乘结果
		if(n == 2){
			result = BruteForce(p,q,n);
			return result;
		}

		//当n大于3时，采用采用分治法，递归求最终结果
		if(n > 2){
			int m = n/2;

			int[][] p1 = QuarterMatrix(p,n,1);
			int[][] p2 = QuarterMatrix(p,n,2);
			int[][] p3 = QuarterMatrix(p,n,3);
			int[][] p4 = QuarterMatrix(p,n,4);
//            System.out.println();
//            System.out.print("矩阵p1值为：");
//            PrintfMatrix(p1,m);
//            System.out.println();
//            System.out.print("矩阵p2值为：");
//            PrintfMatrix(p2,m);
//            System.out.println();
//            System.out.print("矩阵p3值为：");
//            PrintfMatrix(p3,m);
//            System.out.println();
//            System.out.print("矩阵p4值为：");
//            PrintfMatrix(p4,m);

			int[][] q1 = QuarterMatrix(q,n,1);
			int[][] q2 = QuarterMatrix(q,n,2);
			int[][] q3 = QuarterMatrix(q,n,3);
			int[][] q4 = QuarterMatrix(q,n,4);

			int[][] result1 = QuarterMatrix(result,n,1);
			int[][] result2 = QuarterMatrix(result,n,2);
			int[][] result3 = QuarterMatrix(result,n,3);
			int[][] result4 = QuarterMatrix(result,n,4);


			result1 = AddMatrix(DivideAndConquer(p1,q1,m),DivideAndConquer(p2,q3,m),m);
			result2 = AddMatrix(DivideAndConquer(p1,q2,m),DivideAndConquer(p2,q4,m),m);
			result3 = AddMatrix(DivideAndConquer(p3,q1,m),DivideAndConquer(p4,q3,m),m);
			result4 = AddMatrix(DivideAndConquer(p3,q2,m),DivideAndConquer(p4,q4,m),m);


			result = TogetherMatrix(result1,result2,result3,result4,m);
		}
		return result;
	}

	//获取矩阵的四分之一，并决定返回哪一个四分之一
	public static int[][] QuarterMatrix(int[][] p,int n,int number){
		int rows = n/2;   //行数减半
		int cols = n/2;   //列数减半
		int[][] result = new int[rows][cols];
		switch(number){
			case 1 :
			{
				// result = new int[rows][cols];
				for(int i=0;i<rows;i++){
					for(int j=0;j<cols;j++){
						result[i][j] = p[i][j];
					}
				}
				break;
			}

			case 2 :
			{
				// result = new int[rows][n-cols];
				for(int i=0;i<rows;i++){
					for(int j=0;j<n-cols;j++){
						result[i][j] = p[i][j+cols];
					}
				}
				break;
			}

			case 3 :
			{
				// result = new int[n-rows][cols];
				for(int i=0;i<n-rows;i++){
					for(int j=0;j<cols;j++){
						result[i][j] = p[i+rows][j];
					}
				}
				break;
			}

			case 4 :
			{
				// result = new int[n-rows][n-cols];
				for(int i=0;i<n-rows;i++){
					for(int j=0;j<n-cols;j++){
						result[i][j] = p[i+rows][j+cols];
					}
				}
				break;
			}

			default:
				break;
		}

		return result;
	}

	//把均分为四分之一的矩阵，聚合成一个矩阵，其中矩阵a,b,c,d分别对应原完整矩阵的四分中1、2、3、4
	public static int[][] TogetherMatrix(int[][] a,int[][] b,int[][] c,int[][] d,int n){
		int[][] result = new int[2*n][2*n];
		for(int i=0;i<2*n;i++){
			for(int j=0;j<2*n;j++){
				if(i<n){
					if(j<n){
						result[i][j] = a[i][j];
					}
					else
						result[i][j] = b[i][j-n];
				}
				else{
					if(j<n){
						result[i][j] = c[i-n][j];
					}
					else{
						result[i][j] = d[i-n][j-n];
					}
				}
			}
		}

		return result;
	}


	//求两个矩阵相加结果
	public static int[][] AddMatrix(int[][] p,int[][] q,int n){
		int[][] result = new int[n][n];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				result[i][j] = p[i][j]+q[i][j];
			}
		}
		return result;
	}

	//控制台输出矩阵
	public static void PrintfMatrix(int[][] matrix,int n){
		for(int i=0;i<n;i++){
			System.out.println();
			for(int j=0;j<n;j++){
				System.out.print("\t");
				System.out.print(matrix[i][j]);
			}
		}

	}

	public static void main(String args[]){
		int[][] p = initializationMatrix(8);
		int[][] q = initializationMatrix(8);
		System.out.print("矩阵p初始化值为：");
		PrintfMatrix(p,8);
		System.out.println();
		System.out.print("矩阵q初始化值为：");
		PrintfMatrix(q,8);

		int[][] bf_result = BruteForce(p,q,8);
		System.out.println();
		System.out.print("蛮力法计算矩阵p*q结果为：");
		PrintfMatrix(bf_result,8);

		int[][] dac_result = DivideAndConquer(p,q,8);
		System.out.println();
		System.out.print("分治法计算矩阵p*q结果为：");
		PrintfMatrix(dac_result,8);
	}

}

