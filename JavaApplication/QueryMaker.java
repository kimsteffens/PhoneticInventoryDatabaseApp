package phoneticinventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JButton;

/**
 * QueryMaker connects to the Phonetic Inventory Database and can create and
 * execute queries on the database. It is to be used by QueryPanel.
 * 
 * @author Kim Steffens
 */
public class QueryMaker implements QueryMakerImpl {

	private boolean isDbUp;
	private Connection DBcon;

	/**
	 * Creates the connection to the database (initializes DBcon)
	 */
	public QueryMaker() {

		// database to connect to phonetic inventory
		String url = "jdbc:postgresql://localhost:5432/phonetic_inventory";
		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "none");

		// connect to database
		try {
			DBcon = DriverManager.getConnection(url, props);
			setDbUp(true);

		} catch (SQLException e) {
			setDbUp(false);
			e.printStackTrace();
		}

	}

	/**
	 * Queries language_vowel to find languages with specified vowel.
	 * 
	 * @param vowel
	 *            the unicode value of the vowel to be found.
	 * @return the languages with this vowel
	 */
	@Override
	public String queryVowel(String vowel) {

		// the list of languages found
		String returnText = " ";
		try {
			// create sql statement and execute
			PreparedStatement myStatement = DBcon
					.prepareStatement("SELECT name FROM language_vowel WHERE vowel_unicode = ?");
			myStatement.setString(1, vowel);
			ResultSet r = myStatement.executeQuery();

			// get the list of languages
			while (r.next()) {
				returnText += r.getString("name") + " \n ";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnText;
	}

	/**
	 * Queries language_consonant to find languages with the specified consonant
	 * 
	 * @param consonant
	 *            unicode value of the consonant to be found
	 * @return list of languages with this consonant
	 */
	@Override
	public String queryConsonant(String consonant) {

		// the list of languages to be returned
		String returnText = " ";
		try {
			// create and execute query
			PreparedStatement myStatement = DBcon
					.prepareStatement("SELECT name FROM language_consonant WHERE consonant_unicode"
							+ " = ?");
			myStatement.setString(1, consonant);
			ResultSet r = myStatement.executeQuery();

			// create list of languages
			while (r.next()) {
				returnText += r.getString("name") + " \n ";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnText;
	}

	/**
	 * Queries language_consonant and language_vowel to find languages with the
	 * specified phoneme
	 * 
	 * @param phoneme
	 *            unicode value of the phoneme to be found
	 * @return list of language names with this phoneme
	 */
	@Override
	public String queryPhoneme(String phoneme) {
		return queryConsonant(phoneme) + queryVowel(phoneme);

	}

	/**
	 * Queries to find languages without the specified phoneme
	 * 
	 * @param phoneme
	 *            unicode value of the phoneme to be found
	 * @return list of language names, families, and regions without this phoneme
	 */
	@Override
	public String queryNoPhoneme(String phoneme) {
		// the list of languages found
		String returnText = " ";
		try {
			// create sql statement and execute
			PreparedStatement myStatement = DBcon
					.prepareStatement("SELECT DISTINCT languages.name, "
							+ "languages.family , languages.region FROM language_vowel inner "
							+ "join languages on "
							+ "languages.name = language_vowel.name WHERE language_vowel.name NOT IN"
							+ " (SELECT name "
							+ "FROM language_vowel WHERE vowel_unicode = ?)");

			myStatement.setString(1, phoneme);
			ResultSet r = myStatement.executeQuery();

			// get the list of languages
			while (r.next()) {

				returnText += r.getString("name") + ", "
						+ r.getString("family") + ", " + r.getString("region")
						+ " \n ";
			}
			// if not in vowels
			if (returnText == "") {
				PreparedStatement myConsStatement = DBcon
						.prepareStatement("SELECT DISTINCT languages.name, "
								+ "languages.family , languages.region FROM language_consonant inner "
								+ "join languages on "
								+ "languages.name = language_consonant.name WHERE "
								+ "language_consonant.name NOT IN (SELECT name "
								+ "FROM language_consonant WHERE consonant_unicode = ?)");

				myConsStatement.setString(1, phoneme);
				ResultSet r1 = myStatement.executeQuery();

				// get the list of languages
				while (r1.next()) {

					returnText += r.getString("name") + ", "
							+ r.getString("family") + ", " + r.getString("region")
							+ " \n ";;
				}
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return returnText;
	}

	/**
	 * Queries languages to find languages within the specified family.
	 * 
	 * @param familyStr
	 *            the family to be searched for
	 * @return the languages' name, family, and region
	 */
	@Override
	public String queryLanguageByFamily(String familyStr) {
		// the list of languages found
		String returnText = " ";
		try {
			// create sql statement and execute
			PreparedStatement myStatement = DBcon
					.prepareStatement("SELECT name, family, region FROM languages WHERE "
							+ "upper(family) = upper(?)");
			PreparedStatement stm = DBcon
					.prepareStatement("CREATE INDEX lang_index ON languages(upper(family))");

			stm.execute();

			myStatement.setString(1, familyStr);
			ResultSet r = myStatement.executeQuery();

			// get the list of languages
			while (r.next()) {
				returnText += r.getString("name") + ", "
						+ r.getString("family") + ", " + r.getString("region")
						+ " \n ";
			}

			PreparedStatement stmDrop = DBcon
					.prepareStatement("DROP INDEX lang_index");

			stmDrop.execute();

		} catch (SQLException e) {
			
			e.printStackTrace();
			return "No results found.";
		}

		return returnText;
	}

	/**
	 * Queries languages to find languages with the iso639 code
	 * 
	 * @param isoValue
	 *            the iso639 code to be searched for
	 * @return the languages' name, family, and region
	 */
	@Override
	public String queryByIso(String isoValue) {

		// the list of languages to be returned
		String returnText = " ";
		try {
			// create and execute query
			PreparedStatement myStatement = DBcon
					.prepareStatement("SELECT name, family, region FROM languages WHERE iso639 = ?");
			myStatement.setString(1, isoValue);
			PreparedStatement stm = DBcon
					.prepareStatement("CREATE INDEX iso_index ON languages(iso639)");

			stm.execute();
			ResultSet r = myStatement.executeQuery();

			// create list of languages
			while (r.next()) {
				returnText += r.getString("name") + ", "
						+ r.getString("family") + ", " + r.getString("region")
						+ " \n ";
			}

			PreparedStatement stmDrop = DBcon
					.prepareStatement("DROP INDEX iso_index");

			stmDrop.execute();

		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return returnText;
	}

	/**
	 * Checks if the connection was successful
	 * 
	 * @return the isDbUp
	 */
	@Override
	public boolean isDbUp() {
		return isDbUp;
	}

	/**
	 * Sets isDbUp
	 * 
	 * @param isDbUp
	 *            the isDbUp to set
	 */
	@Override
	public void setDbUp(boolean isDbUp) {
		this.isDbUp = isDbUp;
	}

}