package pe.edu.vallegrande.app;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

public class GeminiMain {

    private static final String API_KEY = "AIzaSyDpak3hyc0HHiV2SGlxsWZaGxQLM-9kS8A";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        System.out.println("Bienvenido a Gemini. Escribe 'salir' para terminar el programa.");

        while (true) {
            System.out.println("\nEscribe tu pregunta para Gemini:");
            String pregunta = scanner.nextLine();

            if (pregunta.equalsIgnoreCase("salir")) {
                System.out.println("Saliendo del programa...");
                break;
            }

            try {
                String respuesta = obtenerRespuestaGemini(client, pregunta);
                System.out.println("Respuesta de Gemini: \n" + respuesta);
            } catch (IOException e) {
                System.err.println("Error al conectarse a la API de Gemini: " + e.getMessage());
            } catch (JSONException e) {
                System.err.println("Error al analizar la respuesta de Gemini: " + e.getMessage());
            }
        }
    }

    private static String obtenerRespuestaGemini(OkHttpClient client, String pregunta) throws IOException, JSONException {
        MediaType mediaType = MediaType.parse("application/json");
        String jsonBody = "{\"contents\":[{\"parts\":[{\"text\":\"" + pregunta + "\"}]}]}";
        RequestBody body = RequestBody.create(jsonBody, mediaType);
        Request request = new Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + API_KEY)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();
        return parseResponse(responseBody);
    }

    private static String parseResponse(String responseBody) throws JSONException {
        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray candidates = jsonResponse.getJSONArray("candidates");
        JSONObject content = candidates.getJSONObject(0).getJSONObject("content");
        JSONArray parts = content.getJSONArray("parts");
        JSONObject textObj = parts.getJSONObject(0);
        return textObj.getString("text");
    }
}
