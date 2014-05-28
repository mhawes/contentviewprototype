package com.mystack.contentviewprototype;

import java.util.LinkedList;

/**
 * Controls the retrieval of content items.
 * 
 * Stores a history for going back to previous content
 * 
 */
public class ContentItemController {
	private static final ContentItemController instance = new ContentItemController();
	private static final int HISTORY_MAX = 10;
	private static LinkedList<ContentItem> history;
	private int historyIndex = -1;

	// values which a content modifier changes by on actions
	private static final int LIKE_INCREMENT = 1;
	private static final int DISLIKE_DECREMENT = 10;
	
	private ContentModifierCollection modCol;
	
	public static ContentItemController getInstance() {
		return instance;
	}
	
	private ContentItemController()
	{
		history = new LinkedList<ContentItem>();
		modCol = ContentModifierCollection.getInstance();
	}

	/**
	 * Returns either a new item or the next item in the history list
	 * 
	 * 
	 * @return the next item
	 * @throws NoModifierException 
	 */
	public ContentItem getNextItem() throws NoModifierException
	{
		// if the iterator has not got a next item then add  a new one to the end of the list
		if(historyIndex + 1 == history.size())
		{
			
			try {
				// add with next random modifier
				String modifier = modCol.getNextModifier();
			
				// TODO this is prototype code
				// generate new content item
				switch(modifier)
				{
				case "beer":
					history.addLast(new ContentItem(modifier, R.drawable.beer));
					break;
				case "food":
					history.addLast(new ContentItem(modifier, R.drawable.food));
					break;
				case "cat":
					history.addLast(new ContentItem(modifier, R.drawable.cat));
					break;
				case "football":
					history.addLast(new ContentItem(modifier, R.drawable.football));
					break;
				}
				
				
				if(history.size() > HISTORY_MAX)
				{
					history.removeFirst();
				}
				else
				{
					historyIndex++;
				}
			}
			catch(NoModifierException e)
			{
				throw e;
			}
		}
		else
		{
			historyIndex++;
		}

		// return the next in the iterator
		return history.get(historyIndex);
	}
	
	/**
	 * Returns the previous item from the history
	 * 
	 * 
	 * @return the next item
	 */
	public ContentItem getPrevItem()
	{
		// TODO: handle end of list correctly
		if(historyIndex > 0)
		{
			historyIndex--;
		}
		return history.get(historyIndex);
	}
	
	
	// liked/disliked boolean access
	public void dislikeCurrentContent()
	{
		// if not already disliked, otherwise ignore
		if(!isCurrentContentDisliked())
		{
			int toDec = DISLIKE_DECREMENT;
			
			if(isCurrentContentLiked())
			{
				toDec += LIKE_INCREMENT;
			}
			
			ContentItem ci = getCurrentItem();
			
			modCol.incrementModifier(ci.getModifier(), -toDec);
			
			ci.dislike();
		}
	}
	
	public void likeCurrentContent()
	{
		// if not already liked, otherwise ignore
		if(!isCurrentContentLiked())
		{
			int toInc = LIKE_INCREMENT;
			
			if(isCurrentContentDisliked())
			{
				toInc += DISLIKE_DECREMENT;
			}
			
			ContentItem ci = getCurrentItem();
			
			modCol.incrementModifier(ci.getModifier(), toInc);
			
			ci.like();
		}
	}
	
	private boolean isCurrentContentLiked() {
		if(history.get(historyIndex).getContentStatus() == ItemStatus.LIKED)
		{
			return true;
		}
		return false;
	}
	
	private boolean isCurrentContentDisliked() {
		if(history.get(historyIndex).getContentStatus() == ItemStatus.DISLIKED)
		{
			return true;
		}
		return false;
	}
	
	private ContentItem getCurrentItem()
	{
		return history.get(historyIndex);
	}
	
	// TODO prototype debug methods
	public String toString()
	{
		ContentItem ci = getCurrentItem();
		
		String ret = "modifier: " + ci.getModifier() + " status: ";
		
		switch(ci.getContentStatus())
		{
		case DISLIKED:
			ret += "DISLIKED\n";
			break;
		case LIKED:
			ret += "LIKED\n";
			break;
		case NOT_RATED:
			ret += "NOT RATED\n";
			break;
		default:
			break;
		}

		
		ret +="index: " + historyIndex + "\n";
		
		int i = 0;
		for(ContentItem item : history)
		{
			ret += i++ + ":" + item.getModifier() + "->";
		}
		
		return ret;
	}
	
	public int getIndex()
	{
		return historyIndex;
	}
}
