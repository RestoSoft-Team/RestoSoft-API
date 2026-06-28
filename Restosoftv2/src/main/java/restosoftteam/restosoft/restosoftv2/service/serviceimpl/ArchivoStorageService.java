package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ArchivoStorageService {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    public String guardarImagen(MultipartFile archivo) throws IOException {

        if (archivo.isEmpty()) {
            return null;
        }

        Path directorio = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(directorio);

        String nombreUnico = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replaceAll(" ", "_");

        Path destino = directorio.resolve(nombreUnico);
        archivo.transferTo(destino.toFile());

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        return baseUrl + "/uploads/" + nombreUnico;

    }



    public void eliminarImagen(String rutaImagen) {
        if (rutaImagen == null || rutaImagen.isEmpty()) {
            return;
        }

        try {
            String nombreArchivo = rutaImagen.replace("/uploads/", "");

            Path ruta = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(nombreArchivo);
            File archivo = ruta.toFile();

            if (archivo.exists()) {
                archivo.delete();
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar la imagen: " + e.getMessage());
        }
    }
}