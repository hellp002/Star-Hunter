package logic.exception;

public class SkillRequirementException extends Exception {

	/**
	 * 
	 */
	private String error;
	public SkillRequirementException(String error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		return error;
		
	}

}
