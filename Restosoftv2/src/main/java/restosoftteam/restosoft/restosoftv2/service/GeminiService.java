package restosoftteam.restosoft.restosoftv2.service;

import org.springframework.web.multipart.MultipartFile;

public interface GeminiService {

    String analizarImagenPlano (MultipartFile archivoImagen) throws Exception;

}
