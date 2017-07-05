package com.wslibrary.bg7.ws;

public enum SBCommands {
	
	// producao

	PREFIX_SEND_IMAGE("auction-lotting/product/"),
	CMD_SEND_IMAGE("/attachment/?addToGallery=false&watermark=false"),
	CMD_SEND_PRODUCTS("https://preapi.s4bdigital.net/auction-lotting/product/"),
	CMD_OAUTH("https://preapi.s4bdigital.net/account/oauth/token");


	String value;

	SBCommands(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}