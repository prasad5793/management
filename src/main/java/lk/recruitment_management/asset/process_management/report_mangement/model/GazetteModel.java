package lk.recruitment_management.asset.process_management.report_mangement.model;

import lk.recruitment_management.asset.applicant_gazette.entity.ApplicantGazette;
import lk.recruitment_management.asset.gazette.entity.Gazette;
import lk.recruitment_management.asset.interview.entity.Interview;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GazetteModel {
  private Gazette gazette;
  private List< ApplicantGazette > applicantGazettes;
  private List< ApplicantGazette > pendingList;
  private List< ApplicantGazette > approvedList;
  private List< ApplicantGazette > rejectList;
  private List< ApplicantGazette > firstInterviewList;
  private List< ApplicantGazette > firstInterviewPassList;
  private List< ApplicantGazette > firstInterviewRejectList;
  private List< ApplicantGazette > secondInterviewList;
  private List< ApplicantGazette > secondInterviewPassList;
  private List< ApplicantGazette > secondInterviewRejectList;
  private List< ApplicantGazette > thirdInterviewList;
  private List< ApplicantGazette > thirdInterviewPassList;
  private List< ApplicantGazette > thirdInterviewRejectList;
  private List< ApplicantGazette > forthInterviewList;
  private List< ApplicantGazette > forthInterviewPassList;
  private List< ApplicantGazette > forthInterviewRejectList;
  private List< ApplicantGazette > stateIntelligenceSPassList;
  private List< ApplicantGazette > stateIntelligenceFailList;
  private List< ApplicantGazette > crimeRecordDPassList;
  private List< ApplicantGazette > crimeRecordDFailList;
  private List< ApplicantGazette > criminalInvestigationDPassList;
  private List< ApplicantGazette > criminalInvestigationDFailList;

}
