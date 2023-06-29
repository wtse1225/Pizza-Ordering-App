package application.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");	
	
	// Declare attributes
	private int orderNo;
    private LocalDateTime dateOrder;
    private String customerName;
    private String phoneNumber;
    private List<Pizza> pizzas; // Add a list of piazzas
    private double totalAmount;
        
	// Constructor
	public Order(int orderNo, LocalDateTime dateOrder, String customerName, String phoneNumber) {
		super();
		this.orderNo = orderNo;
		this.dateOrder = dateOrder;
		this.customerName = customerName;
		this.phoneNumber = phoneNumber;
		this.pizzas = new ArrayList<>(); // Initialize a list of piazzas
    }
		
	//Add a method to add piazzas to the order
	public void addPizza(Pizza pizza) {
		pizzas.add(pizza);
		totalAmount += (pizza.getToppingPrice() + pizza.getSizePrice()) * pizza.getQuantity();
	}

	// Add a method to get the list of piazzas
	public List<Pizza> getPizzas() {
		return pizzas;
	}
		

	// Getters 
	public int getOrderNo() {
		return orderNo;
	}

	public String getDateOrder() {
		String formattedDate = dateOrder.format(formatter);
		return formattedDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public double getTotal() {
		return totalAmount;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order [orderNo=").append(orderNo)
          .append(", dateOrder=").append(dateOrder)
          .append(", customerName=").append(customerName)
          .append(", phoneNumber=").append(phoneNumber)
          .append(", pizzas=").append(pizzas)
          .append("]");
        return sb.toString();
    }
}
