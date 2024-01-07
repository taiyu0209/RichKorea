package gbsw.cb.RichKorea.repository;

import gbsw.cb.RichKorea.entity.Pop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PopRepository extends JpaRepository<Pop, String> {

    List<Pop> findAllByOrderByHitDesc();
}
