package com.jpmorgan.msg.junittest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.jpmorgan.msg.process.SenderMessageProcessing;

@RunWith(JUnit4.class)
public class SenderMessagesProcessingJUnitTest {
	
	private static SenderMessageProcessing senderProcess = new SenderMessageProcessing();
	
	@Test
	public void testSenderMessageWithFiftyMessages(){ // test the file with all 50 messages
		senderProcess.senderMessages();
	}
	
	@Test
	public void testSenderMessageWithMoreThanFiftyMessages(){ // test the file with 51 messages
		senderProcess.senderMessages();
	}
	
	
}
