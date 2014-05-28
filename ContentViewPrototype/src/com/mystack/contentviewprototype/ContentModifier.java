package com.mystack.contentviewprototype;

class ContentModifier
{
	private static final int DEFAULT_SCORE = 50;
	
	private String modifier;
	private int score = DEFAULT_SCORE;

	public ContentModifier(String mod)
	{
		modifier = mod;
	}
	
	// give negative number to decrement
	public void incrementScore(int toIncrement) throws ScoreZeroException
	{
		score += toIncrement;
		
		if(score <= 0)
		{
			score = 0;
			throw new ScoreZeroException();
		}
	}
	
	public String getModifier()
	{
		return modifier;
	}
	
	public int getScore()
	{
		return score;
	}
}