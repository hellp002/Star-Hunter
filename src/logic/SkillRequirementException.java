package logic;

public class SkillRequirementException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7302343014094441330L;
	private String error;
	public SkillRequirementException(String error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		return error;
		
	}

}
