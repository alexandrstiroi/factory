package org.shtiroy.factory.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;

public class DateUtils {
    private static final Logger LOGGER = LogManager.getLogger(DateUtils.class);

    /**
     * Method convert string to sql.Date.
     * @param strDate - date string format.
     * @return - sql.Date.
     */
    public static Date strToDate(String strDate){
        try {
            Date date = Date.valueOf(strDate);
            return date;
        } catch (Exception ex) {
            LOGGER.info("parse error");
            return null;
        }
    }

}
