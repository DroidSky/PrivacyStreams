package com.github.privacystreams.core.commons.item;

import com.github.privacystreams.core.Function;
import com.github.privacystreams.core.Item;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A helper class to access common item functions
 */

public class Items {
    /**
     * A predicate that checks whether the field value in a item is in the given list.
     *
     * @param field the field name
     * @param listToCompare the list to check whether the field is in
     * @param <TValue> the type of list elements
     * @return the predicate
     */
    public static <TValue> Function<Item, Boolean> isFieldIn(final String field, final TValue[] listToCompare) {
        return new FieldInCollectionPredicate<>(field, Arrays.asList(listToCompare));
    }

    /**
     * A predicate that checks whether the item contains a given field.
     *
     * @param fieldToCheck the name of field to check
     * @return the predicate
     */
    public static Function<Item, Boolean> containsField(final String fieldToCheck) {
        return new ContainsFieldPredicate(fieldToCheck);
    }

    /**
     * A function that gets the value of a given field in the item.
     *
     * @param field the name of the field to get.
     * @param <TValue> the type of field value
     * @return the function
     */
    public static <TValue> Function<Item, TValue> getField(final String field) {
        return new FieldValueGetter<>(field);
    }

    /**
     * A function that outputs the sub stream in the item.
     * This function must be applied to a group item,
     * That is, each item must have a "sub_stream" field and the field is a list of items.
     *
     * @param subStreamFunction the function to output sub stream.
     * @param <Tout> the type of sub stream collection result.
     * @return the function
     */
    public static <Tout> Function<Item, Tout> outputSubStream(Function<List<Item>, Tout> subStreamFunction) {
        return new ItemSubStreamFunction<>(subStreamFunction);
    }

    /**
     * A function that returns the item as a key-value map.
     *
     * @return the function.
     */
    public static Function<Item, Map<String, Object>> asMap() {
        return new ItemToMapFunction();
    }

    /**
     * A function that maps an item by setting a new field to a value computed with a function.
     *
     * @param fieldToSet the name of the field to set, it can be a new name.
     * @param functionToComputeValue the function to compute the value of the field.
     * @param <TValue> the type of the new field value.
     * @return the item mapper function.
     */
    public static <TValue> Function<Item, Item> setField(
            String fieldToSet, Function<Item, TValue> functionToComputeValue) {
        return new FieldSetter<>(fieldToSet, functionToComputeValue);
    }

    /**
     * A function that maps an item by setting a new field to a certain value.
     *
     * @param fieldToSet the name of the field to set, it can be a new name.
     * @param fieldValue the value of the field.
     * @param <TValue> the type of the new field value.
     * @return the item mapper function.
     */
    public static <TValue> Function<Item, Item> setField(String fieldToSet, TValue fieldValue) {
        return new FieldValueSetter<>(fieldToSet, fieldValue);
    }

    /**
     * A function that projects an item by including some fields.
     * The fields that are not included will not be present in output.
     *
     * @param fieldsToInclude the names of the fields to include.
     * @return the item mapper function.
     */
    public static Function<Item, Item> includeFields(String... fieldsToInclude) {
        return new ItemProjector(ItemProjector.OPERATOR_INCLUDE, fieldsToInclude);
    }

    /**
     * A function that projects an item by excluding some fields.
     * The excluded fields will not be present in output.
     *
     * @param fieldsToExclude the names of the fields to exclude.
     * @return the item mapper function.
     */
    public static Function<Item, Item> excludeFields(String... fieldsToExclude) {
        return new ItemProjector(ItemProjector.OPERATOR_EXCLUDE, fieldsToExclude);
    }

    /**
     * A function that gets the sub item in a given field.
     *
     * @param subItemField the name of sub item field.
     * @return the function.
     */
    public static Function<Item, Item> getSubItem(String subItemField) {
        return new SubItemGetter(subItemField);
    }
}
