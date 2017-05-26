package frisch.java.util;

import frisch.java.util.google.diff_match_patch;
import java.util.*;

/**
 * Created by luke on 6/28/16.
 */
public class ResultProcessor {

    public static int compare(String result, String standard) {

        if (result.equals(standard)) return 100;

        System.out.println(result);
        System.out.println(standard);

        diff_match_patch comparator = new diff_match_patch();
        LinkedList<diff_match_patch.Diff> value = comparator.diff_main(result, standard);
        int idx = comparator.diff_levenshtein(value);
        double score = 100.0 - (100.0 * (double)idx / (double)Math.max(result.length(), standard.length()));
/*        if (standard.length() < result.length()-1) {
            return -1;
        }
        if (result.substring(0, result.length()-1).equals(standard.substring(0, result.length()-1))) {
            return 100;
        }
*/

        return (int)Math.round(score);
    }

}
