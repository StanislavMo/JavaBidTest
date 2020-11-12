package Thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import objects.Bid;

public class BidThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(BidThread.class);

    private Bid bid;

    public BidThread( Bid bid) {
        this.bid = bid;
    }

    public void run() {
        try {
            LOGGER.info("Queued bid: {}", bid);
        } catch (Exception e) {
            System.out.println("Thread crashed.");
        }
    }
}