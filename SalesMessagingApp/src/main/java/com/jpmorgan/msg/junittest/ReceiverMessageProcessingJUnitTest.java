package com.jpmorgan.msg.junittest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jpmorgan.msg.exception.UserException;
import com.jpmorgan.msg.process.ReceiverMessageProcessing;

public class ReceiverMessageProcessingJUnitTest {
	
	private ReceiverMessageProcessing receiverProcess = new ReceiverMessageProcessing();
	
	@Test
	public void checkMessageTypeOneTest() {
		String message = "apple at 10p";
		assertEquals("One", receiverProcess.checkMessageType(message));
	}

	@Test
	public void checkMessageTypeTwoTest() {
		String message = "sales of apples at 3p each";
		assertEquals("Two", receiverProcess.checkMessageType(message));
	}

	@Test
	public void checkMessageTypeThreeTest() {
		String message = "Add 2p apples";
		assertEquals("Three", receiverProcess.checkMessageType(message));
	}
	
	@Test(expected=UserException.class) // check if message is empty
	public void receivedEmptyMessageTest(){
		String message = "";
		receiverProcess.receivedMessageProcess(message);
	}
	
	@Test // check the received message
	public void receivedMessageTest(){
		String message = "50 sales of grapes at 5p each";
		receiverProcess.receivedMessageProcess(message);
	}		
}
