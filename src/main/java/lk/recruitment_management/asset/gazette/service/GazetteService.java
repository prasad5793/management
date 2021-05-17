package lk.recruitment_management.asset.gazette.service;


import lk.recruitment_management.asset.gazette.dao.GazetteDao;
import lk.recruitment_management.asset.gazette.entity.Gazette;
import lk.recruitment_management.asset.gazette.entity.enums.GazetteStatus;
import lk.recruitment_management.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GazetteService implements AbstractService< Gazette, Integer> {

    private final GazetteDao gazetteDao;
    @Autowired
    public GazetteService(GazetteDao gazetteDao){
        this.gazetteDao = gazetteDao;
    }



    public List< Gazette > findAll() {
        return gazetteDao.findAll();
    }


    public Gazette findById(Integer id) {
        return gazetteDao.getOne(id);
    }


    public Gazette persist(Gazette gazette) {
        return gazetteDao.save(gazette);
    }


    public boolean delete(Integer id) {
        gazetteDao.deleteById(id);
        return false;
    }


    public List< Gazette > search(Gazette gazette) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Gazette > sampleCollectingTubeExample = Example.of(gazette, matcher);
        return gazetteDao.findAll(sampleCollectingTubeExample);
    }

  public List< Gazette > findByGazetteStatus(GazetteStatus gazetteStatus) {
  return gazetteDao.findByGazetteStatus(gazetteStatus);
    }

  public List< Gazette> findByCreatedAtIsBetween(LocalDateTime startAt, LocalDateTime endAt) {
        return gazetteDao.findByCreatedAtIsBetween(startAt,endAt);
  }
}
