/* *****************************************************************************
 *  Name:    Mohammad Alqudah
 *  NetID:   malqudah
 *  Precept: P05
 *
 *  Description: Given a digraph (that must be rooted and a directed acyclic
 * graph, this class finds 4 things. First, given two vertices in the digraph,
 * this class finds the length of the shortest common ancestral path between
 * them. in addition, it finds the shortest common ancestor. Similarly, given
 * two subsets of vertices, this class can find the length of the shortest
 * ancestral path between them, as well as the shortest common ancestor between
 * them. implements breadsfirstdirectedpaths to compare distances and to check
 * for all paths from a given vertex.
 *
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Topological;

public class ShortestCommonAncestor {

    // instance variable for the rooted DAG
    private final Digraph digraph;


    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException("Digraph is null");
        }
        dagCheck(G);
        rootCheck(G);
        digraph = new Digraph(G);
    }

    // private helper method to check if the digraph is a DAG
    private void dagCheck(Digraph G) {
        Topological t1 = new Topological(G);
        if (!t1.hasOrder()) {
            throw new IllegalArgumentException("Digraph is not a DAG");
        }
    }

    // private helper method to check if DAG is rooted
    // to be rooted, only one vertex needs to have an outdegree of 0
    // therefore the root has no adjacent vertices
    private void rootCheck(Digraph G) {

        int vertexWithNoAdj = 0;
        // for loop to go through all of the vertices
        for (int i = 0; i < G.V(); i++) {
            // for every vertex, check the edges/adjacent nodes
            // set a count variable initially to 0; if a given vertex has an
            // adjacent vertex, then increment the count variable
            int count = 0;
            for (int ignored : G.adj(i)) {
                count++;
            }
            // if this given vertex does not have any adjacent vertices, then
            // incremenet the vertex with no adj counter
            if (count == 0) {
                vertexWithNoAdj++;
            }
        }
        // since a rooted DAG can only have one root (that is, one vertex with
        // no adjacent vertices, we check the vertex with no adj counter;
        // if it is not 1, then the DAG is not rooted.
        if (vertexWithNoAdj != 1) {
            throw new IllegalArgumentException("Digraph is not rooted");
        }
    }


    // private helper method for the vertex exceptions
    private void intExcep(int v, int w) {
        if (v < 0 || w < 0) {
            throw new IllegalArgumentException("V and W are not in the Digraph");
        }
        if (v > (digraph.V() - 1) || w > (digraph.V() - 1)) {
            throw new IllegalArgumentException("V and W are not in the Digraph");
        }
    }


    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        intExcep(v, w);

        BreadthFirstDirectedPaths bV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bW = new BreadthFirstDirectedPaths(digraph, w);

        int minLength = Integer.MAX_VALUE;
        // use a for loop to check all the vertices
        for (int i = 0; i < digraph.V(); i++) {
            // make sure that a path to given vertex exists before checking
            // distance
            if (!bV.hasPathTo(i) || !bW.hasPathTo(i)) {
                continue;
            }
            // calculate the length to given vertex, if it is the smallest,
            // update length
            int length = bW.distTo(i) + bV.distTo(i);
            if (length < minLength) {
                minLength = length;
            }
        }
        return minLength;
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        intExcep(v, w);

        BreadthFirstDirectedPaths bV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bW = new BreadthFirstDirectedPaths(digraph, w);

        int minLength = Integer.MAX_VALUE;
        int ancestor = -1;
        // use a for loop to check all the vertices
        for (int i = 0; i < digraph.V(); i++) {
            if (!bV.hasPathTo(i) || !bW.hasPathTo(i)) {
                continue;
            }
            int length = bW.distTo(i) + bV.distTo(i);
            if (length < minLength) {
                minLength = length;
                ancestor = i;
            }
        }
        return ancestor;
    }

    // private helper method for the subset exceptions
    private void subChecks(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null) {
            throw new IllegalArgumentException("Subset is null");
        }
        for (Integer a : subsetA) {
            if (a == null) {
                throw new IllegalArgumentException(
                        "vertex is null");
            }
            if (a > (digraph.V() - 1)) {
                throw new IllegalArgumentException(
                        "vertex in subset is out of bounds");
            }
            if (a < 0) {
                throw new IllegalArgumentException(
                        "vertex is negative");
            }


        }
        for (Integer b : subsetB) {
            if (b == null) {
                throw new IllegalArgumentException(
                        "vertex is null");
            }
            if (b > (digraph.V() - 1)) {
                throw new IllegalArgumentException(
                        "vertex in subset is out of bounds");
            }
            if (b < 0) {
                throw new IllegalArgumentException(
                        "vertex is negative");
            }
        }
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        subChecks(subsetA, subsetB);

        BreadthFirstDirectedPaths bA = new BreadthFirstDirectedPaths(digraph, subsetA);
        BreadthFirstDirectedPaths bB = new BreadthFirstDirectedPaths(digraph, subsetB);

        int minSubLength = Integer.MAX_VALUE;
        for (int i = 0; i < digraph.V(); i++) {
            if (!bA.hasPathTo(i) || !bB.hasPathTo(i)) {
                continue;
            }
            int length = bA.distTo(i) + bB.distTo(i);
            if (length < minSubLength) {
                minSubLength = length;
            }
        }
        return minSubLength;
    }


    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        subChecks(subsetA, subsetB);

        BreadthFirstDirectedPaths bA = new BreadthFirstDirectedPaths(digraph, subsetA);
        BreadthFirstDirectedPaths bB = new BreadthFirstDirectedPaths(digraph, subsetB);

        int subAncestor = -1;
        int minSubLength = Integer.MAX_VALUE;
        for (int i = 0; i < digraph.V(); i++) {
            if (!bA.hasPathTo(i) || !bB.hasPathTo(i)) {
                continue;
            }
            int length = bA.distTo(i) + bB.distTo(i);
            if (length < minSubLength) {
                minSubLength = length;
                subAncestor = i;
            }
        }

        return subAncestor;
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);

        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();


            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);


            Queue<Integer> testOne = new Queue<>();
            Queue<Integer> testTwo = new Queue<>();

            int t = StdIn.readInt();
            int s = StdIn.readInt();
            int y = StdIn.readInt();
            int z = StdIn.readInt();
            testTwo.enqueue(t);
            testTwo.enqueue(s);
            testTwo.enqueue(y);
            testTwo.enqueue(z);
            int a = StdIn.readInt();
            int b = StdIn.readInt();
            int c = StdIn.readInt();
            int d = StdIn.readInt();
            testOne.enqueue(a);
            testOne.enqueue(b);
            testOne.enqueue(c);
            testOne.enqueue(d);

            StdOut.println("Shortest common ancestor of subset: " +
                                   sca.ancestorSubset(testOne, testTwo));
            StdOut.println("length of shortest ancestral path: " +
                                   sca.lengthSubset(testOne, testTwo));

        }
    }
}

