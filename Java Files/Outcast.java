/* *****************************************************************************
 *  Name:    Mohammad Alqudah
 *  NetID:   malqudah
 *  Precept: P05
 *
 *
 *  Description:  Given an array of WordNet nouns, this class finds the noun
 * that is least related to the others, the outcast,
 *  where least related means it has the
 * largest overalldistance. overalldistance is calculated by
 * computing the distance between
 * a noun and every other one.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    // creates wordnet object, initialzied in constructor
    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {

        int maxDist = 0;
        String outcast = "";

        // uses for loop to go through all the nouns
        for (int i = 0; i < nouns.length; i++) {
            int dist = 0;
            // for every string i, compare it to all the other strings in the
            // array, and calculate the distance
            for (String others : nouns) {
                if (!others.equals(nouns[i])) {
                    dist += wordNet.distance(nouns[i], others);
                }
            }
            // if the distance calculated is the largest so far, update the
            // max distance and outcast
            if (dist > maxDist) {
                maxDist = dist;
                outcast = nouns[i];
            }
        }
        return outcast;
    }

    // test client (see below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
