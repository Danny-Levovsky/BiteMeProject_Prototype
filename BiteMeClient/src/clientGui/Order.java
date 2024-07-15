package clientGui;

public class Order {

    private String restaurant;
    private int orderNumber;
    private int totalPrice;
    private int orderListNumber;
    private String orderAddress;

    public Order(String restaurant, int orderNumber, int totalPrice, int orderListNumber, String orderAddress) {
        this.restaurant = restaurant;
        this.orderNumber = orderNumber;
        this.totalPrice = totalPrice;
        this.orderListNumber = orderListNumber;
        this.orderAddress = orderAddress;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getOrderListNumber() {
        return orderListNumber;
    }

    public String getOrderAddress() {
        return orderAddress;
    }
}
