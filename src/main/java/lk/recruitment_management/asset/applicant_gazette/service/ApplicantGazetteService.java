package lk.recruitment_management.asset.applicant_gazette.service;



import lk.recruitment_management.asset.applicant_gazette.dao.ApplicantGazetteDao;
import lk.recruitment_management.asset.applicant_gazette.entity.ApplicantGazette;
import lk.recruitment_management.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicantGazetteService implements AbstractService< ApplicantGazette, Integer> {

    private final ApplicantGazetteDao applicantGazetteDao;
    @Autowired
    public ApplicantGazetteService(ApplicantGazetteDao applicantGazetteDao){
        this.applicantGazetteDao = applicantGazetteDao;
    }



    public List< ApplicantGazette > findAll() {
        return applicantGazetteDao.findAll();
    }


    public ApplicantGazette findById(Integer id) {
        return applicantGazetteDao.getOne(id);
    }


    public ApplicantGazette persist(ApplicantGazette applicantGazette) {
        return applicantGazetteDao.save(applicantGazette);
    }


    public boolean delete(Integer id) {
        applicantGazetteDao.deleteById(id);
        return false;
    }


    public List< ApplicantGazette > search(ApplicantGazette applicantGazette) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< ApplicantGazette > sampleCollectingTubeExample = Example.of(applicantGazette, matcher);
        return applicantGazetteDao.findAll(sampleCollectingTubeExample);
    }
}