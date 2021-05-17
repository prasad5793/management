package lk.recruitment_management.asset.process_management.report_mangement;

import lk.recruitment_management.asset.applicant.entity.Applicant;
import lk.recruitment_management.asset.applicant.service.ApplicantService;
import lk.recruitment_management.asset.applicant_gazette.entity.ApplicantGazette;
import lk.recruitment_management.asset.applicant_gazette.entity.enums.ApplicantGazetteStatus;
import lk.recruitment_management.asset.applicant_gazette.entity.enums.ApplyingRank;
import lk.recruitment_management.asset.applicant_gazette.service.ApplicantGazetteService;
import lk.recruitment_management.asset.applicant_gazette_sis_crd_cid_result.entity.ApplicantGazetteSisCrdCid;
import lk.recruitment_management.asset.applicant_gazette_sis_crd_cid_result.entity.enums.InternalDivision;
import lk.recruitment_management.asset.applicant_gazette_sis_crd_cid_result.entity.enums.PassFailed;
import lk.recruitment_management.asset.applicant_gazette_sis_crd_cid_result.service.ApplicantGazetteSisCrdCidService;
import lk.recruitment_management.asset.common_asset.model.TwoDate;
import lk.recruitment_management.asset.gazette.entity.Gazette;
import lk.recruitment_management.asset.gazette.service.GazetteService;
import lk.recruitment_management.asset.process_management.report_mangement.model.GazetteModel;
import lk.recruitment_management.util.service.DateTimeAgeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/report" )
public class ReportController {
  private final ApplicantService applicantService;
  private final ApplicantGazetteSisCrdCidService applicantGazetteSisCrdCidService;
  private final GazetteService gazetteService;
  private final ApplicantGazetteService applicantGazetteService;
  private final DateTimeAgeService dateTimeAgeService;

  public ReportController(ApplicantService applicantService,
                          ApplicantGazetteSisCrdCidService applicantGazetteSisCrdCidService, GazetteService gazetteService, ApplicantGazetteService applicantGazetteService, DateTimeAgeService dateTimeAgeService) {
    this.applicantService = applicantService;
    this.applicantGazetteSisCrdCidService = applicantGazetteSisCrdCidService;
    this.gazetteService = gazetteService;
    this.applicantGazetteService = applicantGazetteService;
    this.dateTimeAgeService = dateTimeAgeService;
  }

  @GetMapping( "/gazette" )
  public String gazette(Model model) {
    model.addAttribute("gazettes", gazetteService.findAll());
    commonApplicant(model);
    return "report/gazetteReportView";
  }

  @PostMapping( "/applicant/search" )
  public String getTwoDate(@ModelAttribute TwoDate twoDate, Model model) {
    List< Applicant > applicants = new ArrayList<>();
    System.out.println(" two datesa  " + twoDate.toString());
    applicantGazetteService.findByCreatedAtIsBetweenAndApplicantGazetteStatusAndApplyingRank(dateTimeAgeService
                                                                                                 .dateTimeToLocalDateStartInDay(twoDate.getStartDate()),
                                                                                             dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate())
        , twoDate.getApplicantGazetteStatus(), twoDate.getApplyingRank())
        .forEach(x -> applicants.add(applicantService.findById(x.getApplicant().getId())));

    model.addAttribute("applicants", applicants);
    commonApplicant(model);
    return "report/gazetteReportView";
  }

  private void commonApplicant(Model model) {
    model.addAttribute("contendHeader", "Applicant Registration");
    model.addAttribute("applyingRanks", ApplyingRank.values());
    model.addAttribute("applicantGazetteStatuses", ApplicantGazetteStatus.values());
  }

  @GetMapping( "/gazette/{id}" )
  public String accordingToGazette(Model model) {
    LocalDate localDate = LocalDate.now();
    TwoDate twoDate = new TwoDate();
    twoDate.setEndDate(localDate);
    twoDate.setStartDate(localDate.minusDays(30));
    return commonGazette(model, twoDate);

  }

  @PostMapping( "/gazette/search" )
  public String getAllPaymentToPayBetweenTwoDate(@ModelAttribute TwoDate twoDate, Model model) {
    return commonGazette(model, twoDate);
  }

  private String commonGazette(Model model, TwoDate twoDate) {
    commonApplicant(model);
    model.addAttribute("message",
                       "This report was belong since " + twoDate.getStartDate() + " to " + twoDate.getEndDate());

    LocalDateTime startAt = dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate());
    LocalDateTime endAt = dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate());
    List< Gazette > gazettes = gazetteService.findByCreatedAtIsBetween(startAt, endAt);
    List< GazetteModel > gazetteModels = new ArrayList<>();
    for ( Gazette gazette : gazettes ) {
      GazetteModel gazetteModel = new GazetteModel();

      List< ApplicantGazette > applicantGazettes = applicantGazetteService.findByGazette(gazette);
      if ( twoDate.getApplyingRank() != null ) {
        applicantGazettes =
            applicantGazettes.stream().filter(x -> x.getApplyingRank().equals(twoDate.getApplyingRank())).collect(Collectors.toList());
      }
      if ( twoDate.getApplicantGazetteStatus() != null ) {
        applicantGazettes =
            applicantGazettes.stream().filter(x -> x.getApplicantGazetteStatus().equals(twoDate.getApplicantGazetteStatus())).collect(Collectors.toList());
      }
      gazetteModel.setGazette(gazette);
      gazetteModel.setApplicantGazettes(applicantGazettes);
      gazetteModel.setPendingList(accordingToStatus(applicantGazettes, ApplicantGazetteStatus.P));
      gazetteModel.setApprovedList(accordingToStatus(applicantGazettes, ApplicantGazetteStatus.A));
      gazetteModel.setRejectList(accordingToStatus(applicantGazettes, ApplicantGazetteStatus.REJ));
      gazetteModel.setFirstInterviewList(accordingToStatus(applicantGazettes, ApplicantGazetteStatus.FST));
      gazetteModel.setFirstInterviewPassList(accordingToStatus(applicantGazettes, ApplicantGazetteStatus.FSTP));
      gazetteModel.setFirstInterviewRejectList(accordingToStatus(applicantGazettes, ApplicantGazetteStatus.FSTR));
      gazetteModel.setSecondInterviewList(accordingToStatus(applicantGazettes, ApplicantGazetteStatus.SND));
      gazetteModel.setSecondInterviewPassList(accordingToStatus(applicantGazettes, ApplicantGazetteStatus.SNDP));
      gazetteModel.setSecondInterviewRejectList(accordingToStatus(applicantGazettes, ApplicantGazetteStatus.SNDR));
      gazetteModel.setThirdInterviewList(accordingToStatus(applicantGazettes, ApplicantGazetteStatus.TND));
      gazetteModel.setThirdInterviewPassList(accordingToStatus(applicantGazettes, ApplicantGazetteStatus.TNDP));
      gazetteModel.setThirdInterviewRejectList(accordingToStatus(applicantGazettes, ApplicantGazetteStatus.TNDR));
      gazetteModel.setForthInterviewList(accordingToStatus(applicantGazettes, ApplicantGazetteStatus.FTH));
      gazetteModel.setForthInterviewPassList(accordingToStatus(applicantGazettes, ApplicantGazetteStatus.FTHP));
      gazetteModel.setForthInterviewRejectList(accordingToStatus(applicantGazettes, ApplicantGazetteStatus.FTHR));
      gazetteModel.setStateIntelligenceSPassList(accordingToInternalInstitute(applicantGazettes, PassFailed.PASS,
                                                                              InternalDivision.SIS));
      gazetteModel.setStateIntelligenceFailList(accordingToInternalInstitute(applicantGazettes, PassFailed.FAILED,
                                                                             InternalDivision.SIS));
      gazetteModel.setCrimeRecordDPassList(accordingToInternalInstitute(applicantGazettes, PassFailed.PASS,
                                                                        InternalDivision.CRD));
      gazetteModel.setCrimeRecordDFailList(accordingToInternalInstitute(applicantGazettes, PassFailed.FAILED,
                                                                        InternalDivision.CRD));
      gazetteModel.setCriminalInvestigationDPassList(accordingToInternalInstitute(applicantGazettes, PassFailed.PASS,
                                                                                  InternalDivision.CID));
      gazetteModel.setCriminalInvestigationDFailList(accordingToInternalInstitute(applicantGazettes,
                                                                                  PassFailed.FAILED,
                                                                                  InternalDivision.CID));
      gazetteModels.add(gazetteModel);
    }


    model.addAttribute("gazetteModels", gazetteModels);
    return "report/gazetteReport";
  }

  private List< ApplicantGazette > accordingToStatus(List< ApplicantGazette > applicantGazettes,
                                                     ApplicantGazetteStatus applicantGazetteStatus) {
    return applicantGazettes.stream().filter(x -> x.getApplicantGazetteStatus().equals(applicantGazetteStatus)).collect(Collectors.toList());
  }

  private List< ApplicantGazette > accordingToInternalInstitute(List< ApplicantGazette > applicantGazettes,
                                                                PassFailed passFailed,
                                                                InternalDivision internalDivision) {
    List< ApplicantGazette > applicantGazetteList = new ArrayList<>();
    for ( ApplicantGazette applicantGazette : applicantGazettes ) {
      applicantGazette.getApplicantGazetteSisCrdCids().forEach(x -> {
                                                                 ApplicantGazetteSisCrdCid applicantGazetteSisCrdCid
                                                                     =
                                                                     applicantGazetteSisCrdCidService.findById(x.getId());
                                                                 if ( applicantGazetteSisCrdCid.getInternalDivision().equals(internalDivision) &&
                                                                     applicantGazetteSisCrdCid.getPassFailed().equals(passFailed) ) {
                                                                   applicantGazetteList.add(applicantGazette);
                                                                 }
                                                               }
                                                              );
    }

    return applicantGazetteList;

  }
}
