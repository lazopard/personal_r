public abstract class abbv {

    public static String abbreviate(String s, int n) {
        String [] tokenizedS = s.split(" ");

        if (tokenizedS.length == n) {
            String finalString = new String[tokenizedS.length];
            for(int j = 0; j < tokenizedS.length; j++) {
                finalString[i] = tokenizedS[i][0];
            }
            return finalString;
        }

        int j = 0;
        String finalString = new String[n];

        for(int i = 0; i < n; i++) {
        }

        return finalString;
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

