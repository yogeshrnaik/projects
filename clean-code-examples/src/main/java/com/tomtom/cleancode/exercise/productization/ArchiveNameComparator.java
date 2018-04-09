package com.tomtom.cleancode.exercise.productization;

import java.util.Comparator;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.tomtom.places.unicorn.domain.avro.archive.ArchiveName;

/**
 * A comparator to sort {@link ArchiveName} objects first on language and then on name, both alphabetically (natural String order).
 */
public class ArchiveNameComparator implements Comparator<ArchiveName> {

    private final Function<ArchiveName, String> name = new Function<ArchiveName, String>() {

        @Override
        public String apply(ArchiveName archiveName) {
            return archiveName.getName().toString();
        }

    };
    private final Function<ArchiveName, String> language = new Function<ArchiveName, String>() {

        @Override
        public String apply(ArchiveName archiveName) {
            return archiveName.getLanguage().toString();
        }

    };

    private final Ordering<ArchiveName> ordering = Ordering.natural().onResultOf(language).compound(Ordering.natural().onResultOf(name));

    @Override
    public int compare(ArchiveName name1, ArchiveName name2) {
        return ordering.compare(name1, name2);
    }

}
