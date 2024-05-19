package pe.edu.vallegrande.app.model;

public class GeminiTopicResponse {
    private String topic;
    private String question;

    // Constructor, getters y setters
    public GeminiTopicResponse() {}

    public GeminiTopicResponse(String topic, String question) {
        this.topic = topic;
        this.question = question;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
