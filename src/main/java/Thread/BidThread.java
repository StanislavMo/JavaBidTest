package Thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import objects.Bid;

public class BidThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(BidThread.class);
    private static final Map<String, Queue<Bid>> QUEUE_MAP = new HashMap<>();

    private Bid bid;

    public BidThread( Bid bid) {
        this.bid = bid;
    }

    public void run() {
        try {
            QUEUE_MAP.put(this.bid.getId(), new LinkedList());

            LOGGER.info("Queued bid: {}", bid);
        } catch (Exception e) {
            System.out.println("Thread crashed.");
        }
    }
}