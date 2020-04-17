package com.anarq.core;

/* 
	Song
		Contains information about an image
	
	Author(s):
		Nick
*/
public class Image {
	private final String url;
	private final int height;
	private final int width;
	
	/* Constructs a new Image class */

	public Image(String url, int height, int width) {
		
        this.url = url;
        this.height = height;
        this.width = width;
    }
    
    /* Returns the url of the image */
	public String getURL() {
		
		return url;
		
	}
	
	/* Returns the height of the image */
	public int getHeight() {
		
		return height;
		
	}
	
	/* Returns the width of the image */
	public int getWidth() {
		
		return width;
		
	}
}