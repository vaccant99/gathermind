package woongjin.gatherMind.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.repository.StudyMemberRepository;

import java.util.List;

@Service
public class StudyMemberService {


    @Autowired
    private StudyMemberRepository studyMemberRepository;



}