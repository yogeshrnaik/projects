package com.tomtom.cleancode.exercise.productization;

import com.tomtom.places.unicorn.domain.avro.archive.ArchiveName;
import com.tomtom.places.unicorn.domain.avro.normalized.NName;
import com.tomtom.places.unicorn.matching.util.NamesUtil;

final class AlternateName {

    private final NName normalizedName;
    private final ArchiveName archiveName;

    public AlternateName(NName normalizedName, ArchiveName archiveName) {
        this.normalizedName = normalizedName;
        this.archiveName = archiveName;
    }

    public NName getNormalizedName() {
        return normalizedName;
    }

    public ArchiveName getArchiveName() {
        return archiveName;
    }

    public String getName() {
        return archiveName.getName().toString();
    }

    public String getArchiveNameLanguage() {
        return archiveName.getLanguage().toString();
    }

    public String getNormalizedNameLanguage() {
        return NamesUtil.toString(normalizedName.getText().getLanguage());
    }

    public String getNormalizedNameScript() {
        return NamesUtil.toString(normalizedName.getText().getScript());
    }

    @Override
    public String toString() {
        return "AlternateName {normalizedName=[" + normalizedName + "], archiveName=[" + archiveName + "]}";
    }
}