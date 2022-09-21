public class Order {
    private Integer id;
    private String type;
    private Float price;
    private Integer quantity;
    private Integer time;
    public Order prev;
    public Order next;

    public Order(Integer id, String type, Float price, Integer quantity, Integer time) {
        this.id = id;
        this.type = type.toLowerCase();
        this.price = price;
        this.quantity = quantity;
        this.time = time;
        prev = null;
        next = null;
    }

    public Order(Integer id, String type, Integer price, Integer quantity) {
        this.id = id;
        this.type = type.toLowerCase();
        this.price = Float.valueOf(price);
        this.quantity = quantity;
        this.time = time;
        prev = null;
        next = null;
    }
    public Integer getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public Float getPrice() { return price; }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer amount) { quantity = amount; }
    public Integer getTime() {return time;}
}