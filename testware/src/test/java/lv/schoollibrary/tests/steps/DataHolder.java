package lv.schoollibrary.tests.steps;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

import cucumber.runtime.java.guice.ScenarioScoped;

import static com.google.common.collect.Maps.newHashMap;

@ScenarioScoped
public class DataHolder {

    private final Map<String, Pair<String, String>> schoolKids = newHashMap();

    public String findKidId(String kidName, String kidSurname) {
        for (Map.Entry<String, Pair<String, String>> entry : schoolKids.entrySet()) {
            Pair<String, String> pair = entry.getValue();
            if (kidName.equals(pair.getLeft()) && kidSurname.equals(pair.getRight())) {
                return entry.getKey();
            }
        }
        throw new RuntimeException("Kid not found");
    }

    public void addSchoolKid(String foundKidId, String name, String surname) {
        schoolKids.put(foundKidId, Pair.of(name, surname));
    }
}
