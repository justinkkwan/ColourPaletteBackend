package com.example.colourpalettebackend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@AllArgsConstructor
@Service
public class ColourPaletteService {
    private LambdaClient lambdaClient;
    ObjectMapper jsonMapper = new ObjectMapper();

    public List<Integer> getColours(InputStream image, int k) throws IOException {
        String imageBase64 = Base64.getEncoder().encodeToString(image.readAllBytes());
        String jsonPayload = "{\"image\":\"" + imageBase64 + "\"}";
        SdkBytes sdkBytes = SdkBytes.fromUtf8String(jsonPayload);

        String lambdaName = "findKMeans";
//        var listedFunctions = lambdaClient.listFunctions();
//        if (listedFunctions.hasFunctions()) {
//            var lambdas = listedFunctions.functions();
//            lambdaName = lambdas.get(0).functionName();
//        }

        InvokeRequest invokeRequest = InvokeRequest.builder()
                .functionName(lambdaName)
                .payload(sdkBytes)
                .build();

        var response = lambdaClient.invoke(invokeRequest);

        //use jackson to deserialize json response
        var resPayload = response.payload().asByteArray();
        JsonNode jsonRoot = jsonMapper.readTree(resPayload);
        List<Integer> colours = jsonMapper.treeToValue(jsonRoot.get("body"), List.class);

        return colours;
    }
}
