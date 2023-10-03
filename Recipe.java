import java.util.*;

public class Recipe {
	private String title;
	private String[] categories;
	private String[] ingredients;
	private String instructions;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getCategories() {
		return categories;
	}
	
	public void setCategories(String[] categories) {
		this.categories = categories;
	}
	
	public void setIngredients(String[] ingredients) {
		this.ingredients = ingredients;
	}
	
	public String[] getIngredients() {
		return ingredients;
	}
	
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	public String getInstructions() {
		return instructions;
	}
	
}
