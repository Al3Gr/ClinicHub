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
            ArrayList<Date> dates = (ArrayList<Date>) clinicHub.newHospitalizationBooking("DAILY", Operation.VASECTOMY);
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
            clinicHub.newHospitalizationBooking("DAILY", Operation.VASECTOMY);
            clinicHub.chooseHospitalizationDate(Calendar.getInstance());
            assertEquals(Calendar.getInstance(), clinicHub.getCurrentHosp().getStart_date());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testShowPrice() {
        try{
            clinicHub.loginPatient("cf2");
            clinicHub.newHospitalizationBooking("DAILY", Operation.VASECTOMY);
            clinicHub.chooseHospitalizationDate(Calendar.getInstance());
            assertEquals(100, clinicHub.showPrice());
            clinicHub.newHospitalizationBooking("STANDARD", Operation.VASECTOMY);
            clinicHub.chooseHospitalizationDate(Calendar.getInstance());
            assertEquals(400, clinicHub.showPrice());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testConfirmHospitalization() {
        try{
            clinicHub.loginPatient("cf2");
            clinicHub.newHospitalizationBooking("DAILY", Operation.VASECTOMY);
            clinicHub.confirmHospitalizationBooking();
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
            assertEquals(dates.size(),31);
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
            clinicHub.confirmExamBooking();
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
            ExamBookingRegister.getInstance().add(e);
            clinicHub.checkBooking(1, "ESAME");
            assertNotNull(clinicHub.getCurrentExam());
            Hospitalization hosp = HospitalizationFactory.getNewHospitalization("STANDARD", Operation.VASECTOMY);
            hosp.setCode(1);
            hosp.setPatient(clinicHub.getCurrentPatient());
            HospitalizationBookingRegister.getInstance().add(hosp);
            clinicHub.checkBooking(1, "RICOVERO");
            assertNotNull(clinicHub.getCurrentHosp());
        } catch (Exception e) {
            fail("currentExam or currentHospitalization is null");
        }
    }

    @Test
    void testCalculateRefund() {
        //TODO
        try {
            // TEST REFUND ESAME
            Calendar c1 = Calendar.getInstance();
            c1.add(Calendar.DAY_OF_MONTH, 7);
            clinicHub.loginPatient("cf2");
            clinicHub.newExamBooking(ExamType.BLOOD_ANALYSIS);
            clinicHub.chooseExamDate(c1);
            clinicHub.confirmExamBooking();
            clinicHub.checkBooking(clinicHub.getCurrentExam().getCode(), "ESAME");
            assertEquals(clinicHub.calculateRefund("ESAME"), clinicHub.getCurrentExam().getPrice());
            Calendar c2 = Calendar.getInstance();
            c2.add(Calendar.DAY_OF_MONTH, 3);
            clinicHub.newExamBooking(ExamType.BLOOD_ANALYSIS);
            clinicHub.chooseExamDate(c2);
            clinicHub.confirmExamBooking();
            clinicHub.checkBooking(clinicHub.getCurrentExam().getCode(), "ESAME");
            assertEquals(clinicHub.calculateRefund("ESAME"), 0.5 * clinicHub.getCurrentExam().getPrice());
            clinicHub.newExamBooking(ExamType.BLOOD_ANALYSIS);
            clinicHub.chooseExamDate(Calendar.getInstance());
            clinicHub.confirmExamBooking();
            clinicHub.checkBooking(clinicHub.getCurrentExam().getCode(), "ESAME");
            assertEquals(clinicHub.calculateRefund("ESAME"), 0);

            // TEST REFUND RICOVERO
            Calendar c3 = Calendar.getInstance();
            c3.add(Calendar.DAY_OF_MONTH, 8);
            clinicHub.newHospitalizationBooking("DAILY", Operation.VASECTOMY);
            clinicHub.chooseHospitalizationDate(c3);
            clinicHub.confirmHospitalizationBooking();
            clinicHub.checkBooking(clinicHub.getCurrentHosp().getCode(), "RICOVERO");
            assertEquals(clinicHub.calculateRefund("RICOVERO"), 0.5 * clinicHub.getCurrentHosp().getPrice());
            Calendar c4 = Calendar.getInstance();
            c4.add(Calendar.DAY_OF_MONTH, 4);
            clinicHub.newHospitalizationBooking("STANDARD", Operation.VASECTOMY);
            clinicHub.chooseHospitalizationDate(c4);
            clinicHub.confirmHospitalizationBooking();
            clinicHub.checkBooking(clinicHub.getCurrentHosp().getCode(), "RICOVERO");
            assertEquals(clinicHub.calculateRefund("RICOVERO"), 0.2 * clinicHub.getCurrentHosp().getPrice());
            clinicHub.newHospitalizationBooking("STANDARD", Operation.VASECTOMY);
            clinicHub.chooseHospitalizationDate(Calendar.getInstance());
            clinicHub.confirmHospitalizationBooking();
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
            clinicHub.confirmExamBooking();

            clinicHub.loginPatient("cf2");
            clinicHub.checkBooking(clinicHub.getCurrentExam().getCode(),"ESAME");
            clinicHub.calculateRefund("ESAME");
            clinicHub.confirmCancel("ESAME");
            assertEquals(0,ExamBookingRegister.getInstance().getSize());

            clinicHub.loginPatient("cf2");
            clinicHub.newHospitalizationBooking("DAILY", Operation.VASECTOMY);
            clinicHub.chooseHospitalizationDate(Calendar.getInstance());
            clinicHub.confirmHospitalizationBooking();

            clinicHub.loginPatient("cf2");
            clinicHub.checkBooking(clinicHub.getCurrentHosp().getCode(),"RICOVERO");
            clinicHub.calculateRefund("RICOVERO");
            clinicHub.confirmCancel("RICOVERO");
            assertEquals(0,HospitalizationBookingRegister.getInstance().getSize());
        }catch(Exception e){
            fail(e);
        }
    }

    @Test
    void testLoginMed() {
        try{
            clinicHub.loginMed("cf1");
            assertNotNull(clinicHub.getCurrentDoctor());
        }catch(Exception e){
            fail("Unexpected exception");
        }
    }

    @Test
    void testSelectExamReady() {
        try{
            Utility.loadTodayExams();
            Exam currentExam = ExamBookingRegister.getInstance().getItem(2);
            clinicHub.selectExamReady(currentExam.getCode(), "esame pronto");
            assertTrue(currentExam.getState());
            assertNotNull(currentExam.getResult());
        }catch(Exception e){
            fail("Unexpected exception");
        }
    }

    @Test
    void testSendResultForEmail() {
        try{
            Utility.loadTodayExams();
            Exam currentExam = ExamBookingRegister.getInstance().getItem(1);
            clinicHub.selectExamReady(currentExam.getCode(), "esame pronto");
            assertTrue(clinicHub.sendResultForEmail());
        }catch(Exception e){
            fail(e);
        }
    }

    @Test
    void testAddDoctor() {
        boolean result = clinicHub.addDoctor("Carlo","Bianchi", LocalDate.now(), "cf55", "3333333333", "cb@gmail.com");
        assertTrue(result);
        assertNotNull(clinicHub.getCurrentDoctor());
    }

    @Test
    void testConfirmDoctor() {
        try {
            clinicHub.addDoctor("Carlo","Bianchi", LocalDate.now(), "cf45", "3333333333", "cb@gmail.com");
            clinicHub.confirmDoctor();
            assertEquals(3, DoctorRegister.getInstance().getSize());
        } catch(Exception e) {
            fail("Unexpected exception");
        }
    }
}