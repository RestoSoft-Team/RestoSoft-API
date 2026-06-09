package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import restosoftteam.restosoft.restosoftv2.service.GeminiService;

import java.util.Base64;

@Service
public class GeminiServiceImpl implements GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String analizarImagenPlano(MultipartFile archivoImagen) throws Exception {

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent?key=" + apiKey;

        String base64Image = Base64.getEncoder().encodeToString(archivoImagen.getBytes());
        String mimeType = archivoImagen.getContentType();

        String requestBody = """
            {
              "contents": [
                {
                  "parts": [
                    {"text": "Actúa como un experto en planos de restaurantes. Identifica las áreas/zonas principales y las mesas dentro de ellas. Calcula las coordenadas X e Y relativas a un lienzo de 800x700 píxeles. DEVUELVE ÚNICAMENTE UN ARREGLO JSON VÁLIDO. Estructura estricta: [ { \\"nombreZona\\": \\"Terraza\\", \\"mesas\\": [ { \\"tipoMesa\\": \\"estándar\\", \\"posicionX\\": 150.5, \\"posicionY\\": 200.0 } ] } ]"},
                    {
                      "inline_data": {
                        "mime_type": "%s",
                        "data": "%s"
                      }
                    }
                  ]
                }
              ]
            }
            """.formatted(mimeType, base64Image);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            String response = restTemplate.postForObject(url, requestEntity, String.class);
            JsonNode root = new ObjectMapper().readTree(response);

            return root.path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text").asText().replace("```json", "").replace("```", "").trim();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}