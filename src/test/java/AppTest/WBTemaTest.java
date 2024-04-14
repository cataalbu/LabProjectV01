package AppTest;

import org.example.domain.Nota;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepository;
import org.example.repository.StudentXMLRepository;
import org.example.repository.TemaXMLRepository;
import org.example.service.Service;
import org.example.validation.NotaValidator;
import org.example.validation.StudentValidator;
import org.example.validation.TemaValidator;
import org.example.validation.Validator;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class WBTemaTest {

    public Service service;

    @Before
    public void beforeSetup() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();
        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.test.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.test.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.test.xml");

        this.service = new Service(fileRepository1, fileRepository2, fileRepository3);

    }

    @Test
    public void testAddTemaToList() {
        long temaCount = StreamSupport.stream(service.findAllTeme().spliterator(), false).count();
        service.saveTema("100", "descriere", 7, 5);

        assertEquals(temaCount + 1, StreamSupport.stream(service.findAllTeme().spliterator(), false).count());
        System.out.println(service.findAllTeme());
        service.deleteTema("100");
    }

    @Test
    public void testAddEmptyTemaIdToList() {
        long temaCount = StreamSupport.stream(service.findAllTeme().spliterator(), false).count();
        service.saveTema("", "descriere", 7, 5);
        assertEquals(temaCount, StreamSupport.stream(service.findAllTeme().spliterator(), false).count());
    }

    @Test
    public void testAddEmptyTemaDescriptionToList() {
        long temaCount = StreamSupport.stream(service.findAllTeme().spliterator(), false).count();
        service.saveTema("100", "", 7, 5);
        assertEquals(temaCount, StreamSupport.stream(service.findAllTeme().spliterator(), false).count());
    }

    @Test
    public void testAddIncorrectDeadlineToList() {
        long temaCount = StreamSupport.stream(service.findAllTeme().spliterator(), false).count();
        service.saveTema("100", "descriere", 2, 5);
        assertEquals(temaCount, StreamSupport.stream(service.findAllTeme().spliterator(), false).count());
    }

    @Test
    public void testAddDuplicateTemaToList() {
        service.saveTema("100", "descriere", 7, 5);
        long temaCount = StreamSupport.stream(service.findAllTeme().spliterator(), false).count();
        service.saveTema("100", "descriere", 7, 5);
        assertEquals(temaCount, StreamSupport.stream(service.findAllTeme().spliterator(), false).count());
        service.deleteTema("100");
    }
}
