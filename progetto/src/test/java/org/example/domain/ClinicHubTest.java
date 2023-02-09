package org.example.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
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
            clinicHub.addPatient("Alessandro", "Gr", LocalDate.now(), "via mario rossi 10", "cf23(", "000111555", "prova@gmail.com");
            assertNotNull(clinicHub.getCurrentPatient());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testConfirmPatient() {
        try {
            clinicHub.addPatient("Alessandro", "Gr", LocalDate.now(), "via mario rossi 10", "cf22", "000111555", "prova@gmail.com");
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
            clinicHub.loginPatient("cf2");
            ArrayList<Date> dates = (ArrayList<Date>) clinicHub.newHospitalization("DAILY", Operation.VASECTOMY);
            assertNotNull(clinicHub.getCurrentHosp());
            assertEquals(31,dates.size());
        } catch (Exception e) {
            fail("Unexpected exception");
        }

    }

    @Test
    void testChooseHospitalization() {
        try {
            clinicHub.loginPatient("cf2");
            clinicHub.newHospitalization("DAILY", Operation.VASECTOMY);
            clinicHub.chooseHospitalization(Calendar.getInstance());
            assertEquals(Calendar.getInstance(), clinicHub.getCurrentHosp().getStart_date());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testShowPrice() {
        try{
            clinicHub.loginPatient("cf2");
            clinicHub.newHospitalization("DAILY", Operation.VASECTOMY);
            clinicHub.chooseHospitalization(Calendar.getInstance());
            assertEquals(100, clinicHub.showPrice());
            clinicHub.newHospitalization("STANDARD", Operation.VASECTOMY);
            clinicHub.chooseHospitalization(Calendar.getInstance());
            assertEquals(400, clinicHub.showPrice());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testConfirmHospitalization() {
        try{
            clinicHub.loginPatient("cf2");
            clinicHub.newHospitalization("DAILY", Operation.VASECTOMY);
            clinicHub.confirmHospitalization();
            assertEquals(1, HospitalizationBookingRegister.getInstance().getSize());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testChooseDoctor() {
        try {
            clinicHub.loginPatient("cf2");
            clinicHub.newExamBooking(ExamType.BLOOD_ANALYSIS);
            clinicHub.chooseExamDate(Calendar.getInstance());
            clinicHub.chooseExamTime(LocalTime.now());
            clinicHub.chooseDoctor("Fd");
            assertNotNull(clinicHub.getCurrentExam().getDoctor());
            assertEquals(clinicHub.getCurrentExam().getDoctor().getLastname(), "Fd");
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testNewExamBooking() {
        try {
            clinicHub.loginPatient("cf2");
            ArrayList<Date> dates = (ArrayList<Date>)clinicHub.newExamBooking(ExamType.BLOOD_ANALYSIS);
            assertNotNull(clinicHub.getCurrentExam());
        } catch (Exception e) {
            fail("Unexpected exception");
        }

    }

    @Test
    void testShowExamPrice() {
        try {
            clinicHub.loginPatient("cf2");
            clinicHub.newExamBooking(ExamType.BLOOD_ANALYSIS);
            clinicHub.chooseExamDate(Calendar.getInstance());
            clinicHub.chooseExamTime(LocalTime.now());
            clinicHub.chooseDoctor("Fd");
            assertEquals(45,clinicHub.showExamPrice());

            clinicHub.loginPatient("cf2");
            clinicHub.newExamBooking(ExamType.BLOOD_ANALYSIS);
            clinicHub.chooseExamDate(Calendar.getInstance());
            clinicHub.chooseExamTime(LocalTime.now());
            clinicHub.chooseDoctor("");
            assertEquals(30,clinicHub.showExamPrice());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testConfirmBooking() {
    }

    @Test
    void testCheckBooking() {
        try {
            clinicHub.loginPatient("cf2");
            Exam e = new Exam(ExamType.BLOOD_ANALYSIS);
            e.setCode(1);
            e.setPatient(clinicHub.getCurrentPatient());
            ExamBookingRegister.getInstance().addBooking(e);
            clinicHub.checkBooking(1, "ESAME");
            assertNotNull(clinicHub.getCurrentExam());
            Hospitalization hosp = HospitalizationFactory.getNewHospitalization("STANDARD", Operation.VASECTOMY);
            hosp.setCode(1);
            hosp.setPatient(clinicHub.getCurrentPatient());
            HospitalizationBookingRegister.getInstance().addBooking(hosp);
            clinicHub.checkBooking(1, "RICOVERO");
            assertNotNull(clinicHub.getCurrentHosp());
        } catch (Exception e) {
            fail("currentExam or currentHospitalization is null");
        }
    }

    @Test
    void testCalculateRefund() {
    }

    @Test
    void testConfirmCancel() {
    }
}