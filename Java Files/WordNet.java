/* *****************************************************************************
 *  Name:    Mohammad Alqudah
 *  NetID:   malqudah
 *  Precept: P05
 *
 *
 *  Description:  Models a WordNet, which takes a set of synsets and hypernyms
 * and uses them to create/model a digraph. the digraph being modeled must be
 * a rooted directed acyclic graph. Models the synsets/nouns using a
 * symbol table. each vertex represents an id number and a synset, with its
 * hypernyms pointing towards it. class constructs the word net and supports
 * finding the shortest common ancestor between two given nouns, as well
 * as the distance between two nouns or a subset.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    // ST for each synset, where an id number serves as key for a synset
    private final ST<Integer, String> stSynset;

    // ST for each noun set, where each noun in a synset serves as a key
    // for the id number
    private final ST<String, Queue<Integer>> nounSt;

    // shortest common ancestor object
    private final ShortestCommonAncestor sca;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        // null argument exception
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("Null input");
        }

        // read in synset lines
        In syn = new In(synsets);

        // initialize ST instance variables
        stSynset = new ST<>();
        nounSt = new ST<>();

        // variable to be incremented w iteration of the while loop; keeps
        // track of the size of the digraph
        int size = 0;

        // while synset input is not empty
        while (!syn.isEmpty()) {

            // increments size
            size++;

            // stores the synset input/line in a String
            String line = syn.readLine();

            // splits line based on a comma; stores this in an array
            // id number then synset then gloss
            String[] splitStr = line.split(",");

            // stores the id number of the line/synset, which is the first
            // part of the split string
            int idNum = Integer.parseInt(splitStr[0]);

            // saves the individual nouns that constitute a synset, separated
            // spaces
            String[] nounStr = splitStr[1].split(" ");

            // put the nouns with its ID in the ST
            stSynset.put(idNum, splitStr[1]);


            // for the length of the array of nouns after the split by a space,
            // add each individual noun
            // to the ST with the corresponding id number
            for (int i = 0; i < nounStr.length; i++) {

                // if the noun is already present
                if (nounSt.contains(nounStr[i])) {
                    nounSt.get(nounStr[i]).enqueue(idNum);
                }
                else {
                    Queue<Integer> q1 = new Queue<Integer>();
                    q1.enqueue(idNum);
                    nounSt.put(nounStr[i], q1);
                }
            }
        }

        // initializes the wordNet digraph to be empty of size of the synset ST,
        // where each synset is a vertex in the digraph
        Digraph wordNet = new Digraph(size);

        // read in hypernym lines
        In hyp = new In(hypernyms);

        // while the hypernym input is not empty
        while (!hyp.isEmpty()) {

            // set the current line
            String line = hyp.readLine();

            // split the input line based off comma
            String[] splitStr = line.split(",");

            // id number of each line/hypernym
            int idNum = Integer.parseInt(splitStr[0]);

            // checks if splitStr contains more than 2 elements; if so, begin
            // for loop
            if (splitStr.length > 1) {
                // start the loop at the second element of splitStr, not the first
                // because the first element is the idNum and not a hypernym
                for (int i = 1; i < splitStr.length; i++) {

                    // set the given hypernym to an int variable
                    int q = Integer.parseInt(splitStr[i]);

                    // connect an edge from the idNum to the hypernym in question;
                    // repeats for all hypernyms in the line
                    wordNet.addEdge(idNum, q);
                }
            }
        }
        sca = new ShortestCommonAncestor(wordNet);
    }

    // all WordNet nouns
    public Iterable<String> nouns() {
        return nounSt;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nounSt.contains(word);
    }

    // private helper method for sca and distance exceptions
    private void nounExcp(String noun1, String noun2) {
        if (!isNoun(noun1) || !isNoun(noun2)) {
            throw new IllegalArgumentException("Noun input is not in WordNet");
        }
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        nounExcp(noun1, noun2);

        Queue<Integer> idOne = nounSt.get(noun1);
        Queue<Integer> idTwo = nounSt.get(noun2);

        int ancestor = sca.ancestorSubset(idOne, idTwo);

        return stSynset.get(ancestor);
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        nounExcp(noun1, noun2);

        Queue<Integer> idOne = nounSt.get(noun1);
        Queue<Integer> idTwo = nounSt.get(noun2);

        return sca.lengthSubset(idOne, idTwo);
    }

    // unit testing (required)
    public static void main(String[] args) {

        WordNet w1 = new WordNet(args[0], args[1]);
        StdOut.println("Does the WordNet contain letter o? " + w1.isNoun("o"));
        StdOut.println("Distance between letter o and b? " + w1.distance("o", "b"));
        StdOut.println("SCA of j and i: " + w1.sca("j", "i"));
        StdOut.println("All nouns: ");
        for (String s1 : w1.nouns()) {
            StdOut.println(s1);
        }
    }
}
