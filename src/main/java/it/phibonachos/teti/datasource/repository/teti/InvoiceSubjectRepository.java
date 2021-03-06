package it.phibonachos.teti.datasource.repository.teti;

import it.phibonachos.teti.datasource.model.teti.InvoiceSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InvoiceSubjectRepository extends JpaRepository<InvoiceSubject, Long>, JpaSpecificationExecutor<InvoiceSubject> {
}
