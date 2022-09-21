import java.util.LinkedList;
import java.util.HashMap;

public class OrderBook {
    public static HashMap<Integer, Order> orders;
    public static HashMap<Float, Level> levels;
    public static Level rootAsks;
    public static Level rootBids;
    public static Float highestBid;
    public static Float smallestAsk;
    public static LinkedList<Trade> history;


    public void post(Order addition) {

        if (addition.getType().equals("ask")) {
            matchAsk(addition);
        } else {
            matchBid(addition);
        }
        if (addition.getQuantity() > 0) {
            if (levels.containsKey(addition.getPrice())) {
                levels.get(addition.getPrice()).append(addition);
            } else {
                levels.put(addition.getPrice(), new Level(addition));
            }
        }
    }

    public void matchBid(Order order) {
        if (smallestAsk == null || order.getPrice() < smallestAsk) {
            if (levels.containsKey(order.getPrice())) {
                levels.get(order.getPrice()).append(order);
            } else {
                levels.put(order.getPrice(), new Level(order));
                if (rootBids == null) {
                    rootBids = levels.get(order.getPrice());
                } else {
                    addLevel(rootBids, levels.get(order.getPrice()));
                }
                if (order.getPrice() > highestBid) {
                    highestBid = order.getPrice();
                }
            }
        } else {
            Level priority = levels.get(smallestAsk);
            while (order.getQuantity() > 0) {
                history.add(new Trade(order, priority.first));
                if (order.getQuantity() < priority.first.getQuantity()) {
                    priority.first.setQuantity(priority.first.getQuantity() - order.getQuantity());
                } else if (order.getQuantity().equals(priority.first.getQuantity())) {
                    priority.removeFirst();
                } else {
                    order.setQuantity(order.getQuantity() - priority.first.getQuantity());
                    priority.removeFirst();

                    if (priority.first == null && priority.last == null) {
                        if (priority.right == null) {
                            priority = priority.parent;
                        } else {
                            Level curr = priority.right;
                            while (curr.left != null) {
                                curr = curr.left;
                            }
                            priority = curr;
                        }
                        smallestAsk = priority.getPrice();
                    }
                }
            }
        }
    }

    public Level addLevel(Level root, Level addition) {
        if (root == null) {
            return null;
        } else {
            Level child;
            if (addition.getPrice() > root.getPrice()) {
                child = addLevel(root.right, addition);
                if (child == null) {
                    root.right = addition;
                }
            } else {
                child = addLevel(root.left, addition);
                if (child == null) {
                    root.left = addition;
                }
            }
            return root;
        }
    }

    public void matchAsk(Order order) {
        if (highestBid == null || order.getPrice() > highestBid) {
            if (levels.containsKey(order.getPrice())) {
                levels.get(order.getPrice()).append(order);
            } else {
                levels.put(order.getPrice(), new Level(order));
                if (rootAsks == null) {
                    rootAsks = levels.get(order.getPrice());
                } else {
                    addLevel(rootAsks, levels.get(order.getPrice()));
                }
                if (order.getPrice() > highestBid) {
                    highestBid = order.getPrice();
                }
            }
        } else {
            Level priority = levels.get(highestBid);
            while (order.getQuantity() > 0) {
                history.add(new Trade(order, priority.first));
                if (order.getQuantity() < priority.first.getQuantity()) {
                    priority.first.setQuantity(priority.first.getQuantity() - order.getQuantity());
                } else if (order.getQuantity().equals(priority.first.getQuantity())) {
                    priority.removeFirst();
                } else {
                    order.setQuantity(order.getQuantity() - priority.first.getQuantity());
                    priority.removeFirst();

                    if (priority.first == null && priority.last == null) {
                        if (priority.left == null) {
                            priority = priority.parent;
                        } else {
                            Level curr = priority.left;
                            while (curr.right != null) {
                                curr = curr.right;
                            }
                            priority = curr;
                        }
                        highestBid = priority.getPrice();
                    }
                }
            }
        }
    }


    public void cancelOrder(Integer id) {
        Order order = orders.get(id);
        Level level = levels.get(order.getPrice());
        level.cancel(id);

        if (level.getVolume() == 0) {
            if (smallestAsk.equals(level.getPrice())) {
                if (level.right != null) {
                    Level curr = level.right;
                    while (curr.left != null) {
                        curr = curr.left;
                    }
                    smallestAsk = curr.getPrice();
                } else if (level.parent != null) {
                    smallestAsk = level.parent.getPrice();
                } else {
                    smallestAsk = null;
                }
            } else if (highestBid.equals(level.getPrice())) {
                if (level.left != null) {
                    Level curr = level.left;
                    while (curr.right != null) {
                        curr = curr.right;
                    }
                    highestBid = curr.getPrice();
                } else if (level.parent != null) {
                    highestBid = level.parent.getPrice();
                } else {
                    highestBid = null;
                }
            }
        }

    }

    public void getMarketDemandBids(Level node) {
        if (node != null) {
            getMarketDemandBids(node.right);
            if (node.getVolume() > 0) {
                System.out.println("Price: " + node.getPrice() + " Volume: " + node.getVolume());
            }
            getMarketDemandBids(node.left);
        }
    }

    public void getMarketDemandAsks(Level node) {
        if (node != null) {
            getMarketDemandBids(node.left);
            if (node.getVolume() > 0) {
                System.out.println("Price: " + node.getPrice() + " Volume: " + node.getVolume());
            }
            getMarketDemandBids(node.right);
        }
    }

    public void getMarketDemand() {
        System.out.println("Asks:");
        getMarketDemandAsks(OrderBook.rootAsks);
        System.out.println();
        System.out.println("Bids:");
        getMarketDemandBids(OrderBook.rootBids);
    }
}
