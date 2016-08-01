package net.bigboo.apollotest;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.engine.Engine;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by roy on 2016/4/13.
 */
public class esSearchTest {
    public static void main(String[] args) {
        Client client = new TransportClient()
        .addTransportAddress(new InetSocketTransportAddress("192.168.1.119", 9300));

        String json = "{" +
        "\"user\":\"kimchy\"," +
        "\"postDate\":\"2014-01-30\"," +
        "\"message\":\"trying1 out Elasticsearch\"" +
    "}";

        IndexResponse response = client.prepareIndex("twitter", "tweet", "2")
                .setSource(json)
                .execute()
                .actionGet();


        /*
        GetResponse response = client.prepareGet("twitter", "tweet", "1")
                .execute()
                .actionGet();
                */

        //System.out.println(response.getSource());

    }
}
