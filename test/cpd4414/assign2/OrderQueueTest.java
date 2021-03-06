/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cpd4414.assign2;

import cpd4414.assign2.OrderQueue;
import cpd4414.assign2.Purchase;
import cpd4414.assign2.Order;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class OrderQueueTest {
    
    public OrderQueueTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testWhenCustomerExistsAndPurchasesExistThenTimeReceivedIsNow() throws Exception {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        orderQueue.add(order);
        orderQueue.setTimeReceived(order);
        
        long expResult = new Date().getTime();
        long result = order.getTimeReceived().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }
    
    @Test
    public void testWhenCustomerNameAndIdDoNotExistThenThrowException() {
        boolean didthrow = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("", "");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        try {
            orderQueue.add(order);
        }
        catch (Exception ex){
            didthrow = true;
        }
        
        assertTrue(didthrow);
    }
    
    @Test
    public void testWhenNoPurchasesExistThrowException() {
        boolean didthrow = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        try {
            orderQueue.add(order);
        }
        catch (Exception ex){
            didthrow = true;
        }
        assertTrue(didthrow);
       
    }
    
    @Test
    public void RequestForOrderWhenOrderInSystemThenReturnOrder() throws Exception {
        
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        orderQueue.add(order);
        orderQueue.setTimeReceived(order);
        
        Order result = order;
        Order expResult = orderQueue.returnQueue();
        assertEquals(result, expResult);
        
    }
    
    @Test
    public void RequestForOrderWithNoOrderInSystemThenThrowNull() {
        OrderQueue orderQueue = new OrderQueue();
        
        Order expResult = orderQueue.returnQueue();
        assertEquals(expResult, null);
        
    }
    
    @Test
    public void TestRequestAnOrderWithTimeReceivedThenSetTimeProcessedToNow() throws Exception{
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        orderQueue.add(order);
        orderQueue.setTimeReceived(order);
        
        orderQueue.orderProcessed(order);
        orderQueue.setTimeProcessed(order);
        
        long expResult = new Date().getTime();
        long result = order.getTimeProcessed().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }
    
    
    @Test
    public void TestIfRequestAnOrderWithTimeReceivedDoesNotExistThenThrowException() throws Exception{
        boolean didthrow = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        orderQueue.add(order);
        
        try {
            orderQueue.orderProcessed(order);
        }
        catch (Exception ex){
            didthrow = true;
        }
        assertTrue(didthrow);
    }
    
    @Test
    public void TestRequestAnOrderWithTimeReceivedAndTimeProcessedThenSetTimeFulfilledToNow() throws Exception{
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        orderQueue.add(order);
        orderQueue.setTimeReceived(order);
        
        orderQueue.orderProcessed(order);
        orderQueue.setTimeProcessed(order);
        
        orderQueue.orderFulfilled(order);
        orderQueue.setTimeFulfilled(order);
        
        long expResult = new Date().getTime();
        long result = order.getTimeFulfilled().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }
    
    @Test
    public void TestIfRequestTimeFulfilledIfNoTimeRecievedThenThrowAnException() throws Exception{
        boolean didthrow = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        orderQueue.add(order);
        
        try {
            orderQueue.orderFulfilled(order);
        }
        catch (Exception ex){
            didthrow = true;
        }
        assertTrue(didthrow);
    }
    
    @Test
    public void TestIfRequestTimeFulfilledIfNoTimeProcessedThenThrowAnException() throws Exception{
        boolean didthrow = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        orderQueue.add(order);
        orderQueue.setTimeReceived(order);
        
        orderQueue.orderProcessed(order);
        
        try {
            orderQueue.orderFulfilled(order);
        }
        catch (Exception ex){
            didthrow = true;
        }
        assertTrue(didthrow);
    }
    
    @Test
    public void TestReturnEmptyStringWithNoOrdersReceivedProcessedOrFulfilled() {
        OrderQueue orderQueue = new OrderQueue();
        String expResult = "";
        String result = orderQueue.toString();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testRequestReportOfAllOrdersInQueueProcessedAndFulfilled() throws Exception {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        orderQueue.add(order);
        orderQueue.setTimeReceived(order);
        
        Order orderTwo = new Order("CUST00002", "DEF Incorporated");
        orderTwo.addPurchase(new Purchase("PROD0002", 450));
        orderTwo.addPurchase(new Purchase("PROD0006", 150));
        orderQueue.add(orderTwo);
        orderQueue.setTimeReceived(orderTwo);
        
        orderQueue.orderProcessed(order);
        orderQueue.setTimeProcessed(order);
        orderQueue.orderFulfilled(order);
        orderQueue.setTimeFulfilled(order);
        
        Date timeNow = new Date();
        String expResult = "{ orders : [{ \"customerId\" : CUST00002, \"customerName\" : DEF Incorporated, \"timeReceived\" : " + timeNow + ", \"timeProcessed\" : null, \"timeFulfilled\" : null, \"purchases\" : [{\"quantity\":150,\"productId\":\"PROD0006\"},{\"quantity\":150,\"productId\":\"PROD0006\"}], \"notes\" : null } , { \"customerId\" : CUST00001, \"customerName\" : ABC Construction, \"timeReceived\" : " + timeNow + ", \"timeProcessed\" : " + timeNow + ", \"timeFulfilled\" : " + timeNow + ", \"purchases\" : [{\"quantity\":250,\"productId\":\"PROD0006\"},{\"quantity\":250,\"productId\":\"PROD0006\"}], \"notes\" : null } ] }";
        String result = orderQueue.toString();
        System.out.println(result);
        assertEquals(expResult, result);
        
        
    }
    
}
