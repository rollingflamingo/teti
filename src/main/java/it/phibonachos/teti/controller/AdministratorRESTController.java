package it.phibonachos.teti.controller;

import it.phibonachos.teti.datasource.model.teti.Administrator;
import it.phibonachos.teti.datasource.model.teti.InvoiceSubject;
import it.phibonachos.teti.restservice.teti.AdministratorService;
import it.phibonachos.teti.restservice.teti.InvoiceSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/adm-api")
public class AdministratorRESTController {

    private final AdministratorService administratorService;
    private final InvoiceSubjectService invoiceSubjectService;

    public AdministratorRESTController(AdministratorService administratorService, InvoiceSubjectService invoiceSubjectService) {
        this.administratorService = administratorService;
        this.invoiceSubjectService = invoiceSubjectService;
    }

    @RequestMapping(value = {"/", "/{page}", "/{page}/{size}"}, method = RequestMethod.POST)
    public ResponseEntity<Object> getAllAdministrator(@PathVariable(name = "page") Optional<Integer> page, @PathVariable(name = "size") Optional<Integer> size, @RequestBody(required = false) Administrator filters) {
        Page<Administrator> matching = administratorService.findFiltered(filters, page.map(i -> i-1).orElse(0), size.orElse(10));
        return new ResponseEntity<>(Map.of("recordsTotal", matching.getTotalElements(), "data", matching.getContent()), HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAdministrator(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(administratorService.find(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("no administrator matching id.", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Object> saveAdministrator(@RequestBody Administrator administrator) {
        if(administrator != null) {
            return new ResponseEntity<>(administratorService.save(administrator), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("cannot pass empty object", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.PUT)
    public ResponseEntity<Object> editAdministrator(@PathVariable("id") Long id, @RequestBody Administrator edited) {
        try {
            Administrator source = administratorService.find(id);
            edited.setId(id);
            BeanUtils.copyProperties(edited, source, "id", "relatedInvoiceSubjects");
            return new ResponseEntity<>(administratorService.save(source), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("no administrator matching id.", HttpStatus.NOT_FOUND);
        }
    }

    // TODO: use response entity.
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAdministrator(@PathVariable("id") Long id) {
        try {
            System.out.println("removing admin: ");
            administratorService.remove(administratorService.find(id));
            return new ResponseEntity<>(List.of(id), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(List.of(id), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}/add-is", method = RequestMethod.POST)
    public ResponseEntity<Object> associateInvoiceSubject(@PathVariable("id") Long id, @RequestBody InvoiceSubject toAssociate) {
        try {
            Administrator admin = administratorService.find(id);
            toAssociate.setAdministrator(admin);
            return new ResponseEntity<>(invoiceSubjectService.save(toAssociate), HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("no administrator matching id.", HttpStatus.NOT_FOUND);
        }
    }
}
