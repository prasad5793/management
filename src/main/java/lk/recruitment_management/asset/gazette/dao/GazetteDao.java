package lk.recruitment_management.asset.gazette.dao;


import lk.recruitment_management.asset.gazette.entity.Gazette;
import lk.recruitment_management.asset.gazette.entity.enums.GazetteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GazetteDao extends JpaRepository< Gazette, Integer> {
  List< Gazette> findByGazetteStatus(GazetteStatus gazetteStatus);

  List< Gazette> findByCreatedAtIsBetween(LocalDateTime startAt, LocalDateTime endAt);
}
