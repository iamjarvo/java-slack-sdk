package testing.json;

import com.github.seratch.jslack.SlackConfig;
import com.github.seratch.jslack.app_backend.events.payload.EventsApiPayload;
import com.github.seratch.jslack.common.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class EventsPayloadDumper {

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private String outputDirectory;

    public EventsPayloadDumper(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void dump(String path, EventsApiPayload<?> payload) throws IOException {
        SlackConfig config = new SlackConfig();
        config.setPrettyResponseLoggingEnabled(true);
        String json = GsonFactory.createSnakeCase(config).toJson(payload);
        Path filePath = new File(toFilePath(path)).toPath();
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, json.getBytes(UTF_8));
    }

    private String toFilePath(String path) {
        return outputDirectory + "/" + path + ".json";
    }

}