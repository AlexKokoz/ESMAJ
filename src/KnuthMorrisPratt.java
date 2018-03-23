/******************************************************************************
 *  Compilation:  javac KnuthMorrisPratt.java
 *  Execution:    java  KnuthMorrisPratt pat txt
 *  Dependencies: -none-
 *
 *  Reads in two strings, the pattern and the input text, and
 *  searches for the pattern in the input text using the 
 *  Knuth-Morris-Pratt algorithm.
 *  
 *  The preprocessing phase takes O(m) space and time, m being
 *  the pattern length. The searching phase can be done in O(m + n)
 *  time. The Knuth-Morris-Pratt algorithm performs 2n - 1 character 
 *  comparisons at most, n being the text length.
 *  
 *  
 *
 *  % java KnuthMorrisPratt atcgacgta gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: atcgacgta
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  match:                    atcgacgta       
 *
 *  % java KnuthMorrisPratt gata gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: gata
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag 
 *  match:             gata                        
 *
 *  % java KnuthMorrisPratt gctc gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: gctc
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag 
 *  match:                                          gctc
 *
 *  %  java KnuthMorrisPratt gcatcgat gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: gcatcgat
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  match:   gcatcgat
 *
 *  % java KnuthMorrisPratt cgacag gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: cgacag
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  match:                                    cgacag  
 *
 ******************************************************************************/

/**
 *  The {@code KnuthMorrisPratt} class finds the first occurrence of a pattern string
 *  in a text string.
 *  <p>
 *  This implementation uses the Knuth-Morris-Pratt algorithm.
 *  <p>
 */
public class KnuthMorrisPratt {
	private String pat; // pattern
	private int m;      // pattern length
	private int[] next; // shift array
	
	/**
     * Preprocesses the pattern string.
     *
     * @param pat the pattern string
     */
	public KnuthMorrisPratt(String pat) {
		this.pat = pat;
		m = pat.length();
		next = new int[m + 1];
		next[0] = -1;
		int j = -1;
		int i = 0;
		while(i < m) {
			while(j > 1 && pat.charAt(i) != pat.charAt(j))
				j = next[j];
			i++;
			j++;
			if (i < m && pat.charAt(i) == pat.charAt(j))
				next[i] = next[j];
			else
				next[i] = j;
		}
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
		int i = 0;
		int j = 0;
		while(j < n) {
			while(i > -1 && pat.charAt(i) != txt.charAt(j))
				i = next[i];
			i++;
			j++;
			if (i >= m) return j - i;
		}
		return n;
	}

	/** 
     * Takes a pattern string and an input string as command-line arguments;
     * searches for the pattern string in the text string; and prints
     * the first occurrence of the pattern string in the text string.
     *
     * @param args the command-line arguments
     */
	public static void main(String[] args) {
		String pattern = args[0];
		String text = args[1];
		KnuthMorrisPratt kmp = new KnuthMorrisPratt(pattern);
		int offset = kmp.search(text);
		System.out.println("pattern: " + pattern);
		System.out.println("text:    " + text);
		System.out.print("match:   ");
		for (int i = 0; i < offset; ++i)
			System.out.print(" ");
		System.out.println(pattern);
	}

}
