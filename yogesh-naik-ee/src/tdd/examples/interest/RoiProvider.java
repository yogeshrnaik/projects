package tdd.examples.interest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoiProvider {

    private List<RoiCriteria> roiCriterias;

    public RoiProvider(RoiCriteria... roiCriteria) {
        roiCriterias = new ArrayList<>();
        roiCriterias.addAll(Arrays.asList(roiCriteria));
    }

    public double getRateOfInterest() {
       return 8.0 + roiCriterias.stream()
               .filter(RoiCriteria::isApplicable)
               .mapToDouble(RoiCriteria::getBonusRateOfInterest)
               .sum();

    }
}
