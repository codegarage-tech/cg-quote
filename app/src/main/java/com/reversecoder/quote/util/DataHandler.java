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
import com.reversecoder.quote.model.Tag;

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

    /*********************
     * Data for all tags *
     *********************/
    public static ArrayList<Tag> initTags() {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        tags.add(new Tag("Rashed"));
        tags.add(new Tag("Meem"));
        tags.add(new Tag("Mou"));
        tags.add(new Tag("Alif"));
        tags.add(new Tag("Rashed"));
        tags.add(new Tag("Meem"));
        tags.add(new Tag("Mou"));
        tags.add(new Tag("Alif"));

        ArrayList<Tag> mTags = new ArrayList<>(SugarRecord.saveInTx(tags));
        for (int i = 0; i < mTags.size(); i++) {
            Log.d(TAG, "Tag " + i + ": " + mTags.get(i).toString());
        }
        return mTags;
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
            SessionManager.setStringSetting(getGlobalContext(), SESSION_DATA_LANGUAGES, DataLanguage.convertFromObjectToString(dataLanguage));
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

        Author OSheaJacksonJr = new Author("O'Shea Jackson, Jr.", "February 24, 1991", "", "Actor", "American", AppUtils.getDrawableResourceId(R.drawable.o_shea_jackson_jr), true);
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

        Author QoriankaKilcher = new Author("Q'orianka Kilcher", "February 11, 1990", "", "Actress", "German", AppUtils.getDrawableResourceId(R.drawable.q_orianka_kilcher), true);
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
        SessionManager.setStringSetting(getGlobalContext(), SESSION_DATA_AUTHORS, DataAuthor.convertFromObjectToString(dataAuthor));

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

    public static Language getLanguage(List<Language> languages, String languageName) {
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

    public static Author getAuthor(List<Author> authors, String authorName) {
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getAuthorName().equalsIgnoreCase(authorName)) {
                return authors.get(i);
            }
        }
        return null;
    }

    public static ArrayList<Quote> initAllQuotes() {

        ArrayList<Quote> quotes = new ArrayList<Quote>();
        List<Author> authors = getAllAuthors();
        List<Language> languages = getAllLanguages();
        Language english = getLanguage(languages, SessionManager.getStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE));

//        Author APJAbdulKalam = getAuthor(authors, "A. P. J. Abdul Kalam");
        Author APJAbdulKalam = new Author("A. P. J. Abdul Kalam", "October 15, 1931", "July 27, 2015", "Statesman", "Indian", AppUtils.getDrawableResourceId(R.drawable.apj_abdul_kalam), true);
        APJAbdulKalam.setId(APJAbdulKalam.save());
//        quotes.add(new Quote("Teaching is a very noble profession that shapes the character, caliber, and future of an individual. If the people remember me as a good teacher, that will be the biggest honour for me.", false, true, english, APJAbdulKalam));
//        quotes.add(new Quote("We should not give up and we should not allow the problem to defeat us.", false, true, english, APJAbdulKalam));
//        quotes.add(new Quote("Let us sacrifice our today so that our children can have a better tomorrow.", false, true, english, APJAbdulKalam));
//        quotes.add(new Quote("If you want to shine like a sun, first burn like a sun.", false, true, english, APJAbdulKalam));
//        quotes.add(new Quote("If a country is to be corruption free and become a nation of beautiful minds, I strongly feel there are three key societal members who can make a difference. They are the father, the mother and the teacher.", false, true, english, APJAbdulKalam));
//        quotes.add(new Quote("To succeed in your mission, you must have single-minded devotion to your goal.", false, true, english, APJAbdulKalam));
//        quotes.add(new Quote("Look at the sky. We are not alone. The whole universe is friendly to us and conspires only to give the best to those who dream and work.", false, true, english, APJAbdulKalam));
//        quotes.add(new Quote("Never stop fighting until you arrive at your destined place - that is, the unique you. Have an aim in life, continuously acquire knowledge, work hard, and have perseverance to realise the great life.", false, true, english, APJAbdulKalam));
//        quotes.add(new Quote("Science is a beautiful gift to humanity; we should not distort it.", false, true, english, APJAbdulKalam));
//        quotes.add(new Quote("The bird is powered by its own life and by its motivation.", false, true, english, APJAbdulKalam));

//        Author albertCamus = getAuthor(authors, "Albert Camus");
        Author albertCamus = new Author("Albert Camus", "November 7, 1913", "January 4, 1960", "Philosopher", "French", AppUtils.getDrawableResourceId(R.drawable.albert_camus), true);
        albertCamus.setId(albertCamus.save());
//        quotes.add(new Quote("Don't walk behind me; I may not lead. Don't walk in front of me; I may not follow. Just walk Autumn is a second spring when every leaf is a flower.", false, true, english, albertCamus));
//        quotes.add(new Quote("Don't walk behind me; I may not lead. Don't walk in front of me; I may not follow. Just walk beside me and be my friend.", false, true, english, albertCamus));
//        quotes.add(new Quote("Blessed are the hearts that can bend; they shall never be broken.", false, true, english, albertCamus));
//        quotes.add(new Quote("The only way to deal with an unfree world is to become so absolutely free that your very existence is an act of rebellion.", false, true, english, albertCamus));
//        quotes.add(new Quote("In the depth of winter I finally learned that there was in me an invincible summer.", false, true, english, albertCamus));
//        quotes.add(new Quote("A man without ethics is a wild beast loosed upon this world.", false, true, english, albertCamus));
//        quotes.add(new Quote("You will never be happy if you continue to search for what happiness consists of. You will never live if you are looking for the meaning of life.", false, true, english, albertCamus));
//        quotes.add(new Quote("Integrity has no need of rules.", false, true, english, albertCamus));
//        quotes.add(new Quote("Real generosity toward the future lies in giving all to the present.", false, true, english, albertCamus));
//        quotes.add(new Quote("The evil that is in the world almost always comes of ignorance, and good intentions may do as much harm as malevolence if they lack understanding.", false, true, english, albertCamus));

//        Author aristotle = getAuthor(authors, "Aristotle");
        Author aristotle = new Author("Aristotle", "384 BC", "322 BC", "Philosopher", "Greek", AppUtils.getDrawableResourceId(R.drawable.aristotle), true);
        aristotle.setId(aristotle.save());
//        quotes.add(new Quote("It is during our darkest moments that we must focus to see the light.", false, true, english, aristotle));
//        quotes.add(new Quote("Quality is not an act, it is a habit.", false, true, english, aristotle));
//        quotes.add(new Quote("The roots of education are bitter, but the fruit is sweet.", false, true, english, aristotle));
//        quotes.add(new Quote("It is the mark of an educated mind to be able to entertain a thought without accepting it.", false, true, english, aristotle));
//        quotes.add(new Quote("Excellence is an art won by training and habituation. We do not act rightly because we have virtue or excellence, but we rather have those because we have acted rightly. We are what we repeatedly do. Excellence, then, is not an act but a habit.", false, true, english, aristotle));
//        quotes.add(new Quote("Pleasure in the job puts perfection in the work.", false, true, english, aristotle));
//        quotes.add(new Quote("There is no great genius without some touch of madness.", false, true, english, aristotle));
//        quotes.add(new Quote("Love is composed of a single soul inhabiting two bodies.", false, true, english, aristotle));
//        quotes.add(new Quote("The one exclusive sign of thorough knowledge is the power of teaching.", false, true, english, aristotle));
//        quotes.add(new Quote("The worst form of inequality is to try to make unequal things equal.", false, true, english, aristotle));

//        Author audreyHepburn = getAuthor(authors, "Audrey Hepburn");
//        quotes.add(new Quote("Nothing is impossible, the word itself says 'I'm possible'!", false, true, english, audreyHepburn));
//        quotes.add(new Quote("The beauty of a woman must be seen from in her eyes, because that is the doorway to her heart, the place where love resides.", false, true, english, audreyHepburn));
//        quotes.add(new Quote("For beautiful eyes, look for the good in others; for beautiful lips, speak only words of kindness; and for poise, walk with the knowledge that you are never alone.", false, true, english, audreyHepburn));
//        quotes.add(new Quote("The most important thing is to enjoy your life - to be happy - it's all that matters.", false, true, english, audreyHepburn));
//        quotes.add(new Quote("The beauty of a woman is not in a facial mode but the true beauty in a woman is reflected in her soul. It is the caring that she lovingly gives the passion that she shows. The beauty of a woman grows with the passing years.", false, true, english, audreyHepburn));
//        quotes.add(new Quote("I believe in pink. I believe that laughing is the best calorie burner. I believe in kissing, kissing a lot. I believe in being strong when everything seems to be going wrong. I believe that happy girls are the prettiest girls. I believe that tomorrow is another day and I believe in miracles.", false, true, english, audreyHepburn));
//        quotes.add(new Quote("The best thing to hold onto in life is each other.", false, true, english, audreyHepburn));
//        quotes.add(new Quote("The beauty of a woman is not in the clothes she wears, the figure that she carries or the way she combs her hair.", false, true, english, audreyHepburn));
//        quotes.add(new Quote("I believe in manicures. I believe in overdressing. I believe in primping at leisure and wearing lipstick. I believe in pink. I believe happy girls are the prettiest girls. I believe that tomorrow is another day, and... I believe in miracles.", false, true, english, audreyHepburn));
//        quotes.add(new Quote("Let's face it, a nice creamy chocolate cake does a lot for a lot of people; it does for me.", false, true, english, audreyHepburn));
//
//        Author abrahamLincoln = getAuthor(authors, "Abraham Lincoln");
//        quotes.add(new Quote("If this is coffee, please bring me some tea; but if this is tea, please bring me some coffee.", false, true, english, abrahamLincoln));
//        quotes.add(new Quote("Character is like a tree and reputation like a shadow. The shadow is what we think of it; the tree is the real thing.", false, true, english, abrahamLincoln));
//        quotes.add(new Quote("I am a slow walker, but I never walk back.", false, true, english, abrahamLincoln));
//        quotes.add(new Quote("My dream is of a place and a time where America will once again be seen as the last best hope of earth.", false, true, english, abrahamLincoln));
//        quotes.add(new Quote("Be sure you put your feet in the right place, then stand firm.", false, true, english, abrahamLincoln));
//        quotes.add(new Quote("You cannot escape the responsibility of tomorrow by evading it today.", false, true, english, abrahamLincoln));
//        quotes.add(new Quote("No man has a good enough memory to be a successful liar.", false, true, english, abrahamLincoln));
//        quotes.add(new Quote("All that I am, or hope to be, I owe to my angel mother.", false, true, english, abrahamLincoln));
//        quotes.add(new Quote("Nearly all men can stand adversity, but if you want to test a man's character, give him power.", false, true, english, abrahamLincoln));
//        quotes.add(new Quote("America will never be destroyed from the outside. If we falter and lose our freedoms, it will be because we destroyed ourselves.", false, true, english, abrahamLincoln));
//
//        Author aldousHuxley = getAuthor(authors, "Aldous Huxley");
//        quotes.add(new Quote("There is only one corner of the universe you can be certain of improving, and that's your own self.", false, true, english, aldousHuxley));
//        quotes.add(new Quote("There are things known and there are things unknown, and in between are the doors of perception.", false, true, english, aldousHuxley));
//        quotes.add(new Quote("The secret of genius is to carry the spirit of the child into old age, which means never losing your enthusiasm.", false, true, english, aldousHuxley));
//        quotes.add(new Quote("After silence, that which comes nearest to expressing the inexpressible is music.", false, true, english, aldousHuxley));
//        quotes.add(new Quote("To travel is to discover that everyone is wrong about other countries.", false, true, english, aldousHuxley));
//        quotes.add(new Quote("Experience is not what happens to you; it's what you do with what happens to you.", false, true, english, aldousHuxley));
//        quotes.add(new Quote("Facts do not cease to exist because they are ignored.", false, true, english, aldousHuxley));
//        quotes.add(new Quote("The more powerful and original a mind, the more it will incline towards the religion of solitude.", false, true, english, aldousHuxley));
//        quotes.add(new Quote("All gods are homemade, and it is we who pull their strings, and so, give them the power to pull ours.", false, true, english, aldousHuxley));
//        quotes.add(new Quote("A democracy which makes or even effectively prepares for modern, scientific war must necessarily cease to be democratic. No country can be really well prepared for modern war unless it is governed by a tyrant, at the head of a highly trained and perfectly obedient bureaucracy.", false, true, english, aldousHuxley));
//
//        Author AalexanderHamilton = getAuthor(authors, "Alexander Hamilton");
//        quotes.add(new Quote("Learn to think continentally.", false, true, english, AalexanderHamilton));
//        quotes.add(new Quote("Real liberty is neither found in despotism or the extremes of democracy, but in moderate governments.", false, true, english, AalexanderHamilton));
//        quotes.add(new Quote("In framing a government which is to be administered by men over men, the great difficulty lies in this: you must first enable the government to control the governed; and in the next place, oblige it to control itself.", false, true, english, AalexanderHamilton));
//        quotes.add(new Quote("A promise must never be broken.", false, true, english, AalexanderHamilton));
//        quotes.add(new Quote("It's not tyranny we desire; it's a just, limited, federal government.", false, true, english, AalexanderHamilton));
//        quotes.add(new Quote("I never expect to see a perfect work from an imperfect man.", false, true, english, AalexanderHamilton));
//        quotes.add(new Quote("I think the first duty of society is justice.", false, true, english, AalexanderHamilton));
//        quotes.add(new Quote("A well adjusted person is one who makes the same mistake twice without getting nervous.", false, true, english, AalexanderHamilton));
//        quotes.add(new Quote("Unless your government is respectable, foreigners will invade your rights; and to maintain tranquillity, it must be respectable - even to observe neutrality, you must have a strong government.", false, true, english, AalexanderHamilton));
//        quotes.add(new Quote("There is a certain enthusiasm in liberty, that makes human nature rise above itself, in acts of bravery and heroism.", false, true, english, AalexanderHamilton));
//
//        Author alexanderPope = getAuthor(authors, "Alexander Pope");
//        quotes.add(new Quote("The greatest magnifying glasses in the world are a man's own eyes when they look upon his own person.", false, true, english, alexanderPope));
//        quotes.add(new Quote("Fools rush in where angels fear to tread.", false, true, english, alexanderPope));
//        quotes.add(new Quote("All nature is but art unknown to thee.", false, true, english, alexanderPope));
//        quotes.add(new Quote("No one should be ashamed to admit they are wrong, which is but saying, in other words, that they are wiser today than they were yesterday.", false, true, english, alexanderPope));
//        quotes.add(new Quote("No woman ever hates a man for being in love with her, but many a woman hate a man for being a friend to her.", false, true, english, alexanderPope));
//        quotes.add(new Quote("Teach me to feel another's woe, to hide the fault I see, that mercy I to others show, that mercy show to me.", false, true, english, alexanderPope));
//        quotes.add(new Quote("Charms strike the sight, but merit wins the soul.", false, true, english, alexanderPope));
//        quotes.add(new Quote("On life's vast ocean diversely we sail. Reasons the card, but passion the gale.", false, true, english, alexanderPope));
//        quotes.add(new Quote("To be angry is to revenge the faults of others on ourselves.", false, true, english, alexanderPope));
//        quotes.add(new Quote("To err is human; to forgive, divine.", false, true, english, alexanderPope));
//
//        Author arnoldSchwarzenegger = getAuthor(authors, "Arnold Schwarzenegger");
//        quotes.add(new Quote("The worst thing I can be is the same as everybody else. I hate that.", false, true, english, arnoldSchwarzenegger));
//        quotes.add(new Quote("It's simple, if it jiggles, it's fat.", false, true, english, arnoldSchwarzenegger));
//        quotes.add(new Quote("Bodybuilding is much like any other sport. To be successful, you must dedicate yourself 100% to your training, diet and mental approach.", false, true, english, arnoldSchwarzenegger));
//        quotes.add(new Quote("Help others and give something back. I guarantee you will discover that while public service improves the lives and the world around you, its greatest reward is the enrichment and new meaning it will bring your own life.", false, true, english, arnoldSchwarzenegger));
//        quotes.add(new Quote("The mind is the limit. As long as the mind can envision the fact that you can do something, you can do it, as long as you really believe 100 percent.", false, true, english, arnoldSchwarzenegger));
//        quotes.add(new Quote("The future is green energy, sustainability, renewable energy.", false, true, english, arnoldSchwarzenegger));
//        quotes.add(new Quote("Milk is for babies. When you grow up you have to drink beer.", false, true, english, arnoldSchwarzenegger));
//        quotes.add(new Quote("For me life is continuously being hungry. The meaning of life is not simply to exist, to survive, but to move ahead, to go up, to achieve, to conquer.", false, true, english, arnoldSchwarzenegger));
//        quotes.add(new Quote("Just remember, you can't climb the ladder of success with your hands in your pockets.", false, true, english, arnoldSchwarzenegger));
//        quotes.add(new Quote("Strength does not come from winning. Your struggles develop your strengths. When you go through hardships and decide not to surrender, that is strength.", false, true, english, arnoldSchwarzenegger));
//
//        Author barackObama = getAuthor(authors, "Barack Obama");
//        quotes.add(new Quote("Change will not come if we wait for some other person or some other time. We are the ones we've been waiting for. We are the change that we seek.", false, true, english, barackObama));
//        quotes.add(new Quote("I see Americans of every party, every background, every faith who believe that we are stronger together: black, white, Latino, Asian, Native American; young, old; gay, straight; men, women, folks with disabilities, all pledging allegiance under the same proud flag to this big, bold country that we love. That's what I see. That's the America I know!", false, true, english, barackObama));
//        quotes.add(new Quote("We need to reject any politics that targets people because of race or religion. This isn't a matter of political correctness. It's a matter of understanding what makes us strong. The world respects us not just for our arsenal; it respects us for our diversity and our openness and the way we respect every faith.", false, true, english, barackObama));
//        quotes.add(new Quote("Let us remember we are all part of one American family. We are united in common values, and that includes belief in equality under the law, basic respect for public order, and the right of peaceful protest.", false, true, english, barackObama));
//        quotes.add(new Quote("If you're walking down the right path and you're willing to keep walking, eventually you'll make progress.", false, true, english, barackObama));
//        quotes.add(new Quote("If the people cannot trust their government to do the job for which it exists - to protect them and to promote their common welfare - all else is lost.", false, true, english, barackObama));
//        quotes.add(new Quote("The future rewards those who press on. I don't have time to feel sorry for myself. I don't have time to complain. I'm going to press on.", false, true, english, barackObama));
//        quotes.add(new Quote("Money is not the only answer, but it makes a difference.", false, true, english, barackObama));
//        quotes.add(new Quote("America isn't Congress. America isn't Washington. America is the striving immigrant who starts a business, or the mom who works two low-wage jobs to give her kid a better life. America is the union leader and the CEO who put aside their differences to make the economy stronger.", false, true, english, barackObama));
//        quotes.add(new Quote("After a century of striving, after a year of debate, after a historic vote, health care reform is no longer an unmet promise. It is the law of the land.", false, true, english, barackObama));
//
//        Author benShapiro = getAuthor(authors, "Ben Shapiro");
//        quotes.add(new Quote("During the Great Depression, levels of crime actually dropped. During the 1920s, when life was free and easy, so was crime. During the 1930s, when the entire American economy fell into a government-owned alligator moat, crime was nearly non-existent. During the 1950s and 1960s, when the economy was excellent, crime rose again.", false, true, english, benShapiro));
//        quotes.add(new Quote("Socialism violates at least three of the Ten Commandments: It turns government into God, it legalizes thievery and it elevates covetousness. Discussions of income inequality, after all, aren't about prosperity but about petty spite. Why should you care how much money I make, so long as you are happy?", false, true, english, benShapiro));
//        quotes.add(new Quote("Distrust of government isn't baseless cynicism. It's realism.", false, true, english, benShapiro));
//        quotes.add(new Quote("Matt Damon's anti-fracking diatribe was funded by the royal family of the United Arab Emirates.", false, true, english, benShapiro));
//        quotes.add(new Quote("Capitalism requires individual responsibility and accountability. People are seen as atomized units in a capitalist system - they are either useful, or they are not. They are not seen racially or ethnically or religiously. They consume and they produce, and those are their only relevant characteristics.", false, true, english, benShapiro));
//        quotes.add(new Quote("The story of Detroit's bankruptcy was simple enough: Allow capitalism to grow the city, campaign against income inequality, tax the job creators until they flee, increase government spending in order to boost employment, promise generous pension plans to keep people voting for failure. Rinse, wash and repeat.", false, true, english, benShapiro));
//        quotes.add(new Quote("Socialism has no moral justification whatsoever; poor people are not morally superior to rich people, nor are they owed anything by rich people simply because of their lack of success. Charity is not a socialist concept - it is a religious one, an acknowledgment of God's sovereignty over property, a sovereignty the Left utterly rejects.", false, true, english, benShapiro));
//        quotes.add(new Quote("Every so often, we all gaze into the abyss. It's a depressing fact of life that eventually the clock expires; eventually the sand in the hourglass runs out. It's the leaving behind of everything that matters to us that hurts the most.", false, true, english, benShapiro));
//        quotes.add(new Quote("Socialism states that you owe me something simply because I exist. Capitalism, by contrast, results in a sort of reality-forced altruism: I may not want to help you, I may dislike you, but if I don't give you a product or service you want, I will starve. Voluntary exchange is more moral than forced redistribution.", false, true, english, benShapiro));
//        quotes.add(new Quote("Freedom of speech and thought matters, especially when it is speech and thought with which we disagree. The moment the majority decides to destroy people for engaging in thought it dislikes, thought crime becomes a reality.", false, true, english, benShapiro));
//
//        Author benjaminDisraeli = getAuthor(authors, "Benjamin Disraeli");
//        quotes.add(new Quote("A great city, whose image dwells in the memory of man, is the type of some great idea. Rome represents conquest; Faith hovers over the towers of Jerusalem; and Athens embodies the pre-eminent quality of the antique world, Art.", false, true, english, benjaminDisraeli));
//        quotes.add(new Quote("Power has only one duty - to secure the social welfare of the People.", false, true, english, benjaminDisraeli));
//        quotes.add(new Quote("Never apologize for showing feeling. When you do so, you apologize for the truth.", false, true, english, benjaminDisraeli));
//        quotes.add(new Quote("The secret of success is constancy to purpose.", false, true, english, benjaminDisraeli));
//        quotes.add(new Quote("Like all great travellers, I have seen more than I remember, and remember more than I have seen.", false, true, english, benjaminDisraeli));
//        quotes.add(new Quote("Youth is a blunder; Manhood a struggle, Old Age a regret.", false, true, english, benjaminDisraeli));
//        quotes.add(new Quote("Nurture your minds with great thoughts. To believe in the heroic makes heroes.", false, true, english, benjaminDisraeli));
//        quotes.add(new Quote("Seeing much, suffering much, and studying much, are the three pillars of learning.", false, true, english, benjaminDisraeli));
//        quotes.add(new Quote("Never complain and never explain.", false, true, english, benjaminDisraeli));
//        quotes.add(new Quote("Courage is fire, and bullying is smoke.", false, true, english, benjaminDisraeli));
//
//        Author benjaminFranklin = getAuthor(authors, "Benjamin Franklin");
//        quotes.add(new Quote("Without freedom of thought, there can be no such thing as wisdom - and no such thing as public liberty without freedom of speech.", false, true, english, benjaminFranklin));
//        quotes.add(new Quote("Early to bed and early to rise makes a man healthy, wealthy and wise.", false, true, english, benjaminFranklin));
//        quotes.add(new Quote("Either write something worth reading or do something worth writing.", false, true, english, benjaminFranklin));
//        quotes.add(new Quote("By failing to prepare, you are preparing to fail.", false, true, english, benjaminFranklin));
//        quotes.add(new Quote("Wine is constant proof that God loves us and loves to see us happy.", false, true, english, benjaminFranklin));
//        quotes.add(new Quote("We are all born ignorant, but one must work hard to remain stupid.", false, true, english, benjaminFranklin));
//        quotes.add(new Quote("Well done is better than well said.", false, true, english, benjaminFranklin));
//        quotes.add(new Quote("Without continual growth and progress, such words as improvement, achievement, and success have no meaning.", false, true, english, benjaminFranklin));
//        quotes.add(new Quote("Tell me and I forget. Teach me and I remember. Involve me and I learn.", false, true, english, benjaminFranklin));
//        quotes.add(new Quote("An investment in knowledge pays the best interest.", false, true, english, benjaminFranklin));
//
//        Author bertrandRussell = getAuthor(authors, "Bertrand Russell");
//        quotes.add(new Quote("I would never die for my beliefs because I might be wrong.", false, true, english, bertrandRussell));
//        quotes.add(new Quote("Science is what you know, philosophy is what you don't know.", false, true, english, bertrandRussell));
//        quotes.add(new Quote("Men are born ignorant, not stupid. They are made stupid by education.", false, true, english, bertrandRussell));
//        quotes.add(new Quote("The only thing that will redeem mankind is cooperation.", false, true, english, bertrandRussell));
//        quotes.add(new Quote("The world is full of magical things patiently waiting for our wits to grow sharper.", false, true, english, bertrandRussell));
//        quotes.add(new Quote("Fear is the main source of superstition, and one of the main sources of cruelty. To conquer fear is the beginning of wisdom.", false, true, english, bertrandRussell));
//        quotes.add(new Quote("The fact that an opinion has been widely held is no evidence whatever that it is not utterly absurd.", false, true, english, bertrandRussell));
//        quotes.add(new Quote("The trouble with the world is that the stupid are cocksure and the intelligent are full of doubt.", false, true, english, bertrandRussell));
//        quotes.add(new Quote("War does not determine who is right - only who is left.", false, true, english, bertrandRussell));
//        quotes.add(new Quote("The good life is one inspired by love and guided by knowledge.", false, true, english, bertrandRussell));
//
//        Author beyonceKnowles = getAuthor(authors, "Beyonce Knowles");
//        quotes.add(new Quote("If everything was perfect, you would never learn and you would never grow.", false, true, english, beyonceKnowles));
//        quotes.add(new Quote("Who I am on stage is very, very different to who I am in real life.", false, true, english, beyonceKnowles));
//        quotes.add(new Quote("I can never be safe; I always try and go against the grain. As soon as I accomplish one thing, I just set a higher goal. That's how I've gotten to where I am.", false, true, english, beyonceKnowles));
//        quotes.add(new Quote("I think it's healthy for a person to be nervous. It means you care - that you work hard and want to give a great performance. You just have to channel that nervous energy into the show.", false, true, english, beyonceKnowles));
//        quotes.add(new Quote("Whenever I'm confused about something, I ask God to reveal the answers to my questions, and he does.", false, true, english, beyonceKnowles));
//        quotes.add(new Quote("I feel like you get more bees with honey. But that doesn't mean I don't get frustrated in my life. My way of dealing with frustration is to shut down and to think and speak logically.", false, true, english, beyonceKnowles));
//        quotes.add(new Quote("I always treat myself to one meal on Sundays when I can have whatever I want. Usually it's pizza, which is my favorite indulgence.", false, true, english, beyonceKnowles));
//        quotes.add(new Quote("I get nervous when I don't get nervous. If I'm nervous I know I'm going to have a good show.", false, true, english, beyonceKnowles));
//        quotes.add(new Quote("I hold a lot of things in. I'm always making sure everybody is okay. I usually don't rage; I usually don't curse. So for me, it's a great thing to be able to scream and say whatever I want.", false, true, english, beyonceKnowles));
//        quotes.add(new Quote("There's my personal life, my sensitive side, and then me as a performer, sexy and energised and fun.", false, true, english, beyonceKnowles));
//
//        Author billGates = getAuthor(authors, "Bill Gates");
//        quotes.add(new Quote("The first rule of any technology used in a business is that automation applied to an efficient operation will magnify the efficiency. The second is that automation applied to an inefficient operation will magnify the inefficiency.", false, true, english, billGates));
//        quotes.add(new Quote("Research shows that there is only half as much variation in student achievement between schools as there is among classrooms in the same school. If you want your child to get the best education possible, it is actually more important to get him assigned to a great teacher than to a great school.", false, true, english, billGates));
//        quotes.add(new Quote("We all need people who will give us feedback. That's how we improve.", false, true, english, billGates));
//        quotes.add(new Quote("Your most unhappy customers are your greatest source of learning.", false, true, english, billGates));
//        quotes.add(new Quote("The advance of technology is based on making it fit in so that you don't really even notice it, so it's part of everyday life.", false, true, english, billGates));
//        quotes.add(new Quote("The Internet is becoming the town square for the global village of tomorrow.", false, true, english, billGates));
//        quotes.add(new Quote("Information technology and business are becoming inextricably interwoven. I don't think anybody can talk meaningfully about one without the talking about the other.", false, true, english, billGates));
//        quotes.add(new Quote("It's fine to celebrate success but it is more important to heed the lessons of failure.", false, true, english, billGates));
//        quotes.add(new Quote("Technology is just a tool. In terms of getting the kids working together and motivating them, the teacher is the most important.", false, true, english, billGates));
//        quotes.add(new Quote("Success is a lousy teacher. It seduces smart people into thinking they can't lose.", false, true, english, billGates));
//
//        Author billyGraham = getAuthor(authors, "Billy Graham");
//        quotes.add(new Quote("Racial prejudice, anti-Semitism, or hatred of anyone with different beliefs has no place in the human mind or heart.", false, true, english, billyGraham));
//        quotes.add(new Quote("Make sure of your commitment to Jesus Christ, and seek to follow Him every day. Don't be swayed by the false values and goals of this world, but put Christ and His will first in everything you do.", false, true, english, billyGraham));
//        quotes.add(new Quote("When wealth is lost, nothing is lost; when health is lost, something is lost; when character is lost, all is lost.", false, true, english, billyGraham));
//        quotes.add(new Quote("God proved His love on the Cross. When Christ hung, and bled, and died, it was God saying to the world, 'I love you.'", false, true, english, billyGraham));
//        quotes.add(new Quote("I think that the Bible teaches that homosexuality is a sin, but the Bible also teaches that pride is a sin, jealousy is a sin, and hate is a sin, evil thoughts are a sin. So I don't think that homosexuality should be chosen as the overwhelming sin that we are doing today.", false, true, english, billyGraham));
//        quotes.add(new Quote("God has given us two hands, one to receive with and the other to give with.", false, true, english, billyGraham));
//        quotes.add(new Quote("Nothing can bring a real sense of security into the home except true love.", false, true, english, billyGraham));
//        quotes.add(new Quote("Each life is made up of mistakes and learning, waiting and growing, practicing patience and being persistent.", false, true, english, billyGraham));
//        quotes.add(new Quote("A child who is allowed to be disrespectful to his parents will not have true respect for anyone.", false, true, english, billyGraham));
//        quotes.add(new Quote("The greatest legacy one can pass on to one's children and grandchildren is not money or other material things accumulated in one's life, but rather a legacy of character and faith.", false, true, english, billyGraham));
//
//        Author blaisePascal = getAuthor(authors, "Blaise Pascal");
//        quotes.add(new Quote("We view things not only from different sides, but with different eyes; we have no wish to find them alike.", false, true, english, blaisePascal));
//        quotes.add(new Quote("Belief is a wise wager. Granted that faith cannot be proved, what harm will come to you if you gamble on its truth and it proves false? If you gain, you gain all; if you lose, you lose nothing. Wager, then, without hesitation, that He exists.", false, true, english, blaisePascal));
//        quotes.add(new Quote("Men never do evil so completely and cheerfully as when they do it from religious conviction.", false, true, english, blaisePascal));
//        quotes.add(new Quote("Noble deeds that are concealed are most esteemed.", false, true, english, blaisePascal));
//        quotes.add(new Quote("Small minds are concerned with the extraordinary, great minds with the ordinary.", false, true, english, blaisePascal));
//        quotes.add(new Quote("In faith there is enough light for those who want to believe and enough shadows to blind those who don't.", false, true, english, blaisePascal));
//        quotes.add(new Quote("Love has reasons which reason cannot understand.", false, true, english, blaisePascal));
//        quotes.add(new Quote("All men's miseries derive from not being able to sit in a quiet room alone.", false, true, english, blaisePascal));
//        quotes.add(new Quote("Human beings must be known to be loved; but Divine beings must be loved to be known.", false, true, english, blaisePascal));
//        quotes.add(new Quote("Kind words do not cost much. Yet they accomplish much.", false, true, english, blaisePascal));
//
//        Author bobDylan = getAuthor(authors, "Bob Dylan");
//        quotes.add(new Quote("I'll let you be in my dreams if I can be in yours.", false, true, english, bobDylan));
//        quotes.add(new Quote("I accept chaos, I'm not sure whether it accepts me.", false, true, english, bobDylan));
//        quotes.add(new Quote("A mistake is to commit a misunderstanding.", false, true, english, bobDylan));
//        quotes.add(new Quote("What's money? A man is a success if he gets up in the morning and goes to bed at night and in between does what he wants to do.", false, true, english, bobDylan));
//        quotes.add(new Quote("All I can do is be me, whoever that is.", false, true, english, bobDylan));
//        quotes.add(new Quote("Inspiration is hard to come by. You have to take it where you find it.", false, true, english, bobDylan));
//        quotes.add(new Quote("Yesterday's just a memory, tomorrow is never what it's supposed to be.", false, true, english, bobDylan));
//        quotes.add(new Quote("No one is free, even the birds are chained to the sky.", false, true, english, bobDylan));
//        quotes.add(new Quote("Take care of all your memories. For you cannot relive them.", false, true, english, bobDylan));
//        quotes.add(new Quote("A hero is someone who understands the responsibility that comes with his freedom.", false, true, english, bobDylan));
//
//        Author CSLewis = getAuthor(authors, "C. S. Lewis");
//        quotes.add(new Quote("The task of the modern educator is not to cut down jungles, but to irrigate deserts.", false, true, english, CSLewis));
//        quotes.add(new Quote("Education without values, as useful as it is, seems rather to make man a more clever devil.", false, true, english, CSLewis));
//        quotes.add(new Quote("History isn't just the story of bad people doing bad things. It's quite as much a story of people trying to do good things. But somehow, something goes wrong.", false, true, english, CSLewis));
//        quotes.add(new Quote("Friendship is unnecessary, like philosophy, like art... It has no survival value; rather it is one of those things that give value to survival.", false, true, english, CSLewis));
//        quotes.add(new Quote("I believe in Christianity as I believe that the sun has risen: not only because I see it, but because by it I see everything else.", false, true, english, CSLewis));
//        quotes.add(new Quote("If I find in myself a desire which no experience in this world can satisfy, the most probable explanation is that I was made for another world.", false, true, english, CSLewis));
//        quotes.add(new Quote("Failures, repeated failures, are finger posts on the road to achievement. One fails forward toward success.", false, true, english, CSLewis));
//        quotes.add(new Quote("We all want progress, but if you're on the wrong road, progress means doing an about-turn and walking back to the right road; in that case, the man who turns back soonest is the most progressive.", false, true, english, CSLewis));
//        quotes.add(new Quote("Look for yourself, and you will find in the long run only hatred, loneliness, despair, rage, ruin, and decay. But look for Christ, and you will find Him, and with Him everything else thrown in.", false, true, english, CSLewis));
//        quotes.add(new Quote("Courage is not simply one of the virtues, but the form of every virtue at the testing point.", false, true, english, CSLewis));
//
//        Author carlJung = getAuthor(authors, "Carl Jung");
//        quotes.add(new Quote("In all chaos there is a cosmos, in all disorder a secret order.", false, true, english, carlJung));
//        quotes.add(new Quote("Every form of addiction is bad, no matter whether the narcotic be alcohol or morphine or idealism.", false, true, english, carlJung));
//        quotes.add(new Quote("Children are educated by what the grown-up is and not by his talk.", false, true, english, carlJung));
//        quotes.add(new Quote("The meeting of two personalities is like the contact of two chemical substances: if there is any reaction, both are transformed.", false, true, english, carlJung));
//        quotes.add(new Quote("Everything that irritates us about others can lead us to an understanding of ourselves.", false, true, english, carlJung));
//        quotes.add(new Quote("Your vision will become clear only when you can look into your own heart. Who looks outside, dreams; who looks inside, awakes.", false, true, english, carlJung));
//        quotes.add(new Quote("Knowing your own darkness is the best method for dealing with the darknesses of other people.", false, true, english, carlJung));
//        quotes.add(new Quote("One looks back with appreciation to the brilliant teachers, but with gratitude to those who touched our human feelings. The curriculum is so much necessary raw material, but warmth is the vital element for the growing plant and for the soul of the child.", false, true, english, carlJung));
//        quotes.add(new Quote("Even a happy life cannot be without a measure of darkness, and the word happy would lose its meaning if it were not balanced by sadness. It is far better take things as they come along with patience and equanimity.", false, true, english, carlJung));
//        quotes.add(new Quote("The word 'happy' would lose its meaning if it were not balanced by sadness.", false, true, english, carlJung));
//
//        Author carlSagan = getAuthor(authors, "Carl Sagan");
//        quotes.add(new Quote("The universe is not required to be in perfect harmony with human ambition.", false, true, english, carlSagan));
//        quotes.add(new Quote("It is far better to grasp the universe as it really is than to persist in delusion, however satisfying and reassuring.", false, true, english, carlSagan));
//        quotes.add(new Quote("For small creatures such as we the vastness is bearable only through love.", false, true, english, carlSagan));
//        quotes.add(new Quote("Extinction is the rule. Survival is the exception.", false, true, english, carlSagan));
//        quotes.add(new Quote("The brain is like a muscle. When it is in use we feel very good. Understanding is joyous.", false, true, english, carlSagan));
//        quotes.add(new Quote("Imagination will often carry us to worlds that never were. But without it we go nowhere.", false, true, english, carlSagan));
//        quotes.add(new Quote("Who are we? We find that we live on an insignificant planet of a humdrum star lost in a galaxy tucked away in some forgotten corner of a universe in which there are far more galaxies than people.", false, true, english, carlSagan));
//        quotes.add(new Quote("If you wish to make an apple pie from scratch, you must first invent the universe.", false, true, english, carlSagan));
//        quotes.add(new Quote("We live in a society exquisitely dependent on science and technology, in which hardly anyone knows anything about science and technology.", false, true, english, carlSagan));
//        quotes.add(new Quote("Science is a way of thinking much more than it is a body of knowledge.", false, true, english, carlSagan));
//
//        Author carlBurnett = getAuthor(authors, "Carl Burnett");
//        quotes.add(new Quote("Only I can change my life. No one can do it for me.", false, true, english, carlBurnett));
//        quotes.add(new Quote("When you have a dream, you've got to grab it and never let go.", false, true, english, carlBurnett));
//        quotes.add(new Quote("Words, once they are printed, have a life of their own.", false, true, english, carlBurnett));
//        quotes.add(new Quote("I liked myself better when I wasn't me.", false, true, english, carlBurnett));
//        quotes.add(new Quote("You have to go through the falling down in order to learn to walk. It helps to know that you can survive it. That's an education in itself.", false, true, english, carlBurnett));
//        quotes.add(new Quote("Everybody I know who is funny, it's in them. You can teach timing, or some people are able to tell a joke, though I don't like to tell jokes. But I think you have to be born with a sense of humor and a sense of timing.", false, true, english, carlBurnett));
//        quotes.add(new Quote("My grandmother and I would go see movies, and we'd come back to the apartment - we had a one-room apartment in Hollywood - and I would kind of lock myself in this little dressing room area with a cracked mirror on the door and act out what I had just seen.", false, true, english, carlBurnett));
//        quotes.add(new Quote("It costs a lot to sue a magazine, and it's too bad that we don't have a system where the losing team has to pay the winning team's lawyers.", false, true, english, carlBurnett));
//        quotes.add(new Quote("'m not always optimistic. You wouldn't have all cylinders cooking if you were always like Mary Poppins.", false, true, english, carlBurnett));
//        quotes.add(new Quote("I love to write. I have always loved writing. That was my first love.", false, true, english, carlBurnett));
//
//        Author charlesDickens = getAuthor(authors, "Charles Dickens");
//        quotes.add(new Quote("A moral being is one who is capable of reflecting on his past actions and their motives - of approving of some and disapproving of others.", false, true, english, charlesDickens));
//        quotes.add(new Quote("Man is descended from a hairy, tailed quadruped, probably arboreal in its habits.", false, true, english, charlesDickens));
//        quotes.add(new Quote("The very essence of instinct is that it's followed independently of reason.", false, true, english, charlesDickens));
//        quotes.add(new Quote("A scientific man ought to have no wishes, no affections, - a mere heart of stone.", false, true, english, charlesDickens));
//        quotes.add(new Quote("I love fools' experiments. I am always making them.", false, true, english, charlesDickens));
//        quotes.add(new Quote("Ignorance more frequently begets confidence than does knowledge: it is those who know little, and not those who know much, who so positively assert that this or that problem will never be solved by science.", false, true, english, charlesDickens));
//        quotes.add(new Quote("The mystery of the beginning of all things is insoluble by us; and I for one must be content to remain an agnostic.", false, true, english, charlesDickens));
//        quotes.add(new Quote("An American monkey, after getting drunk on brandy, would never touch it again, and thus is much wiser than most men.", false, true, english, charlesDickens));
//        quotes.add(new Quote("A man's friendships are one of the best measures of his worth.", false, true, english, charlesDickens));
//        quotes.add(new Quote("A man who dares to waste one hour of time has not discovered the value of life.", false, true, english, charlesDickens));
//
//        Author charlesRSwindoll = getAuthor(authors, "Charles R. Swindoll");
//        quotes.add(new Quote("The remarkable thing is, we have a choice everyday regarding the attitude we will embrace for that day.", false, true, english, charlesRSwindoll));
//        quotes.add(new Quote("A teardrop on earth summons the King of heaven.", false, true, english, charlesRSwindoll));
//        quotes.add(new Quote("We are all faced with a series of great opportunities brilliantly disguised as impossible situations.", false, true, english, charlesRSwindoll));
//        quotes.add(new Quote("Prejudice is a learned trait. You're not born prejudiced; you're taught it.", false, true, english, charlesRSwindoll));
//        quotes.add(new Quote("We cannot change our past. We can not change the fact that people act in a certain way. We can not change the inevitable. The only thing we can do is play on the one string we have, and that is our attitude.", false, true, english, charlesRSwindoll));
//        quotes.add(new Quote("Attitude is more important than the past, than education, than money, than circumstances, than what people do or say. It is more important than appearance, giftedness, or skill.", false, true, english, charlesRSwindoll));
//        quotes.add(new Quote("The difference between something good and something great is attention to detail.", false, true, english, charlesRSwindoll));
//        quotes.add(new Quote("Each day of our lives we make deposits in the memory banks of our children.", false, true, english, charlesRSwindoll));
//        quotes.add(new Quote("I cannot even imagine where I would be today were it not for that handful of friends who have given me a heart full of joy. Let's face it, friends make life a lot more fun.", false, true, english, charlesRSwindoll));
//        quotes.add(new Quote("Life is 10% what happens to you and 90% how you react to it.", false, true, english, charlesRSwindoll));
//
//        Author cheGuevara = getAuthor(authors, "Che Guevara");
//        quotes.add(new Quote("The revolution is not an apple that falls when it is ripe. You have to make it fall.", false, true, english, cheGuevara));
//        quotes.add(new Quote("I have a wish. It as a fear as well - that in my end will be my beginning.", false, true, english, cheGuevara));
//        quotes.add(new Quote("The true revolutionary is guided by a great feeling of love. It is impossible to think of a genuine revolutionary lacking this quality.", false, true, english, cheGuevara));
//        quotes.add(new Quote("We cannot be sure of having something to live for unless we are willing to die for it.", false, true, english, cheGuevara));
//        quotes.add(new Quote("I know you are here to kill me. Shoot, coward, you are only going to kill a man.", false, true, english, cheGuevara));
//        quotes.add(new Quote("The only passion that guides me is for the truth... I look at everything from this point of view.", false, true, english, cheGuevara));
//        quotes.add(new Quote("Passion is needed for any great work, and for the revolution, passion and audacity are required in big doses.", false, true, english, cheGuevara));
//        quotes.add(new Quote("Remember that the revolution is what is important, and each one of us, alone, is worth nothing.", false, true, english, cheGuevara));
//        quotes.add(new Quote("Study hard so that you can master technology, which allows us to master nature.", false, true, english, cheGuevara));
//        quotes.add(new Quote("I am one of those people who believes that the solution to the world's problems is to be found behind the Iron Curtain.", false, true, english, cheGuevara));
//
//        Author christopherHitchens = getAuthor(authors, "Christopher Hitchens");
//        quotes.add(new Quote("The concept of loneliness and exile and self-sufficiency continually bucks me up.", false, true, english, christopherHitchens));
//        quotes.add(new Quote("I don't think it's possible to have a sense of tragedy without having a sense of humor.", false, true, english, christopherHitchens));
//        quotes.add(new Quote("To 'choose' dogma and faith over doubt and experience is to throw out the ripening vintage and to reach greedily for the Kool-Aid.", false, true, english, christopherHitchens));
//        quotes.add(new Quote("Trust is not the same as faith. A friend is someone you trust. Putting faith in anyone is a mistake.", false, true, english, christopherHitchens));
//        quotes.add(new Quote("To the dumb question, 'Why me?' the cosmos barely bothers to return the reply, 'Why not?'", false, true, english, christopherHitchens));
//        quotes.add(new Quote("Religion is part of the human make-up. It's also part of our cultural and intellectual history. Religion was our first attempt at literature, the texts, our first attempt at cosmology, making sense of where we are in the universe, our first attempt at health care, believing in faith healing, our first attempt at philosophy.", false, true, english, christopherHitchens));
//        quotes.add(new Quote("What can be asserted without evidence can be dismissed without evidence.", false, true, english, christopherHitchens));
//        quotes.add(new Quote("I learned that very often the most intolerant and narrow-minded people are the ones who congratulate themselves on their tolerance and open-mindedness.", false, true, english, christopherHitchens));
//        quotes.add(new Quote("A gentleman is never rude except on purpose - I can honestly be nasty sober, believe you me.", false, true, english, christopherHitchens));
//        quotes.add(new Quote("Owners of dogs will have noticed that, if you provide them with food and water and shelter and affection, they will think you are God. Whereas owners of cats are compelled to realize that, if you provide them with food and water and affection, they draw the conclusion that they are God.", false, true, english, christopherHitchens));
//
//        Author clintEastwood = getAuthor(authors, "Clint Eastwood");
//        quotes.add(new Quote("God gave you a brain. Do the best you can with it. And you don't have to be Einstein, but Einstein was mentally tough. He believed what he believed. And he worked out things. And he argued with people who disagreed with him. But I'm sure he didn't call everybody jerks.", false, true, english, clintEastwood));
//        quotes.add(new Quote("I tried being reasonable, I didn't like it.", false, true, english, clintEastwood));
//        quotes.add(new Quote("What you put into life is what you get out of it.", false, true, english, clintEastwood));
//        quotes.add(new Quote("Sometimes if you want to see a change for the better, you have to take things into your own hands.", false, true, english, clintEastwood));
//        quotes.add(new Quote("If you want a guarantee, buy a toaster.", false, true, english, clintEastwood));
//        quotes.add(new Quote("You should never give up your inner self.", false, true, english, clintEastwood));
//        quotes.add(new Quote("I have a very strict gun control policy: if there's a gun around, I want to be in control of it.", false, true, english, clintEastwood));
//        quotes.add(new Quote("They say marriages are made in Heaven. But so is thunder and lightning.", false, true, english, clintEastwood));
//        quotes.add(new Quote("There's a rebel lying deep in my soul.", false, true, english, clintEastwood));
//        quotes.add(new Quote("Respect your efforts, respect yourself. Self-respect leads to self-discipline. When you have both firmly under your belt, that's real power.", false, true, english, clintEastwood));
//
//        Author conorMcGregor = getAuthor(authors, "Conor McGregor");
//        quotes.add(new Quote("You might win some, you might lose some. But you go in, you challenge yourself, you become a better man, a better individual, a better fighter.", false, true, english, conorMcGregor));
//        quotes.add(new Quote("I enjoy competition. I enjoy challenges. If a challenge is in front of me and it appeals to me, I will go ahead and conquer it.", false, true, english, conorMcGregor));
//        quotes.add(new Quote("My success isn't a result of arrogance - it's a result of belief.", false, true, english, conorMcGregor));
//        quotes.add(new Quote("The more you seek the uncomfortable, the more you will become comfortable.", false, true, english, conorMcGregor));
//        quotes.add(new Quote("I am in the fighting game. I don't care about anything else. I don't watch the news, I don't care about politics, I don't care about other sports. I don't care about anything I don't need to care about. This is my sport: it is my life. I study it; I think about it all the time. Nothing else matters.", false, true, english, conorMcGregor));
//        quotes.add(new Quote("I'm just looking to learn, grow, stay focused, and become a better fighter and a better athlete.", false, true, english, conorMcGregor));
//        quotes.add(new Quote("I'm not going to get somewhere and say, 'OK, I'm done.' Success is never final; I'll just keep on going. The same way as failure never being fatal. Just keep going. I'm going to the stars and then past them.", false, true, english, conorMcGregor));
//        quotes.add(new Quote("I'm just trying to be myself. I'm not trying to be anyone else.", false, true, english, conorMcGregor));
//        quotes.add(new Quote("The thing about the truth is, not a lot of people can handle it.", false, true, english, conorMcGregor));
//        quotes.add(new Quote("I don't feel pressure in a negative way. I like pressure. I feel excitement and calm at the same time. No pressure, no diamonds. I want pressure: pressure creates drama, creates emotion.", false, true, english, conorMcGregor));
//
//        Author dalaiLama = getAuthor(authors, "Dalai Lama");
//        quotes.add(new Quote("Happiness is not something ready made. It comes from your own actions.", false, true, english, dalaiLama));
//        quotes.add(new Quote("Calm mind brings inner strength and self-confidence, so that's very important for good health.", false, true, english, dalaiLama));
//        quotes.add(new Quote("In order to carry a positive action we must develop here a positive vision.", false, true, english, dalaiLama));
//        quotes.add(new Quote("Love and compassion are necessities, not luxuries. Without them humanity cannot survive.", false, true, english, dalaiLama));
//        quotes.add(new Quote("Old friends pass away, new friends appear. It is just like the days. An old day passes, a new day arrives. The important thing is to make it meaningful: a meaningful friend - or a meaningful day.", false, true, english, dalaiLama));
//        quotes.add(new Quote("Be kind whenever possible. It is always possible.", false, true, english, dalaiLama));
//        quotes.add(new Quote("If you want others to be happy, practice compassion. If you want to be happy, practice compassion.", false, true, english, dalaiLama));
//        quotes.add(new Quote("When we meet real tragedy in life, we can react in two ways - either by losing hope and falling into self-destructive habits, or by using the challenge to find our inner strength. Thanks to the teachings of Buddha, I have been able to take this second way.", false, true, english, dalaiLama));
//        quotes.add(new Quote("When you practice gratefulness, there is a sense of respect toward others.", false, true, english, dalaiLama));
//        quotes.add(new Quote("Our prime purpose in this life is to help others. And if you can't help them, at least don't hurt them.", false, true, english, dalaiLama));
//
//        Author douglasAdams = getAuthor(authors, "Douglas Adams");
//        quotes.add(new Quote("In the beginning the Universe was created. This has made a lot of people very angry and been widely regarded as a bad move.", false, true, english, douglasAdams));
//        quotes.add(new Quote("It is a well-known fact that those people who must want to rule people are, ipso facto, those least suited to do it... anyone who is capable of getting themselves made President should on no account be allowed to do the job.", false, true, english, douglasAdams));
//        quotes.add(new Quote("This must be Thursday. I never could get the hang of Thursdays.", false, true, english, douglasAdams));
//        quotes.add(new Quote("It is a mistake to think you can solve any major problems just with potatoes.", false, true, english, douglasAdams));
//        quotes.add(new Quote("Human beings, who are almost unique in having the ability to learn from the experience of others, are also remarkable for their apparent disinclination to do so.", false, true, english, douglasAdams));
//        quotes.add(new Quote("A common mistake that people make when trying to design something completely foolproof is to underestimate the ingenuity of complete fools.", false, true, english, douglasAdams));
//        quotes.add(new Quote("I may not have gone where I intended to go, but I think I have ended up where I intended to be.", false, true, english, douglasAdams));
//        quotes.add(new Quote("Flying is learning how to throw yourself at the ground and miss.", false, true, english, douglasAdams));
//        quotes.add(new Quote("I love deadlines. I like the whooshing sound they make as they fly by.", false, true, english, douglasAdams));
//        quotes.add(new Quote("To give real service you must add something which cannot be bought or measured with money, and that is sincerity and integrity.", false, true, english, douglasAdams));
//
//        Author dickGregory = getAuthor(authors, "Dick Gregory");
//        quotes.add(new Quote("Political promises are much like marriage vows. They are made at the beginning of the relationship between candidate and voter, but are quickly forgotten.", false, true, english, dickGregory));
//        quotes.add(new Quote("When I lost my rifle, the Army charged me 85 dollars. That is why in the Navy the Captain goes down with the ship.", false, true, english, dickGregory));
//        quotes.add(new Quote("Riches do not delight us so much with their possession, as torment us with their loss.", false, true, english, dickGregory));
//        quotes.add(new Quote("There's a God force inside of you that gives you a will to live.", false, true, english, dickGregory));
//        quotes.add(new Quote("I am really enjoying the new Martin Luther King Jr stamp - just think about all those white bigots, licking the backside of a black man.", false, true, english, dickGregory));
//        quotes.add(new Quote("Just being a Negro doesn't qualify you to understand the race situation any more than being sick makes you an expert on medicine.", false, true, english, dickGregory));
//        quotes.add(new Quote("You know, I always say white is not a colour, white is an attitude, and if you haven't got trillions of dollars in the bank that you don't need, you can't be white.", false, true, english, dickGregory));
//        quotes.add(new Quote("We thought I was going to be a great athlete, and we were wrong, and I thought I was going to be a great entertainer, and that wasn't it either. I'm going to be an American Citizen. First class.", false, true, english, dickGregory));
//        quotes.add(new Quote("We used to root for the Indians against the cavalry, because we didn't think it was fair in the history books that when the cavalry won it was a great victory, and when the Indians won it was a massacre.", false, true, english, dickGregory));
//        quotes.add(new Quote("Coconut milk is the only thing on this planet that comes identically to mother's milk.", false, true, english, dickGregory));
//
//        Author dollyParton = getAuthor(authors, "Dolly Parton");
//        quotes.add(new Quote("When I wake up, I expect things to be good. If they're not, then I try to set about trying to make them as good as I can 'cause I know I'm gonna have to live that day anyway. So why not try to make the most of it if you can? Some days, they pan out a little better than others, but you still gotta always just try.", false, true, english, dollyParton));
//        quotes.add(new Quote("You can be rich in spirit, kindness, love and all those things that you can't put a dollar sign on.", false, true, english, dollyParton));
//        quotes.add(new Quote("I wanted to write a book that talked about the emotions of children, which is the rainbow. We all have moods. We talk about being blue when we're sad, and being yellow when we're cowards, and when we're mad, we're red.", false, true, english, dollyParton));
//        quotes.add(new Quote("I'm not offended by all the dumb blonde jokes because I know I'm not dumb... and I also know that I'm not blonde.", false, true, english, dollyParton));
//        quotes.add(new Quote("Storms make trees take deeper roots.", false, true, english, dollyParton));
//        quotes.add(new Quote("No matter what, I always make it home for Christmas. I love to go to my Tennessee Mountain Home and invite all of my nieces and nephews and their spouses and kids and do what we all like to do - eat, laugh, trade presents and just enjoy each other... and sometimes I even dress up like Santa Claus!", false, true, english, dollyParton));
//        quotes.add(new Quote("It's a good thing I was born a girl, otherwise I'd be a drag queen.", false, true, english, dollyParton));
//        quotes.add(new Quote("I thank God for my failures. Maybe not at the time but after some reflection. I never feel like a failure just because something I tried has failed.", false, true, english, dollyParton));
//        quotes.add(new Quote("If you don't like the road you're walking, start paving another one.", false, true, english, dollyParton));
//        quotes.add(new Quote("The way I see it, if you want the rainbow, you gotta put up with the rain.", false, true, english, dollyParton));
//
//        Author donaldTrump = getAuthor(authors, "Donald Trump");
//        quotes.add(new Quote("When you open your heart to patriotism, there is no room for prejudice. The Bible tells us, 'How good and pleasant it is when God's people live together in unity.", false, true, english, donaldTrump));
//        quotes.add(new Quote("When somebody challenges you, fight back. Be brutal, be tough.", false, true, english, donaldTrump));
//        quotes.add(new Quote("I have embraced crying mothers who have lost their children because our politicians put their personal agendas before the national good. I have no patience for injustice, no tolerance for government incompetence, no sympathy for leaders who fail their citizens.", false, true, english, donaldTrump));
//        quotes.add(new Quote("We will make America strong again. We will make America proud again. We will make America safe again. And we will make America great again.", false, true, english, donaldTrump));
//        quotes.add(new Quote("One of the key problems today is that politics is such a disgrace, good people don't go into government.", false, true, english, donaldTrump));
//        quotes.add(new Quote("It is time to remember that old wisdom our soldiers will never forget: that whether we are black or brown or white, we all bleed the same red blood of patriots, we all enjoy the same glorious freedoms, and we all salute the same great American Flag.", false, true, english, donaldTrump));
//        quotes.add(new Quote("My whole life is about winning. I don't lose often. I almost never lose.", false, true, english, donaldTrump));
//        quotes.add(new Quote("You have to think anyway, so why not think big?", false, true, english, donaldTrump));
//        quotes.add(new Quote("No dream is too big. No challenge is too great. Nothing we want for our future is beyond our reach.", false, true, english, donaldTrump));
//        quotes.add(new Quote("Sometimes by losing a battle you find a new way to win the war.", false, true, english, donaldTrump));
//
//        Author DrSeuss = getAuthor(authors, "Dr. Seuss");
//        quotes.add(new Quote("Think left and think right and think low and think high. Oh, the thinks you can think up if only you try!", false, true, english, DrSeuss));
//        quotes.add(new Quote("The more that you read, the more things you will know. The more that you learn, the more places you'll go.", false, true, english, DrSeuss));
//        quotes.add(new Quote("Unless someone like you cares a whole awful lot, nothing is going to get better. It's not.", false, true, english, DrSeuss));
//        quotes.add(new Quote("Step with care and great tact, and remember that Life's a Great Balancing Act.", false, true, english, DrSeuss));
//        quotes.add(new Quote("I like nonsense; it wakes up the brain cells.", false, true, english, DrSeuss));
//        quotes.add(new Quote("From there to here, and here to there, funny things are everywhere.", false, true, english, DrSeuss));
//        quotes.add(new Quote("You have brains in your head. You have feet in your shoes. You can steer yourself in any direction you choose. You're on your own, and you know what you know. And you are the guy who'll decide where to go.", false, true, english, DrSeuss));
//        quotes.add(new Quote("A person's a person, no matter how small.", false, true, english, DrSeuss));
//        quotes.add(new Quote("Don't cry because it's over. Smile because it happened.", false, true, english, DrSeuss));
//        quotes.add(new Quote("Today you are you! That is truer than true! There is no one alive who is you-er than you!", false, true, english, DrSeuss));
//
//        Author drake = getAuthor(authors, "Drake");
//        quotes.add(new Quote("Sometimes it's the journey that teaches you a lot about your destination.", false, true, english, drake));
//        quotes.add(new Quote("Jealousy is just love and hate at the same time.", false, true, english, drake));
//        quotes.add(new Quote("People like to build their own story about my life. I don't know if it makes them feel better, or if it makes it okay for them to not like me, but the last thing I grew up as was rich.", false, true, english, drake));
//        quotes.add(new Quote("I feel connected to my generation through the music, but I also fear for us. We're in a very self-destructive state where we're addicted to outside opinions and we all feel like we have fans.", false, true, english, drake));
//        quotes.add(new Quote("Me and my dad are friends. We're cool. I'll never be disappointed again, because I don't expect anything anymore from him. I just let him exist, and that's how we get along.", false, true, english, drake));
//        quotes.add(new Quote("Trying to meet new women, it's always a little more difficult as opposed to calling somebody I knew that's single and trying to rebuild that connection.", false, true, english, drake));
//        quotes.add(new Quote("When I'm writing, I'm thinking about how the songs are going to play live. Fifty bars of rap don't translate onstage. No matter how potent the music, you lose the crowd. They want a hook; they want to sing your stuff back to you.", false, true, english, drake));
//        quotes.add(new Quote("Reviews condition people. At the end of the day, a lot of human minds are malleable. They can be easily shaped with strong words.", false, true, english, drake));
//        quotes.add(new Quote("My dad is a great writer. Naturally talented, naturally charming. He embodies that back-in-the-day cool.", false, true, english, drake));
//        quotes.add(new Quote("I like Ryan Gosling as an actor. I watch all of his movies, and he's Canadian and I just like his swag. I read his interviews and I'm a big fan of his.", false, true, english, drake));
//
//        Author dwightDEisenhower = getAuthor(authors, "Dwight D. Eisenhower");
//        quotes.add(new Quote("Leadership is the art of getting someone else to do something you want done because he wants to do it.", false, true, english, dwightDEisenhower));
//        quotes.add(new Quote("A people that values its privileges above its principles soon loses both.", false, true, english, dwightDEisenhower));
//        quotes.add(new Quote("Neither a wise man nor a brave man lies down on the tracks of history to wait for the train of the future to run over him.", false, true, english, dwightDEisenhower));
//        quotes.add(new Quote("Here in America we are descended in blood and in spirit from revolutionists and rebels - men and women who dare to dissent from accepted doctrine. As their heirs, may we never confuse honest dissent with disloyal subversion.", false, true, english, dwightDEisenhower));
//        quotes.add(new Quote("Plans are nothing; planning is everything.", false, true, english, dwightDEisenhower));
//        quotes.add(new Quote("In preparing for battle I have always found that plans are useless, but planning is indispensable.", false, true, english, dwightDEisenhower));
//        quotes.add(new Quote("You don't lead by hitting people over the head - that's assault, not leadership. Dwight D. Eisenhower", false, true, english, dwightDEisenhower));
//        quotes.add(new Quote("Motivation is the art of getting people to do what you want them to do because they want to do it.", false, true, english, dwightDEisenhower));
//        quotes.add(new Quote("How far you can go without destroying from within what you are trying to defend from without?", false, true, english, dwightDEisenhower));
//        quotes.add(new Quote("The supreme quality for leadership is unquestionably integrity. Without it, no real success is possible, no matter whether it is on a section gang, a football field, in an army, or in an office.", false, true, english, dwightDEisenhower));
//
//        Author elieWiesel = getAuthor(authors, "Elie Wiesel");
//        quotes.add(new Quote("Just as despair can come to one only from other human beings, hope, too, can be given to one only by other human beings.", false, true, english, elieWiesel));
//        quotes.add(new Quote("Wherever men and women are persecuted because of their race, religion, or political views, that place must - at that moment - become the center of the universe.", false, true, english, elieWiesel));
//        quotes.add(new Quote("I marvel at the resilience of the Jewish people. Their best characteristic is their desire to remember. No other people has such an obsession with memory.", false, true, english, elieWiesel));
//        quotes.add(new Quote("Most people think that shadows follow, precede or surround beings or objects. The truth is that they also surround words, ideas, desires, deeds, impulses and memories.", false, true, english, elieWiesel));
//        quotes.add(new Quote("That I survived the Holocaust and went on to love beautiful girls, to talk, to write, to have toast and tea and live my life - that is what is abnormal.", false, true, english, elieWiesel));
//        quotes.add(new Quote("For me, every hour is grace. And I feel gratitude in my heart each time I can meet someone and look at his or her smile.", false, true, english, elieWiesel));
//        quotes.add(new Quote("The opposite of love is not hate, it's indifference.", false, true, english, elieWiesel));
//        quotes.add(new Quote("There may be times when we are powerless to prevent injustice, but there must never be a time when we fail to protest.", false, true, english, elieWiesel));
//        quotes.add(new Quote("We must take sides. Neutrality helps the oppressor, never the victim. Silence encourages the tormentor, never the tormented.", false, true, english, elieWiesel));
//        quotes.add(new Quote("Without memory, there is no culture. Without memory, there would be no civilization, no society, no future.", false, true, english, elieWiesel));
//
//        Author elizabethI = getAuthor(authors, "Elizabeth I");
//        quotes.add(new Quote("I know I have the body of a weak and feeble woman, but I have the heart and stomach of a king, and of a king of England too.", false, true, english, elizabethI));
//        quotes.add(new Quote("I do not want a husband who honours me as a queen, if he does not love me as a woman.", false, true, english, elizabethI));
//        quotes.add(new Quote("Though the sex to which I belong is considered weak you will nevertheless find me a rock that bends to no wind.", false, true, english, elizabethI));
//        quotes.add(new Quote("Fear not, we are of the nature of the lion, and cannot descend to the destruction of mice and such small beasts.", false, true, english, elizabethI));
//        quotes.add(new Quote("There is nothing about which I am more anxious than my country, and for its sake I am willing to die ten deaths, if that be possible.", false, true, english, elizabethI));
//        quotes.add(new Quote("I have the heart of a man, not a woman, and I am not afraid of anything.", false, true, english, elizabethI));
//        quotes.add(new Quote("God has given such brave soldiers to this Crown that, if they do not frighten our neighbours, at least they prevent us from being frightened by them.", false, true, english, elizabethI));
//        quotes.add(new Quote("Must! Is must a word to be addressed to princes? Little man, little man! Thy father, if he had been alive, durst not have used that word.", false, true, english, elizabethI));
//        quotes.add(new Quote("Ye may have a greater prince, but ye shall never have a more loving prince.", false, true, english, elizabethI));
//        quotes.add(new Quote("My mortal foe can no ways wish me a greater harm than England's hate; neither should death be less welcome unto me than such a mishap betide me.", false, true, english, elizabethI));
//
//        Author ellenDeGeneres = getAuthor(authors, "Ellen DeGeneres");
//        quotes.add(new Quote("Sometimes you can't see yourself clearly until you see yourself through the eyes of others.", false, true, english, ellenDeGeneres));
//        quotes.add(new Quote("It's our challenges and obstacles that give us layers of depth and make us interesting. Are they fun when they happen? No. But they are what make us unique. And that's what I know for sure... I think.", false, true, english, ellenDeGeneres));
//        quotes.add(new Quote("Find out who you are and be that person. That's what your soul was put on this Earth to be. Find that truth, live that truth and everything else will come.", false, true, english, ellenDeGeneres));
//        quotes.add(new Quote("I'm not an activist; I don't look for controversy. I'm not a political person, but I'm a person with compassion. I care passionately about equal rights. I care about human rights. I care about animal rights.", false, true, english, ellenDeGeneres));
//        quotes.add(new Quote("We need more kindness, more compassion, more joy, more laughter. I definitely want to contribute to that.", false, true, english, ellenDeGeneres));
//        quotes.add(new Quote("I had everything I'd hoped for, but I wasn't being myself. So I decided to be honest about who I was. It was strange: The people who loved me for being funny suddenly didn't like me for being... me.", false, true, english, ellenDeGeneres));
//        quotes.add(new Quote("We focus so much on our differences, and that is creating, I think, a lot of chaos and negativity and bullying in the world. And I think if everybody focused on what we all have in common - which is - we all want to be happy.", false, true, english, ellenDeGeneres));
//        quotes.add(new Quote("If we're destroying our trees and destroying our environment and hurting animals and hurting one another and all that stuff, there's got to be a very powerful energy to fight that. I think we need more love in the world. We need more kindness, more compassion, more joy, more laughter. I definitely want to contribute to that.", false, true, english, ellenDeGeneres));
//        quotes.add(new Quote("Here are the values that I stand for: honesty, equality, kindness, compassion, treating people the way you want to be treated and helping those in need. To me, those are traditional values.", false, true, english, ellenDeGeneres));
//        quotes.add(new Quote("I was raised around heterosexuals, as all heterosexuals are, that's where us gay people come from... you heterosexuals.", false, true, english, ellenDeGeneres));
//
//        Author elonMusk = getAuthor(authors, "Elon Musk");
//        quotes.add(new Quote("We're running the most dangerous experiment in history right now, which is to see how much carbon dioxide the atmosphere... can handle before there is an environmental catastrophe.", false, true, english, elonMusk));
//        quotes.add(new Quote("If you get up in the morning and think the future is going to be better, it is a bright day. Otherwise, it's not.", false, true, english, elonMusk));
//        quotes.add(new Quote("When I was in college, I wanted to be involved in things that would change the world.", false, true, english, elonMusk));
//        quotes.add(new Quote("I think that's the single best piece of advice: constantly think about how you could be doing things better and questioning yourself.", false, true, english, elonMusk));
//        quotes.add(new Quote("If you're trying to create a company, it's like baking a cake. You have to have all the ingredients in the right proportion.", false, true, english, elonMusk));
//        quotes.add(new Quote("I would like to die on Mars. Just not on impact.", false, true, english, elonMusk));
//        quotes.add(new Quote("Patience is a virtue, and I'm learning patience. It's a tough lesson.", false, true, english, elonMusk));
//        quotes.add(new Quote("Some people don't like change, but you need to embrace change if the alternative is disaster.", false, true, english, elonMusk));
//        quotes.add(new Quote("I think it's very important to have a feedback loop, where you're constantly thinking about what you've done and how you could be doing it better. I think that's the single best piece of advice: constantly think about how you could be doing things better and questioning yourself.", false, true, english, elonMusk));
//        quotes.add(new Quote("When something is important enough, you do it even if the odds are not in your favor.", false, true, english, elonMusk));
//
//        Author elvisPresley = getAuthor(authors, "Elvis Presley");
//        quotes.add(new Quote("I'm not trying to be sexy. It's just my way of expressing myself when I move around.", false, true, english, elvisPresley));
//        quotes.add(new Quote("Truth is like the sun. You can shut it out for a time, but it ain't goin' away.", false, true, english, elvisPresley));
//        quotes.add(new Quote("Rock and roll music, if you like it, if you feel it, you can't help but move to it. That's what happens to me. I can't help it.'", false, true, english, elvisPresley));
//        quotes.add(new Quote("A live concert to me is exciting because of all the electricity that is generated in the crowd and on stage. It's my favorite part of the business, live concerts.", false, true, english, elvisPresley));
//        quotes.add(new Quote("Rhythm is something you either have or don't have, but when you have it, you have it all over.", false, true, english, elvisPresley));
//        quotes.add(new Quote("Adversity is sometimes hard upon a man; but for one man who can stand prosperity, there are a hundred that will stand adversity.", false, true, english, elvisPresley));
//        quotes.add(new Quote("I'll never feel comfortable taking a strong drink, and I'll never feel easy smoking a cigarette. I just don't think those things are right for me.", false, true, english, elvisPresley));
//        quotes.add(new Quote("When I was a boy, I always saw myself as a hero in comic books and in movies. I grew up believing this dream.", false, true, english, elvisPresley));
//        quotes.add(new Quote("Man, that record came out and was real big in Memphis. They started playing it, and it got real big. Don't know why-the lyrics had no meaning.", false, true, english, elvisPresley));
//        quotes.add(new Quote("It's human nature to gripe, but I'm going ahead and doing the best I can.", false, true, english, elvisPresley));
//
//        Author eminem = getAuthor(authors, "Eminem");
//        quotes.add(new Quote("A lot of truth is said in jest.", false, true, english, eminem));
//        quotes.add(new Quote("Sometimes I feel like rap music is almost the key to stopping racism.", false, true, english, eminem));
//        quotes.add(new Quote("Dealing with backstabbers, there was one thing I learned. They're only powerful when you got your back turned.", false, true, english, eminem));
//        quotes.add(new Quote("Everybody has goals, aspirations or whatever, and everybody has been at a point in their life where nobody believed in them.", false, true, english, eminem));
//        quotes.add(new Quote("I am whatever you say I am; if I wasn't, then why would you say I am.", false, true, english, eminem));
//        quotes.add(new Quote("I'm stupid, I'm ugly, I'm dumb, I smell. Did I mention I'm stupid?", false, true, english, eminem));
//        quotes.add(new Quote("I say what I want to say and do what I want to do. There's no in between. People will either love you for it or hate you for it.", false, true, english, eminem));
//        quotes.add(new Quote("Somewhere deep down there's a decent man in me, he just can't be found.", false, true, english, eminem));
//        quotes.add(new Quote("Trust is hard to come by. That's why my circle is small and tight. I'm kind of funny about making new friends.", false, true, english, eminem));
//        quotes.add(new Quote("The truth is you don't know what is going to happen tomorrow. Life is a crazy ride, and nothing is guaranteed.", false, true, english, eminem));
//
//        Author ermaBombeck = getAuthor(authors, "Erma Bombeck");
//        quotes.add(new Quote("Don't confuse fame with success. Madonna is one; Helen Keller is the other.", false, true, english, ermaBombeck));
//        quotes.add(new Quote("For years my wedding ring has done its job. It has led me not into temptation. It has reminded my husband numerous times at parties that it's time to go home. It has been a source of relief to a dinner companion. It has been a status symbol in the maternity ward.", false, true, english, ermaBombeck));
//        quotes.add(new Quote("When your mother asks, 'Do you want a piece of advice?' it is a mere formality. It doesn't matter if you answer yes or no. You're going to get it anyway.", false, true, english, ermaBombeck));
//        quotes.add(new Quote("When I stand before God at the end of my life, I would hope that I would not have a single bit of talent left, and could say, 'I used everything you gave me'.", false, true, english, ermaBombeck));
//        quotes.add(new Quote("Never have more children than you have car windows.", false, true, english, ermaBombeck));
//        quotes.add(new Quote("Dreams have only one owner at a time. That's why dreamers are lonely.", false, true, english, ermaBombeck));
//        quotes.add(new Quote("Thanksgiving dinners take eighteen hours to prepare. They are consumed in twelve minutes. Half-times take twelve minutes. This is not coincidence.", false, true, english, ermaBombeck));
//        quotes.add(new Quote("Never go to a doctor whose office plants have died.", false, true, english, ermaBombeck));
//        quotes.add(new Quote("There is a thin line that separates laughter and pain, comedy and tragedy, humor and hurt.", false, true, english, ermaBombeck));
//        quotes.add(new Quote("A grandmother pretends she doesn't know who you are on Halloween.", false, true, english, ermaBombeck));
//
//        Author ernestHemingway = getAuthor(authors, "Ernest Hemingway");
//        quotes.add(new Quote("There is no friend as loyal as a book.", false, true, english, ernestHemingway));
//        quotes.add(new Quote("Courage is grace under pressure.", false, true, english, ernestHemingway));
//        quotes.add(new Quote("There is nothing noble in being superior to your fellow men. True nobility lies in being superior to your former self.", false, true, english, ernestHemingway));
//        quotes.add(new Quote("But man is not made for defeat. A man can be destroyed but not defeated.", false, true, english, ernestHemingway));
//        quotes.add(new Quote("Never go on trips with anyone you do not love.", false, true, english, ernestHemingway));
//        quotes.add(new Quote("I like to listen. I have learned a great deal from listening carefully. Most people never listen.", false, true, english, ernestHemingway));
//        quotes.add(new Quote("An intelligent man is sometimes forced to be drunk to spend time with his fools.", false, true, english, ernestHemingway));
//        quotes.add(new Quote("I love sleep. My life has the tendency to fall apart when I'm awake, you know?", false, true, english, ernestHemingway));
//        quotes.add(new Quote("The world breaks everyone, and afterward, some are strong at the broken places.", false, true, english, ernestHemingway));
//        quotes.add(new Quote("The best way to find out if you can trust somebody is to trust them.", false, true, english, ernestHemingway));
//
//        Author edgarAllanPoe = getAuthor(authors, "Edgar Allan Poe");
//        quotes.add(new Quote("Deep into that darkness peering, long I stood there, wondering, fearing, doubting, dreaming dreams no mortal ever dared to dream before.", false, true, english, edgarAllanPoe));
//        quotes.add(new Quote("I would define, in brief, the poetry of words as the rhythmical creation of Beauty.", false, true, english, edgarAllanPoe));
//        quotes.add(new Quote("Words have no power to impress the mind without the exquisite horror of their reality.", false, true, english, edgarAllanPoe));
//        quotes.add(new Quote("All that we see or seem is but a dream within a dream.", false, true, english, edgarAllanPoe));
//        quotes.add(new Quote("They who dream by day are cognizant of many things which escape those who dream only by night.", false, true, english, edgarAllanPoe));
//        quotes.add(new Quote("I became insane, with long intervals of horrible sanity.", false, true, english, edgarAllanPoe));
//        quotes.add(new Quote("The boundaries which divide Life from Death are at best shadowy and vague. Who shall say where the one ends, and where the other begins?", false, true, english, edgarAllanPoe));
//        quotes.add(new Quote("We loved with a love that was more than love.", false, true, english, edgarAllanPoe));
//        quotes.add(new Quote("Once upon a midnight dreary, while I pondered weak and weary.", false, true, english, edgarAllanPoe));
//        quotes.add(new Quote("Science has not yet taught us if madness is or is not the sublimity of the intelligence.", false, true, english, edgarAllanPoe));
//
//        Author francisofAssisi = getAuthor(authors, "Francis of Assisi");
//        quotes.add(new Quote("Start by doing what's necessary; then do what's possible; and suddenly you are doing the impossible.", false, true, english, francisofAssisi));
//        quotes.add(new Quote("Lord, make me an instrument of thy peace. Where there is hatred, let me sow love.", false, true, english, francisofAssisi));
//        quotes.add(new Quote("A single sunbeam is enough to drive away many shadows.", false, true, english, francisofAssisi));
//        quotes.add(new Quote("Where there is charity and wisdom, there is neither fear nor ignorance.", false, true, english, francisofAssisi));
//        quotes.add(new Quote("It is no use walking anywhere to preach unless our walking is our preaching.", false, true, english, francisofAssisi));
//        quotes.add(new Quote("While you are proclaiming peace with your lips, be careful to have it even more fully in your heart.", false, true, english, francisofAssisi));
//        quotes.add(new Quote("If God can work through me, he can work through anyone. Francis of Assisi", false, true, english, francisofAssisi));
//        quotes.add(new Quote("Men lose all the material things they leave behind them in this world, but they carry with them the reward of their charity and the alms they give. For these, they will receive from the Lord the reward and recompense they deserve.", false, true, english, francisofAssisi));
//        quotes.add(new Quote("For it is in giving that we receive.", false, true, english, francisofAssisi));
//        quotes.add(new Quote("Lord, grant that I might not so much seek to be loved as to love.", false, true, english, francisofAssisi));
//
//        Author frankLloydWright = getAuthor(authors, "Frank Lloyd Wright");
//        quotes.add(new Quote("Less is only more where more is no good.", false, true, english, frankLloydWright));
//        quotes.add(new Quote("Life always rides in strength to victory, not through internationalism... but only through the direct responsibility of the individual.", false, true, english, frankLloydWright));
//        quotes.add(new Quote("Space is the breath of art.", false, true, english, frankLloydWright));
//        quotes.add(new Quote("No house should ever be on a hill or on anything. It should be of the hill. Belonging to it. Hill and house should live together each the happier for the other.", false, true, english, frankLloydWright));
//        quotes.add(new Quote("There is nothing more uncommon than common sense.", false, true, english, frankLloydWright));
//        quotes.add(new Quote("The longer I live, the more beautiful life becomes.", false, true, english, frankLloydWright));
//        quotes.add(new Quote("Every great architect is - necessarily - a great poet. He must be a great original interpreter of his time, his day, his age.", false, true, english, frankLloydWright));
//        quotes.add(new Quote("The mother art is architecture. Without an architecture of our own we have no soul of our own civilization.", false, true, english, frankLloydWright));
//        quotes.add(new Quote("Study nature, love nature, stay close to nature. It will never fail you.", false, true, english, frankLloydWright));
//
//
//        Author frankSinatra = getAuthor(authors, "Frank Sinatra");
//        quotes.add(new Quote("You gotta love livin', baby, 'cause dyin' is a pain in the ass.", false, true, english, frankSinatra));
//        quotes.add(new Quote("People often remark that I'm pretty lucky. Luck is only important in so far as getting the chance to sell yourself at the right moment. After that, you've got to have talent and know how to use it.", false, true, english, frankSinatra));
//        quotes.add(new Quote("Cock your hat - angles are attitudes.", false, true, english, frankSinatra));
//        quotes.add(new Quote("I'm like Albert Schweitzer and Bertrand Russell and Albert Einstein in that I have a respect for life - in any form. I believe in nature, in the birds, the sea, the sky, in everything I can see or that there is real evidence for. If these things are what you mean by God, then I believe in God.", false, true, english, frankSinatra));
//        quotes.add(new Quote("Dare to wear the foolish clown face.", false, true, english, frankSinatra));
//        quotes.add(new Quote("I would like to be remembered as a man who had a wonderful time living life, a man who had good friends, fine family - and I don't think I could ask for anything more than that, actually.", false, true, english, frankSinatra));
//        quotes.add(new Quote("I'm gonna live till I die.", false, true, english, frankSinatra));
//        quotes.add(new Quote("I believe that God knows what each of us wants and needs. It's not necessary for us to make it to church on Sunday to reach Him. You can find Him anyplace. And if that sounds heretical, my source is pretty good: Matthew, Five to Seven, The Sermon on the Mount.", false, true, english, frankSinatra));
//        quotes.add(new Quote("The best revenge is massive success.", false, true, english, frankSinatra));
//        quotes.add(new Quote("Alcohol may be man's worst enemy, but the bible says love your enemy.", false, true, english, frankSinatra));
//
//        Author franklinDRoosevelt = getAuthor(authors, "Franklin D. Roosevelt");
//        quotes.add(new Quote("The point in history at which we stand is full of promise and danger. The world will either move forward toward unity and widely shared prosperity - or it will move apart.", false, true, english, franklinDRoosevelt));
//        quotes.add(new Quote("Confidence... thrives on honesty, on honor, on the sacredness of obligations, on faithful protection and on unselfish performance. Without them it cannot live.", false, true, english, franklinDRoosevelt));
//        quotes.add(new Quote("The only thing we have to fear is fear itself.", false, true, english, franklinDRoosevelt));
//        quotes.add(new Quote("I'm not the smartest fellow in the world, but I can sure pick smart colleagues.", false, true, english, franklinDRoosevelt));
//        quotes.add(new Quote("Democracy cannot succeed unless those who express their choice are prepared to choose wisely. The real safeguard of democracy, therefore, is education.", false, true, english, franklinDRoosevelt));
//        quotes.add(new Quote("If civilization is to survive, we must cultivate the science of human relationships - the ability of all peoples, of all kinds, to live together, in the same world at peace.", false, true, english, franklinDRoosevelt));
//        quotes.add(new Quote("When you reach the end of your rope, tie a knot in it and hang on.", false, true, english, franklinDRoosevelt));
//        quotes.add(new Quote("Let us never forget that government is ourselves and not an alien power over us. The ultimate rulers of our democracy are not a President and senators and congressmen and government officials, but the voters of this country.", false, true, english, franklinDRoosevelt));
//        quotes.add(new Quote("Nobody will ever deprive the American people of the right to vote except the American people themselves and the only way they could do this is by not voting.", false, true, english, franklinDRoosevelt));
//        quotes.add(new Quote("Happiness lies in the joy of achievement and the thrill of creative effort.", false, true, english, franklinDRoosevelt));
//
//        Author franzKafka = getAuthor(authors, "Franz Kafka");
//        quotes.add(new Quote("I have the true feeling of myself only when I am unbearably unhappy.", false, true, english, franzKafka));
//        quotes.add(new Quote("Every revolution evaporates and leaves behind only the slime of a new bureaucracy.", false, true, english, franzKafka));
//        quotes.add(new Quote("You do not need to leave your room. Remain sitting at your table and listen. Do not even listen, simply wait, be quiet still and solitary. The world will freely offer itself to you to be unmasked, it has no choice, it will roll in ecstasy at your feet.", false, true, english, franzKafka));
//        quotes.add(new Quote("My peers, lately, have found companionship through means of intoxication - it makes them sociable. I, however, cannot force myself to use drugs to cheat on my loneliness - it is all that I have - and when the drugs and alcohol dissipate, will be all that my peers have as well.", false, true, english, franzKafka));
//        quotes.add(new Quote("So long as you have food in your mouth, you have solved all questions for the time being.", false, true, english, franzKafka));
//        quotes.add(new Quote("Start with what is right rather than what is acceptable.", false, true, english, franzKafka));
//        quotes.add(new Quote("From a certain point onward there is no longer any turning back. That is the point that must be reached.", false, true, english, franzKafka));
//        quotes.add(new Quote("God gives the nuts, but he does not crack them.", false, true, english, franzKafka));
//        quotes.add(new Quote("A first sign of the beginning of understanding is the wish to die.", false, true, english, franzKafka));
//        quotes.add(new Quote("Anyone who keeps the ability to see beauty never grows old.", false, true, english, franzKafka));
//
//
//        Author frederickDouglass = getAuthor(authors, "Frederick Douglass");
//        quotes.add(new Quote("The soul that is within me no man can degrade.", false, true, english, frederickDouglass));
//        quotes.add(new Quote("The thing worse than rebellion is the thing that causes rebellion.", false, true, english, frederickDouglass));
//        quotes.add(new Quote("I didn't know I was a slave until I found out I couldn't do the things I wanted.", false, true, english, frederickDouglass));
//        quotes.add(new Quote("To suppress free speech is a double wrong. It violates the rights of the hearer as well as those of the speaker.", false, true, english, frederickDouglass));
//        quotes.add(new Quote("The white man's happiness cannot be purchased by the black man's misery.", false, true, english, frederickDouglass));
//        quotes.add(new Quote("It is not light that we need, but fire; it is not the gentle shower, but thunder. We need the storm, the whirlwind, and the earthquake.", false, true, english, frederickDouglass));
//        quotes.add(new Quote("Without a struggle, there can be no progress.", false, true, english, frederickDouglass));
//        quotes.add(new Quote("Where justice is denied, where poverty is enforced, where ignorance prevails, and where any one class is made to feel that society is an organized conspiracy to oppress, rob and degrade them, neither persons nor property will be safe.", false, true, english, frederickDouglass));
//        quotes.add(new Quote("It is easier to build strong children than to repair broken men.", false, true, english, frederickDouglass));
//        quotes.add(new Quote("If there is no struggle, there is no progress.", false, true, english, frederickDouglass));
//
//        Author fridaKahlo = getAuthor(authors, "Frida Kahlo");
//        quotes.add(new Quote("I love you more than my own skin.", false, true, english, fridaKahlo));
//        quotes.add(new Quote("I am my own muse, the subject I know best.", false, true, english, fridaKahlo));
//        quotes.add(new Quote("I never paint dreams or nightmares. I paint my own reality.", false, true, english, fridaKahlo));
//        quotes.add(new Quote("I paint flowers so they will not die.", false, true, english, fridaKahlo));
//        quotes.add(new Quote("I paint self-portraits because I am so often alone, because I am the person I know best.", false, true, english, fridaKahlo));
//        quotes.add(new Quote("I tried to drown my sorrows, but the bastards learned how to swim, and now I am overwhelmed by this decent and good feeling.", false, true, english, fridaKahlo));
//        quotes.add(new Quote("I leave you my portrait so that you will have my presence all the days and nights that I am away from you.", false, true, english, fridaKahlo));
//        quotes.add(new Quote("I was a child who went about in a world of colors... My friends, my companions, became women slowly; I became old in instants.", false, true, english, fridaKahlo));
//        quotes.add(new Quote("I find that Americans completely lack sensibility and good taste. They are boring, and they all have faces like unbaked rolls.", false, true, english, fridaKahlo));
//        quotes.add(new Quote("The most important part of the body is the brain. Of my face, I like the eyebrows and eyes. Aside from that, I like nothing. My head is too small.", false, true, english, fridaKahlo));
//
//        Author friedrichNietzsche = getAuthor(authors, "Friedrich Nietzsche");
//        quotes.add(new Quote("God is dead. God remains dead. And we have killed him. Yet his shadow still looms. How shall we comfort ourselves, the murderers of all murderers? What was holiest and mightiest of all that the world has yet owned has bled to death under our knives; who will wipe this blood off us? What water is there for us to clean ourselves?", false, true, english, friedrichNietzsche));
//        quotes.add(new Quote("All truly great thoughts are conceived by walking.", false, true, english, friedrichNietzsche));
//        quotes.add(new Quote("The individual has always had to struggle to keep from being overwhelmed by the tribe. If you try it, you will be lonely often, and sometimes frightened. But no price is too high to pay for the privilege of owning yourself.", false, true, english, friedrichNietzsche));
//        quotes.add(new Quote("Whoever fights monsters should see to it that in the process he does not become a monster. And if you gaze long enough into an abyss, the abyss will gaze back into you.", false, true, english, friedrichNietzsche));
//        quotes.add(new Quote("We love life, not because we are used to living but because we are used to loving.", false, true, english, friedrichNietzsche));
//        quotes.add(new Quote("Thoughts are the shadows of our feelings - always darker, emptier and simpler.", false, true, english, friedrichNietzsche));
//        quotes.add(new Quote("There is always some madness in love. But there is also always some reason in madness.", false, true, english, friedrichNietzsche));
//        quotes.add(new Quote("Without music, life would be a mistake.", false, true, english, friedrichNietzsche));
//        quotes.add(new Quote("To live is to suffer, to survive is to find some meaning in the suffering.", false, true, english, friedrichNietzsche));
//        quotes.add(new Quote("It is not a lack of love, but a lack of friendship that makes unhappy marriages.", false, true, english, friedrichNietzsche));
//
//        Author fyodorDostoevsky = getAuthor(authors, "Fyodor Dostoevsky");
//        quotes.add(new Quote("If there is no God, everything is permitted.", false, true, english, fyodorDostoevsky));
//        quotes.add(new Quote("A real gentleman, even if he loses everything he owns, must show no emotion. Money must be so far beneath a gentleman that it is hardly worth troubling about.", false, true, english, fyodorDostoevsky));
//        quotes.add(new Quote("Much unhappiness has come into the world because of bewilderment and things left unsaid.", false, true, english, fyodorDostoevsky));
//        quotes.add(new Quote("The cleverest of all, in my opinion, is the man who calls himself a fool at least once a month.", false, true, english, fyodorDostoevsky));
//        quotes.add(new Quote("Sarcasm: the last refuge of modest and chaste-souled people when the privacy of their soul is coarsely and intrusively invaded.", false, true, english, fyodorDostoevsky));
//        quotes.add(new Quote("The soul is healed by being with children.", false, true, english, fyodorDostoevsky));
//        quotes.add(new Quote("To live without Hope is to Cease to live.", false, true, english, fyodorDostoevsky));
//        quotes.add(new Quote("The greatest happiness is to know the source of unhappiness.", false, true, english, fyodorDostoevsky));
//        quotes.add(new Quote("We sometimes encounter people, even perfect strangers, who begin to interest us at first sight, somehow suddenly, all at once, before a word has been spoken.", false, true, english, fyodorDostoevsky));
//        quotes.add(new Quote("Beauty is mysterious as well as terrible. God and devil are fighting there, and the battlefield is the heart of man.", false, true, english, fyodorDostoevsky));
//
//        Author georgeBernardShaw = getAuthor(authors, "George Bernard Shaw");
//        quotes.add(new Quote("Life isn't about finding yourself. Life is about creating yourself.", false, true, english, georgeBernardShaw));
//        quotes.add(new Quote("Progress is impossible without change, and those who cannot change their minds cannot change anything.", false, true, english, georgeBernardShaw));
//        quotes.add(new Quote("We are made wise not by the recollection of our past, but by the responsibility for our future.", false, true, english, georgeBernardShaw));
//        quotes.add(new Quote("The single biggest problem in communication is the illusion that it has taken place.", false, true, english, georgeBernardShaw));
//        quotes.add(new Quote("There is no sincerer love than the love of food.", false, true, english, georgeBernardShaw));
//        quotes.add(new Quote("Beware of false knowledge; it is more dangerous than ignorance.", false, true, english, georgeBernardShaw));
//        quotes.add(new Quote("Success does not consist in never making mistakes but in never making the same one a second time.", false, true, english, georgeBernardShaw));
//        quotes.add(new Quote("If you cannot get rid of the family skeleton, you may as well make it dance.", false, true, english, georgeBernardShaw));
//        quotes.add(new Quote("Better keep yourself clean and bright; you are the window through which you must see the world.", false, true, english, georgeBernardShaw));
//        quotes.add(new Quote("Imagination is the beginning of creation. You imagine what you desire, you will what you imagine and at last you create what you will.", false, true, english, georgeBernardShaw));
//
//        Author galileoGalilei = getAuthor(authors, "Galileo Galilei");
//        quotes.add(new Quote("We cannot teach people anything; we can only help them discover it within themselves.", false, true, english, galileoGalilei));
//        quotes.add(new Quote("All truths are easy to understand once they are discovered; the point is to discover them.", false, true, english, galileoGalilei));
//        quotes.add(new Quote("The Bible shows the way to go to heaven, not the way the heavens go.", false, true, english, galileoGalilei));
//        quotes.add(new Quote("If I were again beginning my studies, I would follow the advice of Plato and start with mathematics.", false, true, english, galileoGalilei));
//        quotes.add(new Quote("In questions of science, the authority of a thousand is not worth the humble reasoning of a single individual.", false, true, english, galileoGalilei));
//        quotes.add(new Quote("Measure what is measurable, and make measurable what is not so.", false, true, english, galileoGalilei));
//        quotes.add(new Quote("I do not feel obliged to believe that the same God who has endowed us with sense, reason, and intellect has intended us to forgo their use.", false, true, english, galileoGalilei));
//        quotes.add(new Quote("I give infinite thanks to God, who has been pleased to make me the first observer of marvelous things.", false, true, english, galileoGalilei));
//        quotes.add(new Quote("It is surely harmful to souls to make it a heresy to believe what is proved.", false, true, english, galileoGalilei));
//        quotes.add(new Quote("I think that in the discussion of natural problems we ought to begin not with the Scriptures, but with experiments, and demonstrations.", false, true, english, galileoGalilei));
//
//        Author georgeCarlin = getAuthor(authors, "George Carlin");
//        quotes.add(new Quote("Frisbeetarianism is the belief that when you die, your soul goes up on the roof and gets stuck.", false, true, english, georgeCarlin));
//        quotes.add(new Quote("The main reason Santa is so jolly is because he knows where all the bad girls live.", false, true, english, georgeCarlin));
//        quotes.add(new Quote("Have you ever noticed that anybody driving slower than you is an idiot, and anyone going faster than you is a maniac?", false, true, english, georgeCarlin));
//        quotes.add(new Quote("Electricity is really just organized lightning.", false, true, english, georgeCarlin));
//        quotes.add(new Quote("Inside every cynical person, there is a disappointed idealist.", false, true, english, georgeCarlin));
//        quotes.add(new Quote("Don't sweat the petty things and don't pet the sweaty things.", false, true, english, georgeCarlin));
//        quotes.add(new Quote("Just cause you got the monkey off your back doesn't mean the circus has left town.", false, true, english, georgeCarlin));
//        quotes.add(new Quote("May the forces of evil become confused on the way to your house.", false, true, english, georgeCarlin));
//        quotes.add(new Quote("There are nights when the wolves are silent and only the moon howls.", false, true, english, georgeCarlin));
//        quotes.add(new Quote("Weather forecast for tonight: dark.", false, true, english, georgeCarlin));
//
//        Author georgeOrwell = getAuthor(authors, "George Orwell");
//        quotes.add(new Quote("Power is not a means, it is an end. One does not establish a dictatorship in order to safeguard a revolution; one makes the revolution in order to establish the dictatorship.", false, true, english, georgeOrwell));
//        quotes.add(new Quote("The essence of being human is that one does not seek perfection.", false, true, english, georgeOrwell));
//        quotes.add(new Quote("The very concept of objective truth is fading out of the world. Lies will pass into history.", false, true, english, georgeOrwell));
//        quotes.add(new Quote("Political language... is designed to make lies sound truthful and murder respectable, and to give an appearance of solidity to pure wind.", false, true, english, georgeOrwell));
//        quotes.add(new Quote("Freedom is the right to tell people what they do not want to hear.", false, true, english, georgeOrwell));
//        quotes.add(new Quote("Each generation imagines itself to be more intelligent than the one that went before it, and wiser than the one that comes after it.", false, true, english, georgeOrwell));
//        quotes.add(new Quote("Who controls the past controls the future. Who controls the present controls the past.", false, true, english, georgeOrwell));
//        quotes.add(new Quote("Happiness can exist only in acceptance.", false, true, english, georgeOrwell));
//        quotes.add(new Quote("In our age there is no such thing as 'keeping out of politics.' All issues are political issues, and politics itself is a mass of lies, evasions, folly, hatred and schizophrenia.", false, true, english, georgeOrwell));
//        quotes.add(new Quote("War is peace. Freedom is slavery. Ignorance is strength.", false, true, english, georgeOrwell));
//
//        Author georgeSPatton = getAuthor(authors, "George S. Patton");
//        quotes.add(new Quote("Courage is fear holding on a minute longer.", false, true, english, georgeSPatton));
//        quotes.add(new Quote("You need to overcome the tug of people against you as you reach for high goals.", false, true, english, georgeSPatton));
//        quotes.add(new Quote("Wars may be fought with weapons, but they are won by men. It is the spirit of men who follow and of the man who leads that gains the victory.", false, true, english, georgeSPatton));
//        quotes.add(new Quote("Battle is the most magnificent competition in which a human being can indulge. It brings out all that is best; it removes all that is base. All men are afraid in battle. The coward is the one who lets his fear overcome his sense of duty. Duty is the essence of manhood.", false, true, english, georgeSPatton));
//        quotes.add(new Quote("If everyone is thinking alike, then somebody isn't thinking.", false, true, english, georgeSPatton));
//        quotes.add(new Quote("Success is how high you bounce when you hit bottom.", false, true, english, georgeSPatton));
//        quotes.add(new Quote("The object of war is not to die for your country but to make the other bastard die for his.", false, true, english, georgeSPatton));
//        quotes.add(new Quote("It is foolish and wrong to mourn the men who died. Rather we should thank God that such men lived.", false, true, english, georgeSPatton));
//        quotes.add(new Quote("A good plan violently executed now is better than a perfect plan executed next week.", false, true, english, georgeSPatton));
//        quotes.add(new Quote("Accept the challenges so that you can feel the exhilaration of victory.", false, true, english, georgeSPatton));
//
//        Author georgeWBush = getAuthor(authors, "George W. Bush");
//        quotes.add(new Quote("This was not an act of terrorism, but it was an act of war.", false, true, english, georgeWBush));
//        quotes.add(new Quote("On September 11 2001, America felt its vulnerability even to threats that gather on the other side of the Earth. We resolved then, and we are resolved today, to confront every threat from any source that could bring sudden terror and suffering to America.", false, true, english, georgeWBush));
//        quotes.add(new Quote("To those of you who received honours, awards and distinctions, I say well done. And to the C students, I say you, too, can be president of the United States.", false, true, english, georgeWBush));
//        quotes.add(new Quote("It's clearly a budget. It's got a lot of numbers in it.", false, true, english, georgeWBush));
//        quotes.add(new Quote("Some folks look at me and see a certain swagger, which in Texas is called 'walking.'", false, true, english, georgeWBush));
//        quotes.add(new Quote("We will not waver; we will not tire; we will not falter, and we will not fail. Peace and Freedom will prevail.", false, true, english, georgeWBush));
//        quotes.add(new Quote("America is a Nation with a mission - and that mission comes from our most basic beliefs. We have no desire to dominate, no ambitions of empire. Our aim is a democratic peace - a peace founded upon the dignity and rights of every man and woman.", false, true, english, georgeWBush));
//        quotes.add(new Quote("We've climbed the mighty mountain. I see the valley below, and it's a valley of peace.", false, true, english, georgeWBush));
//        quotes.add(new Quote("Terrorist attacks can shake the foundations of our biggest buildings, but they cannot touch the foundation of America. These acts shatter steel, but they cannot dent the steel of American resolve.", false, true, english, georgeWBush));
//        quotes.add(new Quote("America is the land of the second chance - and when the gates of the prison open, the path ahead should lead to a better life.", false, true, english, georgeWBush));
//
//        Author georgeWashington = getAuthor(authors, "George Washington");
//        quotes.add(new Quote("It is better to offer no excuse than a bad one.", false, true, english, georgeWashington));
//        quotes.add(new Quote("We should not look back unless it is to derive useful lessons from past errors, and for the purpose of profiting by dearly bought experience.", false, true, english, georgeWashington));
//        quotes.add(new Quote("Liberty, when it begins to take root, is a plant of rapid growth.", false, true, english, georgeWashington));
//        quotes.add(new Quote("The Constitution is the guide which I never will abandon.", false, true, english, georgeWashington));
//        quotes.add(new Quote("Guard against the impostures of pretended patriotism.", false, true, english, georgeWashington));
//        quotes.add(new Quote("Truth will ultimately prevail where there is pains to bring it to light.", false, true, english, georgeWashington));
//        quotes.add(new Quote("To be prepared for war is one of the most effective means of preserving peace.", false, true, english, georgeWashington));
//        quotes.add(new Quote("Observe good faith and justice toward all nations. Cultivate peace and harmony with all.", false, true, english, georgeWashington));
//        quotes.add(new Quote("If the freedom of speech is taken away then dumb and silent we may be led, like sheep to the slaughter.", false, true, english, georgeWashington));
//        quotes.add(new Quote("It is far better to be alone, than to be in bad company.", false, true, english, georgeWashington));
//
//        Author helenKeller = getAuthor(authors, "Helen Keller");
//        quotes.add(new Quote("The best and most beautiful things in the world cannot be seen or even touched - they must be felt with the heart.", false, true, english, helenKeller));
//        quotes.add(new Quote("Optimism is the faith that leads to achievement. Nothing can be done without hope and confidence.", false, true, english, helenKeller));
//        quotes.add(new Quote("Walking with a friend in the dark is better than walking alone in the light.", false, true, english, helenKeller));
//        quotes.add(new Quote("Keep your face to the sunshine and you cannot see a shadow.", false, true, english, helenKeller));
//        quotes.add(new Quote("Character cannot be developed in ease and quiet. Only through experience of trial and suffering can the soul be strengthened, ambition inspired, and success achieved.", false, true, english, helenKeller));
//        quotes.add(new Quote("Knowledge is love and light and vision.", false, true, english, helenKeller));
//        quotes.add(new Quote("So long as the memory of certain beloved friends lives in my heart, I shall say that life is good.", false, true, english, helenKeller));
//        quotes.add(new Quote("Alone we can do so little; together we can do so much.", false, true, english, helenKeller));
//        quotes.add(new Quote("Life is either a great adventure or nothing.", false, true, english, helenKeller));
//        quotes.add(new Quote("Never bend your head. Always hold it high. Look the world straight in the eye.", false, true, english, helenKeller));
//
//        Author HJacksonBrown = getAuthor(authors, "H. Jackson Brown");
//        quotes.add(new Quote("Never forget the three powerful resources you always have available to you: love, prayer, and forgiveness.", false, true, english, HJacksonBrown));
//        quotes.add(new Quote("Earn your success based on service to others, not at the expense of others.", false, true, english, HJacksonBrown));
//        quotes.add(new Quote("Our character is what we do when we think no one is looking.", false, true, english, HJacksonBrown));
//        quotes.add(new Quote("Never give up on what you really want to do. The person with big dreams is more powerful than the one with all the facts.", false, true, english, HJacksonBrown));
//        quotes.add(new Quote("Live so that when your children think of fairness, caring, and integrity, they think of you.", false, true, english, HJacksonBrown));
//        quotes.add(new Quote("Remember that the happiest people are not those getting more, but those giving more.", false, true, english, HJacksonBrown));
//        quotes.add(new Quote("Remember that the most valuable antiques are dear old friends.", false, true, english, HJacksonBrown));
//        quotes.add(new Quote("Sometimes the heart sees what is invisible to the eye.", false, true, english, HJacksonBrown));
//        quotes.add(new Quote("Love is when the other person's happiness is more important than your own.", false, true, english, HJacksonBrown));
//        quotes.add(new Quote("The best preparation for tomorrow is doing your best today.", false, true, english, HJacksonBrown));
//
//        Author HLMencken = getAuthor(authors, "H. L. Mencken");
//        quotes.add(new Quote("The one permanent emotion of the inferior man is fear - fear of the unknown, the complex, the inexplicable. What he wants above everything else is safety.", false, true, english, HLMencken));
//        quotes.add(new Quote("A judge is a law student who marks his own examination papers.", false, true, english, HLMencken));
//        quotes.add(new Quote("Legend: A lie that has attained the dignity of age.", false, true, english, HLMencken));
//        quotes.add(new Quote("In this world of sin and sorrow there is always something to be thankful for; as for me, I rejoice that I am not a Republican.", false, true, english, HLMencken));
//        quotes.add(new Quote("Every election is a sort of advance auction sale of stolen goods.", false, true, english, HLMencken));
//        quotes.add(new Quote("The whole aim of practical politics is to keep the populace alarmed (and hence clamorous to be led to safety) by menacing it with an endless series of hobgoblins, all of them imaginary.", false, true, english, HLMencken));
//        quotes.add(new Quote("Democracy is a pathetic belief in the collective wisdom of individual ignorance.", false, true, english, HLMencken));
//        quotes.add(new Quote("For every complex problem there is an answer that is clear, simple, and wrong.", false, true, english, HLMencken));
//        quotes.add(new Quote("On some great and glorious day the plain folks of the land will reach their heart's desire at last, and the White House will be adorned by a downright moron.", false, true, english, HLMencken));
//        quotes.add(new Quote("Democracy is the art and science of running the circus from the monkey cage.", false, true, english, HLMencken));
//
//        Author HPLovecraft = getAuthor(authors, "H. P. Lovecraft");
//        quotes.add(new Quote("Blue, green, grey, white, or black; smooth, ruffled, or mountainous; that ocean is not silent.", false, true, english, HPLovecraft));
//        quotes.add(new Quote("The most merciful thing in the world... is the inability of the human mind to correlate all its contents.", false, true, english, HPLovecraft));
//        quotes.add(new Quote("It is absolutely necessary, for the peace and safety of mankind, that some of earth's dark, dead corners and unplumbed depths be let alone; lest sleeping abnormalities wake to resurgent life, and blasphemously surviving nightmares squirm and splash out of their black lairs to newer and wider conquests.", false, true, english, HPLovecraft));
//        quotes.add(new Quote("We live on a placid island of ignorance in the midst of black seas of infinity, and it was not meant that we should voyage far.", false, true, english, HPLovecraft));
//        quotes.add(new Quote("Children will always be afraid of the dark, and men with minds sensitive to hereditary impulse will always tremble at the thought of the hidden and fathomless worlds of strange life which may pulsate in the gulfs beyond the stars, or press hideously upon our own globe in unholy dimensions which only the dead and the moonstruck can glimpse.", false, true, english, HPLovecraft));
//        quotes.add(new Quote("Unhappy is he to whom the memories of childhood bring only fear and sadness.", false, true, english, HPLovecraft));
//        quotes.add(new Quote("The world is indeed comic, but the joke is on mankind.", false, true, english, HPLovecraft));
//        quotes.add(new Quote("Cats are the runes of beauty, invincibility, wonder, pride, freedom, coldness, self-sufficiency, and dainty individuality - the qualities of sensitive, enlightened, mentally developed, pagan, cynical, poetic, philosophic, dispassionate, reserved, independent, Nietzschean, unbroken, civilised, master-class men.", false, true, english, HPLovecraft));
//        quotes.add(new Quote("The oldest and strongest emotion of mankind is fear, and the oldest and strongest kind of fear is fear of the unknown.", false, true, english, HPLovecraft));
//        quotes.add(new Quote("Ocean is more ancient than the mountains, and freighted with the memories and the dreams of Time.", false, true, english, HPLovecraft));
//
//        Author harrietTubman = getAuthor(authors, "Harriet Tubman");
//        quotes.add(new Quote("Read my letter to the old folks, and give my love to them, and tell my brothers to be always watching unto prayer, and when the good old ship of Zion comes along, to be ready to step aboard.", false, true, english, harrietTubman));
//        quotes.add(new Quote("In my dreams and visions, I seemed to see a line, and on the other side of that line were green fields, and lovely flowers, and beautiful white ladies, who stretched out their arms to me over the line, but I couldn't reach them no-how. I always fell before I got to the line.", false, true, english, harrietTubman));
//        quotes.add(new Quote("Never wound a snake; kill it.", false, true, english, harrietTubman));
//        quotes.add(new Quote("I grew up like a neglected weed - ignorant of liberty, having no experience of it.", false, true, english, harrietTubman));
//        quotes.add(new Quote("I had crossed the line. I was free; but there was no one to welcome me to the land of freedom. I was a stranger in a strange land.", false, true, english, harrietTubman));
//        quotes.add(new Quote("I had reasoned this out in my mind, there was one of two things I had a right to, liberty or death; if I could not have one, I would have the other.", false, true, english, harrietTubman));
//        quotes.add(new Quote("I was the conductor of the Underground Railroad for eight years, and I can say what most conductors can't say; I never ran my train off the track and I never lost a passenger.", false, true, english, harrietTubman));
//        quotes.add(new Quote("I think slavery is the next thing to hell. If a person would send another into bondage, he would, it appears to me, be bad enough to send him into hell if he could.", false, true, english, harrietTubman));
//        quotes.add(new Quote("Now I've been free, I know what a dreadful condition slavery is. I have seen hundreds of escaped slaves, but I never saw one who was willing to go back and be a slave.", false, true, english, harrietTubman));
//        quotes.add(new Quote("Now I've been free, I know what a dreadful condition slavery is. I have seen hundreds of escaped slaves, but I never saw one who was willing to go back and be a slave.", false, true, english, harrietTubman));
//
//        Author harrySTruman = getAuthor(authors, "Harry S Truman");
//        quotes.add(new Quote("You want a friend in Washington? Get a dog.", false, true, english, harrySTruman));
//        quotes.add(new Quote("The atom bomb was no 'great decision.' It was merely another powerful weapon in the arsenal of righteousness.", false, true, english, harrySTruman));
//        quotes.add(new Quote("A pessimist is one who makes difficulties of his opportunities and an optimist is one who makes opportunities of his difficulties.", false, true, english, harrySTruman));
//        quotes.add(new Quote("I never did give anybody hell. I just told the truth and they thought it was hell.", false, true, english, harrySTruman));
//        quotes.add(new Quote("In reading the lives of great men, I found that the first victory they won was over themselves. self-discipline with all of them came first.", false, true, english, harrySTruman));
//        quotes.add(new Quote("Actions are the seed of fate deeds grow into destiny.", false, true, english, harrySTruman));
//        quotes.add(new Quote("America was not built on fear. America was built on courage, on imagination and an unbeatable determination to do the job at hand.", false, true, english, harrySTruman));
//        quotes.add(new Quote("It is amazing what you can accomplish if you do not care who gets the credit.", false, true, english, harrySTruman));
//        quotes.add(new Quote("Men make history and not the other way around. In periods where there is no leadership, society stands still. Progress occurs when courageous, skillful leaders seize the opportunity to change things for the better.", false, true, english, harrySTruman));
//        quotes.add(new Quote("There is nothing new in the world except the history you do not know.", false, true, english, harrySTruman));
//
//        Author henryDavidThoreau = getAuthor(authors, "Henry David Thoreau");
//        quotes.add(new Quote("What lies behind us and what lies ahead of us are tiny matters compared to what lives within us.", false, true, english, henryDavidThoreau));
//        quotes.add(new Quote("Pursue some path, however narrow and crooked, in which you can walk with love and reverence.", false, true, english, henryDavidThoreau));
//        quotes.add(new Quote("Not until we are lost do we begin to understand ourselves.", false, true, english, henryDavidThoreau));
//        quotes.add(new Quote("An early-morning walk is a blessing for the whole day.", false, true, english, henryDavidThoreau));
//        quotes.add(new Quote("I am grateful for what I am and have. My thanksgiving is perpetual.", false, true, english, henryDavidThoreau));
//        quotes.add(new Quote("Many men go fishing all of their lives without knowing that it is not fish they are after.", false, true, english, henryDavidThoreau));
//        quotes.add(new Quote("The language of friendship is not words but meanings.", false, true, english, henryDavidThoreau));
//        quotes.add(new Quote("This world is but a canvas to our imagination.", false, true, english, henryDavidThoreau));
//        quotes.add(new Quote("Friends... they cherish one another's hopes. They are kind to one another's dreams.", false, true, english, henryDavidThoreau));
//        quotes.add(new Quote("It's not what you look at that matters, it's what you see.", false, true, english, henryDavidThoreau));
//
//        Author henryFord = getAuthor(authors, "Henry Ford");
//        quotes.add(new Quote("Thinking is the hardest work there is, which is probably the reason why so few engage in it.", false, true, english, henryFord));
//        quotes.add(new Quote("When everything seems to be going against you, remember that the airplane takes off against the wind, not with it.", false, true, english, henryFord));
//        quotes.add(new Quote("Obstacles are those frightful things you see when you take your eyes off your goal.", false, true, english, henryFord));
//        quotes.add(new Quote("If you think you can do a thing or think you can't do a thing, you're right.", false, true, english, henryFord));
//        quotes.add(new Quote("If everyone is moving forward together, then success takes care of itself.", false, true, english, henryFord));
//        quotes.add(new Quote("A business that makes nothing but money is a poor business.", false, true, english, henryFord));
//        quotes.add(new Quote("Don't find fault, find a remedy.", false, true, english, henryFord));
//        quotes.add(new Quote("Failure is simply the opportunity to begin again, this time more intelligently.", false, true, english, henryFord));
//        quotes.add(new Quote("My best friend is the one who brings out the best in me.", false, true, english, henryFord));
//        quotes.add(new Quote("Coming together is a beginning; keeping together is progress; working together is success.", false, true, english, henryFord));
//
//        Author henryKissinger = getAuthor(authors, "Henry Kissinger");
//        quotes.add(new Quote("It is, after all, the responsibility of the expert to operate the familiar and that of the leader to transcend it.", false, true, english, henryKissinger));
//        quotes.add(new Quote("For other nations, utopia is a blessed past never to be recovered; for Americans it is just beyond the horizon.", false, true, english, henryKissinger));
//        quotes.add(new Quote("My view of my role is that together with like-minded men and women, I could help contribute to a bipartisan view of American engagement in the world for another period; I could do my part to overcome this really, in a way, awful period in which we are turning history into personal recriminations, depriving our political system of a serious debate.", false, true, english, henryKissinger));
//        quotes.add(new Quote("America has fought five wars since 1945 and has gained its objectives in only one of them, the Gulf War.", false, true, english, henryKissinger));
//        quotes.add(new Quote("Ninety percent of the politicians give the other ten percent a bad reputation.", false, true, english, henryKissinger));
//        quotes.add(new Quote("University politics are vicious precisely because the stakes are so small.", false, true, english, henryKissinger));
//        quotes.add(new Quote("The Vietnam War was a great tragedy for our country. And it is now far enough away so that one can study without using the slogans to see what's really happened.", false, true, english, henryKissinger));
//        quotes.add(new Quote("Accept everything about yourself - I mean everything, You are you and that is the beginning and the end - no apologies, no regrets.", false, true, english, henryKissinger));
//        quotes.add(new Quote("The task of the leader is to get his people from where they are to where they have not been.", false, true, english, henryKissinger));
//        quotes.add(new Quote("There cannot be a crisis next week. My schedule is already full.", false, true, english, henryKissinger));
//
//        Author iceCube = getAuthor(authors, "Ice Cube");
//        quotes.add(new Quote("I think, to me, reality is better than being fake.", false, true, english, iceCube));
//        quotes.add(new Quote("Don't worry about being a star, worry about doing good work, and all that will come to you.", false, true, english, iceCube));
//        quotes.add(new Quote("Truth is the ultimate power. When the truth comes around, all the lies have to run and hide.", false, true, english, iceCube));
//        quotes.add(new Quote("I think the worst thing you can do about a situation is nothing.", false, true, english, iceCube));
//        quotes.add(new Quote("I make a mean cup of coffee, if you give me the right ingredients.", false, true, english, iceCube));
//        quotes.add(new Quote("Rap is always evolving. It's easy for the old school to hate the new school, but it's a music that got a little stifled I think, by the Internet a little bit.", false, true, english, iceCube));
//        quotes.add(new Quote("What I learned from architectural drafting is that everything has to have a plan to work. You just can't wing it. I can't get all the materials I need for a house and just start building. Whether it's a career, family, life - you have to plan it out.", false, true, english, iceCube));
//        quotes.add(new Quote("I used to game a lot, you know, back in the day. My gaming time done got so short that my skills ain't where they need to be to be online, you know what I'm saying? I just got that Xbox One. I gotta get my skills back, up the par to call myself a gamer.", false, true, english, iceCube));
//        quotes.add(new Quote("I have a really beautiful life right now, so there is no reason to be hostile. I'm a husband, a father and a man who tries to do the right thing in life and in my work.", false, true, english, iceCube));
//        quotes.add(new Quote("Gangsta to us didn't have anything to do with Al Capone and stuff like that. It's just about living your life the way you want to live it. And you're not going to let nothing stop you.", false, true, english, iceCube));
//
//        Author idaBWells = getAuthor(authors, "Ida B. Wells");
//        quotes.add(new Quote("The people must know before they can act, and there is no educator to compare with the press.", false, true, english, idaBWells));
//        quotes.add(new Quote("Our country's national crime is lynching. It is not the creature of an hour, the sudden outburst of uncontrolled fury, or the unspeakable brutality of an insane mob.", false, true, english, idaBWells));
//        quotes.add(new Quote("Somebody must show that the Afro-American race is more sinned against than sinning, and it seems to have fallen upon me to do so.", false, true, english, idaBWells));
//        quotes.add(new Quote("No nation, savage or civilized, save only the United States of America, has confessed its inability to protect its women save by hanging, shooting, and burning alleged offenders.", false, true, english, idaBWells));
//        quotes.add(new Quote("The mob spirit has grown with the increasing intelligence of the Afro-American.", false, true, english, idaBWells));
//        quotes.add(new Quote("The white man's victory soon became complete by fraud, violence, intimidation and murder.", false, true, english, idaBWells));
//        quotes.add(new Quote("What becomes a crime deserving capital punishment when the tables are turned is a matter of small moment when the negro woman is the accusing party.", false, true, english, idaBWells));
//        quotes.add(new Quote("The South is brutalized to a degree not realized by its own inhabitants, and the very foundation of government, law and order, are imperilled.", false, true, english, idaBWells));
//        quotes.add(new Quote("The alleged menace of universal suffrage having been avoided by the absolute suppression of the negro vote, the spirit of mob murder should have been satisfied and the butchery of negroes should have ceased.", false, true, english, idaBWells));
//        quotes.add(new Quote("Brave men do not gather by thousands to torture and murder a single individual, so gagged and bound he cannot make even feeble resistance or defense.", false, true, english, idaBWells));
//
//        Author immanuelKant = getAuthor(authors, "Immanuel Kant");
//        quotes.add(new Quote("He who is cruel to animals becomes hard also in his dealings with men. We can judge the heart of a man by his treatment of animals.", false, true, english, immanuelKant));
//        quotes.add(new Quote("Always recognize that human individuals are ends, and do not use them as means to your end.", false, true, english, immanuelKant));
//        quotes.add(new Quote("All our knowledge begins with the senses, proceeds then to the understanding, and ends with reason. There is nothing higher than reason.", false, true, english, immanuelKant));
//        quotes.add(new Quote("It is beyond a doubt that all our knowledge begins with experience.", false, true, english, immanuelKant));
//        quotes.add(new Quote("Morality is not the doctrine of how we may make ourselves happy, but how we may make ourselves worthy of happiness.", false, true, english, immanuelKant));
//        quotes.add(new Quote("Live your life as though your every act were to become a universal law.", false, true, english, immanuelKant));
//        quotes.add(new Quote("Thoughts without content are empty, intuitions without concepts are blind.", false, true, english, immanuelKant));
//        quotes.add(new Quote("Two things awe me most, the starry sky above me and the moral law within me.", false, true, english, immanuelKant));
//        quotes.add(new Quote("In law a man is guilty when he violates the rights of others. In ethics he is guilty if he only thinks of doing so.", false, true, english, immanuelKant));
//        quotes.add(new Quote("Science is organized knowledge. Wisdom is organized life.", false, true, english, immanuelKant));
//
//        Author indiraGandhi = getAuthor(authors, "Indira Gandhi");
//        quotes.add(new Quote("There are two kinds of people, those who do the work and those who take the credit. Try to be in the first group; there is less competition there.", false, true, english, indiraGandhi));
//        quotes.add(new Quote("Winning or losing of the election is less important than strengthening the country.", false, true, english, indiraGandhi));
//        quotes.add(new Quote("I have lived a long life, and I am proud that I spend the whole of my life in the service of my people. I am only proud of this and nothing else. I shall continue to serve until my last breath, and when I die, I can say, that every drop of my blood will invigorate India and strengthen it.", false, true, english, indiraGandhi));
//        quotes.add(new Quote("The power to question is the basis of all human progress.", false, true, english, indiraGandhi));
//        quotes.add(new Quote("You cannot shake hands with a clenched fist.", false, true, english, indiraGandhi));
//        quotes.add(new Quote("You must learn to be still in the midst of activity and to be vibrantly alive in repose.", false, true, english, indiraGandhi));
//        quotes.add(new Quote("We have to prove to the disinherited majority of the world that ecology and conservation will not work against their interest but will bring an improvement in their lives.", false, true, english, indiraGandhi));
//        quotes.add(new Quote("Have a bias toward action - let's see something happen now. You can break that big plan into small steps and take the first step right away.", false, true, english, indiraGandhi));
//        quotes.add(new Quote("Happiness is a state of mind, you know. I don't think you are permanently happy. One is happy about certain things and not so happy about others.", false, true, english, indiraGandhi));
//        quotes.add(new Quote("I do not like carving the world into segments; we are one world.", false, true, english, indiraGandhi));
//
//        Author indraNooyi = getAuthor(authors, "Indra Nooyi");
//        quotes.add(new Quote("Leadership is hard to define and good leadership even harder. But if you can get people to follow you to the ends of the earth, you are a great leader.", false, true, english, indraNooyi));
//        quotes.add(new Quote("At the end of the day, don't forget that you're a person, don't forget you're a mother, don't forget you're a wife, don't forget you're a daughter.", false, true, english, indraNooyi));
//        quotes.add(new Quote("As a leader, I am tough on myself and I raise the standard for everybody; however, I am very caring because I want people to excel at what they are doing so that they can aspire to be me in the future.", false, true, english, indraNooyi));
//        quotes.add(new Quote("When it comes to women, there has been a tendency to define women in sports in the context of their relationships - they watch games because their husbands watch. They're interested because their kids play a sport. They buy tickets to a sporting event because it's a way to spend time with family.", false, true, english, indraNooyi));
//        quotes.add(new Quote("Every year in consulting is like three years in the corporate world because you have multiple clients, multiple issues - you grow so much.", false, true, english, indraNooyi));
//        quotes.add(new Quote("The distance between number one and number two is always a constant. If you want to improve the organization, you have to improve yourself and the organization gets pulled up with you. That is a big lesson. I cannot just expect the organization to improve if I don't improve myself and lift the organization, because that distance is a constant.", false, true, english, indraNooyi));
//        quotes.add(new Quote("Most companies target women as end users, but few are effectively utilizing female employees when it comes to innovating for female consumers. When women are empowered in the design and innovation process, the likelihood of success in the marketplace improves by 144%!", false, true, english, indraNooyi));
//        quotes.add(new Quote("Each and every one of us has unknowingly played a part in the obesity problem.", false, true, english, indraNooyi));
//        quotes.add(new Quote("I'm very honest - brutally honest. I always look at things from their point of view as well as mine. And I know when to walk away.", false, true, english, indraNooyi));
//        quotes.add(new Quote("Is Naked Juice a beverage, or is it a snack? I think we can liquefy snacks or snackify liquids.", false, true, english, indraNooyi));
//
//        Author irisApfel = getAuthor(authors, "Iris Apfel");
//        quotes.add(new Quote("Great personal style is an extreme curiosity about yourself.", false, true, english, irisApfel));
//        quotes.add(new Quote("I don't happen to approve of plastic surgery. I think God put plastic surgeons on this earth for good reasons - people get burned or people might have a nose like Pinocchio and that has to be fixed. But to just chop yourself up to look a few years younger? You could come out looking like a Picasso picture.", false, true, english, irisApfel));
//        quotes.add(new Quote("I mix everything up. A museum curator once said to me that there is a great jazz component to the way I do things because good jazz is improvisation and draws elements from all different cultures. And that's the way I do everything - the way I dress and decorate.", false, true, english, irisApfel));
//        quotes.add(new Quote("If you don't learn constantly, you don't grow, and you will wither. Too many people wither on the vine. Sure, it gets a little harder as you get older, but new experiences and new challenges keep it fresh.", false, true, english, irisApfel));
//        quotes.add(new Quote("You have to push yourself when you're older because it's very easy to fall into the trap. You start to fall apart - you just have to do your best to paste yourself together. I think doing things and being active is very important. When your mind is busy, you don't hurt so much.", false, true, english, irisApfel));
//        quotes.add(new Quote("The White House is the people's house. When you do historical restoration, that must be what it is.", false, true, english, irisApfel));
//        quotes.add(new Quote("Fashion and interior design are one and the same.", false, true, english, irisApfel));
//        quotes.add(new Quote("Self-exploration is very painful, but unless you do that, you will never know who you are and who you want to be.", false, true, english, irisApfel));
//        quotes.add(new Quote("My father told me once not to expect anything from anybody so I wouldn't be disappointed. If somebody was nice and did nice things for me, I should be overjoyed, but I shouldn't go through life expecting it, which is very good advice.", false, true, english, irisApfel));
//        quotes.add(new Quote("There's different shopping in Paris than there is at a bazaar in Istanbul, but they're all wonderful.", false, true, english, irisApfel));
//
//        Author isaacNewton = getAuthor(authors, "Isaac Newton");
//        quotes.add(new Quote("Atheism is so senseless. When I look at the solar system, I see the earth at the right distance from the sun to receive the proper amounts of heat and light. This did not happen by chance.", false, true, english, isaacNewton));
//        quotes.add(new Quote("Gravity may put the planets into motion, but without the divine Power, it could never put them into such a circulating motion as they have about the Sun; and therefore, for this as well as other reasons, I am compelled to ascribe the frame of this System to an intelligent Agent.", false, true, english, isaacNewton));
//        quotes.add(new Quote("Genius is patience.", false, true, english, isaacNewton));
//        quotes.add(new Quote("My powers are ordinary. Only my application brings me success.", false, true, english, isaacNewton));
//        quotes.add(new Quote("I can calculate the motion of heavenly bodies, but not the madness of people.", false, true, english, isaacNewton));
//        quotes.add(new Quote("To every action there is always opposed an equal reaction.", false, true, english, isaacNewton));
//        quotes.add(new Quote("I do not know what I may appear to the world, but to myself I seem to have been only like a boy playing on the seashore, and diverting myself in now and then finding a smoother pebble or a prettier shell than ordinary, whilst the great ocean of truth lay all undiscovered before me.", false, true, english, isaacNewton));
//        quotes.add(new Quote("Truth is ever to be found in simplicity, and not in the multiplicity and confusion of things.", false, true, english, isaacNewton));
//        quotes.add(new Quote("We build too many walls and not enough bridges.", false, true, english, isaacNewton));
//        quotes.add(new Quote("If I have seen further than others, it is by standing upon the shoulders of giants.", false, true, english, isaacNewton));
//
//        Author isaacAsimov = getAuthor(authors, "Isaac Asimov");
//        quotes.add(new Quote("Never let your sense of morals get in the way of doing what's right.", false, true, english, isaacAsimov));
//        quotes.add(new Quote("Humanity has the stars in its future, and that future is too important to be lost under the burden of juvenile folly and ignorant superstition.", false, true, english, isaacAsimov));
//        quotes.add(new Quote("The true delight is in the finding out rather than in the knowing.", false, true, english, isaacAsimov));
//        quotes.add(new Quote("The saddest aspect of life right now is that science gathers knowledge faster than society gathers wisdom.", false, true, english, isaacAsimov));
//        quotes.add(new Quote("Writing, to me, is simply thinking through my fingers.", false, true, english, isaacAsimov));
//        quotes.add(new Quote("I do not fear computers. I fear the lack of them.", false, true, english, isaacAsimov));
//        quotes.add(new Quote("Violence is the last refuge of the incompetent.", false, true, english, isaacAsimov));
//        quotes.add(new Quote("There is a cult of ignorance in the United States, and there has always been. The strain of anti-intellectualism has been a constant thread winding its way through our political and cultural life, nurtured by the false notion that democracy means that my ignorance is just as good as your knowledge.", false, true, english, isaacAsimov));
//        quotes.add(new Quote("Life is pleasant. Death is peaceful. It's the transition that's troublesome.", false, true, english, isaacAsimov));
//        quotes.add(new Quote("People who think they know everything are a great annoyance to those of us who do.", false, true, english, isaacAsimov));
//
//        Author jesusChrist = getAuthor(authors, "Jesus Christ");
//        quotes.add(new Quote("Do not let your hearts be troubled. Trust in God; trust also in me.", false, true, english, jesusChrist));
//        quotes.add(new Quote("I am the Way, the Truth, and the Life. No one comes to the Father except through me.", false, true, english, jesusChrist));
//        quotes.add(new Quote("And know that I am with you always; yes, to the end of time.", false, true, english, jesusChrist));
//        quotes.add(new Quote("A new command I give you: Love one another. As I have loved you, so you must love one another.", false, true, english, jesusChrist));
//        quotes.add(new Quote("For what shall it profit a man, if he gain the whole world, and suffer the loss of his soul?", false, true, english, jesusChrist));
//        quotes.add(new Quote("But I say to you, Love your enemies and pray for those who persecute you, so that you may be sons of your Father who is in heaven; for he makes his sun rise on the evil and on the good, and sends rain on the just and on the unjust.", false, true, english, jesusChrist));
//        quotes.add(new Quote("So I say to you, Ask and it will be given to you; search, and you will find; knock, and the door will be opened for you.", false, true, english, jesusChrist));
//        quotes.add(new Quote("Blessed are the merciful, for they will be shown mercy.", false, true, english, jesusChrist));
//        quotes.add(new Quote("Do not be anxious about tomorrow, for tomorrow will be anxious for itself. Let the day's own trouble be sufficient for the day.", false, true, english, jesusChrist));
//        quotes.add(new Quote("Let the one among you who is without sin be the first to cast a stone.", false, true, english, jesusChrist));
//
//        Author JKRowling = getAuthor(authors, "J. K. Rowling");
//        quotes.add(new Quote("Imagination is not only the uniquely human capacity to envision that which is not, and therefore the fount of all invention and innovation. In its arguably most transformative and revelatory capacity, it is the power to that enables us to empathize with humans whose experiences we have never shared.", false, true, english, JKRowling));
//        quotes.add(new Quote("Indifference and neglect often do much more damage than outright dislike.", false, true, english, JKRowling));
//        quotes.add(new Quote("The most important thing is to read as much as you can, like I did. It will give you an understanding of what makes good writing and it will enlarge your vocabulary.", false, true, english, JKRowling));
//        quotes.add(new Quote("Poverty entails fear and stress and sometimes depression. It meets a thousand petty humiliations and hardships. Climbing out of poverty by your own efforts that is something on which to pride yourself but poverty itself is romanticized by fools.", false, true, english, JKRowling));
//        quotes.add(new Quote("Anything's possible if you've got enough nerve.", false, true, english, JKRowling));
//        quotes.add(new Quote("I sometimes have a tendency to walk on the dark side.", false, true, english, JKRowling));
//        quotes.add(new Quote("It takes a great deal of bravery to stand up to our enemies, but just as much to stand up to our friends.", false, true, english, JKRowling));
//        quotes.add(new Quote("If you want to see the true measure of a man, watch how he treats his inferiors, not his equals.", false, true, english, JKRowling));
//        quotes.add(new Quote("It is impossible to live without failing at something, unless you live so cautiously that you might as well not have lived at all, in which case you have failed by default.", false, true, english, JKRowling));
//        quotes.add(new Quote("It is our choices... that show what we truly are, far more than our abilities.", false, true, english, JKRowling));
//
//
//        Author jackWelch = getAuthor(authors, "Jack Welch");
//        quotes.add(new Quote("Giving people self-confidence is by far the most important thing that I can do. Because then they will act.", false, true, english, jackWelch));
//        quotes.add(new Quote("If you pick the right people and give them the opportunity to spread their wings and put compensation as a carrier behind it you almost don't have to manage them.", false, true, english, jackWelch));
//        quotes.add(new Quote("Management is all about managing in the short term, while developing the plans for the long ", false, true, english, jackWelch));
//        quotes.add(new Quote("Globalization has changed us into a company that searches the world, not just to sell or to source, but to find intellectual capital - the world's best talents and greatest ideas.", false, true, english, jackWelch));
//        quotes.add(new Quote("Be candid with everyone.", false, true, english, jackWelch));
//        quotes.add(new Quote("Number one, cash is king... number two, communicate... number three, buy or bury the competition.", false, true, english, jackWelch));
//        quotes.add(new Quote("Good business leaders create a vision, articulate the vision, passionately own the vision, and relentlessly drive it to completion.", false, true, english, jackWelch));
//        quotes.add(new Quote("Control your own destiny or someone else will.", false, true, english, jackWelch));
//        quotes.add(new Quote("Before you are a leader, success is all about growing yourself. When you become a leader, success is all about growing others.", false, true, english, jackWelch));
//        quotes.add(new Quote("An organization's ability to learn, and translate that learning into action rapidly, is the ultimate competitive advantage.", false, true, english, jackWelch));
//
//        Author jamesABaldwin = getAuthor(authors, "James A. Baldwin");
//        quotes.add(new Quote("The power of the white world is threatened whenever a black man refuses to accept the white world's definitions.", false, true, english, jamesABaldwin));
//        quotes.add(new Quote("Those who say it can't be done are usually interrupted by others doing it.", false, true, english, jamesABaldwin));
//        quotes.add(new Quote("I imagine one of the reasons people cling to their hates so stubbornly is because they sense, once hate is gone, they will be forced to deal with pain.", false, true, english, jamesABaldwin));
//        quotes.add(new Quote("Fires can't be made with dead embers, nor can enthusiasm be stirred by spiritless men. Enthusiasm in our daily work lightens effort and turns even labor into pleasant tasks.", false, true, english, jamesABaldwin));
//        quotes.add(new Quote("Children have never been very good at listening to their elders, but they have never failed to imitate them.", false, true, english, jamesABaldwin));
//        quotes.add(new Quote("I love America more than any other country in this world, and, exactly for this reason, I insist on the right to criticize her perpetually.", false, true, english, jamesABaldwin));
//        quotes.add(new Quote("Love does not begin and end the way we seem to think it does. Love is a battle, love is a war; love is a growing up.", false, true, english, jamesABaldwin));
//        quotes.add(new Quote("Anyone who has ever struggled with poverty knows how extremely expensive it is to be poor.", false, true, english, jamesABaldwin));
//        quotes.add(new Quote("The paradox of education is precisely this - that as one begins to become conscious one begins to examine the society in which he is being educated.", false, true, english, jamesABaldwin));
//        quotes.add(new Quote("It is certain, in any case, that ignorance, allied with power, is the most ferocious enemy justice can have.", false, true, english, jamesABaldwin));
//
//        Author johnCMaxwell = getAuthor(authors, "John C. Maxwell");
//        quotes.add(new Quote("Without failure there is no achievement.", false, true, english, johnCMaxwell));
//        quotes.add(new Quote("Leaders must be close enough to relate to others, but far enough ahead to motivate them.", false, true, english, johnCMaxwell));
//        quotes.add(new Quote("Family and friendships are two of the greatest facilitators of happiness.", false, true, english, johnCMaxwell));
//        quotes.add(new Quote("A word of encouragement from a teacher to a child can change a life. A word of encouragement from a spouse can save a marriage. A word of encouragement from a leader can inspire a person to reach her potential.", false, true, english, johnCMaxwell));
//        quotes.add(new Quote("There are two kinds of pride, both good and bad. 'Good pride' represents our dignity and self-respect. 'Bad pride' is the deadly sin of superiority that reeks of conceit and arrogance.", false, true, english, johnCMaxwell));
//        quotes.add(new Quote("A man must be big enough to admit his mistakes, smart enough to profit from them, and strong enough to correct them.", false, true, english, johnCMaxwell));
//        quotes.add(new Quote("The secret of your success is determined by your daily agenda.", false, true, english, johnCMaxwell));
//        quotes.add(new Quote("People may hear your words, but they feel your attitude.", false, true, english, johnCMaxwell));
//        quotes.add(new Quote("A leader is one who knows the way, goes the way, and shows the way.", false, true, english, johnCMaxwell));
//        quotes.add(new Quote("Leadership is influence.", false, true, english, johnCMaxwell));
//
//        Author johnFKennedy = getAuthor(authors, "John F. Kennedy");
//        quotes.add(new Quote("Conformity is the jailer of freedom and the enemy of growth.", false, true, english, johnFKennedy));
//        quotes.add(new Quote("My brother Bob doesn't want to be in government - he promised Dad he'd go straight.", false, true, english, johnFKennedy));
//        quotes.add(new Quote("Efforts and courage are not enough without purpose and direction.", false, true, english, johnFKennedy));
//        quotes.add(new Quote("Things do not happen. Things are made to happen.", false, true, english, johnFKennedy));
//        quotes.add(new Quote("If we cannot now end our differences, at least we can help make the world safe for diversity.", false, true, english, johnFKennedy));
//        quotes.add(new Quote("My fellow Americans, ask not what your country can do for you, ask what you can do for your country.", false, true, english, johnFKennedy));
//        quotes.add(new Quote("Physical fitness is not only one of the most important keys to a healthy body, it is the basis of dynamic and creative intellectual activity.", false, true, english, johnFKennedy));
//        quotes.add(new Quote("Leadership and learning are indispensable to each other.", false, true, english, johnFKennedy));
//        quotes.add(new Quote("Change is the law of life. And those who look only to the past or present are certain to miss the future.", false, true, english, johnFKennedy));
//        quotes.add(new Quote("As we express our gratitude, we must never forget that the highest appreciation is not to utter words, but to live by them.", false, true, english, johnFKennedy));
//
//        Author johannWolfgangvonGoethe = getAuthor(authors, "Johann Wolfgang von Goethe");
//        quotes.add(new Quote("I call architecture frozen music.", false, true, english, johannWolfgangvonGoethe));
//        quotes.add(new Quote("A man's manners are a mirror in which he shows his portrait.", false, true, english, johannWolfgangvonGoethe));
//        quotes.add(new Quote("One must ask children and birds how cherries and strawberries taste.", false, true, english, johannWolfgangvonGoethe));
//        quotes.add(new Quote("The way you see people is the way you treat them, and the way you treat them is what they become.", false, true, english, johannWolfgangvonGoethe));
//        quotes.add(new Quote("Magic is believing in yourself, if you can do that, you can make anything happen.", false, true, english, johannWolfgangvonGoethe));
//        quotes.add(new Quote("Correction does much, but encouragement does more.", false, true, english, johannWolfgangvonGoethe));
//        quotes.add(new Quote("Behavior is the mirror in which everyone shows their image.", false, true, english, johannWolfgangvonGoethe));
//        quotes.add(new Quote("Girls we love for what they are; young men for what they promise to be.", false, true, english, johannWolfgangvonGoethe));
//        quotes.add(new Quote("Knowing is not enough; we must apply. Willing is not enough; we must do.", false, true, english, johannWolfgangvonGoethe));
//        quotes.add(new Quote("The soul that sees beauty may sometimes walk alone.", false, true, english, johannWolfgangvonGoethe));
//
//        Author kanyeWest = getAuthor(authors, "Kanye West");
//        quotes.add(new Quote("George Bush doesn't care about black people.", false, true, english, kanyeWest));
//        quotes.add(new Quote("Keep your nose out the sky, keep your heart to god, and keep your face to the raising sun.", false, true, english, kanyeWest));
//        quotes.add(new Quote("I refuse to accept other people's ideas of happiness for me. As if there's a 'one size fits all' standard for happiness.", false, true, english, kanyeWest));
//        quotes.add(new Quote("I think I do myself a disservice by comparing myself to Steve Jobs and Walt Disney and human beings that we've seen before. It should be more like Willy Wonka... and welcome to my chocolate factory.", false, true, english, kanyeWest));
//        quotes.add(new Quote("I don't know what's better gettin' laid or gettin' paid.", false, true, english, kanyeWest));
//        quotes.add(new Quote("I am Warhol. I am the No. 1 most impactful artist of our generation. I am Shakespeare in the flesh.", false, true, english, kanyeWest));
//        quotes.add(new Quote("Nothing in life is promised except death.", false, true, english, kanyeWest));
//        quotes.add(new Quote("Would you believe in what you believe in if you were the only one who believed it?", false, true, english, kanyeWest));
//        quotes.add(new Quote("I feel like I'm too busy writing history to read it.", false, true, english, kanyeWest));
//        quotes.add(new Quote("If you have the opportunity to play this game of life you need to appreciate every moment. a lot of people don't appreciate the moment until it's passed.", false, true, english, kanyeWest));
//
//        Author karlMarx = getAuthor(authors, "Karl Marx");
//        quotes.add(new Quote("Religion is the sigh of the oppressed creature, the heart of a heartless world, and the soul of soulless conditions. It is the opium of the people.", false, true, english, karlMarx));
//        quotes.add(new Quote("Workers of the world unite; you have nothing to lose but your chains.C", false, true, english, karlMarx));
//        quotes.add(new Quote("The theory of Communism may be summed up in one sentence: Abolish all private property.", false, true, english, karlMarx));
//        quotes.add(new Quote("Capitalist production, therefore, develops technology, and the combining together of various processes into a social whole, only by sapping the original sources of all wealth - the soil and the labourer.", false, true, english, karlMarx));
//        quotes.add(new Quote("Social progress can be measured by the social position of the female sex.", false, true, english, karlMarx));
//        quotes.add(new Quote("Democracy is the road to socialism.", false, true, english, karlMarx));
//        quotes.add(new Quote("The production of too many useful things results in too many useless people.", false, true, english, karlMarx));
//        quotes.add(new Quote("The history of all previous societies has been the history of class struggles.", false, true, english, karlMarx));
//        quotes.add(new Quote("Let the ruling classes tremble at a communist revolution. The proletarians have nothing to lose but their chains. They have a world to win. Workingmen of all countries, unite!", false, true, english, karlMarx));
//        quotes.add(new Quote("History repeats itself, first as tragedy, second as farce.", false, true, english, karlMarx));
//
//        Author kendrickLamar = getAuthor(authors, "Kendrick Lamar");
//        quotes.add(new Quote("My moms always told me, 'How long you gonna play the victim?' I can say I'm mad and I hate everything, but nothing really changes until I change myself.", false, true, english, kendrickLamar));
//        quotes.add(new Quote("I learned, when I look in the mirror and tell my story, that I should be myself and not peep whatever everybody is doing.", false, true, english, kendrickLamar));
//        quotes.add(new Quote("If I'm gonna tell a real story, I'm gonna start with my name.", false, true, english, kendrickLamar));
//        quotes.add(new Quote("My whole thing is to inspire, to better people, to better myself forever in this thing that we call rap, this thing that we call hip hop.", false, true, english, kendrickLamar));
//        quotes.add(new Quote("I look at where I'm at today and realize that most of my success is owed to the mentors that was in my life.", false, true, english, kendrickLamar));
//        quotes.add(new Quote("People have to go through trials and tribulations to get where they at. Do your thing - continue to rock it - because obviously, God wants you here.", false, true, english, kendrickLamar));
//        quotes.add(new Quote("It's a great, great experience to finally get the reception that you know you rightfully deserve.", false, true, english, kendrickLamar));
//        quotes.add(new Quote("Since day one, since the first time I touched the pen, I wanted to be the best at what I do.", false, true, english, kendrickLamar));
//        quotes.add(new Quote("People are used to music that justifies street culture but something that's not touched on is why these kids act the way they act, live the way they live.", false, true, english, kendrickLamar));
//        quotes.add(new Quote("My word will never be as strong as God's word. All I am is just a vessel, doing His work.", false, true, english, kendrickLamar));
//
//        Author keanuReeves = getAuthor(authors, "Keanu Reeves");
//        quotes.add(new Quote("Falling in love and having a relationship are two different things.", false, true, english, keanuReeves));
//        quotes.add(new Quote("Grief changes shape, but it never ends.", false, true, english, keanuReeves));
//        quotes.add(new Quote("I am not handsome or sexy. Of course, it's not like I am hopeless.", false, true, english, keanuReeves));
//        quotes.add(new Quote("On a good night, I get underwear, bras, and hotel-room keys thrown onstage... You start to think that you're Tom Jones.", false, true, english, keanuReeves));
//        quotes.add(new Quote("The simple act of paying attention can take you a long way.", false, true, english, keanuReeves));
//        quotes.add(new Quote("Kissing someone is pretty intimate, actually very intimate, and your heart always kind of skips a beat before you do that.", false, true, english, keanuReeves));
//        quotes.add(new Quote("It's the journey of self, I guess. You start with this kind of loner, outside guy, which a lot of people can relate to, and he goes out into the world.", false, true, english, keanuReeves));
//        quotes.add(new Quote("Because we're actors we can pretend and fake it, but I'd rather the intimate investment was authentic.", false, true, english, keanuReeves));
//        quotes.add(new Quote("My name can't be that tough to pronounce!", false, true, english, keanuReeves));
//        quotes.add(new Quote("Energy can't be created or destroyed, and energy flows. It must be in a direction, with some kind of internal, emotive, spiritual direction. It must have some effect somewhere.", false, true, english, keanuReeves));
//
//        Author karlPilkington = getAuthor(authors, "Karl Pilkington");
//        quotes.add(new Quote("I'm not that lazy, but I don't need that much money. I lead a fairly simple life.", false, true, english, karlPilkington));
//        quotes.add(new Quote("I'd rather live in a cave with a view of a palace than live in a palace with a view of a cave.", false, true, english, karlPilkington));
//        quotes.add(new Quote("Being honest with you, it's not the 'great' wall of China. It's an all right wall. It's the 'All Right Wall of China.'", false, true, english, karlPilkington));
//        quotes.add(new Quote("I've never understood the 'things to do before you die' idea. If I was ill, I'd be in no mood to have a swim with a dolphin.", false, true, english, karlPilkington));
//        quotes.add(new Quote("If you sit in a bath of pineapple chunks, it can kill you. That's well documented.", false, true, english, karlPilkington));
//        quotes.add(new Quote("That's the problem with having a bald head. It exaggerates the shape.", false, true, english, karlPilkington));
//        quotes.add(new Quote("People who live in a glass house have to answer the door.", false, true, english, karlPilkington));
//        quotes.add(new Quote("People always tell me I'm going to regret not having kids. But what if I have one and then I regret having it? Has anyone thought of that option?", false, true, english, karlPilkington));
//        quotes.add(new Quote("A slug is always on its own. It's a lonely insect.", false, true, english, karlPilkington));
//        quotes.add(new Quote("Everyone is living for everyone else now. They're doing stuff so they can tell other people about it. I don't get all that social media stuff, I've always got other things I want to do - odd jobs around the house. No one wants to hear about that.", false, true, english, karlPilkington));
//
//        Author laoTzu = getAuthor(authors, "Lao Tzu");
//        quotes.add(new Quote("Being deeply loved by someone gives you strength, while loving someone deeply gives you courage.", false, true, english, laoTzu));
//        quotes.add(new Quote("Do the difficult things while they are easy and do the great things while they are small. A journey of a thousand miles must begin with a single step.", false, true, english, laoTzu));
//        quotes.add(new Quote("The journey of a thousand miles begins with one step.", false, true, english, laoTzu));
//        quotes.add(new Quote("Kindness in words creates confidence. Kindness in thinking creates profoundness. Kindness in giving creates love.", false, true, english, laoTzu));
//        quotes.add(new Quote("Life is a series of natural and spontaneous changes. Don't resist them - that only creates sorrow. Let reality be reality. Let things flow naturally forward in whatever way they like.", false, true, english, laoTzu));
//        quotes.add(new Quote("A good traveler has no fixed plans, and is not intent on arriving.", false, true, english, laoTzu));
//        quotes.add(new Quote("Silence is a source of great strength.", false, true, english, laoTzu));
//        quotes.add(new Quote("When the best leader's work is done the people say, 'We did it ourselves.'", false, true, english, laoTzu));
//        quotes.add(new Quote("Nothing is softer or more flexible than water, yet nothing can resist it.", false, true, english, laoTzu));
//        quotes.add(new Quote("Music in the soul can be heard by the universe.", false, true, english, laoTzu));
//
//        Author leBronJames = getAuthor(authors, "LeBron James");
//        quotes.add(new Quote("You can't be afraid to fail. It's the only way you succeed - you're not gonna succeed all the time, and I know that.", false, true, english, leBronJames));
//        quotes.add(new Quote("My mom and I have always been there for each other. We had some tough times, but she was always there for me.", false, true, english, leBronJames));
//        quotes.add(new Quote("I have short goals - to get better every day, to help my teammates every day - but my only ultimate goal is to win an NBA championship. It's all that matters. I dream about it. I dream about it all the time, how it would look, how it would feel. It would be so amazing.", false, true, english, leBronJames));
//        quotes.add(new Quote("But sports carried me away from being in a gang, or being associated with drugs. Sports was my way out.", false, true, english, leBronJames));
//        quotes.add(new Quote("I think, team first. It allows me to succeed, it allows my team to succeed.", false, true, english, leBronJames));
//        quotes.add(new Quote("Every night on the court I give my all, and if I'm not giving 100 percent, I criticize myself.", false, true, english, leBronJames));
//        quotes.add(new Quote("Basketball is my passion, I love it. But my family and friends mean everything to me. That's what's important. I need my phone so I can keep in contact with them at all times.", false, true, english, leBronJames));
//        quotes.add(new Quote("You know, God gave me a gift to do other things besides play the game of basketball.", false, true, english, leBronJames));
//        quotes.add(new Quote("I've always been an unselfish guy, and that's the only way I know how to play on the court and I try to play to the maximum of my ability - not only for myself but for my teammates.", false, true, english, leBronJames));
//
//        Author leoTolstoy = getAuthor(authors, "Leo Tolstoy");
//        quotes.add(new Quote("All, everything that I understand, I understand only because I love.", false, true, english, leoTolstoy));
//        quotes.add(new Quote("Art is not a handicraft, it is the transmission of feeling the artist has experienced.", false, true, english, leoTolstoy));
//        quotes.add(new Quote("One of the first conditions of happiness is that the link between Man and Nature shall not be broken.", false, true, english, leoTolstoy));
//        quotes.add(new Quote("Truth, like gold, is to be obtained not by its growth, but by washing away from it all that is not gold.", false, true, english, leoTolstoy));
//        quotes.add(new Quote("There is no greatness where there is no simplicity, goodness and truth.", false, true, english, leoTolstoy));
//        quotes.add(new Quote("If you want to be happy, be.", false, true, english, leoTolstoy));
//        quotes.add(new Quote("An arrogant person considers himself perfect. This is the chief harm of arrogance. It interferes with a person's main task in life - becoming a better person.", false, true, english, leoTolstoy));
//        quotes.add(new Quote("The sole meaning of life is to serve humanity.", false, true, english, leoTolstoy));
//        quotes.add(new Quote("Everyone thinks of changing the world, but no one thinks of changing himself.", false, true, english, leoTolstoy));
//        quotes.add(new Quote("The two most powerful warriors are patience and time.", false, true, english, leoTolstoy));
//
//        Author leonardodaVinci = getAuthor(authors, "Leonardo da Vinci");
//        quotes.add(new Quote("It had long since come to my attention that people of accomplishment rarely sat back and let things happen to them. They went out and happened to things. Leonardo da Vinci", false, true, english, leonardodaVinci));
//        quotes.add(new Quote("Nothing strengthens authority so much as silence.", false, true, english, leonardodaVinci));
//        quotes.add(new Quote("As a well-spent day brings happy sleep, so a life well spent brings happy death.", false, true, english, leonardodaVinci));
//        quotes.add(new Quote("The noblest pleasure is the joy of understanding.", false, true, english, leonardodaVinci));
//        quotes.add(new Quote("While I thought that I was learning how to live, I have been learning how to die.", false, true, english, leonardodaVinci));
//        quotes.add(new Quote("Learning never exhausts the mind.", false, true, english, leonardodaVinci));
//        quotes.add(new Quote("Simplicity is the ultimate sophistication.", false, true, english, leonardodaVinci));
//        quotes.add(new Quote("Water is the driving force of all nature.", false, true, english, leonardodaVinci));
//        quotes.add(new Quote("I love those who can smile in trouble, who can gather strength from distress, and grow brave by reflection. 'Tis the business of little minds to shrink, but they whose heart is firm, and whose conscience approves their conduct, will pursue their principles unto death.", false, true, english, leonardodaVinci));
//        quotes.add(new Quote("Tears come from the heart and not from the brain.", false, true, english, leonardodaVinci));
//
//        Author lilUziVert = getAuthor(authors, "Lil Uzi Vert");
//        quotes.add(new Quote("If you're you, it doesn't matter if you're the most boring person in the world: someone will like you. You're not trying to be anyone else.", false, true, english, lilUziVert));
//        quotes.add(new Quote("I'm just doing me, and to me, that's what got me this far.", false, true, english, lilUziVert));
//        quotes.add(new Quote("There are always people who are into the old way of doing things. I don't think it's a bad thing necessarily, but things change - nothing stays the same. If you can stay true to yourself, you're always going to be legendary.", false, true, english, lilUziVert));
//        quotes.add(new Quote("'Vert' is, like, straight to the top, like a vertical leap.", false, true, english, lilUziVert));
//        quotes.add(new Quote("I stopped thinking; I just go out, and everything is in the moment. I just stop thinking and start moving - you just gotta be you.", false, true, english, lilUziVert));
//        quotes.add(new Quote("Some guy was just like, 'You rap fast, man. Like a little machine gun,' and from that moment, I was Lil Uzi.", false, true, english, lilUziVert));
//        quotes.add(new Quote("I got different moods. Like, if I'm in a good mood, I listen to 'Up' by Thug. If I'm not in a good mood, I'll listen to 'King TROUP' - that's a real emotional one.", false, true, english, lilUziVert));
//        quotes.add(new Quote("I like to look cool and dress nice. Lil Uzi is a rock star. Just know that.", false, true, english, lilUziVert));
//        quotes.add(new Quote("Don't get me wrong: school is good and all, but school is way too slow for me. Like, super slow. So I didn't want to go. I wanted to learn on my own with real life experiences.", false, true, english, lilUziVert));
//        quotes.add(new Quote("The type of music I make, it's not just straight-up rapping. There's emotion in it. That's why people feel each song differently. I get all my vibes from rock music, you know? All my melodies and all that.", false, true, english, lilUziVert));
//
//        Author malcolmX = getAuthor(authors, "Malcolm X");
//        quotes.add(new Quote("Education is the passport to the future, for tomorrow belongs to those who prepare for it today.", false, true, english, malcolmX));
//        quotes.add(new Quote("The media's the most powerful entity on earth. They have the power to make the innocent guilty and to make the guilty innocent, and that's power. Because they control the minds of the masses.", false, true, english, malcolmX));
//        quotes.add(new Quote("There is no better than adversity. Every defeat, every heartbreak, every loss, contains its own seed, its own lesson on how to improve your performance the next time.", false, true, english, malcolmX));
//        quotes.add(new Quote("You're not supposed to be so blind with patriotism that you can't face reality. Wrong is wrong, no matter who says it.", false, true, english, malcolmX));
//        quotes.add(new Quote("Be peaceful, be courteous, obey the law, respect everyone; but if someone puts his hand on you, send him to the cemetery.", false, true, english, malcolmX));
//        quotes.add(new Quote("I'm for truth, no matter who tells it. I'm for justice, no matter who it's for or against.", false, true, english, malcolmX));
//        quotes.add(new Quote("The future belongs to those who prepare for it today.", false, true, english, malcolmX));
//        quotes.add(new Quote("A man who stands for nothing will fall for anything.", false, true, english, malcolmX));
//        quotes.add(new Quote("I don't even call it violence when it's in self defense; I call it intelligence.", false, true, english, malcolmX));
//        quotes.add(new Quote("I have more respect for a man who lets me know where he stands, even if he's wrong, than the one who comes up like an angel and is nothing but a devil.", false, true, english, malcolmX));
//
//        Author motherTeresa = getAuthor(authors, "Mother Teresa");
//        quotes.add(new Quote("Let us always meet each other with smile, for the smile is the beginning of love.", false, true, english, motherTeresa));
//        quotes.add(new Quote("Peace begins with a smile.", false, true, english, motherTeresa));
//        quotes.add(new Quote("We need to find God, and he cannot be found in noise and restlessness. God is the friend of silence. See how nature - trees, flowers, grass- grows in silence; see the stars, the moon and the sun, how they move in silence... We need silence to be able to touch souls.", false, true, english, motherTeresa));
//        quotes.add(new Quote("We shall never know all the good that a simple smile can do.", false, true, english, motherTeresa));
//        quotes.add(new Quote("Be faithful in small things because it is in them that your strength lies.", false, true, english, motherTeresa));
//        quotes.add(new Quote("Spread love everywhere you go. Let no one ever come to you without leaving happier.", false, true, english, motherTeresa));
//        quotes.add(new Quote("Loneliness and the feeling of being unwanted is the most terrible poverty.", false, true, english, motherTeresa));
//        quotes.add(new Quote("If we have no peace, it is because we have forgotten that we belong to each other.", false, true, english, motherTeresa));
//        quotes.add(new Quote("We think sometimes that poverty is only being hungry, naked and homeless. The poverty of being unwanted, unloved and uncared for is the greatest poverty. We must start in our own homes to remedy this kind of poverty.", false, true, english, motherTeresa));
//        quotes.add(new Quote("Joy is prayer; joy is strength: joy is love; joy is a net of love by which you can catch souls.", false, true, english, motherTeresa));
//
//        Author mahatmaGandhi = getAuthor(authors, "Mahatma Gandhi");
//        quotes.add(new Quote("A nation's culture resides in the hearts and in the soul of its people.", false, true, english, mahatmaGandhi));
//        quotes.add(new Quote("An eye for an eye only ends up making the whole world blind.", false, true, english, mahatmaGandhi));
//        quotes.add(new Quote("Happiness is when what you think, what you say, and what you do are in harmony.", false, true, english, mahatmaGandhi));
//        quotes.add(new Quote("Prayer is the key of the morning and the bolt of the evening. Mahatma Gandhi", false, true, english, mahatmaGandhi));
//        quotes.add(new Quote("The best way to find yourself is to lose yourself in the service of others.", false, true, english, mahatmaGandhi));
//        quotes.add(new Quote("First they ignore you, then they laugh at you, then they fight you, then you win.", false, true, english, mahatmaGandhi));
//        quotes.add(new Quote("Live as if you were to die tomorrow. Learn as if you were to live forever.", false, true, english, mahatmaGandhi));
//        quotes.add(new Quote("You must be the change you wish to see in the world.", false, true, english, mahatmaGandhi));
//        quotes.add(new Quote("The weak can never forgive. Forgiveness is the attribute of the strong.", false, true, english, mahatmaGandhi));
//        quotes.add(new Quote("Where there is love there is life.", false, true, english, mahatmaGandhi));
//
//        Author marcusTulliusCicero = getAuthor(authors, "Marcus Tullius Cicero");
//        quotes.add(new Quote("It is not by muscle, speed, or physical dexterity that great things are achieved, but by reflection, force of character, and judgment.", false, true, english, marcusTulliusCicero));
//        quotes.add(new Quote("What then is freedom? The power to live as one wishes.", false, true, english, marcusTulliusCicero));
//        quotes.add(new Quote("The countenance is the portrait of the soul, and the eyes mark its intentions.", false, true, english, marcusTulliusCicero));
//        quotes.add(new Quote("The higher we are placed, the more humbly we should walk.", false, true, english, marcusTulliusCicero));
//        quotes.add(new Quote("Any man can make mistakes, but only an idiot persists in his error.", false, true, english, marcusTulliusCicero));
//        quotes.add(new Quote("If you have a garden and a library, you have everything you need.", false, true, english, marcusTulliusCicero));
//        quotes.add(new Quote("Silence is one of the great arts of conversation", false, true, english, marcusTulliusCicero));
//        quotes.add(new Quote("The safety of the people shall be the highest law", false, true, english, marcusTulliusCicero));
//        quotes.add(new Quote("Gratitude is not only the greatest of virtues, but the parent of all the others.", false, true, english, marcusTulliusCicero));
//        quotes.add(new Quote("The life of the dead is placed in the memory of the living.", false, true, english, marcusTulliusCicero));
//
//        Author marilynMonroe = getAuthor(authors, "Marilyn Monroe");
//        quotes.add(new Quote("It's better to be unhappy alone than unhappy with someone - so far.", false, true, english, marilynMonroe));
//        quotes.add(new Quote("Success makes so many people hate you. I wish it wasn't that way. It would be wonderful to enjoy success without seeing envy in the eyes of those around you.", false, true, english, marilynMonroe));
//        quotes.add(new Quote("Experts on romance say for a happy marriage there has to be more than a passionate love. For a lasting union, they insist, there must be a genuine liking for each other. Which, in my book, is a good definition for friendship.", false, true, english, marilynMonroe));
//        quotes.add(new Quote("We are all of us stars, and we deserve to twinkle.", false, true, english, marilynMonroe));
//        quotes.add(new Quote("I restore myself when I'm alone.", false, true, english, marilynMonroe));
//        quotes.add(new Quote("Give a girl the right shoes, and she can conquer the world.", false, true, english, marilynMonroe));
//        quotes.add(new Quote("Imperfection is beauty, madness is genius and it's better to be absolutely ridiculous than absolutely boring.", false, true, english, marilynMonroe));
//        quotes.add(new Quote("I am good, but not an angel. I do sin, but I am not the devil. I am just a small girl in a big world trying to find someone to love.", false, true, english, marilynMonroe));
//        quotes.add(new Quote("I've never dropped anyone I believed in.", false, true, english, marilynMonroe));
//        quotes.add(new Quote("I'm selfish, impatient, and a little insecure. I make mistakes, I'm out of control, and at times hard to handle. But if you can't handle me at my worst, then you sure as hell don't deserve me at my best.", false, true, english, marilynMonroe));
//
//        Author napoleonBonaparte = getAuthor(authors, "Napoleon Bonaparte");
//        quotes.add(new Quote("You must not fight too often with one enemy, or you will teach him all your art of war.", false, true, english, napoleonBonaparte));
//        quotes.add(new Quote("A leader is a dealer in hope.", false, true, english, napoleonBonaparte));
//        quotes.add(new Quote("Death is nothing, but to live defeated and inglorious is to die daily.", false, true, english, napoleonBonaparte));
//        quotes.add(new Quote("Never interrupt your enemy when he is making a mistake", false, true, english, napoleonBonaparte));
//        quotes.add(new Quote("Women are nothing but machines for producing children.", false, true, english, napoleonBonaparte));
//        quotes.add(new Quote("Impossible is a word to be found only in the dictionary of fools.", false, true, english, napoleonBonaparte));
//        quotes.add(new Quote("Great ambition is the passion of a great character. Those endowed with it may perform very good or very bad acts. All depends on the principles which direct them.", false, true, english, napoleonBonaparte));
//        quotes.add(new Quote("A soldier will fight long and hard for a bit of colored ribbon.", false, true, english, napoleonBonaparte));
//        quotes.add(new Quote("If you want a thing done well, do it yourself.", false, true, english, napoleonBonaparte));
//        quotes.add(new Quote("In politics stupidity is not a handicap.", false, true, english, napoleonBonaparte));
//
//        Author napoleonHill = getAuthor(authors, "Napoleon Hill");
//        quotes.add(new Quote("First comes thought; then organization of that thought, into ideas and plans; then transformation of those plans into reality. The beginning, as you will observe, is in your imagination.", false, true, english, napoleonHill));
//        quotes.add(new Quote("If you cannot do great things, do small things in a great way.", false, true, english, napoleonHill));
//        quotes.add(new Quote("Effort only fully releases its reward after a person refuses to quit.", false, true, english, napoleonHill));
//        quotes.add(new Quote("Victory is always possible for the person who refuses to stop fighting.", false, true, english, napoleonHill));
//        quotes.add(new Quote("The starting point of all achievement is desire.", false, true, english, napoleonHill));
//        quotes.add(new Quote("Your big opportunity may be right where you are now.", false, true, english, napoleonHill));
//        quotes.add(new Quote("Education comes from within; you get it by struggle and effort and thought.", false, true, english, napoleonHill));
//        quotes.add(new Quote("Great achievement is usually born of great sacrifice, and is never the result of selfishness.", false, true, english, napoleonHill));
//        quotes.add(new Quote("Patience, persistence and perspiration make an unbeatable combination for success.", false, true, english, napoleonHill));
//        quotes.add(new Quote("Strength and growth come only through continuous effort and struggle.", false, true, english, napoleonHill));
//
//        Author nas = getAuthor(authors, "Nas");
//        quotes.add(new Quote("I want to have fun. It's a beautiful life. You learn, you win, you lose, but you get up.", false, true, english, nas));
//        quotes.add(new Quote("Once you make it to your point of making it, you'll appreciate the struggle.", false, true, english, nas));
//        quotes.add(new Quote("I just enjoy life now. I just enjoy every morning I get to wake up.", false, true, english, nas));
//        quotes.add(new Quote("The flaws, the mistakes I make - that's the real me.", false, true, english, nas));
//        quotes.add(new Quote("Your look reflects what's happening in your mind. You gotta have some swag to you.", false, true, english, nas));
//        quotes.add(new Quote("You can't please everybody. You'd be crazy if you're trying to. So take some time out to do some things for yourself.", false, true, english, nas));
//        quotes.add(new Quote("With age comes common sense and wisdom.", false, true, english, nas));
//        quotes.add(new Quote("Working with great people makes you great; you learn a lot and it also gives you the experience and confidence to move on with your own career.", false, true, english, nas));
//        quotes.add(new Quote("I don't want any title. I just say what I say, and hopefully somebody gets it, man. I'm not perfect, and I'm just here and trying to make a dollar, and being real at the same time, you know?", false, true, english, nas));
//        quotes.add(new Quote("When you're a teenager, you want to meet a lot of girls - you want to get the most girls. You don't know anything about respect; you don't know anything about being faithful and loyal to your girlfriend.", false, true, english, nas));
//
//        Author neilArmstrong = getAuthor(authors, "Neil Armstrong");
//        quotes.add(new Quote("Well, I think we tried very hard not to be overconfident, because when you get overconfident, that's when something snaps up and bites you.", false, true, english, neilArmstrong));
//        quotes.add(new Quote("Houston, Tranquillity Base here. The Eagle has landed.", false, true, english, neilArmstrong));
//        quotes.add(new Quote("It suddenly struck me that that tiny pea, pretty and blue, was the Earth. I put up my thumb and shut one eye, and my thumb blotted out the planet Earth. I didn't feel like a giant. I felt very, very small.", false, true, english, neilArmstrong));
//        quotes.add(new Quote("The Eagle has landed.", false, true, english, neilArmstrong));
//        quotes.add(new Quote("The important achievement of Apollo was demonstrating that humanity is not forever chained to this planet and our visions go rather further than that and our opportunities are unlimited.", false, true, english, neilArmstrong));
//        quotes.add(new Quote("I think we're going to the moon because it's in the nature of the human being to face challenges. It's by the nature of his deep inner soul... we're required to do these things just as salmon swim upstream.", false, true, english, neilArmstrong));
//        quotes.add(new Quote("I believe every human has a finite number of heartbeats. I don't intend to waste any of mine.", false, true, english, neilArmstrong));
//        quotes.add(new Quote("Research is creating new knowledge.", false, true, english, neilArmstrong));
//        quotes.add(new Quote("That's one small step for a man, one giant leap for mankind.", false, true, english, neilArmstrong));
//        quotes.add(new Quote("Mystery creates wonder and wonder is the basis of man's desire to understand.", false, true, english, neilArmstrong));
//
//        Author neildeGrasseTyson = getAuthor(authors, "Neil deGrasse Tyson");
//        quotes.add(new Quote("We are part of this universe; we are in this universe, but perhaps more important than both of those facts, is that the universe is in us.", false, true, english, neildeGrasseTyson));
//        quotes.add(new Quote("Ever since the Industrial Revolution, investments in science and technology have proved to be reliable engines of economic growth. If homegrown interest in those fields is not regenerated soon, the comfortable lifestyle to which Americans have become accustomed will draw to a rapid close.", false, true, english, neildeGrasseTyson));
//        quotes.add(new Quote("Perhaps we've never been visited by aliens because they have looked upon Earth and decided there's no sign of intelligent life.", false, true, english, neildeGrasseTyson));
//        quotes.add(new Quote("Rational thoughts never drive people's creativity the way emotions do.", false, true, english, neildeGrasseTyson));
//        quotes.add(new Quote("Once you have an innovation culture, even those who are not scientists or engineers - poets, actors, journalists - they, as communities, embrace the meaning of what it is to be scientifically literate. They embrace the concept of an innovation culture. They vote in ways that promote it. They don't fight science and they don't fight technology.", false, true, english, neildeGrasseTyson));
//        quotes.add(new Quote("There is no greater education than one that is self-driven.", false, true, english, neildeGrasseTyson));
//        quotes.add(new Quote("If your ego starts out, 'I am important, I am big, I am special,' you're in for some disappointments when you look around at what we've discovered about the universe. No, you're not big. No, you're not. You're small in time and in space. And you have this frail vessel called the human body that's limited on Earth.", false, true, english, neildeGrasseTyson));
//        quotes.add(new Quote("Even with all our technology and the inventions that make modern life so much easier than it once was, it takes just one big natural disaster to wipe all that away and remind us that, here on Earth, we're still at the mercy of nature.", false, true, english, neildeGrasseTyson));
//        quotes.add(new Quote("The Universe is under no obligation to make sense to you.", false, true, english, neildeGrasseTyson));
//        quotes.add(new Quote("Humans aren't as good as we should be in our capacity to empathize with feelings and thoughts of others, be they humans or other animals on Earth. So maybe part of our formal education should be training in empathy. Imagine how different the world would be if, in fact, that were 'reading, writing, arithmetic, empathy.'", false, true, english, neildeGrasseTyson));
//
//        Author oscarWilde = getAuthor(authors, "Oscar Wilde");
//        quotes.add(new Quote("Keep love in your heart. A life without it is like a sunless garden when the flowers are dead.", false, true, english, oscarWilde));
//        quotes.add(new Quote("If you are not too long, I will wait here for you all my life.", false, true, english, oscarWilde));
//        quotes.add(new Quote("Men always want to be a woman's first love - women like to be a man's last romance.", false, true, english, oscarWilde));
//        quotes.add(new Quote("Women are made to be loved, not understood.", false, true, english, oscarWilde));
//        quotes.add(new Quote("True friends stab you in the front.", false, true, english, oscarWilde));
//        quotes.add(new Quote("Experience is simply the name we give our mistakes.", false, true, english, oscarWilde));
//        quotes.add(new Quote("Success is a science; if you have the conditions, you get the result.", false, true, english, oscarWilde));
//        quotes.add(new Quote("I can resist everything except temptation.", false, true, english, oscarWilde));
//        quotes.add(new Quote("Memory... is the diary that we all carry about with us.", false, true, english, oscarWilde));
//        quotes.add(new Quote("A dreamer is one who can only find his way by moonlight, and his punishment is that he sees the dawn before the rest of the world.", false, true, english, oscarWilde));
//
//        Author OJSimpson = getAuthor(authors, "O. J. Simpson");
//        quotes.add(new Quote("The day you take complete responsibility for yourself, the day you stop making any excuses, that's the day you start to the top.", false, true, english, OJSimpson));
//        quotes.add(new Quote("I didn't beat her. I just pushed her out of bed.", false, true, english, OJSimpson));
//        quotes.add(new Quote("My NFL pension can barely pay my son's tuition. You know, it's very little money.", false, true, english, OJSimpson));
//        quotes.add(new Quote("I really have reached a point where I can write a book about all of this.", false, true, english, OJSimpson));
//        quotes.add(new Quote("I believe in the jury system.", false, true, english, OJSimpson));
//        quotes.add(new Quote("I think I've been a great citizen.", false, true, english, OJSimpson));
//        quotes.add(new Quote("I've done my time. I've done it as well and as respectfully as I think anyone can.", false, true, english, OJSimpson));
//        quotes.add(new Quote("Nicole will come up in conversations where it's in a part of the conversation. Or we may be somewhere and I would tell some story about their mother and I. You know, we always honor her birthday.", false, true, english, OJSimpson));
//        quotes.add(new Quote("I could not tell you the date of my mother's death. I could not tell you the date of my dad's death. These are not dates that I find significant.", false, true, english, OJSimpson));
//        quotes.add(new Quote("I had some problems with fidelity in my life but pretty much got along with everybody.", false, true, english, OJSimpson));
//
//        Author OSheaJackson = getAuthor(authors, "O'Shea Jackson");
//        quotes.add(new Quote("Have confidence in everything. No matter what it is that you're doing, know that you can do it better than anyone.", false, true, english, OSheaJackson));
//        quotes.add(new Quote("The stress and turmoil that my father had to go through at a young age to make sure that I didn't have the same trials and tribulations, I couldn't be more grateful.", false, true, english, OSheaJackson));
//        quotes.add(new Quote("My parents wouldn't have sent me out into the world with wool over my eyes. You have to be aware, or you'll be swallowed.", false, true, english, OSheaJackson));
//        quotes.add(new Quote("It's always extremities when you are a young black male dealing with law enforcement.", false, true, english, OSheaJackson));
//        quotes.add(new Quote("Being on set with my dad - that's so cool. People always ask me if that made me nervous, but it's the same element when you're a kid - when your parents come in the auditorium for those school performances. It calms you.", false, true, english, OSheaJackson));
//        quotes.add(new Quote("It's always extremities when you are a young black male dealing with law enforcement.", false, true, english, OSheaJackson));
//        quotes.add(new Quote("Being on set with my dad - that's so cool. People always ask me if that made me nervous, but it's the same element when you're a kid - when your parents come in the auditorium for those school performances. It calms you.", false, true, english, OSheaJackson));
//        quotes.add(new Quote("This entire cast, N.W.A, was an all-star group, and I really feel like people are going to look at 'Straight Outta Compton' years from now like this was an all-star cast.", false, true, english, OSheaJackson));
//        quotes.add(new Quote("When, you know, when you're playing basketball, you have to have confidence in your moves if they're gonna work.", false, true, english, OSheaJackson));
//        quotes.add(new Quote("Music is always there, but if you're asking me my first love, it's film.", false, true, english, OSheaJackson));
//
//        Author OWinstonLink = getAuthor(authors, "O. Winston Link");
//        quotes.add(new Quote("I never expected that. I didn't aim for that. All I wanted was to get some nice pictures of trains at night.", false, true, english, OWinstonLink));
//        quotes.add(new Quote("You can show me some stick ice cream and I can tell you if it's good or not just looking at it.", false, true, english, OWinstonLink));
//        quotes.add(new Quote("I was strong and healthy and I was enjoying what I was doing.", false, true, english, OWinstonLink));
//        quotes.add(new Quote("I was one man and I tackled a big railroad. I did the best I could.", false, true, english, OWinstonLink));
//
//        Author obiageliEzekwesili = getAuthor(authors, "Obiageli Ezekwesili");
//        quotes.add(new Quote("Social media is simply a tool that facilitates actions.", false, true, english, obiageliEzekwesili));
//        quotes.add(new Quote("My mum was a quintessential businesswoman. She taught me problem-solving. She can solve any problem.", false, true, english, obiageliEzekwesili));
//        quotes.add(new Quote("I would like to see a lot of people more involved in practical solutions to practical problems. Women have got to the point where we can turn the world upside down.", false, true, english, obiageliEzekwesili));
//        quotes.add(new Quote("In terms of competitiveness of new global environment, Kenya will have absolutely no choice but to tackle the most important constraint to its development: it has been corruption.", false, true, english, obiageliEzekwesili));
//        quotes.add(new Quote("There's absolutely nothing that the God I believe in cannot do.", false, true, english, obiageliEzekwesili));
//        quotes.add(new Quote("I have always battled injustice. As a child, I used to fight on the side of my friends when boys terrorized them.", false, true, english, obiageliEzekwesili));
//        quotes.add(new Quote("How can the cost of education be the cost of life? It is unacceptable; it is reprehensible that we have allowed it to fester.", false, true, english, obiageliEzekwesili));
//        quotes.add(new Quote("How can the cost of education be the cost of life? It is unacceptable; it is reprehensible that we have allowed it to fester.", false, true, english, obiageliEzekwesili));
//        quotes.add(new Quote("Do our children now have to choose between getting an education and dying? Some of us cannot move on and accept that kind of society.", false, true, english, obiageliEzekwesili));
//        quotes.add(new Quote("There's absolutely nothing that the God I believe in cannot do.", false, true, english, obiageliEzekwesili));
//
//        Author plato = getAuthor(authors, "Plato");
//        quotes.add(new Quote("Wise men speak because they have something to say; Fools because they have to say something.", false, true, english, plato));
//        quotes.add(new Quote("Music is a moral law. It gives soul to the universe, wings to the mind, flight to the imagination, and charm and gaiety to life and to everything.", false, true, english, plato));
//        quotes.add(new Quote("Human behavior flows from three main sources: desire, emotion, and knowledge.", false, true, english, plato));
//        quotes.add(new Quote("We can easily forgive a child who is afraid of the dark; the real tragedy of life is when men are afraid of the light.", false, true, english, plato));
//        quotes.add(new Quote("You can discover more about a person in an hour of play than in a year of conversation.", false, true, english, plato));
//        quotes.add(new Quote("Thinking: the talking of the soul with itself.", false, true, english, plato));
//        quotes.add(new Quote("One of the penalties for refusing to participate in politics is that you end up being governed by your inferiors.", false, true, english, plato));
//        quotes.add(new Quote("Courage is knowing what not to fear.", false, true, english, plato));
//        quotes.add(new Quote("Dictatorship naturally arises out of democracy, and the most aggravated form of tyranny and slavery out of the most extreme liberty.", false, true, english, plato));
//        quotes.add(new Quote("Ignorance, the root and stem of all evil.", false, true, english, plato));
//
//        Author pabloPicasso = getAuthor(authors, "Pablo Picasso");
//        quotes.add(new Quote("Our goals can only be reached through a vehicle of a plan, in which we must fervently believe, and upon which we must vigorously act. There is no other route to success.", false, true, english, pabloPicasso));
//        quotes.add(new Quote("Action is the foundational key to all success.", false, true, english, pabloPicasso));
//        quotes.add(new Quote("Youth has no age.", false, true, english, pabloPicasso));
//        quotes.add(new Quote("Everything you can imagine is real.", false, true, english, pabloPicasso));
//        quotes.add(new Quote("Who sees the human face correctly: the photographer, the mirror, or the painter?", false, true, english, pabloPicasso));
//        quotes.add(new Quote("Colors, like features, follow the changes of the emotions.", false, true, english, pabloPicasso));
//        quotes.add(new Quote("It takes a long time to become young.", false, true, english, pabloPicasso));
//        quotes.add(new Quote("Some painters transform the sun into a yellow spot, others transform a yellow spot into the sun.", false, true, english, pabloPicasso));
//        quotes.add(new Quote("All children are artists. The problem is how to remain an artist once he grows up.", false, true, english, pabloPicasso));
//        quotes.add(new Quote("The purpose of art is washing the dust of daily life off our souls.", false, true, english, pabloPicasso));
//
//        Author patrickHenry = getAuthor(authors, "Patrick Henry");
//        quotes.add(new Quote("Is life so dear or peace so sweet as to be purchased at the price of chains and slavery? Forbid it, Almighty God! I know not what course others may take, but as for me, give me liberty, or give me death!", false, true, english, patrickHenry));
//        quotes.add(new Quote("I have but one lamp by which my feet are guided, and that is the lamp of experience.", false, true, english, patrickHenry));
//        quotes.add(new Quote("The liberties of a people never were, nor ever will be, secure, when the transactions of their rulers may be concealed from them.", false, true, english, patrickHenry));
//        quotes.add(new Quote("The distinctions between Virginians, Pennsylvanians, New Yorkers, and New Englanders are no more. I am not a Virginian but an American.", false, true, english, patrickHenry));
//        quotes.add(new Quote("When the American spirit was in its youth, the language of America was different: Liberty, sir, was the primary object.", false, true, english, patrickHenry));
//        quotes.add(new Quote("I know of no way of judging the future but by the past.", false, true, english, patrickHenry));
//        quotes.add(new Quote("Perfect freedom is as necessary to the health and vigor of commerce as it is to the health and vigor of citizenship.", false, true, english, patrickHenry));
//        quotes.add(new Quote("For my part, whatever anguish of spirit it may cost, I am willing to know the whole truth; to know the worst and provide for it.", false, true, english, patrickHenry));
//        quotes.add(new Quote("It is natural to indulge in the illusions of hope. We are apt to shut our eyes to that siren until she allures us to our death.", false, true, english, patrickHenry));
//        quotes.add(new Quote("Shall we, who have laid the proud British lion at our feet, now be afraid of his whelps?", false, true, english, patrickHenry));
//
//        Author pauloCoelho = getAuthor(authors, "Paulo Coelho");
//        quotes.add(new Quote("Waiting is painful. Forgetting is painful. But not knowing which to do is the worse kind of suffering.", false, true, english, pauloCoelho));
//        quotes.add(new Quote("No one can lie, no one can hide anything, when he looks directly into someone's eyes.", false, true, english, pauloCoelho));
//        quotes.add(new Quote("When you want something, all the universe conspires in helping you to achieve it.", false, true, english, pauloCoelho));
//        quotes.add(new Quote("When you are enthusiastic about what you do, you feel this positive energy. It's very simple.", false, true, english, pauloCoelho));
//        quotes.add(new Quote("I can control my destiny, but not my fate. Destiny means there are opportunities to turn right or left, but fate is a one-way street. I believe we all have the choice as to whether we fulfil our destiny, but our fate is sealed.", false, true, english, pauloCoelho));
//        quotes.add(new Quote("Remember your dreams and fight for them. You must know what you want from life. There is just one thing that makes your dream become impossible: the fear of failure.", false, true, english, pauloCoelho));
//        quotes.add(new Quote("Life was always a matter of waiting for the right moment to act.", false, true, english, pauloCoelho));
//        quotes.add(new Quote("Culture makes people understand each other better. And if they understand each other better in their soul, it is easier to overcome the economic and political barriers. But first they have to understand that their neighbour is, in the end, just like them, with the same problems, the same questions.", false, true, english, pauloCoelho));
//        quotes.add(new Quote("Be brave. Take risks. Nothing can substitute experience.", false, true, english, pauloCoelho));
//        quotes.add(new Quote("I have seen many storms in my life. Most storms have caught me by surprise, so I had to learn very quickly to look further and understand that I am not capable of controlling the weather, to exercise the art of patience and to respect the fury of nature.", false, true, english, pauloCoelho));
//
//        Author pele = getAuthor(authors, "Pele");
//        quotes.add(new Quote("Success is no accident. It is hard work, perseverance, learning, studying, sacrifice and most of all, love of what you are doing or learning to do.", false, true, english, pele));
//        quotes.add(new Quote("Enthusiasm is everything. It must be taut and vibrating like a guitar string.", false, true, english, pele));
//        quotes.add(new Quote("I am constantly being asked about individuals. The only way to win is as a team. Football is not about one or two or three star players.", false, true, english, pele));
//        quotes.add(new Quote("When you are young, you do a lot of stupid things.", false, true, english, pele));
//        quotes.add(new Quote("Wherever you go, there are three icons that everyone knows: Jesus Christ, Pele and Coca-Cola.", false, true, english, pele));
//        quotes.add(new Quote("If I pass away one day, I am happy because I tried to do my best. My sport allowed me to do so much because it's the biggest sport in the world.", false, true, english, pele));
//        quotes.add(new Quote("When I was a footballer, I surrounded myself with footballers. We were all friends. But in Brasilia you don't know who your friends are. It can be a dangerous place.", false, true, english, pele));
//        quotes.add(new Quote("I have to trust something that gives me power; I have to believe in something, but in my career I have a lot of moments I cannot explain with God.", false, true, english, pele));
//        quotes.add(new Quote("Everything on earth is a game. A passing thing. We all end up dead. We all end up the same, don't we?", false, true, english, pele));
//        quotes.add(new Quote("The World Cup is a very complicated tournament - six games, seven if you make it to the final - and maybe if you lose one game you're out, even if you're the best.", false, true, english, pele));
//
//        Author QoriankaKilcher = getAuthor(authors, "Q'orianka Kilcher");
//        quotes.add(new Quote("I love the power of celebrity because you can give voice to the voiceless.", false, true, english, QoriankaKilcher));
//        quotes.add(new Quote("You need people around you that care about you and are thinking about you in your best interest. And keep your mind straight.", false, true, english, QoriankaKilcher));
//        quotes.add(new Quote("I really identified with Pocahontas' struggles as a young woman trying to identify herself in a modern, changing world and trying to stay true to her culture and heritage.", false, true, english, QoriankaKilcher));
//        quotes.add(new Quote("I honestly think I was an Indian living in the time of the Trail of Tears. Something like that. Every time I read books about back then, I get so devastatingly sad, so, so... I feel such a deep connection to it.", false, true, english, QoriankaKilcher));
//        quotes.add(new Quote("My birthday is Feb. 11, and I'm both excited and not excited by it. You'll never be 15 again, and you really, really need to savor every day like it's your last.", false, true, english, QoriankaKilcher));
//        quotes.add(new Quote("Portraying Pocahontas' story well was important to me because she was a real person and these were real events in her life.", false, true, english, QoriankaKilcher));
//        quotes.add(new Quote("I just want to learn even more about my culture and about the Algonquin culture because I fell in love with Pocahontas and the Algonquin tribe.", false, true, english, QoriankaKilcher));
//        quotes.add(new Quote("Yes, I'm proud to be indigenous. I'm half-Quechua-Huachipaeri from Peru.", false, true, english, QoriankaKilcher));
//        quotes.add(new Quote("My father's Peruvian! I actually have a lot of family in Cuzco. I'm also Swiss, Alaskan, French, Spanish and Italian.", false, true, english, QoriankaKilcher));
//        quotes.add(new Quote("I feel like, as a celebrity, I have a responsibility to tell important stories.", false, true, english, QoriankaKilcher));
//
//        Author qTip = getAuthor(authors, "Q-Tip");
//        quotes.add(new Quote("If there's a 'Cruel Summer' then there's got to be a 'Cruel Winter,' right? That's all I'm saying.", false, true, english, qTip));
//        quotes.add(new Quote("I definitely move to the beat of my own drummer and man, he's not playing something anyone has heard before. It's pretty cool, though. Pretty cool.", false, true, english, qTip));
//        quotes.add(new Quote("One thing the music industry has taught me is to manage my expectations. Q-Tip.", false, true, english, qTip));
//        quotes.add(new Quote("I do try to speak of positive things. I still try to, like, present two sides of the story, and I do try to relate to life in a 360 degree and not be one-dimensional. But by all means, manage expectations.", false, true, english, qTip));
//        quotes.add(new Quote("I don't really listen to my work. If I have to DJ and I play something, I hear it. But I don't sit quietly and listen to my work; I'm always off to do the next thing.", false, true, english, qTip));
//        quotes.add(new Quote("I read the Koran and it appealed to me. At the time I was agnostic and it really breathed spiritually back into me. For me it's really a cushion; it's cool, I'm cool with it.", false, true, english, qTip));
//        quotes.add(new Quote("I sang in church, but growing up in the neighborhood, music was more of an expression of relief or entertainment.", false, true, english, qTip));
//        quotes.add(new Quote("You know, I hate to sound self-involved, but I feel like I haven't peaked yet.", false, true, english, qTip));
//        quotes.add(new Quote("I feel like Obama in a way. His idea that hope means not shrinking from a fight; it's the courage to reach for something. My music is that. Those are principles I try to embody.", false, true, english, qTip));
//        quotes.add(new Quote("In 1999, I just came out of putting out the song 'Vivrant Thing' and 'Breathe and Stop' off the 'Amplified' album. Clive Davis signed me to Arista.", false, true, english, qTip));
//
//        Author qandeelBaloch = getAuthor(authors, "Qandeel Baloch");
//        quotes.add(new Quote("Love me or hate me, both are in my favour. If you love me, I will always be in your heart, and if you hate me, I will be in your mind.", false, true, english, qandeelBaloch));
//        quotes.add(new Quote("I did a job. I completed my Matric and my Bachelors. I did a marketing job. I worked as a bus hostess. I did a lot of jobs; I struggled a lot. I got out from there. The first thing I did when I got out of Darul Aman was my Matric. Then I did my Bachelors privately; I kept doing it.", false, true, english, qandeelBaloch));
//        quotes.add(new Quote("At least international media can see how I am trying to change the typical orthodox mindset of people who don't want to come out of their shells of false beliefs and old practices.", false, true, english, qandeelBaloch));
//        quotes.add(new Quote("I don't know how many girls have felt support through my persona. I'm a girl power. So many girls tell me I'm a girl power, and yes, I am.", false, true, english, qandeelBaloch));
//        quotes.add(new Quote("I am not hungry for roles on TV or in films. I am happy being a social media sensation.", false, true, english, qandeelBaloch));
//        quotes.add(new Quote("I've fought with everyone . And now I have become so headstrong that I only do what I want.", false, true, english, qandeelBaloch));
//        quotes.add(new Quote("Nothing is good in this society. This patriarchal society is bad.", false, true, english, qandeelBaloch));
//        quotes.add(new Quote("It's time to bring a change because the world is changing. Let's open our minds and live in present.", false, true, english, qandeelBaloch));
//        quotes.add(new Quote("It was my wish since I was a child to become something, to be able to stand on my own two feet, to do something for myself.", false, true, english, qandeelBaloch));
//        quotes.add(new Quote("Pakistan is a free country, so according to me, in a free country, it's every right of the citizen to live the way they wish.", false, true, english, qandeelBaloch));
//
//        Author quavo = getAuthor(authors, "Quavo");
//        quotes.add(new Quote("History repeats itself. So you might wanna pay attention.", false, true, english, quavo));
//        quotes.add(new Quote("I'd open doors for anyone who opens doors for me.", false, true, english, quavo));
//        quotes.add(new Quote("Wealth is just consistency... I don't want to be rich. I want to be wealthy.", false, true, english, quavo));
//        quotes.add(new Quote("Being spontaneous is a blessing.", false, true, english, quavo));
//        quotes.add(new Quote("When you do it yourself, you feel better.", false, true, english, quavo));
//        quotes.add(new Quote("The people we grew up watching and listening to - Outkast, Gucci Mane, Hot Boys, Lil Wayne, Master P - all that type of stuff, we took those styles and made it our own.", false, true, english, quavo));
//        quotes.add(new Quote("You gotta give everybody a new trend a new wave, something new to do.", false, true, english, quavo));
//        quotes.add(new Quote("I'm just willing to try different things. But you gotta keep it all making sense.", false, true, english, quavo));
//        quotes.add(new Quote("We just feel like history repeats itself. You ain't never going to see nothing brand new; you're only going to see when records are broken. And we're here to just set records and set trends and follow the footsteps that have been shown to us.", false, true, english, quavo));
//        quotes.add(new Quote("I'll tell ya, when you open up that can of dab, it's always fresh.", false, true, english, quavo));
//
//        Author queenChristina = getAuthor(authors, "Queen Christina");
//        quotes.add(new Quote("It is necessary to try to pass one's self always; this occupation ought to last as long as life.", false, true, english, queenChristina));
//        quotes.add(new Quote("Fools are more to be feared than the wicked.", false, true, english, queenChristina));
//
//        Author robinWilliams = getAuthor(authors, "Robin Williams");
//        quotes.add(new Quote("No matter what people tell you, words and ideas can change the world.", false, true, english, robinWilliams));
//        quotes.add(new Quote("I'm sorry, if you were right, I'd agree with you.", false, true, english, robinWilliams));
//        quotes.add(new Quote("If women ran the world we wouldn't have wars, just intense negotiations every 28 days.", false, true, english, robinWilliams));
//        quotes.add(new Quote("I used to think that the worst thing in life was to end up alone. It's not. The worst thing in life is to end up with people who make you feel alone.", false, true, english, robinWilliams));
//        quotes.add(new Quote("Why do they call it rush hour when nothing moves?", false, true, english, robinWilliams));
//        quotes.add(new Quote("You're only given a little spark of madness. You mustn't lose it.", false, true, english, robinWilliams));
//        quotes.add(new Quote("Never pick a fight with an ugly person, they've got nothing to lose.", false, true, english, robinWilliams));
//        quotes.add(new Quote("Spring is nature's way of saying, 'Let's party!'", false, true, english, robinWilliams));
//        quotes.add(new Quote("People say satire is dead. It's not dead; it's alive and living in the White House.", false, true, english, robinWilliams));
//        quotes.add(new Quote("For me, comedy starts as a spew, a kind of explosion, and then you sculpt it from there, if at all. It comes out of a deeper, darker side. Maybe it comes from anger, because I'm outraged by cruel absurdities, the hypocrisy that exists everywhere, even within yourself, where it's hardest to see.", false, true, english, robinWilliams));
//
//        Author rumi = getAuthor(authors, "Rumi");
//        quotes.add(new Quote("Let the beauty of what you love be what you do.", false, true, english, rumi));
//        quotes.add(new Quote("Everything that is made beautiful and fair and lovely is made for the eye of one who sees.", false, true, english, rumi));
//        quotes.add(new Quote("We are born of love; Love is our mother.", false, true, english, rumi));
//        quotes.add(new Quote("Beauty surrounds us, but usually we need to be walking in a garden to know it.", false, true, english, rumi));
//        quotes.add(new Quote("Grief can be the garden of compassion. If you keep your heart open through everything, your pain can become your greatest ally in your life's search for love and wisdom.", false, true, english, rumi));
//        quotes.add(new Quote("Don't grieve. Anything you lose comes round in another form.", false, true, english, rumi));
//        quotes.add(new Quote("The garden of love is green without limit and yields many fruits other than sorrow or joy. Love is beyond either condition: without spring, without autumn, it is always fresh.", false, true, english, rumi));
//        quotes.add(new Quote("Everyone has been made for some particular work, and the desire for that work has been put in every heart.", false, true, english, rumi));
//        quotes.add(new Quote("May this marriage be full of laughter, our every day in paradise.", false, true, english, rumi));
//        quotes.add(new Quote("Listen! Clam up your mouth and be silent like an oyster shell, for that tongue of yours is the enemy of the soul, my friend. When the lips are silent, the heart has a hundred tongues.", false, true, english, rumi));
//
//        Author ralphWaldoEmerson = getAuthor(authors, "Ralph Waldo Emerson");
//        quotes.add(new Quote("Adopt the pace of nature: her secret is patience.", false, true, english, ralphWaldoEmerson));
//        quotes.add(new Quote("Never lose an opportunity of seeing anything beautiful, for beauty is God's handwriting.", false, true, english, ralphWaldoEmerson));
//        quotes.add(new Quote("Love of beauty is taste. The creation of beauty is art.", false, true, english, ralphWaldoEmerson));
//        quotes.add(new Quote("For every minute you remain angry, you give up sixty seconds of peace of mind.", false, true, english, ralphWaldoEmerson));
//        quotes.add(new Quote("Write it on your heart that every day is the best day in the year.", false, true, english, ralphWaldoEmerson));
//        quotes.add(new Quote("Do not go where the path may lead, go instead where there is no path and leave a trail.", false, true, english, ralphWaldoEmerson));
//        quotes.add(new Quote("Nature always wears the colors of the spirit.", false, true, english, ralphWaldoEmerson));
//        quotes.add(new Quote("What lies behind you and what lies in front of you, pales in comparison to what lies inside of you.", false, true, english, ralphWaldoEmerson));
//        quotes.add(new Quote("The first wealth is health.", false, true, english, ralphWaldoEmerson));
//        quotes.add(new Quote("It is one of the blessings of old friends that you can afford to be stupid with them.", false, true, english, ralphWaldoEmerson));
//
//        Author richardBranson = getAuthor(authors, "Richard Branson");
//        quotes.add(new Quote("Business opportunities are like buses, there's always another one coming.", false, true, english, richardBranson));
//        quotes.add(new Quote("By putting the employee first, the customer effectively comes first by default, and in the end, the shareholder comes first by default as well.", false, true, english, richardBranson));
//        quotes.add(new Quote("Starting your own business isn't just a job - it's a way of life.", false, true, english, richardBranson));
//        quotes.add(new Quote("From my very first day as an entrepreneur, I've felt the only mission worth pursuing in business is to make people's lives better.", false, true, english, richardBranson));
//        quotes.add(new Quote("Treat failure as a lesson on how not to approach achieving a goal, and then use that learning to improve your chances of success when you try again. Failure is only the end if you decide to stop.", false, true, english, richardBranson));
//        quotes.add(new Quote("Fun is one of the most important - and underrated - ingredients in any successful venture. If you're not having fun, then it's probably time to call it quits and try something else.", false, true, english, richardBranson));
//        quotes.add(new Quote("A passionate belief in your business and personal objectives can make all the difference between success and failure. If you aren't proud of what you're doing, why should anybody else be?", false, true, english, richardBranson));
//        quotes.add(new Quote("Do not be embarrassed by your failures, learn from them and start again.", false, true, english, richardBranson));
//        quotes.add(new Quote("My attitude has always been, if you fall flat on your face, at least you're moving forward. All you have to do is get back up and try again.", false, true, english, richardBranson));
//        quotes.add(new Quote("You don't learn to walk by following rules. You learn by doing, and by falling over.", false, true, english, richardBranson));
//
//        Author socrates = getAuthor(authors, "Socrates");
//        quotes.add(new Quote("The only true wisdom is in knowing you know nothing.", false, true, english, socrates));
//        quotes.add(new Quote("To know, is to know that you know nothing. That is the meaning of true knowledge.", false, true, english, socrates));
//        quotes.add(new Quote("Our prayers should be for blessings in general, for God knows best what is good for us.", false, true, english, socrates));
//        quotes.add(new Quote("I know that I am intelligent, because I know that I know nothing.", false, true, english, socrates));
//        quotes.add(new Quote("He is richest who is content with the least, for content is the wealth of nature.", false, true, english, socrates));
//        quotes.add(new Quote("True wisdom comes to each of us when we realize how little we understand about life, ourselves, and the world around us.", false, true, english, socrates));
//        quotes.add(new Quote("By all means, marry. If you get a good wife, you'll become happy; if you get a bad one, you'll become a philosopher.", false, true, english, socrates));
//        quotes.add(new Quote("Be as you wish to seem.", false, true, english, socrates));
//        quotes.add(new Quote("Death may be the greatest of all human blessings.", false, true, english, socrates));
//        quotes.add(new Quote("Worthless people live only to eat and drink; people of worth eat and drink only to live.", false, true, english, socrates));
//
//        Author saintAugustine = getAuthor(authors, "Saint Augustine");
//        quotes.add(new Quote("Do you wish to rise? Begin by descending. You plan a tower that will pierce the clouds? Lay first the foundation of humility.", false, true, english, saintAugustine));
//        quotes.add(new Quote("In the absence of justice, what is sovereignty but organized robbery?", false, true, english, saintAugustine));
//        quotes.add(new Quote("Men go abroad to wonder at the heights of mountains, at the huge waves of the sea, at the long courses of the rivers, at the vast compass of the ocean, at the circular motions of the stars, and they pass by themselves without wondering.", false, true, english, saintAugustine));
//        quotes.add(new Quote("What does love look like? It has the hands to help others. It has the feet to hasten to the poor and needy. It has eyes to see misery and want. It has the ears to hear the sighs and sorrows of men. That is what love looks like.", false, true, english, saintAugustine));
//        quotes.add(new Quote("Patience is the companion of wisdom.", false, true, english, saintAugustine));
//        quotes.add(new Quote("Punishment is justice for the unjust.", false, true, english, saintAugustine));
//        quotes.add(new Quote("Pray as though everything depended on God. Work as though everything depended on you.", false, true, english, saintAugustine));
//        quotes.add(new Quote("Faith is to believe what you do not see; the reward of this faith is to see what you believe.", false, true, english, saintAugustine));
//        quotes.add(new Quote("It was pride that changed angels into devils; it is humility that makes men as angels.", false, true, english, saintAugustine));
//        quotes.add(new Quote("The world is a book, and those who do not travel read only a page.", false, true, english, saintAugustine));
//
//        Author sigmundFreud = getAuthor(authors, "Sigmund Freud");
//        quotes.add(new Quote("Dreams are often most profound when they seem the most crazy.", false, true, english, sigmundFreud));
//        quotes.add(new Quote("We are never so defensless against suffering as when we love.", false, true, english, sigmundFreud));
//        quotes.add(new Quote("Most people do not really want freedom, because freedom involves responsibility, and most people are frightened of responsibility.", false, true, english, sigmundFreud));
//        quotes.add(new Quote("Being entirely honest with oneself is a good exercise.", false, true, english, sigmundFreud));
//        quotes.add(new Quote("The interpretation of dreams is the royal road to a knowledge of the unconscious activities of the mind.", false, true, english, sigmundFreud));
//        quotes.add(new Quote("The mind is like an iceberg, it floats with one-seventh of its bulk above water.", false, true, english, sigmundFreud));
//        quotes.add(new Quote("Flowers are restful to look at. They have neither emotions nor conflicts.", false, true, english, sigmundFreud));
//        quotes.add(new Quote("I cannot think of any need in childhood as strong as the need for a father's protection.", false, true, english, sigmundFreud));
//        quotes.add(new Quote("Time spent with cats is never wasted.", false, true, english, sigmundFreud));
//        quotes.add(new Quote("Civilization began the first time an angry person cast a word instead of a rock.", false, true, english, sigmundFreud));
//
//        Author simonSinek = getAuthor(authors, "Simon Sinek");
//        quotes.add(new Quote("There is a difference between listening and waiting for your turn to speak.", false, true, english, simonSinek));
//        quotes.add(new Quote("Leadership is a way of thinking, a way of acting and, most importantly, a way of communicating.", false, true, english, simonSinek));
//        quotes.add(new Quote("We can't all be good at everything. This is partly the logic behind having a team in the first place, so each role can be filled with the person best suited for that role and together, every job and every strength is covered.", false, true, english, simonSinek));
//        quotes.add(new Quote("Don't quit. Never give up trying to build the world you can see, even if others can't see it. Listen to your drum and your drum only. It's the one that makes the sweetest sound.", false, true, english, simonSinek));
//        quotes.add(new Quote("The challenge of the unknown future is so much more exciting than the stories of the accomplished past.", false, true, english, simonSinek));
//        quotes.add(new Quote("Champions are not the ones who always win races - champions are the ones who get out there and try. And try harder the next time. And even harder the next time. 'Champion' is a state of mind. They are devoted. They compete to best themselves as much if not more than they compete to best others. Champions are not just athletes.", false, true, english, simonSinek));
//        quotes.add(new Quote("There is no decision that we can make that doesn't come with some sort of balance or sacrifice.", false, true, english, simonSinek));
//        quotes.add(new Quote("A leader's job is not to do the work for others, it's to help others figure out how to do it themselves, to get things done, and to succeed beyond what they thought possible.", false, true, english, simonSinek));
//        quotes.add(new Quote("Leadership is not about the next election, it's about the next generation.", false, true, english, simonSinek));
//        quotes.add(new Quote("The strong bond of friendship is not always a balanced equation; friendship is not always about giving and taking in equal shares. Instead, friendship is grounded in a feeling that you know exactly who will be there for you when you need something, no matter what or when.", false, true, english, simonSinek));
//
//        Author sorenKierkegaard = getAuthor(authors, "Soren Kierkegaard");
//        quotes.add(new Quote("The highest and most beautiful things in life are not to be heard about, nor read about, nor seen but, if one will, are to be lived.", false, true, english, sorenKierkegaard));
//        quotes.add(new Quote("Our life always expresses the result of our dominant thoughts.", false, true, english, sorenKierkegaard));
//        quotes.add(new Quote("What is a poet? An unhappy person who conceals profound anguish in his heart but whose lips are so formed that as sighs and cries pass over them they sound like beautiful music.", false, true, english, sorenKierkegaard));
//        quotes.add(new Quote("Face the facts of being what you are, for that is what changes what you are.", false, true, english, sorenKierkegaard));
//        quotes.add(new Quote("Be that self which one truly is.", false, true, english, sorenKierkegaard));
//        quotes.add(new Quote("Don't forget to love yourself.", false, true, english, sorenKierkegaard));
//        quotes.add(new Quote("People demand freedom of speech as a compensation for the freedom of thought which they seldom use.", false, true, english, sorenKierkegaard));
//        quotes.add(new Quote("Prayer does not change God, but it changes him who prays.", false, true, english, sorenKierkegaard));
//        quotes.add(new Quote("Life can only be understood backwards; but it must be lived forwards.", false, true, english, sorenKierkegaard));
//        quotes.add(new Quote("Life is not a problem to be solved, but a reality to be experienced.", false, true, english, sorenKierkegaard));
//
//        Author theodoreRoosevelt = getAuthor(authors, "Theodore Roosevelt");
//        quotes.add(new Quote("Believe you can and you're halfway there.", false, true, english, theodoreRoosevelt));
//        quotes.add(new Quote("Keep your eyes on the stars, and your feet on the ground.", false, true, english, theodoreRoosevelt));
//        quotes.add(new Quote("People ask the difference between a leader and a boss. The leader leads, and the boss drives.", false, true, english, theodoreRoosevelt));
//        quotes.add(new Quote("If you could kick the person in the pants responsible for most of your trouble, you wouldn't sit for a month.", false, true, english, theodoreRoosevelt));
//        quotes.add(new Quote("Far better is it to dare mighty things, to win glorious triumphs, even though checkered by failure... than to rank with those poor spirits who neither enjoy nor suffer much, because they live in a gray twilight that knows not victory nor defeat.", false, true, english, theodoreRoosevelt));
//        quotes.add(new Quote("Nobody cares how much you know, until they know how much you care.", false, true, english, theodoreRoosevelt));
//        quotes.add(new Quote("In any moment of decision, the best thing you can do is the right thing, the next best thing is the wrong thing, and the worst thing you can do is nothing.", false, true, english, theodoreRoosevelt));
//        quotes.add(new Quote("To educate a man in mind and not in morals is to educate a menace to society.", false, true, english, theodoreRoosevelt));
//        quotes.add(new Quote("Great thoughts speak only to the thoughtful mind, but great actions speak to all mankind.", false, true, english, theodoreRoosevelt));
//        quotes.add(new Quote("With self-discipline most anything is possible.", false, true, english, theodoreRoosevelt));
//
//        Author TSEliot = getAuthor(authors, "T. S. Eliot");
//        quotes.add(new Quote("If you aren't in over your head, how do you know how tall you are?", false, true, english, TSEliot));
//        quotes.add(new Quote("Poetry is not a turning loose of emotion, but an escape from emotion; it is not the expression of personality, but an escape from personality. But, of course, only those who have personality and emotions know what it means to want to escape from these things.", false, true, english, TSEliot));
//        quotes.add(new Quote("I said to my soul, be still, and wait without hope, For hope would be hope for the wrong thing.", false, true, english, TSEliot));
//        quotes.add(new Quote("Immature poets imitate; mature poets steal.", false, true, english, TSEliot));
//        quotes.add(new Quote("Home is where one starts from.", false, true, english, TSEliot));
//        quotes.add(new Quote("So the darkness shall be the light, and the stillness the dancing.", false, true, english, TSEliot));
//        quotes.add(new Quote("I have measured out my life with coffee spoons.", false, true, english, TSEliot));
//        quotes.add(new Quote("Moving between the legs of tables and of chairs, rising or falling, grasping at kisses and toys, advancing boldly, sudden to take alarm, retreating to the corner of arm and knee, eager to be reassured, taking pleasure in the fragrant brilliance of the Christmas tree.", false, true, english, TSEliot));
//        quotes.add(new Quote("We shall not cease from exploration, and the end of all our exploring will be to arrive where we started and know the place for the first time.", false, true, english, TSEliot));
//        quotes.add(new Quote("Only those who will risk going too far can possibly find out how far one can go.", false, true, english, TSEliot));
//
//        Author taylorSwift = getAuthor(authors, "Taylor Swift");
//        quotes.add(new Quote("I am alone a lot, which is good. I need that time to just be alone after a long day, just decompress. So, I go to either my house or the hotel, or my apartment, or whatever - wherever I am, I go home and I watch TV and I sit there, with my cat, and I just watch TV or go online, check my emails.", false, true, english, taylorSwift));
//        quotes.add(new Quote("I think fearless is having fears but jumping anyway.", false, true, english, taylorSwift));
//        quotes.add(new Quote("I love the scents of winter! For me, it's all about the feeling you get when you smell pumpkin spice, cinnamon, nutmeg, gingerbread and spruce.", false, true, english, taylorSwift));
//        quotes.add(new Quote("My ultimate goal is to end up being happy. Most of the time.", false, true, english, taylorSwift));
//        quotes.add(new Quote("When you are missing someone, time seems to move slower, and when I'm falling in love with someone, time seems to be moving faster.", false, true, english, taylorSwift));
//        quotes.add(new Quote("No matter what happens in life, be good to people. Being good to people is a wonderful legacy to leave behind.", false, true, english, taylorSwift));
//        quotes.add(new Quote("I love making new friends and I respect people for a lot of different reasons.", false, true, english, taylorSwift));
//        quotes.add(new Quote("I think every girl's dream is to find a bad boy at the right time, when he wants to not be bad anymore.", false, true, english, taylorSwift));
//        quotes.add(new Quote("We don't need to share the same opinions as others, but we need to be respectful.", false, true, english, taylorSwift));
//        quotes.add(new Quote("Red is such an interesting color to correlate with emotion, because it's on both ends of the spectrum. On one end you have happiness, falling in love, infatuation with someone, passion, all that. On the other end, you've got obsession, jealousy, danger, fear, anger and frustration.", false, true, english, taylorSwift));
//
//        Author thomasAquinas = getAuthor(authors, "Thomas Aquinas");
//        quotes.add(new Quote("To bear with patience wrongs done to oneself is a mark of perfection, but to bear with patience wrongs done to someone else is a mark of imperfection and even of actual sin.", false, true, english, thomasAquinas));
//        quotes.add(new Quote("Law is nothing other than a certain ordinance of reason for the common good, promulgated by the person who has the care of the community.", false, true, english, thomasAquinas));
//        quotes.add(new Quote("Good can exist without evil, whereas evil cannot exist without good.", false, true, english, thomasAquinas));
//        quotes.add(new Quote("Better to illuminate than merely to shine, to deliver to others contemplated truths than merely to contemplate.", false, true, english, thomasAquinas));
//        quotes.add(new Quote("Every judgement of conscience, be it right or wrong, be it about things evil in themselves or morally indifferent, is obligatory, in such wise that he who acts against his conscience always sins.", false, true, english, thomasAquinas));
//        quotes.add(new Quote("Sorrow can be alleviated by good sleep, a bath and a glass of wine.", false, true, english, thomasAquinas));
//        quotes.add(new Quote("To one who has faith, no explanation is necessary. To one without faith, no explanation is possible.", false, true, english, thomasAquinas));
//        quotes.add(new Quote("If the highest aim of a captain were to preserve his ship, he would keep it in port forever.", false, true, english, thomasAquinas));
//        quotes.add(new Quote("The things that we love tell us what we are.", false, true, english, thomasAquinas));
//        quotes.add(new Quote("There is nothing on this earth more to be prized than true friendship.", false, true, english, thomasAquinas));
//
//        Author thomasHobbes = getAuthor(authors, "Thomas Hobbes");
//        quotes.add(new Quote("A man's conscience and his judgment is the same thing; and as the judgment, so also the conscience, may be erroneous.", false, true, english, thomasHobbes));
//        quotes.add(new Quote("There is no such thing as perpetual tranquillity of mind while we live here; because life itself is but motion, and can never be without desire, nor without fear, no more than without sense.", false, true, english, thomasHobbes));
//        quotes.add(new Quote("Leisure is the Mother of Philosophy.", false, true, english, thomasHobbes));
//        quotes.add(new Quote("Words are the money of fools.", false, true, english, thomasHobbes));
//        quotes.add(new Quote("Curiosity is the lust of the mind.", false, true, english, thomasHobbes));
//        quotes.add(new Quote("During the time men live without a common power to keep them all in awe, they are in that conditions called war; and such a war, as if of every man, against every man.", false, true, english, thomasHobbes));
//        quotes.add(new Quote("The obligation of subjects to the sovereign is understood to last as long, and no longer, than the power lasteth by which he is able to protect them.", false, true, english, thomasHobbes));
//        quotes.add(new Quote("The right of nature... is the liberty each man hath to use his own power, as he will himself, for the preservation of his own nature; that is to say, of his own life.", false, true, english, thomasHobbes));
//        quotes.add(new Quote("It is not wisdom but Authority that makes a law.", false, true, english, thomasHobbes));
//        quotes.add(new Quote("The condition of man... is a condition of war of everyone against everyone.", false, true, english, thomasHobbes));
//
//        Author voltaire = getAuthor(authors, "Voltaire");
//        quotes.add(new Quote("God gave us the gift of life; it is up to us to give ourselves the gift of living well.", false, true, english, voltaire));
//        quotes.add(new Quote("Judge a man by his questions rather than his answers.", false, true, english, voltaire));
//        quotes.add(new Quote("Appreciation is a wonderful thing: It makes what is excellent in others belong to us as well.", false, true, english, voltaire));
//        quotes.add(new Quote("The art of medicine consists in amusing the patient while nature cures the disease.", false, true, english, voltaire));
//        quotes.add(new Quote("Tears are the silent language of grief.", false, true, english, voltaire));
//        quotes.add(new Quote("It is difficult to free fools from the chains they revere.", false, true, english, voltaire));
//        quotes.add(new Quote("Those who can make you believe absurdities can make you commit atrocities.", false, true, english, voltaire));
//        quotes.add(new Quote("Each player must accept the cards life deals him or her: but once they are in hand, he or she alone must decide how to play the cards in order to win the game.", false, true, english, voltaire));
//        quotes.add(new Quote("Optimism is the madness of insisting that all is well when we are miserable.", false, true, english, voltaire));
//        quotes.add(new Quote("It is better to risk saving a guilty man than to condemn an innocent one.", false, true, english, voltaire));
//
//        Author valentinoRossi = getAuthor(authors, "Valentino Rossi");
//        quotes.add(new Quote("Riding a race bike is an art - a thing that you do because you feel something inside.", false, true, english, valentinoRossi));
//        quotes.add(new Quote("The great fights with your strongest rivals are always the biggest motivation. When you win easily it's not the same taste.", false, true, english, valentinoRossi));
//        quotes.add(new Quote("The work that we do during the winter is very important; we have a new bike and it's important to develop it during this time, and we start with this test.", false, true, english, valentinoRossi));
//        quotes.add(new Quote("The most important thing is to have a good relationship with the bike... you have to understand what she wants. I think of a motorcycle as a woman, and I know that sounds silly, but it's true.", false, true, english, valentinoRossi));
//        quotes.add(new Quote("My normal life is like being on holiday.", false, true, english, valentinoRossi));
//        quotes.add(new Quote("I am able to ride the bike and think clearly about strategy and tyres. I also have positive thinking. I am very constructively critical.", false, true, english, valentinoRossi));
//        quotes.add(new Quote("I race to win. If I am on the bike or in a car it will always be the same.", false, true, english, valentinoRossi));
//        quotes.add(new Quote("I never race for records. The motivation to try to beat the record is not enough to continue. You have to enjoy it.", false, true, english, valentinoRossi));
//        quotes.add(new Quote("Maybe the bike is more dangerous, but the passion for the car for me is second to the bike.", false, true, english, valentinoRossi));
//        quotes.add(new Quote("To be a great motorbike racer, the most important thing is passion for the bike.", false, true, english, valentinoRossi));
//
//        Author victorHugo = getAuthor(authors, "Victor Hugo");
//        quotes.add(new Quote("How did it happen that their lips came together? How does it happen that birds sing, that snow melts, that the rose unfolds, that the dawn whitens behind the stark shapes of trees on the quivering summit of the hill? A kiss, and all was said.", false, true, english, victorHugo));
//        quotes.add(new Quote("There is one spectacle grander than the sea, that is the sky; there is one spectacle grander than the sky, that is the interior of the soul.", false, true, english, victorHugo));
//        quotes.add(new Quote("When dictatorship is a fact, revolution becomes a right.", false, true, english, victorHugo));
//        quotes.add(new Quote("Life is the flower for which love is the honey.", false, true, english, victorHugo));
//        quotes.add(new Quote("A mother's arms are made of tenderness and children sleep soundly in them.", false, true, english, victorHugo));
//        quotes.add(new Quote("Concision in style, precision in thought, decision in life.", false, true, english, victorHugo));
//        quotes.add(new Quote("He who opens a school door, closes a prison.", false, true, english, victorHugo));
//        quotes.add(new Quote("When a woman is talking to you, listen to what she says with her eyes.", false, true, english, victorHugo));
//        quotes.add(new Quote("Music expresses that which cannot be said and on which it is impossible to be silent.", false, true, english, victorHugo));
//        quotes.add(new Quote("Laughter is the sun that drives winter from the human face.", false, true, english, victorHugo));
//
//        Author viktorEFrankl = getAuthor(authors, "Viktor E. Frankl");
//        quotes.add(new Quote("A man who becomes conscious of the responsibility he bears toward a human being who affectionately waits for him, or to an unfinished work, will never be able to throw away his life. He knows the 'why' for his existence, and will be able to bear almost any 'how.'", false, true, english, viktorEFrankl));
//        quotes.add(new Quote("If there is a meaning in life at all, then there must be a meaning in suffering. Suffering is an ineradicable part of life, even as fate and death. Without suffering and death, human life cannot be complete.", false, true, english, viktorEFrankl));
//        quotes.add(new Quote("The last of human freedoms - the ability to chose one's attitude in a given set of circumstances.", false, true, english, viktorEFrankl));
//        quotes.add(new Quote("Challenging the meaning of life is the truest expression of the state of being human.", false, true, english, viktorEFrankl));
//        quotes.add(new Quote("For the meaning of life differs from man to man, from day to day and from hour to hour. What matters, therefore, is not the meaning of life in general but rather the specific meaning of a person's life at a given moment.", false, true, english, viktorEFrankl));
//        quotes.add(new Quote("Everyone has his own specific vocation or mission in life; everyone must carry out a concrete assignment that demands fulfillment. Therein he cannot be replaced, nor can his life be repeated, thus, everyone's task is unique as his specific opportunity to implement it.", false, true, english, viktorEFrankl));
//        quotes.add(new Quote("Between stimulus and response there is a space. In that space is our power to choose our response. In our response lies our growth and our freedom.", false, true, english, viktorEFrankl));
//        quotes.add(new Quote("Everything can be taken from a man but one thing: the last of human freedoms - to choose one's attitude in any given set of circumstances, to choose one's own way.", false, true, english, viktorEFrankl));
//        quotes.add(new Quote("When we are no longer able to change a situation - we are challenged to change ourselves.", false, true, english, viktorEFrankl));
//        quotes.add(new Quote("Happiness must happen, and the same holds for success: you have to let it happen by not caring about it.", false, true, english, viktorEFrankl));
//
//        Author vinScully = getAuthor(authors, "Vin Scully");
//        quotes.add(new Quote("As long as you live keep smiling because it brightens everybody's day.", false, true, english, vinScully));
//        quotes.add(new Quote("The roar of the crowd has always been the sweetest music. It's intoxicating.", false, true, english, vinScully));
//        quotes.add(new Quote("It's a wonderful feeling being a bridge to the past and unite generations.", false, true, english, vinScully));
//        quotes.add(new Quote("Statistics are used much like a drunk uses a lamppost: for support, not illumination.", false, true, english, vinScully));
//        quotes.add(new Quote("Andre Dawson has a bruised knee and is listed as day-to-day. Aren't we all?", false, true, english, vinScully));
//        quotes.add(new Quote("Good is not good when better is expected.", false, true, english, vinScully));
//        quotes.add(new Quote("Almost all of us growing up have played baseball on some level. It has an inside track with people. It has a unifying effect.", false, true, english, vinScully));
//        quotes.add(new Quote("I guess my thermometer for my baseball fever is still a goose bump.", false, true, english, vinScully));
//        quotes.add(new Quote("I don't like to be alone, but I do cherish the moments that I'm alone with a good book.", false, true, english, vinScully));
//        quotes.add(new Quote("Don't let the winds blow your dreams away... or steal your faith in God.", false, true, english, vinScully));
//
//        Author vinceLombardi = getAuthor(authors, "Vince Lombardi");
//        quotes.add(new Quote("Football is like life - it requires perseverance, self-denial, hard work, sacrifice, dedication and respect for authority.", false, true, english, vinceLombardi));
//        quotes.add(new Quote("Winning is habit. Unfortunately, so is losing.", false, true, english, vinceLombardi));
//        quotes.add(new Quote("Practice does not make perfect. Only perfect practice makes perfect.", false, true, english, vinceLombardi));
//        quotes.add(new Quote("The achievements of an organization are the results of the combined effort of each individual.", false, true, english, vinceLombardi));
//        quotes.add(new Quote("The measure of who we are is what we do with what we have.", false, true, english, vinceLombardi));
//        quotes.add(new Quote("Individual commitment to a group effort - that is what makes a team work, a company work, a society work, a civilization work.", false, true, english, vinceLombardi));
//        quotes.add(new Quote("Winners never quit and quitters never win.", false, true, english, vinceLombardi));
//        quotes.add(new Quote("We didn't lose the game; we just ran out of time.", false, true, english, vinceLombardi));
//        quotes.add(new Quote("The price of success is hard work, dedication to the job at hand, and the determination that whether we win or lose, we have applied the best of ourselves to the task at hand.", false, true, english, vinceLombardi));
//        quotes.add(new Quote("Perfection is not attainable, but if we chase perfection we can catch excellence.", false, true, english, vinceLombardi));
//
//        Author vincentVanGogh = getAuthor(authors, "Vincent Van Gogh");
//        quotes.add(new Quote("What would life be if we had no courage to attempt anything?", false, true, english, vincentVanGogh));
//        quotes.add(new Quote("I feel that there is nothing more truly artistic than to love people.", false, true, english, vincentVanGogh));
//        quotes.add(new Quote("If you hear a voice within you say 'you cannot paint,' then by all means paint, and that voice will be silenced.", false, true, english, vincentVanGogh));
//        quotes.add(new Quote("There is no blue without yellow and without orange.", false, true, english, vincentVanGogh));
//        quotes.add(new Quote("I put my heart and my soul into my work, and have lost my mind in the process.", false, true, english, vincentVanGogh));
//        quotes.add(new Quote("The fishermen know that the sea is dangerous and the storm terrible, but they have never found these dangers sufficient reason for remaining ashore.", false, true, english, vincentVanGogh));
//        quotes.add(new Quote("For my part I know nothing with any certainty, but the sight of the stars makes me dream.", false, true, english, vincentVanGogh));
//        quotes.add(new Quote("I dream of painting and then I paint my dream.", false, true, english, vincentVanGogh));
//        quotes.add(new Quote("I often think that the night is more alive and more richly colored than the day.", false, true, english, vincentVanGogh));
//        quotes.add(new Quote("Great things are done by a series of small things brought together.", false, true, english, vincentVanGogh));
//
//        Author waltDisney = getAuthor(authors, "Walt Disney");
//        quotes.add(new Quote("If you can dream it, you can do it.", false, true, english, waltDisney));
//        quotes.add(new Quote("We keep moving forward, opening new doors, and doing new things, because we're curious and curiosity keeps leading us down new paths.", false, true, english, waltDisney));
//        quotes.add(new Quote("All our dreams can come true, if we have the courage to pursue them.", false, true, english, waltDisney));
//        quotes.add(new Quote("The way to get started is to quit talking and begin doing.", false, true, english, waltDisney));
//        quotes.add(new Quote("You can design and create, and build the most wonderful place in the world. But it takes people to make the dream a reality.", false, true, english, waltDisney));
//        quotes.add(new Quote("Times and conditions change so rapidly that we must keep our aim constantly focused on the future.", false, true, english, waltDisney));
//        quotes.add(new Quote("The way to get started is to quit talking and begin doing.", false, true, english, waltDisney));
//        quotes.add(new Quote("It's kind of fun to do the impossible.", false, true, english, waltDisney));
//        quotes.add(new Quote("When you're curious, you find lots of interesting things to do.", false, true, english, waltDisney));
//        quotes.add(new Quote("All the adversity I've had in my life, all my troubles and obstacles, have strengthened me... You may not realize it when it happens, but a kick in the teeth may be the best thing in the world for you.", false, true, english, waltDisney));
//
//        Author WCFields = getAuthor(authors, "W. C. Fields");
//        quotes.add(new Quote("A woman drove me to drink and I didn't even have the decency to thank her.", false, true, english, WCFields));
//        quotes.add(new Quote("It ain't what they call you, it's what you answer to.", false, true, english, WCFields));
//        quotes.add(new Quote("Always carry a flagon of whiskey in case of snakebite and furthermore always carry a small snake.", false, true, english, WCFields));
//        quotes.add(new Quote("I never worry about being driven to drink; I just worry about being driven home.", false, true, english, WCFields));
//        quotes.add(new Quote("The best cure for insomnia is to get a lot of sleep.", false, true, english, WCFields));
//        quotes.add(new Quote("No doubt exists that all women are crazy; it's only a question of degree.", false, true, english, WCFields));
//        quotes.add(new Quote("If you can't dazzle them with brilliance, baffle them with bull.", false, true, english, WCFields));
//        quotes.add(new Quote("A rich man is nothing but a poor man with money.", false, true, english, WCFields));
//        quotes.add(new Quote("Start every day off with a smile and get it over with.", false, true, english, WCFields));
//        quotes.add(new Quote("I cook with wine, sometimes I even add it to the food.", false, true, english, WCFields));
//
//        Author yaelNaim = getAuthor(authors, "Yael Naim");
//        quotes.add(new Quote("In France, I found there is a lot of attention to the little details and to the quality of life.", false, true, english, yaelNaim));
//        quotes.add(new Quote("When I'd go to Israel, I felt like a tourist. My social and professional ties had started to dissolve, and it confused me. I didn't know whether I should stay here in Paris or go back to Israel, or even cut off all my ties with Israel so I could really plant roots here. Or maybe go somewhere else altogether.", false, true, english, yaelNaim));
//        quotes.add(new Quote("When I write in Hebrew, I don't look for sophistication in music; it's just pure emotion that comes out.", false, true, english, yaelNaim));
//        quotes.add(new Quote("Hebrew is my first language, so it's really the most personal and the most simple. When I write in Hebrew, I don't look for sophistication in music; it's just pure emotion that comes out.", false, true, english, yaelNaim));
//        quotes.add(new Quote("I like to sing covers of songs that are at the extreme ends from what I usually listen to.", false, true, english, yaelNaim));
//        quotes.add(new Quote("English is really free for me; there's no limits to the music and the imagination. And French, it's just I live in Paris, and it's really a poetic language where you can really play with words.", false, true, english, yaelNaim));
//        quotes.add(new Quote("My first album was full of ideas and attempts to go in all kinds of directions. I was young. I loved making music, but I didn't have a clear path. I also lacked in confidence.", false, true, english, yaelNaim));
//        quotes.add(new Quote("My big influences are Joni Mitchell, and a lot of classical and Indian music, as well as Nina Simone and the personal blues and jazz of Billie Holiday. Other influences for me include Bjork, Nick Drake, and Sufjan Stevens.", false, true, english, yaelNaim));
//        quotes.add(new Quote("Songs are a way to express what I have felt. A way to understand what happened to me or to other people.", false, true, english, yaelNaim));
//        quotes.add(new Quote("As always, I wrote songs. Some people cook or play sports. This is what I love to do. Sometimes I can't express myself that well in talk, so I write songs.", false, true, english, yaelNaim));
//
//        Author yaelStone = getAuthor(authors, "Yael Stone");
//        quotes.add(new Quote("Family hang-outs can go very late into the night and involve lots of music.", false, true, english, yaelStone));
//        quotes.add(new Quote("Prison makes an interesting context for so many different characters to come together. You get to see what lines get drawn between people.", false, true, english, yaelStone));
//        quotes.add(new Quote("I've definitely had those moments when I think a relationship with somebody is one way, and then it just flips.", false, true, english, yaelStone));
//        quotes.add(new Quote("Australia has a very big history of incarceration. What does that mean to us? What does it mean that we came over to a country that's not necessarily ours and filled it with white prisoners?", false, true, english, yaelStone));
//        quotes.add(new Quote("It's really interesting working in television as opposed to the theater, where you know the arc of the character and you are able to create this whole backstory.", false, true, english, yaelStone));
//        quotes.add(new Quote("I never want to be anywhere else than in the rehearsal room. I mean, it's so lame to say, but it makes me supremely happy to work with people and to talk and invent and laugh.", false, true, english, yaelStone));
//        quotes.add(new Quote("In Australia, kids play in American accents.", false, true, english, yaelStone));
//        quotes.add(new Quote("One thing that is not to be underestimated is American culture's influence on the rest of the world.", false, true, english, yaelStone));
//        quotes.add(new Quote("My working history as an actor is definitely in the theatre; it certainly was in Australia.", false, true, english, yaelStone));
//        quotes.add(new Quote("", false, true, english, yaelStone));
//
//        Author yahooSerious = getAuthor(authors, "Yahoo Serious");
//        quotes.add(new Quote("In 1905 Albert discovered Relativity, in 1906 he invented Rock and Roll.", false, true, english, yahooSerious));
//        quotes.add(new Quote("Find your own specific voice in filmmaking and go for it. Either people will get it or they won't and that's what it's all about.", false, true, english, yahooSerious));
//        quotes.add(new Quote("The American formula things are out there but they don't have any stories to tell - we have all the stories to tell - but they're all formula.", false, true, english, yahooSerious));
//        quotes.add(new Quote("It's an infinite creative universe to explore so why chase conservative options?", false, true, english, yahooSerious));
//        quotes.add(new Quote("There are a thousand weird untold stories in the Australian film industry, this has been one of them.", false, true, english, yahooSerious));
//        quotes.add(new Quote("All the jokes in my films, the comedy, they're not me, I just try to hold a big mirror up to us.", false, true, english, yahooSerious));
//        quotes.add(new Quote("Australians don't have a preconceived notion of what things have to be... we can go on a fantastic journey.", false, true, english, yahooSerious));
//        quotes.add(new Quote("It's because we are so flooded with American culture that we're startled when we see ourselves up there on the screen.", false, true, english, yahooSerious));
//        quotes.add(new Quote("We live in a time where government is not a leadership thing, it's more a business that's out there and running riot, so I guess the people have to go out there and say stuff.", false, true, english, yahooSerious));
//        quotes.add(new Quote("", false, true, english, yahooSerious));
//
//        Author yairLapid = getAuthor(authors, "Yair Lapid");
//        quotes.add(new Quote("One of the things that hold together a human society is the existence of basic politeness among its members.", false, true, english, yairLapid));
//        quotes.add(new Quote("I want to live in a country that is not just a place but also an idea, and Jerusalem is the heart of the idea. There may be practical considerations, but a country cannot exist without an ethos, and Jerusalem is an ethos.", false, true, english, yairLapid));
//        quotes.add(new Quote("I don't reject caution, but you also have to be careful about caution because there's a stage when it turns into paralysis.", false, true, english, yairLapid));
//        quotes.add(new Quote("If you want to do stuff, you have to be able to handle controversy.", false, true, english, yairLapid));
//        quotes.add(new Quote("How can Israel say that everyone is equal before the law - that you're equal before the law - when the law defines Judaism as the cultural, national and legislative basis for the state?", false, true, english, yairLapid));
//        quotes.add(new Quote("Israel can't be the only country in the Western world not to have freedom of religion.", false, true, english, yairLapid));
//        quotes.add(new Quote("Arab society features apartheid of women, apartheid of homosexuals, and apartheid of Christians, Jews, and democracy.", false, true, english, yairLapid));
//        quotes.add(new Quote("Every Jew is my brother, and I will not succumb to hate speech.", false, true, english, yairLapid));
//        quotes.add(new Quote("Jerusalem will remain under Israeli sovereignty and will not be divided.", false, true, english, yairLapid));
//        quotes.add(new Quote("At times, we need to stop and rethink everything. Our entire history is made up of people who were sure they knew the truth yet forgot that the truth has an annoying tendency to change on occasion without us noticing it.", false, true, english, yairLapid));
//
//        Author yotamOttolenghi = getAuthor(authors, "Yotam Ottolenghi");
//        quotes.add(new Quote("The combination of olive oil, garlic and lemon juice lifts the spirits in winter.", false, true, english, yotamOttolenghi));
//        quotes.add(new Quote("The moment to tell my barber I was gay just never came up.", false, true, english, yotamOttolenghi));
//        quotes.add(new Quote("A well-made salad must have a certain uniformity; it should make perfect sense for those ingredients to share a bowl.", false, true, english, yotamOttolenghi));
//        quotes.add(new Quote("I keep returning to the combination of artichoke, broad beans and lemon. The freshness of young beans and the lemon juice 'lifts' the artichoke and balances its hearty nature.", false, true, english, yotamOttolenghi));
//        quotes.add(new Quote("As is always the way with pancakes, the first hotcake to come out of the pan will probably be a bit misshapen. Just scoff it, and carry on with the rest.", false, true, english, yotamOttolenghi));
//        quotes.add(new Quote("Pasta with melted cheese is the one thing I could eat over and over again.", false, true, english, yotamOttolenghi));
//        quotes.add(new Quote("For those, like me, who can't rely on being given a home smoker this Christmas, you can build your own approximation with just a roll of tin foil and a big wok or pan for which you have a lid.", false, true, english, yotamOttolenghi));
//        quotes.add(new Quote("The addition of vinaigrette to freshly roasted vegetables gives them a freshness and juiciness they don't normally have; the acidity brings out new shades of flavour, too.", false, true, english, yotamOttolenghi));
//        quotes.add(new Quote("For me, the end of childhood came when the number of candles on my birthday cake no longer reflected my age, around 19 or 20. From then on, each candle came to represent an entire decade.", false, true, english, yotamOttolenghi));
//
//        Author zigZiglar = getAuthor(authors, "Zig Ziglar");
//        quotes.add(new Quote("What you get by achieving your goals is not as important as what you become by achieving your goals.", false, true, english, zigZiglar));
//        quotes.add(new Quote("Positive thinking will let you do everything better than negative thinking will.", false, true, english, zigZiglar));
//        quotes.add(new Quote("Your attitude, not your aptitude, will determine your altitude.", false, true, english, zigZiglar));
//        quotes.add(new Quote("You were born to win, but to be a winner, you must plan to win, prepare to win, and expect to win.", false, true, english, zigZiglar));
//        quotes.add(new Quote("If people like you, they'll listen to you, but if they trust you, they'll do business with you.", false, true, english, zigZiglar));
//        quotes.add(new Quote("The foundation stones for a balanced success are honesty, character, integrity, faith, love and loyalty.", false, true, english, zigZiglar));
//        quotes.add(new Quote("With integrity, you have nothing to fear, since you have nothing to hide. With integrity, you will do the right thing, so you will have no guilt.", false, true, english, zigZiglar));
//        quotes.add(new Quote("Try to look at your weakness and convert it into your strength. That's success.", false, true, english, zigZiglar));
//        quotes.add(new Quote("You were designed for accomplishment, engineered for success, and endowed with the seeds of greatness.", false, true, english, zigZiglar));
//        quotes.add(new Quote("People often say that motivation doesn't last. Well, neither does bathing - that's why we recommend it daily.", false, true, english, zigZiglar));
//
//        Author zacGoldsmith = getAuthor(authors, "Zac Goldsmith");
//        quotes.add(new Quote("A pound invested in energy efficiency buys seven times more energy solution than a pound invested in nuclear power.", false, true, english, zacGoldsmith));
//        quotes.add(new Quote("Yes, Heathrow is the U.K.'s busiest airport, but new runways or a new airport are not the answer. It is far better to focus on improving capacity.", false, true, english, zacGoldsmith));
//        quotes.add(new Quote("Green policy is about triggering a shift to a cleaner way of doing things. To be effective, it needs to incentivise the right behaviour, for example through tax breaks, and that needs to be paid for by disincentives on polluting behaviour.", false, true, english, zacGoldsmith));
//        quotes.add(new Quote("Leonardo DiCaprio is a rare phenomenon. Whereas for so many celebrities an interest in the environment is a fashionable accessory, for DiCaprio it is a thread that runs through everything he does.", false, true, english, zacGoldsmith));
//        quotes.add(new Quote("I don't know David Cameron very well. I like him. I think you can judge a book by its cover - whoever said you can't is wrong - that's the whole point of nature giving us intuition, instinct and so on. I think the cover is pretty good.", false, true, english, zacGoldsmith));
//        quotes.add(new Quote("The 5,000 or so acres of Royal Parks are one of the things that make London special.", false, true, english, zacGoldsmith));
//        quotes.add(new Quote("You know I don't really have faith in politicians - this is quite a sleazy business. But there is no law which says that all politicians will turn out to be scumbags.", false, true, english, zacGoldsmith));
//        quotes.add(new Quote("In North America, more than half of all children travel to school by bus. We need a similar programme in London.", false, true, english, zacGoldsmith));
//        quotes.add(new Quote("More than half the world's largest 100 economies are corporations. They have no loyalties to place or citizens.", false, true, english, zacGoldsmith));
//        quotes.add(new Quote("I think sometimes if you are too interested in day-to-day politics, you lose sight of the long term.", false, true, english, zacGoldsmith));
//
//        Author zacHanson = getAuthor(authors, "Zac Hanson");
//        quotes.add(new Quote("It's cool to have critical success because it's always nice for your peers to say, 'Good job.' But who cares about them?", false, true, english, zacHanson));
//        quotes.add(new Quote("The real reason we ended up getting into that type of music was our dad worked for an oil company so we spent a year overseas when we were young kids. Because of that, it was all Spanish TV and radio so we ended up having these '50s and '60s tapes, tapes of that music.", false, true, english, zacHanson));
//        quotes.add(new Quote("It's pretty much run by everybody. We're very involved in everything that goes on. We always have been.", false, true, english, zacHanson));
//        quotes.add(new Quote("It would be nice to have radio support, not that we've ever had that much trouble with it.", false, true, english, zacHanson));
//        quotes.add(new Quote("You may be pulling from different influences because of different things that are going on in your life, different people that are around you and more experiences to pull from.", false, true, english, zacHanson));
//
//        Author zhuZhu = getAuthor(authors, "Zhu Zhu");
//        quotes.add(new Quote("In China, we don't know about the swimming pool game, but we know about Marco Polo.", false, true, english, zhuZhu));
//        quotes.add(new Quote("Sometimes I'll do a mask if I had a lot of makeup on that day or was out in the sun. I like a hydrogen mask. It's an easy one, and it's supposed to soothe and relax your skin.", false, true, english, zhuZhu));
//        quotes.add(new Quote("English is not my first language.", false, true, english, zhuZhu));
//        quotes.add(new Quote("I have really long hair, so I don't cut it all that often. Sometimes, when I'm working, I just have the stylist on set trim it for me. I don't dye my hair. When I was a teenager, I dyed my hair five colors at one time. It was all different shades of red going from more orange to more purple. I thought I looked so cool.", false, true, english, zhuZhu));
//        quotes.add(new Quote("I was planning to study more, but it's a struggle with so many opportunities for film and trying to get better through studying. No matter what, I want to be making more movies.", false, true, english, zhuZhu));
//        quotes.add(new Quote("I don't mind doing action or kung fu, but I'm also really happy to do something dramatic. I'd like to show that a Chinese girl doesn't have to do crazy martial arts to get the part.", false, true, english, zhuZhu));
//        quotes.add(new Quote("I love Jo Malone. I got the Orange Blossom scent as a gift many years ago, and I fell in love with it. It's very light, natural, sweet. It's there but not that obvious.", false, true, english, zhuZhu));
//        quotes.add(new Quote("I like the MAC Face and Body foundation. Sometimes it can't cover all my flaws, but I like it because it looks really natural and it evens out my skin tone.", false, true, english, zhuZhu));
//
//        Author zoeKazan = getAuthor(authors, "Zoe Kazan");
//        quotes.add(new Quote("Nothing's going to come to you by sitting around and waiting for it.", false, true, english, zoeKazan));
//        quotes.add(new Quote("Writing-wise, I like to have a lot of things on the burners at once, because when I hit a wall, I like to move on to the thing I haven't hit a wall on.", false, true, english, zoeKazan));
//        quotes.add(new Quote("I really love people. I love to meet people. I'm curious about people.", false, true, english, zoeKazan));
//        quotes.add(new Quote("I have mad luck. I'm super-good at games like backgammon or anything that requires rolling dice.", false, true, english, zoeKazan));
//        quotes.add(new Quote("I don't like pretentious films or pretentious people.", false, true, english, zoeKazan));
//        quotes.add(new Quote("You set up the story, but the characters start talking, and they go places that you didn't expect. You have to follow.", false, true, english, zoeKazan));
//        quotes.add(new Quote("And I think the female creative urge is intrinsically biologically linked to our ability to give birth to a child, even if we've never... I've never given birth, but I feel like it's part of our psychology.", false, true, english, zoeKazan));
//        quotes.add(new Quote("People really do make the assumption that I had some weirdo Hollywood upbringing, but my parents are incredibly down-to-earth people who worked really hard to raise us in a way that was health.", false, true, english, zoeKazan));
//        quotes.add(new Quote("And when I get bored, it's like the worst parts of me come out. I really veer to self-destructive tendencies quickly.", false, true, english, zoeKazan));
//        quotes.add(new Quote("Anytime that I've felt uninspired, I don't force myself to sit down and write. I only do it when I feel the impulse.", false, true, english, zoeKazan));
//
//        Author zooeyDeschanel = getAuthor(authors, "Zooey Deschanel");
//        quotes.add(new Quote("The Internet's like one big bathroom wall with a lot of people who anonymously can say really mean things. It's fine, I believe in freedom of speech and I think people should think what they want, but I don't care to hear it.", false, true, english, zooeyDeschanel));
//        quotes.add(new Quote("True love that lasts forever... yes, I do believe in it. My parents have been married for 40 years and my grandparents were married for 70 years. I come from a long line of true loves.", false, true, english, zooeyDeschanel));
//        quotes.add(new Quote("Summer has always been my favorite season. I feel happier.", false, true, english, zooeyDeschanel));
//        quotes.add(new Quote("I grew up believing my sister was from the planet Neptune and had been sent down to Earth to kill me.", false, true, english, zooeyDeschanel));
//        quotes.add(new Quote("I'm into sincerity in music and sincerity in art. If it doesn't feel true, I don't want to do it. Things that are too dramatic scare me. I think that's why I don't always fit into the world of performing arts.", false, true, english, zooeyDeschanel));
//        quotes.add(new Quote("From my perspective, probably women are won over by people who are sweet and respectful and courteous and kind and funny. I think those are the things that win women over.", false, true, english, zooeyDeschanel));
//        quotes.add(new Quote("I'm always doing something musically - when I'm working or when I'm off.", false, true, english, zooeyDeschanel));
//        quotes.add(new Quote("I'm a person who gets better with practice. Getting older is awesome - because you get more practice.", false, true, english, zooeyDeschanel));
//        quotes.add(new Quote("One thing I love about Christmas music is that it has a tradition of warmth.", false, true, english, zooeyDeschanel));
//        quotes.add(new Quote("Nothing's better than a picnic.", false, true, english, zooeyDeschanel));
//
//        Author zuleikhaRobinson = getAuthor(authors, "Zuleikha Robinson");
//        quotes.add(new Quote("The only time a friend has ever helped me in the industry was how I got my first job - that was through Mike Figgis.", false, true, english, zuleikhaRobinson));
//        quotes.add(new Quote("It took a lot of time to find my stride, and it was really humbling.", false, true, english, zuleikhaRobinson));
//        quotes.add(new Quote("I honestly never really watch the Emmys.", false, true, english, zuleikhaRobinson));
//        quotes.add(new Quote("I did go through a phase where I played videogames quite often, but I haven't in a few years.", false, true, english, zuleikhaRobinson));
//        quotes.add(new Quote("I like to frequent antiques shops and flea markets like the Rose Bowl in Pasadena.", false, true, english, zuleikhaRobinson));
//        quotes.add(new Quote("When I got the role in 'Homeland,' it really opened something up. Other people respected me more as an actor, doors were opened, and I understood for the first time that it wasn't personal. All that rejection wasn't personal.", false, true, english, zuleikhaRobinson));
//        quotes.add(new Quote("'Lone Gunmen' was my first job, and I got a little cocky, thinking I'm brilliant and don't have to work that hard because it comes so naturally.", false, true, english, zuleikhaRobinson));
//        quotes.add(new Quote("I'm mostly vegetarian.", false, true, english, zuleikhaRobinson));
//        quotes.add(new Quote("Only twice have I really had a hard time leaving a character. The first was my character in 'Rome' and then in 'Homeland.'", false, true, english, zuleikhaRobinson));
//        quotes.add(new Quote("I do like to play the darker characters; I don't really know why.", false, true, english, zuleikhaRobinson));
//
//        Author zygmuntBauman = getAuthor(authors, "Zygmunt Bauman");
//        quotes.add(new Quote("In a liquid modern life there are no permanent bonds, and any that we take up for a time must be tied loosely so that they can be untied again, as quickly and as effortlessly as possible, when circumstances change - as they surely will in our liquid modern society, over and over again.", false, true, english, zygmuntBauman));
//        quotes.add(new Quote("We live in a globalising world. That means that all of us, consciously or not, depend on each other. Whatever we do or refrain from doing affects the lives of people who live in places we'll never visit.", false, true, english, zygmuntBauman));
//        quotes.add(new Quote("This awful concept of underclass is really horrifying. You're not lower class, you are excluded - outside.", false, true, english, zygmuntBauman));
//        quotes.add(new Quote("As far as love is concerned, possession, power, fusion and disenchantment are the Four Horsemen of the Apocalypse.", false, true, english, zygmuntBauman));
//        quotes.add(new Quote("Relationships, like cars, should undergo regular services to make sure they are still roadworthy.", false, true, english, zygmuntBauman));
//        quotes.add(new Quote("Like the phoenix, socialism is reborn from every pile of ashes left day in, day out, by burnt-out human dreams and charred hopes.", false, true, english, zygmuntBauman));
//        quotes.add(new Quote("Why do I write books? Why do I think? Why should I be passionate? Because things could be different, they could be made better.", false, true, english, zygmuntBauman));
//        quotes.add(new Quote("The task for sociology is to come to the help of the individual. We have to be in service of freedom. It is something we have lost sight of.", false, true, english, zygmuntBauman));
//        quotes.add(new Quote("The carrying power of a bridge is not the average strength of the pillars, but the strength of the weakest pillar. I have always believed that you do not measure the health of a society by GNP but by the condition of its worst off.", false, true, english, zygmuntBauman));
//        quotes.add(new Quote("I was leftwing, I am leftwing, and I will die leftwing.", false, true, english, zygmuntBauman));

        ArrayList<Quote> mQuotes = new ArrayList<>(SugarRecord.saveInTx(quotes));
        DataQuote mDataQuote = new DataQuote(mQuotes);
        SessionManager.setStringSetting(getGlobalContext(), SESSION_DATA_QUOTES, DataQuote.convertFromObjectToString(mDataQuote));

//        Log.d(TAG, "dataQuote: " + SessionManager.getStringSetting(getGlobalContext(), SESSION_DATA_QUOTES));
//        DataQuote finalDataQuote = DataQuote.convertFromStringToObject(SessionManager.getStringSetting(getGlobalContext(), SESSION_DATA_QUOTES), DataQuote.class);
//        ArrayList<Quote> tempQuotes = finalDataQuote.getQuotes();
//        Log.d(TAG, "dataQuote final: " + finalDataQuote.toString());
//        Log.d(TAG, "dataQuote size: " + tempQuotes.size());

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
            if (SessionManager.getBooleanSetting(getGlobalContext(), SESSION_FREE_APP, false)) {
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
            if (SessionManager.getBooleanSetting(getGlobalContext(), SESSION_FREE_APP, false)) {
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
        List<Language> languages = getAllLanguages();
        Language language = getLanguage(languages, SessionManager.getStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE));

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
        List<Language> languages = getAllLanguages();
        ArrayList<MappedQuote> mTempData = mappedQuoteArrayList;
        if (mTempData.size() > 0) {
            if (SessionManager.getBooleanSetting(getGlobalContext(), SESSION_FREE_APP, false)) {
                ArrayList<MappedQuote> arrAd = DataHandler.getAllMappedQuoteAdvertises();
                int index = 0;
                Language language = getLanguage(languages, SessionManager.getStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE));
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
        List<Language> languages = getAllLanguages();
        ArrayList<MappedQuote> tempMappedQuotes = DataHandler.getAllMappedQuotesWithAdvertise(DataHandler.getAllMappedQuotes());
        Language language = getLanguage(languages, SessionManager.getStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE));

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
        List<Language> languages = getAllLanguages();
        ArrayList<MappedQuote> tempMappedQuotes = DataHandler.getAllMappedFavouriteQuotesWithAdvertise(DataHandler.getAllMappedFavouriteQuotes());

        Language language = getLanguage(languages, SessionManager.getStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE));
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
        List<Language> languages = getAllLanguages();
        ArrayList<Author> mAuthors = getAllAuthors();

        Language language = getLanguage(languages, SessionManager.getStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE));
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
        List<Language> languages = getAllLanguages();
        ArrayList<MappedQuote> mTempData = mappedQuoteArrayList;
        if (mTempData.size() > 0) {
            if (SessionManager.getBooleanSetting(getGlobalContext(), SESSION_FREE_APP, false)) {
                ArrayList<MappedQuote> arrAd = DataHandler.getAllMappedQuoteAdvertises();
                int index = 0;
                Language language = getLanguage(languages, SessionManager.getStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE));

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
            if (SessionManager.getBooleanSetting(getGlobalContext(), SESSION_FREE_APP, false)) {
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