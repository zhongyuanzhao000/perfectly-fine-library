package fdse21.group25.perfectlyfinelibrary.copyservice.service;

import java.util.List;

import fdse21.group25.perfectlyfinelibrary.common.dto.LoginAdminDto;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.copyservice.dto.AddedCopyDto;
import fdse21.group25.perfectlyfinelibrary.copyservice.entity.Copy;

public interface CopyService {
    List<Copy> findCopies(Copy example);

    List<Copy> addNewCopy(LoginAdminDto admin, AddedCopyDto copy) throws NotFoundException;

    Copy findCopyByCopyId(Long copyId) throws NotFoundException;
}