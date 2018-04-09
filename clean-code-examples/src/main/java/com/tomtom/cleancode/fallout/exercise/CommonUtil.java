package com.tomtom.cleancode.fallout.exercise;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.tomtom.places.unicorn.domain.avro.archive.ArchiveBrandName;
import com.tomtom.places.unicorn.domain.avro.archive.ArchivePlace;
import com.tomtom.places.unicorn.domain.avro.archive.ArchivePlaceDiff;
import com.tomtom.places.unicorn.domain.avro.archive.POI;
import com.tomtom.places.unicorn.domain.productized.archive.ArchivePlaces;

/**
 * Common utility class
 *
 * @author anjanapp
 */
public class CommonUtil {

    public static final String COMPL_MERGE_FOLDER = "completed";
    public static final String INPROGRESS_MERGE_FOLDER = "inprogress";
    public static final String RETRY_FOLDER = "Retry";
    private static final String UNKNOWN = "Unknown";
    private static final Logger LOGGER = Logger.getLogger(CommonUtil.class);

    public static List<ArchiveBrandName> getBrand(final ArchivePlace place) throws IllegalArgumentException {
        final POI poi = ArchivePlaces.getPoi(place);
        if (poi != null && poi.getBrandNames() != null) {
            return poi.getBrandNames();
        }
        return null;
    }

    public static List<String> getBrand(final POI poi) throws IllegalArgumentException {
        final List<String> brands = new ArrayList<String>();
        if (poi != null && poi.getBrandNames() != null) {

            for (final ArchiveBrandName archiveBrandName : poi.getBrandNames()) {
                brands.add(archiveBrandName.getValue().toString());
            }

            return brands;
        }
        return null;
    }

    public static List<String> getBrands(final ArchivePlaceDiff place) throws IllegalArgumentException {

        final Set<String> brands = new LinkedHashSet<String>();
        if (place.getBefore() != null) {
            final POI poi = ArchivePlaces.getPoi(place.getBefore());
            brands.addAll(getBrand(poi));
        }

        if (place.getAfter() != null) {
            final POI poi = ArchivePlaces.getPoi(place.getAfter());
            brands.addAll(getBrand(poi));
        }
        return Lists.newArrayList(brands);
    }

    public static List<String> getBrands(final ArchivePlace place) throws IllegalArgumentException {

        final List<String> brands = new ArrayList<String>();
        if (place != null) {

            for (final ArchiveBrandName archiveBrandName : getBrand(place)) {
                brands.add(archiveBrandName.getValue().toString());
            }
        }

        return brands;
    }

}
