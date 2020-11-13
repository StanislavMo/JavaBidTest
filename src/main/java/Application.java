import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;

import objects.Bid;
import Thread.BidThread;

public class Application
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private static final int DELAY = 3000;
    private static final int PERIOD = 60000;

    public static void main(String[] args)
    {
        TimerTask fileReparse = new TimerTask() {
            public void run() {
                readJsonFile();
            }
        };
        Timer timer = new Timer("Timer");

        timer.scheduleAtFixedRate(fileReparse, DELAY, PERIOD);
    }

    public static void readJsonFile() {
        try {
            InputStream jsonStream = Bid.class.getResourceAsStream("/bids.json");
            ObjectMapper mapper = new ObjectMapper();

            JSONArray jsonBidList = mapper.readValue(jsonStream, JSONArray.class);

            List<Bid> bidsList = new ArrayList<>();

            for(int i = 0; i < jsonBidList.size(); i++) {
                Bid bid = new Bid();

                bid.setId(((HashMap<String,HashMap<String,String>>)jsonBidList.get(i)).get("bid").get("id"));
                bid.setTs(((HashMap<String,HashMap<String,String>>)jsonBidList.get(i)).get("bid").get("ts"));
                bid.setTy(((HashMap<String,HashMap<String,String>>)jsonBidList.get(i)).get("bid").get("ty"));

                String pl = ((HashMap<String,HashMap<String,String>>)jsonBidList.get(i)).get("bid").get("pl");
                byte[] plBytes = Base64.getDecoder().decode(pl);
                String plString = new String(plBytes);

                bid.setPl(plString);

                bidsList.add(bid);

                Thread thread = new BidThread(bid);
                thread.start();
            }
        } catch (IOException e) {
            LOGGER.error("Error during parsing JSON file: {}", e.getMessage(), e);
        }
    }
}