package com.example.cam;

import java.util.ArrayList;

public class RawProduct {
	private String name;
	private String description;
	private byte[] barcodeImage;
	private byte[] logoImage;
	private ArrayList<byte[]> textImages;
	
	public RawProduct() {
		
	}
	
	public void setBarcodeImage(byte[] barcodeImage) {
		this.barcodeImage = barcodeImage;
	}
	
	public void setLogoImage(byte[] logoImage) {
		this.logoImage = logoImage;
	}
	
	public void addTextImage(byte[] textImage) {
		this.textImages.add(textImage);
	}

	public byte[] getBarcodeImage() {
		return barcodeImage;
	}

	public byte[] getLogoImage() {
		return logoImage;
	}

	public ArrayList<byte[]> getTextImages() {
		return textImages;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toJSON() {
		return "{ name: " + name + ", description: " + description + "}";
	}
}
