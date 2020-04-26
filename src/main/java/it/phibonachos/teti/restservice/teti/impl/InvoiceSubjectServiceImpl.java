package it.phibonachos.teti.restservice.teti.impl;

import it.phibonachos.teti.datasource.model.teti.InvoiceSubject;
import it.phibonachos.teti.datasource.repository.specification.SpecsInterface;
import it.phibonachos.teti.datasource.repository.teti.InvoiceSubjectRepository;
import it.phibonachos.teti.restservice.teti.InvoiceSubjectService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceSubjectServiceImpl implements InvoiceSubjectService {

    private final InvoiceSubjectRepository invoiceSubRepository;

    public InvoiceSubjectServiceImpl(InvoiceSubjectRepository invoiceSubRepository) {
        this.invoiceSubRepository = invoiceSubRepository;
    }

    public List<InvoiceSubject> findAll(){
        return invoiceSubRepository.findAll();
    }


    public List<InvoiceSubject> findFiltered(InvoiceSubject filters) {
        return invoiceSubRepository.findAll(SpecsInterface.havingProperties(filters));
    }

    @Override
    public Page<InvoiceSubject> findFiltered(InvoiceSubject filter, int page, int size) {
        return invoiceSubRepository.findAll(SpecsInterface.havingProperties(filter), PageRequest.of(page, size));
    }

    public InvoiceSubject save(InvoiceSubject InvoiceSubject) {
        return invoiceSubRepository.save(InvoiceSubject);
    }

    @Override
    public InvoiceSubject find(Long id) throws Exception {
        return invoiceSubRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public boolean remove(InvoiceSubject InvoiceSubject) {
        try {
            invoiceSubRepository.delete(InvoiceSubject);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean remove(Long id) {
        try {
            invoiceSubRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public long count() {
        return invoiceSubRepository.count();
    }

}
