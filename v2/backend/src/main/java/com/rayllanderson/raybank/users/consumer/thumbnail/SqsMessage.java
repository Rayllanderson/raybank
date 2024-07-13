package com.rayllanderson.raybank.users.consumer.thumbnail;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SqsMessage(
        @JsonProperty("Records")
        List<SqsRecord> records
) {

    public String key() {
        if (records == null || records().isEmpty()) return null;
        return records().get(0).s3().object().key();
    }

    public String keyWithoutPrefix() {
        String fullKey = key();
        if (fullKey == null || !fullKey.contains("thumbnails/")) {
            return null;
        }
        return fullKey.split("thumbnails/")[1];
    }


    record SqsRecord(
            @JsonProperty("s3")
            SqsObject s3
    ) {


        record SqsObject(
                @JsonProperty("object")
                S3Object object
        ) {
            record S3Object(
                    @JsonProperty("key")
                    String key
            ) {
            }
        }
    }
}