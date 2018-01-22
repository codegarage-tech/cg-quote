package tech.codegarage.quotes.model.database;

import android.util.Log;

import com.kannan.glazy.GlazyCard;
import com.kannan.glazy.views.GlazyImageView;
import com.reversecoder.library.storage.SessionManager;
import com.reversecoder.library.util.AllSettingsManager;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import tech.codegarage.quotes.R;
import tech.codegarage.quotes.activity.AmazingTodayActivity;

import static tech.codegarage.quotes.application.QuoteApp.getGlobalContext;
import static tech.codegarage.quotes.util.AllConstants.SESSION_DATA_DATA_BUILDER;
import static tech.codegarage.quotes.util.AllConstants.SESSION_QUOTE_OF_THE_DAY;

public class LitePalDataHandler {

    private static String TAG = LitePalDataHandler.class.getSimpleName();

    /**************
     * Data input *
     **************/
    public static ArrayList<LitePalDataBuilder> initAllQuotes() {
        ArrayList<LitePalDataBuilder> litePalDataBuilders = new ArrayList<>();

        litePalDataBuilders.add(
                new LitePalDataBuilder()
                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
                        .setLitePalAuthor(EnumAuthor.APJ_ABDUL_KALAM.getLitePalAuthor())
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Teaching is a very noble profession that shapes the character, caliber, and future of an individual. If the people remember me as a good teacher, that will be the biggest honour for me.", false, true))
                                .addLitePalTags(EnumTag.FUTURE.getLitePalTag())
                                .addLitePalTags(EnumTag.TEACHER.getLitePalTag())
                                .addLitePalTags(EnumTag.CHARACTER.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("We should not give up and we should not allow the problem to defeat us.", false, true))
                                .addLitePalTags(EnumTag.MOTIVATIONAL.getLitePalTag())
                                .addLitePalTags(EnumTag.DEFEAT.getLitePalTag())
                                .addLitePalTags(EnumTag.GIVE.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Let us sacrifice our today so that our children can have a better tomorrow.", false, true))
                                .addLitePalTags(EnumTag.INSPIRATIONAL.getLitePalTag())
                                .addLitePalTags(EnumTag.TODAY.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("If you want to shine like a sun, first burn like a sun.", false, true))
                                .addLitePalTags(EnumTag.SUN.getLitePalTag())
                                .addLitePalTags(EnumTag.SHINE.getLitePalTag())
                                .addLitePalTags(EnumTag.WANT.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("If a country is to be corruption free and become a nation of beautiful minds, I strongly feel there are three key societal members who can make a difference. They are the father, the mother and the teacher.", false, true))
                                .addLitePalTags(EnumTag.FAMILY.getLitePalTag())
                                .addLitePalTags(EnumTag.BEAUTIFUL.getLitePalTag())
                                .addLitePalTags(EnumTag.FATHER.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("To succeed in your mission, you must have single-minded devotion to your goal.", false, true))
                                .addLitePalTags(EnumTag.GOAL.getLitePalTag())
                                .addLitePalTags(EnumTag.MISSION.getLitePalTag())
                                .addLitePalTags(EnumTag.YOU.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Look at the sky. We are not alone. The whole universe is friendly to us and conspires only to give the best to those who dream and work.", false, true))
                                .addLitePalTags(EnumTag.WORK.getLitePalTag())
                                .addLitePalTags(EnumTag.BEST.getLitePalTag())
                                .addLitePalTags(EnumTag.ALONE.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Never stop fighting until you arrive at your destined place - that is, the unique you. Have an aim in life, continuously acquire knowledge, work hard, and have perseverance to realise the great life.", false, true))
                                .addLitePalTags(EnumTag.LIFE.getLitePalTag())
                                .addLitePalTags(EnumTag.WORK.getLitePalTag())
                                .addLitePalTags(EnumTag.PERSEVERANCE.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Science is a beautiful gift to humanity; we should not distort it.", false, true))
                                .addLitePalTags(EnumTag.BEAUTIFUL.getLitePalTag())
                                .addLitePalTags(EnumTag.SCIENCE.getLitePalTag())
                                .addLitePalTags(EnumTag.GIFT.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("The bird is powered by its own life and by its motivation.", false, true))
                                .addLitePalTags(EnumTag.INSPIRATIONAL.getLitePalTag())
                                .addLitePalTags(EnumTag.LIFE.getLitePalTag())
                                .addLitePalTags(EnumTag.BIRD.getLitePalTag())
                                .buildQuotes()
                        )
        );

        litePalDataBuilders.add(
                new LitePalDataBuilder()
                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
                        .setLitePalAuthor(EnumAuthor.ALBERT_CAMUS.getLitePalAuthor())
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Don't walk behind me; I may not lead. Don't walk in front of me; I may not follow. Just walk Autumn is a second spring when every leaf is a flower.", false, true))
                                .addLitePalTags(EnumTag.NATURE.getLitePalTag())
                                .addLitePalTags(EnumTag.FLOWER.getLitePalTag())
                                .addLitePalTags(EnumTag.SPRING.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Don't walk behind me; I may not lead. Don't walk in front of me; I may not follow. Just walk beside me and be my friend.", false, true))
                                .addLitePalTags(EnumTag.FRIENDSHIP.getLitePalTag())
                                .addLitePalTags(EnumTag.ME.getLitePalTag())
                                .addLitePalTags(EnumTag.WALK.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Blessed are the hearts that can bend; they shall never be broken.", false, true))
                                .addLitePalTags(EnumTag.MOVING_ON.getLitePalTag())
                                .addLitePalTags(EnumTag.BLESSED.getLitePalTag())
                                .addLitePalTags(EnumTag.BROKEN.getLitePalTag())
                                .buildQuotes()
                        )

                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("The only way to deal with an unfree world is to become so absolutely free that your very existence is an act of rebellion.", false, true))
                                .addLitePalTags(EnumTag.FREEDOM.getLitePalTag())
                                .addLitePalTags(EnumTag.FREE.getLitePalTag())
                                .addLitePalTags(EnumTag.WORLD.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("In the depth of winter I finally learned that there was in me an invincible summer.", false, true))
                                .addLitePalTags(EnumTag.WINTER.getLitePalTag())
                                .addLitePalTags(EnumTag.NATURE.getLitePalTag())
                                .addLitePalTags(EnumTag.ME.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("A man without ethics is a wild beast loosed upon this world.", false, true))
                                .addLitePalTags(EnumTag.MAN.getLitePalTag())
                                .addLitePalTags(EnumTag.ETHICS.getLitePalTag())
                                .addLitePalTags(EnumTag.WORLD.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("You will never be happy if you continue to search for what happiness consists of. You will never live if you are looking for the meaning of life.", false, true))
                                .addLitePalTags(EnumTag.LIFE.getLitePalTag())
                                .addLitePalTags(EnumTag.HAPPINESS.getLitePalTag())
                                .addLitePalTags(EnumTag.HAPPY.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Integrity has no need of rules.", false, true))
                                .addLitePalTags(EnumTag.INTEGRITY.getLitePalTag())
                                .addLitePalTags(EnumTag.RULES.getLitePalTag())
                                .addLitePalTags(EnumTag.NEED.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Real generosity toward the future lies in giving all to the present.", false, true))
                                .addLitePalTags(EnumTag.FUTURE.getLitePalTag())
                                .addLitePalTags(EnumTag.GIVING.getLitePalTag())
                                .addLitePalTags(EnumTag.GENEROSITY.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("The evil that is in the world almost always comes of ignorance, and good intentions may do as much harm as malevolence if they lack understanding.", false, true))
                                .addLitePalTags(EnumTag.GOOD.getLitePalTag())
                                .addLitePalTags(EnumTag.IGNORANCE.getLitePalTag())
                                .addLitePalTags(EnumTag.WORLD.getLitePalTag())
                                .buildQuotes()
                        )
        );

        litePalDataBuilders.add(
                new LitePalDataBuilder()
                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
                        .setLitePalAuthor(EnumAuthor.ARISTOTLE.getLitePalAuthor())
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("It is during our darkest moments that we must focus to see the light.", false, true))
                                .addLitePalTags(EnumTag.INSPIRATIONAL.getLitePalTag())
                                .addLitePalTags(EnumTag.LIGHT.getLitePalTag())
                                .addLitePalTags(EnumTag.FOCUS.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Quality is not an act, it is a habit.", false, true))
                                .addLitePalTags(EnumTag.MOTIVATIONAL.getLitePalTag())
                                .addLitePalTags(EnumTag.QUALITY.getLitePalTag())
                                .addLitePalTags(EnumTag.HABIT.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("The roots of education are bitter, but the fruit is sweet.", false, true))
                                .addLitePalTags(EnumTag.EDUCATION.getLitePalTag())
                                .addLitePalTags(EnumTag.ROOTS.getLitePalTag())
                                .addLitePalTags(EnumTag.SWEET.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("It is the mark of an educated mind to be able to entertain a thought without accepting it.", false, true))
                                .addLitePalTags(EnumTag.EDUCATION.getLitePalTag())
                                .addLitePalTags(EnumTag.THOUGHT.getLitePalTag())
                                .addLitePalTags(EnumTag.MIND.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Excellence is an art won by training and habituation. We do not act rightly because we have virtue or excellence, but we rather have those because we have acted rightly. We are what we repeatedly do. Excellence, then, is not an act but a habit.", false, true))
                                .addLitePalTags(EnumTag.ART.getLitePalTag())
                                .addLitePalTags(EnumTag.TRAINING.getLitePalTag())
                                .addLitePalTags(EnumTag.EXCELLENCE.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Pleasure in the job puts perfection in the work.", false, true))
                                .addLitePalTags(EnumTag.WORK.getLitePalTag())
                                .addLitePalTags(EnumTag.JOB.getLitePalTag())
                                .addLitePalTags(EnumTag.PERFECTION.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("There is no great genius without some touch of madness.", false, true))
                                .addLitePalTags(EnumTag.INTELLIGENCE.getLitePalTag())
                                .addLitePalTags(EnumTag.GREAT.getLitePalTag())
                                .addLitePalTags(EnumTag.GENIUS.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Love is composed of a single soul inhabiting two bodies.", false, true))
                                .addLitePalTags(EnumTag.LOVE.getLitePalTag())
                                .addLitePalTags(EnumTag.LOVE_IS.getLitePalTag())
                                .addLitePalTags(EnumTag.SOUL.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("The one exclusive sign of thorough knowledge is the power of teaching.", false, true))
                                .addLitePalTags(EnumTag.TEACHER.getLitePalTag())
                                .addLitePalTags(EnumTag.KNOWLEDGE.getLitePalTag())
                                .addLitePalTags(EnumTag.POWER.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("The worst form of inequality is to try to make unequal things equal.", false, true))
                                .addLitePalTags(EnumTag.EQUALITY.getLitePalTag())
                                .addLitePalTags(EnumTag.INEQUALITY.getLitePalTag())
                                .addLitePalTags(EnumTag.TRY.getLitePalTag())
                                .buildQuotes()
                        )
        );

        litePalDataBuilders.add(
                new LitePalDataBuilder()
                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
                        .setLitePalAuthor(EnumAuthor.AUDREY_HEPBURN.getLitePalAuthor())
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Nothing is impossible, the word itself says 'I'm possible'!", false, true))
                                .addLitePalTags(EnumTag.INSPIRATIONAL.getLitePalTag())
                                .addLitePalTags(EnumTag.IMPOSSIBLE.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("The beauty of a woman must be seen from in her eyes, because that is the doorway to her heart, the place where love resides.", false, true))
                                .addLitePalTags(EnumTag.LOVE.getLitePalTag())
                                .addLitePalTags(EnumTag.BEAUTY.getLitePalTag())
                                .addLitePalTags(EnumTag.EYES.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("For beautiful eyes, look for the good in others; for beautiful lips, speak only words of kindness; and for poise, walk with the knowledge that you are never alone.", false, true))
                                .addLitePalTags(EnumTag.KINDNESS.getLitePalTag())
                                .addLitePalTags(EnumTag.WISDOM.getLitePalTag())
                                .addLitePalTags(EnumTag.BEAUTIFUL.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("The most important thing is to enjoy your life - to be happy - it's all that matters.", false, true))
                                .addLitePalTags(EnumTag.LIFE.getLitePalTag())
                                .addLitePalTags(EnumTag.HAPPY.getLitePalTag())
                                .addLitePalTags(EnumTag.BE_HAPPY.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("The beauty of a woman is not in a facial mode but the true beauty in a woman is reflected in her soul. It is the caring that she lovingly gives the passion that she shows. The beauty of a woman grows with the passing years.", false, true))
                                .addLitePalTags(EnumTag.BEAUTY.getLitePalTag())
                                .addLitePalTags(EnumTag.PASSION.getLitePalTag())
                                .addLitePalTags(EnumTag.SOUL.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("I believe in pink. I believe that laughing is the best calorie burner. I believe in kissing, kissing a lot. I believe in being strong when everything seems to be going wrong. I believe that happy girls are the prettiest girls. I believe that tomorrow is another day and I believe in miracles.", false, true))
                                .addLitePalTags(EnumTag.INSPIRATIONAL.getLitePalTag())
                                .addLitePalTags(EnumTag.DAY.getLitePalTag())
                                .addLitePalTags(EnumTag.HAPPY.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("The best thing to hold onto in life is each other. ", false, true))
                                .addLitePalTags(EnumTag.LOVE.getLitePalTag())
                                .addLitePalTags(EnumTag.LIFE.getLitePalTag())
                                .addLitePalTags(EnumTag.BEST.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("The beauty of a woman is not in the clothes she wears, the figure that she carries or the way she combs her hair.", false, true))
                                .addLitePalTags(EnumTag.BEAUTY.getLitePalTag())
                                .addLitePalTags(EnumTag.HAIR.getLitePalTag())
                                .addLitePalTags(EnumTag.WOMAN.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("I believe in manicures. I believe in overdressing. I believe in primping at leisure and wearing lipstick. I believe in pink. I believe happy girls are the prettiest girls. I believe that tomorrow is another day, and... I believe in miracles.", false, true))
                                .addLitePalTags(EnumTag.DAY.getLitePalTag())
                                .addLitePalTags(EnumTag.HAPPY.getLitePalTag())
                                .addLitePalTags(EnumTag.TOMORROW.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Let's face it, a nice creamy chocolate cake does a lot for a lot of people; it does for me.", false, true))
                                .addLitePalTags(EnumTag.FOOD.getLitePalTag())
                                .addLitePalTags(EnumTag.ME.getLitePalTag())
                                .addLitePalTags(EnumTag.CAKE.getLitePalTag())
                                .buildQuotes()
                        )
        );

        litePalDataBuilders.add(
                new LitePalDataBuilder()
                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
                        .setLitePalAuthor(EnumAuthor.ABRAHAM_LINCOLN.getLitePalAuthor())
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("If this is coffee, please bring me some tea; but if this is tea, please bring me some coffee.", false, true))
                                .addLitePalTags(EnumTag.FUNNY.getLitePalTag())
                                .addLitePalTags(EnumTag.COFFEE.getLitePalTag())
                                .addLitePalTags(EnumTag.TEA.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Character is like a tree and reputation like a shadow. The shadow is what we think of it; the tree is the real thing.", false, true))
                                .addLitePalTags(EnumTag.CHARACTER.getLitePalTag())
                                .addLitePalTags(EnumTag.TREE.getLitePalTag())
                                .addLitePalTags(EnumTag.SHADOW.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("I am a slow walker, but I never walk back.", false, true))
                                .addLitePalTags(EnumTag.I_AM.getLitePalTag())
                                .addLitePalTags(EnumTag.WALK.getLitePalTag())
                                .addLitePalTags(EnumTag.SLOW.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("My dream is of a place and a time where America will once again be seen as the last best hope of earth.", false, true))
                                .addLitePalTags(EnumTag.HOPE.getLitePalTag())
                                .addLitePalTags(EnumTag.TIME.getLitePalTag())
                                .addLitePalTags(EnumTag.BEST.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Be sure you put your feet in the right place, then stand firm.", false, true))
                                .addLitePalTags(EnumTag.STRENGTH.getLitePalTag())
                                .addLitePalTags(EnumTag.STAND.getLitePalTag())
                                .addLitePalTags(EnumTag.FEET.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("You cannot escape the responsibility of tomorrow by evading it today.", false, true))
                                .addLitePalTags(EnumTag.FUTURE.getLitePalTag())
                                .addLitePalTags(EnumTag.TODAY.getLitePalTag())
                                .addLitePalTags(EnumTag.TOMORROW.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("No man has a good enough memory to be a successful liar.", false, true))
                                .addLitePalTags(EnumTag.FUNNY.getLitePalTag())
                                .addLitePalTags(EnumTag.GOOD.getLitePalTag())
                                .addLitePalTags(EnumTag.MAN.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("All that I am, or hope to be, I owe to my angel mother.", false, true))
                                .addLitePalTags(EnumTag.HOPE.getLitePalTag())
                                .addLitePalTags(EnumTag.MOTHER.getLitePalTag())
                                .addLitePalTags(EnumTag.I_AM.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Nearly all men can stand adversity, but if you want to test a man's character, give him power.", false, true))
                                .addLitePalTags(EnumTag.CHARACTER.getLitePalTag())
                                .addLitePalTags(EnumTag.ADVERSITY.getLitePalTag())
                                .addLitePalTags(EnumTag.MAN.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("America will never be destroyed from the outside. If we falter and lose our freedoms, it will be because we destroyed ourselves.", false, true))
                                .addLitePalTags(EnumTag.FREEDOM.getLitePalTag())
                                .addLitePalTags(EnumTag.AMERICA.getLitePalTag())
                                .addLitePalTags(EnumTag.WILL.getLitePalTag())
                                .buildQuotes()
                        )
        );
        litePalDataBuilders.add(
                new LitePalDataBuilder()
                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
                        .setLitePalAuthor(EnumAuthor.ALDOUS_HUXLEY.getLitePalAuthor())
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("There is only one corner of the universe you can be certain of improving, and that's your own self.", false, true))
                                .addLitePalTags(EnumTag.MOTIVATIONAL.getLitePalTag())
                                .addLitePalTags(EnumTag.SELF.getLitePalTag())
                                .addLitePalTags(EnumTag.UNIVERSE.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("There are things known and there are things unknown, and in between are the doors of perception.", false, true))
                                .addLitePalTags(EnumTag.PERCEPTION.getLitePalTag())
                                .addLitePalTags(EnumTag.BRAINY.getLitePalTag())
                                .addLitePalTags(EnumTag.DOORS.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("The secret of genius is to carry the spirit of the child into old age, which means never losing your enthusiasm.", false, true))
                                .addLitePalTags(EnumTag.ATTITUDE.getLitePalTag())
                                .addLitePalTags(EnumTag.AGE.getLitePalTag())
                                .addLitePalTags(EnumTag.CHILD.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("After silence, that which comes nearest to expressing the inexpressible is music.", false, true))
                                .addLitePalTags(EnumTag.MUSIC.getLitePalTag())
                                .addLitePalTags(EnumTag.SILENCE.getLitePalTag())
                                .addLitePalTags(EnumTag.EXPRESSING.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("To travel is to discover that everyone is wrong about other countries.", false, true))
                                .addLitePalTags(EnumTag.TRAVEL.getLitePalTag())
                                .addLitePalTags(EnumTag.WRONG.getLitePalTag())
                                .addLitePalTags(EnumTag.DISCOVER.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Experience is not what happens to you; it's what you do with what happens to you.", false, true))
                                .addLitePalTags(EnumTag.WISDOM.getLitePalTag())
                                .addLitePalTags(EnumTag.EXPERIENCE.getLitePalTag())
                                .addLitePalTags(EnumTag.YOU.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Facts do not cease to exist because they are ignored.", false, true))
                                .addLitePalTags(EnumTag.FACTS.getLitePalTag())
                                .addLitePalTags(EnumTag.EXIST.getLitePalTag())
                                .addLitePalTags(EnumTag.BECAUSE.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("The more powerful and original a mind, the more it will incline towards the religion of solitude.", false, true))
                                .addLitePalTags(EnumTag.RELIGION.getLitePalTag())
                                .addLitePalTags(EnumTag.POWERFUL.getLitePalTag())
                                .addLitePalTags(EnumTag.MIND.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("All gods are homemade, and it is we who pull their strings, and so, give them the power to pull ours.", false, true))
                                .addLitePalTags(EnumTag.POWER.getLitePalTag())
                                .addLitePalTags(EnumTag.GIVE.getLitePalTag())
                                .addLitePalTags(EnumTag.GOD.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("A democracy which makes or even effectively prepares for modern, scientific war must necessarily cease to be democratic. No country can be really well prepared for modern war unless it is governed by a tyrant, at the head of a highly trained and perfectly obedient bureaucracy.", false, true))
                                .addLitePalTags(EnumTag.WAR.getLitePalTag())
                                .addLitePalTags(EnumTag.DEMOCRACY.getLitePalTag())
                                .addLitePalTags(EnumTag.COUNTRY.getLitePalTag())
                                .buildQuotes()
                        )
        );
        litePalDataBuilders.add(
                new LitePalDataBuilder()
                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
                        .setLitePalAuthor(EnumAuthor.ALEXANDER_HAMILTON.getLitePalAuthor())
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Learn to think continentally.", false, true))
                                .addLitePalTags(EnumTag.EDUCATION.getLitePalTag())
                                .addLitePalTags(EnumTag.LEARN.getLitePalTag())
                                .addLitePalTags(EnumTag.THINK.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Real liberty is neither found in despotism or the extremes of democracy, but in moderate governments.", false, true))
                                .addLitePalTags(EnumTag.DEMOCRACY.getLitePalTag())
                                .addLitePalTags(EnumTag.LIBERTY.getLitePalTag())
                                .addLitePalTags(EnumTag.REAL.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("In framing a government which is to be administered by men over men, the great difficulty lies in this: you must first enable the government to control the governed; and in the next place, oblige it to control itself.", false, true))
                                .addLitePalTags(EnumTag.GREAT.getLitePalTag())
                                .addLitePalTags(EnumTag.GOVERNMENT.getLitePalTag())
                                .addLitePalTags(EnumTag.MEN.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("A promise must never be broken.", false, true))
                                .addLitePalTags(EnumTag.BROKEN.getLitePalTag())
                                .addLitePalTags(EnumTag.PROMISE.getLitePalTag())
                                .addLitePalTags(EnumTag.NEVER.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("It's not tyranny we desire; it's a just, limited, federal government.", false, true))
                                .addLitePalTags(EnumTag.GOVERNMENT.getLitePalTag())
                                .addLitePalTags(EnumTag.DESIRE.getLitePalTag())
                                .addLitePalTags(EnumTag.TYRANNY.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("I never expect to see a perfect work from an imperfect man.", false, true))
                                .addLitePalTags(EnumTag.WORK.getLitePalTag())
                                .addLitePalTags(EnumTag.MAN.getLitePalTag())
                                .addLitePalTags(EnumTag.PERFECT.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("I think the first duty of society is justice.", false, true))
                                .addLitePalTags(EnumTag.JUSTICE.getLitePalTag())
                                .addLitePalTags(EnumTag.SOCIETY.getLitePalTag())
                                .addLitePalTags(EnumTag.FIRST.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("A well adjusted person is one who makes the same mistake twice without getting nervous.", false, true))
                                .addLitePalTags(EnumTag.MISTAKE.getLitePalTag())
                                .addLitePalTags(EnumTag.NERVOUS.getLitePalTag())
                                .addLitePalTags(EnumTag.SAME.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Unless your government is respectable, foreigners will invade your rights; and to maintain tranquillity, it must be respectable - even to observe neutrality, you must have a strong government.", false, true))
                                .addLitePalTags(EnumTag.STRONG.getLitePalTag())
                                .addLitePalTags(EnumTag.GOVERNMENT.getLitePalTag())
                                .addLitePalTags(EnumTag.YOU.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("There is a certain enthusiasm in liberty, that makes human nature rise above itself, in acts of bravery and heroism.", false, true))
                                .addLitePalTags(EnumTag.NATURE.getLitePalTag())
                                .addLitePalTags(EnumTag.LIBERTY.getLitePalTag())
                                .addLitePalTags(EnumTag.RISE.getLitePalTag())
                                .buildQuotes()
                        )
        );
//        ////////////////////////////////
//        litePalDataBuilders.add(
//                new LitePalDataBuilder()
//                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
//                        .setLitePalAuthor(EnumAuthor.ALEXANDER_POPE.getLitePalAuthor())
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("The greatest magnifying glasses in the world are a man's own eyes when they look upon his own person.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Fools rush in where angels fear to tread.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("All nature is but art unknown to thee.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("No one should be ashamed to admit they are wrong, which is but saying, in other words, that they are wiser today than they were yesterday.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("No woman ever hates a man for being in love with her, but many a woman hate a man for being a friend to her.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Teach me to feel another's woe, to hide the fault I see, that mercy I to others show, that mercy show to me.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Charms strike the sight, but merit wins the soul.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("On life's vast ocean diversely we sail. Reasons the card, but passion the gale.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("To be angry is to revenge the faults of others on ourselves.\n", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("To err is human; to forgive, divine.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//        );
//        litePalDataBuilders.add(
//                new LitePalDataBuilder()
//                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
//                        .setLitePalAuthor(EnumAuthor.ARNOLD_SCHWARZENEGGER.getLitePalAuthor())
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("The worst thing I can be is the same as everybody else. I hate that.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("It's simple, if it jiggles, it's fat.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Bodybuilding is much like any other sport. To be successful, you must dedicate yourself 100% to your training, diet and mental approach.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Help others and give something back. I guarantee you will discover that while public service improves the lives and the world around you, its greatest reward is the enrichment and new meaning it will bring your own life.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("The mind is the limit. As long as the mind can envision the fact that you can do something, you can do it, as long as you really believe 100 percent.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("The future is green energy, sustainability, renewable energy.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Milk is for babies. When you grow up you have to drink beer.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("For me life is continuously being hungry. The meaning of life is not simply to exist, to survive, but to move ahead, to go up, to achieve, to conquer.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Just remember, you can't climb the ladder of success with your hands in your pockets.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Strength does not come from winning. Your struggles develop your strengths. When you go through hardships and decide not to surrender, that is strength.", false, true))
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .addLitePalTags(EnumTag..getLitePalTag())
//                                .buildQuotes()
//                        )
//        );
//        litePalDataBuilders.add(
//                new LitePalDataBuilder()
//                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
//                        .setLitePalAuthor(EnumAuthor.BARACK_OBAMA.getLitePalAuthor())
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Change will not come if we wait for some other person or some other time. We are the ones we've been waiting for. We are the change that we seek.", false, true))
//                                .addLitePalTags(EnumTag.MOTIVATIONAL.getLitePalTag())
//                                .addLitePalTags(EnumTag.SELF.getLitePalTag())
//                                .addLitePalTags(EnumTag.UNIVERSE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("I see Americans of every party, every background, every faith who believe that we are stronger together: black, white, Latino, Asian, Native American; young, old; gay, straight; men, women, folks with disabilities, all pledging allegiance under the same proud flag to this big, bold country that we love. That's what I see. That's the America I know!", false, true))
//                                .addLitePalTags(EnumTag.PERCEPTION.getLitePalTag())
//                                .addLitePalTags(EnumTag.BRAINY.getLitePalTag())
//                                .addLitePalTags(EnumTag.DOORS.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("We need to reject any politics that targets people because of race or religion. This isn't a matter of political correctness. It's a matter of understanding what makes us strong. The world respects us not just for our arsenal; it respects us for our diversity and our openness and the way we respect every faith.", false, true))
//                                .addLitePalTags(EnumTag.ATTITUDE.getLitePalTag())
//                                .addLitePalTags(EnumTag.AGE.getLitePalTag())
//                                .addLitePalTags(EnumTag.CHILD.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Let us remember we are all part of one American family. We are united in common values, and that includes belief in equality under the law, basic respect for public order, and the right of peaceful protest.", false, true))
//                                .addLitePalTags(EnumTag.MUSIC.getLitePalTag())
//                                .addLitePalTags(EnumTag.SILENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXPRESSING.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("If you're walking down the right path and you're willing to keep walking, eventually you'll make progress.", false, true))
//                                .addLitePalTags(EnumTag.TRAVEL.getLitePalTag())
//                                .addLitePalTags(EnumTag.WRONG.getLitePalTag())
//                                .addLitePalTags(EnumTag.DISCOVER.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("If the people cannot trust their government to do the job for which it exists - to protect them and to promote their common welfare - all else is lost.\n", false, true))
//                                .addLitePalTags(EnumTag.WISDOM.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXPERIENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.YOU.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("The future rewards those who press on. I don't have time to feel sorry for myself. I don't have time to complain. I'm going to press on.", false, true))
//                                .addLitePalTags(EnumTag.FACTS.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXIST.getLitePalTag())
//                                .addLitePalTags(EnumTag.BECAUSE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Money is not the only answer, but it makes a difference.", false, true))
//                                .addLitePalTags(EnumTag.RELIGION.getLitePalTag())
//                                .addLitePalTags(EnumTag.POWERFUL.getLitePalTag())
//                                .addLitePalTags(EnumTag.MIND.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("America isn't Congress. America isn't Washington. America is the striving immigrant who starts a business, or the mom who works two low-wage jobs to give her kid a better life. America is the union leader and the CEO who put aside their differences to make the economy stronger.", false, true))
//                                .addLitePalTags(EnumTag.POWER.getLitePalTag())
//                                .addLitePalTags(EnumTag.GIVE.getLitePalTag())
//                                .addLitePalTags(EnumTag.GOD.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("After a century of striving, after a year of debate, after a historic vote, health care reform is no longer an unmet promise. It is the law of the land.", false, true))
//                                .addLitePalTags(EnumTag.WAR.getLitePalTag())
//                                .addLitePalTags(EnumTag.DEMOCRACY.getLitePalTag())
//                                .addLitePalTags(EnumTag.COUNTRY.getLitePalTag())
//                                .buildQuotes()
//                        )
//        );
//        litePalDataBuilders.add(
//                new LitePalDataBuilder()
//                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
//                        .setLitePalAuthor(EnumAuthor.BEN_SHAPIRO.getLitePalAuthor())
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("During the Great Depression, levels of crime actually dropped. During the 1920s, when life was free and easy, so was crime. During the 1930s, when the entire American economy fell into a government-owned alligator moat, crime was nearly non-existent. During the 1950s and 1960s, when the economy was excellent, crime rose again.", false, true))
//                                .addLitePalTags(EnumTag.MOTIVATIONAL.getLitePalTag())
//                                .addLitePalTags(EnumTag.SELF.getLitePalTag())
//                                .addLitePalTags(EnumTag.UNIVERSE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Socialism violates at least three of the Ten Commandments: It turns government into God, it legalizes thievery and it elevates covetousness. Discussions of income inequality, after all, aren't about prosperity but about petty spite. Why should you care how much money I make, so long as you are happy?", false, true))
//                                .addLitePalTags(EnumTag.PERCEPTION.getLitePalTag())
//                                .addLitePalTags(EnumTag.BRAINY.getLitePalTag())
//                                .addLitePalTags(EnumTag.DOORS.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Distrust of government isn't baseless cynicism. It's realism.", false, true))
//                                .addLitePalTags(EnumTag.ATTITUDE.getLitePalTag())
//                                .addLitePalTags(EnumTag.AGE.getLitePalTag())
//                                .addLitePalTags(EnumTag.CHILD.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Matt Damon's anti-fracking diatribe was funded by the royal family of the United Arab Emirates.", false, true))
//                                .addLitePalTags(EnumTag.MUSIC.getLitePalTag())
//                                .addLitePalTags(EnumTag.SILENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXPRESSING.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Capitalism requires individual responsibility and accountability. People are seen as atomized units in a capitalist system - they are either useful, or they are not. They are not seen racially or ethnically or religiously. They consume and they produce, and those are their only relevant characteristics.", false, true))
//                                .addLitePalTags(EnumTag.TRAVEL.getLitePalTag())
//                                .addLitePalTags(EnumTag.WRONG.getLitePalTag())
//                                .addLitePalTags(EnumTag.DISCOVER.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("The story of Detroit's bankruptcy was simple enough: Allow capitalism to grow the city, campaign against income inequality, tax the job creators until they flee, increase government spending in order to boost employment, promise generous pension plans to keep people voting for failure. Rinse, wash and repeat.", false, true))
//                                .addLitePalTags(EnumTag.WISDOM.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXPERIENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.YOU.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Socialism has no moral justification whatsoever; poor people are not morally superior to rich people, nor are they owed anything by rich people simply because of their lack of success. Charity is not a socialist concept - it is a religious one, an acknowledgment of God's sovereignty over property, a sovereignty the Left utterly rejects.", false, true))
//                                .addLitePalTags(EnumTag.FACTS.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXIST.getLitePalTag())
//                                .addLitePalTags(EnumTag.BECAUSE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Every so often, we all gaze into the abyss. It's a depressing fact of life that eventually the clock expires; eventually the sand in the hourglass runs out. It's the leaving behind of everything that matters to us that hurts the most.", false, true))
//                                .addLitePalTags(EnumTag.RELIGION.getLitePalTag())
//                                .addLitePalTags(EnumTag.POWERFUL.getLitePalTag())
//                                .addLitePalTags(EnumTag.MIND.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Socialism states that you owe me something simply because I exist. Capitalism, by contrast, results in a sort of reality-forced altruism: I may not want to help you, I may dislike you, but if I don't give you a product or service you want, I will starve. Voluntary exchange is more moral than forced redistribution.", false, true))
//                                .addLitePalTags(EnumTag.POWER.getLitePalTag())
//                                .addLitePalTags(EnumTag.GIVE.getLitePalTag())
//                                .addLitePalTags(EnumTag.GOD.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Freedom of speech and thought matters, especially when it is speech and thought with which we disagree. The moment the majority decides to destroy people for engaging in thought it dislikes, thought crime becomes a reality.", false, true))
//                                .addLitePalTags(EnumTag.WAR.getLitePalTag())
//                                .addLitePalTags(EnumTag.DEMOCRACY.getLitePalTag())
//                                .addLitePalTags(EnumTag.COUNTRY.getLitePalTag())
//                                .buildQuotes()
//                        )
//        );
//        litePalDataBuilders.add(
//                new LitePalDataBuilder()
//                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
//                        .setLitePalAuthor(EnumAuthor.BENJAMIN_DISRAELI.getLitePalAuthor())
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("A great city, whose image dwells in the memory of man, is the type of some great idea. Rome represents conquest; Faith hovers over the towers of Jerusalem; and Athens embodies the pre-eminent quality of the antique world, Art.", false, true))
//                                .addLitePalTags(EnumTag.MOTIVATIONAL.getLitePalTag())
//                                .addLitePalTags(EnumTag.SELF.getLitePalTag())
//                                .addLitePalTags(EnumTag.UNIVERSE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Power has only one duty - to secure the social welfare of the People.", false, true))
//                                .addLitePalTags(EnumTag.PERCEPTION.getLitePalTag())
//                                .addLitePalTags(EnumTag.BRAINY.getLitePalTag())
//                                .addLitePalTags(EnumTag.DOORS.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Never apologize for showing feeling. When you do so, you apologize for the truth.", false, true))
//                                .addLitePalTags(EnumTag.ATTITUDE.getLitePalTag())
//                                .addLitePalTags(EnumTag.AGE.getLitePalTag())
//                                .addLitePalTags(EnumTag.CHILD.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("The secret of success is constancy to purpose.", false, true))
//                                .addLitePalTags(EnumTag.MUSIC.getLitePalTag())
//                                .addLitePalTags(EnumTag.SILENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXPRESSING.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Like all great travellers, I have seen more than I remember, and remember more than I have seen.", false, true))
//                                .addLitePalTags(EnumTag.TRAVEL.getLitePalTag())
//                                .addLitePalTags(EnumTag.WRONG.getLitePalTag())
//                                .addLitePalTags(EnumTag.DISCOVER.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Youth is a blunder; Manhood a struggle, Old Age a regret.", false, true))
//                                .addLitePalTags(EnumTag.WISDOM.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXPERIENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.YOU.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Nurture your minds with great thoughts. To believe in the heroic makes heroes.", false, true))
//                                .addLitePalTags(EnumTag.FACTS.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXIST.getLitePalTag())
//                                .addLitePalTags(EnumTag.BECAUSE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Seeing much, suffering much, and studying much, are the three pillars of learning.", false, true))
//                                .addLitePalTags(EnumTag.RELIGION.getLitePalTag())
//                                .addLitePalTags(EnumTag.POWERFUL.getLitePalTag())
//                                .addLitePalTags(EnumTag.MIND.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Never complain and never explain.", false, true))
//                                .addLitePalTags(EnumTag.POWER.getLitePalTag())
//                                .addLitePalTags(EnumTag.GIVE.getLitePalTag())
//                                .addLitePalTags(EnumTag.GOD.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Courage is fire, and bullying is smoke.", false, true))
//                                .addLitePalTags(EnumTag.WAR.getLitePalTag())
//                                .addLitePalTags(EnumTag.DEMOCRACY.getLitePalTag())
//                                .addLitePalTags(EnumTag.COUNTRY.getLitePalTag())
//                                .buildQuotes()
//                        )
//        );
//        litePalDataBuilders.add(
//                new LitePalDataBuilder()
//                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
//                        .setLitePalAuthor(EnumAuthor.BENJAMIN_FRANKLIN.getLitePalAuthor())
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Without freedom of thought, there can be no such thing as wisdom - and no such thing as public liberty without freedom of speech.", false, true))
//                                .addLitePalTags(EnumTag.MOTIVATIONAL.getLitePalTag())
//                                .addLitePalTags(EnumTag.SELF.getLitePalTag())
//                                .addLitePalTags(EnumTag.UNIVERSE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Early to bed and early to rise makes a man healthy, wealthy and wise.", false, true))
//                                .addLitePalTags(EnumTag.PERCEPTION.getLitePalTag())
//                                .addLitePalTags(EnumTag.BRAINY.getLitePalTag())
//                                .addLitePalTags(EnumTag.DOORS.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Either write something worth reading or do something worth writing.", false, true))
//                                .addLitePalTags(EnumTag.ATTITUDE.getLitePalTag())
//                                .addLitePalTags(EnumTag.AGE.getLitePalTag())
//                                .addLitePalTags(EnumTag.CHILD.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("By failing to prepare, you are preparing to fail.", false, true))
//                                .addLitePalTags(EnumTag.MUSIC.getLitePalTag())
//                                .addLitePalTags(EnumTag.SILENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXPRESSING.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Wine is constant proof that God loves us and loves to see us happy.", false, true))
//                                .addLitePalTags(EnumTag.TRAVEL.getLitePalTag())
//                                .addLitePalTags(EnumTag.WRONG.getLitePalTag())
//                                .addLitePalTags(EnumTag.DISCOVER.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("We are all born ignorant, but one must work hard to remain stupid.", false, true))
//                                .addLitePalTags(EnumTag.WISDOM.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXPERIENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.YOU.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Well done is better than well said.", false, true))
//                                .addLitePalTags(EnumTag.FACTS.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXIST.getLitePalTag())
//                                .addLitePalTags(EnumTag.BECAUSE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Without continual growth and progress, such words as improvement, achievement, and success have no meaning.", false, true))
//                                .addLitePalTags(EnumTag.RELIGION.getLitePalTag())
//                                .addLitePalTags(EnumTag.POWERFUL.getLitePalTag())
//                                .addLitePalTags(EnumTag.MIND.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Tell me and I forget. Teach me and I remember. Involve me and I learn.", false, true))
//                                .addLitePalTags(EnumTag.POWER.getLitePalTag())
//                                .addLitePalTags(EnumTag.GIVE.getLitePalTag())
//                                .addLitePalTags(EnumTag.GOD.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("An investment in knowledge pays the best interest.", false, true))
//                                .addLitePalTags(EnumTag.WAR.getLitePalTag())
//                                .addLitePalTags(EnumTag.DEMOCRACY.getLitePalTag())
//                                .addLitePalTags(EnumTag.COUNTRY.getLitePalTag())
//                                .buildQuotes()
//                        )
//        );
//        litePalDataBuilders.add(
//                new LitePalDataBuilder()
//                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
//                        .setLitePalAuthor(EnumAuthor.BERTRAND_RUSSELL.getLitePalAuthor())
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("I would never die for my beliefs because I might be wrong.", false, true))
//                                .addLitePalTags(EnumTag.MOTIVATIONAL.getLitePalTag())
//                                .addLitePalTags(EnumTag.SELF.getLitePalTag())
//                                .addLitePalTags(EnumTag.UNIVERSE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Science is what you know, philosophy is what you don't know.", false, true))
//                                .addLitePalTags(EnumTag.PERCEPTION.getLitePalTag())
//                                .addLitePalTags(EnumTag.BRAINY.getLitePalTag())
//                                .addLitePalTags(EnumTag.DOORS.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Men are born ignorant, not stupid. They are made stupid by education.", false, true))
//                                .addLitePalTags(EnumTag.ATTITUDE.getLitePalTag())
//                                .addLitePalTags(EnumTag.AGE.getLitePalTag())
//                                .addLitePalTags(EnumTag.CHILD.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("The only thing that will redeem mankind is cooperation.", false, true))
//                                .addLitePalTags(EnumTag.MUSIC.getLitePalTag())
//                                .addLitePalTags(EnumTag.SILENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXPRESSING.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("The world is full of magical things patiently waiting for our wits to grow sharper.", false, true))
//                                .addLitePalTags(EnumTag.TRAVEL.getLitePalTag())
//                                .addLitePalTags(EnumTag.WRONG.getLitePalTag())
//                                .addLitePalTags(EnumTag.DISCOVER.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Fear is the main source of superstition, and one of the main sources of cruelty. To conquer fear is the beginning of wisdom.", false, true))
//                                .addLitePalTags(EnumTag.WISDOM.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXPERIENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.YOU.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("The fact that an opinion has been widely held is no evidence whatever that it is not utterly absurd.", false, true))
//                                .addLitePalTags(EnumTag.FACTS.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXIST.getLitePalTag())
//                                .addLitePalTags(EnumTag.BECAUSE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("The trouble with the world is that the stupid are cocksure and the intelligent are full of doubt.", false, true))
//                                .addLitePalTags(EnumTag.RELIGION.getLitePalTag())
//                                .addLitePalTags(EnumTag.POWERFUL.getLitePalTag())
//                                .addLitePalTags(EnumTag.MIND.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("War does not determine who is right - only who is left.", false, true))
//                                .addLitePalTags(EnumTag.POWER.getLitePalTag())
//                                .addLitePalTags(EnumTag.GIVE.getLitePalTag())
//                                .addLitePalTags(EnumTag.GOD.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("The good life is one inspired by love and guided by knowledge.", false, true))
//                                .addLitePalTags(EnumTag.WAR.getLitePalTag())
//                                .addLitePalTags(EnumTag.DEMOCRACY.getLitePalTag())
//                                .addLitePalTags(EnumTag.COUNTRY.getLitePalTag())
//                                .buildQuotes()
//                        )
//        );
//        litePalDataBuilders.add(
//                new LitePalDataBuilder()
//                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
//                        .setLitePalAuthor(EnumAuthor.BEYONCE_KNOWLES.getLitePalAuthor())
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("If everything was perfect, you would never learn and you would never grow.", false, true))
//                                .addLitePalTags(EnumTag.MOTIVATIONAL.getLitePalTag())
//                                .addLitePalTags(EnumTag.SELF.getLitePalTag())
//                                .addLitePalTags(EnumTag.UNIVERSE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Who I am on stage is very, very different to who I am in real life.", false, true))
//                                .addLitePalTags(EnumTag.PERCEPTION.getLitePalTag())
//                                .addLitePalTags(EnumTag.BRAINY.getLitePalTag())
//                                .addLitePalTags(EnumTag.DOORS.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("I can never be safe; I always try and go against the grain. As soon as I accomplish one thing, I just set a higher goal. That's how I've gotten to where I am.", false, true))
//                                .addLitePalTags(EnumTag.ATTITUDE.getLitePalTag())
//                                .addLitePalTags(EnumTag.AGE.getLitePalTag())
//                                .addLitePalTags(EnumTag.CHILD.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("I think it's healthy for a person to be nervous. It means you care - that you work hard and want to give a great performance. You just have to channel that nervous energy into the show.", false, true))
//                                .addLitePalTags(EnumTag.MUSIC.getLitePalTag())
//                                .addLitePalTags(EnumTag.SILENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXPRESSING.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Whenever I'm confused about something, I ask God to reveal the answers to my questions, and he does.", false, true))
//                                .addLitePalTags(EnumTag.TRAVEL.getLitePalTag())
//                                .addLitePalTags(EnumTag.WRONG.getLitePalTag())
//                                .addLitePalTags(EnumTag.DISCOVER.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("I feel like you get more bees with honey. But that doesn't mean I don't get frustrated in my life. My way of dealing with frustration is to shut down and to think and speak logically.", false, true))
//                                .addLitePalTags(EnumTag.WISDOM.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXPERIENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.YOU.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("I always treat myself to one meal on Sundays when I can have whatever I want. Usually it's pizza, which is my favorite indulgence.", false, true))
//                                .addLitePalTags(EnumTag.FACTS.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXIST.getLitePalTag())
//                                .addLitePalTags(EnumTag.BECAUSE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("I get nervous when I don't get nervous. If I'm nervous I know I'm going to have a good show.", false, true))
//                                .addLitePalTags(EnumTag.RELIGION.getLitePalTag())
//                                .addLitePalTags(EnumTag.POWERFUL.getLitePalTag())
//                                .addLitePalTags(EnumTag.MIND.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("I hold a lot of things in. I'm always making sure everybody is okay. I usually don't rage; I usually don't curse. So for me, it's a great thing to be able to scream and say whatever I want.", false, true))
//                                .addLitePalTags(EnumTag.POWER.getLitePalTag())
//                                .addLitePalTags(EnumTag.GIVE.getLitePalTag())
//                                .addLitePalTags(EnumTag.GOD.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("There's my personal life, my sensitive side, and then me as a performer, sexy and energised and fun.", false, true))
//                                .addLitePalTags(EnumTag.WAR.getLitePalTag())
//                                .addLitePalTags(EnumTag.DEMOCRACY.getLitePalTag())
//                                .addLitePalTags(EnumTag.COUNTRY.getLitePalTag())
//                                .buildQuotes()
//                        )
//        );
//        litePalDataBuilders.add(
//                new LitePalDataBuilder()
//                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
//                        .setLitePalAuthor(EnumAuthor.BILL_GATES.getLitePalAuthor())
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("The first rule of any technology used in a business is that automation applied to an efficient operation will magnify the efficiency. The second is that automation applied to an inefficient operation will magnify the inefficiency.", false, true))
//                                .addLitePalTags(EnumTag.MOTIVATIONAL.getLitePalTag())
//                                .addLitePalTags(EnumTag.SELF.getLitePalTag())
//                                .addLitePalTags(EnumTag.UNIVERSE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Research shows that there is only half as much variation in student achievement between schools as there is among classrooms in the same school. If you want your child to get the best education possible, it is actually more important to get him assigned to a great teacher than to a great school.", false, true))
//                                .addLitePalTags(EnumTag.PERCEPTION.getLitePalTag())
//                                .addLitePalTags(EnumTag.BRAINY.getLitePalTag())
//                                .addLitePalTags(EnumTag.DOORS.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("We all need people who will give us feedback. That's how we improve.", false, true))
//                                .addLitePalTags(EnumTag.ATTITUDE.getLitePalTag())
//                                .addLitePalTags(EnumTag.AGE.getLitePalTag())
//                                .addLitePalTags(EnumTag.CHILD.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Your most unhappy customers are your greatest source of learning.", false, true))
//                                .addLitePalTags(EnumTag.MUSIC.getLitePalTag())
//                                .addLitePalTags(EnumTag.SILENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXPRESSING.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("The advance of technology is based on making it fit in so that you don't really even notice it, so it's part of everyday life.", false, true))
//                                .addLitePalTags(EnumTag.TRAVEL.getLitePalTag())
//                                .addLitePalTags(EnumTag.WRONG.getLitePalTag())
//                                .addLitePalTags(EnumTag.DISCOVER.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("The Internet is becoming the town square for the global village of tomorrow.", false, true))
//                                .addLitePalTags(EnumTag.WISDOM.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXPERIENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.YOU.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Information technology and business are becoming inextricably interwoven. I don't think anybody can talk meaningfully about one without the talking about the other.", false, true))
//                                .addLitePalTags(EnumTag.FACTS.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXIST.getLitePalTag())
//                                .addLitePalTags(EnumTag.BECAUSE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("It's fine to celebrate success but it is more important to heed the lessons of failure.", false, true))
//                                .addLitePalTags(EnumTag.RELIGION.getLitePalTag())
//                                .addLitePalTags(EnumTag.POWERFUL.getLitePalTag())
//                                .addLitePalTags(EnumTag.MIND.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Technology is just a tool. In terms of getting the kids working together and motivating them, the teacher is the most important.", false, true))
//                                .addLitePalTags(EnumTag.POWER.getLitePalTag())
//                                .addLitePalTags(EnumTag.GIVE.getLitePalTag())
//                                .addLitePalTags(EnumTag.GOD.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("Success is a lousy teacher. It seduces smart people into thinking they can't lose.", false, true))
//                                .addLitePalTags(EnumTag.WAR.getLitePalTag())
//                                .addLitePalTags(EnumTag.DEMOCRACY.getLitePalTag())
//                                .addLitePalTags(EnumTag.COUNTRY.getLitePalTag())
//                                .buildQuotes()
//                        )
//        );




        DataLitePalDataBuilder dataLitePalDataBuilder = new DataLitePalDataBuilder(litePalDataBuilders);
        SessionManager.setStringSetting(getGlobalContext(), SESSION_DATA_DATA_BUILDER, DataLitePalDataBuilder.convertFromObjectToString(dataLitePalDataBuilder));

        return litePalDataBuilders;
    }

    public static ArrayList<LitePalDataBuilder> getAllQuotes() {
        ArrayList<LitePalDataBuilder> litePalDataBuilders = new ArrayList<>();
        if (!AllSettingsManager.isNullOrEmpty(SessionManager.getStringSetting(getGlobalContext(), SESSION_DATA_DATA_BUILDER))) {
            DataLitePalDataBuilder dataLitePalDataBuilder = DataLitePalDataBuilder.convertFromStringToObject(SessionManager.getStringSetting(getGlobalContext(), SESSION_DATA_DATA_BUILDER), DataLitePalDataBuilder.class);
            litePalDataBuilders = dataLitePalDataBuilder.getLitePalDataBuilders();
        }
        return litePalDataBuilders;
    }

//    public static ArrayList<LitePalDataBuilder> getAllQuoteAdvertises() {
//        ArrayList<LitePalDataBuilder> advertises = new ArrayList<LitePalDataBuilder>();
//        advertises.add(new LitePalDataBuilder(new LitePalLanguage(), new LitePalAuthor(), new ArrayList<LitePalDataBuilder.LitePalQuoteBuilder>(),false));
//        advertises.add(new LitePalDataBuilder("Add 2", false, false, null, null));
//        advertises.add(new LitePalDataBuilder("Add 3", false, false, null, null));
//        advertises.add(new LitePalDataBuilder("Add 4", false, false, null, null));
//        advertises.add(new LitePalDataBuilder("Add 5", false, false, null, null));
//        return advertises;
//    }

    /**********************
     * Methods for Author *
     **********************/
    public static LitePalDataBuilder.LitePalQuoteBuilder updateQuote(LitePalDataBuilder litePalDataBuilder, LitePalDataBuilder.LitePalQuoteBuilder litePalQuoteBuilder) {
        //Update data into database
        int id = litePalQuoteBuilder.getLitePalQuote().update(litePalQuoteBuilder.getLitePalQuote().getId());
        //Search the updated data from database
        LitePalQuote litePalQuoteUpdated = LitePalQuote.find(LitePalQuote.class, litePalQuoteBuilder.getLitePalQuote().getId());
        //Update data into adapter view
        if ((id == 1) && (litePalQuoteUpdated != null) && (litePalQuoteUpdated.isFavourite() == litePalQuoteBuilder.getLitePalQuote().isFavourite())) {
            Log.d(TAG, "Updated quote(success response): " + litePalQuoteUpdated.toString());
            ArrayList<LitePalDataBuilder> litePalDataBuilders = getAllQuotes();
            for (LitePalDataBuilder dataBuilder : litePalDataBuilders) {
                if (dataBuilder.getLitePalAuthor().getAuthorName().equalsIgnoreCase(litePalDataBuilder.getLitePalAuthor().getAuthorName())) {
                    Log.d(TAG, "Updated quote: " + "found author");
                    int quotePosition = getQuotePosition(dataBuilder.getLitePalQuoteBuilders(), litePalQuoteBuilder);
                    Log.d(TAG, "Updated quote(id session): " + quotePosition);
                    if (quotePosition != -1) {
                        dataBuilder.getLitePalQuoteBuilders().remove(quotePosition);
                        litePalQuoteBuilder.setLitePalQuote(litePalQuoteUpdated);
                        dataBuilder.getLitePalQuoteBuilders().add(quotePosition, litePalQuoteBuilder);
                        Log.d(TAG, "Updated quote: " + dataBuilder.getLitePalQuoteBuilders().get(quotePosition).toString());

                        //Set updated value into session
                        DataLitePalDataBuilder dataLitePalDataBuilder = new DataLitePalDataBuilder(litePalDataBuilders);
                        SessionManager.setStringSetting(getGlobalContext(), SESSION_DATA_DATA_BUILDER, DataLitePalDataBuilder.convertFromObjectToString(dataLitePalDataBuilder));
                        //Log.d(TAG, "Updated quote(All session): " + SessionManager.getStringSetting(getGlobalContext(), SESSION_DATA_DATA_BUILDER));
                        //Set update to the quote of the day item
                        QuoteOfTheDay quoteOfTheDay = QuoteOfTheDay.convertFromStringToObject(SessionManager.getStringSetting(getGlobalContext(), SESSION_QUOTE_OF_THE_DAY), QuoteOfTheDay.class);
                        if(quoteOfTheDay!=null && quoteOfTheDay.getLitePalQuoteBuilder().getLitePalQuote().getQuoteDescription().equalsIgnoreCase(litePalQuoteUpdated.getQuoteDescription())){
                            quoteOfTheDay.setLitePalDataBuilder(dataBuilder);
                            quoteOfTheDay.setLitePalQuoteBuilder(litePalQuoteBuilder);
                            SessionManager.setStringSetting(getGlobalContext(), SESSION_QUOTE_OF_THE_DAY, QuoteOfTheDay.convertFromObjectToString(quoteOfTheDay));
                        }

                        return litePalQuoteBuilder;
                    }
                }
            }
        }
        return null;
    }

    public static int getQuotePosition(ArrayList<LitePalDataBuilder.LitePalQuoteBuilder> litePalQuoteBuilders, LitePalDataBuilder.LitePalQuoteBuilder litePalQuoteBuilder) {
        for (int i = 0; i < litePalQuoteBuilders.size(); i++) {
            if (litePalQuoteBuilders.get(i).getLitePalQuote().getQuoteDescription().equalsIgnoreCase(litePalQuoteBuilder.getLitePalQuote().getQuoteDescription())) {
                return i;
            }
        }
        return -1;
    }

    public static LitePalDataBuilder getAuthorData(int dataPosition) {
        ArrayList<LitePalDataBuilder> litePalDataBuilders = getAllQuotes();
        if (dataPosition < litePalDataBuilders.size()) {
            return litePalDataBuilders.get(dataPosition);
        }
        return null;
    }

    public static ArrayList<GlazyCard> getAllGlazyCards(ArrayList<LitePalDataBuilder> litePalDataBuilders) {

        ArrayList<GlazyCard> glazyCards = new ArrayList<GlazyCard>();
        GlazyCard glazyCard;
        LitePalDataBuilder quote;
        GlazyImageView.ImageCutType lastTransitionType = GlazyImageView.ImageCutType.LINE_POSITIVE;
        for (int i = 0; i < litePalDataBuilders.size(); i++) {

            quote = litePalDataBuilders.get(i);
            glazyCard = new GlazyCard()
                    .withTitle(quote.isLitePalData() ? quote.getLitePalAuthor().getAuthorName() : "Advertise")
                    .withSubTitle(quote.isLitePalData() ? quote.getLitePalAuthor().getOccupation() : "")
                    .withOccupation(quote.isLitePalData() ? quote.getLitePalAuthor().getOccupation() : "")
                    .withNationality(quote.isLitePalData() ? quote.getLitePalAuthor().getNationality() : "")
                    .withBirthDate(quote.isLitePalData() ? quote.getLitePalAuthor().getBirthDate() : "")
                    .withDeathDate(quote.isLitePalData() ? quote.getLitePalAuthor().getDeathDate() : "")
                    .withDescription(quote.isLitePalData() ? (quote.getLitePalQuoteBuilders().size() > 0 ? quote.getLitePalQuoteBuilders().get(0).getLitePalQuote().getQuoteDescription() : "") : "")
                    .withImageRes((quote.getLitePalAuthor().getProfileImage() != -1) ? quote.getLitePalAuthor().getProfileImage() : R.drawable.avatar_male)
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

    /*************************
     * Methods for Favourite *
     *************************/
    public static ArrayList<LitePalDataBuilder> getAllFavouriteQuotes() {
        ArrayList<LitePalDataBuilder> litePalDataBuilders = getAllQuotes();
        ArrayList<LitePalDataBuilder> favouriteDataBuilders = new ArrayList<>();

        for (LitePalDataBuilder litePalDataBuilder : litePalDataBuilders) {
            LitePalDataBuilder tempLitePalDataBuilder = litePalDataBuilder;
            ArrayList<LitePalDataBuilder.LitePalQuoteBuilder> tempLitePalQuoteBuilders = new ArrayList<>();
            for (LitePalDataBuilder.LitePalQuoteBuilder litePalQuoteBuilder : litePalDataBuilder.getLitePalQuoteBuilders()) {
                if (litePalQuoteBuilder.getLitePalQuote().isFavourite()) {
                    tempLitePalQuoteBuilders.add(litePalQuoteBuilder);
                }
            }
            if (tempLitePalQuoteBuilders.size() > 0) {
                tempLitePalDataBuilder.setLitePalQuoteBuilders(tempLitePalQuoteBuilders);
                favouriteDataBuilders.add(tempLitePalDataBuilder);
            }
        }

        return favouriteDataBuilders;
    }

    /*******************
     * Methods for Tag *
     *******************/
    public static ArrayList<LitePalTag> insetTags(ArrayList<LitePalTag> litePalTags) {
        ArrayList<LitePalTag> mLitePalTags = new ArrayList<>();
        for (int i = 0; i < litePalTags.size(); i++) {
            mLitePalTags.add(insetTag(litePalTags.get(i)));
        }
        return mLitePalTags;
    }

    public static LitePalTag insetTag(LitePalTag litePalTag) {
        LitePalTag mSavedTag = getTag(litePalTag.getTagName());
        if (mSavedTag != null) {
            Log.d(TAG, "insetTag(existing): " + mSavedTag.toString());
            return mSavedTag;
        } else {
            if (litePalTag.save()) {
                LitePalTag savedTag = getTag(litePalTag.getTagName());
                if (savedTag != null) {
                    Log.d(TAG, "insetTag(new): " + savedTag.toString());
                    return savedTag;
                }
            }
        }
        return null;
    }

    public static LitePalTag getTag(String tagName) {
        List<LitePalTag> savedTags = DataSupport.where("tagName = ?", tagName).find(LitePalTag.class);
        if (savedTags != null && savedTags.size() == 1) {
            Log.d(TAG, "getTag: " + savedTags.get(0).toString());
            return savedTags.get(0);
        }
        return null;
    }

    /**********************
     * Methods for Author *
     **********************/
    public static LitePalAuthor insetAuthor(LitePalAuthor litePalAuthor) {
        LitePalAuthor savedAuthor = getAuthor(litePalAuthor.getAuthorName());
        if (savedAuthor != null) {
            Log.d(TAG, "insetAuthor(existing): " + savedAuthor.toString());
            return savedAuthor;
        } else {
            if (litePalAuthor.save()) {
                LitePalAuthor mSavedAuthor = getAuthor(litePalAuthor.getAuthorName());
                if (mSavedAuthor != null) {
                    Log.d(TAG, "insetAuthor(new): " + mSavedAuthor.toString());
                    return mSavedAuthor;
                }
            }
        }
        return null;
    }

    public static LitePalAuthor getAuthor(String authorName) {
        List<LitePalAuthor> savedAuthors = DataSupport.where("authorName = ?", authorName).find(LitePalAuthor.class);
        if (savedAuthors != null && savedAuthors.size() == 1) {
            Log.d(TAG, "getAuthor: " + savedAuthors.get(0).toString());
            return savedAuthors.get(0);
        }
        return null;
    }

    /************************
     * Methods for Language *
     ************************/
    public static LitePalLanguage insetLanguage(LitePalLanguage litePalLanguage) {
        LitePalLanguage savedLanguage = getLanguage(litePalLanguage.getLanguageName());
        if (savedLanguage != null) {
            Log.d(TAG, "insetLanguage(Existing): " + savedLanguage.toString());
            return savedLanguage;
        } else {
            if (litePalLanguage.save()) {
                LitePalLanguage mSavedLanguage = getLanguage(litePalLanguage.getLanguageName());
                if (mSavedLanguage != null) {
                    Log.d(TAG, "insetLanguage(new): " + litePalLanguage.toString());
                    return litePalLanguage;
                }
            }
        }
        return null;
    }

    public static LitePalLanguage getLanguage(String languageName) {
        List<LitePalLanguage> savedLanguage = DataSupport.where("languageName = ?", languageName).find(LitePalLanguage.class);
        if (savedLanguage != null && savedLanguage.size() == 1) {
            Log.d(TAG, "getLanguage: " + savedLanguage.get(0).toString());
            return savedLanguage.get(0);
        }
        return null;
    }

    /*********************
     * Methods for Quote *
     *********************/
    public static LitePalQuote insertQuote(LitePalQuote litePalQuote) {
        LitePalQuote savedQuote = getQuote(litePalQuote.getQuoteDescription());
        if (savedQuote != null) {
            Log.d(TAG, "insetQuote(existing): " + savedQuote.toString());
            return savedQuote;
        } else {
            if (litePalQuote.save()) {
                LitePalQuote mSavedQuote = getQuote(litePalQuote.getQuoteDescription());
                if (mSavedQuote != null) {
                    Log.d(TAG, "insetQuote(new): " + mSavedQuote.toString());
                    return mSavedQuote;
                }
                return litePalQuote;
            }
        }
        return null;
    }

    public static LitePalQuote getQuote(String quoteDescription) {
        List<LitePalQuote> savedQuotes = DataSupport.where("quoteDescription = ?", quoteDescription).find(LitePalQuote.class);
        if (savedQuotes != null && savedQuotes.size() == 1) {
            Log.d(TAG, "getQuote(existing): " + savedQuotes.get(0).toString());
            return savedQuotes.get(0);
        }
        return null;
    }

    /********************************************
     * Methods for Quote, Language, Author, Tag *
     ********************************************/
    public static void insertQuoteLanguageAuthorTag(LitePalDataBuilder litePalDataBuilder) {
        for (int i = 0; i < litePalDataBuilder.getLitePalQuoteBuilders().size(); i++) {
            LitePalDataBuilder.LitePalQuoteBuilder litePalQuoteBuilder = litePalDataBuilder.getLitePalQuoteBuilders().get(i);
            for (int j = 0; j < litePalQuoteBuilder.getLitePalTags().size(); j++) {
                Log.d(TAG, "insertQuoteLanguageAuthorTag(quote): " + litePalQuoteBuilder.getLitePalQuote().toString());
                LitePalQuoteLanguageAuthorTag litePalQuoteLanguageAuthorTag = new LitePalQuoteLanguageAuthorTag(litePalQuoteBuilder.getLitePalQuote().getId(), litePalDataBuilder.getLitePalLanguage().getId(), litePalDataBuilder.getLitePalAuthor().getId(), litePalQuoteBuilder.getLitePalTags().get(j).getId());

                LitePalQuoteLanguageAuthorTag mSavedData = getQuoteLanguageAuthorTag(litePalQuoteLanguageAuthorTag.getMd5());
                if (mSavedData == null) {
                    Log.d(TAG, "insertQuoteLanguageAuthorTag(new): " + litePalQuoteLanguageAuthorTag.toString());
                    litePalQuoteLanguageAuthorTag.save();
                }
            }
        }
    }

    public static LitePalQuoteLanguageAuthorTag getQuoteLanguageAuthorTag(String md5) {
        List<LitePalQuoteLanguageAuthorTag> savedQuotes = DataSupport.where("md5 = ?", md5).find(LitePalQuoteLanguageAuthorTag.class);
        if (savedQuotes != null && savedQuotes.size() == 1) {
            Log.d(TAG, "getQuoteLanguageAuthorTag(existing): " + savedQuotes.get(0).toString());
            return savedQuotes.get(0);
        }
        return null;
    }
}