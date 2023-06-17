# Trade-Engine
This is a trade engine I built to model security exchanges.
The modules implement a matching engine and orderbook to supports three primary trading functions:
- post: users can post orders in O(1)
- cancel: users can cancel pending orders in O(1)
- get market demand: users can pull data on volume at each price point to understand the depth of the bid and ask sides

Unfinished
