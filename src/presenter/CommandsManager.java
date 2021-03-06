package presenter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

/***
 * Handles Maze3d commands in MVP architecture.
 * Gets any relevant command, parses the string and executes the right model method to handle it.
 * @param model Model Type
 * @param view View Type
 * @author Itamar&Chen
 *
 */
public class CommandsManager {
	
	private Model model;
	private View view;
	HashMap<String, Command> commands;
	
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;
		this.commands = new HashMap<String, Command>();
		this.commands.put("dir", new DirCommand());
		this.commands.put("generate_maze", new GenerateMazeCommand());
		this.commands.put("display", new DisplayMazeCommand());
		this.commands.put("display_cross_section", new DisplayCrossSectionCommand());
		this.commands.put("solve_maze", new SolveMazeCommand());
		this.commands.put("display_solution", new DisplaySolutionCommand());
		this.commands.put("maze_ready", new MazeReadyCommand());
		this.commands.put("load", new LoadMazeCommand());
		this.commands.put("save", new SaveMazeCommand());
		this.commands.put("exit", new ExitCommand());
	
	}
	
	public void executeCommand(String command){
		// Parse message
		String[] splitted = command.split(" ");
		String cmd = splitted[0];			
		// Check if there is no a string to command
		if(!commands.containsKey(cmd)) { 
			this.view.displayMessage("Command doesn't exist");			
		}
		else {
			String commandArgs = "";
			if (splitted.length > 1) {
				commandArgs = command.substring(
						command.indexOf(" ") + 1);							
			}
			// Get and execute the command
			commands.get(cmd).doCommand(commandArgs);; 
		}
	}
	
	public HashMap<String, Command> getCommandsMap() {
		return this.commands;
	}
	
	class GenerateMazeCommand implements Command {

		@Override
		public void doCommand(String args) {
			String[] params = args.split(" ");
			System.out.println(params[0] +" "+params[1]);
			String name = params[0];
			int x = Integer.parseInt(params[1]);
			int y = Integer.parseInt(params[2]);
			int z = Integer.parseInt(params[3]);
			model.generateMaze(name, x, y, z);
		}		
	}
	/***
	 * Lists files in a given folder By using the File Class.
	 * @param path
	 *
	 */
	class DirCommand implements Command {

		private File file;
		
		@Override
		public void doCommand(String args) {
			file = new File(args.split(" ")[0]);
			String[] list = file.list();
			for(String str: list)
				System.out.println(str + '\t');
		}

	}
	/***
	 * Displays a maze.
	 * @param maze_name
	 *
	 */
	class DisplayMazeCommand implements Command {

		@Override
		public void doCommand(String args) {
			String name = args.split(" ")[0];
			Maze3d maze = model.getMaze(name);
			view.displayMaze(name,maze);
		}		
	}
	/***
	 * Displays a maze cross section according to the users choice.
	 * @param maze_name
	 * @param axis
	 * @param index
	 *
	 */
	class DisplayCrossSectionCommand implements Command {

		@Override
		public void doCommand(String args) {
			String[] params = args.split(" ");
			String name = params[0];
			String axis = params[1].toLowerCase();
			int num = Integer.parseInt(params[2]);
			
			switch(axis){
				case("x"):
					view.displayMessage(model.getMaze(name).getCrossSectionByX(num).toString());
				case("y"):
					view.displayMessage(model.getMaze(name).getCrossSectionByY(num).toString());
				case("z"):
					view.displayMessage(model.getMaze(name).getCrossSectionByZ(num).toString());
			}
		}

	}
	
	/***
	 * Notifies when maze is ready.
	 * @param maze_name
	 *
	 */
	class MazeReadyCommand implements Command {

		@Override
		public void doCommand(String args) {
			String[] params = args.split(" ");
			String name = params[0];
			Maze3d maze = model.getMaze(name);
			view.notifyMazeIsReady(name);
			view.displayMaze(name,maze);
		}		
	}
	/***
	 * Solve a given maze
	 * @param maze_name
	 *
	 */
	class SolveMazeCommand implements Command {
		@Override
		public void doCommand(String args) {
			model.solve(args.split(" ")[0]);
	}
	}
	/***
	 * Displays a solution of maze.
	 * @param maze_name
	 *
	 */
	class DisplaySolutionCommand implements Command {

		@Override
		public void doCommand(String args) {
			model.getSolution(args.split(" ")[0]);
			
		}
	}
	/***
	 * Loads maze from file.
	 * @param maze_name
	 * @param file_name
	 *
	 */
	class LoadMazeCommand implements Command {
		@Override
		public void doCommand(String args) {
				try {
					model.loadMaze(args);
				} catch (IOException e) {
					view.displayMessage("Invalid path.");
				}
		}

	}
	/***
	 * Saves maze to file.
	 * @param maze_name
	 * @param file_name
	 *
	 */
	class SaveMazeCommand implements Command {
		@Override
		public void doCommand(String args) {
				try {
					model.saveMaze(args);
				} catch (IOException e) {
					view.displayMessage("Invalid path.");
				}
		}

	}
	
	/***
	 * Exits CLI.
	 * @param 
	 *
	 */
	public class ExitCommand implements Command {

		@Override
		public void doCommand(String cmd) {
			model.exit();
		}

	}
}

