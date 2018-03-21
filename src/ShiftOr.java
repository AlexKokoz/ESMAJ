/******************************************************************************
 *  Compilation:  javac ShiftOr.java
 *  Execution:    java ShiftOr pat txt
 *  Dependencies: -none-
 *
 *  Reads in two strings, the pattern and the input text, and
 *  searches for the pattern in the input text using the 
 *  Shift-Or algorithm.
 *  
 *  
 *
 *  % java ShiftOr atcgacgta gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: atcgacgta
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  match:                    atcgacgta       
 *
 *  % java ShiftOr gata gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: gata
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag 
 *  match:             gata                        
 *
 *  % java ShiftOr gctc gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: gctc
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag 
 *  match:                                          gctc
 *
 *  %  java ShiftOr gcatcgat gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: gcatcgat
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  match:   gcatcgat
 *
 *  % java ShiftOr cgacag gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: cgacag
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  match:                                    cgacag  
 *
 ******************************************************************************/

/**
 *  The {@code ShiftOr} class finds the first occurrence of a pattern string
 *  in a text string.
 *  <p>
 *  This implementation uses the Shift-Or algorithm.
 *  <p>
 */
public class ShiftOr {
	private static final int WORD_SIZE = 16; // word size	
	
	private int m;      // pattern length
	private int R;      // radix
	private int[] S;    // positions of the characters in the pattern
	private int lim;    // limit
	
	/**
     * Pre-processes the pattern string.
     *
     * @param pat the pattern string
     */
	public ShiftOr(String pat) {
		m = pat.length();
		R = 256;
		S = new int[R];
		
		// preprocessing
		for (int i = 0; i < R; ++i)
			S[i] = ~0;
		lim = 0;
		for(int i = 0, j = 1; i < m; ++i, j <<= 1) {
			S[pat.charAt(i)] &= ~j;
			lim |= j;			
		}
		lim = ~(lim >> 1);		
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
		if (m > WORD_SIZE)
			throw new IllegalArgumentException("Use pattern size <= word size.");
		int state = ~0;
		for(int i = 0; i < n; ++i) {
			state = (state << 1) | S[txt.charAt(i)];
			if (state < lim) 
				return i - m + 1;
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
		ShiftOr so = new ShiftOr(pattern);
		int offset = so.search(text);
		System.out.println("pattern: " + pattern);
		System.out.println("text:    " + text);
		System.out.print("match:   ");
		for (int i = 0; i < offset; ++i)
			System.out.print(" ");
		System.out.println(pattern);
	}
}
