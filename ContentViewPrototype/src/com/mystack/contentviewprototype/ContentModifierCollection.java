package com.mystack.contentviewprototype;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

public class ContentModifierCollection{
	private static final ContentModifierCollection instance = new ContentModifierCollection();
	
	// List sorted by highest score first. Easier to pick 
	LinkedList<ContentModifier> col;
	
	public static ContentModifierCollection getInstance() {
		return instance;
	}
	
	private ContentModifierCollection()
	{
		col = new LinkedList<ContentModifier>();
	}
	
	public void addContentModifiers(Collection<String> toAdd)
	{
		for(String mod : toAdd)
		{
			// filter out any null strings
			if(mod != null)
			{
				// TODO shuffle order so initial random selection is fair.
				col.add( new ContentModifier(mod));
			}
		}
	}
	
	public int getScoreByName(String toGet) throws NoModifierException
	{
		for(ContentModifier mod : col)
		{
			if(mod.getModifier().equals(toGet))
			{
				return mod.getScore();
			}
		}
		throw new NoModifierException();
	}
	
	/**
	 * Get the next modifier to be searched for content.
	 * Randomly selected from list sorted by score. First in the list has more chance
	 * of 
	 * 
	 * @return
	 * @throws NoModifierException 
	 */
	public String getNextModifier() throws NoModifierException 
	{
		ContentModifier cmod = null;
		Random ran = new Random();
		int accu = 0;
		
		// accumulate all scores
		for(ContentModifier tempCmod : col)
		{
			accu += tempCmod.getScore();
		}
		
		// weighted random selection
		while(accu >= 0)
		{
			cmod = col.get(ran.nextInt(col.size()));
			accu -= cmod.getScore();
			
			if(accu <= 0)
			{
				return cmod.getModifier();
			}
		}
		throw new NoModifierException();
	}
	
	/**
	 * Increment modifier by an amount. Give negative for decrementing.
	 * 
	 * @param mod
	 * @param value
	 */
	public void incrementModifier(String mod, int value)
	{
		ContentModifier cmod = null;
		try {
			cmod = getModifierByName(mod);
			cmod.incrementScore(value);
		} catch (NoModifierException e) {
			// no modifier exists of name mod
		} catch (ScoreZeroException e) {
			// the modifer has had its score set to 0
			// TODO: check it isnt in the history then remove
		}
	}
	
	private ContentModifier getModifierByName(String mod) throws NoModifierException
	{
		for(ContentModifier cmod : col)
		{
			if(cmod.getModifier().equals(mod))
			{
				return cmod;
			}
		}
		// throw exception when no such modifier exists
		throw new NoModifierException();
	}
	
	// TODO This is a prototype runtime debug method
	public String toString()
	{
		String ret = "";
		
		for(ContentModifier item : col)
		{
			ret += item.getModifier() + ": score " + item.getScore() + "\n";
		}
		return ret;
	}
}