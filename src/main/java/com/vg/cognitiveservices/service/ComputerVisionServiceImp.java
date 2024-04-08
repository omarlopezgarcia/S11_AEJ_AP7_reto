package com.vg.cognitiveservices.service;

import com.vg.cognitiveservices.model.Description;
import okhttp3.*;
import org.json.JSONObject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

public class ComputerVisionServiceImp implements ComputerVisionService  {

    @Override
    public Flux<Description> getAll() {
        return null;
    }

    @Override
    public Mono<Description> save(Description description) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"url\": \"https://img.freepik.com/foto-gratis/retrato-hombre-blanco-aislado_53876-40306.jpg\"\n}\n");
        Request request = new Request.Builder()
                .url("https://trilary-computer-vision.cognitiveservices.azure.com/vision/v3.2/analyze?visualFeatures=Categories%2CDescription%2CColor%2CAdult%2CFaces%2CBrands%2CImageType")
                .method("POST", body)
                .addHeader("Ocp-Apim-Subscription-Key", "b8434bc8abf5416bbc99984979915d7d")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return null;

    }

}
