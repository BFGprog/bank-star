package sky.pro.bankstar.service;

import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;
import sky.pro.bankstar.model.Info;

@Service
public class InfoService {

    private final BuildProperties buildProperties;

    public InfoService(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    public Info getInfo() {
        return new Info(buildProperties.getName(), buildProperties.getVersion());
    }
}
