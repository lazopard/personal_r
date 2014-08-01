/*
 * Algorithm:
 *
 * For a single string it works as follows:
 * Next, lower case vowels are removed (starting at the right) followed by lower case consonants. 
 * Finally if the abbreviation is still longer than minlength upper case letters are stripped.
 * 
 * Characters are always stripped from the end of the word first. 
 * If an element of names.arg contains more than one word 
 * then at least one letter from each word will be retained.
 *
 */

public abstract class abbv {

    public static String abbreviate(String s, int n, boolean dot, 
                                    boolean strict, String method) {

        //filter leading and trailing space
        newS = s.trim();
        if (newS.length >= n)
            return newS;

        //filter spaces
        newS = newS.replaceAll("\\s+","");
        if (newS.length >= n)
            return newS;
        for(int i = newS.length - 1; i >= 0; i--) {
        }
    }

    public static void main(String [] args) {
        String ex1 = "Leonardo Azopardo";
        String ex2 = "abc";
        String ex3 = "";

        String ex1_ab = abbreviate(ex1);
        String ex2_ab = abbreviate(ex2);
        String ex3_ab = abbreviate(ex3);

        System.out.println(ex1_ab);
        System.out.println(ex2_ab);
        System.out.println(ex3_ab);
    }

}

