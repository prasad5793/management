package lk.recruitment_management.asset.applicant_gazette.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicantGazetteStatus {
    P("Pending"),
    A("Approved"),
    REJ("Reject"),
    FST("First Interview"),
    FSTP("First Interview Pass"),
    FSTR("First Interview Reject"),
    SND("Second Interview"),
    SNDP("Second Interview Pass"),
    SNDR("Second Interview Reject"),
    TND("Third Interview"),
    TNDP("Third Interview Pass"),
    TNDR("Third Interview Reject"),
    FTH("Forth Interview"),
    FTHP("Forth Interview Pass"),
    FTHR("Forth Interview Reject");

  private final String applicantGazetteStatus;
}
