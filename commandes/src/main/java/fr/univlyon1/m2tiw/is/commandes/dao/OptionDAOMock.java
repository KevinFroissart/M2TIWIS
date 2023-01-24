package fr.univlyon1.m2tiw.is.commandes.dao;

import fr.univlyon1.m2tiw.is.commandes.model.Option;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OptionDAOMock /*implements OptionDAO*/ {
//
//    private final Map<String, Option> configurationMocks = new HashMap<>();
//
//    public OptionDAOMock() {
//        String[] configs = {"motorisation/essence", "motorisation/diesel", "motorisation/hybride",
//                "motorisation/électrique", "couleur/blanc", "couleur/noir", "couleur/rouge",
//                "couleur/vert", "couleur/bleu", "couleur/jaune"};
//        for (String s : configs) {
//            String[] couple = s.split("/");
//            Option option = new Option(couple[0], couple[1]);
//            configurationMocks.put(option.getNom(), option);
//        }
//    }
//
//    @Override
//    public Option getOption(String option, String valeur) {
//        Option configuration = new Option(option, valeur);
//        if (configurationMocks.containsKey(configuration.getNom())) {
//            return configurationMocks.get(configuration.getNom());
//        } else {
//            configurationMocks.put(configuration.getNom(), configuration);
//            return configuration;
//        }
//    }
//
//    @Override
//    public Collection<Option> getAllOptions() {
//        return configurationMocks.values();
//    }
}