package org.example.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ClinicHubTest {

    static ClinicHub clinicHub;

    @BeforeAll
    public static void initTest(){
        clinicHub = ClinicHub.getInstance();
    }


    @Test
    void testAddPatient() {
        try {
            clinicHub.addPatient("Alessandro", "Gr", new Date(), "via mario rossi 10", "cf23(", "000111555", "prova@gmail.com");
            assertNotNull(clinicHub.getCurrentPatient());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testConfirmPatient() {
        try {
            clinicHub.addPatient("Alessandro", "Gr", new Date(), "via mario rossi 10", "cf22", "000111555", "prova@gmail.com");
            clinicHub.confirmPatient();
            assertEquals(3, clinicHub.getPatientRegister().size());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testLoginPatient() {
        try{
            clinicHub.loginPatient("cf2");
            assertNotNull(clinicHub.getCurrentPatient());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testNewHospitalization() {
        try{
            ArrayList<Date> dates = (ArrayList<Date>) clinicHub.newHospitalization("DAILY", Operation.VASECTOMY);
            assertNotNull(clinicHub.getCurrentHosp());
            assertNotNull(dates);
        } catch (Exception e) {
            fail("Unexpected exception");
        }


    }

    @Test
    void testChooseHospitalization() {
    }

    @Test
    void testCalculatePrice() {
    }

    @Test
    void testConfirmHospitalization() {
        try{
            clinicHub.newHospitalization("DAILY", Operation.VASECTOMY);
            clinicHub.loginPatient("cf2");
            clinicHub.confirmHospitalization();
            assertEquals(1, clinicHub.getHospRegister().size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}