package com.jpmorgan.msg.report;

import java.util.ArrayList;
import java.util.Map;

import com.jpmorgan.msg.dto.ProductDO;
/**
 * @author Shipra Gupta
 * */
public class ReportGenerator {
	

	/**
	 * generates report after every 10th message of sales
	 * 
	 * @param Map<String, ProductDO> itemsDetails
	 * 
	 * @return void
	 * */
	public void generateSalesReport(Map<String, ProductDO> itemsDetails) {
		
		System.out.println("******************LOG REPORT OF 10 SALES************************");
		for(Map.Entry<String, ProductDO> entry: itemsDetails.entrySet()){
			
			System.out.println("---------------------------------------");
			System.out.println("Product : "+entry.getKey());
			System.out.println("Quantity : "+entry.getValue().getNumberOfItems());
			System.out.println("Total Price : "+entry.getValue().getItemPrice());
			System.out.println("---------------------------------------");
			
			
		}
		System.out.println("*******REPORT GENERATED********");
		
	}
	
	/**
	 * generates report of adjustment messages after every 50th message of sales
	 * 
	 * @param Map<String, ArrayList<String>> adjustmentMsgs
	 * 
	 * @return void
	 * */
	public void generateAdjustmentSalesReport(Map<String, ArrayList<String>> adjustmentMsgs) {
		
		System.out.println("----------PAUSING APPLICATION-----------");
		System.out.println("This application will not accept new messages from now");
		for(Map.Entry<String, ArrayList<String>> entry: adjustmentMsgs.entrySet()){
			System.out.println("---------------------------------------");
			System.out.println("PRODUCT : "+entry.getKey());
			for(String messages: entry.getValue()){
				System.out.println("Adjustment Message : "+messages);
			}
			System.out.println("---------------------------------------");
		}
		
	}

	
}
