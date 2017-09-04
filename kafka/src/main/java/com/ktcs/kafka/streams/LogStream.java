/**
 * Copyright 2016 Confluent Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.ktcs.kafka.streams;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.connect.json.JsonDeserializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Pattern;

import static com.ktcs.kafka.streams.Constant.*;

/**
 * Demonstrates, using the high-level KStream DSL, how to implement the WordCount program that
 * computes a simple word occurrence histogram from an input text. This example uses lambda
 * expressions and thus works with Java 8+ only.
 * <p>
 * In this example, the input stream reads from a topic named "TextLinesTopic", where the values of
 * messages represent lines of text; and the histogram output is written to topic
 * "WordsWithCountsTopic", where each record is an updated count of a single word, i.e. {@code word (String) -> currentCount (Long)}.
 * <p>
 * Note: Before running this example you must 1) create the source topic (e.g. via {@code kafka-topics --create ...}),
 * then 2) start this example and 3) write some data to the source topic (e.g. via {@code kafka-console-producer}).
 * Otherwise you won't see any data arriving in the output topic.
 * <p>
 * <br>
 * HOW TO RUN THIS EXAMPLE
 * <p>
 * 1) Start Zookeeper and Kafka. Please refer to <a href='http://docs.confluent.io/current/quickstart.html#quickstart'>QuickStart</a>.
 * <p>
 * 2) Create the input and output topics used by this example.
 * <pre>
 * {@code
 * $ bin/kafka-topics --create --topic TextLinesTopic \
 *                    --zookeeper localhost:2181 --partitions 1 --replication-factor 1
 * $ bin/kafka-topics --create --topic WordsWithCountsTopic \
 *                    --zookeeper localhost:2181 --partitions 1 --replication-factor 1
 * }</pre>
 * Note: The above commands are for the Confluent Platform. For Apache Kafka it should be {@code bin/kafka-topics.sh ...}.
 * <p>
 * 3) Start this example application either in your IDE or on the command line.
 * <p>
 * If via the command line please refer to <a href='https://github.com/confluentinc/examples/tree/3.2.x/kafka-streams#packaging-and-running'>Packaging</a>.
 * Once packaged you can then run:
 * <pre>
 * {@code
 * $ java -cp target/streams-examples-3.2.2-standalone.jar io.confluent.examples.streams.WordCountLambdaExample
 * }</pre>
 * 4) Write some input data to the source topic "TextLinesTopic" (e.g. via {@code kafka-console-producer}).
 * The already running example application (step 3) will automatically process this input data and write the
 * results to the output topic "WordsWithCountsTopic".
 * <pre>
 * {@code
 * # Start the console producer. You can then enter input data by writing some line of text, followed by ENTER:
 * #
 * #   hello kafka streams<ENTER>
 * #   all streams lead to kafka<ENTER>
 * #   join kafka summit<ENTER>
 * #
 * # Every line you enter will become the value of a single Kafka message.
 * $ bin/kafka-console-producer --broker-list localhost:9092 --topic TextLinesTopic
 * }</pre>
 * 5) Inspect the resulting data in the output topic, e.g. via {@code kafka-console-consumer}.
 * <pre>
 * {@code
 * $ bin/kafka-console-consumer --topic WordsWithCountsTopic --from-beginning \
 *                              --new-consumer --bootstrap-server localhost:9092 \
 *                              --property print.key=true \
 *                              --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
 * }</pre>
 * You should see output data similar to below. Please note that the exact output
 * sequence will depend on how fast you type the above sentences. If you type them
 * slowly, you are likely to get each count update, e.g., kafka 1, kafka 2, kafka 3.
 * If you type them quickly, you are likely to get fewer count updates, e.g., just kafka 3.
 * This is because the commit interval is set to 10 seconds. Anything typed within
 * that interval will be compacted in memory.
 * <pre>
 * {@code
 * hello    1
 * kafka    1
 * streams  1
 * all      1
 * streams  2
 * lead     1
 * to       1
 * join     1
 * kafka    3
 * summit   1
 * }</pre>
 * 6) Once you're done with your experiments, you can stop this example via {@code Ctrl-C}. If needed,
 * also stop the Kafka broker ({@code Ctrl-C}), and only then stop the ZooKeeper instance (`{@code Ctrl-C}).
 */
public class LogStream {


    public static void main(final String[] args) throws Exception {

        final String bootstrapServers = args.length > 0 ? args[0] : "localhost:9092";

        final Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "LogStream-example");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);
        properties.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);

        final Serializer<JsonNode> jsonSerializer = new JsonSerializer();
        final Deserializer<JsonNode> jsonDeserializer = new JsonDeserializer();

        final Serde<JsonNode> jsonSerde = Serdes.serdeFrom(jsonSerializer, jsonDeserializer);

        final KStreamBuilder builder = new KStreamBuilder();

        //log_original_
        final Pattern t = Pattern.compile("^log_original_.*");

        final KStream<String, JsonNode> stream = builder.stream(
                new WallclockTimestampExtractor(),
                Serdes.String(),
                jsonSerde,
                t
        );

//        final Predicate<String, JsonNode> error = (key, value) -> value.get("error");
//        final Predicate<String, JsonNode> notError = (key, value) -> value.get("error").asText().length() < 0;
        //@SuppressWarnings("unchecked")

        stream
                .map(LogStream::apply)
                //.branch(error, notError);
                .to(Serdes.String(), jsonSerde, "log_kafka");
        /*
        branch[0].to(Serdes.String(), jsonSerde, "log_kafka");
        branch[1].to(Serdes.String(), jsonSerde, "log_kafka_error");*/


        final KafkaStreams streams = new KafkaStreams(builder, properties);
        streams.cleanUp();
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    private static KeyValue<String, JsonNode> apply(String key, JsonNode value) {

        System.out.println();

        System.out.println("key  = >" + key);
        System.out.println("JsonNode  = >" + value);
        System.out.println("JsonNode.class  = >" + value.getClass().getSimpleName());

        // 克隆节点数据进行修改
        final ObjectNode nodeFk = value.deepCopy();
        // 异常信息保存,如果处理过程中未出现错误,大小默认为 0
        final List<String> errors = new ArrayList<>();

        String index = STR_NULL, type = STR_NULL;
        final ObjectNode eventNode = JsonNodeFactory.instance.objectNode();
        try {
            final JsonNode dataParam = nodeFk.get(FIELD_DATA_PARAM);
            System.out.println("dataParam   =>" + dataParam);
            if (dataParam instanceof NullNode) {
                errors.add("dataParam is NullNode");
            } else {
                index = dataParam.get(FIELD_GAME_KEY_S).asText(STR_NULL);
                type = nodeFk.get(FIELD_CATEGORY).asText(STR_NULL);
            }

            final JsonNode event1 = nodeFk.get(FIELD_EVENT);
            System.out.println("event  = >" + event1);
            //progression_8_l:world:world_z1:world_z1-2:world_z1-2-killbos:difficult:succeed:123000
            final String[] split = event1.asText("").split(":");
            final int eventLength = split.length;
            if (eventLength > 0) {
                final String eventType = split[0];
                final String[] s2 = eventType.split("_");
                final String eventTypeStr = s2[0];
                final int numStr = Integer.parseInt(s2[1]);
                final String rStr = s2[2];

                type += "_" + eventTypeStr;

                eventNode.put("t0", eventType);
                for (int i = 1; i < numStr - 1; i++) {
                    String event = split[i];
                    eventNode.put("t" + i, event);
                }

                String eventResult = Utils.pars(rStr);
                Objects.requireNonNull(eventResult, "[eventResult] match failed");

                eventNode.put(eventResult, split[numStr - 1]);
            }
        } catch (Exception e) {
            errors.add("process :" + e.getMessage());
            e.printStackTrace();
        }

        //add error
        if (!errors.isEmpty()) {
            System.out.println("errors  =>" + errors);
            final ArrayNode errorJsonNodes = JsonNodeFactory.instance.arrayNode(errors.size());
            for (String v : errors) {
                errorJsonNodes.add(v);
            }
            nodeFk.set(FIELD_ERROR, errorJsonNodes);
            index = type = STR_NULL;
        }

        final ObjectNode elasticJsonNodes = JsonNodeFactory.instance.objectNode();
        elasticJsonNodes.put(FIELD_INDEX, index);
        elasticJsonNodes.put(FIELD_TYPE, type);
        nodeFk.set(FIELD_ELASTIC, elasticJsonNodes);

        nodeFk.set(FIELD_EVENT_CHAIN, eventNode);

        System.out.println("nodeFk  = >" + nodeFk);
        return new KeyValue<>(key, nodeFk);
    }
}
