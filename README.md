/* *****************************************************************************
 *  Name: Mohammad Alqudah
 *  NetID: malqudah
 *  Precept: P05
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 6: WordNet


/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in synsets.txt. Why did you make this choice?
 **************************************************************************** */
The data structure I chose to store the information from synsets.txt was a
symbol table, in which the key was an Integer (synset ID number) and the value
was a synset, where each ID number points to a synset.
The elementary symbol table fulfilled the necessary performance requirements.
it uses space linear relative to the input size, and its methods fulfill
the logn time constraint necessary for some of the wordnet methods


/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in hypernyms.txt. Why did you make this choice?
 **************************************************************************** */
Similar to the synset input, i also used a symbol table to represent the hyper
nyms, but with a slight twist. Instead of using the id number as a key and
synset as a value, I used a noun as a key (or rather, a hypernym) and queue
 of Integers as a value, since each id number can have more than one hypernym.
this also fulfilled certain performance requirements.
for example, using the contains method from the symbol table API takes
log n time, where n is the number of key=value pairs. for this assignment,
this allows isNoun to be fulfilled.


/* *****************************************************************************
 *  Describe concisely the algorithm you use in the constructor of
 *  ShortestCommonAncestor to check if the digraph is a rooted DAG.
 *  What is the order of growth of the worst-case running times of
 *  your algorithm? Express your answer as a function of the
 *  number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.)
 **************************************************************************** */

Description: To check if the digraph was a rooted DAG, i split up the work to
two private helper methods, which split the work of checking. The first
method involved making use of topological order and the Topological class.
based off a statement from the textbook, "A digraph has a topological order
if and only if it is a DAG", i simply checked if the digraph was a DAG via
checking for topological order.
for the second method to check if the DAG was rooted, i based it off the claim
that a DAG is rooted if there is only one vertex with 0 adjacent vertices.
Essentially, i ran through all of the vertices in the digraph and kept track
of the vertices with 0 adjacent vertices. if there was only one vertex with
0 adjacent vertices, then this was the root, and the DAG was rooted.



Order of growth of running time: 3V + 2E
O(V + E)


/* *****************************************************************************
 *  Describe concisely your algorithm to compute the shortest common ancestor
 *  in ShortestCommonAncestor. For each method, give the order of growth of
 *  the best- and worst-case running times. Express your answers as functions
 *  of the number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.)
 *
 *  If you use hashing, assume the uniform hashing assumption so that put()
 *  and get() take constant time per operation.
 *
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't forget
 *  to count the time needed to initialize the marked[], edgeTo[], and
 *  distTo[] arrays.
 **************************************************************************** */

Description:
To compute the shortest common ancestor, i created two BreadthFirstDirectedPaths
objects using the given digraph and vertices. next i assigned integer variables
for the min length and ancestor to aribitrary values; the max value for an int
and -1 respectively. i then went through all fo the vertices in the digraph
and calculated the distance from the input vertices to the given one in theloop.
if the distance was shorter than the smallest distance, update it and set the
new shortest common ancestor.


                                 running time
method                  best case            worst case
--------------------------------------------------------
length()                 V + E                    V + E

ancestor()               V + E                   V + E

lengthSubset()            V + E                  V + E

ancestorSubset()        V + E                    V + E



/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */

Maia and Molly

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */


/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */




/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
