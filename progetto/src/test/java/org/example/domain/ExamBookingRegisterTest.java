package org.example.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

}