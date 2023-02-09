package org.example.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HospitalizationBookingRegisterTest {
    static ClinicHub clinicHub;
    static HospitalizationBookingRegister hospitalizationBookingRegister;

    @BeforeAll
    public static void initTest(){
        clinicHub = ClinicHub.getInstance();
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
            clinicHub.loginPatient("cf2");
            Hospitalization h = HospitalizationFactory.getNewHospitalization("DAILY", Operation.VASECTOMY);
            h.setPatient(clinicHub.getCurrentPatient());
            hospitalizationBookingRegister.addBooking(h);
            boolean risultato = hospitalizationBookingRegister.checkPatient(h.getCode(),clinicHub.getCurrentPatient());
            assertTrue(risultato);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }
}
