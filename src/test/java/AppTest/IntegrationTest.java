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

import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
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
    public void integration_testAddStudent() {
        long studentsCount = StreamSupport.stream(service.findAllStudents().spliterator(), false).count();
        service.saveStudent("100", "Nume", 222);

        assertEquals(studentsCount + 1, StreamSupport.stream(service.findAllStudents().spliterator(), false).count());
        service.deleteStudent("100");
    }

    @Test
    public void integration_testAddTema() {
        long temaCount = StreamSupport.stream(service.findAllTeme().spliterator(), false).count();
        service.saveTema("100", "descriere", 7, 5);

        assertEquals(temaCount + 1, StreamSupport.stream(service.findAllTeme().spliterator(), false).count());
        service.deleteTema("100");
    }

    @Test
    public void integration_testAddNota() throws IOException {
        long noteCount = StreamSupport.stream(service.findAllNote().spliterator(), false).count();
        service.saveNota("1", "1", 9, 7, "bravo");
        assertEquals(noteCount + 1, StreamSupport.stream(service.findAllNote().spliterator(), false).count());
        FileWriter myWriter = new FileWriter("note.test.xml");
        myWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<Entitati />");
        myWriter.close();

    }


    @Test
    public void integration_testAddAll() throws IOException {
        long studentsCount = StreamSupport.stream(service.findAllStudents().spliterator(), false).count();
        long temaCount = StreamSupport.stream(service.findAllTeme().spliterator(), false).count();
        long noteCount = StreamSupport.stream(service.findAllNote().spliterator(), false).count();
        service.saveStudent("100", "Nume", 931);
        service.saveTema("100", "Descriere", 9, 3);

        service.saveNota("100", "100", 9, 9, "okok");

        assertEquals(studentsCount + 1, StreamSupport.stream(service.findAllStudents().spliterator(), false).count());
        assertEquals(temaCount + 1, StreamSupport.stream(service.findAllTeme().spliterator(), false).count());
        assertEquals(noteCount + 1, StreamSupport.stream(service.findAllNote().spliterator(), false).count());

        FileWriter myWriter = new FileWriter("note.test.xml");
        service.deleteStudent("100");
        service.deleteTema("100");
        myWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<Entitati />");
        myWriter.close();
    }
}
