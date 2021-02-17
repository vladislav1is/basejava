package com.redfox.webapp.util;

import com.redfox.webapp.model.Organization;

public class HtmlUtil {
    public static String formatDates(Organization.Experience position) {
        return DateUtil.format(position.getStartDate()) + " - " + DateUtil.format(position.getEndDate());
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}