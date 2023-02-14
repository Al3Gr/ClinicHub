package org.example.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExamBookingRegisterTest {
    static ClinicHub clinicHub;
    static ExamBookingRegister examBookingRegister;

    @BeforeAll
    public static void initTest() {
        clinicHub = ClinicHub.getInstance();
        examBookingRegister = ExamBookingRegister.getInstance();
    }

    @Test
    void testAddBooking() {
        Exam e = new Exam(ExamType.BLOOD_ANALYSIS);
        examBookingRegister.addBooking(e);
        assertEquals(1, examBookingRegister.getSize());
    }

    @Test
    void testCheckPatient() {
        try {
            clinicHub.loginPatient("cf2");
            Exam e = new Exam(ExamType.BLOOD_ANALYSIS);
            e.setPatient(clinicHub.getCurrentPatient());
            examBookingRegister.addBooking(e);
            boolean risultato = examBookingRegister.checkPatient(e.getCode(),clinicHub.getCurrentPatient());
            assertTrue(risultato);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testGetTodayExamByDoc() {
        try {
            Doctor d = new Doctor("Giovanni", "Fr", new Date(), "cf1", "gio.fr@gmail.com", "3298888555");
            Utility.loadTodayExams();
            List<Exam> exams= examBookingRegister.getTodayExamByDoc(d);
            assertEquals(2,exams.size());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

}