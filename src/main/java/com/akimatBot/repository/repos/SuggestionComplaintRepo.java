package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.SuggestionComplaint;
import com.akimatBot.entity.enums.AppealType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SuggestionComplaintRepo extends JpaRepository<SuggestionComplaint, Integer> {
    SuggestionComplaint findSuggestionComplaintById(int id);
    List<SuggestionComplaint> findAllByAnsweredFalseAndAppealType(AppealType appealType);
    List<SuggestionComplaint> findAllByAnsweredTrueAndAppealType(AppealType appealType);
    List<SuggestionComplaint> findAllByUploadedDateBetween(Date start, Date end);
}
