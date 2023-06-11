package ph.cdo.backend.service;

import ph.cdo.backend.dto.records.AdminDTOEntity;
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.service.impl.user.IUserService;

public interface AdminService extends IUserService<Admin, AdminDTOEntity> {
}
