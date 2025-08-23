package com.meowcdd.entity.supabase;

public enum RequiredQualifications {
    NO_QUALIFICATION_REQUIRED("Không yêu cầu trình độ chuyên môn"),
    PSYCHOLOGIST_REQUIRED("Yêu cầu chuyên gia tâm lý"),
    PEDIATRICIAN_REQUIRED("Yêu cầu bác sĩ nhi khoa"),
    DEVELOPMENTAL_SPECIALIST_REQUIRED("Yêu cầu chuyên gia phát triển trẻ em"),
    THERAPIST_REQUIRED("Yêu cầu nhà trị liệu"),
    NURSE_REQUIRED("Yêu cầu y tá"),
    TEACHER_REQUIRED("Yêu cầu giáo viên");

    private final String displayName;

    RequiredQualifications(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
