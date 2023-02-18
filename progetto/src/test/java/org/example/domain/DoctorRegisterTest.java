package org.example.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorRegisterTest {
    static DoctorRegister doctorRegister;

    @BeforeAll
    public static void initTest(){
        doctorRegister = DoctorRegister.getInstance();
    }

    @Test
    void testAddDoctor() {
        Doctor d3 = new Doctor("Giovanni", "Fr", LocalDate.now(), "cf4", "gio.fr@gmail.com", "3298888555");
        doctorRegister.addDoctor(d3);
        assertEquals(1,doctorRegister.getSize());
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
            Doctor d3 = new Doctor("Giovanni", "Fr", LocalDate.now(), "cf4", "gio.fr@gmail.com", "3298888555");
            doctorRegister.addDoctor(d3);
            assertNotNull(doctorRegister.getDoctor("Fr"));
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testGetDoctorByCf() {
        try {
            Doctor d3 = new Doctor("Giovanni", "Fr", LocalDate.now(), "cf4", "gio.fr@gmail.com", "3298888555");
            doctorRegister.addDoctor(d3);
            assertNotNull(doctorRegister.getDoctorByCf("cf4"));
            assertEquals(d3, doctorRegister.getDoctorByCf("cf4"));
        } catch(Exception e) {
            fail("Unexpected exception");
        }
    }
}
