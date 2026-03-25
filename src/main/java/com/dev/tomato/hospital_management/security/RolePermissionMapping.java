package com.dev.tomato.hospital_management.security;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dev.tomato.hospital_management.entity.type.PermissionType;
import com.dev.tomato.hospital_management.entity.type.RoleType;

import static com.dev.tomato.hospital_management.entity.type.PermissionType.*;
import static com.dev.tomato.hospital_management.entity.type.RoleType.*;

public class RolePermissionMapping {

    public static final Map<RoleType, Set<PermissionType>> map= Map.of(
        PATIENT, Set.of(PATIENT_READ, PATIENT_WRITE, APPPOINTMENT_READ),
        DOCTOR, Set.of(PATIENT_READ, APPPOINTMENT_READ, APPOINTMENT_WRITE,APPOINTMENT_DELETE, REPORT_VIEW),
        ADMIN, Set.of(PATIENT_READ, PATIENT_WRITE, APPPOINTMENT_READ, APPOINTMENT_WRITE, APPOINTMENT_DELETE, USER_MANAGE, REPORT_VIEW)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(RoleType roleType){
        return map.get(roleType).stream()
            .map(permission-> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toSet());
    }
}
