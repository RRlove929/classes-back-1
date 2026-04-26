package com.roncoo.education.course.fabric;

import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.Network;
import org.hyperledger.fabric.protos.common.Block;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Fabric 交易元数据查询
 */
@Slf4j
@Component
public class FabricMetaQuery {

    /**
     * 根据交易ID查询所在区块高度
     */
    public Long queryBlockNumberByTxId(String txId) {
        if (!StringUtils.hasText(txId)) {
            return null;
        }
        Network network = FabricConfig.getDefaultNetwork();
        if (network == null) {
            return null;
        }
        try {
            Contract qscc = network.getContract("qscc");
            byte[] blockBytes = qscc.evaluateTransaction("GetBlockByTxID", FabricConfig.getChannelName(), txId);
            if (blockBytes == null || blockBytes.length == 0) {
                return null;
            }
            Block block = Block.parseFrom(blockBytes);
            return block.getHeader().getNumber();
        } catch (Exception e) {
            // txId 不是真实交易ID 或 节点不支持时，留给下次重试
            log.warn("query block number fail, txId={}, err={}", txId, e.getMessage());
            return null;
        }
    }
}

