package co.istad.mbanking.features.file;

import co.istad.mbanking.features.file.dto.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    FileResponse upload(MultipartFile file) throws IOException;

}
