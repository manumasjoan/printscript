


public class XXXX {

    private static String getNextWord(LexicalRangeState lexicalRangeState, String input) {
        StringBuilder string = new StringBuilder();

        if (firstCharIsLetter(lexicalRangeState, input)) {

            string.append(input.charAt(lexicalRangeState.getIndex()));

            int i= lexicalRangeState.getIndex();


            while(i + 1 < input.length() && Character.isLetter(input.charAt(i + 1))){
                char nextChar=input.charAt(i + 1);
                string.append(nextChar);
                i++;
            }

        }

        return string.toString();

    }

    private static boolean firstCharIsLetter(LexicalRangeState lexicalRangeState, String input) {
        return Character.isLetter(input.charAt(lexicalRangeState.getIndex()));
    }
}
