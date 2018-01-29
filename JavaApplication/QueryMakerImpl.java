package phoneticinventory;

public interface QueryMakerImpl {

	/**
	 * Queries language_vowel to find languages with specified vowel.
	 * 
	 * @param vowel
	 *            the unicode value of the vowel to be found.
	 * @return the languages with this vowel
	 */
	public abstract String queryVowel(String vowel);

	/**
	 * Queries language_consonant to find languages with the specified consonant
	 * 
	 * @param consonant
	 *            unicode value of the consonant to be found
	 * @return list of languages with this consonant
	 */
	public abstract String queryConsonant(String consonant);

	/**
	 * Queries language_consonant and language_vowel to find languages with the
	 * specified phoneme
	 * 
	 * @param phoneme
	 *            unicode value of the phoneme to be found
	 * @return list of language names with this phoneme
	 */
	public abstract String queryPhoneme(String phoneme);

	/**
	 * Queries to find languages without the specified phoneme
	 * 
	 * @param phoneme
	 *            unicode value of the phoneme to be found
	 * @return list of language names, families, and regions without this phoneme
	 */
	public abstract String queryNoPhoneme(String phoneme);

	/**
	 * Queries languages to find languages within the specified family.
	 * 
	 * @param familyStr
	 *            the family to be searched for
	 * @return the languages' name, family, and region
	 */
	public abstract String queryLanguageByFamily(String familyStr);

	/**
	 * Queries languages to find languages with the iso639 code
	 * 
	 * @param isoValue
	 *            the iso639 code to be searched for
	 * @return the languages' name, family, and region
	 */
	public abstract String queryByIso(String isoValue);

	/**
	 * Checks if the connection was successful
	 * 
	 * @return the isDbUp
	 */
	public abstract boolean isDbUp();

	/**
	 * Sets isDbUp
	 * 
	 * @param isDbUp
	 *            the isDbUp to set
	 */
	public abstract void setDbUp(boolean isDbUp);

}