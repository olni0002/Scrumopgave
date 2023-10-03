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
				No categories set
				No ingredients set
				Not rated yet
				No instructions written
				""");
			fileWriter.close();
		} catch (IOException e) {}
		
	}
	
	private void configureRecipe() throws FileNotFoundException {
		configurationOptions();
		whichConf();
		
	}
	
	private void whichConf() throws FileNotFoundException {
		String s = action.nextLine().strip();
		
		switch (s) {
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
			default:
				whichConf();
		}
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
		
		Scanner input = new Scanner(new File(con + ".dat"));
		String cat = input.nextLine();
		String ing = input.nextLine();
		String rat = input.nextLine();
		
		try { 
			FileWriter fileWriter = new FileWriter(con + ".dat");
			fileWriter.write(cat + "\n");
			fileWriter.write(ing + "\n");
			fileWriter.write(rating + "\n");
			
			while (input.hasNextLine()) {
				fileWriter.write(input.nextLine() + "\n");
			}
			
			fileWriter.close();
		} catch (IOException e) {}
		
	}
	
	private void editCat() throws FileNotFoundException {
		System.out.print("Write comma-seperated categories: ");
		String cats = action.nextLine();
		
		Scanner input = new Scanner(new File(con + ".dat"));
		String s = input.nextLine();
		
		try { 
			FileWriter fileWriter = new FileWriter(con + ".dat");
			fileWriter.write(cats + "\n");
			
			while (input.hasNextLine()) {
				fileWriter.write(input.nextLine() + "\n");
			}
			
			fileWriter.close();
		} catch (IOException e) {}
		
	}
	
	private void editIng() throws FileNotFoundException {
		System.out.print("Write comma-seperated ingredients: ");
		String ing = action.nextLine();
		Scanner input = new Scanner(new File(con + ".dat"));
		String cat = input.nextLine();
		String t = input.nextLine();
		
		try { 
			FileWriter fileWriter = new FileWriter(con + ".dat");
			fileWriter.write(cat + "\n");
			fileWriter.write(ing + "\n");
			while (input.hasNextLine()) {
				fileWriter.write(input.nextLine() + "\n");
			}
			
			fileWriter.close();
		} catch (IOException e) {}
	}
	
	private void editIns() throws FileNotFoundException {
		System.out.println("Write instructions (type \"done\" to finish):");
		String instructions = "";
		String nxtln = action.nextLine();
		while (!(nxtln.equals("done"))) {
			instructions += nxtln + "\n";
			nxtln = action.nextLine();
		}
		
		
		Scanner input = new Scanner(new File(con + ".dat"));
		String cat = input.nextLine();
		String t = input.nextLine();
		
		try { 
			FileWriter fileWriter = new FileWriter(con + ".dat");
			fileWriter.write(cat + "\n" + t + "\n" + instructions);
			
			fileWriter.close();
		} catch (IOException e) {}
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
			
				You can configure categories, ingredients, rating and instructions.
				
					1) categories
					2) ingredients
					3) rating
					4) instructions
					
				""");
				System.out.print("What do you want to edit (0 to exit)? [1-4]: ");
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
					
		Recipe recipe = new Recipe();
		Scanner input = new Scanner(new File(title + ".dat"));		
		String[] categories = input.nextLine().split(", ");				
		String[] ingredients = input.nextLine().split(", ");
		String rating = input.nextLine();
		
		String instructions = "";
		while (input.hasNextLine()) {
			instructions += "|	" + input.nextLine() + "\n";
		}
		
		recipe.setTitle(title);
		recipe.setCategories(categories);
		recipe.setIngredients(ingredients);
		recipe.setInstructions(instructions);
		recipe.setRating(rating);
		System.out.println();
		System.out.println("|	" + recipe.getTitle());
		System.out.println("|");
		System.out.println("|		Categories:");
		System.out.println("|			" + Arrays.toString(recipe.getCategories()));
		System.out.println("|");
		System.out.println("|		Ingredients:");
		System.out.println("|			" + Arrays.toString(recipe.getIngredients()));
		System.out.println("|");
		System.out.println("|		Rating (out of 5):");
		System.out.println("|			[" + toStars(recipe.getRating()) + "]");
		System.out.println("|");
		System.out.println(recipe.getInstructions());
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
