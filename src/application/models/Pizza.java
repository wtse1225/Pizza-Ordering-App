package application.models;

public class Pizza {
    private String topping;
    private String size;
    private int quantity;
    private double toppingPrice;
    private double sizePrice;
    

    public Pizza(String topping, String size, int quantity, double toppingPrice, double sizePrice) {
        this.topping = topping;
        this.size = size;
        this.quantity = quantity;
        this.toppingPrice = toppingPrice;
        this.sizePrice = sizePrice;
    }
    
    // Getters
	public String getTopping() {
		return topping;
	}

	public String getSize() {
		return size;
	}

	public double getToppingPrice() {
		return toppingPrice;
	}

	public double getSizePrice() {
		return sizePrice;
	}

	public int getQuantity() {
		return quantity;
	}
		
	@Override
	public String toString() {
		return "Pizza [topping=" + topping + ", size=" + size + ", quantity=" + quantity + ", toppingPrice="
				+ toppingPrice + ", sizePrice=" + sizePrice + "]";
	}    
}
