package user_domain.view.language;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Observable;

public class Language extends Observable {
    private static Language instance;
    private Languages selectedLanguage;
    private TranslatedFields fields;

    private Language(){
        setSelectedLanguage(Languages.ENGLISH);
    }

    public static Language getLanguageInstance(){
        if(instance == null)
            instance = new Language();
        return instance;
    }

    public static Language getInstance() {
        return instance;
    }

    public static void setInstance(Language instance) {
        Language.instance = instance;
    }

    public Languages getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(Languages selectedLanguage) {
        String fileName = selectedLanguage.toString().toLowerCase().substring(0,1).toUpperCase() + selectedLanguage.toString().substring(1).toLowerCase() + ".xml";
        try {
            String xml = new String(Files.readAllBytes(Path.of(getClass().getClassLoader().getResource(fileName).toURI())), StandardCharsets.UTF_8);
            XmlMapper xmlMapper = new XmlMapper();
            fields = xmlMapper.readValue(xml, TranslatedFields.class);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        this.selectedLanguage = selectedLanguage;
        setChanged();
        notifyObservers();
    }

    public TranslatedFields getFields() {
        return fields;
    }
}
