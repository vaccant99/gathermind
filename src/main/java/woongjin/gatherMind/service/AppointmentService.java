package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.repository.AppointmentRepository;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    public final AppointmentRepository appointmentRepository;
}
