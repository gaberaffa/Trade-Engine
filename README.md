# Trade-Engine
This is Trade Engine source code I built to model security exchanges.
The code implements a matching engine and orderbook and supports three primary functions:
- post: users can post orders in O(1)
- cancel: users can cancel pending orders in O(1)
- get market demand: users can pull data regarding the depth and confidence of each limit for the bid and ask side
