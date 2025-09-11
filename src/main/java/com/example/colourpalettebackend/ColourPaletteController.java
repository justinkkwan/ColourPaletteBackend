package com.example.colourpalettebackend;

import com.example.colourpalettebackend.clients.ImageTransformServiceClient;
import com.example.colourpalettebackend.dto.GenerateOutlineResponse;
import com.example.colourpalettebackend.dto.ImageTransformationRequest;
import com.example.colourpalettebackend.dto.GenerateColourPaletteResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;

@AllArgsConstructor
@RestController
public class ColourPaletteController {

    ColourPaletteService colourPaletteService;
    ImageTransformServiceClient imageTransformServiceClient;

    @GetMapping(path="/server-test")
    public String serverTest() {
        return "Server is functional.";
    }

    @GetMapping(path="/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("healthy");
    }

    @PostMapping(path="/generate-colour-palette", consumes="multipart/form-data")
    public GenerateColourPaletteResponse generateColourPalette(@Valid @ModelAttribute ImageTransformationRequest request) {
        System.out.println("Request received!");
        List<Integer> colours = null;

        try {
            colours = colourPaletteService.getColours(request.file().getInputStream(), 5);
        } catch (IOException e) {
            System.err.println("Error with file stream for " + request.file().getOriginalFilename());
            throw new RuntimeException(e);
        }

        // TODO: Validation that the file is an image

//        try {
//            Path path = Path.of("", request.file().getOriginalFilename());
//
//            InputStream fileInputStream = request.file().getInputStream();
//
//            Files.copy(fileInputStream, path, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


        return new GenerateColourPaletteResponse(colours);
    }

    @PostMapping(path="/generate-outline", consumes="multipart/form-data")
    public GenerateOutlineResponse generateOutline(@Valid @ModelAttribute ImageTransformationRequest request) {
        try {
            InputStream iStream = request.file().getInputStream();

            byte[] tracedImage = imageTransformServiceClient.traceImage(iStream.readAllBytes());

//            InputStream tracedImageBuffer = new ByteArrayInputStream(tracedImage);
//            Files.copy(tracedImageBuffer, Path.of("output.png"), StandardCopyOption.REPLACE_EXISTING);

            String tracedImageBase64 = Base64.getEncoder().encodeToString(tracedImage);
            return new GenerateOutlineResponse(tracedImageBase64);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
