package org.example.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
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
            assertEquals(30,clinicHub.showExamPrice());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testConfirmBooking() {
        try{
            clinicHub.loginPatient("cf2");
            clinicHub.newExamBooking(ExamType.BLOOD_ANALYSIS);
            clinicHub.chooseExamDate(Calendar.getInstance());
            clinicHub.chooseExamTime(LocalTime.now());
            clinicHub.confirmBooking();
            assertEquals(1, ExamBookingRegister.getInstance().getSize());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
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
        try {
            // TEST REFUND ESAME
            String date_string = "01-03-2023";
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse(date_string);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            clinicHub.loginPatient("cf2");
            clinicHub.newExamBooking(ExamType.BLOOD_ANALYSIS);
            clinicHub.chooseExamDate(c);
            clinicHub.confirmBooking();
            clinicHub.checkBooking(clinicHub.getCurrentExam().getCode(), "ESAME");
            assertEquals(clinicHub.calculateRefund("ESAME"), clinicHub.getCurrentExam().getPrice());
            date_string = "12-02-2023";
            date = formatter.parse(date_string);
            c.setTime(date);
            clinicHub.newExamBooking(ExamType.BLOOD_ANALYSIS);
            clinicHub.chooseExamDate(c);
            clinicHub.confirmBooking();
            clinicHub.checkBooking(clinicHub.getCurrentExam().getCode(), "ESAME");
            assertEquals(clinicHub.calculateRefund("ESAME"), 0.5 * clinicHub.getCurrentExam().getPrice());
            date_string = "10-02-2023";
            date = formatter.parse(date_string);
            c.setTime(date);
            clinicHub.newExamBooking(ExamType.BLOOD_ANALYSIS);
            clinicHub.chooseExamDate(c);
            clinicHub.confirmBooking();
            clinicHub.checkBooking(clinicHub.getCurrentExam().getCode(), "ESAME");
            assertEquals(clinicHub.calculateRefund("ESAME"), 0);

            // TEST REFUND RICOVERO
            date_string = "11-03-2023";
            date = formatter.parse(date_string);
            c.setTime(date);
            clinicHub.newHospitalization("DAILY", Operation.VASECTOMY);
            clinicHub.chooseHospitalization(c);
            clinicHub.confirmHospitalization();
            clinicHub.checkBooking(clinicHub.getCurrentHosp().getCode(), "RICOVERO");
            assertEquals(clinicHub.calculateRefund("RICOVERO"), 0.5 * clinicHub.getCurrentHosp().getPrice());
            date_string = "13-02-2023";
            date = formatter.parse(date_string);
            c.setTime(date);
            clinicHub.newHospitalization("STANDARD", Operation.VASECTOMY);
            clinicHub.chooseHospitalization(c);
            clinicHub.confirmHospitalization();
            clinicHub.checkBooking(clinicHub.getCurrentHosp().getCode(), "RICOVERO");
            assertEquals(clinicHub.calculateRefund("RICOVERO"), 0.2 * clinicHub.getCurrentHosp().getPrice());
            date_string = "10-02-2023";
            date = formatter.parse(date_string);
            c.setTime(date);
            clinicHub.newHospitalization("STANDARD", Operation.VASECTOMY);
            clinicHub.chooseHospitalization(c);
            clinicHub.confirmHospitalization();
            clinicHub.checkBooking(clinicHub.getCurrentHosp().getCode(), "RICOVERO");
            assertEquals(clinicHub.calculateRefund("RICOVERO"), 0);

        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testConfirmCancel() {
        try{
            clinicHub.loginPatient("cf2");
            clinicHub.newExamBooking(ExamType.BLOOD_ANALYSIS);
            clinicHub.chooseExamDate(Calendar.getInstance());
            clinicHub.chooseExamTime(LocalTime.now());
            clinicHub.confirmBooking();

            clinicHub.loginPatient("cf2");
            clinicHub.checkBooking(clinicHub.getCurrentExam().getCode(),"ESAME");
            clinicHub.calculateRefund("ESAME");
            clinicHub.confirmCancel("ESAME");
            assertEquals(0,ExamBookingRegister.getInstance().getSize());

            clinicHub.loginPatient("cf2");
            clinicHub.newHospitalization("DAILY", Operation.VASECTOMY);
            clinicHub.chooseHospitalization(Calendar.getInstance());
            clinicHub.confirmHospitalization();

            clinicHub.loginPatient("cf2");
            clinicHub.checkBooking(clinicHub.getCurrentHosp().getCode(),"RICOVERO");
            clinicHub.calculateRefund("RICOVERO");
            clinicHub.confirmCancel("RICOVERO");
            assertEquals(0,HospitalizationBookingRegister.getInstance().getSize());
        }catch(Exception e){
            fail("Unexpected exception");
        }
    }
}