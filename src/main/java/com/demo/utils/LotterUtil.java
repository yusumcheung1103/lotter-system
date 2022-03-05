package com.demo.utils;

import com.demo.model.Winner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LotterUtil
{
    private static final Logger LOGGER = LoggerFactory.getLogger(LotterUtil.class);

    public static void logWinner(Winner winner)
    {
           LOGGER.info("-------------------------------------------------------");
            LOGGER.info("winner is: {}", winner.getBuyerName());
            LOGGER.info("ticket number: {}", winner.getTicket().getTicketNumber());
            LOGGER.info("-------------------------------------------------------");

    }




}
