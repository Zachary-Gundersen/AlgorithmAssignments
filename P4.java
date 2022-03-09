package programmingset;

import java.util.Scanner;

public class P4 {

	public static void main(String[] args) {

        int cols;
        int rows;
        Scanner scan = new Scanner(System.in);
           
        rows = scan.nextInt();
        cols = scan.nextInt();
        int[][] vals = new int[rows][cols];
        int[][] cumulative = new int[rows][cols];
        String[][] path = new String[rows][cols];
        int[] switchcost = new int[cols - 1];
      
        for (int i = 0; i < rows; i++)
        {
          
            for (int j = 0; j < cols; j++)
            {
                vals[i][j] = scan.nextInt();
            }
        }
        
        for (int i = 0; i < cols-1; i++)
        {
            switchcost[i] = scan.nextInt();
        }

        for (int j = 0; j < rows; j++)
        {
            cumulative[j][0] = vals[j][0];
            path[j][0] = Integer.toString(j+1);
        }

        for(int j = 1; j < cols; j++)
        {
        for(int i = 0; i < rows; i++)
        {
            
                int minindex = i;
                int min = cumulative[i][j-1];
                for(int z = 0; z < rows; z++)
                {
                    if(z == i)
                    {
                        if(min > cumulative[z][j-1])
                        {
                            minindex = z;
                            min = cumulative[z][j-1];
                        }
                    }
                    else
                    {
                        if(min > cumulative[z][j-1] + switchcost[j - 1])
                        {
                            minindex = z;
                            min = cumulative[z][j-1] + switchcost[j-1];
                        }
                    }
                }
                cumulative[i][j] = min + vals[i][j];
                path[i][j] = path[minindex][j-1] + " " + Integer.toString(i+1);
            }

        }
        int truemin = cumulative[0][cols - 1];
        int index = 0;
        for(int i = 0; i < rows; i++)
        {
            if (truemin > cumulative[i][cols - 1])
            {
                truemin = cumulative[i][cols - 1];
                index = i;
            }
        }
        System.out.println(truemin);
        System.out.println(path[index][cols-1]);
        
    }
	}


