package com.admin.service.system.role;

import com.admin.dao.DaoSupport;
import com.admin.entity.system.Role;
import com.admin.util.PageData;
import com.admin.util.RightsHelper;
import com.admin.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("roleService")
public class RoleService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public List<Role> listAllERRoles(PageData pd) throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllERRoles", pd);

	}

	public List<Role> listAllappERRoles(PageData pd) throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllappERRoles", pd);

	}

	public List<Role> listAllRoles(PageData pd) throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllRoles", pd);

	}

	// 通过当前登录用的角色id获取管理权限数据
	public PageData findGLbyrid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.findGLbyrid", pd);
	}

	// 通过当前登录用的角色id获取用户权限数据
	public PageData findYHbyrid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.findYHbyrid", pd);
	}

	// 列出此角色下的所有用户
	public List<PageData> listAllUByRid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoleMapper.listAllUByRid", pd);

	}

	// 列出此角色下的所有会员
//	public List<PageData> listAllAppUByRid(PageData pd) throws Exception {
//		return (List<PageData>) dao.findForList("RoleMapper.listAllAppUByRid", pd);
//
//	}

	/**
	 * 列出此部门的所有下级
	 */
	public List<Role> listAllRolesByPId(PageData pd) throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllRolesByPId", pd);

	}

	// 列出K权限表里的数据
	public List<PageData> listAllkefu(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoleMapper.listAllkefu", pd);
	}

	// 列出G权限表里的数据
	public List<PageData> listAllGysQX(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoleMapper.listAllGysQX", pd);
	}

	// 删除K权限表里对应的数据
	public void deleteKeFuById(String ROLE_ID) throws Exception {
		dao.delete("RoleMapper.deleteKeFuById", ROLE_ID);
	}

	// 删除G权限表里对应的数据
	public void deleteGById(String ROLE_ID) throws Exception {
		dao.delete("RoleMapper.deleteGById", ROLE_ID);
	}

	public void deleteRoleById(String ROLE_ID) throws Exception {
		dao.delete("RoleMapper.deleteRoleById", ROLE_ID);

	}

	public Role getRoleById(String roleId) throws Exception {
		return (Role) dao.findForObject("RoleMapper.getRoleById", roleId);

	}

	public void updateRoleRights(Role role) throws Exception {
		dao.update("RoleMapper.updateRoleRights", role);
	}

	public void updateRoleBaseQx(Role role) throws Exception {
		dao.update("RoleMapper.updateRoleBaseQx", role);
	}

	/**
	 * 权限(增删改查)
	 */
	public void updateQx(String msg, PageData pd) throws Exception {
		dao.update("RoleMapper." + msg, pd);
	}

	/**
	 * 客服权限
	 */
	public void updateKFQx(String msg, PageData pd) throws Exception {
		dao.update("RoleMapper." + msg, pd);
	}

	/**
	 * Gc权限
	 */
	public void gysqxc(String msg, PageData pd) throws Exception {
		dao.update("RoleMapper." + msg, pd);
	}

	/**
	 * 给全部子职位加菜单权限
	 */
	public void setAllRights(PageData pd) throws Exception {
		dao.update("RoleMapper.setAllRights", pd);

	}

	/**
	 * 添加
	 */
	public void add(PageData pd) throws Exception {
		dao.findForList("RoleMapper.insert", pd);
	}

	/**
	 * 保存客服权限
	 */
	public void saveKeFu(PageData pd) throws Exception {
		dao.findForList("RoleMapper.saveKeFu", pd);
	}

	/**
	 * 保存G权限
	 */
	public void saveGYSQX(PageData pd) throws Exception {
		dao.findForList("RoleMapper.saveGYSQX", pd);
	}

	/**
	 * 通过id查找
	 */
	public PageData findObjectById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.findObjectById", pd);
	}
	/**
	 * 通过name查找
	 */
	public PageData findByName(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.findByName", pd);
	}

	/**
	 * 编辑角色
	 */
	public PageData edit(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.edit", pd);
	}

	public List<Role> listSubRoleByParentId(String parentId) throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listSubRoleByParentId", parentId);

	}

	public List<Role> listAllRolesNew(String menuId, PageData pd) throws Exception {
		List<Role> rl = this.listAllRoles(pd);
		for (Role role : rl) {
			role.setOpen(true);
			List<Role> subList = this.listSubRoleByParentId(role.getROLE_ID());
			for (Role subRole : subList) {
				if (Tools.notEmpty(subRole.getRIGHTS())) {
					subRole.setHasRole(RightsHelper.testRights(subRole.getRIGHTS(), menuId));
					List<Role> sunRole = new ArrayList<>();
					Role addRole = new Role();
					addRole.setROLE_NAME("增加权限");
					addRole.setHasRole(RightsHelper.testRights(subRole.getADD_QX(), menuId));
					sunRole.add(addRole);
					Role delRole = new Role();
					delRole.setROLE_NAME("删除权限");
					delRole.setHasRole(RightsHelper.testRights(subRole.getDEL_QX(), menuId));
					sunRole.add(delRole);
					Role editRole = new Role();
					editRole.setROLE_NAME("修改权限");
					editRole.setHasRole(RightsHelper.testRights(subRole.getEDIT_QX(), menuId));
					sunRole.add(editRole);
					Role chaRole = new Role();
					chaRole.setROLE_NAME("查询权限");
					chaRole.setHasRole(RightsHelper.testRights(subRole.getCHA_QX(), menuId));
					sunRole.add(chaRole);
					subRole.setSubRole(sunRole);
				}
			}
			role.setSubRole(subList);
		}
		return rl;
	}
}
