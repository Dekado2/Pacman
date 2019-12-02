package model;

public class Answer {
	
	/**
	 *  the answer's id
	 */
	private int aID;
	/**
	 *  the answer itself
	 */
	private String aText;
	
   /**
    *  Answer Constructor
    * @param aID
    * @param aText
    */
	public Answer (int aID, String aText)
	{
		this.aID=aID;
		this.aText=aText;
	}
	/**
	 * Get Answer ID
	 * @return aID
	 */
	public int getAID() {
		return aID;
	}
	/**
	 * Get Answer text
	 * @return aText
	 */
	public String getAText() {
		return aText;
	}

	/**
	 * Sets the answer text
	 * @param aText
	 */
	protected void setAText(String aText) {
		this.aText = aText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + aID;
		result = prime * result + ((aText == null) ? 0 : aText.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		if (aID != other.aID)
			return false;
		if (aText == null) {
			if (other.aText != null)
				return false;
		} else if (!aText.equals(other.aText))
			return false;
		return true;
	}

	/**
	 * format printing
	 * @param x
	 * @return
	 */
    public String tostring (boolean x){
    	return "Answer [aID=" + aID + ", aText=" + aText +"]";
    }
	/**
	 * normal printing
	 */
	@Override
	public String toString() {
		return aID +": " + aText;
	}
	
}
