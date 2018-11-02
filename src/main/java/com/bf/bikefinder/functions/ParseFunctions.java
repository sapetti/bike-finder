package com.bf.bikefinder.functions;

import io.vavr.Tuple2;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class ParseFunctions {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParseFunctions.class);

    @SuppressWarnings("unchecked")
    public static <T, E> Consumer<Tuple2<E, Element>> setChildAttr(BiConsumer<E, T> setter, String childSelector, String attrSelector) {
        return tuple -> setter.accept(tuple._1, (T) tuple._2.selectFirst(childSelector).attr(attrSelector));
    }

    @SuppressWarnings("unchecked")
    public static <T, E> Consumer<Tuple2<E, Element>> setAttr(BiConsumer<E, T> setter, String attrSelector) {
        return tuple -> setter.accept(tuple._1, (T) tuple._2.attr(attrSelector));
    }

    @SuppressWarnings("unchecked")
    public static <T, E> Consumer<Tuple2<E, Element>> setValue(BiConsumer<E, T> setter, T value) {
        return tuple -> setter.accept(tuple._1, value);
    }

    public static Function<String, Double> parseDouble = num -> {
        NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
        try {
            return Double.valueOf(nf.parse(num).doubleValue());
        } catch (ParseException e) {
            LOGGER.error("Error parsing price:: " + num, e);
            return null;
        }
    };

    public static Function<String, String> replaceDots = text -> text.replace(".", ",");

}
