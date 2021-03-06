package lk.recruitment_management.asset.applicant_non_relative.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.recruitment_management.asset.applicant.entity.Applicant;
import lk.recruitment_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("ApplicantNonRelative")
public class ApplicantNonRelative extends AuditEntity {

    @NotEmpty
    @Column(nullable = false)
    private String name;

    private String address;

    @Size(max = 10, message = "Mobile number length should be contained 10 and 9")
    @Column(unique = true)
    private String mobile;

    private String land;

    @ManyToOne
    private Applicant applicant;

}
