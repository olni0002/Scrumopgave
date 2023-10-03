import java.util.*;

public class Recipe {
	private String title;
	private String[] categories;
	private String[] ingredients;
	private String instructions;
	private String rating;

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
	
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public String getRating() {
		return rating;
	}
	
}
