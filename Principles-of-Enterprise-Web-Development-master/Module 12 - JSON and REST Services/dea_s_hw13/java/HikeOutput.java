package dea_s_hw13;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HikeOutput {
	private int price;
	private String errorMessage = "";
	
	public HikeOutput() {
		super();
	}
	
	public HikeOutput(int price, String errorMessage) {
		this.price = price;
		this.errorMessage = errorMessage;
	}
	
	@JsonProperty("PRICE")
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@JsonProperty("ERROR")
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
