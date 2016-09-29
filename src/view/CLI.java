package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;

/***
 * CLI in MVP architecture.
 * @author Itamar Mizrahi&Chen Erlich
 *
 */
public class CLI extends Observable implements View  {
	private BufferedReader in;
	private PrintWriter out;	
	private MyView view;
	
	public CLI(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
		this.view = new MyView(in, out);
	}
	
	private void printMenu() {
		out.print("Choose command: ");
		this.view.displayMessage("\tdir <path>");
		this.view.displayMessage("\tgenerate_maze <name> <params>");
		this.view.displayMessage("\tdisplay <name>");
		this.view.displayMessage("\tdisplay_cross_section <name> <axis> <index>");
		this.view.displayMessage("\tsave_maze <name> <file_name>");
		this.view.displayMessage("\tload_maze <file_name> <name>");
		this.view.displayMessage("\tsolve <name> <algorithm>");
		this.view.displayMessage("\tdisplay_solution <name>");
		this.view.displayMessage("\texit");
		out.flush();
	}
	
	public void start() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
				
					printMenu();
					try {
						String commandLine = in.readLine();
						sendCommand(commandLine);
						
						if (commandLine.equals("exit"))
							break;
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}			
		});
		thread.start();		
	}

	@Override
	public void notifyMazeIsReady(String name) {
		this.view.notifyMazeIsReady(name);
	}

	@Override
	public void displayMaze(Maze3d maze) {
		this.view.displayMaze(maze);
	}

	@Override
	public void displayMessage(String msg) {
		this.view.displayMessage(msg);
	}

	@Override
	public void sendCommand(String arg) {
		setChanged();
		notifyObservers(arg);
	}
	
}
