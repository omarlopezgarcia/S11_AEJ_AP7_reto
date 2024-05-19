package pe.edu.vallegrande.app.service.impl;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.app.service.GeminiTopicService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static pe.edu.vallegrande.app.util.Constant.GEMINI_API_URL;

@Service
public class GeminiTopicServiceImpl implements GeminiTopicService {

    private final OkHttpClient client;

    public GeminiTopicServiceImpl() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public String askQuestion(String topic, String question) throws Exception {
        if (relatedQuestion(topic, question)) {
            return getGeminiResponse(question);
        } else {
            return "La pregunta no está relacionada con el tema proporcionado.";
        }
    }

    private boolean relatedQuestion(String topic, String question) {
        String[] topicWords = topic.toLowerCase().split(" ");
        String[] questionWords = question.toLowerCase().split(" ");

        for (String wordTopic : topicWords) {
            for (String wordQuestion : questionWords) {
                if (wordQuestion.contains(wordTopic) || wordTopic.contains(wordQuestion)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getGeminiResponse(String question) throws IOException, JSONException {
        MediaType mediaType = MediaType.parse("application/json");
        String jsonBody = "{\"contents\":[{\"parts\":[{\"text\":\"" + question + "\"}]}]}";
        RequestBody body = RequestBody.create(jsonBody, mediaType);
        Request request = new Request.Builder()
                .url(GEMINI_API_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Error en la solicitud: " + response);
        }

        String responseBody = response.body().string();
        return parseResponse(responseBody);
    }

    private String parseResponse(String responseBody) throws JSONException {
        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray candidates = jsonResponse.getJSONArray("candidates");
        JSONObject content = candidates.getJSONObject(0).getJSONObject("content");
        JSONArray parts = content.getJSONArray("parts");
        JSONObject textObj = parts.getJSONObject(0);
        return textObj.getString("text");
    }
}
