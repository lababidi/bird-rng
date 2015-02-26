import Twitter.Message;
import Twitter.Properties;
import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * Created by mahmoud on 2/21/15.
 */

public class Streaming implements Runnable{

    Client hosebirdClient;
    MessageDigest md;


    BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(100000);
    BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>(1000);
    BlockingQueue<Byte> randomBytes;

    public Streaming(BlockingQueue<Byte> randomBytes) {
        /** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
        msgQueue = new LinkedBlockingQueue<>(100000);
        eventQueue = new LinkedBlockingQueue<>(1000);
        this.randomBytes = randomBytes;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

/** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
// Optional: set up some followings and track terms
        List<Long> followings = Lists.newArrayList(1234L, 566788L);
        List<String> terms = Lists.newArrayList("twitter", "api");
        hosebirdEndpoint.followings(followings);
        hosebirdEndpoint.trackTerms(terms);


        Properties p = new Properties();

        Authentication hosebirdAuth = new OAuth1(p.consumerKey, p.consumerSecret, p.authKey, p.authSecret);

        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client-01")                              // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue))
                .eventMessageQueue(eventQueue);                          // optional: use this if you want to process client events

        hosebirdClient = builder.build();
    }
    public void run(){
        hosebirdClient.connect();   // Attempts to establish a connection.

        Conversion conversion = new Conversion();

        while (!hosebirdClient.isDone()) {
            String msg = null;
            try {
                msg = msgQueue.take();
                Message message = conversion.twitterify(msg);
                if(null!=message){
//                    messages.put(message);

                    for (byte b : digest(message))
                        randomBytes.put(b);
                }

            } catch (InterruptedException e) {
                System.out.println(msg);
                e.printStackTrace();
            }
        }
    }

    public byte[] digest(Message message){
        byte[] digest = new byte[0];
        try {
            md.update(message.text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            digest = md.digest();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return digest;
    }



}
