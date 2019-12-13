package com.qa.fw.test;

import org.testng.annotations.Test;

import com.qa.fw.keyword.engine.KeywordEngine;

public class LoginTest {
	
	public KeywordEngine keywordEngine;
	
	@Test
	public void loginTest() {
	keywordEngine = new KeywordEngine();
	keywordEngine.startExecution("login");
	}
	
}
