package fr.univlyon1.m2tiw.is.commandes.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.univlyon1.m2tiw.tiw1.annotations.Configuration;

/**
 * Modèle de configuration de l'application.
 */
@Configuration
public class ApplicationConfiguration {

    @JsonProperty("application-config")
    private Configuration configuration;

    public Configuration getConfiguration() {
        return configuration;
    }

    public static class Configuration {

        @JsonProperty("name")
        private String name;
        @JsonProperty("business-components")
        private List<Component> businessComponents;
        @JsonProperty("controller-components")
        private List<Component> controllerComponents;
        @JsonProperty("service-components")
        private List<Component> serviceComponents;
        @JsonProperty("persistence-components")
        private List<Component> persistenceComponents;

        public static class Component {
            @JsonProperty("class-name")
            private String className;

            @JsonProperty("hasImplementation")
            private boolean hasImplementation;

            @JsonProperty("params")
            private List<Parameter> parameters;

            public String getClassName() {
                return className;
            }

            public boolean hasImplementation() {
                return hasImplementation;
            }

            public Map<String, String> getParameters() {
                return Optional.ofNullable(parameters)
                        .orElse(Collections.emptyList())
                        .stream()
                        .collect(Collectors.toMap(Parameter::getName, Parameter::getValue));
            }
        }

        public static class Parameter {
            @JsonProperty("name")
            private String name;
            @JsonProperty("value")
            private String value;

            public String getName() {
                return name;
            }

            public String getValue() {
                return value;
            }
        }

        public List<Component> getAllComponents() {
            List<Component> allComponents = new ArrayList<>();
            allComponents.addAll(businessComponents);
            allComponents.addAll(controllerComponents);
            allComponents.addAll(serviceComponents);
            allComponents.addAll(persistenceComponents);
            return allComponents;
        }
    }
}
