package com.mystack.contentviewprototype;

import java.util.ArrayList;

public class ContentModel {
	// eager initialised instance
	private static final ContentModel instance = new ContentModel();
	
	private ContentItemController conControl = ContentItemController.getInstance();
	private ContentModifierCollection modCol = ContentModifierCollection.getInstance();
	
	private ContentModel()
	{
		// TODO this is prototype code
		// Pre-populate the list of content modifiers
		ArrayList<String> toAdd = new ArrayList<String>();
		toAdd.add("food");
		toAdd.add("cat");
		toAdd.add("football");
		toAdd.add("beer");
		modCol.addContentModifiers(toAdd);
	}
	
	public static ContentModel getInstance()
	{
		return instance;
	}

	// public APIs
	/**
	 * 
	 * @return The id of the next content resource
	 * @throws NoModifierException
	 */
	public int getNextContent() throws NoModifierException
	{
		ContentItem content = conControl.getNextItem();
		
		// TODO prototype just gets the resource ID 
		return content.getId();
	}
	
	public int getPreviousContent()
	{
		ContentItem content = conControl.getPrevItem();
		
		// TODO prototype just gets the resource ID 
		return content.getId();
	}
	
	public void likeCurrentContent()
	{
		conControl.likeCurrentContent();
	}
	
	public void dislikeCurrentContent()
	{
		conControl.dislikeCurrentContent();
	}
	
	// TODO This is a prototype debug method
	public String toString()
	{
		String ret = "";
		
		ret += modCol.toString();
		
		ret += conControl.toString();
		
		return ret;
	}
}
