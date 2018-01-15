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

import static tech.codegarage.quotes.application.QuoteApp.getGlobalContext;
import static tech.codegarage.quotes.util.AllConstants.SESSION_DATA_DATA_BUILDER;

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

//        litePalDataBuilders.add(
//                new LitePalDataBuilder()
//                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
//                        .setLitePalAuthor(EnumAuthor.ARISTOTLE.getLitePalAuthor())
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.INSPIRATIONAL.getLitePalTag())
//                                .addLitePalTags(EnumTag.LIGHT.getLitePalTag())
//                                .addLitePalTags(EnumTag.FOCUS.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.MOTIVATIONAL.getLitePalTag())
//                                .addLitePalTags(EnumTag.QUALITY.getLitePalTag())
//                                .addLitePalTags(EnumTag.HABIT.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.EDUCATION.getLitePalTag())
//                                .addLitePalTags(EnumTag.ROOTS.getLitePalTag())
//                                .addLitePalTags(EnumTag.SWEET.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.EDUCATION.getLitePalTag())
//                                .addLitePalTags(EnumTag.THOUGHT.getLitePalTag())
//                                .addLitePalTags(EnumTag.MIND.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.ART.getLitePalTag())
//                                .addLitePalTags(EnumTag.TRAINING.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXCELLENCE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.WORK.getLitePalTag())
//                                .addLitePalTags(EnumTag.JOB.getLitePalTag())
//                                .addLitePalTags(EnumTag.PERFECTION.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.INTELLIGENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.GREAT.getLitePalTag())
//                                .addLitePalTags(EnumTag.GENIUS.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.LOVE.getLitePalTag())
//                                .addLitePalTags(EnumTag.LOVE_IS.getLitePalTag())
//                                .addLitePalTags(EnumTag.SOUL.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.TEACHER.getLitePalTag())
//                                .addLitePalTags(EnumTag.KNOWLEDGE.getLitePalTag())
//                                .addLitePalTags(EnumTag.POWER.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.EQUALITY.getLitePalTag())
//                                .addLitePalTags(EnumTag.INEQUALITY.getLitePalTag())
//                                .addLitePalTags(EnumTag.TRY.getLitePalTag())
//                                .buildQuotes()
//                        )
//        );
//
//        litePalDataBuilders.add(
//                new LitePalDataBuilder()
//                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
//                        .setLitePalAuthor(EnumAuthor.ARISTOTLE.getLitePalAuthor())
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.INSPIRATIONAL.getLitePalTag())
//                                .addLitePalTags(EnumTag.LIGHT.getLitePalTag())
//                                .addLitePalTags(EnumTag.FOCUS.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.MOTIVATIONAL.getLitePalTag())
//                                .addLitePalTags(EnumTag.QUALITY.getLitePalTag())
//                                .addLitePalTags(EnumTag.HABIT.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.EDUCATION.getLitePalTag())
//                                .addLitePalTags(EnumTag.ROOTS.getLitePalTag())
//                                .addLitePalTags(EnumTag.SWEET.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.EDUCATION.getLitePalTag())
//                                .addLitePalTags(EnumTag.THOUGHT.getLitePalTag())
//                                .addLitePalTags(EnumTag.MIND.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.ART.getLitePalTag())
//                                .addLitePalTags(EnumTag.TRAINING.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXCELLENCE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.WORK.getLitePalTag())
//                                .addLitePalTags(EnumTag.JOB.getLitePalTag())
//                                .addLitePalTags(EnumTag.PERFECTION.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.INTELLIGENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.GREAT.getLitePalTag())
//                                .addLitePalTags(EnumTag.GENIUS.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.LOVE.getLitePalTag())
//                                .addLitePalTags(EnumTag.LOVE_IS.getLitePalTag())
//                                .addLitePalTags(EnumTag.SOUL.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.TEACHER.getLitePalTag())
//                                .addLitePalTags(EnumTag.KNOWLEDGE.getLitePalTag())
//                                .addLitePalTags(EnumTag.POWER.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.EQUALITY.getLitePalTag())
//                                .addLitePalTags(EnumTag.INEQUALITY.getLitePalTag())
//                                .addLitePalTags(EnumTag.TRY.getLitePalTag())
//                                .buildQuotes()
//                        )
//        );
//
//        litePalDataBuilders.add(
//                new LitePalDataBuilder()
//                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
//                        .setLitePalAuthor(EnumAuthor.ARISTOTLE.getLitePalAuthor())
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.INSPIRATIONAL.getLitePalTag())
//                                .addLitePalTags(EnumTag.LIGHT.getLitePalTag())
//                                .addLitePalTags(EnumTag.FOCUS.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.MOTIVATIONAL.getLitePalTag())
//                                .addLitePalTags(EnumTag.QUALITY.getLitePalTag())
//                                .addLitePalTags(EnumTag.HABIT.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.EDUCATION.getLitePalTag())
//                                .addLitePalTags(EnumTag.ROOTS.getLitePalTag())
//                                .addLitePalTags(EnumTag.SWEET.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.EDUCATION.getLitePalTag())
//                                .addLitePalTags(EnumTag.THOUGHT.getLitePalTag())
//                                .addLitePalTags(EnumTag.MIND.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.ART.getLitePalTag())
//                                .addLitePalTags(EnumTag.TRAINING.getLitePalTag())
//                                .addLitePalTags(EnumTag.EXCELLENCE.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.WORK.getLitePalTag())
//                                .addLitePalTags(EnumTag.JOB.getLitePalTag())
//                                .addLitePalTags(EnumTag.PERFECTION.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.INTELLIGENCE.getLitePalTag())
//                                .addLitePalTags(EnumTag.GREAT.getLitePalTag())
//                                .addLitePalTags(EnumTag.GENIUS.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.LOVE.getLitePalTag())
//                                .addLitePalTags(EnumTag.LOVE_IS.getLitePalTag())
//                                .addLitePalTags(EnumTag.SOUL.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.TEACHER.getLitePalTag())
//                                .addLitePalTags(EnumTag.KNOWLEDGE.getLitePalTag())
//                                .addLitePalTags(EnumTag.POWER.getLitePalTag())
//                                .buildQuotes()
//                        )
//                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
//                                .setLitePalQuote(new LitePalQuote("", false, true))
//                                .addLitePalTags(EnumTag.EQUALITY.getLitePalTag())
//                                .addLitePalTags(EnumTag.INEQUALITY.getLitePalTag())
//                                .addLitePalTags(EnumTag.TRY.getLitePalTag())
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