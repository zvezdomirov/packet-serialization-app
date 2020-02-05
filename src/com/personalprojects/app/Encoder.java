package com.personalprojects.app;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Encoder {

    private static final int CHUNK_SIZE = 5;
    private static final String CHUNK_LENGTH_EXCEPTION_MESSAGE =
            "Argument: chunkLength has to be positive";

    private Encoder() {

    }

    static String encode(String input) {
        List<String> packets = splitIntoPackets(input, CHUNK_SIZE);
        return packets.stream()
                .map(Encoder::encodePacket)
                .collect(Collectors.joining("|"));
    }

    private static List<String> splitIntoPackets(String input, int chunkLength) {
        //Check input for correctness
        if (input == null) {
            throw new NullPointerException();
        }
        if (chunkLength <= 0) {
            throw new IllegalArgumentException(
                    CHUNK_LENGTH_EXCEPTION_MESSAGE
            );
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < input.length(); i += chunkLength) {
            result.add(input.substring(i, Math.min(input.length(), i + chunkLength)));
        }
        return result;
    }

    private static String encodePacket(String packet) {
        return String.format("%d;%s;%s",
                packet.length(),
                packet,
                Base64.encodeBase64String(
                        DigestUtils.md5(packet)));
    }
}
