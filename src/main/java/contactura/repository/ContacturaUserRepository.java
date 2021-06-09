package contactura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import contactura.model.ContacturaUser;

@Repository
public interface ContacturaUserRepository extends JpaRepository<ContacturaUser, Long>{
	ContacturaUser findByUsername (String username);

}
