package com.vg.cognitiveservices.service.impl;

import com.vg.cognitiveservices.model.ComputerVisionResponse;
import com.vg.cognitiveservices.repository.ComputerVisionResponseRepository;
import com.vg.cognitiveservices.service.ComputerVisionService;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ComputerVisionServiceImpl implements ComputerVisionService {

    private final ComputerVisionResponseRepository responseRepository;

    @Autowired
    public ComputerVisionServiceImpl(ComputerVisionResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    @Override
    public Mono<ComputerVisionResponse> save(String imageUrl) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n  \"url\": \"" + imageUrl + "\"\n}\n");
            Request request = new Request.Builder()
                    .url("https://trilary-computer-vision.cognitiveservices.azure.com/vision/v3.2/analyze?visualFeatures=Categories%2CDescription%2CColor%2CAdult%2CFaces%2CBrands%2CImageType")
                    .method("POST", body)
                    .addHeader("Ocp-Apim-Subscription-Key", "b8434bc8abf5416bbc99984979915d7d")
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            JSONObject jsonObject = new JSONObject(response.body().string());
            ComputerVisionResponse computerVisionResponse = new ComputerVisionResponse();

            computerVisionResponse.setDescription(jsonObject.getJSONObject("description").getJSONArray("captions").getJSONObject(0).getString("text"));

            JSONArray tagsArray = jsonObject.getJSONObject("description").getJSONArray("tags");
            List<String> tags = IntStream.range(0, tagsArray.length())
                    .mapToObj(tagsArray::getString)
                    .collect(Collectors.toList());
            computerVisionResponse.setTags(tags);


            JSONObject adultObject = jsonObject.getJSONObject("adult");
            computerVisionResponse.setAdultContent(adultObject.getBoolean("isAdultContent"));
            computerVisionResponse.setRacyContent(adultObject.getBoolean("isRacyContent"));
            computerVisionResponse.setGoryContent(adultObject.getBoolean("isGoryContent"));

            computerVisionResponse.setImageUrl(imageUrl);

            return responseRepository.save(computerVisionResponse);
        } catch (IOException e) {
            throw new RuntimeException("Error en la solicitud HTTP", e);
        }
    }

}
