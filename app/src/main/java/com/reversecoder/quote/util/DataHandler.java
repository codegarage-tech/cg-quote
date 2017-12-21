package com.reversecoder.quote.util;

import android.util.Log;

import com.kannan.glazy.GlazyCard;
import com.kannan.glazy.views.GlazyImageView;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.reversecoder.library.storage.SessionManager;
import com.reversecoder.library.util.AllSettingsManager;
import com.reversecoder.quote.R;
import com.reversecoder.quote.model.Author;
import com.reversecoder.quote.model.DataAuthor;
import com.reversecoder.quote.model.DataLanguage;
import com.reversecoder.quote.model.DataQuote;
import com.reversecoder.quote.model.Language;
import com.reversecoder.quote.model.MappedQuote;
import com.reversecoder.quote.model.Quote;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.reversecoder.localechanger.data.Locales.getAllLocales;
import static com.reversecoder.quote.application.QuoteApp.getGlobalContext;
import static com.reversecoder.quote.util.AllConstants.SESSION_DATA_AUTHORS;
import static com.reversecoder.quote.util.AllConstants.SESSION_DATA_LANGUAGES;
import static com.reversecoder.quote.util.AllConstants.SESSION_DATA_QUOTES;
import static com.reversecoder.quote.util.AllConstants.SESSION_FREE_APP;
import static com.reversecoder.quote.util.AllConstants.SESSION_SELECTED_LANGUAGE;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class DataHandler {

    private static String TAG = DataHandler.class.getSimpleName();

    public static ArrayList<MappedQuote> initDataBase() {
        if (mAllMappedQuotes.size() == 0) {
            DataHandler.initAllLanguages();
            DataHandler.initAllAuthors();
            DataHandler.initAllQuotes();
            mAllMappedQuotes = DataHandler.getAllData();
        }
        return mAllMappedQuotes;
    }

    public static ArrayList<MappedQuote> initAllFavouriteData() {
        mAllMappedFavouriteQuotes = getAllFavouriteData();
        return mAllMappedFavouriteQuotes;
    }

    /***********************
     * Data for all quotes *
     ***********************/
    public static ArrayList<MappedQuote> mAllMappedQuotes = new ArrayList<MappedQuote>();

    public static Quote setFavouriteForAuthorFragment(Quote quote, boolean isFavourite) {
//        MappedQuote mMappedQuote;
        for (MappedQuote mappedQuote : mAllMappedQuotes) {
//            mMappedQuote = mappedQuote;
            if (mappedQuote.getAuthor().getAuthorName().equalsIgnoreCase(quote.getAuthor().getAuthorName())) {
                for (Quote mQuote : mappedQuote.getQuotes()) {
                    if (mQuote.getQuoteDescription().equalsIgnoreCase(quote.getQuoteDescription())) {
                        Quote updatedQuote = Quote.findById(Quote.class, mQuote.getId());
                        if (updatedQuote.isFavourite() != isFavourite) {
                            updatedQuote.setFavourite(isFavourite);
                            updatedQuote.save();
                            mQuote.setFavourite(isFavourite);
                            quote.setFavourite(isFavourite);

//                            Quote tagQuote = Quote.findById(Quote.class, updatedQuote.getId());
//                            Log.d("setFavourite", "updatedQuote: " + tagQuote.toString());
//                            Log.d("setFavourite", "foldableQuote: " + foldableQuote.toString());
                        }
                    }
                }
            }
        }
        return quote;
    }

    public static ArrayList<Language> initAllLanguages() {

        ArrayList<Language> languages = new ArrayList<Language>();
        List<Locale> locales = getAllLocales();

        for (int i = 0; i < locales.size(); i++) {
            Language language = new Language(locales.get(i).getLanguage());
            language.setId(language.save());

            languages.add(language);
        }

        DataLanguage dataLanguage = new DataLanguage(languages);
        SessionManager.setStringSetting(getGlobalContext(), SESSION_DATA_LANGUAGES, dataLanguage.toString());

        return languages;
    }

    public static ArrayList<Language> getAllLanguages() {
        if (AllSettingsManager.isNullOrEmpty(SessionManager.getStringSetting(getGlobalContext(), SESSION_DATA_LANGUAGES))) {
            List<Language> languages = Language.listAll(Language.class);
            DataLanguage dataLanguage = new DataLanguage(new ArrayList<Language>(languages));
            SessionManager.setStringSetting(getGlobalContext(), SESSION_DATA_LANGUAGES, dataLanguage.toString());
        }

        DataLanguage mDataLanguage = DataLanguage.convertFromStringToObject(SessionManager.getStringSetting(getGlobalContext(), SESSION_DATA_LANGUAGES), DataLanguage.class);

        return mDataLanguage.getLanguages();
    }

    public static ArrayList<Author> initAllAuthors() {

        ArrayList<Author> authors = new ArrayList<Author>();

        Author APJAbulKalam = new Author("A. P. J. Abdul Kalam", "October 15, 1931", "July 27, 2015", "Statesman", "Indian", AppUtils.getDrawableResourceId(R.drawable.apj_abdul_kalam), true);
        authors.add(APJAbulKalam);

        Author albertCamus = new Author("Albert Camus", "November 7, 1913", "January 4, 1960", "Philosopher", "French", AppUtils.getDrawableResourceId(R.drawable.albert_camus), true);
        authors.add(albertCamus);

        Author aristotle = new Author("Aristotle", "384 BC", "322 BC", "Philosopher", "Greek", AppUtils.getDrawableResourceId(R.drawable.aristotle), true);
        authors.add(aristotle);

        Author audreyHepburn = new Author("Audrey Hepburn", "May 4, 1929", "January 20, 1993", "Actress", "Belgian", AppUtils.getDrawableResourceId(R.drawable.audrey_hepburn), true);
        authors.add(audreyHepburn);

        Author abrahamLincoln = new Author("Abraham Lincoln", "February 12, 1809", "April 15, 1865", "President", "American", AppUtils.getDrawableResourceId(R.drawable.abraham_lincoln), true);
        authors.add(abrahamLincoln);

        Author aldousHuxley = new Author("Aldous Huxley", "July 26, 1894", "November 22, 1963", "Novelist", "English", AppUtils.getDrawableResourceId(R.drawable.aldous_huxley), true);
        authors.add(aldousHuxley);

        Author alexanderHamilton = new Author("Alexander Hamilton", "January 11, 1755", "July 12, 1804", "Politician", "American", AppUtils.getDrawableResourceId(R.drawable.alexander_hamilton), true);
        authors.add(alexanderHamilton);

        Author alexanderPope = new Author("Alexander Pope", "May 21, 1688", "May 30, 1744", "Poet", "English", AppUtils.getDrawableResourceId(R.drawable.alexander_pope), true);
        authors.add(alexanderPope);

        Author arnoldSchwarzenegger = new Author("Arnold Schwarzenegger", "July 30, 1947", "", "Actor", "Austrian", AppUtils.getDrawableResourceId(R.drawable.arnold_schwarzenegger), true);
        authors.add(arnoldSchwarzenegger);

        Author barackObama = new Author("Barack Obama", "August 4, 1961", "", "President", "American", AppUtils.getDrawableResourceId(R.drawable.barac_obama), true);
        authors.add(barackObama);

        Author benShapiro = new Author("Ben Shapiro", "January 15, 1984", "", "Author", "American", AppUtils.getDrawableResourceId(R.drawable.ben_shapiro), true);
        authors.add(benShapiro);

        Author benjaminDisraeli = new Author("Benjamin Disraeli", "December 21, 1804", "April 19, 1881", "Statesman", "British", AppUtils.getDrawableResourceId(R.drawable.benjamin_disraeli), true);
        authors.add(benjaminDisraeli);

        Author benjaminFranklin = new Author("Benjamin Franklin", "January 17, 1706", "April 17, 1790", "Politician", "American", AppUtils.getDrawableResourceId(R.drawable.benjamin_franklin), true);
        authors.add(benjaminFranklin);

        Author bertrandRussell = new Author("Bertrand Russell", "May 18, 1872", "February 2, 1970", "Philosopher", "British", AppUtils.getDrawableResourceId(R.drawable.bertrand_russell), true);
        authors.add(bertrandRussell);

        Author beyonceKnowles = new Author("Beyonce Knowles", "September 4, 1981", "", "Musician", "American", AppUtils.getDrawableResourceId(R.drawable.beyonce_knowles), true);
        authors.add(beyonceKnowles);

        Author billGates = new Author("Bill Gates", "October 28, 1955", "", "Businessman", "American", AppUtils.getDrawableResourceId(R.drawable.bill_gates), true);
        authors.add(billGates);

        Author billyGraham = new Author("Billy Graham", "November 7, 1918", "", "Clergyman", "American", AppUtils.getDrawableResourceId(R.drawable.billy_graham), true);
        authors.add(billyGraham);

        Author blaisePascal = new Author("Blaise Pascal", "June 19, 1623", "August 19, 1662", "Philosopher", "French", AppUtils.getDrawableResourceId(R.drawable.blaise_pascal), true);
        authors.add(blaisePascal);

        Author bobDylan = new Author("Bob Dylan", "May 24, 1941", "Present", "Musician", "American", AppUtils.getDrawableResourceId(R.drawable.bob_dylan), true);
        authors.add(bobDylan);

        Author CSLewis = new Author("C. S. Lewis", "November 29, 1898", "November 22, 1963", "Author", "Irish", AppUtils.getDrawableResourceId(R.drawable.cslewis), true);
        authors.add(CSLewis);

        Author carlJung = new Author("Carl Jung", "July 26, 1875", "June 6, 1961", "Psychologist", "Swiss", AppUtils.getDrawableResourceId(R.drawable.carl_jung), true);
        authors.add(carlJung);

        Author carlSagan = new Author("Carl Sagan", "November 9, 1934", "December 20, 1996", "Scientist", "American", AppUtils.getDrawableResourceId(R.drawable.carl_sagan), true);
        authors.add(carlSagan);

        Author carlBurnett = new Author("Carl Burnett", "April 26, 1933", "", "Actress", "American", AppUtils.getDrawableResourceId(R.drawable.carl_burnett), true);
        authors.add(carlBurnett);

        Author charlesDickens = new Author("Charles Dickens", "February 12, 1809", "April 19, 1882", "Scientist", "English", AppUtils.getDrawableResourceId(R.drawable.charles_dickens), true);
        authors.add(charlesDickens);

        Author charlesRSwindoll = new Author("Charles R. Swindoll", "October 18, 1934", "", "Clergyman", "American", AppUtils.getDrawableResourceId(R.drawable.charles_r_swindoll), true);
        authors.add(charlesRSwindoll);

        Author cheGuevara = new Author("Che Guevara", "June 14, 1928", "October 9, 1967", "Revolutionary", "Argentinian", AppUtils.getDrawableResourceId(R.drawable.che_guevara), true);
        authors.add(cheGuevara);

        Author christopherHitchens = new Author("Christopher Hitchens", "April 13, 1949", "December 15, 2011", "Author", "American", AppUtils.getDrawableResourceId(R.drawable.christopher_hitchens), true);
        authors.add(christopherHitchens);

        Author clintEastwood = new Author("Clint Eastwood", "May 31, 1930", "Present", "Actor", "American", AppUtils.getDrawableResourceId(R.drawable.clint_eastwood), true);
        authors.add(clintEastwood);

        Author conorMcGregor = new Author("Conor McGregor", "July 14, 1988", "", "Athlete", "Irish", AppUtils.getDrawableResourceId(R.drawable.conor_mcgregor), true);
        authors.add(conorMcGregor);

        Author dalaiLama = new Author("Dalai Lama", "July 6, 1935", "", "Leader", "Tibetan", AppUtils.getDrawableResourceId(R.drawable.dalai_lama), true);
        authors.add(dalaiLama);

        Author douglasAdams = new Author("Douglas Adams", "March 11, 1952", "May 11, 2001", "Writer", "English", AppUtils.getDrawableResourceId(R.drawable.douglas_adams), true);
        authors.add(douglasAdams);

        Author dickGregory = new Author("Dick Gregory", "October 12, 1932", "", "Comedian", "American", AppUtils.getDrawableResourceId(R.drawable.dick_gregory), true);
        authors.add(dickGregory);

        Author dollyParton = new Author("Dolly Parton", "January 19, 1946", "", "Musician", "American", AppUtils.getDrawableResourceId(R.drawable.dolly_parton), true);
        authors.add(dollyParton);

        Author donaldTrump = new Author("Donald Trump", "June 14, 1946", "", "President", "American", AppUtils.getDrawableResourceId(R.drawable.donald_trump), true);
        authors.add(donaldTrump);

        Author DrSeuss = new Author("Dr. Seuss", "March 2, 1904", "September 24, 1991", "Writer", "American", AppUtils.getDrawableResourceId(R.drawable.dr_seuss), true);
        authors.add(DrSeuss);

        Author drake = new Author("Drake", "October 24, 1986", "", "Musician", "Canadian", AppUtils.getDrawableResourceId(R.drawable.drake), true);
        authors.add(drake);

        Author dwightDEisenhower = new Author("Dwight D. Eisenhower", "October 14, 1890", "March 28, 1969", "President", "American", AppUtils.getDrawableResourceId(R.drawable.dwight_d_eisenhower), true);
        authors.add(dwightDEisenhower);

        Author elieWiesel = new Author("Elie Wiesel", "September 30, 1928", "", "Novelist", "American", AppUtils.getDrawableResourceId(R.drawable.elie_wiesel), true);
        authors.add(elieWiesel);

        Author elizabethI = new Author("Elizabeth I", "September 7, 1533", "March 24, 1603", "Royalty", "English", AppUtils.getDrawableResourceId(R.drawable.elizabeth_i), true);
        authors.add(elizabethI);

        Author ellenDeGeneres = new Author("Ellen DeGeneres", "January 26, 1958", "", "Comedian", "American", AppUtils.getDrawableResourceId(R.drawable.ellen_degenres), true);
        authors.add(ellenDeGeneres);

        Author elonMusk = new Author("Elon Musk", "June 28, 1971", "", "Businessman", "American", AppUtils.getDrawableResourceId(R.drawable.elon_musk), true);
        authors.add(elonMusk);

        Author elvisPresley = new Author("Elvis Presley", "January 8, 1935", "August 16, 1977", "Musician", "American", AppUtils.getDrawableResourceId(R.drawable.elvis_presley), true);
        authors.add(elvisPresley);

        Author eminem = new Author("Eminem", "October 17, 1972", "", "Musician", "American", AppUtils.getDrawableResourceId(R.drawable.eminem), true);
        authors.add(eminem);

        Author ermaBombeck = new Author("Erma Bombeck", "February 21, 1927", "April 22, 1996", "Journalist", "American", AppUtils.getDrawableResourceId(R.drawable.erma_bombesck), true);
        authors.add(ermaBombeck);

        Author ernestHemingway = new Author("Ernest Hemingway", "July 21, 1899", "July 2, 1961", "Novelist", "American", AppUtils.getDrawableResourceId(R.drawable.ernest_hemingway), true);
        authors.add(ernestHemingway);

        Author edgarAllanPoe = new Author("Edgar Allan Poe", "January 19, 1809", "October 7, 1849", "Poet", "American", AppUtils.getDrawableResourceId(R.drawable.edgar_allan_poe), true);
        authors.add(edgarAllanPoe);

        Author francisofAssisi = new Author("Francis of Assisi", "1182", "1226", "Saint", "Italian", AppUtils.getDrawableResourceId(R.drawable.francis_of_assisi), true);
        authors.add(francisofAssisi);

        Author frankLloydWright = new Author("Frank Lloyd Wright", "June 8, 1867", "April 9, 1959", "Architect", "American", AppUtils.getDrawableResourceId(R.drawable.frank_lloyd_wright), true);
        authors.add(frankLloydWright);

        Author frankSinatra = new Author("Frank Sinatra", "December 12, 1915", "May 14, 1998", "Musician", "American", AppUtils.getDrawableResourceId(R.drawable.frank_sinatra), true);
        authors.add(frankSinatra);

        Author franklinDRoosevelt = new Author("Franklin D. Roosevelt", "January 30, 1882", "April 12, 1945", "President", "American", AppUtils.getDrawableResourceId(R.drawable.franklin_d_roosevelt), true);
        authors.add(franklinDRoosevelt);

        Author franzKafka = new Author("Franz Kafka", "July 3, 1883", "June 3, 1924", "Novelist", "", AppUtils.getDrawableResourceId(R.drawable.franz_kafka), true);
        authors.add(franzKafka);

        Author frederickDouglass = new Author("Frederick Douglass", "February 14, 1817", "February 20, 1895", "Author", "American", AppUtils.getDrawableResourceId(R.drawable.frederic_doughlass), true);
        authors.add(frederickDouglass);

        Author fridaKahlo = new Author("Frida Kahlo", "July 6, 1907", "July 13, 1954", "Artist", "Mexican", AppUtils.getDrawableResourceId(R.drawable.frida_kahlo), true);
        authors.add(fridaKahlo);

        Author friedrichNietzsche = new Author("Friedrich Nietzsche", "October 15, 1844", "August 25, 1900", "Philosopher", "German", AppUtils.getDrawableResourceId(R.drawable.friedrich_nietzsche), true);
        authors.add(friedrichNietzsche);

        Author fyodorDostoevsky = new Author("Fyodor Dostoevsky", "November 11, 1821", "February 9, 1881", "Novelist", "Russian", AppUtils.getDrawableResourceId(R.drawable.fyodor_dostoevsky), true);
        authors.add(fyodorDostoevsky);

        Author georgeBernardShaw = new Author("George Bernard Shaw", "July 26, 1856", "November 2, 1950", "Dramatist", "Irish", AppUtils.getDrawableResourceId(R.drawable.george_bernard_shaw), true);
        authors.add(georgeBernardShaw);

        Author galileoGalilei = new Author("Galileo Galilei", "February 15, 1564", "January 8, 1642", "Scientist", "Italian", AppUtils.getDrawableResourceId(R.drawable.galileo_galilei), true);
        authors.add(galileoGalilei);

        Author georgeCarlin = new Author("George Carlin", "May 12, 1937", "June 22, 2008", "Comedian", "American", AppUtils.getDrawableResourceId(R.drawable.goerge_carlin), true);
        authors.add(georgeCarlin);

        Author georgeOrwell = new Author("George Orwell", "June 25, 1903", "January 21, 1950", "Author", "British", AppUtils.getDrawableResourceId(R.drawable.george_orwell), true);
        authors.add(georgeOrwell);

        Author georgeSPatton = new Author("George S. Patton", "November 11, 1885", "December 21, 1945", "Soldier", "American", AppUtils.getDrawableResourceId(R.drawable.george_s_patton), true);
        authors.add(georgeSPatton);

        Author georgeWBush = new Author("George W. Bush", "July 6, 1946", "", "President", "American", AppUtils.getDrawableResourceId(R.drawable.george_w_bush), true);
        authors.add(georgeWBush);

        Author georgeWashington = new Author("George Washington", "February 22, 1732", "December 14, 1799", "President", "American", AppUtils.getDrawableResourceId(R.drawable.goerge_washington), true);
        authors.add(georgeWashington);

        Author helenKeller = new Author("Helen Keller", "June 27, 1880", "June 1, 1968", "Author", "American", AppUtils.getDrawableResourceId(R.drawable.helen_keller), true);
        authors.add(helenKeller);

        Author HJacksonBrownJr = new Author("H. Jackson Brown, Jr.", "1940", "", "Author", "Author", AppUtils.getDrawableResourceId(R.drawable.jackson_brown_jr), true);
        authors.add(HJacksonBrownJr);

        Author HLMencken = new Author("H. L. Mencken", "September 12, 1880", "January 29, 1956", "Writer", "American", AppUtils.getDrawableResourceId(R.drawable.hl_mencken), true);
        authors.add(HLMencken);

        Author HPLovecraft = new Author("H. P. Lovecraft", "August 20, 1890", "March 15, 1937", "Novelist", "American", AppUtils.getDrawableResourceId(R.drawable.h_p_lovecraft), true);
        authors.add(HPLovecraft);

        Author harrietTubman = new Author("Harriet Tubman", "1820", "1913", "Activist", "American", AppUtils.getDrawableResourceId(R.drawable.harriet_tubman), true);
        authors.add(harrietTubman);

        Author harrySTruman = new Author("Harry S Truman", "May 8, 1884", "December 26, 1972", "President", "American", AppUtils.getDrawableResourceId(R.drawable.harry_s_truman), true);
        authors.add(harrySTruman);

        Author henryDavidThoreau = new Author("Henry David Thoreau", "July 12, 1817", "May 6, 1862", "Author", "American", AppUtils.getDrawableResourceId(R.drawable.henry_david_thoreau), true);
        authors.add(henryDavidThoreau);

        Author henryFord = new Author("Henry Ford", "July 30, 1863", "April 7, 1947", "Businessman", "American", AppUtils.getDrawableResourceId(R.drawable.henry_ford), true);
        authors.add(henryFord);

        Author henryKissinger = new Author("Henry Kissinger", "May 27, 1923", "", "Statesman", "American", AppUtils.getDrawableResourceId(R.drawable.henry_kissinger), true);
        authors.add(henryKissinger);

        Author iceCube = new Author("Ice Cube", "June 15, 1969", "", "Musician", "American", AppUtils.getDrawableResourceId(R.drawable.ice_cube), true);
        authors.add(iceCube);

        Author idaBWells = new Author("Ida B. Wells", "July 16, 1862", "March 25, 1931", "Activist", "American", AppUtils.getDrawableResourceId(R.drawable.ida_b_wells), true);
        authors.add(idaBWells);

        Author immanuelKant = new Author("Immanuel Kant", "April 22, 1724", "February 12, 1804", "Philosopher", "German", AppUtils.getDrawableResourceId(R.drawable.immanuel_kant), true);
        authors.add(immanuelKant);

        Author indiraGandhi = new Author("Indira Gandhi", "November 19, 1917", "October 31, 1984", "Statesman", "Indian", AppUtils.getDrawableResourceId(R.drawable.indira_gandhi), true);
        authors.add(indiraGandhi);

        Author indraNooyi = new Author("Indra Nooyi", "October 28, 1955", "", "Businesswoman", "Indian", AppUtils.getDrawableResourceId(R.drawable.indra_nooyi), true);
        authors.add(indraNooyi);

        Author irisApfel = new Author("Iris Apfel", "August 29, 1921", "", "Businesswoman", "American", AppUtils.getDrawableResourceId(R.drawable.iris_apfel), true);
        authors.add(irisApfel);

        Author isaacNewton = new Author("Isaac Newton", "December 25, 1642", "March 20, 1727", "Mathematician", "English", AppUtils.getDrawableResourceId(R.drawable.isaac_newton), true);
        authors.add(isaacNewton);

        Author isaacAsimov = new Author("Isaac Asimov", "January 2, 1920", "April 6, 1992", "Scientist", "American", AppUtils.getDrawableResourceId(R.drawable.isaac_asimov), true);
        authors.add(isaacAsimov);

        Author jesusChrist = new Author("Jesus Christ", "", "", "Leader", "", AppUtils.getDrawableResourceId(R.drawable.jesus_christ), true);
        authors.add(jesusChrist);

        Author JKRowling = new Author("J. K. Rowling", "July 31, 1965", "Present", "Author", "English", AppUtils.getDrawableResourceId(R.drawable.j_k_rowling), true);
        authors.add(JKRowling);

        Author janeAusten = new Author("Jane Austen", "December 16, 1775", "July 28, 1817", "Writer", "British", AppUtils.getDrawableResourceId(R.drawable.jane_austen), true);
        authors.add(janeAusten);

        Author jackWelch = new Author("Jack Welch", "November 19, 1935", "", "Businessman", "American", AppUtils.getDrawableResourceId(R.drawable.jack_welch), true);
        authors.add(jackWelch);

        Author jamesABaldwin = new Author("James A. Baldwin", "August 2, 1924", "December 1, 1987", "Author", "American", AppUtils.getDrawableResourceId(R.drawable.james_a_baldwin), true);
        authors.add(jamesABaldwin);

        Author johnCMaxwell = new Author("John C. Maxwell", "1947", "", "Clergyman", "American", AppUtils.getDrawableResourceId(R.drawable.john_c_maxwell), true);
        authors.add(johnCMaxwell);

        Author johnFKennedy = new Author("John F. Kennedy", "May 29, 1917", "November 22, 1963", "President", "American", AppUtils.getDrawableResourceId(R.drawable.john_f_kennedy), true);
        authors.add(johnFKennedy);

        Author johannWolfgangvonGoethe = new Author("Johann Wolfgang von Goethe", "August 28, 1749", "March 22, 1832", "Poet", "German", AppUtils.getDrawableResourceId(R.drawable.johann_wolfgang_von_goethe), true);
        authors.add(johannWolfgangvonGoethe);

        Author kanyeWest = new Author("Kanye West", "June 8, 1977", "", "Musician", "American", AppUtils.getDrawableResourceId(R.drawable.kanye_west), true);
        authors.add(kanyeWest);

        Author karlMarx = new Author("Karl Marx", "May 5, 1818", "March 14, 1883", "Philosopher", "German", AppUtils.getDrawableResourceId(R.drawable.karl_marx), true);
        authors.add(karlMarx);

        Author kendrickLamar = new Author("Kendrick Lamar", "June 17, 1987", "", "Musician", "American", AppUtils.getDrawableResourceId(R.drawable.kendrick_lamar), true);
        authors.add(kendrickLamar);

        Author keanuReeves = new Author("Keanu Reeves", "September 2, 1964", "", "Actor", "Canadian", AppUtils.getDrawableResourceId(R.drawable.keanu_reeves), true);
        authors.add(keanuReeves);

        Author karlPilkington = new Author("Karl Pilkington", "September 23, 1972", "", "Actor", "British", AppUtils.getDrawableResourceId(R.drawable.karl_pilkington), true);
        authors.add(karlPilkington);

        Author laoTzu = new Author("Lao Tzu", "", "", "Philosopher", "Chinese", AppUtils.getDrawableResourceId(R.drawable.lao_tzu), true);
        authors.add(laoTzu);

        Author leBronJames = new Author("LeBron James", "December 30, 1984", "", "Athlete", "American", AppUtils.getDrawableResourceId(R.drawable.lebron_james), true);
        authors.add(leBronJames);

        Author leoTolstoy = new Author("Leo Tolstoy", "September 9, 1828", "November 20, 1910", "Novelist", "Russian", AppUtils.getDrawableResourceId(R.drawable.leo_tolstoy), true);
        authors.add(leoTolstoy);

        Author leonardodaVinci = new Author("Leonardo da Vinci", "April 15, 1452", "May 2, 1519", "Artist", "Italian", AppUtils.getDrawableResourceId(R.drawable.leonardo_da_vinci), true);
        authors.add(leonardodaVinci);

        Author lilUziVert = new Author("Lil Uzi Vert", "July 31, 1994", "", "Musician", "American", AppUtils.getDrawableResourceId(R.drawable.lil_uzi_vert), true);
        authors.add(lilUziVert);

        Author malcolmX = new Author("Malcolm X", "May 19, 1925", "February 21, 1965", "Activist", "American", AppUtils.getDrawableResourceId(R.drawable.malcolm_x), true);
        authors.add(malcolmX);

        Author motherTeresa = new Author("Mother Teresa", "August 26, 1910", "September 5, 1997", "Saint", "Albanian", AppUtils.getDrawableResourceId(R.drawable.mother_teresa), true);
        authors.add(motherTeresa);

        Author mahatmaGandhi = new Author("Mahatma Gandhi", "October 2, 1869", "January 30, 1948", "Leader", "Indian", AppUtils.getDrawableResourceId(R.drawable.mahatma_gandhi), true);
        authors.add(mahatmaGandhi);

        Author marcusTulliusCicero = new Author("Marcus Tullius Cicero", "106 BC", "43 BC", "Statesman", "Roman", AppUtils.getDrawableResourceId(R.drawable.marcus_tullius_cicero), true);
        authors.add(marcusTulliusCicero);

        Author marilynMonroe = new Author("Marilyn Monroe", "June 1, 1926", "August 5, 1962", "Actress", "American", AppUtils.getDrawableResourceId(R.drawable.marilyn_monroe), true);
        authors.add(marilynMonroe);

        Author napoleonBonaparte = new Author("Napoleon Bonaparte", "August 15, 1769", "May 5, 1821", "Leader", "French", AppUtils.getDrawableResourceId(R.drawable.napoleon_bonaprte), true);
        authors.add(napoleonBonaparte);

        Author napoleonHill = new Author("Napoleon Hill", "October 26, 1883", "November 8, 1970", "Writer", "American", AppUtils.getDrawableResourceId(R.drawable.napoleon_hill), true);
        authors.add(napoleonHill);

        Author nas = new Author("Nas", "September 14, 1973", "", "Musician", "American", AppUtils.getDrawableResourceId(R.drawable.nas), true);
        authors.add(nas);

        Author neilArmstrong = new Author("Neil Armstrong", "August 5, 1930", "August 25, 2012", "Astronaut", "American", AppUtils.getDrawableResourceId(R.drawable.neil_armstrong), true);
        authors.add(neilArmstrong);

        Author neildeGrasse = new Author("Neil deGrasse", "October 5, 1958", "", "Scientist", "American", AppUtils.getDrawableResourceId(R.drawable.neil_de_grasse), true);
        authors.add(neildeGrasse);

        Author oscarWilde = new Author("Oscar Wilde", "October 16, 1854", "November 30, 1900", "Dramatist", "Irish", AppUtils.getDrawableResourceId(R.drawable.oscar_wilde), true);
        authors.add(oscarWilde);

        Author OJSimpson = new Author("O. J. Simpson", "July 9, 1947", "", "Athlete", "American", AppUtils.getDrawableResourceId(R.drawable.o_j_simpson), true);
        authors.add(OJSimpson);
//
        Author OSheaJacksonJr = new Author("O\\'Shea Jackson, Jr.", "February 24, 1991", "", "Actor", "American", AppUtils.getDrawableResourceId(R.drawable.o_shea_jackson_jr), true);
        authors.add(OSheaJacksonJr);

        Author OWinstonLink = new Author("O. Winston Link", "December 16, 1914", "January 30, 2001", "Photographer", "American", AppUtils.getDrawableResourceId(R.drawable.o_winston_link), true);
        authors.add(OWinstonLink);

        Author obiageliEzekwesili = new Author("Obiageli Ezekwesili", "April 28, 1963", "", "Public Servant", "Nigerian", AppUtils.getDrawableResourceId(R.drawable.obiageli_ezekwesili), true);
        authors.add(obiageliEzekwesili);

        Author plato = new Author("Plato", "427 BC", "347 BC", "Philosopher", "Greek", AppUtils.getDrawableResourceId(R.drawable.plato), true);
        authors.add(plato);

        Author pabloPicasso = new Author("Pablo Picasso", "October 25, 1881", "April 8, 1973", "Artist", "Spanish", AppUtils.getDrawableResourceId(R.drawable.pablo_picasso), true);
        authors.add(pabloPicasso);

        Author patrickHenry = new Author("Patrick Henry", "May 29, 1736", "June 6, 1799", "Politician", "American", AppUtils.getDrawableResourceId(R.drawable.patrick_henry), true);
        authors.add(patrickHenry);

        Author pauloCoelho = new Author("Paulo Coelho", "August 24, 1947", "", "Novelist", "Brazilian", AppUtils.getDrawableResourceId(R.drawable.paulo_coelho), true);
        authors.add(pauloCoelho);

        Author pele = new Author("Pele", "October 23, 1940", "", "Athlete", "Brazilian", AppUtils.getDrawableResourceId(R.drawable.pele), true);
        authors.add(pele);
//
        Author QoriankaKilcher = new Author("Q\\'orianka Kilcher", "February 11, 1990", "", "Actress", "German", AppUtils.getDrawableResourceId(R.drawable.q_orianka_kilcher), true);
        authors.add(QoriankaKilcher);

        Author QTip = new Author("Q-Tip", "April 10, 1970", "", "Musician", "American", AppUtils.getDrawableResourceId(R.drawable.q_tip), true);
        authors.add(QTip);

        Author qandeelBaloch = new Author("Qandeel Baloch", "March 1, 1990", "July 15, 2016", "Actress", "Pakistani", AppUtils.getDrawableResourceId(R.drawable.qandeel_baloch), true);
        authors.add(qandeelBaloch);

        Author quavo = new Author("Quavo", "April 2, 1991", "", "Musician", "American", AppUtils.getDrawableResourceId(R.drawable.quavo), true);
        authors.add(quavo);

        Author queenChristina = new Author("Queen Christina", "1626", "1689", "Royalty", "Swedish", AppUtils.getDrawableResourceId(R.drawable.queen_christina), true);
        authors.add(queenChristina);

        Author robinWilliams = new Author("Robin Williams", "July 21, 1951", "August 11, 2014", "Comedian", "American", AppUtils.getDrawableResourceId(R.drawable.robin_williams), true);
        authors.add(robinWilliams);

        Author rumi = new Author("Rumi", "September 30, 1207", "December 17, 1273", "Poet", "", AppUtils.getDrawableResourceId(R.drawable.rumi), true);
        authors.add(rumi);

        Author ralphWaldoEmerson = new Author("Ralph Waldo Emerson", "May 25, 1803", "April 27, 1882", "Poet", "American", AppUtils.getDrawableResourceId(R.drawable.ralph_waldo_emerson), true);
        authors.add(ralphWaldoEmerson);

        Author reneDescartes = new Author("Rene Descartes", "March 31, 1596", "February 11, 1650", "Mathematician", "French", AppUtils.getDrawableResourceId(R.drawable.rene_descartes), true);
        authors.add(reneDescartes);

        Author richardBranson = new Author("Richard Branson", "July 18, 1950", "", "Businessman", "British", AppUtils.getDrawableResourceId(R.drawable.richard_branson), true);
        authors.add(richardBranson);

        Author socrates = new Author("Socrates", "469 BC", "399 BC", "Philosopher", "Greek", AppUtils.getDrawableResourceId(R.drawable.socrates), true);
        authors.add(socrates);

        Author saintAugustine = new Author("Saint Augustine", "354", "430", "", "", AppUtils.getDrawableResourceId(R.drawable.saint_augustin), true);
        authors.add(saintAugustine);

        Author sigmundFreud = new Author("Sigmund Freud", "May 6, 1856", "September 23, 1939", "Psychologist", "Austrian", AppUtils.getDrawableResourceId(R.drawable.sigmund_freud), true);
        authors.add(sigmundFreud);

        Author simonSinek = new Author("Simon Sinek", "October 9, 1973", "", "Author", "English", AppUtils.getDrawableResourceId(R.drawable.simon_sinek), true);
        authors.add(simonSinek);

        Author sorenKierkegaard = new Author("Soren Kierkegaard", "May 5, 1813", "November 11, 1855", "Philosopher", "Danish", AppUtils.getDrawableResourceId(R.drawable.soren_kierkegaard), true);
        authors.add(sorenKierkegaard);

        Author theodoreRoosevelt = new Author("Theodore Roosevelt", "October 27, 1858", "January 6, 1919", "President", "American", AppUtils.getDrawableResourceId(R.drawable.theodore_roosevelt), true);
        authors.add(theodoreRoosevelt);

        Author TSEliot = new Author("T. S. Eliot", "September 26, 1888", "January 4, 1965", "Poet", "American", AppUtils.getDrawableResourceId(R.drawable.t_s_elliot), true);
        authors.add(TSEliot);

        Author taylorSwift = new Author("Taylor Swift", "December 13, 1989", "", "Musician", "American", AppUtils.getDrawableResourceId(R.drawable.taylor_swift), true);
        authors.add(taylorSwift);

        Author thomasAquinas = new Author("Thomas Aquinas", "1225", "1274", "Theologian", "Italian", AppUtils.getDrawableResourceId(R.drawable.thomas_aquinas), true);
        authors.add(thomasAquinas);

        Author thomasHobbes = new Author("Thomas Hobbes", "April 5, 1588", "December 4, 1679", "Philosopher", "English", AppUtils.getDrawableResourceId(R.drawable.thomas_hobbes), true);
        authors.add(thomasHobbes);

        Author voltaire = new Author("Voltaire", "November 21, 1694", "May 30, 1778", "Writer", "French", AppUtils.getDrawableResourceId(R.drawable.voltair), true);
        authors.add(voltaire);

        Author valentinoRossi = new Author("Valentino Rossi", "February 16, 1979", "", "Athlete", "Italian", AppUtils.getDrawableResourceId(R.drawable.valentino_rossi), true);
        authors.add(valentinoRossi);

        Author victorHugo = new Author("Victor Hugo", "February 26, 1802", "May 22, 1885", "Author", "French", AppUtils.getDrawableResourceId(R.drawable.victor_hugo), true);
        authors.add(victorHugo);

        Author viktorEFrankl = new Author("Viktor E. Frankl", "March 26, 1905", "September 2, 1997", "Psychologist", "Austrian", AppUtils.getDrawableResourceId(R.drawable.viktor_e_frankl), true);
        authors.add(viktorEFrankl);

        Author vinScully = new Author("Vin Scully", "November 29, 1927", "", "Celebrity", "American", AppUtils.getDrawableResourceId(R.drawable.vin_scully), true);
        authors.add(vinScully);

        Author vinceLombardi = new Author("Vince Lombardi", "June 11, 1913", "September 3, 1970", "Coach", "American", AppUtils.getDrawableResourceId(R.drawable.vince_lombardi), true);
        authors.add(vinceLombardi);

        Author vincentVanGogh = new Author("Vincent Van Gogh", "March 30, 1853", "July 29, 1890", "Artist", "Dutch", AppUtils.getDrawableResourceId(R.drawable.vincent_van_gogh), true);
        authors.add(vincentVanGogh);

        Author waltDisney = new Author("Walt Disney", "December 5, 1901", "December 15, 1966", "Cartoonist", "American", AppUtils.getDrawableResourceId(R.drawable.walt_disney), true);
        authors.add(waltDisney);

        Author WCFields = new Author("W. C. Fields", "January 29, 1880", "December 25, 1946", "Comedian", "American", AppUtils.getDrawableResourceId(R.drawable.w_c_fields), true);
        authors.add(WCFields);

        Author WEBDuBois = new Author("W. E. B. Du Bois", "February 23, 1868", "August 27, 1963", "Writer", "American", AppUtils.getDrawableResourceId(R.drawable.w_e_b_du_bois), true);
        authors.add(WEBDuBois);

        Author waltWhitman = new Author("Walt Whitman", "May 31, 1819", "March 26, 1892", "Poet", "American", AppUtils.getDrawableResourceId(R.drawable.walt_whitman), true);
        authors.add(waltWhitman);

        Author wayneDyer = new Author("Wayne Dyer", "May 10, 1940", "August 29, 2015", "Psychologist", "American", AppUtils.getDrawableResourceId(R.drawable.wayne_dyer), true);
        authors.add(wayneDyer);

        Author yaelNaim = new Author("Yael Naim", "February 6, 1978", "", "Musician", "French", AppUtils.getDrawableResourceId(R.drawable.yael_naim), true);
        authors.add(yaelNaim);

        Author yaelStone = new Author("Yael Stone", "March 6, 1985", "", "Actress", "Australian", AppUtils.getDrawableResourceId(R.drawable.yael_stone), true);
        authors.add(yaelStone);

        Author yahooSerious = new Author("Yahoo Serious", "July 27, 1953", "", "Director", "Australian", AppUtils.getDrawableResourceId(R.drawable.yahoo_seriuos), true);
        authors.add(yahooSerious);

        Author yairLapid = new Author("Yair Lapid", "November 5, 1963", "", "Politician", "Israeli", AppUtils.getDrawableResourceId(R.drawable.yair_lapid), true);
        authors.add(yairLapid);

        Author yotamOttolenghi = new Author("Yotam Ottolenghi", "December 14, 1968", "", "Chef", "Israeli", AppUtils.getDrawableResourceId(R.drawable.yotam_ottolenghi), true);
        authors.add(yotamOttolenghi);

        Author zigZiglar = new Author("Zig Ziglar", "November 6, 1926", "November 28, 2012", "Author", "American", AppUtils.getDrawableResourceId(R.drawable.zig_ziglar), true);
        authors.add(zigZiglar);

        Author zacGoldsmith = new Author("Zac Goldsmith", "January 20, 1975", "", "Politician", "British", AppUtils.getDrawableResourceId(R.drawable.zac_goldsmith), true);
        authors.add(zacGoldsmith);

        Author zacHanson = new Author("Zac Hanson", "October 22, 1985", "", "Musician", "American", AppUtils.getDrawableResourceId(R.drawable.zac_hanson), true);
        authors.add(zacHanson);

        Author zhuZhu = new Author("Zhu Zhu", "July 19, 1984", "", "Actress", "Chinese", AppUtils.getDrawableResourceId(R.drawable.zhu_zhu), true);
        authors.add(zhuZhu);

        Author zoeKazan = new Author("Zoe Kazan", "September 9, 1983", "", "Actress", "American", AppUtils.getDrawableResourceId(R.drawable.zoe_kazan), true);
        authors.add(zoeKazan);

        Author zooeyDeschanel = new Author("Zooey Deschanel", "January 17, 1980", "", "Actress", "American", AppUtils.getDrawableResourceId(R.drawable.zooey_deschanel), true);
        authors.add(zooeyDeschanel);

        Author zuleikhaRobinson = new Author("Zuleikha Robinson", "June 29, 1977", "", "Actress", "British", AppUtils.getDrawableResourceId(R.drawable.zuleikha_robinson), true);
        authors.add(zuleikhaRobinson);

        Author zygmuntBauman = new Author("Zygmunt Bauman", "November 19, 1925", "January 9, 2017", "Sociologist", "Polish", AppUtils.getDrawableResourceId(R.drawable.zigmunt_bauman), true);
        authors.add(zygmuntBauman);

        ArrayList<Author> mAuthors = new ArrayList<>(SugarRecord.saveInTx(authors));
        DataAuthor dataAuthor = new DataAuthor(authors);
        SessionManager.setStringSetting(getGlobalContext(), SESSION_DATA_AUTHORS, dataAuthor.toString());

        return mAuthors;
    }

    public static ArrayList<Author> getAllAuthors() {
        if (AllSettingsManager.isNullOrEmpty(SessionManager.getStringSetting(getGlobalContext(), SESSION_DATA_AUTHORS))) {
            List<Author> authors = Author.listAll(Author.class);
            DataAuthor dataAuthor = new DataAuthor(new ArrayList<Author>(authors));
            SessionManager.setStringSetting(getGlobalContext(), SESSION_DATA_AUTHORS, dataAuthor.toString());
        }

        DataAuthor mDataAuthor = DataAuthor.convertFromStringToObject(SessionManager.getStringSetting(getGlobalContext(), SESSION_DATA_AUTHORS), DataAuthor.class);

        return mDataAuthor.getAuthors();
    }

    public static Language getLanguage(String languageName) {
        List<Language> languages = getAllLanguages();
        for (int i = 0; i < languages.size(); i++) {
            if (languages.get(i).getLanguageName().equalsIgnoreCase(languageName)) {
                return languages.get(i);
            }
        }
        return null;
    }

    public static Author getAuthor(String authorName) {
        List<Author> authors = getAllAuthors();
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getAuthorName().equalsIgnoreCase(authorName)) {
                return authors.get(i);
            }
        }
        return null;
    }

    public static ArrayList<Quote> initAllQuotes() {

        ArrayList<Quote> quotes = new ArrayList<Quote>();
        Language english = getLanguage(SessionManager.getStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE));

        Author APJAbdulKalam = getAuthor("A. P. J. Abdul Kalam");
        quotes.add(new Quote("Teaching is a very noble profession that shapes the character, caliber, and future of an individual. If the people remember me as a good teacher, that will be the biggest honour for me.", false, true, english, APJAbdulKalam));
        quotes.add(new Quote("We should not give up and we should not allow the problem to defeat us.", false, true, english, APJAbdulKalam));
        quotes.add(new Quote("Let us sacrifice our today so that our children can have a better tomorrow.", false, true, english, APJAbdulKalam));
        quotes.add(new Quote("If you want to shine like a sun, first burn like a sun.", false, true, english, APJAbdulKalam));
        quotes.add(new Quote("If a country is to be corruption free and become a nation of beautiful minds, I strongly feel there are three key societal members who can make a difference. They are the father, the mother and the teacher.", false, true, english, APJAbdulKalam));
        quotes.add(new Quote("To succeed in your mission, you must have single-minded devotion to your goal.", false, true, english, APJAbdulKalam));
        quotes.add(new Quote("Look at the sky. We are not alone. The whole universe is friendly to us and conspires only to give the best to those who dream and work.", false, true, english, APJAbdulKalam));
        quotes.add(new Quote("Never stop fighting until you arrive at your destined place - that is, the unique you. Have an aim in life, continuously acquire knowledge, work hard, and have perseverance to realise the great life.", false, true, english, APJAbdulKalam));
        quotes.add(new Quote("Science is a beautiful gift to humanity; we should not distort it.", false, true, english, APJAbdulKalam));
        quotes.add(new Quote("The bird is powered by its own life and by its motivation.", false, true, english, APJAbdulKalam));

        Author albertCamus = getAuthor("Albert Camus");
        quotes.add(new Quote("Don't walk behind me; I may not lead. Don't walk in front of me; I may not follow. Just walk Autumn is a second spring when every leaf is a flower.", false, true, english, albertCamus));
        quotes.add(new Quote("Don't walk behind me; I may not lead. Don't walk in front of me; I may not follow. Just walk beside me and be my friend.", false, true, english, albertCamus));
        quotes.add(new Quote("Blessed are the hearts that can bend; they shall never be broken.", false, true, english, albertCamus));
        quotes.add(new Quote("The only way to deal with an unfree world is to become so absolutely free that your very existence is an act of rebellion.", false, true, english, albertCamus));
        quotes.add(new Quote("In the depth of winter I finally learned that there was in me an invincible summer.", false, true, english, albertCamus));
        quotes.add(new Quote("A man without ethics is a wild beast loosed upon this world.", false, true, english, albertCamus));
        quotes.add(new Quote("You will never be happy if you continue to search for what happiness consists of. You will never live if you are looking for the meaning of life.", false, true, english, albertCamus));
        quotes.add(new Quote("Integrity has no need of rules.", false, true, english, albertCamus));
        quotes.add(new Quote("Real generosity toward the future lies in giving all to the present.", false, true, english, albertCamus));
        quotes.add(new Quote("The evil that is in the world almost always comes of ignorance, and good intentions may do as much harm as malevolence if they lack understanding.", false, true, english, albertCamus));

        Author aristotle = getAuthor("Aristotle");
        quotes.add(new Quote("It is during our darkest moments that we must focus to see the light.", false, true, english, aristotle));
        quotes.add(new Quote("Quality is not an act, it is a habit.", false, true, english, aristotle));
        quotes.add(new Quote("The roots of education are bitter, but the fruit is sweet.", false, true, english, aristotle));
        quotes.add(new Quote("It is the mark of an educated mind to be able to entertain a thought without accepting it.", false, true, english, aristotle));
        quotes.add(new Quote("Excellence is an art won by training and habituation. We do not act rightly because we have virtue or excellence, but we rather have those because we have acted rightly. We are what we repeatedly do. Excellence, then, is not an act but a habit.", false, true, english, aristotle));
        quotes.add(new Quote("Pleasure in the job puts perfection in the work.", false, true, english, aristotle));
        quotes.add(new Quote("There is no great genius without some touch of madness.", false, true, english, aristotle));
        quotes.add(new Quote("Love is composed of a single soul inhabiting two bodies.", false, true, english, aristotle));
        quotes.add(new Quote("The one exclusive sign of thorough knowledge is the power of teaching.", false, true, english, aristotle));
        quotes.add(new Quote("The worst form of inequality is to try to make unequal things equal.", false, true, english, aristotle));

        Author audreyHepburn = getAuthor("Audrey Hepburn");
        quotes.add(new Quote("Nothing is impossible, the word itself says 'I'm possible'!", false, true, english, audreyHepburn));
        quotes.add(new Quote("The beauty of a woman must be seen from in her eyes, because that is the doorway to her heart, the place where love resides.", false, true, english, audreyHepburn));
        quotes.add(new Quote("For beautiful eyes, look for the good in others; for beautiful lips, speak only words of kindness; and for poise, walk with the knowledge that you are never alone.", false, true, english, audreyHepburn));
        quotes.add(new Quote("The most important thing is to enjoy your life - to be happy - it's all that matters.", false, true, english, audreyHepburn));
        quotes.add(new Quote("The beauty of a woman is not in a facial mode but the true beauty in a woman is reflected in her soul. It is the caring that she lovingly gives the passion that she shows. The beauty of a woman grows with the passing years.", false, true, english, audreyHepburn));
        quotes.add(new Quote("I believe in pink. I believe that laughing is the best calorie burner. I believe in kissing, kissing a lot. I believe in being strong when everything seems to be going wrong. I believe that happy girls are the prettiest girls. I believe that tomorrow is another day and I believe in miracles.", false, true, english, audreyHepburn));
        quotes.add(new Quote("The best thing to hold onto in life is each other.", false, true, english, audreyHepburn));
        quotes.add(new Quote("The beauty of a woman is not in the clothes she wears, the figure that she carries or the way she combs her hair.", false, true, english, audreyHepburn));
        quotes.add(new Quote("I believe in manicures. I believe in overdressing. I believe in primping at leisure and wearing lipstick. I believe in pink. I believe happy girls are the prettiest girls. I believe that tomorrow is another day, and... I believe in miracles.", false, true, english, audreyHepburn));
        quotes.add(new Quote("Let's face it, a nice creamy chocolate cake does a lot for a lot of people; it does for me.", false, true, english, audreyHepburn));

        Author abrahamLincoln = getAuthor("Abraham Lincoln");
        quotes.add(new Quote("If this is coffee, please bring me some tea; but if this is tea, please bring me some coffee.", false, true, english, abrahamLincoln));
        quotes.add(new Quote("Character is like a tree and reputation like a shadow. The shadow is what we think of it; the tree is the real thing.", false, true, english, abrahamLincoln));
        quotes.add(new Quote("I am a slow walker, but I never walk back.", false, true, english, abrahamLincoln));
        quotes.add(new Quote("My dream is of a place and a time where America will once again be seen as the last best hope of earth.", false, true, english, abrahamLincoln));
        quotes.add(new Quote("Be sure you put your feet in the right place, then stand firm.", false, true, english, abrahamLincoln));
        quotes.add(new Quote("You cannot escape the responsibility of tomorrow by evading it today.", false, true, english, abrahamLincoln));
        quotes.add(new Quote("No man has a good enough memory to be a successful liar.", false, true, english, abrahamLincoln));
        quotes.add(new Quote("All that I am, or hope to be, I owe to my angel mother.", false, true, english, abrahamLincoln));
        quotes.add(new Quote("Nearly all men can stand adversity, but if you want to test a man's character, give him power.", false, true, english, abrahamLincoln));
        quotes.add(new Quote("America will never be destroyed from the outside. If we falter and lose our freedoms, it will be because we destroyed ourselves.", false, true, english, abrahamLincoln));

        Author aldousHuxley = getAuthor("Aldous Huxley");
        quotes.add(new Quote("There is only one corner of the universe you can be certain of improving, and that's your own self.", false, true, english, aldousHuxley));
        quotes.add(new Quote("There are things known and there are things unknown, and in between are the doors of perception.", false, true, english, aldousHuxley));
        quotes.add(new Quote("The secret of genius is to carry the spirit of the child into old age, which means never losing your enthusiasm.", false, true, english, aldousHuxley));
        quotes.add(new Quote("After silence, that which comes nearest to expressing the inexpressible is music.", false, true, english, aldousHuxley));
        quotes.add(new Quote("To travel is to discover that everyone is wrong about other countries.", false, true, english, aldousHuxley));
        quotes.add(new Quote("Experience is not what happens to you; it's what you do with what happens to you.", false, true, english, aldousHuxley));
        quotes.add(new Quote("Facts do not cease to exist because they are ignored.", false, true, english, aldousHuxley));
        quotes.add(new Quote("The more powerful and original a mind, the more it will incline towards the religion of solitude.", false, true, english, aldousHuxley));
        quotes.add(new Quote("All gods are homemade, and it is we who pull their strings, and so, give them the power to pull ours.", false, true, english, aldousHuxley));
        quotes.add(new Quote("A democracy which makes or even effectively prepares for modern, scientific war must necessarily cease to be democratic. No country can be really well prepared for modern war unless it is governed by a tyrant, at the head of a highly trained and perfectly obedient bureaucracy.", false, true, english, aldousHuxley));

        Author AalexanderHamilton = getAuthor("Alexander Hamilton");
        quotes.add(new Quote("Learn to think continentally.", false, true, english, AalexanderHamilton));
        quotes.add(new Quote("Real liberty is neither found in despotism or the extremes of democracy, but in moderate governments.", false, true, english, AalexanderHamilton));
        quotes.add(new Quote("In framing a government which is to be administered by men over men, the great difficulty lies in this: you must first enable the government to control the governed; and in the next place, oblige it to control itself.", false, true, english, AalexanderHamilton));
        quotes.add(new Quote("A promise must never be broken.", false, true, english, AalexanderHamilton));
        quotes.add(new Quote("It's not tyranny we desire; it's a just, limited, federal government.", false, true, english, AalexanderHamilton));
        quotes.add(new Quote("I never expect to see a perfect work from an imperfect man.", false, true, english, AalexanderHamilton));
        quotes.add(new Quote("I think the first duty of society is justice.", false, true, english, AalexanderHamilton));
        quotes.add(new Quote("A well adjusted person is one who makes the same mistake twice without getting nervous.", false, true, english, AalexanderHamilton));
        quotes.add(new Quote("Unless your government is respectable, foreigners will invade your rights; and to maintain tranquillity, it must be respectable - even to observe neutrality, you must have a strong government.", false, true, english, AalexanderHamilton));
        quotes.add(new Quote("There is a certain enthusiasm in liberty, that makes human nature rise above itself, in acts of bravery and heroism.", false, true, english, AalexanderHamilton));

        Author alexanderPope = getAuthor("Alexander Pope");
        quotes.add(new Quote("The greatest magnifying glasses in the world are a man's own eyes when they look upon his own person.", false, true, english, alexanderPope));
        quotes.add(new Quote("Fools rush in where angels fear to tread.", false, true, english, alexanderPope));
        quotes.add(new Quote("All nature is but art unknown to thee.", false, true, english, alexanderPope));
        quotes.add(new Quote("No one should be ashamed to admit they are wrong, which is but saying, in other words, that they are wiser today than they were yesterday.", false, true, english, alexanderPope));
        quotes.add(new Quote("No woman ever hates a man for being in love with her, but many a woman hate a man for being a friend to her.", false, true, english, alexanderPope));
        quotes.add(new Quote("Teach me to feel another's woe, to hide the fault I see, that mercy I to others show, that mercy show to me.", false, true, english, alexanderPope));
        quotes.add(new Quote("Charms strike the sight, but merit wins the soul.", false, true, english, alexanderPope));
        quotes.add(new Quote("On life's vast ocean diversely we sail. Reasons the card, but passion the gale.", false, true, english, alexanderPope));
        quotes.add(new Quote("To be angry is to revenge the faults of others on ourselves.", false, true, english, alexanderPope));
        quotes.add(new Quote("To err is human; to forgive, divine.", false, true, english, alexanderPope));

        Author arnoldSchwarzenegger = getAuthor("Arnold Schwarzenegger");
        quotes.add(new Quote("The worst thing I can be is the same as everybody else. I hate that.", false, true, english, arnoldSchwarzenegger));
        quotes.add(new Quote("It's simple, if it jiggles, it's fat.", false, true, english, arnoldSchwarzenegger));
        quotes.add(new Quote("Bodybuilding is much like any other sport. To be successful, you must dedicate yourself 100% to your training, diet and mental approach.", false, true, english, arnoldSchwarzenegger));
        quotes.add(new Quote("Help others and give something back. I guarantee you will discover that while public service improves the lives and the world around you, its greatest reward is the enrichment and new meaning it will bring your own life.", false, true, english, arnoldSchwarzenegger));
        quotes.add(new Quote("The mind is the limit. As long as the mind can envision the fact that you can do something, you can do it, as long as you really believe 100 percent.", false, true, english, arnoldSchwarzenegger));
        quotes.add(new Quote("The future is green energy, sustainability, renewable energy.", false, true, english, arnoldSchwarzenegger));
        quotes.add(new Quote("Milk is for babies. When you grow up you have to drink beer.", false, true, english, arnoldSchwarzenegger));
        quotes.add(new Quote("For me life is continuously being hungry. The meaning of life is not simply to exist, to survive, but to move ahead, to go up, to achieve, to conquer.", false, true, english, arnoldSchwarzenegger));
        quotes.add(new Quote("Just remember, you can't climb the ladder of success with your hands in your pockets.", false, true, english, arnoldSchwarzenegger));
        quotes.add(new Quote("Strength does not come from winning. Your struggles develop your strengths. When you go through hardships and decide not to surrender, that is strength.", false, true, english, arnoldSchwarzenegger));

        Author barackObama = getAuthor("Barack Obama");
        quotes.add(new Quote("Change will not come if we wait for some other person or some other time. We are the ones we've been waiting for. We are the change that we seek.", false, true, english, barackObama));
        quotes.add(new Quote("I see Americans of every party, every background, every faith who believe that we are stronger together: black, white, Latino, Asian, Native American; young, old; gay, straight; men, women, folks with disabilities, all pledging allegiance under the same proud flag to this big, bold country that we love. That's what I see. That's the America I know!", false, true, english, barackObama));
        quotes.add(new Quote("We need to reject any politics that targets people because of race or religion. This isn't a matter of political correctness. It's a matter of understanding what makes us strong. The world respects us not just for our arsenal; it respects us for our diversity and our openness and the way we respect every faith.", false, true, english, barackObama));
        quotes.add(new Quote("Let us remember we are all part of one American family. We are united in common values, and that includes belief in equality under the law, basic respect for public order, and the right of peaceful protest.", false, true, english, barackObama));
        quotes.add(new Quote("If you're walking down the right path and you're willing to keep walking, eventually you'll make progress.", false, true, english, barackObama));
        quotes.add(new Quote("If the people cannot trust their government to do the job for which it exists - to protect them and to promote their common welfare - all else is lost.", false, true, english, barackObama));
        quotes.add(new Quote("The future rewards those who press on. I don't have time to feel sorry for myself. I don't have time to complain. I'm going to press on.", false, true, english, barackObama));
        quotes.add(new Quote("Money is not the only answer, but it makes a difference.", false, true, english, barackObama));
        quotes.add(new Quote("America isn't Congress. America isn't Washington. America is the striving immigrant who starts a business, or the mom who works two low-wage jobs to give her kid a better life. America is the union leader and the CEO who put aside their differences to make the economy stronger.", false, true, english, barackObama));
        quotes.add(new Quote("After a century of striving, after a year of debate, after a historic vote, health care reform is no longer an unmet promise. It is the law of the land.", false, true, english, barackObama));

        Author benShapiro = getAuthor("Ben Shapiro");
        quotes.add(new Quote("During the Great Depression, levels of crime actually dropped. During the 1920s, when life was free and easy, so was crime. During the 1930s, when the entire American economy fell into a government-owned alligator moat, crime was nearly non-existent. During the 1950s and 1960s, when the economy was excellent, crime rose again.", false, true, english, benShapiro));
        quotes.add(new Quote("Socialism violates at least three of the Ten Commandments: It turns government into God, it legalizes thievery and it elevates covetousness. Discussions of income inequality, after all, aren't about prosperity but about petty spite. Why should you care how much money I make, so long as you are happy?", false, true, english, benShapiro));
        quotes.add(new Quote("Distrust of government isn't baseless cynicism. It's realism.", false, true, english, benShapiro));
        quotes.add(new Quote("Matt Damon's anti-fracking diatribe was funded by the royal family of the United Arab Emirates.", false, true, english, benShapiro));
        quotes.add(new Quote("Capitalism requires individual responsibility and accountability. People are seen as atomized units in a capitalist system - they are either useful, or they are not. They are not seen racially or ethnically or religiously. They consume and they produce, and those are their only relevant characteristics.", false, true, english, benShapiro));
        quotes.add(new Quote("The story of Detroit's bankruptcy was simple enough: Allow capitalism to grow the city, campaign against income inequality, tax the job creators until they flee, increase government spending in order to boost employment, promise generous pension plans to keep people voting for failure. Rinse, wash and repeat.", false, true, english, benShapiro));
        quotes.add(new Quote("Socialism has no moral justification whatsoever; poor people are not morally superior to rich people, nor are they owed anything by rich people simply because of their lack of success. Charity is not a socialist concept - it is a religious one, an acknowledgment of God's sovereignty over property, a sovereignty the Left utterly rejects.", false, true, english, benShapiro));
        quotes.add(new Quote("Every so often, we all gaze into the abyss. It's a depressing fact of life that eventually the clock expires; eventually the sand in the hourglass runs out. It's the leaving behind of everything that matters to us that hurts the most.", false, true, english, benShapiro));
        quotes.add(new Quote("Socialism states that you owe me something simply because I exist. Capitalism, by contrast, results in a sort of reality-forced altruism: I may not want to help you, I may dislike you, but if I don't give you a product or service you want, I will starve. Voluntary exchange is more moral than forced redistribution.", false, true, english, benShapiro));
        quotes.add(new Quote("Freedom of speech and thought matters, especially when it is speech and thought with which we disagree. The moment the majority decides to destroy people for engaging in thought it dislikes, thought crime becomes a reality.", false, true, english, benShapiro));

        Author benjaminDisraeli = getAuthor("Benjamin Disraeli");
        quotes.add(new Quote("A great city, whose image dwells in the memory of man, is the type of some great idea. Rome represents conquest; Faith hovers over the towers of Jerusalem; and Athens embodies the pre-eminent quality of the antique world, Art.", false, true, english, benjaminDisraeli));
        quotes.add(new Quote("Power has only one duty - to secure the social welfare of the People.", false, true, english, benjaminDisraeli));
        quotes.add(new Quote("Never apologize for showing feeling. When you do so, you apologize for the truth.", false, true, english, benjaminDisraeli));
        quotes.add(new Quote("The secret of success is constancy to purpose.", false, true, english, benjaminDisraeli));
        quotes.add(new Quote("Like all great travellers, I have seen more than I remember, and remember more than I have seen.", false, true, english, benjaminDisraeli));
        quotes.add(new Quote("Youth is a blunder; Manhood a struggle, Old Age a regret.", false, true, english, benjaminDisraeli));
        quotes.add(new Quote("Nurture your minds with great thoughts. To believe in the heroic makes heroes.", false, true, english, benjaminDisraeli));
        quotes.add(new Quote("Seeing much, suffering much, and studying much, are the three pillars of learning.", false, true, english, benjaminDisraeli));
        quotes.add(new Quote("Never complain and never explain.", false, true, english, benjaminDisraeli));
        quotes.add(new Quote("Courage is fire, and bullying is smoke.", false, true, english, benjaminDisraeli));

        Author benjaminFranklin = getAuthor("Benjamin Franklin");
        quotes.add(new Quote("Without freedom of thought, there can be no such thing as wisdom - and no such thing as public liberty without freedom of speech.", false, true, english, benjaminFranklin));
        quotes.add(new Quote("Early to bed and early to rise makes a man healthy, wealthy and wise.", false, true, english, benjaminFranklin));
        quotes.add(new Quote("Either write something worth reading or do something worth writing.", false, true, english, benjaminFranklin));
        quotes.add(new Quote("By failing to prepare, you are preparing to fail.", false, true, english, benjaminFranklin));
        quotes.add(new Quote("Wine is constant proof that God loves us and loves to see us happy.", false, true, english, benjaminFranklin));
        quotes.add(new Quote("We are all born ignorant, but one must work hard to remain stupid.", false, true, english, benjaminFranklin));
        quotes.add(new Quote("Well done is better than well said.", false, true, english, benjaminFranklin));
        quotes.add(new Quote("Without continual growth and progress, such words as improvement, achievement, and success have no meaning.", false, true, english, benjaminFranklin));
        quotes.add(new Quote("Tell me and I forget. Teach me and I remember. Involve me and I learn.", false, true, english, benjaminFranklin));
        quotes.add(new Quote("An investment in knowledge pays the best interest.", false, true, english, benjaminFranklin));

        Author bertrandRussell = getAuthor("Bertrand Russell");
        quotes.add(new Quote("I would never die for my beliefs because I might be wrong.", false, true, english, bertrandRussell));
        quotes.add(new Quote("Science is what you know, philosophy is what you don't know.", false, true, english, bertrandRussell));
        quotes.add(new Quote("Men are born ignorant, not stupid. They are made stupid by education.", false, true, english, bertrandRussell));
        quotes.add(new Quote("The only thing that will redeem mankind is cooperation.", false, true, english, bertrandRussell));
        quotes.add(new Quote("The world is full of magical things patiently waiting for our wits to grow sharper.", false, true, english, bertrandRussell));
        quotes.add(new Quote("Fear is the main source of superstition, and one of the main sources of cruelty. To conquer fear is the beginning of wisdom.", false, true, english, bertrandRussell));
        quotes.add(new Quote("The fact that an opinion has been widely held is no evidence whatever that it is not utterly absurd.", false, true, english, bertrandRussell));
        quotes.add(new Quote("The trouble with the world is that the stupid are cocksure and the intelligent are full of doubt.", false, true, english, bertrandRussell));
        quotes.add(new Quote("War does not determine who is right - only who is left.", false, true, english, bertrandRussell));
        quotes.add(new Quote("The good life is one inspired by love and guided by knowledge.", false, true, english, bertrandRussell));

        Author beyonceKnowles = getAuthor("Beyonce Knowles");
        quotes.add(new Quote("If everything was perfect, you would never learn and you would never grow.", false, true, english, beyonceKnowles));
        quotes.add(new Quote("Who I am on stage is very, very different to who I am in real life.", false, true, english, beyonceKnowles));
        quotes.add(new Quote("I can never be safe; I always try and go against the grain. As soon as I accomplish one thing, I just set a higher goal. That's how I've gotten to where I am.", false, true, english, beyonceKnowles));
        quotes.add(new Quote("I think it's healthy for a person to be nervous. It means you care - that you work hard and want to give a great performance. You just have to channel that nervous energy into the show.", false, true, english, beyonceKnowles));
        quotes.add(new Quote("Whenever I'm confused about something, I ask God to reveal the answers to my questions, and he does.", false, true, english, beyonceKnowles));
        quotes.add(new Quote("I feel like you get more bees with honey. But that doesn't mean I don't get frustrated in my life. My way of dealing with frustration is to shut down and to think and speak logically.", false, true, english, beyonceKnowles));
        quotes.add(new Quote("I always treat myself to one meal on Sundays when I can have whatever I want. Usually it's pizza, which is my favorite indulgence.", false, true, english, beyonceKnowles));
        quotes.add(new Quote("I get nervous when I don't get nervous. If I'm nervous I know I'm going to have a good show.", false, true, english, beyonceKnowles));
        quotes.add(new Quote("I hold a lot of things in. I'm always making sure everybody is okay. I usually don't rage; I usually don't curse. So for me, it's a great thing to be able to scream and say whatever I want.", false, true, english, beyonceKnowles));
        quotes.add(new Quote("There's my personal life, my sensitive side, and then me as a performer, sexy and energised and fun.", false, true, english, beyonceKnowles));

        Author billGates = getAuthor("Bill Gates");
        quotes.add(new Quote("The first rule of any technology used in a business is that automation applied to an efficient operation will magnify the efficiency. The second is that automation applied to an inefficient operation will magnify the inefficiency.", false, true, english, billGates));
        quotes.add(new Quote("Research shows that there is only half as much variation in student achievement between schools as there is among classrooms in the same school. If you want your child to get the best education possible, it is actually more important to get him assigned to a great teacher than to a great school.", false, true, english, billGates));
        quotes.add(new Quote("We all need people who will give us feedback. That's how we improve.", false, true, english, billGates));
        quotes.add(new Quote("Your most unhappy customers are your greatest source of learning.", false, true, english, billGates));
        quotes.add(new Quote("The advance of technology is based on making it fit in so that you don't really even notice it, so it's part of everyday life.", false, true, english, billGates));
        quotes.add(new Quote("The Internet is becoming the town square for the global village of tomorrow.", false, true, english, billGates));
        quotes.add(new Quote("Information technology and business are becoming inextricably interwoven. I don't think anybody can talk meaningfully about one without the talking about the other.", false, true, english, billGates));
        quotes.add(new Quote("It's fine to celebrate success but it is more important to heed the lessons of failure.", false, true, english, billGates));
        quotes.add(new Quote("Technology is just a tool. In terms of getting the kids working together and motivating them, the teacher is the most important.", false, true, english, billGates));
        quotes.add(new Quote("Success is a lousy teacher. It seduces smart people into thinking they can't lose.", false, true, english, billGates));

        Author billyGraham = getAuthor("Billy Graham");
        quotes.add(new Quote("Racial prejudice, anti-Semitism, or hatred of anyone with different beliefs has no place in the human mind or heart.", false, true, english, billyGraham));
        quotes.add(new Quote("Make sure of your commitment to Jesus Christ, and seek to follow Him every day. Don't be swayed by the false values and goals of this world, but put Christ and His will first in everything you do.", false, true, english, billyGraham));
        quotes.add(new Quote("When wealth is lost, nothing is lost; when health is lost, something is lost; when character is lost, all is lost.", false, true, english, billyGraham));
        quotes.add(new Quote("God proved His love on the Cross. When Christ hung, and bled, and died, it was God saying to the world, 'I love you.'", false, true, english, billyGraham));
        quotes.add(new Quote("I think that the Bible teaches that homosexuality is a sin, but the Bible also teaches that pride is a sin, jealousy is a sin, and hate is a sin, evil thoughts are a sin. So I don't think that homosexuality should be chosen as the overwhelming sin that we are doing today.", false, true, english, billyGraham));
        quotes.add(new Quote("God has given us two hands, one to receive with and the other to give with.", false, true, english, billyGraham));
        quotes.add(new Quote("Nothing can bring a real sense of security into the home except true love.", false, true, english, billyGraham));
        quotes.add(new Quote("Each life is made up of mistakes and learning, waiting and growing, practicing patience and being persistent.", false, true, english, billyGraham));
        quotes.add(new Quote("A child who is allowed to be disrespectful to his parents will not have true respect for anyone.", false, true, english, billyGraham));
        quotes.add(new Quote("The greatest legacy one can pass on to one's children and grandchildren is not money or other material things accumulated in one's life, but rather a legacy of character and faith.", false, true, english, billyGraham));

        Author blaisePascal = getAuthor("Blaise Pascal");
        quotes.add(new Quote("We view things not only from different sides, but with different eyes; we have no wish to find them alike.", false, true, english, blaisePascal));
        quotes.add(new Quote("Belief is a wise wager. Granted that faith cannot be proved, what harm will come to you if you gamble on its truth and it proves false? If you gain, you gain all; if you lose, you lose nothing. Wager, then, without hesitation, that He exists.", false, true, english, blaisePascal));
        quotes.add(new Quote("Men never do evil so completely and cheerfully as when they do it from religious conviction.", false, true, english, blaisePascal));
        quotes.add(new Quote("Noble deeds that are concealed are most esteemed.", false, true, english, blaisePascal));
        quotes.add(new Quote("Small minds are concerned with the extraordinary, great minds with the ordinary.", false, true, english, blaisePascal));
        quotes.add(new Quote("In faith there is enough light for those who want to believe and enough shadows to blind those who don't.", false, true, english, blaisePascal));
        quotes.add(new Quote("Love has reasons which reason cannot understand.", false, true, english, blaisePascal));
        quotes.add(new Quote("All men's miseries derive from not being able to sit in a quiet room alone.", false, true, english, blaisePascal));
        quotes.add(new Quote("Human beings must be known to be loved; but Divine beings must be loved to be known.", false, true, english, blaisePascal));
        quotes.add(new Quote("Kind words do not cost much. Yet they accomplish much.", false, true, english, blaisePascal));

        Author bobDylan = getAuthor("Bob Dylan");
        quotes.add(new Quote("I'll let you be in my dreams if I can be in yours.", false, true, english, bobDylan));
        quotes.add(new Quote("I accept chaos, I'm not sure whether it accepts me.", false, true, english, bobDylan));
        quotes.add(new Quote("A mistake is to commit a misunderstanding.", false, true, english, bobDylan));
        quotes.add(new Quote("What's money? A man is a success if he gets up in the morning and goes to bed at night and in between does what he wants to do.", false, true, english, bobDylan));
        quotes.add(new Quote("All I can do is be me, whoever that is.", false, true, english, bobDylan));
        quotes.add(new Quote("Inspiration is hard to come by. You have to take it where you find it.", false, true, english, bobDylan));
        quotes.add(new Quote("Yesterday's just a memory, tomorrow is never what it's supposed to be.", false, true, english, bobDylan));
        quotes.add(new Quote("No one is free, even the birds are chained to the sky.", false, true, english, bobDylan));
        quotes.add(new Quote("Take care of all your memories. For you cannot relive them.", false, true, english, bobDylan));
        quotes.add(new Quote("A hero is someone who understands the responsibility that comes with his freedom.", false, true, english, bobDylan));

        Author CSLewis = getAuthor("C. S. Lewis");
        quotes.add(new Quote("The task of the modern educator is not to cut down jungles, but to irrigate deserts.", false, true, english, CSLewis));
        quotes.add(new Quote("Education without values, as useful as it is, seems rather to make man a more clever devil.", false, true, english, CSLewis));
        quotes.add(new Quote("History isn't just the story of bad people doing bad things. It's quite as much a story of people trying to do good things. But somehow, something goes wrong.", false, true, english, CSLewis));
        quotes.add(new Quote("Friendship is unnecessary, like philosophy, like art... It has no survival value; rather it is one of those things that give value to survival.", false, true, english, CSLewis));
        quotes.add(new Quote("I believe in Christianity as I believe that the sun has risen: not only because I see it, but because by it I see everything else.", false, true, english, CSLewis));
        quotes.add(new Quote("If I find in myself a desire which no experience in this world can satisfy, the most probable explanation is that I was made for another world.", false, true, english, CSLewis));
        quotes.add(new Quote("Failures, repeated failures, are finger posts on the road to achievement. One fails forward toward success.", false, true, english, CSLewis));
        quotes.add(new Quote("We all want progress, but if you're on the wrong road, progress means doing an about-turn and walking back to the right road; in that case, the man who turns back soonest is the most progressive.", false, true, english, CSLewis));
        quotes.add(new Quote("Look for yourself, and you will find in the long run only hatred, loneliness, despair, rage, ruin, and decay. But look for Christ, and you will find Him, and with Him everything else thrown in.", false, true, english, CSLewis));
        quotes.add(new Quote("Courage is not simply one of the virtues, but the form of every virtue at the testing point.", false, true, english, CSLewis));

        Author carlJung = getAuthor("Carl Jung");
        quotes.add(new Quote("In all chaos there is a cosmos, in all disorder a secret order.", false, true, english, carlJung));
        quotes.add(new Quote("Every form of addiction is bad, no matter whether the narcotic be alcohol or morphine or idealism.", false, true, english, carlJung));
        quotes.add(new Quote("Children are educated by what the grown-up is and not by his talk.", false, true, english, carlJung));
        quotes.add(new Quote("The meeting of two personalities is like the contact of two chemical substances: if there is any reaction, both are transformed.", false, true, english, carlJung));
        quotes.add(new Quote("Everything that irritates us about others can lead us to an understanding of ourselves.", false, true, english, carlJung));
        quotes.add(new Quote("Your vision will become clear only when you can look into your own heart. Who looks outside, dreams; who looks inside, awakes.", false, true, english, carlJung));
        quotes.add(new Quote("Knowing your own darkness is the best method for dealing with the darknesses of other people.", false, true, english, carlJung));
        quotes.add(new Quote("One looks back with appreciation to the brilliant teachers, but with gratitude to those who touched our human feelings. The curriculum is so much necessary raw material, but warmth is the vital element for the growing plant and for the soul of the child.", false, true, english, carlJung));
        quotes.add(new Quote("Even a happy life cannot be without a measure of darkness, and the word happy would lose its meaning if it were not balanced by sadness. It is far better take things as they come along with patience and equanimity.", false, true, english, carlJung));
        quotes.add(new Quote("The word 'happy' would lose its meaning if it were not balanced by sadness.", false, true, english, carlJung));

        Author carlSagan = getAuthor("Carl Sagan");
        quotes.add(new Quote("The universe is not required to be in perfect harmony with human ambition.", false, true, english, carlSagan));
        quotes.add(new Quote("It is far better to grasp the universe as it really is than to persist in delusion, however satisfying and reassuring.", false, true, english, carlSagan));
        quotes.add(new Quote("For small creatures such as we the vastness is bearable only through love.", false, true, english, carlSagan));
        quotes.add(new Quote("Extinction is the rule. Survival is the exception.", false, true, english, carlSagan));
        quotes.add(new Quote("The brain is like a muscle. When it is in use we feel very good. Understanding is joyous.", false, true, english, carlSagan));
        quotes.add(new Quote("Imagination will often carry us to worlds that never were. But without it we go nowhere.", false, true, english, carlSagan));
        quotes.add(new Quote("Who are we? We find that we live on an insignificant planet of a humdrum star lost in a galaxy tucked away in some forgotten corner of a universe in which there are far more galaxies than people.", false, true, english, carlSagan));
        quotes.add(new Quote("If you wish to make an apple pie from scratch, you must first invent the universe.", false, true, english, carlSagan));
        quotes.add(new Quote("We live in a society exquisitely dependent on science and technology, in which hardly anyone knows anything about science and technology.", false, true, english, carlSagan));
        quotes.add(new Quote("Science is a way of thinking much more than it is a body of knowledge.", false, true, english, carlSagan));

        Author carlBurnett = getAuthor("Carl Burnett");
        quotes.add(new Quote("Only I can change my life. No one can do it for me.", false, true, english, carlBurnett));
        quotes.add(new Quote("When you have a dream, you've got to grab it and never let go.", false, true, english, carlBurnett));
        quotes.add(new Quote("Words, once they are printed, have a life of their own.", false, true, english, carlBurnett));
        quotes.add(new Quote("I liked myself better when I wasn't me.", false, true, english, carlBurnett));
        quotes.add(new Quote("You have to go through the falling down in order to learn to walk. It helps to know that you can survive it. That's an education in itself.", false, true, english, carlBurnett));
        quotes.add(new Quote("Everybody I know who is funny, it's in them. You can teach timing, or some people are able to tell a joke, though I don't like to tell jokes. But I think you have to be born with a sense of humor and a sense of timing.", false, true, english, carlBurnett));
        quotes.add(new Quote("My grandmother and I would go see movies, and we'd come back to the apartment - we had a one-room apartment in Hollywood - and I would kind of lock myself in this little dressing room area with a cracked mirror on the door and act out what I had just seen.", false, true, english, carlBurnett));
        quotes.add(new Quote("It costs a lot to sue a magazine, and it's too bad that we don't have a system where the losing team has to pay the winning team's lawyers.", false, true, english, carlBurnett));
        quotes.add(new Quote("'m not always optimistic. You wouldn't have all cylinders cooking if you were always like Mary Poppins.", false, true, english, carlBurnett));
        quotes.add(new Quote("I love to write. I have always loved writing. That was my first love.", false, true, english, carlBurnett));

        Author charlesDickens = getAuthor("Charles Dickens");
        quotes.add(new Quote("A moral being is one who is capable of reflecting on his past actions and their motives - of approving of some and disapproving of others.", false, true, english, charlesDickens));
        quotes.add(new Quote("Man is descended from a hairy, tailed quadruped, probably arboreal in its habits.", false, true, english, charlesDickens));
        quotes.add(new Quote("The very essence of instinct is that it's followed independently of reason.", false, true, english, charlesDickens));
        quotes.add(new Quote("A scientific man ought to have no wishes, no affections, - a mere heart of stone.", false, true, english, charlesDickens));
        quotes.add(new Quote("I love fools' experiments. I am always making them.", false, true, english, charlesDickens));
        quotes.add(new Quote("Ignorance more frequently begets confidence than does knowledge: it is those who know little, and not those who know much, who so positively assert that this or that problem will never be solved by science.", false, true, english, charlesDickens));
        quotes.add(new Quote("The mystery of the beginning of all things is insoluble by us; and I for one must be content to remain an agnostic.", false, true, english, charlesDickens));
        quotes.add(new Quote("An American monkey, after getting drunk on brandy, would never touch it again, and thus is much wiser than most men.", false, true, english, charlesDickens));
        quotes.add(new Quote("A man's friendships are one of the best measures of his worth.", false, true, english, charlesDickens));
        quotes.add(new Quote("A man who dares to waste one hour of time has not discovered the value of life.", false, true, english, charlesDickens));

        Author charlesRSwindoll = getAuthor("Charles R. Swindoll");
        quotes.add(new Quote("The remarkable thing is, we have a choice everyday regarding the attitude we will embrace for that day.", false, true, english, charlesRSwindoll));
        quotes.add(new Quote("A teardrop on earth summons the King of heaven.", false, true, english, charlesRSwindoll));
        quotes.add(new Quote("We are all faced with a series of great opportunities brilliantly disguised as impossible situations.", false, true, english, charlesRSwindoll));
        quotes.add(new Quote("Prejudice is a learned trait. You're not born prejudiced; you're taught it.", false, true, english, charlesRSwindoll));
        quotes.add(new Quote("We cannot change our past. We can not change the fact that people act in a certain way. We can not change the inevitable. The only thing we can do is play on the one string we have, and that is our attitude.", false, true, english, charlesRSwindoll));
        quotes.add(new Quote("Attitude is more important than the past, than education, than money, than circumstances, than what people do or say. It is more important than appearance, giftedness, or skill.", false, true, english, charlesRSwindoll));
        quotes.add(new Quote("The difference between something good and something great is attention to detail.", false, true, english, charlesRSwindoll));
        quotes.add(new Quote("Each day of our lives we make deposits in the memory banks of our children.", false, true, english, charlesRSwindoll));
        quotes.add(new Quote("I cannot even imagine where I would be today were it not for that handful of friends who have given me a heart full of joy. Let's face it, friends make life a lot more fun.", false, true, english, charlesRSwindoll));
        quotes.add(new Quote("Life is 10% what happens to you and 90% how you react to it.", false, true, english, charlesRSwindoll));

        Author cheGuevara = getAuthor("Che Guevara");
        quotes.add(new Quote("The revolution is not an apple that falls when it is ripe. You have to make it fall.", false, true, english, cheGuevara));
        quotes.add(new Quote("I have a wish. It as a fear as well - that in my end will be my beginning.", false, true, english, cheGuevara));
        quotes.add(new Quote("The true revolutionary is guided by a great feeling of love. It is impossible to think of a genuine revolutionary lacking this quality.", false, true, english, cheGuevara));
        quotes.add(new Quote("We cannot be sure of having something to live for unless we are willing to die for it.", false, true, english, cheGuevara));
        quotes.add(new Quote("I know you are here to kill me. Shoot, coward, you are only going to kill a man.", false, true, english, cheGuevara));
        quotes.add(new Quote("The only passion that guides me is for the truth... I look at everything from this point of view.", false, true, english, cheGuevara));
        quotes.add(new Quote("Passion is needed for any great work, and for the revolution, passion and audacity are required in big doses.", false, true, english, cheGuevara));
        quotes.add(new Quote("Remember that the revolution is what is important, and each one of us, alone, is worth nothing.", false, true, english, cheGuevara));
        quotes.add(new Quote("Study hard so that you can master technology, which allows us to master nature.", false, true, english, cheGuevara));
        quotes.add(new Quote("I am one of those people who believes that the solution to the world's problems is to be found behind the Iron Curtain.", false, true, english, cheGuevara));

        Author christopherHitchens = getAuthor("Christopher Hitchens");
        quotes.add(new Quote("The concept of loneliness and exile and self-sufficiency continually bucks me up.", false, true, english, christopherHitchens));
        quotes.add(new Quote("I don't think it's possible to have a sense of tragedy without having a sense of humor.", false, true, english, christopherHitchens));
        quotes.add(new Quote("To 'choose' dogma and faith over doubt and experience is to throw out the ripening vintage and to reach greedily for the Kool-Aid.", false, true, english, christopherHitchens));
        quotes.add(new Quote("Trust is not the same as faith. A friend is someone you trust. Putting faith in anyone is a mistake.", false, true, english, christopherHitchens));
        quotes.add(new Quote("To the dumb question, 'Why me?' the cosmos barely bothers to return the reply, 'Why not?'", false, true, english, christopherHitchens));
        quotes.add(new Quote("Religion is part of the human make-up. It's also part of our cultural and intellectual history. Religion was our first attempt at literature, the texts, our first attempt at cosmology, making sense of where we are in the universe, our first attempt at health care, believing in faith healing, our first attempt at philosophy.", false, true, english, christopherHitchens));
        quotes.add(new Quote("What can be asserted without evidence can be dismissed without evidence.", false, true, english, christopherHitchens));
        quotes.add(new Quote("I learned that very often the most intolerant and narrow-minded people are the ones who congratulate themselves on their tolerance and open-mindedness.", false, true, english, christopherHitchens));
        quotes.add(new Quote("A gentleman is never rude except on purpose - I can honestly be nasty sober, believe you me.", false, true, english, christopherHitchens));
        quotes.add(new Quote("Owners of dogs will have noticed that, if you provide them with food and water and shelter and affection, they will think you are God. Whereas owners of cats are compelled to realize that, if you provide them with food and water and affection, they draw the conclusion that they are God.", false, true, english, christopherHitchens));

        Author clintEastwood = getAuthor("Clint Eastwood");
        quotes.add(new Quote("God gave you a brain. Do the best you can with it. And you don't have to be Einstein, but Einstein was mentally tough. He believed what he believed. And he worked out things. And he argued with people who disagreed with him. But I'm sure he didn't call everybody jerks.", false, true, english, clintEastwood));
        quotes.add(new Quote("I tried being reasonable, I didn't like it.", false, true, english, clintEastwood));
        quotes.add(new Quote("What you put into life is what you get out of it.", false, true, english, clintEastwood));
        quotes.add(new Quote("Sometimes if you want to see a change for the better, you have to take things into your own hands.", false, true, english, clintEastwood));
        quotes.add(new Quote("If you want a guarantee, buy a toaster.", false, true, english, clintEastwood));
        quotes.add(new Quote("You should never give up your inner self.", false, true, english, clintEastwood));
        quotes.add(new Quote("I have a very strict gun control policy: if there's a gun around, I want to be in control of it.", false, true, english, clintEastwood));
        quotes.add(new Quote("They say marriages are made in Heaven. But so is thunder and lightning.", false, true, english, clintEastwood));
        quotes.add(new Quote("There's a rebel lying deep in my soul.", false, true, english, clintEastwood));
        quotes.add(new Quote("Respect your efforts, respect yourself. Self-respect leads to self-discipline. When you have both firmly under your belt, that's real power.", false, true, english, clintEastwood));

        Author conorMcGregor = getAuthor("Conor McGregor");
        quotes.add(new Quote("You might win some, you might lose some. But you go in, you challenge yourself, you become a better man, a better individual, a better fighter.", false, true, english, conorMcGregor));
        quotes.add(new Quote("I enjoy competition. I enjoy challenges. If a challenge is in front of me and it appeals to me, I will go ahead and conquer it.", false, true, english, conorMcGregor));
        quotes.add(new Quote("My success isn't a result of arrogance - it's a result of belief.", false, true, english, conorMcGregor));
        quotes.add(new Quote("The more you seek the uncomfortable, the more you will become comfortable.", false, true, english, conorMcGregor));
        quotes.add(new Quote("I am in the fighting game. I don't care about anything else. I don't watch the news, I don't care about politics, I don't care about other sports. I don't care about anything I don't need to care about. This is my sport: it is my life. I study it; I think about it all the time. Nothing else matters.", false, true, english, conorMcGregor));
        quotes.add(new Quote("I'm just looking to learn, grow, stay focused, and become a better fighter and a better athlete.", false, true, english, conorMcGregor));
        quotes.add(new Quote("I'm not going to get somewhere and say, 'OK, I'm done.' Success is never final; I'll just keep on going. The same way as failure never being fatal. Just keep going. I'm going to the stars and then past them.", false, true, english, conorMcGregor));
        quotes.add(new Quote("I'm just trying to be myself. I'm not trying to be anyone else.", false, true, english, conorMcGregor));
        quotes.add(new Quote("The thing about the truth is, not a lot of people can handle it.", false, true, english, conorMcGregor));
        quotes.add(new Quote("I don't feel pressure in a negative way. I like pressure. I feel excitement and calm at the same time. No pressure, no diamonds. I want pressure: pressure creates drama, creates emotion.", false, true, english, conorMcGregor));

        Author dalaiLama = getAuthor("Dalai Lama");
        quotes.add(new Quote("Happiness is not something ready made. It comes from your own actions.", false, true, english, dalaiLama));
        quotes.add(new Quote("Calm mind brings inner strength and self-confidence, so that's very important for good health.", false, true, english, dalaiLama));
        quotes.add(new Quote("In order to carry a positive action we must develop here a positive vision.", false, true, english, dalaiLama));
        quotes.add(new Quote("Love and compassion are necessities, not luxuries. Without them humanity cannot survive.", false, true, english, dalaiLama));
        quotes.add(new Quote("Old friends pass away, new friends appear. It is just like the days. An old day passes, a new day arrives. The important thing is to make it meaningful: a meaningful friend - or a meaningful day.", false, true, english, dalaiLama));
        quotes.add(new Quote("Be kind whenever possible. It is always possible.", false, true, english, dalaiLama));
        quotes.add(new Quote("If you want others to be happy, practice compassion. If you want to be happy, practice compassion.", false, true, english, dalaiLama));
        quotes.add(new Quote("When we meet real tragedy in life, we can react in two ways - either by losing hope and falling into self-destructive habits, or by using the challenge to find our inner strength. Thanks to the teachings of Buddha, I have been able to take this second way.", false, true, english, dalaiLama));
        quotes.add(new Quote("When you practice gratefulness, there is a sense of respect toward others.", false, true, english, dalaiLama));
        quotes.add(new Quote("Our prime purpose in this life is to help others. And if you can't help them, at least don't hurt them.", false, true, english, dalaiLama));

        Author douglasAdams = getAuthor("Douglas Adams");
        quotes.add(new Quote("In the beginning the Universe was created. This has made a lot of people very angry and been widely regarded as a bad move.", false, true, english, douglasAdams));
        quotes.add(new Quote("It is a well-known fact that those people who must want to rule people are, ipso facto, those least suited to do it... anyone who is capable of getting themselves made President should on no account be allowed to do the job.", false, true, english, douglasAdams));
        quotes.add(new Quote("This must be Thursday. I never could get the hang of Thursdays.", false, true, english, douglasAdams));
        quotes.add(new Quote("It is a mistake to think you can solve any major problems just with potatoes.", false, true, english, douglasAdams));
        quotes.add(new Quote("Human beings, who are almost unique in having the ability to learn from the experience of others, are also remarkable for their apparent disinclination to do so.", false, true, english, douglasAdams));
        quotes.add(new Quote("A common mistake that people make when trying to design something completely foolproof is to underestimate the ingenuity of complete fools.", false, true, english, douglasAdams));
        quotes.add(new Quote("I may not have gone where I intended to go, but I think I have ended up where I intended to be.", false, true, english, douglasAdams));
        quotes.add(new Quote("Flying is learning how to throw yourself at the ground and miss.", false, true, english, douglasAdams));
        quotes.add(new Quote("I love deadlines. I like the whooshing sound they make as they fly by.", false, true, english, douglasAdams));
        quotes.add(new Quote("To give real service you must add something which cannot be bought or measured with money, and that is sincerity and integrity.", false, true, english, douglasAdams));

        Author dickGregory = getAuthor("Dick Gregory");
        quotes.add(new Quote("Political promises are much like marriage vows. They are made at the beginning of the relationship between candidate and voter, but are quickly forgotten.", false, true, english, dickGregory));
        quotes.add(new Quote("When I lost my rifle, the Army charged me 85 dollars. That is why in the Navy the Captain goes down with the ship.", false, true, english, dickGregory));
        quotes.add(new Quote("Riches do not delight us so much with their possession, as torment us with their loss.", false, true, english, dickGregory));
        quotes.add(new Quote("There's a God force inside of you that gives you a will to live.", false, true, english, dickGregory));
        quotes.add(new Quote("I am really enjoying the new Martin Luther King Jr stamp - just think about all those white bigots, licking the backside of a black man.", false, true, english, dickGregory));
        quotes.add(new Quote("Just being a Negro doesn't qualify you to understand the race situation any more than being sick makes you an expert on medicine.", false, true, english, dickGregory));
        quotes.add(new Quote("You know, I always say white is not a colour, white is an attitude, and if you haven't got trillions of dollars in the bank that you don't need, you can't be white.", false, true, english, dickGregory));
        quotes.add(new Quote("We thought I was going to be a great athlete, and we were wrong, and I thought I was going to be a great entertainer, and that wasn't it either. I'm going to be an American Citizen. First class.", false, true, english, dickGregory));
        quotes.add(new Quote("We used to root for the Indians against the cavalry, because we didn't think it was fair in the history books that when the cavalry won it was a great victory, and when the Indians won it was a massacre.", false, true, english, dickGregory));
        quotes.add(new Quote("Coconut milk is the only thing on this planet that comes identically to mother's milk.", false, true, english, dickGregory));

        Author dollyParton = getAuthor("Dolly Parton");
        quotes.add(new Quote("When I wake up, I expect things to be good. If they're not, then I try to set about trying to make them as good as I can 'cause I know I'm gonna have to live that day anyway. So why not try to make the most of it if you can? Some days, they pan out a little better than others, but you still gotta always just try.", false, true, english, dollyParton));
        quotes.add(new Quote("You can be rich in spirit, kindness, love and all those things that you can't put a dollar sign on.", false, true, english, dollyParton));
        quotes.add(new Quote("I wanted to write a book that talked about the emotions of children, which is the rainbow. We all have moods. We talk about being blue when we're sad, and being yellow when we're cowards, and when we're mad, we're red.", false, true, english, dollyParton));
        quotes.add(new Quote("I'm not offended by all the dumb blonde jokes because I know I'm not dumb... and I also know that I'm not blonde.", false, true, english, dollyParton));
        quotes.add(new Quote("Storms make trees take deeper roots.", false, true, english, dollyParton));
        quotes.add(new Quote("No matter what, I always make it home for Christmas. I love to go to my Tennessee Mountain Home and invite all of my nieces and nephews and their spouses and kids and do what we all like to do - eat, laugh, trade presents and just enjoy each other... and sometimes I even dress up like Santa Claus!", false, true, english, dollyParton));
        quotes.add(new Quote("It's a good thing I was born a girl, otherwise I'd be a drag queen.", false, true, english, dollyParton));
        quotes.add(new Quote("I thank God for my failures. Maybe not at the time but after some reflection. I never feel like a failure just because something I tried has failed.", false, true, english, dollyParton));
        quotes.add(new Quote("If you don't like the road you're walking, start paving another one.", false, true, english, dollyParton));
        quotes.add(new Quote("The way I see it, if you want the rainbow, you gotta put up with the rain.", false, true, english, dollyParton));

        Author donaldTrump = getAuthor("Donald Trump");
        quotes.add(new Quote("When you open your heart to patriotism, there is no room for prejudice. The Bible tells us, 'How good and pleasant it is when God's people live together in unity.", false, true, english, donaldTrump));
        quotes.add(new Quote("When somebody challenges you, fight back. Be brutal, be tough.", false, true, english, donaldTrump));
        quotes.add(new Quote("I have embraced crying mothers who have lost their children because our politicians put their personal agendas before the national good. I have no patience for injustice, no tolerance for government incompetence, no sympathy for leaders who fail their citizens.", false, true, english, donaldTrump));
        quotes.add(new Quote("We will make America strong again. We will make America proud again. We will make America safe again. And we will make America great again.", false, true, english, donaldTrump));
        quotes.add(new Quote("One of the key problems today is that politics is such a disgrace, good people don't go into government.", false, true, english, donaldTrump));
        quotes.add(new Quote("It is time to remember that old wisdom our soldiers will never forget: that whether we are black or brown or white, we all bleed the same red blood of patriots, we all enjoy the same glorious freedoms, and we all salute the same great American Flag.", false, true, english, donaldTrump));
        quotes.add(new Quote("My whole life is about winning. I don't lose often. I almost never lose.", false, true, english, donaldTrump));
        quotes.add(new Quote("You have to think anyway, so why not think big?", false, true, english, donaldTrump));
        quotes.add(new Quote("No dream is too big. No challenge is too great. Nothing we want for our future is beyond our reach.", false, true, english, donaldTrump));
        quotes.add(new Quote("Sometimes by losing a battle you find a new way to win the war.", false, true, english, donaldTrump));

        Author DrSeuss = getAuthor("Dr. Seuss");
        quotes.add(new Quote("Think left and think right and think low and think high. Oh, the thinks you can think up if only you try!", false, true, english, DrSeuss));
        quotes.add(new Quote("The more that you read, the more things you will know. The more that you learn, the more places you'll go.", false, true, english, DrSeuss));
        quotes.add(new Quote("Unless someone like you cares a whole awful lot, nothing is going to get better. It's not.", false, true, english, DrSeuss));
        quotes.add(new Quote("Step with care and great tact, and remember that Life's a Great Balancing Act.", false, true, english, DrSeuss));
        quotes.add(new Quote("I like nonsense; it wakes up the brain cells.", false, true, english, DrSeuss));
        quotes.add(new Quote("From there to here, and here to there, funny things are everywhere.", false, true, english, DrSeuss));
        quotes.add(new Quote("You have brains in your head. You have feet in your shoes. You can steer yourself in any direction you choose. You're on your own, and you know what you know. And you are the guy who'll decide where to go.", false, true, english, DrSeuss));
        quotes.add(new Quote("A person's a person, no matter how small.", false, true, english, DrSeuss));
        quotes.add(new Quote("Don't cry because it's over. Smile because it happened.", false, true, english, DrSeuss));
        quotes.add(new Quote("Today you are you! That is truer than true! There is no one alive who is you-er than you!", false, true, english, DrSeuss));

        Author drake = getAuthor("Drake");
        quotes.add(new Quote("Sometimes it's the journey that teaches you a lot about your destination.", false, true, english, drake));
        quotes.add(new Quote("Jealousy is just love and hate at the same time.", false, true, english, drake));
        quotes.add(new Quote("People like to build their own story about my life. I don't know if it makes them feel better, or if it makes it okay for them to not like me, but the last thing I grew up as was rich.", false, true, english, drake));
        quotes.add(new Quote("I feel connected to my generation through the music, but I also fear for us. We're in a very self-destructive state where we're addicted to outside opinions and we all feel like we have fans.", false, true, english, drake));
        quotes.add(new Quote("Me and my dad are friends. We're cool. I'll never be disappointed again, because I don't expect anything anymore from him. I just let him exist, and that's how we get along.", false, true, english, drake));
        quotes.add(new Quote("Trying to meet new women, it's always a little more difficult as opposed to calling somebody I knew that's single and trying to rebuild that connection.", false, true, english, drake));
        quotes.add(new Quote("When I'm writing, I'm thinking about how the songs are going to play live. Fifty bars of rap don't translate onstage. No matter how potent the music, you lose the crowd. They want a hook; they want to sing your stuff back to you.", false, true, english, drake));
        quotes.add(new Quote("Reviews condition people. At the end of the day, a lot of human minds are malleable. They can be easily shaped with strong words.", false, true, english, drake));
        quotes.add(new Quote("My dad is a great writer. Naturally talented, naturally charming. He embodies that back-in-the-day cool.", false, true, english, drake));
        quotes.add(new Quote("I like Ryan Gosling as an actor. I watch all of his movies, and he's Canadian and I just like his swag. I read his interviews and I'm a big fan of his.", false, true, english, drake));

        Author dwightDEisenhower = getAuthor("Dwight D. Eisenhower");
        quotes.add(new Quote("Leadership is the art of getting someone else to do something you want done because he wants to do it.", false, true, english, dwightDEisenhower));
        quotes.add(new Quote("A people that values its privileges above its principles soon loses both.", false, true, english, dwightDEisenhower));
        quotes.add(new Quote("Neither a wise man nor a brave man lies down on the tracks of history to wait for the train of the future to run over him.", false, true, english, dwightDEisenhower));
        quotes.add(new Quote("Here in America we are descended in blood and in spirit from revolutionists and rebels - men and women who dare to dissent from accepted doctrine. As their heirs, may we never confuse honest dissent with disloyal subversion.", false, true, english, dwightDEisenhower));
        quotes.add(new Quote("Plans are nothing; planning is everything.", false, true, english, dwightDEisenhower));
        quotes.add(new Quote("In preparing for battle I have always found that plans are useless, but planning is indispensable.", false, true, english, dwightDEisenhower));
        quotes.add(new Quote("You don't lead by hitting people over the head - that's assault, not leadership. Dwight D. Eisenhower", false, true, english, dwightDEisenhower));
        quotes.add(new Quote("Motivation is the art of getting people to do what you want them to do because they want to do it.", false, true, english, dwightDEisenhower));
        quotes.add(new Quote("How far you can go without destroying from within what you are trying to defend from without?", false, true, english, dwightDEisenhower));
        quotes.add(new Quote("The supreme quality for leadership is unquestionably integrity. Without it, no real success is possible, no matter whether it is on a section gang, a football field, in an army, or in an office.", false, true, english, dwightDEisenhower));

        Author elieWiesel = getAuthor("Elie Wiesel");
        quotes.add(new Quote("Just as despair can come to one only from other human beings, hope, too, can be given to one only by other human beings.", false, true, english, elieWiesel));
        quotes.add(new Quote("Wherever men and women are persecuted because of their race, religion, or political views, that place must - at that moment - become the center of the universe.", false, true, english, elieWiesel));
        quotes.add(new Quote("I marvel at the resilience of the Jewish people. Their best characteristic is their desire to remember. No other people has such an obsession with memory.", false, true, english, elieWiesel));
        quotes.add(new Quote("Most people think that shadows follow, precede or surround beings or objects. The truth is that they also surround words, ideas, desires, deeds, impulses and memories.", false, true, english, elieWiesel));
        quotes.add(new Quote("That I survived the Holocaust and went on to love beautiful girls, to talk, to write, to have toast and tea and live my life - that is what is abnormal.", false, true, english, elieWiesel));
        quotes.add(new Quote("For me, every hour is grace. And I feel gratitude in my heart each time I can meet someone and look at his or her smile.", false, true, english, elieWiesel));
        quotes.add(new Quote("The opposite of love is not hate, it's indifference.", false, true, english, elieWiesel));
        quotes.add(new Quote("There may be times when we are powerless to prevent injustice, but there must never be a time when we fail to protest.", false, true, english, elieWiesel));
        quotes.add(new Quote("We must take sides. Neutrality helps the oppressor, never the victim. Silence encourages the tormentor, never the tormented.", false, true, english, elieWiesel));
        quotes.add(new Quote("Without memory, there is no culture. Without memory, there would be no civilization, no society, no future.", false, true, english, elieWiesel));

        Author elizabethI = getAuthor("Elizabeth I");
        quotes.add(new Quote("I know I have the body of a weak and feeble woman, but I have the heart and stomach of a king, and of a king of England too.", false, true, english, elizabethI));
        quotes.add(new Quote("I do not want a husband who honours me as a queen, if he does not love me as a woman.", false, true, english, elizabethI));
        quotes.add(new Quote("Though the sex to which I belong is considered weak you will nevertheless find me a rock that bends to no wind.", false, true, english, elizabethI));
        quotes.add(new Quote("Fear not, we are of the nature of the lion, and cannot descend to the destruction of mice and such small beasts.", false, true, english, elizabethI));
        quotes.add(new Quote("There is nothing about which I am more anxious than my country, and for its sake I am willing to die ten deaths, if that be possible.", false, true, english, elizabethI));
        quotes.add(new Quote("I have the heart of a man, not a woman, and I am not afraid of anything.", false, true, english, elizabethI));
        quotes.add(new Quote("God has given such brave soldiers to this Crown that, if they do not frighten our neighbours, at least they prevent us from being frightened by them.", false, true, english, elizabethI));
        quotes.add(new Quote("Must! Is must a word to be addressed to princes? Little man, little man! Thy father, if he had been alive, durst not have used that word.", false, true, english, elizabethI));
        quotes.add(new Quote("Ye may have a greater prince, but ye shall never have a more loving prince.", false, true, english, elizabethI));
        quotes.add(new Quote("My mortal foe can no ways wish me a greater harm than England's hate; neither should death be less welcome unto me than such a mishap betide me.", false, true, english, elizabethI));

        Author ellenDeGeneres = getAuthor("Ellen DeGeneres");
        quotes.add(new Quote("Sometimes you can't see yourself clearly until you see yourself through the eyes of others.", false, true, english, ellenDeGeneres));
        quotes.add(new Quote("It's our challenges and obstacles that give us layers of depth and make us interesting. Are they fun when they happen? No. But they are what make us unique. And that's what I know for sure... I think.", false, true, english, ellenDeGeneres));
        quotes.add(new Quote("Find out who you are and be that person. That's what your soul was put on this Earth to be. Find that truth, live that truth and everything else will come.", false, true, english, ellenDeGeneres));
        quotes.add(new Quote("I'm not an activist; I don't look for controversy. I'm not a political person, but I'm a person with compassion. I care passionately about equal rights. I care about human rights. I care about animal rights.", false, true, english, ellenDeGeneres));
        quotes.add(new Quote("We need more kindness, more compassion, more joy, more laughter. I definitely want to contribute to that.", false, true, english, ellenDeGeneres));
        quotes.add(new Quote("I had everything I'd hoped for, but I wasn't being myself. So I decided to be honest about who I was. It was strange: The people who loved me for being funny suddenly didn't like me for being... me.", false, true, english, ellenDeGeneres));
        quotes.add(new Quote("We focus so much on our differences, and that is creating, I think, a lot of chaos and negativity and bullying in the world. And I think if everybody focused on what we all have in common - which is - we all want to be happy.", false, true, english, ellenDeGeneres));
        quotes.add(new Quote("If we're destroying our trees and destroying our environment and hurting animals and hurting one another and all that stuff, there's got to be a very powerful energy to fight that. I think we need more love in the world. We need more kindness, more compassion, more joy, more laughter. I definitely want to contribute to that.", false, true, english, ellenDeGeneres));
        quotes.add(new Quote("Here are the values that I stand for: honesty, equality, kindness, compassion, treating people the way you want to be treated and helping those in need. To me, those are traditional values.", false, true, english, ellenDeGeneres));
        quotes.add(new Quote("I was raised around heterosexuals, as all heterosexuals are, that's where us gay people come from... you heterosexuals.", false, true, english, ellenDeGeneres));

        Author elonMusk = getAuthor("Elon Musk");
        quotes.add(new Quote("We're running the most dangerous experiment in history right now, which is to see how much carbon dioxide the atmosphere... can handle before there is an environmental catastrophe.", false, true, english, elonMusk));
        quotes.add(new Quote("If you get up in the morning and think the future is going to be better, it is a bright day. Otherwise, it's not.", false, true, english, elonMusk));
        quotes.add(new Quote("When I was in college, I wanted to be involved in things that would change the world.", false, true, english, elonMusk));
        quotes.add(new Quote("I think that's the single best piece of advice: constantly think about how you could be doing things better and questioning yourself.", false, true, english, elonMusk));
        quotes.add(new Quote("If you're trying to create a company, it's like baking a cake. You have to have all the ingredients in the right proportion.", false, true, english, elonMusk));
        quotes.add(new Quote("I would like to die on Mars. Just not on impact.", false, true, english, elonMusk));
        quotes.add(new Quote("Patience is a virtue, and I'm learning patience. It's a tough lesson.", false, true, english, elonMusk));
        quotes.add(new Quote("Some people don't like change, but you need to embrace change if the alternative is disaster.", false, true, english, elonMusk));
        quotes.add(new Quote("I think it's very important to have a feedback loop, where you're constantly thinking about what you've done and how you could be doing it better. I think that's the single best piece of advice: constantly think about how you could be doing things better and questioning yourself.", false, true, english, elonMusk));
        quotes.add(new Quote("When something is important enough, you do it even if the odds are not in your favor.", false, true, english, elonMusk));

        Author elvisPresley = getAuthor("Elvis Presley");
        quotes.add(new Quote("I'm not trying to be sexy. It's just my way of expressing myself when I move around.", false, true, english, elvisPresley));
        quotes.add(new Quote("Truth is like the sun. You can shut it out for a time, but it ain't goin' away.", false, true, english, elvisPresley));
        quotes.add(new Quote("Rock and roll music, if you like it, if you feel it, you can't help but move to it. That's what happens to me. I can't help it.'", false, true, english, elvisPresley));
        quotes.add(new Quote("A live concert to me is exciting because of all the electricity that is generated in the crowd and on stage. It's my favorite part of the business, live concerts.", false, true, english, elvisPresley));
        quotes.add(new Quote("Rhythm is something you either have or don't have, but when you have it, you have it all over.", false, true, english, elvisPresley));
        quotes.add(new Quote("Adversity is sometimes hard upon a man; but for one man who can stand prosperity, there are a hundred that will stand adversity.", false, true, english, elvisPresley));
        quotes.add(new Quote("I'll never feel comfortable taking a strong drink, and I'll never feel easy smoking a cigarette. I just don't think those things are right for me.", false, true, english, elvisPresley));
        quotes.add(new Quote("When I was a boy, I always saw myself as a hero in comic books and in movies. I grew up believing this dream.", false, true, english, elvisPresley));
        quotes.add(new Quote("Man, that record came out and was real big in Memphis. They started playing it, and it got real big. Don't know why-the lyrics had no meaning.", false, true, english, elvisPresley));
        quotes.add(new Quote("It's human nature to gripe, but I'm going ahead and doing the best I can.", false, true, english, elvisPresley));

        Author eminem = getAuthor("Eminem");
        quotes.add(new Quote("A lot of truth is said in jest.", false, true, english, eminem));
        quotes.add(new Quote("Sometimes I feel like rap music is almost the key to stopping racism.", false, true, english, eminem));
        quotes.add(new Quote("Dealing with backstabbers, there was one thing I learned. They're only powerful when you got your back turned.", false, true, english, eminem));
        quotes.add(new Quote("Everybody has goals, aspirations or whatever, and everybody has been at a point in their life where nobody believed in them.", false, true, english, eminem));
        quotes.add(new Quote("I am whatever you say I am; if I wasn't, then why would you say I am.", false, true, english, eminem));
        quotes.add(new Quote("I'm stupid, I'm ugly, I'm dumb, I smell. Did I mention I'm stupid?", false, true, english, eminem));
        quotes.add(new Quote("I say what I want to say and do what I want to do. There's no in between. People will either love you for it or hate you for it.", false, true, english, eminem));
        quotes.add(new Quote("Somewhere deep down there's a decent man in me, he just can't be found.", false, true, english, eminem));
        quotes.add(new Quote("Trust is hard to come by. That's why my circle is small and tight. I'm kind of funny about making new friends.", false, true, english, eminem));
        quotes.add(new Quote("The truth is you don't know what is going to happen tomorrow. Life is a crazy ride, and nothing is guaranteed.", false, true, english, eminem));

        Author ermaBombeck = getAuthor("Erma Bombeck");
        quotes.add(new Quote("Don't confuse fame with success. Madonna is one; Helen Keller is the other.", false, true, english, ermaBombeck));
        quotes.add(new Quote("For years my wedding ring has done its job. It has led me not into temptation. It has reminded my husband numerous times at parties that it's time to go home. It has been a source of relief to a dinner companion. It has been a status symbol in the maternity ward.", false, true, english, ermaBombeck));
        quotes.add(new Quote("When your mother asks, 'Do you want a piece of advice?' it is a mere formality. It doesn't matter if you answer yes or no. You're going to get it anyway.", false, true, english, ermaBombeck));
        quotes.add(new Quote("When I stand before God at the end of my life, I would hope that I would not have a single bit of talent left, and could say, 'I used everything you gave me'.", false, true, english, ermaBombeck));
        quotes.add(new Quote("Never have more children than you have car windows.", false, true, english, ermaBombeck));
        quotes.add(new Quote("Dreams have only one owner at a time. That's why dreamers are lonely.", false, true, english, ermaBombeck));
        quotes.add(new Quote("Thanksgiving dinners take eighteen hours to prepare. They are consumed in twelve minutes. Half-times take twelve minutes. This is not coincidence.", false, true, english, ermaBombeck));
        quotes.add(new Quote("Never go to a doctor whose office plants have died.", false, true, english, ermaBombeck));
        quotes.add(new Quote("There is a thin line that separates laughter and pain, comedy and tragedy, humor and hurt.", false, true, english, ermaBombeck));
        quotes.add(new Quote("A grandmother pretends she doesn't know who you are on Halloween.", false, true, english, ermaBombeck));

        Author ernestHemingway = getAuthor("Ernest Hemingway");
        quotes.add(new Quote("There is no friend as loyal as a book.", false, true, english, ernestHemingway));
        quotes.add(new Quote("Courage is grace under pressure.", false, true, english, ernestHemingway));
        quotes.add(new Quote("There is nothing noble in being superior to your fellow men. True nobility lies in being superior to your former self.", false, true, english, ernestHemingway));
        quotes.add(new Quote("But man is not made for defeat. A man can be destroyed but not defeated.", false, true, english, ernestHemingway));
        quotes.add(new Quote("Never go on trips with anyone you do not love.", false, true, english, ernestHemingway));
        quotes.add(new Quote("I like to listen. I have learned a great deal from listening carefully. Most people never listen.", false, true, english, ernestHemingway));
        quotes.add(new Quote("An intelligent man is sometimes forced to be drunk to spend time with his fools.", false, true, english, ernestHemingway));
        quotes.add(new Quote("I love sleep. My life has the tendency to fall apart when I'm awake, you know?", false, true, english, ernestHemingway));
        quotes.add(new Quote("The world breaks everyone, and afterward, some are strong at the broken places.", false, true, english, ernestHemingway));
        quotes.add(new Quote("The best way to find out if you can trust somebody is to trust them.", false, true, english, ernestHemingway));

        Author edgarAllanPoe = getAuthor("Edgar Allan Poe");
        quotes.add(new Quote("Deep into that darkness peering, long I stood there, wondering, fearing, doubting, dreaming dreams no mortal ever dared to dream before.", false, true, english, edgarAllanPoe));
        quotes.add(new Quote("I would define, in brief, the poetry of words as the rhythmical creation of Beauty.", false, true, english, edgarAllanPoe));
        quotes.add(new Quote("Words have no power to impress the mind without the exquisite horror of their reality.", false, true, english, edgarAllanPoe));
        quotes.add(new Quote("All that we see or seem is but a dream within a dream.", false, true, english, edgarAllanPoe));
        quotes.add(new Quote("They who dream by day are cognizant of many things which escape those who dream only by night.", false, true, english, edgarAllanPoe));
        quotes.add(new Quote("I became insane, with long intervals of horrible sanity.", false, true, english, edgarAllanPoe));
        quotes.add(new Quote("The boundaries which divide Life from Death are at best shadowy and vague. Who shall say where the one ends, and where the other begins?", false, true, english, edgarAllanPoe));
        quotes.add(new Quote("We loved with a love that was more than love.", false, true, english, edgarAllanPoe));
        quotes.add(new Quote("Once upon a midnight dreary, while I pondered weak and weary.", false, true, english, edgarAllanPoe));
        quotes.add(new Quote("Science has not yet taught us if madness is or is not the sublimity of the intelligence.", false, true, english, edgarAllanPoe));

        Author francisofAssisi = getAuthor("Francis of Assisi");
        quotes.add(new Quote("Start by doing what's necessary; then do what's possible; and suddenly you are doing the impossible.", false, true, english, francisofAssisi));
        quotes.add(new Quote("Lord, make me an instrument of thy peace. Where there is hatred, let me sow love.", false, true, english, francisofAssisi));
        quotes.add(new Quote("A single sunbeam is enough to drive away many shadows.", false, true, english, francisofAssisi));
        quotes.add(new Quote("Where there is charity and wisdom, there is neither fear nor ignorance.", false, true, english, francisofAssisi));
        quotes.add(new Quote("It is no use walking anywhere to preach unless our walking is our preaching.", false, true, english, francisofAssisi));
        quotes.add(new Quote("While you are proclaiming peace with your lips, be careful to have it even more fully in your heart.", false, true, english, francisofAssisi));
        quotes.add(new Quote("If God can work through me, he can work through anyone. Francis of Assisi", false, true, english, francisofAssisi));
        quotes.add(new Quote("Men lose all the material things they leave behind them in this world, but they carry with them the reward of their charity and the alms they give. For these, they will receive from the Lord the reward and recompense they deserve.", false, true, english, francisofAssisi));
        quotes.add(new Quote("For it is in giving that we receive.", false, true, english, francisofAssisi));
        quotes.add(new Quote("Lord, grant that I might not so much seek to be loved as to love.", false, true, english, francisofAssisi));

        ArrayList<Quote> mQuotes = new ArrayList<>(SugarRecord.saveInTx(quotes));
//        DataQuote dataQuote = new DataQuote(mQuotes);
//        SessionManager.setStringSetting(getGlobalContext(), SESSION_DATA_QUOTES, dataQuote.toString());
//
//        ArrayList<Quote> tempQuotes=DataQuote.convertFromStringToObject(SessionManager.getStringSetting(getGlobalContext(), SESSION_DATA_QUOTES),DataQuote.class).getQuotes();
//        for(int i=0;i<){}

        return mQuotes;
    }

    public static ArrayList<Quote> getAllQuoteAdvertises() {
        ArrayList<Quote> advertises = new ArrayList<Quote>();
        advertises.add(new Quote("Add 1", false, false, null, null));
        advertises.add(new Quote("Add 2", false, false, null, null));
        advertises.add(new Quote("Add 3", false, false, null, null));
        advertises.add(new Quote("Add 4", false, false, null, null));
        advertises.add(new Quote("Add 5", false, false, null, null));
        return advertises;
    }

    public static ArrayList<Author> getAllAuthorAdvertises() {
        ArrayList<Author> advertises = new ArrayList<Author>();
        advertises.add(new Author("Add 1", "", "", "", "", -1, false));
        advertises.add(new Author("Add 2", "", "", "", "", -1, false));
        advertises.add(new Author("Add 3", "", "", "", "", -1, false));
        advertises.add(new Author("Add 4", "", "", "", "", -1, false));
        advertises.add(new Author("Add 5", "", "", "", "", -1, false));
        return advertises;
    }

    public static ArrayList<Quote> getAllQuotes(Author author, Language language) {
        List<Quote> quotes = Select.from(Quote.class)
                .where(Condition.prop("author").eq(author.getId()), Condition.prop("language").eq(language.getId()))
                .list();
//        List<Quote> quotes = Quote.find(Quote.class, "author = ?", new String[]{author1.getId()+""});
        return new ArrayList<Quote>(quotes);
    }

    public static ArrayList<MappedQuote> getAllMappedQuoteAdvertises() {
        ArrayList<MappedQuote> advertises = new ArrayList<MappedQuote>();
        advertises.add(new MappedQuote(new Author("Add 1", "", "", "", "", -1, false), new Language("English"), new ArrayList<Quote>(), false));
        advertises.add(new MappedQuote(new Author("Add 2", "", "", "", "", -1, false), new Language("English"), new ArrayList<Quote>(), false));
        advertises.add(new MappedQuote(new Author("Add 3", "", "", "", "", -1, false), new Language("English"), new ArrayList<Quote>(), false));
        advertises.add(new MappedQuote(new Author("Add 4", "", "", "", "", -1, false), new Language("English"), new ArrayList<Quote>(), false));
        advertises.add(new MappedQuote(new Author("Add 5", "", "", "", "", -1, false), new Language("English"), new ArrayList<Quote>(), false));
        return advertises;
    }

    public static ArrayList<Author> getAllAuthorWithAdvertise(ArrayList<Author> authorArrayList) {
        ArrayList<Author> arrAll = new ArrayList<Author>();
        ArrayList<Author> tempAll = authorArrayList;
        if (tempAll.size() > 0) {
            if (SessionManager.getBooleanSetting(getGlobalContext(),SESSION_FREE_APP,false)) {
                ArrayList<Author> arrAd = DataHandler.getAllAuthorAdvertises();
                int index = 0;
                for (Author author : tempAll) {
                    arrAll.add(author);
                    Double randomNumber = Math.random();
                    Log.d("randomnumber: ", randomNumber + "");
//            if (Math.random() < 0.2) {
                    if (randomNumber > 0.5) {
                        Author advertise = new Author(author.getAuthorName().substring(0, 3), "", "", "", "", -1, false);
//                        arrAll.add(arrAd.get(index % arrAd.size()));
                        arrAll.add(advertise);
                        index++;
                    }
                }
            } else {
                arrAll = tempAll;
            }
        }
        return arrAll;
    }

    public static ArrayList<Quote> getAllQuotesWithAdvertise(Author author, Language language) {
        ArrayList<Quote> arrAll = new ArrayList<Quote>();
        ArrayList<Quote> tempAll = getAllQuotes(author, language);
        if (tempAll.size() > 0) {
            if (SessionManager.getBooleanSetting(getGlobalContext(),SESSION_FREE_APP,false)) {
                ArrayList<Quote> arrAd = DataHandler.getAllQuoteAdvertises();
                int index = 0;
                for (Quote quote : tempAll) {
                    arrAll.add(quote);
                    Double randomNumber = Math.random();
                    Log.d("randomnumber: ", randomNumber + "");
//            if (Math.random() < 0.2) {
                    if (randomNumber > 0.5) {
                        arrAll.add(arrAd.get(index % arrAd.size()));
                        index++;
                    }
                }
            } else {
                arrAll = tempAll;
            }
        }
        return arrAll;
    }

    public static ArrayList<MappedQuote> getAllMappedQuotes() {

        ArrayList<MappedQuote> mData = new ArrayList<MappedQuote>();
        ArrayList<Author> mAuthors = getAllAuthors();
        Language language = getLanguage(SessionManager.getStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE));

        for (int i = 0; i < mAuthors.size(); i++) {
            Author author = mAuthors.get(i);
            ArrayList<Quote> mQuotes = getAllQuotes(author, language);
            MappedQuote mappedQuote = new MappedQuote(author, language, mQuotes, true);
            mData.add(mappedQuote);
        }

        return mData;
    }

    public static ArrayList<MappedQuote> getAllMappedQuotesWithAdvertise(ArrayList<MappedQuote> mappedQuoteArrayList) {

        ArrayList<MappedQuote> mData = new ArrayList<MappedQuote>();
        ArrayList<MappedQuote> mTempData = mappedQuoteArrayList;
        if (mTempData.size() > 0) {
            if (SessionManager.getBooleanSetting(getGlobalContext(),SESSION_FREE_APP,false)) {
                ArrayList<MappedQuote> arrAd = DataHandler.getAllMappedQuoteAdvertises();
                int index = 0;
                Language language = getLanguage(SessionManager.getStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE));
                for (MappedQuote author : mTempData) {
                    mData.add(author);
                    Double randomNumber = Math.random();
                    Log.d("randomnumber: ", randomNumber + "");
//            if (Math.random() < 0.2) {
                    if (randomNumber > 0.5) {
                        MappedQuote advertise = new MappedQuote(new Author(author.getAuthor().getAuthorName().substring(0, 2) + "z", "", "", "", "", -1, false), language, new ArrayList<Quote>(), false);
//                        mData.add(arrAd.get(index % arrAd.size()));
                        mData.add(advertise);
                        index++;
                    }
                }
            } else {
                mData = mTempData;
            }
        }
        return mData;
    }

    public static ArrayList<MappedQuote> getAllData() {
        ArrayList<MappedQuote> mappedQuotes = new ArrayList<MappedQuote>();
        ArrayList<MappedQuote> tempMappedQuotes = DataHandler.getAllMappedQuotesWithAdvertise(DataHandler.getAllMappedQuotes());
        Language language = getLanguage(SessionManager.getStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE));

        for (int i = 0; i < tempMappedQuotes.size(); i++) {
            MappedQuote mappedQuote = tempMappedQuotes.get(i);
            Author author = tempMappedQuotes.get(i).getAuthor();
            if (mappedQuote.isMappedQuote()) {
                mappedQuote.setQuotes(DataHandler.getAllQuotesWithAdvertise(author, language));
            }
            mappedQuotes.add(mappedQuote);
        }

        return mappedQuotes;
    }

    public static ArrayList<Quote> getAllQuotes(Author author, ArrayList<MappedQuote> mappedQuoteArrayList) {
        if (mappedQuoteArrayList.size() > 0) {
            for (int i = 0; i < mappedQuoteArrayList.size(); i++) {
                if (mappedQuoteArrayList.get(i).getAuthor().getAuthorName().equalsIgnoreCase(author.getAuthorName())) {
                    return mappedQuoteArrayList.get(i).getQuotes();
                }
            }
        }
        return null;
    }

    public static ArrayList<GlazyCard> getAllGlazyCards(ArrayList<MappedQuote> quoteArrayList) {

        ArrayList<GlazyCard> glazyCards = new ArrayList<GlazyCard>();
        GlazyCard glazyCard;
        MappedQuote quote;
        GlazyImageView.ImageCutType lastTransitionType = GlazyImageView.ImageCutType.LINE_POSITIVE;
        for (int i = 0; i < quoteArrayList.size(); i++) {

            quote = quoteArrayList.get(i);
            glazyCard = new GlazyCard()
                    .withTitle(quote.isMappedQuote() ? quote.getAuthor().getAuthorName() : "Advertise")
                    .withSubTitle(quote.isMappedQuote() ? quote.getAuthor().getOccupation() : "")
                    .withOccupation(quote.isMappedQuote() ? quote.getAuthor().getOccupation() : "")
                    .withNationality(quote.isMappedQuote() ? quote.getAuthor().getNationality() : "")
                    .withBirthDate(quote.isMappedQuote() ? quote.getAuthor().getBirthDate() : "")
                    .withDeathDate(quote.isMappedQuote() ? quote.getAuthor().getDeathDate() : "")
                    .withDescription(quote.isMappedQuote() ? (quote.getQuotes().size() > 0 ? quote.getQuotes().get(0).getQuoteDescription() : "") : "")
                    .withImageRes((quote.getAuthor().getProfileImage() != -1) ? quote.getAuthor().getProfileImage() : R.drawable.avatar_male)
                    .withImageCutType(lastTransitionType)
                    .withImageCutHeightDP(50);
            glazyCards.add(glazyCard);

            if (lastTransitionType == GlazyImageView.ImageCutType.LINE_POSITIVE) {
                lastTransitionType = GlazyImageView.ImageCutType.ARC;
            } else if (lastTransitionType == GlazyImageView.ImageCutType.ARC) {
                lastTransitionType = GlazyImageView.ImageCutType.WAVE;
            } else if (lastTransitionType == GlazyImageView.ImageCutType.WAVE) {
                lastTransitionType = GlazyImageView.ImageCutType.LINE_POSITIVE;
            }
        }

        return glazyCards;
    }

    /*********************************
     * Data for all favourite quotes *
     *********************************/
    public static ArrayList<MappedQuote> mAllMappedFavouriteQuotes = new ArrayList<MappedQuote>();

    public static MappedQuote isFavouriteItemFound(Author author) {
        if (mAllMappedFavouriteQuotes.size() > 0) {
            for (int i = 0; i < mAllMappedFavouriteQuotes.size(); i++) {
                if (mAllMappedFavouriteQuotes.get(i).getAuthor().getAuthorName().equalsIgnoreCase(author.getAuthorName())) {
                    return mAllMappedFavouriteQuotes.get(i);
                }
            }
        }
        return null;
    }

    public static ArrayList<MappedQuote> getAllFavouriteData() {
        ArrayList<MappedQuote> mappedQuotes = new ArrayList<MappedQuote>();
        ArrayList<MappedQuote> tempMappedQuotes = DataHandler.getAllMappedFavouriteQuotesWithAdvertise(DataHandler.getAllMappedFavouriteQuotes());

        Language language = getLanguage(SessionManager.getStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE));
        for (int i = 0; i < tempMappedQuotes.size(); i++) {
            MappedQuote mappedQuote = tempMappedQuotes.get(i);
            Author author = tempMappedQuotes.get(i).getAuthor();
            if (mappedQuote.isMappedQuote()) {
                mappedQuote.setQuotes(DataHandler.getAllFavouriteQuotesWithAdvertise(author, language));
            }
            mappedQuotes.add(mappedQuote);
        }

        return mappedQuotes;
    }

    public static ArrayList<Quote> getAllFavouriteQuotes(Author author, Language language) {
        ArrayList<Quote> quotes = getAllQuotes(author, language);
        ArrayList<Quote> mTempQuote = new ArrayList<Quote>();
        for (Quote quote : quotes) {
            if (quote.isFavourite()) {
                mTempQuote.add(quote);
            }
        }
        return mTempQuote;
    }

    public static ArrayList<MappedQuote> getAllMappedFavouriteQuotes() {

        ArrayList<MappedQuote> mData = new ArrayList<MappedQuote>();
        ArrayList<Author> mAuthors = getAllAuthors();

        Language language = getLanguage(SessionManager.getStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE));
        for (int i = 0; i < mAuthors.size(); i++) {
            Author author = mAuthors.get(i);
            ArrayList<Quote> mQuotes = getAllFavouriteQuotes(author, language);
            if (mQuotes.size() > 0) {
                MappedQuote mappedQuote = new MappedQuote(author, language, mQuotes, true);
                mData.add(mappedQuote);
            }
        }

        return mData;
    }

    public static ArrayList<MappedQuote> getAllMappedFavouriteQuotesWithAdvertise(ArrayList<MappedQuote> mappedQuoteArrayList) {

        ArrayList<MappedQuote> mData = new ArrayList<MappedQuote>();
        ArrayList<MappedQuote> mTempData = mappedQuoteArrayList;
        if (mTempData.size() > 0) {
            if (SessionManager.getBooleanSetting(getGlobalContext(),SESSION_FREE_APP,false)) {
                ArrayList<MappedQuote> arrAd = DataHandler.getAllMappedQuoteAdvertises();
                int index = 0;
                Language language = getLanguage(SessionManager.getStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE));

                for (MappedQuote author : mTempData) {
                    mData.add(author);
                    Double randomNumber = Math.random();
                    Log.d("randomnumber: ", randomNumber + "");
//            if (Math.random() < 0.2) {
                    if (randomNumber > 0.5) {
                        MappedQuote advertise = new MappedQuote(new Author(author.getAuthor().getAuthorName().substring(0, 2) + "z", "", "", "", "", -1, false), language, new ArrayList<Quote>(), false);
//                        mData.add(arrAd.get(index % arrAd.size()));
                        mData.add(advertise);
                        index++;
                    }
                }
            } else {
                mData = mTempData;
            }
        }
        return mData;
    }

    public static ArrayList<Quote> getAllFavouriteQuotesWithAdvertise(Author author, Language language) {
        ArrayList<Quote> arrAll = new ArrayList<Quote>();
        ArrayList<Quote> tempAll = getAllFavouriteQuotes(author, language);
        if (tempAll.size() > 0) {
            if (SessionManager.getBooleanSetting(getGlobalContext(),SESSION_FREE_APP,false)) {
                ArrayList<Quote> arrAd = DataHandler.getAllQuoteAdvertises();
                int index = 0;
                for (Quote quote : tempAll) {
                    arrAll.add(quote);
                    Double randomNumber = Math.random();
                    Log.d("randomnumber: ", randomNumber + "");
//            if (Math.random() < 0.2) {
                    if (randomNumber > 0.5) {
                        arrAll.add(arrAd.get(index % arrAd.size()));
                        index++;
                    }
                }
            } else {
                arrAll = tempAll;
            }
        }
        return arrAll;
    }

    public static Quote setFavouriteForFavouriteFragment(Quote quote, boolean isFavourite) {
        Quote mFoldableQuote = setFavouriteForAuthorFragment(quote, isFavourite);
        if ((mFoldableQuote != null) && (mFoldableQuote.isFavourite() == isFavourite)) {
            for (MappedQuote mappedQuote : mAllMappedFavouriteQuotes) {
                if (mappedQuote.getAuthor().getAuthorName().equalsIgnoreCase(quote.getAuthor().getAuthorName())) {
                    for (Quote mQuote : mappedQuote.getQuotes()) {
                        if (mQuote.getQuoteDescription().equalsIgnoreCase(quote.getQuoteDescription())) {

                            mQuote.setFavourite(isFavourite);
                        }
                    }
                }
            }
        }
        return quote;
    }
}