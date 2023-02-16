package org.example.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class HospitalizationBookingRegisterTest {
    static HospitalizationBookingRegister hospitalizationBookingRegister;

    @BeforeAll
    public static void initTest(){
        hospitalizationBookingRegister = HospitalizationBookingRegister.getInstance();
    }

    @Test
    void testAddBooking() {
        Hospitalization h = HospitalizationFactory.getNewHospitalization("DAILY", Operation.VASECTOMY);
        hospitalizationBookingRegister.addBooking(h);
        assertEquals(1,hospitalizationBookingRegister.getSize());
    }

    @Test
    void testCheckPatient() {
        try {
            Patient p = new Patient("testName", "testLastname", LocalDate.now(), "testRecidence", "testCf", "testTel","testEmail");
            Hospitalization h = HospitalizationFactory.getNewHospitalization("DAILY", Operation.VASECTOMY);
            h.setPatient(p);
            hospitalizationBookingRegister.addBooking(h);
            boolean risultato = hospitalizationBookingRegister.checkPatient(h.getCode(),p);
            assertTrue(risultato);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }
}
