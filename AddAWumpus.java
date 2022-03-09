/*
* This is my, Zachary Gundersen, program that takes in a maze and finds the minimum amount of treasure(shown by an integer) a player(p) can get when adding one aditional monster(m)
*Inputs
*Your program will receive an integer number of rows and columns followed by one string of non-whitespace characters for each row of the maze. 
*Each string will be on its own line (terminated with a line break).  The maze may be as small as 5x5 or as large as 100x100.
*Example:
*7 9
*#########
*#.p..3..#
*#.......#
*#.####..#
*#1m#.8..#
*#9......#
*#########
*Output
*Your program should output three integers (and no other text):  The coordinates of any best location to place a monster (a row followed by a column, 0-based, starting in the upper left), followed by an integer indicating how much treasure the player will collect (when the additional monster is ideally placed). 
*Example 
*2 4
*1
*
*/
package WumpusWorld;

import java.util.ArrayList;

import java.util.Scanner;
import java.util.Stack;

public class AddAWumpus {
	// global variables to decrease time complexity
	static char[][] Maze;
	static boolean[][] Visited;
	static ArrayList<Vertex> visit = new ArrayList<Vertex>();
	static int rows;
	static int cols;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		// this chunk of code gets all the information from the console
		rows = scan.nextInt();
		cols = scan.nextInt();
		scan.nextLine();
		Maze = new char[rows][cols];

		Vertex player = new Vertex();
		for (int i = 0; i < rows; i++) {

			String line = scan.nextLine();
			if (line.contains("p")) {
				player.setY(line.indexOf('p'));
				player.setX(i);

			}
			char[] lineArray = line.toCharArray();

			Maze[i] = lineArray;
		}
		// This is used to set the amount of treasure the player finds without an
		// aditional monster, and it populates a global variable
		// list that I use to know when placing a monster will do anything
		int minTreasure = WFT(player, Integer.MAX_VALUE, true) + 1;
		// the temp vertex is used for the current location, and the v
		Vertex Answervertex = new Vertex(0, 0);

		// calls a Whatever first search on the maze with different monster placements.
		// keeps track of a minimum treasure amount and its corresponding vertex

		ArrayList<Vertex> visited2 = visit;

		for (Vertex it : visited2) {

			if (Maze[it.getX()][it.getY()] == '.' && !whatIsItNextTo(it).contains("p")) {
				Maze[it.getX()][it.getY()] = 'm';
				int temptreasure = WFT(player, minTreasure, false);
				if (temptreasure < minTreasure) {
					minTreasure = temptreasure;
					Answervertex.setX(it.getX());
					Answervertex.setY(it.getY());
				}
				Maze[it.getX()][it.getY()] = '.';

			}
		}

		System.out.println(Answervertex.getX() + " " + Answervertex.getY());
		System.out.println(minTreasure);
		scan.close();

	}

	/*
	 * This is a helper method that takes a starting vertex, a amount of treasure
	 * that after it is exceeded it stops looking, and a boolean that informs the
	 * method if this is the first time it is called.
	 */
	public static int WFT(Vertex PlayerVert, int minTreasure, boolean first) {
		Stack<Vertex> traversal = new Stack<Vertex>();
		int treasure = 0;
		Visited = new boolean[rows][cols];
		traversal.push(PlayerVert);
		Visited[PlayerVert.getX()][PlayerVert.getY()] = true;
		while (!traversal.isEmpty()) {
			Vertex current = traversal.pop();

			Visited[current.getX()][current.getY()] = true;
			
			//this method should only be used if we are using the WFT to populate potential monster placements.
			if (first && Maze[current.getX()][current.getY()] == '.') {
				visit.add(current);
			}

			//gets all the characters adjacent to the current one
			String nextTo = whatIsItNextTo(current);
			if (!nextTo.contains("m")) {

				for (int i = 0; i < nextTo.length(); i++) {
					//for each character if it is not a wall or a monster then it adds it to the queue and marks it as visited.
					if (nextTo.charAt(i) != '#') {
						if (i == 0) {
							Vertex v = current.upper();
							if (!Visited[v.getX()][v.getY()]) {
								traversal.push(v);

								Visited[v.getX()][v.getY()] = true;
							}
						} else if (i == 1) {
							Vertex v = current.leftside();
							if (!Visited[v.getX()][v.getY()]) {
								traversal.push(v);

								Visited[v.getX()][v.getY()] = true;
							}
						} else if (i == 2) {
							Vertex v = current.down();
							if (!Visited[v.getX()][v.getY()]) {
								traversal.push(v);

								Visited[v.getX()][v.getY()] = true;
							}
						} else {
							Vertex v = current.rightside();
							if (!Visited[v.getX()][v.getY()]) {
								traversal.push(v);

								Visited[v.getX()][v.getY()] = true;
							}
						}
					}
				}

			}

			char in = Maze[current.getX()][current.getY()];
			//we add treasure to the total if found
			if (Character.isDigit(in)) {
				treasure += Integer.parseInt(String.valueOf(in));
			}
			//if treasure exceeds mintreasure we stop searching.
			if (treasure >= minTreasure) {
				return treasure;
			}

		}

		return treasure;
	}
	/*
	 * 
	 * a helper method that gets the characters adjacent to a specific vertex
	 */
	public static String whatIsItNextTo(Vertex current) {

		String surround = "";
		int x = current.getX();
		int y = current.getY();

		surround += Maze[x - 1][y];
		surround += Maze[x][y - 1];
		surround += Maze[x + 1][y];
		surround += Maze[x][y + 1];

		return surround;

	}

}
/*
 * a class that represents a coordinate pair
 */
class Vertex {
	private int xCord;
	private int yCord;

	Vertex() {

	}

	Vertex(int x, int y) {
		xCord = x;
		yCord = y;
	}

	Vertex upper() {

		return new Vertex(xCord - 1, yCord);
	}

	Vertex rightside() {
		return new Vertex(xCord, yCord + 1);
	}

	Vertex leftside() {
		return new Vertex(xCord, yCord - 1);

	}

	Vertex down() {
		return new Vertex(xCord + 1, yCord);

	}

	int getX() {
		return xCord;
	}

	int getY() {
		return yCord;
	}

	void setX(int x) {

		xCord = x;
	}

	void setY(int y) {
		yCord = y;
	}

	@Override
	public boolean equals(Object v) {
		if ((v instanceof Vertex)) {
			if (xCord == ((Vertex) v).getX() & yCord == ((Vertex) v).getY()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		return xCord + yCord;

	}

}
