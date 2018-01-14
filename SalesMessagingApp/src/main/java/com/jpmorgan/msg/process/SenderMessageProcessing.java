package com.jpmorgan.msg.process;

import java.io.BufferedReader;
import java.io.FileReader;

import com.jpmorgan.msg.exception.UserException;

/**
 * @author Shipra Gupta 
 * 
 * This class contains main method from where we are sending sample input file as a sender
 * for the messaging app.
 *
 */
public class SenderMessageProcessing 
{
	
	private static ReceiverMessageProcessing receiveMsg;
	
	/**
	 * sending messages for processing
	 * 
	 * @return void
	 * */
    public static void main( String[] args )
    {
        try {
        	receiveMsg = new ReceiverMessageProcessing();
			String readMessage;
			BufferedReader senderInputFile = new BufferedReader(new FileReader("sample/message_input.txt"));
			while((readMessage = senderInputFile.readLine())!=null){
				receiveMsg.messageProcess(readMessage);
			}
		} catch (Exception e) {
			throw new UserException("Exception occurred while sending messages :: "+e.getMessage());
		}
    }
}
