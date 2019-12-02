package utils;

/**
 * enum for difficulty level
 * translates from number to string
 *
 */
public enum E_DifficultyLevel {
	HARD(3),
	MEDIUM(2),
	EASY(1);
	private int text;
	
	 E_DifficultyLevel(int text)
	{
		this.text=text;
	}
	
	 public int getDiff() {
		 return text;
	 }
	public E_DifficultyLevel toStringDifficultyLevel(int value)
	{
		if(value==1) {
			
			return E_DifficultyLevel.EASY;
		}else if(value==2)
			return E_DifficultyLevel.MEDIUM;
		else if(value==3)
			return E_DifficultyLevel.HARD;
		return null;
	
	}

}
