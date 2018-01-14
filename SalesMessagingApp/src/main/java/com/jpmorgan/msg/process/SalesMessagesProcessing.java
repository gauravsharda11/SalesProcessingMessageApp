package com.jpmorgan.msg.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.jpmorgan.msg.dto.ProductDO;
import com.jpmorgan.msg.exception.UserException;
/**
 * @author Shipra Gupta
 * */
public class SalesMessagesProcessing {
	
	/**
	 * this method processes the message of type 'one' to get the details of the sales
	 * 
	 * @param String[] msgArray 
	 * @param Map<String, ProductDO> productDetails
	 * 
	 * @return void
	 * */
	public void processMessageTypeOne(String[] msgArray, Map<String, ProductDO> productDetails) {
		
		String productType = msgArray[0];
		int numberOfItems = 1;
		double itemPrice = Double.parseDouble(msgArray[2].substring(0, (msgArray[2].length()-1)));
		ProductDO prodDo = new ProductDO();
		if(!productDetails.containsKey(productType)){
			prodDo.setNumberOfItems(numberOfItems);
			prodDo.setItemPrice(itemPrice);
			productDetails.put(productType, prodDo);
		}else{
			prodDo = getTotalQuantityAndValue(productType,numberOfItems,itemPrice,productDetails);
			productDetails.put(productType, prodDo);
		}
	}

	/**
	 * this method processes the message of type 'two' to get the details of the sales
	 * 
	 * @param String[] msgArray 
	 * @param Map<String, ProductDO> productDetails
	 * 
	 * @return void
	 * */
	public void processMessageTypeTwo(String[] msgArray, Map<String, ProductDO> productDetails) {
		
		String productType = msgArray[3].substring(0, msgArray[3].length()-1);
		int numberOfItems = Integer.parseInt(msgArray[0]);
		double itemPrice = Double.parseDouble(msgArray[5].substring(0, (msgArray[5].length()-1))) * numberOfItems;
		ProductDO prodDo = new ProductDO();
		if(!productDetails.containsKey(productType)){
			prodDo.setNumberOfItems(numberOfItems);
			prodDo.setItemPrice(itemPrice);
			productDetails.put(productType, prodDo);
		}else{
			prodDo = getTotalQuantityAndValue(productType,numberOfItems,itemPrice,productDetails);
			productDetails.put(productType, prodDo);
		}
	}
	
	/**
	 * this method processes the message of type 'three' to get the details of the sales
	 * 
	 * @param String[] msgArray 
	 * @param Map<String, ProductDO> productDetails
	 * @param Map<String, ArrayList<String>> adjustmentMessagesLog
	 * 
	 * @return void
	 * */
	public void processMessageTypeThree(String[] msgArray, Map<String, ProductDO> productDetails, Map<String, ArrayList<String>> adjustmentMessagesLog) {
		
		String productType = msgArray[2].substring(0, msgArray[2].length()-1);
		double itemPrice = Double.parseDouble(msgArray[1].substring(0, (msgArray[1].length()-1))) * productDetails.get(productType).getNumberOfItems();
		String adjutmentType = msgArray[0];
		if(productDetails.containsKey(productType)){
			if(adjutmentType.equalsIgnoreCase("add")){
				productDetails.get(productType).setItemPrice(productDetails.get(productType).getItemPrice()+itemPrice);
			}else if(adjutmentType.equalsIgnoreCase("multiply")){
				productDetails.get(productType).setItemPrice(productDetails.get(productType).getItemPrice()*itemPrice);
			}else if(adjutmentType.equalsIgnoreCase("subtract")){
				productDetails.get(productType).setItemPrice(productDetails.get(productType).getItemPrice()-itemPrice);
			}

			ArrayList<String> msgs;
			if(!adjustmentMessagesLog.containsKey(productType)){
				msgs = new ArrayList<String>();
				String receivedadjustmentMessage = String.join(" ", msgArray);
				msgs.add(receivedadjustmentMessage);
				adjustmentMessagesLog.put(productType, msgs);
			}else{
				//msgs = new ArrayList<String>();
				msgs =	adjustmentMessagesLog.get(productType);
				msgs.add(String.join(" ", msgArray));
				adjustmentMessagesLog.get(productType).add(String.join(" ", msgArray));
			}
			
			
		}else{
			throw new UserException("No products present for adjustment");
		}
	}

	/**
	 * calculating total price and number of items to be processed
	 * 
	 * @param String productType 
	 * @param int numberOfItems
	 * @param double itemPrice
	 * @param Map<String, ProductDO> prodDetails
	 * 
	 * @return ProductDO
	 * */
	public ProductDO getTotalQuantityAndValue(String productType, int numberOfItems,double itemPrice, Map<String, ProductDO> prodDetails) {
		
		ProductDO productDo = new ProductDO();
		productDo.setNumberOfItems(prodDetails.get(productType).getNumberOfItems()+numberOfItems);
		productDo.setItemPrice(prodDetails.get(productType).getItemPrice()+itemPrice);
		return productDo;
	}

}
