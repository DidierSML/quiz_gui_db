package database;

//Data model to represent data from the Category table
public class Category {

    private int category_Id;
    private String categoryName;

    //Constructor with Parameters
    public Category(int category_Id, String categoryName) {
        this.category_Id = category_Id;
        this.categoryName = categoryName;
    }

    //Getters
    public int getCategory_Id() {
        return category_Id;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
