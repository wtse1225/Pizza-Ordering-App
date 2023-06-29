package application.controllers;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.models.Order;
import application.models.Pizza;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SampleController {

    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    
    @FXML
    private TextField custName;

    @FXML
    private RadioButton op1;

    @FXML
    private RadioButton op2;

    @FXML
    private RadioButton op3;

    @FXML
    private TextField phoneNum;

    @FXML
    private TextField qty;

    @FXML
    private ComboBox<String> sizeCombo;
    
    @FXML
    private ToggleGroup toppingToggleGroup;

    @FXML
    private TextArea summary;
    
    @FXML
    private ImageView imageView;
    
    
    // Data structure
    private final Map<Integer, Order> orderMap = new HashMap<>();
    private final Map<String, Double> toppingPrices = new HashMap<>();
    private final Map<String, Double> sizePrices = new HashMap<>();
    private List<Pizza> pizzaList = new ArrayList<>();
    
    // Instance variables
    private int orderNo = 1; // start from 1
    private LocalDateTime dateOrder = LocalDateTime.now();
    private String name;
    private String phone;
    private double taxRate = 0.13;    
    
    
    @FXML
    void addBtn(ActionEvent event) {
    	StringBuilder summaryText = new StringBuilder();
    	
    	// Extract each pizza input from the UI
    	name = custName.getText();
    	phone = phoneNum.getText(); 
    	String topping = ((RadioButton)toppingToggleGroup.getSelectedToggle()).getText();
    	String size = sizeCombo.getValue();
    	Integer qtyOrder = Integer.parseInt(qty.getText());
    	double toppingPrice = toppingPrices.get(topping);
        double sizePrice = sizePrices.get(size);  
        
        // Input validation
        if (name.isEmpty()) {        	
        	summaryText.append("Please enter a customer name").append("\n");
        	summary.setText(summaryText.toString());
        } else if (phone.isEmpty() || !phone.matches("\\d+")) {
        	summaryText.append("Please enter a valid phone number").append("\n");
        	summary.setText(summaryText.toString());
        } else {
        	
	        // Create a new Pizza object (it could be multiple for the same order)
	        Pizza pizza = new Pizza(topping, size, qtyOrder, toppingPrice, sizePrice);
	        
	        // Add the pizza to the order list
	        pizzaList.add(pizza);
	        
	        summaryText.append("Pizza ").append("(" + pizza.getTopping() + " " + pizza.getSize() + " x " + pizza.getQuantity() + ") added to this order");	        
        }
        summary.setText(summaryText.toString());
    }
    
    
    @FXML
    void placeOrderBtn(ActionEvent event) {   
    	
    	StringBuilder summaryText = new StringBuilder();  
    	
    	if (pizzaList.isEmpty()) {
    		
    		summaryText.append("You have no customer orders").append("\n");
    		
    	} else {
    		// Check if this is within the existing order; if yes, retrieve the order; if no, create a new order
            Order order = orderMap.getOrDefault(orderNo, new Order(orderNo, dateOrder, name, phone));
            
            // Add each pizza from the pizzaList to the order
            for (Pizza pizza : pizzaList) {
                order.addPizza(pizza);
            }
            
            // Add the order back to the map
            orderMap.put(orderNo, order);

            // Clear the pizzaList and previous order inputs
            pizzaList.clear();
            custName.clear();
            phoneNum.clear();
            qty.setText("1");
            sizeCombo.setValue("Medium");
            toppingToggleGroup.selectToggle(op1);
    	}
    	
    	// Print out the current order details
    	if (orderMap.get(orderNo) != null) {
    		
    		Order currentOrder = orderMap.get(orderNo);
	        
    		summaryText.append("Order Number: ").append(currentOrder.getOrderNo()).append("\n");
	        summaryText.append("Order Date: ").append(currentOrder.getDateOrder()).append("\n");
	        summaryText.append("Customer Name: ").append(currentOrder.getCustomerName()).append("\n");
	        summaryText.append("Customer Phone: ").append(currentOrder.getPhoneNumber()).append("\n");
	        summaryText.append("\n");
	            
	        for (Pizza pizza : currentOrder.getPizzas()) {
	            summaryText.append("Pizza Type: ").append(pizza.getTopping()).append("\n");
	            summaryText.append("Pizza Size: ").append(pizza.getSize()).append("\n");
	            summaryText.append("Quantity: ").append(pizza.getQuantity()).append("\n");
	            summaryText.append("Price: ").append((pizza.getToppingPrice() + pizza.getSizePrice()) * pizza.getQuantity()).append("\n"); 
	            summaryText.append("\n");
	        }            
	            
	        summaryText.append("Before tax: $").append(decimalFormat.format(currentOrder.getTotal())).append("\n");
	        summaryText.append("Total after tax: $").append(decimalFormat.format(currentOrder.getTotal() * (1 + taxRate))).append("\n");
	        summaryText.append("--------------------------------------\n");
	        
	        orderNo++;	
	        System.out.println(orderMap);
	    }	        
    	summary.setText(summaryText.toString());
	    
	}
    
    
    @FXML
    void reportBtn(ActionEvent event) {
    	StringBuilder summaryText = new StringBuilder();

        for (Order order : orderMap.values()) {
        	summaryText.append("Order Number: ").append(order.getOrderNo()).append("\n");
	        summaryText.append("Order Date: ").append(order.getDateOrder()).append("\n");
	        summaryText.append("Customer Name: ").append(order.getCustomerName()).append("\n");
	        summaryText.append("Customer Phone: ").append(order.getPhoneNumber()).append("\n");
	        summaryText.append("\n");
	        
	        for (Pizza pizza : order.getPizzas()) {
	            summaryText.append("Pizza Type: ").append(pizza.getTopping()).append("\n");
	            summaryText.append("Pizza Size: ").append(pizza.getSize()).append("\n");
	            summaryText.append("Quantity: ").append(pizza.getQuantity()).append("\n");
	            summaryText.append("Price: ").append((pizza.getToppingPrice() + pizza.getSizePrice()) * pizza.getQuantity()).append("\n"); 
	            summaryText.append("\n");
	        }
	    
	        summaryText.append("Before tax: $").append(decimalFormat.format(order.getTotal())).append("\n");
	        summaryText.append("Total after tax: $").append(decimalFormat.format(order.getTotal() * (1 + taxRate))).append("\n");
	        summaryText.append("--------------------------------------\n");

	        summary.setText(summaryText.toString());
        }
    }
    
    @FXML
    void clearBtn(ActionEvent event) {
    	// Clear the form fields
        custName.clear();
        phoneNum.clear();
        qty.setText("1");
        sizeCombo.setValue("Medium");
        toppingToggleGroup.selectToggle(op1);

        // Clear the summary text
        summary.clear();
    }
    
    @FXML
    public void initialize() {
    	Image image = new Image(getClass().getResource("/images/b.jpg").toExternalForm());
    	imageView.setImage(image);    	
    	imageView.setFitWidth(232);
    	imageView.setFitHeight(492);
    	imageView.setPreserveRatio(true); 
    	
    	sizeCombo.getItems().addAll("Small", "Medium", "Large");
    	sizeCombo.setValue("Medium");
    	
    	// Create a Map to store the prices 
        toppingPrices.put("Cheese", 10.0);
        toppingPrices.put("Vegetarian", 8.5);
        toppingPrices.put("Meat Lover", 12.75);

        // Create a Map to store the prices for sizes
        sizePrices.put("Small", 6.0);
        sizePrices.put("Medium", 8.0);
        sizePrices.put("Large", 10.0);
    }
}
