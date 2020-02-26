# Neo4j 4.0- Drivers & Authorization

Sample code that demonstrates a couple of ways for authorization via drivers in a Java application

## Naive Implementation

Create a driver per request.
See `SimpleController.getPersonNamesNaive()`

## Cached Implementation

Obtain driver from a cache
See `SimpleController.getPersonNamesCached()`

## AbstractRoutingDriver Implementation
A wrapping Driver dynamically selects the target driver and returns a Session
See `SimpleController.getPersonNamesCachedRouting()`