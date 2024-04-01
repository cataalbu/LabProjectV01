package AppTest;

import org.example.domain.Nota;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepository;
import org.example.repository.StudentXMLRepository;
import org.example.repository.TemaXMLRepository;
import org.example.service.Service;
import org.example.validation.*;

import org.junit.Before;
import org.junit.Test;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class AppTest {

    public Service service;

    /** Assignment 2**/
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
    public void testAddToList() {
        long studentsCount = StreamSupport.stream(service.findAllStudents().spliterator(), false).count();
        service.saveStudent("100", "Nume", 222);

        assertEquals(studentsCount + 1, StreamSupport.stream(service.findAllStudents().spliterator(), false).count());
        service.deleteStudent("100");
    }

    @Test
    public void testAddDuplicateToList() {
        long studentsCount = StreamSupport.stream(service.findAllStudents().spliterator(), false).count();
        service.saveStudent("100", "Nume", 222);
        service.saveStudent("100", "Nume", 222);
        assertEquals(studentsCount + 1, StreamSupport.stream(service.findAllStudents().spliterator(), false).count());
        service.deleteStudent("100");
    }

    @Test
    public void testAddEmptyIdToList() {
        long studentsCount = StreamSupport.stream(service.findAllStudents().spliterator(), false).count();
        service.saveStudent("", "Nume", 222);
        assertEquals(studentsCount, StreamSupport.stream(service.findAllStudents().spliterator(), false).count());
    }

    @Test
    public void testAddNullIdToList() {
        long studentsCount = StreamSupport.stream(service.findAllStudents().spliterator(), false).count();
        service.saveStudent(null, "Nume", 222);
        assertEquals(studentsCount, StreamSupport.stream(service.findAllStudents().spliterator(), false).count());
    }

    @Test
    public void testAddEmptyStudentName() {
        long studentsCount = StreamSupport.stream(service.findAllStudents().spliterator(), false).count();
        service.saveStudent("100", "", 222);
        assertEquals(studentsCount, StreamSupport.stream(service.findAllStudents().spliterator(), false).count());
    }

    @Test
    public void testAddNullStudentName() {
        long studentsCount = StreamSupport.stream(service.findAllStudents().spliterator(), false).count();
        service.saveStudent("100", null, 222);
        assertEquals(studentsCount, StreamSupport.stream(service.findAllStudents().spliterator(), false).count());
    }

    @Test
    public void testAddNegativeStudentGroup() {
        long studentsCount = StreamSupport.stream(service.findAllStudents().spliterator(), false).count();
        service.saveStudent("100", "Nume", -222);
        assertEquals(studentsCount, StreamSupport.stream(service.findAllStudents().spliterator(), false).count());
    }

    @Test
    public void testAddBigStudentGroup() {
        long studentsCount = StreamSupport.stream(service.findAllStudents().spliterator(), false).count();
        service.saveStudent("100", "Nume", 10000);
        assertEquals(studentsCount, StreamSupport.stream(service.findAllStudents().spliterator(), false).count());
    }

    /** Assignment 3**/
    @Test
    public void testAddTemaToList() {
        long temaCount = StreamSupport.stream(service.findAllTeme().spliterator(), false).count();
        service.saveTema("100", "descriere", 7, 5);

        assertEquals(temaCount + 1, StreamSupport.stream(service.findAllTeme().spliterator(), false).count());
        service.deleteTema("100");
    }

    @Test
    public void testAddEmptyTemaIdToList() {
        long temaCount = StreamSupport.stream(service.findAllTeme().spliterator(), false).count();
        service.saveTema("", "descriere", 7, 5);
        assertEquals(temaCount, StreamSupport.stream(service.findAllTeme().spliterator(), false).count());
    }
}