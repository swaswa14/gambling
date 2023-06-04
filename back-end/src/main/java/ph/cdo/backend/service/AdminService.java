package ph.cdo.backend.service;

import ph.cdo.backend.dto.AdminDTO;
import ph.cdo.backend.dto.mapper.AdminDTOMapper;
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.entity.user.Agent;

public interface AdminService extends IUserService<Admin, AdminDTO>{
}
