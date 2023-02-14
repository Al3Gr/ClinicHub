package org.example.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorRegisterTest {
    static ClinicHub clinicHub;
    static DoctorRegister doctorRegister;

    @BeforeAll
    public static void initTest(){
        clinicHub = ClinicHub.getInstance();
        doctorRegister = DoctorRegister.getInstance();
    }

    @Test
    void testAddDoctor() {
        Doctor d3 = new Doctor("Giovanni", "Fr", new Date(), "cf4", "gio.fr@gmail.com", "3298888555");
        doctorRegister.addDoctor(d3);
        assertEquals(3,doctorRegister.getSize());
    }

    @Test
    void testGetDoctor() {
        try {
            assertNotNull(doctorRegister.getDoctor());
        } catch(Exception e) {
            fail("Unexpected exception");
        }
    }


    @Test
    void testGetDoctorByLastName() {
        try {
            assertNotNull(doctorRegister.getDoctor("fD"));
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testGetDoctorByCf() {
        try {
            assertNotNull(doctorRegister.getDoctorByCf("cf0"));
        } catch(Exception e) {
            fail("Unexpected exception");
        }
    }
}
