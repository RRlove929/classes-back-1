package com.roncoo.education.course.fabric;/*
 * Copyright IBM Corp. All Rights Reserved.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.client.*;
import org.hyperledger.fabric.protos.gateway.ErrorDetail;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
public final class FabricContract {

    public static Contract getContract() {
        return FabricConfig.getDefalutContract();
    }


    public String createBizRecord(String bizKey, String jsonString) {
        return submitTransaction("CreateBizRecord", bizKey, jsonString);
    }

    public String submitTransaction(final String functionName, final String... args) {
        try {
            byte[] result = getContract().submitTransaction(functionName, args);
            return result == null ? null : new String(result);
        } catch (EndorseException | SubmitException | CommitStatusException e) {
            log.error("addAsset error", e);
            StringBuilder stringBuilder = new StringBuilder();
            List<ErrorDetail> details = e.getDetails();
            if (!details.isEmpty()) {
                for (ErrorDetail detail : details) {
                    stringBuilder.append("- address: ")
                            .append(detail.getAddress())
                            .append(", mspId: ")
                            .append(detail.getMspId())
                            .append(", message: ")
                            .append(detail.getMessage());
                }
            }
            log.error("submitTransaction error: {}", stringBuilder);
            throw new RuntimeException(stringBuilder.toString());
        } catch (CommitException e) {
            log.error("submitTransaction error", e);
            throw new RuntimeException(
                    "Transaction " + e.getTransactionId() + " failed to commit with status code " + e.getCode());
        }
    }


    public String get(String functionName, String... args) {
        try {
            return new String(getContract().evaluateTransaction(functionName, args));
        } catch (GatewayException e) {
            StringBuilder stringBuilder = new StringBuilder();
            List<ErrorDetail> details = e.getDetails();
            if (!details.isEmpty()) {
                for (ErrorDetail detail : details) {
                    stringBuilder.append("- address: ")
                            .append(detail.getAddress())
                            .append(", mspId: ")
                            .append(detail.getMspId())
                            .append(", message: ")
                            .append(detail.getMessage());
                }
            }
            log.error("get error", e);
            throw new RuntimeException(stringBuilder.toString());
        }
    }

}
