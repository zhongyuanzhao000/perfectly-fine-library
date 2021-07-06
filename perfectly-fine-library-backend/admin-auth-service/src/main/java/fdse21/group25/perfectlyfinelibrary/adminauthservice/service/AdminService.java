package fdse21.group25.perfectlyfinelibrary.adminauthservice.service;

import fdse21.group25.perfectlyfinelibrary.adminauthservice.dto.AdminRegisterDto;
import fdse21.group25.perfectlyfinelibrary.adminauthservice.entity.Admin;

public interface AdminService {
    Admin addNewAdmin(AdminRegisterDto dto);
}
