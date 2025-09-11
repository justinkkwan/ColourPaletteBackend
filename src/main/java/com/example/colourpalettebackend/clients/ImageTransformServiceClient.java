package com.example.colourpalettebackend.clients;

import com.example.colourpalettebackend.generated.ImageTransformAPI.ImageBytes;
import com.example.colourpalettebackend.generated.ImageTransformAPI.ImageTransformServiceGrpc;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageTransformServiceClient {
    ManagedChannel channel;
    ImageTransformServiceGrpc.ImageTransformServiceBlockingStub blockingStub;

    @Autowired
    public ImageTransformServiceClient(@Value("${image.transform.service.url}") String host, @Value("${image.transform.service.port}") int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    public ImageTransformServiceClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = ImageTransformServiceGrpc.newBlockingStub(channel);
    }

    public byte[] traceImage(byte[] image) {
        ByteString byteString = ByteString.copyFrom(image);
        ImageBytes imageRequest = ImageBytes.newBuilder().setImage(byteString).build();

        try {
            var trace = blockingStub.traceImage(imageRequest);
            return trace.getImage().toByteArray();
        }
        catch (StatusRuntimeException statusError) {
            System.err.println("Remote call for trace_image met with error status: " + statusError.getStatus() + ". Error: " + statusError.getMessage());
            throw statusError;
        }
    }
}
