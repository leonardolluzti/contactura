package contactura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import contactura.model.Contactura;

@Repository
public interface ContacturaRepository extends JpaRepository<Contactura, Long>{

}
