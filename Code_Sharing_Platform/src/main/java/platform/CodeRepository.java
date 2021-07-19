package platform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CodeRepository extends JpaRepository<Code, UUID> {

    List<Code> findTop10ByViewRestrictedFalseAndTimeRestrictedFalseOrderByDateDesc();
}
