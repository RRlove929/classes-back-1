package com.roncoo.education.course.fabric;


import io.grpc.ChannelCredentials;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.TlsChannelCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.Gateway;
import org.hyperledger.fabric.client.Network;
import org.hyperledger.fabric.client.identity.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FabricConfig {
    private static final HashMap<String, Contract> contractMap = new HashMap<>();
    private static String DEFAULT_CLIENT;

    public static Contract getDefalutContract() {
        return contractMap.get(DEFAULT_CLIENT);
    }

    public static Contract getContract(String mspId) {
        return contractMap.get(mspId);
    }

    private final FabricProperties fabricProperties;

    @PostConstruct
    public void init() throws IOException, CertificateException, InvalidKeyException {
        log.info("FabricConfig init");
        DEFAULT_CLIENT = fabricProperties.getDefaultClient();
        for (FabricProperties.FabricClientConf client : fabricProperties.getClients()) {
            log.info("FabricConfig client init : {}", client);
            Path cryptoPath = Paths.get(client.getCryptoPath());
            Path certDirPath = cryptoPath.resolve(Paths.get(client.getCertDirPath()));
            Path keyDirPath = cryptoPath.resolve(Paths.get(client.getKeyDirPath()));
            Path tlsCertPath = cryptoPath.resolve(Paths.get(client.getTlsCertPath()));
            ManagedChannel channel = newGrpcConnection(tlsCertPath, client.getPeerEndpoint(), client.getOverrideAuth());
            Gateway.Builder builder = Gateway.newInstance()
                    .identity(newIdentity(certDirPath, client.getMspId()))
                    .signer(newSigner(keyDirPath))
                    .connection(channel)
                    // Default timeouts for different gRPC calls
                    .evaluateOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                    .endorseOptions(options -> options.withDeadlineAfter(15, TimeUnit.SECONDS))
                    .submitOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                    .commitStatusOptions(options -> options.withDeadlineAfter(1, TimeUnit.MINUTES));

            Gateway gateway = builder.connect();
            // Get a network instance representing the channel where the smart contract is
            // deployed.
            Network network = gateway.getNetwork(fabricProperties.getChannelName());
            // Get the smart contract from the network.
            Contract contract = network.getContract(fabricProperties.getChaincodeName());
            contractMap.put(client.getMspId(), contract);
        }
        log.info("FabricConfig init success");
    }

    private static ManagedChannel newGrpcConnection(Path tlsCertPath, String peerEndpoint, String overrideAuth) throws IOException {
        ChannelCredentials build = TlsChannelCredentials.newBuilder()
                .trustManager(tlsCertPath.toFile())
                .build();
        return Grpc.newChannelBuilder(peerEndpoint, build)
                .overrideAuthority(overrideAuth)
                .build();
    }

    private static Identity newIdentity(Path certDirPath, String mspId) throws IOException, CertificateException {

        try (BufferedReader bufferedReader = Files.newBufferedReader(getFirstFilePath(certDirPath))) {
            X509Certificate certificate = Identities.readX509Certificate(bufferedReader);
            return new X509Identity(mspId, certificate);
        }
    }

    private static Signer newSigner(Path keyDirPath) throws IOException, InvalidKeyException {
        try (BufferedReader keyReader = Files.newBufferedReader(getFirstFilePath(keyDirPath))) {
            PrivateKey privateKey = Identities.readPrivateKey(keyReader);
            return Signers.newPrivateKeySigner(privateKey);
        }
    }

    private static Path getFirstFilePath(Path dirPath) throws IOException {
        try (Stream<Path> keyFiles = Files.list(dirPath)) {
            return keyFiles.findFirst()
                    .orElseThrow(() -> new IOException("No files found in " + dirPath));
        }
    }
}
