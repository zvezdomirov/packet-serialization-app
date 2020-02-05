package com.personalprojects.app;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Decoder {

    private static final String INTEGRITY_EXCEPTION_MESSAGE =
            "Packet integrity has been compromised: ";

    private Decoder() {

    }

    public static String decode(String fileContent) {
        List<String> packets = Arrays.asList(fileContent.split("\\|"));
        verifyIntegrity(packets);
        return packets.stream()
                .map(Decoder::extractData)
                .collect(Collectors.joining(""));
    }

    private static String extractData(String packet) {
        return packet.split(";")[1];
    }

    private static void verifyIntegrity(List<String> packets) {
        for (String packet : packets) {
            if (!verifyIntegrity(packet)) {
                throw new SecurityException(
                        INTEGRITY_EXCEPTION_MESSAGE + packet
                );
            }
        }
    }

    private static boolean verifyIntegrity(String packet) {
        String[] tokens = packet.split(";");
        int lengthHeader = Integer.parseInt(tokens[0]);
        String data = tokens[1];
        String hash = tokens[2];
        String recalculatedHash =
                Base64.encodeBase64String(
                        DigestUtils.md5(data));

        return lengthHeader == data.length() &&
                hash.equals(recalculatedHash);
    }
}
