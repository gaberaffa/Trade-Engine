public class Trade {
    private Float price;
    private Integer quantity;
    private Integer bidId;
    private Integer askId;

    public Trade(Order bid, Order ask) {
        this.bidId = bid.getId();
        this.askId = ask.getId();

        if (bid.getTime() < ask.getTime()) {
            this.price = bid.getPrice();
        } else {
            this.price = ask.getPrice();
        }
        this.quantity = Math.min(bid.getQuantity(), ask.getQuantity());
    }
}