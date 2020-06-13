# Travelling-salesman-problem-Prim-s-Minimum-Spanning-Tree-algorithm

Steps:
Initialize the set V with all the vertices of the graph G.
Initialize the set T with the starting vertex, initialize the set W = V - T.
Initialize the vector that stores the degree of the vertices (at the beginning all 0), initialize the list of edges where the path is stored.
Find the minimum cost edge A (u, w) such that vertex u belongs to set T, vertex w belongs to set W and the degree of u and w is less than 2.
Increase the degree of v and w (Vertices), add the edge A (u, w) to the list of edges of the tour,
add vertex w to the vertex set T of the tour and remove vertex w from the set W
Finally, select the edge A (u, w) whose vertices have a degree less than 2 and adding them to the edges of the tour, 
returns the list of edges of the tour.
