package org.example.interfaces;

import org.example.domain.Exam;

public interface EmailService {
    boolean sendResult(Exam e, String result);
}
