import java.util.*;
import java.io.*;

public class Main {
	Scanner action = new Scanner(System.in);
	String option;
	String con;
	
	public static void main(String[] args) throws FileNotFoundException {	
		new Main().run();
	}
		
	public void run() throws FileNotFoundException {
		
		displayOptions();
		runOption();
		
	}
	
	private void runOption() throws FileNotFoundException {
		System.out.print("Choose option (0 to exit) [1-4]: ");
		option = action.nextLine().strip();
			
		switch (option) {
			case "1":
				createRecipe();
				break;
			case "2":
				configureRecipe();
				break;
			case "3":
				searchForRecipe();
				break;
			case "4":
				displayRecipe();
				break;
			case "0":
				break;
			default:
				runOption();
		}
	}
	
	private void searchForRecipe() throws FileNotFoundException {
		System.out.print("Type search query: ");
		String search = action.nextLine();
		
		Scanner files;
		File dir = new File(".");
		for (File f : dir.listFiles()) {
			files = new Scanner(f);
			
			if (f.getName().endsWith(".dat")) {
				String listCat = files.nextLine();
				String listIng = files.nextLine();
				String noFileExt = f.getName().replace(".dat", "");
				if (noFileExt.contains(search) || listCat.contains(search) || listIng.contains(search)) {
					System.out.println(f.getName());
				}
			}
		}
	}
	
	private void createRecipe() throws FileNotFoundException {
		System.out.print("Name your recipe: ");
		String title = action.nextLine().strip();
		File recipeData = new File(title + ".dat");
		
		try {
      		recipeData.createNewFile();
    	} catch (IOException e) {}
		
		try {
			FileWriter fileWriter = new FileWriter(recipeData);
			fileWriter.write("""
				@categories
				No categories set
				@end
				
				@ingredients
				No ingredients set
				@end
				
				@rating
				Not rated yet
				@end
				
				@instructions
				No instructions written
				@end
				""");
			fileWriter.close();
		} catch (IOException e) {}
		
	}
	
	private void configureRecipe() throws FileNotFoundException {
		configurationOptions();
		whichConf();
		
	}
	
	private void whichConf() throws FileNotFoundException {
		System.out.print("What do you want to edit (0 to exit)? [1-5]: ");
		String s = action.nextLine().strip();
		
		switch (s) {
			case "0":
				break;
			case "1":
				editCat();
				break;
			case "2":
				editIng();
				break;
			case "3":
				editRating();
				break;
			case "4":
				editIns();
				break;
			case "5":
				addComment();
				break;
			default:
				whichConf();
		}
	}
	
	private void addComment() throws FileNotFoundException {
		System.out.println("Write comment (type \"done\" to finish):");
		String comment = action.nextLine();
		String nxtln = action.nextLine();
		while (!(nxtln.equals("done"))) {
			comment += "\n" + nxtln;
			nxtln = action.nextLine();
		}
		
		String newComment = newFileContent() + "\n@comment\n" + comment + "\n@end";
		writeToFile(newComment);
	}
	
	private void editRating() throws FileNotFoundException {
		System.out.print("Give recipe a rating of 1-5 out of 5: ");
		String rating = action.nextLine().strip();
		
		switch (rating) {
			case "1", "2", "3", "4", "5":
				break;
			default:
				System.out.println("Rating must be an integer between 1 and 5");
				editRating();
				return;
		}
		
		String newRating = newFileContent("@rating", rating);
		writeToFile(newRating);
	}		
	
	private String newFileContent() throws FileNotFoundException {
		Scanner input = new Scanner(new File(con + ".dat"));
		String fileContent = "";
		while (input.hasNextLine()) {
			fileContent += input.nextLine() + "\n";
		}
		return fileContent;
	}					
						
	private String newFileContent(String type, String newData) throws FileNotFoundException {		
		Scanner input = new Scanner(new File(con + ".dat"));
		String nextLine;
		String fileContent = "";
		
		while (input.hasNextLine()) {
			nextLine = input.nextLine();
			if (nextLine.equals(type)) {
				nextLine = input.nextLine();
				String oldData;
				while (!(nextLine.equals("@end"))) {
					oldData = nextLine;
					nextLine = input.nextLine();
				}
				fileContent += type + "\n" + newData + "\n";	
			}
			fileContent += nextLine + "\n";
		}
		return fileContent;
	}
		
	private void writeToFile(String fileContent) {
		try { 
			FileWriter fileWriter = new FileWriter(con + ".dat");
			fileWriter.write(fileContent);
			fileWriter.close();
		} catch (IOException e) {}
	}
	
	private void editCat() throws FileNotFoundException {
		System.out.print("Write comma-seperated categories: ");
		String cats = action.nextLine();
		String newCategories = newFileContent("@categories", cats);
		writeToFile(newCategories);
		
	}
	
	private void editIng() throws FileNotFoundException {
		System.out.print("Write comma-seperated ingredients: ");
		String ing = action.nextLine();
		String newIngredients = newFileContent("@ingredients", ing);
		writeToFile(newIngredients);
	}
	
	private void editIns() throws FileNotFoundException {
		System.out.println("Write instructions (type \"done\" to finish):");
		String instructions = action.nextLine();
		String nxtln = action.nextLine();
		while (!(nxtln.equals("done"))) {
			instructions += "\n" + nxtln;
			nxtln = action.nextLine();
		}
		
		String newInstructions = newFileContent("@instructions", instructions);
		writeToFile(newInstructions);
	}
	
	private void configurationOptions() throws FileNotFoundException {
		
		System.out.print("What recipe would you like to configure? ");
		con = action.nextLine();
		
		if (!recipeExists(con)) {
			System.out.println("Recipe doesn't exist.");
			configurationOptions();
			return;
		}
		
		System.out.print("""
			
				You can configure categories, ingredients, rating, instructions and add comments.
				
					1) categories
					2) ingredients
					3) rating
					4) instructions
					5) add comment
					
				""");
	}
	
	private boolean recipeExists(String recipeName) throws FileNotFoundException {
		Scanner files;
		File dir = new File(".");
		for (File f : dir.listFiles()) {
			if (f.getName().equals(recipeName + ".dat")) {
				return true;
			}
		}
		return false;
	}
	
	private void displayRecipe() throws FileNotFoundException {
		System.out.print("Recipe name: ");
		String title = action.nextLine();
		
		if (!recipeExists(title)) {
			System.out.println("Recipe doesn't exist.");
			displayRecipe();
			return;
		}
		
		Scanner input = new Scanner(new File(title + ".dat"));		
		String[] categories = null;				
		String[] ingredients = null;
		String rating = "";
		String comment = "";
		String instructions = "";
		
		boolean commentsExist = false;
		String nextLine;
		while (input.hasNextLine()) {
			nextLine = input.nextLine();
            
            if (nextLine.equals("@instructions")) {
                String nextInstructionLine = input.nextLine();
                do {
                 	instructions += "|	" + nextInstructionLine + "\n";
                 	nextInstructionLine = input.nextLine();
                } while (!(nextInstructionLine.equals("@end")));
                
            } else if (nextLine.equals("@categories")) {
            	categories = input.nextLine().split(", ");
            	                
            } else if (nextLine.equals("@ingredients")) {
                ingredients = input.nextLine().split(", ");
               
            } else if (nextLine.equals("@comment")) {
            	commentsExist = true;
                String nextCommentLine = input.nextLine();
                do {
                 	comment += "		|	" + nextCommentLine + "\n";
                 	nextCommentLine = input.nextLine();
                } while (!(nextCommentLine.equals("@end")));
                comment += "\n";
            } else if (nextLine.equals("@rating")) {
            	rating = input.nextLine();
            }
        }
		
		System.out.println();
		System.out.println("	" + title);
		System.out.println("");
		System.out.println("		Categories:");
		System.out.println("			" + Arrays.toString(categories));
		System.out.println("");
		System.out.println("		Ingredients:");
		System.out.println("			" + Arrays.toString(ingredients));
		System.out.println("");
		System.out.println("		Rating (out of 5):");
		
		switch (rating) {
			case "1", "2", "3", "4", "5":
				System.out.println("			[" + toStars(rating) + "]");
				break;
			case "Not rated yet":
				System.out.println("			[" + rating + "]");
		}
		
		System.out.println("");
		System.out.print(instructions);
		System.out.println("");
		
		if (commentsExist) {
			System.out.println("		Comments:");
			System.out.print(comment);
		} else {
			System.out.println("		No comments added");
		}
		
	}
	
	private String toStars(String recipeRating) {
		int numberOfStars = Integer.parseInt(recipeRating);
		recipeRating = "";
		for (int i = 1; i <= numberOfStars; i++) {
			recipeRating += "*";
		}
		return recipeRating;
	}
	
	private void displayOptions() {
		
		System.out.print("""
			What would you like to do?
				
				1) Create new recipe.
				2) Configure recipe.
				3) Search for recipe.
				4) Display recipe.
			
			""");
		
	}
	
}
