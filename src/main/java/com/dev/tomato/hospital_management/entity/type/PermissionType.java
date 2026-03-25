package com.dev.tomato.hospital_management.entity.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum PermissionType {

    PATIENT_READ("patient:read"),
    PATIENT_WRITE("patient:write"),
    APPPOINTMENT_READ("appointment:read"),
    APPOINTMENT_WRITE("appointment:write"),
    APPOINTMENT_DELETE("appointment:delete"),
    USER_MANAGE("user:manage"), // for admin to manage users
    REPORT_VIEW("report:view"); // for doctors to view reports

    private final String permission;

}
