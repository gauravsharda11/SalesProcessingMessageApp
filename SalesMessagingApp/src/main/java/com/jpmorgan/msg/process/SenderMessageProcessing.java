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
	
	
    public static void main( String[] args )
    {
        SenderMessageProcessing senderMsgProcess = new SenderMessageProcessing();
        senderMsgProcess.senderMessages();
    }
    
    /**
	 * sending messages for processing
	 * 
	 * @return void
	 * */
    public void senderMessages(){
    	try {
        	receiveMsg = new ReceiverMessageProcessing();
			String readMessage;
			BufferedReader senderInputFile = new BufferedReader(new FileReader("sample/message_input.txt"));
			while((readMessage = senderInputFile.readLine())!=null){
				receiveMsg.receivedMessageProcess(readMessage);
			}
			senderInputFile.close();
		} catch (Exception e) {
			throw new UserException("Exception occurred while sending messages :: "+e.getMessage());
		}
    }
}
