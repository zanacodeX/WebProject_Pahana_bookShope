// File: dto/Book.java
package dto;

public class Book {
    private int id;
    private String name;
    private String author;
    private String category;
    private double price;
    private String imagePath;
   
    // Add this field

    // Constructors
    public Book() {}

    public Book(int id, String name, String author, String category,double price, String imagePath) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.category = category;
        this.price = price;
     
        this.imagePath = imagePath;
       
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	

   
}
