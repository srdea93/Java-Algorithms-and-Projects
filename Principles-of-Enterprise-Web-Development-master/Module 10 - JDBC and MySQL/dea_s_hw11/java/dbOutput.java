package dea_s_hw11;

public class dbOutput {
	String output = "";
	String errorMessage = "";

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public dbOutput()
	{	
		this.output = "";
	}
	
	public void addLineToOutput(String line) {
		this.output = this.output + line + "\n";
	}
	
	public void addErrorMessage(String line) {
		this.errorMessage = this.errorMessage + line;
	}

}
