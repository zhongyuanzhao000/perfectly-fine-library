package fdse21.group25.perfectlyfinelibrary.adminservice.service;

import java.util.List;

import fdse21.group25.perfectlyfinelibrary.adminservice.entity.Config;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginAdminDto;
import fdse21.group25.perfectlyfinelibrary.common.exception.UnauthorizedException;

public interface AdminService {
    void updateConfigs(List<Config> configs);

    List<Config> findConfigs();

    void notify(LoginAdminDto checkToken) throws UnauthorizedException;
}