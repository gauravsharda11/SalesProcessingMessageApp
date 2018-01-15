package com.jpmorgan.msg.junittest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.omg.CORBA.SystemException;

import com.jpmorgan.msg.dto.ProductDO;
import com.jpmorgan.msg.exception.UserException;
import com.jpmorgan.msg.process.SalesMessagesProcessing;

public class SalesMessagesProcessingJUnitTest {
	
	private static SalesMessagesProcessing salesProcess = new SalesMessagesProcessing();

	@Test
	public void processMessageTypeOneTestForNewItems() {
		String message = "orange at 30p";
		String[] messageArray = message.split(" ");
		Map<String, ProductDO> saleMap = new HashMap<>();
		salesProcess.processMessageTypeOne(messageArray, saleMap);
		assertEquals(true, saleMap.containsKey("orange"));
		assertEquals(Integer.valueOf(1), saleMap.get("orange").getNumberOfItems());
		assertEquals("Check price : ",30, saleMap.get("orange").getItemPrice(),100);
	}

	@Test
	public void processMessageTypeOneTestForExistingItems() {
		String message = "chocolate at 30p";
		String[] messageArray = message.split(" ");
		Map<String, ProductDO> saleMap = new HashMap<>();
		ProductDO productDO = new ProductDO();
		productDO.setNumberOfItems(1);
		productDO.setItemPrice(10);
		saleMap.put("chocolate", productDO);
		salesProcess.processMessageTypeOne(messageArray, saleMap);
		assertEquals(true, saleMap.containsKey("chocolate"));
		assertEquals(Integer.valueOf(2), saleMap.get("chocolate").getNumberOfItems());
		assertEquals("Check price : ",40, saleMap.get("chocolate").getItemPrice(),100);
	}

	@Test
	public void processMessageTypeTwoTestForNewItems() {
		String message = "10 sales of oranges at 10p each";
		String[] messageArray = message.split(" ");
		String saleType = "orange";
		Map<String, ProductDO> saleMap = new HashMap<>();
		salesProcess.processMessageTypeTwo(messageArray, saleMap);
		assertEquals(true, saleMap.containsKey(saleType));
		assertEquals(Integer.valueOf(10), saleMap.get(saleType).getNumberOfItems());
		assertEquals("Check price : ",100, saleMap.get(saleType).getItemPrice(),500);
	}

	@Test
	public void processMessageTypeTwoTestForExistingItems() {
		String message = "20 sales of bars at 10p each";
		String saleType = "bar";
		String[] messageArray = message.split(" ");
		Map<String, ProductDO> saleMap = new HashMap<>();
		ProductDO productDO = new ProductDO();
		productDO.setNumberOfItems(1);
		productDO.setItemPrice(10);
		saleMap.put(saleType, productDO);
		salesProcess.processMessageTypeTwo(messageArray, saleMap);
		assertEquals(true, saleMap.containsKey(saleType));
		assertEquals(Integer.valueOf(21), saleMap.get(saleType).getNumberOfItems());
		assertEquals("Check price : ", 210, saleMap.get(saleType).getItemPrice(), 500);
	}

	@Test
	public void processMessageTypeThreeAddTest() {
		String message = "Add 30p bananas";
		String[] messageArray = message.split(" ");
		Map<String, ProductDO> saleMap = new HashMap<>();
		ProductDO productDO = new ProductDO();
		productDO.setNumberOfItems(10);
		productDO.setItemPrice(1000);
		saleMap.put("banana", productDO);
		Map<String,ArrayList<String>> adjustmentMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> msgsList = new ArrayList<String>();
		msgsList.add("Add 30p bananas");
		adjustmentMap.put("banana", msgsList);
		salesProcess.processMessageTypeThree(messageArray, saleMap,adjustmentMap);
		assertEquals(Integer.valueOf(10), saleMap.get("banana").getNumberOfItems());
		assertEquals("Check price : ",1300, saleMap.get("banana").getItemPrice(),1500);
	}

	@Test
	public void processMessageTypeThreeSubtractTest() {
		String message = "Subtract 3p oranges";
		String[] messageArray = message.split(" ");
		Map<String, ProductDO> saleMap = new HashMap<>();
		ProductDO productDO = new ProductDO();
		productDO.setNumberOfItems(10);
		productDO.setItemPrice(1000);
		saleMap.put("orange", productDO);
		Map<String,ArrayList<String>> adjustmentMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> msgsList = new ArrayList<String>();
		msgsList.add("Subtract 3p oranges");
		adjustmentMap.put("orange", msgsList);
		salesProcess.processMessageTypeThree(messageArray, saleMap,adjustmentMap);
		assertEquals(Integer.valueOf(10), saleMap.get("orange").getNumberOfItems());
		assertEquals("Check price : ",970, saleMap.get("orange").getItemPrice(),1000);
	}

	@Test
	public void processMessageThreeTestForCaseMultiply() {
		String message = "Multiply 2p apples";
		String[] messageArray = message.split(" ");
		Map<String, ProductDO> saleMap = new HashMap<>();
		ProductDO productDO = new ProductDO();
		productDO.setNumberOfItems(10);
		productDO.setItemPrice(1000);
		saleMap.put("apple", productDO);
		Map<String,ArrayList<String>> adjustmentMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> msgsList = new ArrayList<String>();
		msgsList.add("Multiply 2p apples");
		adjustmentMap.put("apple", msgsList);
		salesProcess.processMessageTypeThree(messageArray, saleMap,adjustmentMap);
		assertEquals(Integer.valueOf(10), saleMap.get("apple").getNumberOfItems());
		assertEquals("Check price : ",20000, saleMap.get("apple").getItemPrice(),25000);
	}

}
