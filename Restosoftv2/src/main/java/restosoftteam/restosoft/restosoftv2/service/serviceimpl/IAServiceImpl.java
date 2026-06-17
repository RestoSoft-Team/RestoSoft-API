package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import restosoftteam.restosoft.restosoftv2.service.IAService;

import java.util.Base64;

@Service
public class IAServiceImpl implements IAService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${ollama.api.url}")
    private String ollamaUrl;

    @Value("${ollama.model.vision}")
    private String ollamaModel;

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Value("${groq.api.url}")
    private String groqUrl;

    @Value("${groq.model}")
    private String groqModel;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String analizarImagenPlanoGemini(MultipartFile archivoImagen) throws Exception {
        String base64Image = Base64.getEncoder().encodeToString(archivoImagen.getBytes());
        String mimeType = archivoImagen.getContentType();

        String[] geminiModels = {
                "gemini-3.5-flash",
                "gemini-3.1-flash-lite",
                "gemini-2.5-flash",
                "gemini-2.5-flash-lite",
                "gemini-3.1-flash"
        };

        String requestBody = """
            {
              "contents": [
                {
                  "parts": [
                    {"text": "Actúa como un experto en planos de restaurantes. Identifica las áreas/zonas principales y las mesas dentro de ellas. Calcula las coordenadas X e Y relativas a un lienzo de 800x700 píxeles. Estructura JSON estricta: [ { \\"nombreZona\\": \\"Terraza\\", \\"mesas\\": [ { \\"tipoMesa\\": \\"estándar\\", \\"posicionX\\": 150.5, \\"posicionY\\": 200.0 } ] } ]"},
                    {
                      "inline_data": {
                        "mime_type": "%s",
                        "data": "%s"
                      }
                    }
                  ]
                }
              ],
              "generationConfig": {
                "temperature": 0.3,
                "responseMimeType": "application/json"
              }
            }
            """.formatted(mimeType, base64Image);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        for (String model : geminiModels) {
            try {
                System.out.println("Intentando analizar el plano con: " + model);
                String url = "https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent?key=" + apiKey;
                String response = restTemplate.postForObject(url, requestEntity, String.class);

                JsonNode root = objectMapper.readTree(response);
                return root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText().trim();

            } catch (Exception e) {
                System.err.println("Fallo con el modelo " + model + " -> " + e.getMessage());
            }
        }
        throw new RuntimeException("Error crítico: Todos los modelos de Gemini están saturados.");
    }

    @Override
    public String analizarImagenPlanoGemma3(MultipartFile archivoImagen) throws Exception {
        String base64Image = Base64.getEncoder().encodeToString(archivoImagen.getBytes());

        String requestBody = """
            {
              "model": "%s",
              "prompt": "Actúa como un analizador avanzado de planos. Identifica zonas y mesas. Devuelve JSON: [ { \\"nombreZona\\": \\"string\\", \\"mesas\\": [ { \\"tipoMesa\\": \\"string\\", \\"posicionX\\": 0.0, \\"posicionY\\": 0.0 } ] } ]. Sin texto extra.",
              "images": ["%s"],
              "format": "json",
              "stream": false,
              "options": {
                "temperature": 0.0,
                "top_k": 1,
                "repeat_penalty": 1.5
              }
            }
            """.formatted(ollamaModel, base64Image);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            String response = restTemplate.postForObject(ollamaUrl, requestEntity, String.class);
            JsonNode root = objectMapper.readTree(response);
            String rawOutput = root.path("response").asText().trim();

            JsonNode outputNode = objectMapper.readTree(rawOutput);
            if (outputNode.isObject()) {
                ArrayNode arrayNode = objectMapper.createArrayNode();
                arrayNode.add(outputNode);
                return arrayNode.toString();
            }
            return rawOutput;
        } catch (Exception e) {
            throw new RuntimeException("Error en análisis dinámico de Gemma: " + e.getMessage());
        }
    }

    @Override
    public String analizarImagenPlanoGroq(MultipartFile archivoImagen) throws Exception {
        String base64Image = Base64.getEncoder().encodeToString(archivoImagen.getBytes());
        String mimeType = archivoImagen.getContentType();

        String requestBody = """
        {
          "model": "%s",
          "messages": [
            {
              "role": "user",
              "content": [
                {
                  "type": "text",
                  "text": "Actúa como un experto en planos de restaurantes. Identifica las áreas/zonas principales y las mesas dentro de ellas. Calcula las coordenadas X e Y relativas a un lienzo de 800x700 píxeles. Estructura JSON estricta obligatoria: { \\"zonas\\": [ { \\"nombreZona\\": \\"Terraza\\", \\"mesas\\": [ { \\"tipoMesa\\": \\"estándar\\", \\"posicionX\\": 150.5, \\"posicionY\\": 200.0 } ] } ] }"
                },
                {
                  "type": "image_url",
                  "image_url": {
                    "url": "data:%s;base64,%s"
                  }
                }
              ]
            }
          ],
          "temperature": 0.1,
          "response_format": {"type": "json_object"}
        }
        """.formatted(groqModel, mimeType, base64Image);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqApiKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            String response = restTemplate.postForObject(groqUrl, requestEntity, String.class);
            JsonNode root = objectMapper.readTree(response);
            return root.path("choices").get(0).path("message").path("content").asText().trim();

        } catch (org.springframework.web.client.HttpClientErrorException e) {
            System.err.println("--- ERROR DE API DE GROQ ---");
            System.err.println("Código de estado: " + e.getStatusCode());
            System.err.println("Respuesta del cuerpo: " + e.getResponseBodyAsString());
            System.err.println("----------------------------");
            throw new RuntimeException("Groq rechazó la petición: " + e.getResponseBodyAsString());

        } catch (Exception e) {
            throw new RuntimeException("Error general en Groq Vision: " + e.getMessage());
        }
    }

}