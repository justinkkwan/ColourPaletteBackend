package com.example.colourpalettebackend.dto;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public record ImageTransformationRequest(@NonNull MultipartFile file) {
}
