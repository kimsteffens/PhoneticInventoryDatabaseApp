# PhoneticInventoryDatabaseApp
The Phonetic Inventory database consists of a database of languages, along with the phonemes (sounds) it uses. The Java application is used in order to make querying user-friendly.

## Prerequisites
Before setting up the database and its application, JDK 8 and PostgreSQL must be installed onto the machine. 

## Setup
To set up the database, a Postgres database must be created using Database/inventorydatabase.sql. Ensure that the database is created on the user "postgres" with password "none" on localhost:5432 (or else the connection details in QueryMaker.java must be altered). This is necessary for the Java application to connect. No data is currently provided.

## The Database
There are currently five tables:

  1. Consonant
  2. Vowel
  3. Language_Vowel
  4. Language_Consonant
  5. Languages
  
Each item in the language table includes the languages' name, region in which it is spoken, language family, and the iso639 code associated with it. The items in the vowel and consonant table contain all the information needed in order to identify the phoneme. The items in language_vowel and language_consonant include the name of a language and the phoneme (the consonant or vowel) unicode. These latter two tables are used to actually assign phonemes for a language, one phoneme at a time.

## The Application
The application was created in Java. It has three classes: 

  1. PhoneticInventory: the main class that sets up the application
  2. QueryMaker: used to connect to the database and to create queries (additionally, there is an interface for QueryMaker)
  3. QueryPanel: the GUI

 The application consists of a simple GUI with four search options:
  1. contains phoneme- a search option to find languages containing the user-given phoneme (in unicode) and return their names.
  2. does not contain phoneme- a search option to find languages that do not contain the user-given phoneme (in unicode) and list the name, family, and region (NOTE: the reason this search option returns name, family, and region and not just name like "contains phoneme" is to improve the exam by not making it easy to find the correct code.
  3. find languages in family- a search option to find all languages in a user-given family and returns the name, family and region.
  4. find language by iso639- find the language with the user-given iso639 code and list its name, family, and region.

## Outlook
In the future more search options should be implemented, such as searching for languages with multiple phonemes, and searching for languages by consonant and vowel attributes.

The GUI should also be improved to be more sleek and modern, as it is currently rather basic.

Later, I'd like to for the full phonetic inventory of a language to be shown when a language is selected in the result list.





