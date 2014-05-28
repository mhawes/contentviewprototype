package com.mystack.contentviewprototype;

public class ContentItem {
	private String mod;
	
	// TODO remove resource ID used for prototype.
	private int resId;
	
	private ItemStatus status = ItemStatus.NOT_RATED;
	
	public ContentItem(String contentMod, int id)
	{
		mod = contentMod;
		resId = id;
		
	}
	
	public int getId()
	{
		return resId;
	}

	public void like() {
		status = ItemStatus.LIKED;
	}

	public void dislike() {
		status = ItemStatus.DISLIKED;
	}
	
	public ItemStatus getContentStatus()
	{
		return status;
	}

	public String getModifier() {
		return mod;
	}
}
