/******************************************************************************
 *  Compilation:  javac KarpRabin.java
 *  Execution:    java KarpRabin pat txt
 *  Dependencies: -none-
 *
 *  Reads in two strings, the pattern and the input text, and
 *  searches for the pattern in the input text using the 
 *  Rabin-Karp algorithm.
 *  
 *  
 *
 *  % java RabinKarp atcgacgta gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: atcgacgta
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  match:                    atcgacgta       
 *
 *  % java RabinKarp gata gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: gata
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag 
 *  match:             gata                        
 *
 *  % java RabinKarp gctc gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: gctc
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag 
 *  match:                                          gctc
 *
 *  %  java RabinKarp gcatcgat gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: gcatcgat
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  match:   gcatcgat
 *
 *  % java RabinKarp cgacag gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  pattern: cgacag
 *  text:    gcatcgatcagatatcgatcgacgtagcatgcacgacag
 *  match:                                    cgacag  
 *
 ******************************************************************************/

/**
 *  The {@code KarpRabin} class finds the first occurrence of a pattern string
 *  in a text string.
 *  <p>
 *  This implementation uses the Karp-Rabin algorithm.
 *  <p>
 */
public class KarpRabin {
	private String pat;  // pattern
	private int patHash; // pattern hash value
	private int m;       // pattern length
	private int d;       // 2^(m-1)
	
	/**
     * Pre-processes the pattern string.
     *
     * @param pattern the pattern string
     */
	public KarpRabin(String pat) {
		this.pat = pat;
		this.m = pat.length();
		
		// precompute 2^(m-1) for use in removing leading digit
		d = 1;
		for (int i = 1; i < m; ++i)
			d = (d << 1);
		patHash = hash(pat, m);
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
		if (n < m) return n;
		int txtHash = hash(txt, m);
		
		for (int i = 0; i <= n - m; ++i) {
			if (patHash == txtHash && match(txt, i)) 
				return i;
			if (i == n - m) break;
			txtHash = rehash(txt.charAt(i), txt.charAt(i + m), txtHash);
		}
		return n;
	}
	
	// Compute hash value for pattern[0..m-1]
	private int hash(String s, int m) {
		int hash = 0;
		for (int i = 0; i < m; ++i) {
			hash = (hash << 1) + s.charAt(i);
		}
		return hash;
	}
	
	// Does pattern[0..m-1] match txt[start..start + m - 1]?
	private boolean match(String txt, int start) {
		for (int i = 0; i < m; ++i) {
			if (pat.charAt(i) != txt.charAt(start + i))
				return false;
		}
		return true;
	}
	
	// Recompute hash by removing leading digit, and adding trailing digit
	private int rehash(char leading, char trailing, int hash) {
		return ((hash - leading * d) << 1) + trailing;
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
		KarpRabin kr = new KarpRabin(pattern);
		int offset = kr.search(text);
		System.out.println("pattern: " + pattern);
		System.out.println("text:    " + text);
		System.out.print("match:   ");
		for (int i = 0; i < offset; ++i)
			System.out.print(" ");
		System.out.println(pattern);
		
		
	}

}
