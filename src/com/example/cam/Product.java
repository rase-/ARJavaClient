package com.example.cam;

public class Product {
	private String name;
	private String description;
	private int thumbsUp;
	private int thumbsDown;
	
	public Product(String name, String description, int thumbsUp, int thumbsDown) {
		super();
		this.name = name;
		this.description = description;
		this.thumbsUp = thumbsUp;
		this.thumbsDown = thumbsDown;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getThumbsUp() {
		return thumbsUp;
	}
	public void setThumbsUp(int thumbsUp) {
		this.thumbsUp = thumbsUp;
	}
	public int getThumbsDown() {
		return thumbsDown;
	}
	public void setThumbsDown(int thumbsDown) {
		this.thumbsDown = thumbsDown;
	}
}
