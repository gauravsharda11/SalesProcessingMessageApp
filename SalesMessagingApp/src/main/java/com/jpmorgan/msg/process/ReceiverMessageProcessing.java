package com.jpmorgan.msg.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.jpmorgan.msg.dto.ProductDO;
import com.jpmorgan.msg.exception.UserException;
import com.jpmorgan.msg.report.ReportGenerator;
/**
 * @author Shipra Gupta
 * */
public class ReceiverMessageProcessing {
	
	
	private static Integer msgCounter = 0;
	private static SalesMessagesProcessing salesProcess;
	private static ReportGenerator reportGenerator;
	private Map<String,ProductDO> itemsDetails = new HashMap<String, ProductDO>();
	private Map<String,ArrayList<String>> adjustmentMessagesLog = new HashMap<String, ArrayList<String>>();

	/**
	 * receives messages from sender and then sends for further processing
	 * 
	 * @param String receivedMessage
	 * 
	 * @return void
	 * */
	public void messageProcess(String receivedMessage) {

		if(receivedMessage.isEmpty()){
			throw new UserException("Cannot accept empty message");
		}
		msgCounter++;
			
		String[] msgArray = null;
		salesProcess = new SalesMessagesProcessing();
		reportGenerator = new ReportGenerator();
		try {
			msgArray = receivedMessage.split(" ");
			
			switch(checkMessageType(receivedMessage)){
			
			case "One":{
				salesProcess.processMessageTypeOne(msgArray,itemsDetails);
				break;
			}
			case "Two":{
				salesProcess.processMessageTypeTwo(msgArray,itemsDetails);
				break;
			}
			case "Three":{
				salesProcess.processMessageTypeThree(msgArray,itemsDetails,adjustmentMessagesLog);
				break;
			}
			default:{
				break;
			}
			}
			
			if(msgCounter % 10 == 0 && msgCounter % 50 != 0){
				reportGenerator.generateSalesReport(itemsDetails);
			}
			if(msgCounter % 50 == 0){
				reportGenerator.generateAdjustmentSalesReport(adjustmentMessagesLog);
			}
			if(msgCounter>50){
				return;
			}
		} catch (Exception e) {
			throw new UserException("Exception occurred while processing messages :: "+e.getMessage());
		}
			
	}

	/**
	 * checks the type of message for sales
	 * 
	 * @param String message
	 * @return String
	 * */
	private String checkMessageType(String message) {
		if(message.contains("sales")){
			return "Two";
		}else if(message.contains("at")){
			return "One";
		}else {
			return "Three";
		}
	}
	
}
