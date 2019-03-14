package ts.oyster.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Station {

    private String stationName;
    private Set<Integer> zones;

    public Station(String name, Integer... zones) {
        this.stationName = name;
        this.zones = new HashSet<>(Arrays.asList(zones));
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Set<Integer> getZones() {
        return zones;
    }

}
