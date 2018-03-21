/******************************************************************************
 *  Compilation:  javac QuickSearch.java
 *  Execution:    java QuickSearch pat txt
 *  Dependencies: -none-
 *
 *  Reads in two strings, the pattern and the input text, and
 *  searches for the pattern in the input text using a 
 *  simplification of the Boyer-Moore algorithm.
 *  
 *  
 *
 *  % java QuickSearch atcgacgta gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: atcgacgta
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  match:                    atcgacgta       
 *
 *  % java QuickSearch gata gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: gata
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag 
 *  match:             gata                        
 *
 *  % java QuickSearch gctc gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: gctc
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag 
 *  match:                                          gctc
 *
 *  %  java QuickSearch gcatcgat gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: gcatcgat
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  match:   gcatcgat
 *
 *  % java QuickSearch cgacag gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: cgacag
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  match:                                    cgacag  
 *
 ******************************************************************************/

/**
 *  The {@code QuickSearch} class finds the first occurrence of a pattern 
 *  string in a text string.
 *  <p>
 *  This implementation is a simplification of the Boyer-Moore algorithm 
 *  (using only the bad-character shift).
 *  
 */
public class QuickSearch {
    private final int R;    // the radix
    private int[] qsBc;     // the bad-character shift table 
    private char[] pattern; // either the character array for the pattern
    private String pat;     // or the pattern string
    
    /**
     * Preprocesses the pattern string.
     *
     * @param pat the pattern string
     */
    public QuickSearch(String pat) {
        this.R =  256;
        this.pat = pat;        
        
        //initialize bad-character shift table
        preQsBc(pat);
        
    }
    
    /**
     * Preprocesses the pattern string.
     *
     * @param pattern the pattern string
     * @param R the alphabet size
     */
    public QuickSearch(char[] pattern, int R) {
        this.R = R;
        this.pattern = new char[pattern.length];
        for (int i = 0; i < pattern.length; i++)
            this.pattern[i] = pattern[i];
        
      //initialize bad-character shift table
        preQsBc(pattern);
    }
    
    /**
     * initializes the bad character shift table according to
     * the given string; essentially it calculates a skip step 
     * for each element of the radix, that is the length of the string 
     * minus the position of the most right occurrence of each character
     * in the given string,or the length of the string plus one if a character 
     * doesn't exist in the given string
     *   
     * @param s the given string
     */
    private void preQsBc(String s) {
        int m = s.length();
        int i;
        qsBc = new int[R];
        for (i = 0; i < this.R; i++)
            qsBc[i] = m + 1;
        for (i = 0; i < m; i++)
            qsBc[s.charAt(i)] = m - i;
    }
    
    /**
     * initializes the bad character shift table according to
     * the given string; essentially it calculates a skip step 
     * for each element of the radix, that is the length of the string 
     * minus the position of the most right occurrence of each character
     * in the given string,or the length of the string plus one if a character 
     * doesn't exist in the given string
     *   
     * @param s the given string
     */
    private void preQsBc(char[] s) {
        int m = s.length;
        int i;
        qsBc = new int[R];
        for (i = 0; i < this.R; i++)
            qsBc[i] = m + 1;
        for (i = 0; i < m; i++)
            qsBc[s[i]] = m - i;
    }     
    
    /**
     * Returns the index of the first occurrence of the pattern string
     * in the text string.
     *
     * @param  txt the text string
     * @return the index of the first occurrence of the pattern string
     *         in the text string; n if no such match
     */
    public int search(String txt) {
        int n = txt.length();
        int m = pat.length();
        int skip;
        for(int i = 0; i <= n - m; i += skip) {
            int j;
            for (j = 0; j < m; j++)
                if (pat.charAt(j) != txt.charAt(i + j)) break;
            if (j == m) return i;
            skip = qsBc[txt.charAt(i + m)];
        }
        return n;
    }    
    
    /**
     * Returns the index of the first occurrence of the pattern string
     * in the text string.
     *
     * @param  txt the text string
     * @return the index of the first occurrence of the pattern string
     *         in the text string; n if no such match
     */
    public int search(char[] txt) {
        int n = txt.length;
        int m = pattern.length;
        int skip;
        for(int i = 0; i <= n - m; i += skip) {
            int j;
            for (j = 0; j < m; j++)
                if (pattern[j] != txt[i + j]) break;
            if (j == m) return i;
            skip = qsBc[txt[i + m]];
        }
        return n;
    }    
    
    
    /** 
     * Takes a pattern string as command-line argument, and reads an input text file;
     * searches for the pattern string in the text file; and prints
     * the first occurrence of the pattern string in the text file.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];

        QuickSearch qs = new QuickSearch(pat);
        int offset = qs.search(txt);

        // print results
        System.out.println("text:    " + txt);
        System.out.print("pattern: ");
        for (int i = 0; i < offset; i++)
        	System.out.print(" ");
        System.out.println(pat);
    }
}


