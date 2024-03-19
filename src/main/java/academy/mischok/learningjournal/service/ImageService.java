package academy.mischok.learningjournal.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@EnableScheduling
public class ImageService {

    private static final File PICTURE_DIRECTORY = new File("pictures");
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public ImageService() {
        if (!PICTURE_DIRECTORY.exists() || !PICTURE_DIRECTORY.isDirectory()) {
            if (!PICTURE_DIRECTORY.mkdirs()) {
                throw new RuntimeException("Could not create picture directory");
            }
        }
    }

    public Optional<File> findPicture(String pictureId) {
        return Arrays.stream(Objects.requireNonNull(PICTURE_DIRECTORY
                .listFiles((dir, name) -> name.split("\\.")[0].equals(pictureId))
        )).findFirst();
    }

    public byte[] fileToBytes(final File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    public void deletePicture(String pictureUid) {
        this.findPicture(pictureUid).ifPresent(File::delete);
    }

    public String createPicture(MultipartFile picture) throws IOException {
        final String pictureId = this.generateRandomId();
        final int length = Objects.requireNonNull(picture.getOriginalFilename()).split("\\.").length;
        final String extension = length > 1 ? picture.getOriginalFilename().split("\\.")[length - 1] : "png";
        final File file = new File("pictures/" + pictureId + "." + extension);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new FileSystemException("Could not create file!");
            }
        }
        Files.write(file.toPath(), picture.getBytes());
        return pictureId;
    }

    private boolean existsWithId(String id) {
        if (!PICTURE_DIRECTORY.exists() || !PICTURE_DIRECTORY.isDirectory()) {
            return false;
        }
        return Objects.requireNonNull(PICTURE_DIRECTORY
                .listFiles((dir, name) -> name.split("\\.")[0].equals(name))
        ).length >= 1;
    }

    private String generateRandomId() {
        String id;
        do {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                builder.append(CHARS.charAt(ThreadLocalRandom.current().nextInt(CHARS.length())));
            }
            id = builder.toString();
        } while (existsWithId(id));
        return id;
    }
}