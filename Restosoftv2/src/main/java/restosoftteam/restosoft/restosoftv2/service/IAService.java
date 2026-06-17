package restosoftteam.restosoft.restosoftv2.service;

import org.springframework.web.multipart.MultipartFile;

public interface IAService {

    String analizarImagenPlanoGemini (MultipartFile archivoImagen) throws Exception;
    String analizarImagenPlanoGemma3 (MultipartFile archivoImagen) throws Exception;
    String analizarImagenPlanoGroq (MultipartFile archivoImagen) throws Exception;

}
