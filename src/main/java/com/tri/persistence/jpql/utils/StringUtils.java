package com.tri.persistence.jpql.utils;

import java.util.List;

public class StringUtils {

	/**
	 * Joins items of a text list, separating items by comma.
	 * 
	 * @param list
	 *            list of text items
	 * @return joined list
	 */
	public static String join(final List<String> list) {

		// zero, empty or one element
		if (list == null) {
			return null;
		} else if (list.size() == 0) {
			return "";
		} else if (list.size() == 1) {
			return list.get(0);
		}

		// two or more elements
		final StringBuilder builder = new StringBuilder();
		for (String item : list) {
			if (builder.length() > 0) {
				builder.append(", ");
			}
			builder.append(item);
		}
		return builder.toString();

	}

	/**
	 * Joins items of a text array, separating items by comma.
	 * 
	 * @param list
	 *            list of text items
	 * @return joined list
	 */
	public static String join(final String[] list) {

		// zero, empty or one element
		if (list == null) {
			return null;
		} else if (list.length == 0) {
			return "";
		} else if (list.length == 1) {
			return list[0];
		}

		// two or more elements
		final StringBuilder builder = new StringBuilder();
		for (String item : list) {
			if (builder.length() > 0) {
				builder.append(", ");
			}
			builder.append(item);
		}
		return builder.toString();

	}

	/**
	 * Returns true if the given string is null, empty or contains whitespace
	 * only.
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return {@code true} if the String is null, empty or whitespace
	 */
	public static boolean isBlank(final String str) {
		final int strLength = (str == null) ? 0 : str.length();
		if (strLength == 0) {
			return true;
		}
		for (int i = 0; i < strLength; i++) {
			if (Character.isWhitespace(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

}
