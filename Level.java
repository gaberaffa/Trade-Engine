public class Level {
    private Float price;
    private Integer volume;
    public Level parent;
    public Level left;
    public Level right;
    public Order first;
    public Order last;

    public Level(Order first) {
        this.price = first.getPrice();
        volume = first.getQuantity();
        this.first = first;
        last = first;
        parent = null;
        left = null;
        right = null;
    }

    public void append(Order addition) {
        volume += addition.getQuantity();
        addition.prev = last;
        last.next = addition;
        last = addition;
    }

    public void removeFirst() {
        volume -= first.getQuantity();
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.prev = null;
        }
    }

    public void cancel(Integer id) {
        Order order = OrderBook.orders.get(id);
        volume -= order.getQuantity();
        if (order.prev == null) {
            removeFirst();
        } else {
            order.prev.next = order.next;
            if (order.next == null) {
                last = order.prev;
            } else {
                order.next.prev = order.prev;
            }
        }
    }

    public Float getPrice() { return price;}

    public Integer getVolume() { return volume; }
}