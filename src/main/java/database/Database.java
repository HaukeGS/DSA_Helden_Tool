package database;

// import skills.Language;
import aventurian.Aventurian;
import skills.Property;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private List<Property> properties;
    //private List<Language> languages;


    public void initialize() {
        properties = new ArrayList<>();
       // languages = new ArrayList<>();

        //properties.add(new Property("bla", "blub", 350, Optional.of((Aventurian a)-> a.setName("bla")), Optional.of((Aventurian a) -> a.setName("blub"))));


       // languages.add(new Language("Garethi", "wichtigste Sprache", 50, 5, ));

    }
}
