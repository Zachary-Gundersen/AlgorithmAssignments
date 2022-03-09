/*
This is my, Zachary Gundersen's implemntation of a program that takes in a set of lines and finds the most effecient path through them
This is a Dynamic Programming implementation
Inputs
On the first line of console input, your program will receive an integer n, the number of production lines in the range [2..5],
then an integer s, the number of production steps in the range [1..600].
The next n lines will provide the step costs for manufacturing line.  Each line will provide s integers in the range [1..100],
The last line will provide the switching costs for switching between lines. 
This line will provide s-1 integers (because switching is only possible between steps) in the range [1..30].
Example, with spaces added to aid readability:

3 5
18   11    6   30   27
29    7   24    6    6
 8   22    7   23   21
5 7 6 8
Your program should not be sensitive to excess spaces (keep it simple), but there will not be excess spaces in the tests.

Output
Your program should first print out one integer, the minimum time required to produce a processor.

Next, your program should print out s integers that indicate which line should be used for each step. 
For example, if an optimal solution uses line 2, then line 1, then line 3, your program would print "2 1 3".

Example solution for above inputs:

48
3 1 1 2 2
*/
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


