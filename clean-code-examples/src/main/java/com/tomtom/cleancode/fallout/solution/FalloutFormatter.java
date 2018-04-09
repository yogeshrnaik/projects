package com.tomtom.cleancode.fallout.solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.tomtom.places.unicorn.domain.avro.archive.ArchiveFallout;
import com.tomtom.places.unicorn.domain.avro.archive.ArchivePlace;
import com.tomtom.places.unicorn.domain.avro.archive.ArchivePlaceDiff;
import com.tomtom.places.unicorn.domain.avro.archive.POI;
import com.tomtom.places.unicorn.domain.avro.archive.RelatedArchivePlaceDiff;
import com.tomtom.places.unicorn.domain.avro.archive.Violation;
import com.tomtom.places.unicorn.domain.avro.archive.ViolationVariable;
import com.tomtom.places.unicorn.domain.diff.ArchivePlaceDiffs;
import com.tomtom.places.unicorn.domain.util.CharSequenceUtil;

public class FalloutFormatter {

    private static final String REASON_OF_VIOLATION_KEY = "Reason of Violation";
    private static final String EMPTY = "";
    private static final String NEW_LINE = "\n";
    private static final String CAUSED_BY = "Caused by";

    public List<String[]> formatFallout(long sNo, ArchiveFallout fallout) {

        String brandName = null;
        String transactionType = null;

        List<String[]> lines = new ArrayList<String[]>();
        String reason = CharSequenceUtil.asNonNullString(fallout.getReason());
        String transactionId = CharSequenceUtil.asNonNullString(fallout.getTransactionId());

        if (CollectionUtils.isEmpty(fallout.getViolations())) {
            // it's a system failure or a QA failure

            // Create failures string separated by ;
            String failures = "";
            if (fallout.getFailures() != null) {
                failures = Joiner.on("; ").join(Iterables.transform(fallout.getFailures(), new Function<CharSequence, String>() {

                    @Override
                    public String apply(CharSequence failure) {
                        return CharSequenceUtil.asNonNullString(failure);
                    }
                }));
            }

            RelatedArchivePlaceDiff relatedArchivePlaceDiff = fallout.getFallout();
            List<ArchivePlaceDiff> archivePlaceDiffList = relatedArchivePlaceDiff.getArchivePlaceDiffs();
            if (archivePlaceDiffList != null) {
                for (ArchivePlaceDiff archivePlaceDiff : archivePlaceDiffList) {
                    FalloutLine falloutLine = null;
                    if (archivePlaceDiff != null) {
                        ArchivePlace before = archivePlaceDiff.getBefore();
                        ArchivePlace after = archivePlaceDiff.getAfter();
                        brandName = Joiner.on(",").skipNulls().join(CommonUtil.getBrands(archivePlaceDiff));
                        transactionType = getTransactionType(archivePlaceDiff);
                        if (after != null) {
                            falloutLine =
                                new FalloutLine(sNo, fallout.getErrorType().toString(), transactionId, reason,
                                    getFeatureID(archivePlaceDiff),
                                    getFeatureDesc(archivePlaceDiff), failures, brandName, transactionType, fallout.getFallout(),
                                    archivePlaceDiff);
                            lines.add(falloutLine.convertToLine());
                        } else if (before != null) {
                            falloutLine =
                                new FalloutLine(sNo, fallout.getErrorType().toString(), transactionId, reason,
                                    getFeatureID(archivePlaceDiff),
                                    getFeatureDesc(archivePlaceDiff), failures, brandName, transactionType, fallout.getFallout(),
                                    archivePlaceDiff);
                            lines.add(falloutLine.convertToLine());
                        }
                    }

                }
            }

        } else {
            // it's one or more QA violations

            for (Violation violation : fallout.getViolations()) {
                if (CharSequenceUtil.isNotEmpty(violation.getMessage())) {
                    // take the violation message as the reason instead of the unfriendly QA_CHECK_COMMITER_ERROR from the exception
                    reason = CharSequenceUtil.asNonNullString(violation.getMessage());
                }
                for (ViolationVariable var : violation.getVariables()) {
                    // if the violation has a reason variable, take this as the reason because it's usually more detailed than the violation
                    // message
                    if (REASON_OF_VIOLATION_KEY.equalsIgnoreCase(var.getKey().toString())) {
                        String reasonFromKey = CharSequenceUtil.asNonNullString(var.getValue());
                        reason = StringUtils.isEmpty(reasonFromKey) ? reason : reasonFromKey;
                        break;
                    }
                }
                RelatedArchivePlaceDiff relatedArchivePlaceDiff = fallout.getFallout();
                List<ArchivePlaceDiff> archivePlaceDiffList = relatedArchivePlaceDiff.getArchivePlaceDiffs();

                if (archivePlaceDiffList != null) {
                    for (ArchivePlaceDiff archivePlaceDiff : archivePlaceDiffList) {
                        if (archivePlaceDiff != null) {
                            ArchivePlace before = archivePlaceDiff.getBefore();
                            ArchivePlace after = archivePlaceDiff.getAfter();
                            brandName = Joiner.on(",").skipNulls().join(CommonUtil.getBrands(archivePlaceDiff));
                            transactionType = getTransactionType(archivePlaceDiff);
                            if (after != null) {
                                addFalloutLine(sNo, fallout, transactionId, lines, reason,
                                    violation, after, brandName, transactionType, archivePlaceDiff);
                            } else if (before != null) {
                                addFalloutLine(sNo, fallout, transactionId, lines, reason,
                                    violation, before, brandName, transactionType, archivePlaceDiff);
                            }
                        }
                    }
                }

            }

        }
        return lines;
    }

    private void addFalloutLine(long sNo, ArchiveFallout fallout, String transactionId,
        List<String[]> lines, String reason, Violation violation,
        ArchivePlace archivePlace, String brandName, String transactionType, ArchivePlaceDiff archivePlaceDiff) {
        FalloutLine falloutLine =
            new FalloutLine(sNo, fallout.getErrorType()
                .toString(), transactionId, reason,
                CharSequenceUtil.asNonNullString(violation
                    .getFeatureId()),
                CharSequenceUtil.asNonNullString(violation.getFeatureType()), CharSequenceUtil.asNonNullString(violation
                    .getMessage()),
                violationVariablesAsMap(violation), violation.getRuleId(), violation.getRuleMajorVersion(),
                violation.getRuleMediumVersion(), CharSequenceUtil.asNonNullString(violation.getRuleBehaviour()),
                CharSequenceUtil.asNonNullString(violation.getRuleSeverity()),
                CharSequenceUtil.asNonNullString(violation.getRuleLink()), brandName, transactionType, fallout.getFallout(),
                archivePlaceDiff);
        lines.add(falloutLine.convertToLine());
    }

    private Map<String, String> violationVariablesAsMap(Violation violation) {
        Map<String, String> violationVariablesAsMap = Maps.newHashMap();
        for (ViolationVariable var : violation.getVariables()) {
            violationVariablesAsMap.put(CharSequenceUtil.asString(var.getKey()), CharSequenceUtil.asNonNullString(var.getValue()));
        }
        return violationVariablesAsMap;
    }

    private String getFeatureDesc(ArchivePlaceDiff diff) {
        POI poi = getPoi(diff);
        if (poi != null && poi.getGdfFeatureCodeDesc() != null) {
            return poi.getGdfFeatureCodeDesc().toString();
        } else {
            return "";
        }
    }

    private String getFeatureID(ArchivePlaceDiff diff) {
        String featureID = ArchivePlaceDiffs.getCppId(diff);
        return featureID;
    }

    private String getFeatureID(RelatedArchivePlaceDiff diff) {
        String featureID = ArchivePlaceDiffs.getCppId(diff.getArchivePlaceDiffs().get(0));
        return featureID;
    }

    private String getFeatureDesc(RelatedArchivePlaceDiff diff) {
        POI poi = getPoi(diff);
        if (poi != null && poi.getGdfFeatureCodeDesc() != null) {
            return poi.getGdfFeatureCodeDesc().toString();
        } else {
            return "";
        }
    }

    private POI getPoi(RelatedArchivePlaceDiff diff) {
        List<ArchivePlaceDiff> archivePlaceDiffList = diff.getArchivePlaceDiffs();
        POI poi = null;
        if (archivePlaceDiffList != null) {
            for (ArchivePlaceDiff archivePlaceDiff : archivePlaceDiffList) {
                if (archivePlaceDiff != null) {
                    if (archivePlaceDiff.getAfter() != null) {
                        poi = archivePlaceDiff.getAfter().getPois().get(0);
                        break;
                    } else if (archivePlaceDiff.getBefore() != null) {
                        poi = archivePlaceDiff.getBefore().getPois().get(0);
                        break;
                    }

                }

            }
        }

        return poi;
    }

    private POI getPoi(ArchivePlaceDiff archivePlaceDiff) {
        POI poi = null;
        if (archivePlaceDiff.getAfter() != null) {
            poi = archivePlaceDiff.getAfter().getPois().get(0);
        } else if (archivePlaceDiff.getBefore() != null) {
            poi = archivePlaceDiff.getBefore().getPois().get(0);
        }

        return poi;
    }

    private String getTransactionType(ArchivePlaceDiff archivePlaceDiff) {
        String transactionName = null;
        if (archivePlaceDiff.getBefore() == null
            && archivePlaceDiff.getAfter() != null) {
            transactionName = "Add";
        } else if (archivePlaceDiff.getBefore() != null
            && archivePlaceDiff.getAfter() != null) {
            transactionName = "Edit";
        } else if (archivePlaceDiff.getBefore() != null
            && archivePlaceDiff.getAfter() == null) {
            transactionName = "Delete";
        }

        return transactionName;
    }

    private static class FalloutLine {

        private final long sNo;
        private final String reason;
        private final String errorType;
        private final String transactionId;
        private final String featureId;
        private final String featureType;
        private final String violationMessage;
        private final Map<String, String> violationVariables;
        private final Integer ruleId;
        private final Integer majorVersion;
        private final Integer mediumVersion;
        private final String ruleBehaviour;
        private final String ruleSeverity;
        private final String link;
        private final String failures;
        private final RelatedArchivePlaceDiff diff;
        private final String brandName;
        private final String transactionType;
        private final ArchivePlaceDiff archivePlaceDiff;

        public FalloutLine(long sNo, String errorType, String transactionId, String reason, String featureId, String featureType,
            String violationMessage,
            Map<String, String> violationVariables,
            Integer ruleId, Integer majorVersion, Integer mediumVersion, String ruleBehaviour, String ruleSeverity, String link,
            String brandName, String transactionType, RelatedArchivePlaceDiff diff, ArchivePlaceDiff archivePlaceDiff) {

            this(sNo, errorType, transactionId, reason, featureId, featureType, violationMessage, violationVariables, ruleId, majorVersion,
                mediumVersion,
                ruleBehaviour, ruleSeverity, link, null, brandName, transactionType, diff, archivePlaceDiff);
        }

        public FalloutLine(long sNo, String errorType, String transactionId, String reason, String featureId, String featureType,
            String failures,
            String brandName, String transactionType, RelatedArchivePlaceDiff diff, ArchivePlaceDiff archivePlaceDiff) {
            this(sNo, errorType, transactionId, reason, featureId, featureType, null, null, null, null, null, null, null, null, failures,
                brandName,
                transactionType, diff, archivePlaceDiff);
        }

        private FalloutLine(long sNo, String errorType, String transactionId, String reason, String featureId, String featureType,
            String violationMessage,
            Map<String, String> violationVariables,
            Integer ruleId, Integer majorVersion, Integer mediumVersion, String ruleBehaviour, String ruleSeverity, String link,
            String failures,
            String brandName, String transactionType, RelatedArchivePlaceDiff diff, ArchivePlaceDiff archivePlaceDiff) {

            this.sNo = sNo;
            this.errorType = errorType;
            this.transactionId = transactionId;
            this.featureId = featureId;
            this.featureType = featureType;
            this.reason = reason;
            this.violationMessage = violationMessage;
            this.violationVariables = violationVariables;
            this.ruleId = ruleId;
            this.majorVersion = majorVersion;
            this.mediumVersion = mediumVersion;
            this.ruleBehaviour = ruleBehaviour;
            this.ruleSeverity = ruleSeverity;
            this.link = link;
            this.failures = failures;
            this.diff = diff;
            this.brandName = brandName;
            this.transactionType = transactionType;
            this.archivePlaceDiff = archivePlaceDiff;
        }

        private String formatReason() {
            // Restricting reason till \r\n
            if (reason.contains(NEW_LINE)) {
                return reason.substring(0, reason.indexOf(NEW_LINE));
            }
            return reason;
        }

        private String formatFailureMessages(String failures) {
            // Restricting failure till \r\n
            String failureMessage = "";
            if (failures.contains(NEW_LINE)) {
                failureMessage = failures.substring(0, failures.indexOf(NEW_LINE));
                if (failures.contains(CAUSED_BY)) {
                    failureMessage = failureMessage + ";" + formatCausedBy(failures.substring(failures.indexOf(CAUSED_BY)));
                }
            } else {
                failureMessage = failures;
            }
            return failureMessage;
        }

        private String formatCausedBy(String causedBy) {
            // Restricting causedBy till \r\n
            if (causedBy.contains(NEW_LINE)) {
                return causedBy.substring(0, causedBy.indexOf(NEW_LINE));
            }
            return causedBy;
        }

        private String formatViolationVariables() {
            if (violationVariables == null) {
                return "";
            }
            Map<String, String> violationFormated = Maps.newHashMap();
            for (Map.Entry<String, String> violation : violationVariables.entrySet()) {
                String value = trimTillNewLine(violation.getValue());
                String key = trimTillNewLine(violation.getKey());
                violationFormated.put(key, value);
            }
            return Joiner.on("; ").withKeyValueSeparator(": ").join(violationFormated);
        }

        private String trimTillNewLine(String value) {
            if (value.contains(NEW_LINE)) {
                return value.substring(0, value.indexOf(NEW_LINE));
            }
            return value;
        }

        private String formatRuleId() {
            return integerToString(ruleId);
        }

        private static String integerToString(Integer data) {
            return data != null ? data.toString() : EMPTY;
        }

        private String formatVersion() {
            if (!(majorVersion == null && mediumVersion == null)) {
                return integerToString(majorVersion) + "." + integerToString(mediumVersion);
            } else {
                return EMPTY;
            }
        }

        private String formatRelatedArchivePlaceDiff() {
            if (diff != null) {
                if (diff.getArchivePlaceDiffs().size() > 1 && archivePlaceDiff != null) {
                    return archivePlaceDiff.toString();
                } else {
                    return diff.toString();
                }
            } else {
                return "";
            }
        }

        private String[] convertToLine() {
            return new String[] {
                String.valueOf(sNo),
                errorType,
                StringUtils.defaultString(transactionId),
                StringUtils.defaultString(featureId),
                StringUtils.defaultString(featureType),
                formatReason(),
                StringUtils.defaultString(violationMessage),
                formatViolationVariables(),
                getCountry(),
                getState(),
                formatRuleId(),
                formatVersion(),
                StringUtils.defaultString(ruleBehaviour),
                StringUtils.defaultString(ruleSeverity),
                StringUtils.defaultString(link),
                formatFailureMessages(StringUtils.defaultString(failures)),
                getLatitudeOfPoi(),
                getLongitudeOfPoi(),
                getExternalIdentifierOfPoi(),
                StringUtils.defaultString(brandName),
                StringUtils.defaultString(transactionType),
                formatRelatedArchivePlaceDiff()};
        }

        private String getLatitudeOfPoi() {
            POI poi = null;
            if (archivePlaceDiff.getAfter() != null) {
                poi = archivePlaceDiff.getAfter().getPois().get(0);
            } else if (archivePlaceDiff.getBefore() != null) {
                poi = archivePlaceDiff.getBefore().getPois().get(0);
            }

            return CharSequenceUtil.asNonNullString(poi.getLatitudeOfPoi());
        }

        private String getLongitudeOfPoi() {
            POI poi = null;
            if (archivePlaceDiff.getAfter() != null) {
                poi = archivePlaceDiff.getAfter().getPois().get(0);
            } else if (archivePlaceDiff.getBefore() != null) {
                poi = archivePlaceDiff.getBefore().getPois().get(0);
            }

            return CharSequenceUtil.asNonNullString(poi.getLongitudeOfPoi());
        }

        private String getExternalIdentifierOfPoi() {
            POI poi = null;
            if (archivePlaceDiff.getAfter() != null) {
                poi = archivePlaceDiff.getAfter().getPois().get(0);
            } else if (archivePlaceDiff.getBefore() != null) {
                poi = archivePlaceDiff.getBefore().getPois().get(0);
            }

            return CharSequenceUtil.asNonNullString(poi.getExternalIdentifier());
        }

        private String getCountry() {
            return String.valueOf(getArchivePlace().getIso3Country());
        }

        private String getState() {
            if (getArchivePlace().getState() != null) {
                return getArchivePlace().getState().toString();
            } else {
                return "";
            }
        }

        private POI getPoi() {

            return getArchivePlace().getPois().get(0);
        }

        private ArchivePlace getArchivePlace() {
            List<ArchivePlaceDiff> archivePlaceDiffList = diff
                .getArchivePlaceDiffs();
            ArchivePlace archivePlace = null;
            if (archivePlaceDiffList != null) {
                for (ArchivePlaceDiff archivePlaceDiff : archivePlaceDiffList) {
                    if (archivePlaceDiff != null) {
                        if (archivePlaceDiff.getAfter() != null) {
                            archivePlace = archivePlaceDiff.getAfter();
                            break;
                        } else if (archivePlaceDiff.getBefore() != null) {
                            archivePlace = archivePlaceDiff.getBefore();
                            break;
                        }
                    }
                }
            }
            return archivePlace;
        }

    }
}
