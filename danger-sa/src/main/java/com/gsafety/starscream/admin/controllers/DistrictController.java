package com.gsafety.starscream.admin.controllers;

import java.math.BigDecimal;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsafety.common.utils.AntiSpamUtils;
import com.gsafety.starscream.basedata.model.District;
import com.gsafety.starscream.basedata.service.DistrictService;
import com.gsafety.starscream.utils.R;
/**
 * @ClassName:DistrictController
 * @Description:行政区划管理Controller
 * @Author: duzhigang
 * @Version:1.0
 * @Date:2015/01/14
 */
@Path("admin/basedata")
public class DistrictController {

	@Autowired
	Invocation inv;

	@Autowired
	private DistrictService districtService;

	@Get("district/list")
	public String list() {
		try {
			// 查询所有行政区划，返回树形结构
			List<District> districts = districtService.findTree(null);
			inv.addModel("districtList", districts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "basedata/district-list";
	}

	@Get("district/add/{distCode:[0-9]+}")
	public String district_add(@Param("distCode") String distCode) {
		// 查询所有行政区划，返回树形结构
		List<District> districts = districtService.findTree(null);
		inv.addModel("districtList", districts);
		if (StringUtils.isNotEmpty(distCode)) {
			inv.addModel("districtCode", distCode);
		}
		return "basedata/district-add";
	}

	@Post("district/save")
	public String district_save(
			@Param("parentDistrictCode") String parentDistrictCode,
			@Param("distCode") String distCode,
			@Param("distName") String distName,
			@Param("distShortName") String distShortName,
			@Param("distLongitude") float distLongitude,
			@Param("distLatitude") float distLatitude,
			@Param("distNotes") String distNotes) {
		
		parentDistrictCode = AntiSpamUtils.safeText(parentDistrictCode);
		distCode = AntiSpamUtils.safeText(distCode);
		distName = AntiSpamUtils.safeText(distName);
		distShortName = AntiSpamUtils.safeText(distShortName);
		distNotes = AntiSpamUtils.safeText(distNotes);
		
		District district = new District();
		district.setDistCode(distCode);
		district.setParentCode(parentDistrictCode);
		district.setDistName(distName);
		district.setDistShortName(distShortName);
		district.setLongitude(BigDecimal.valueOf(distLongitude));
		district.setLatitude(BigDecimal.valueOf(distLatitude));
		district.setDistNotes(distNotes);
		// 新增行政区划数据
		districtService.save(district);
		
		return R.redirect("/admin/basedata/district/list", inv);
	}

	@Get("district/edit/{distCode:[0-9]+}")
	public String district_edit(@Param("distCode") String distCode) {
		try {
			// 查询所有行政区划，返回树形结构
			List<District> districts = districtService.findTree(null);
			inv.addModel("districtList", districts);
			District district = districtService.findByDistCode(distCode);
			inv.addModel("district", district);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "basedata/district-edit";
	}

	@Post("district/update/{distCode:[0-9]+}")
	public String district_update(
			@Param("parentDistrictCode") String parentDistCode,
			@Param("distCode") String distCode,
			@Param("distName") String distName,
			@Param("distShortName") String distShortName,
			@Param("distLongitude") float distLongitude,
			@Param("distLatitude") float distLatitude,
			@Param("distNotes") String distNotes){
		
		parentDistCode = AntiSpamUtils.safeText(parentDistCode);
		distCode = AntiSpamUtils.safeText(distCode);
		distName = AntiSpamUtils.safeText(distName);
		distShortName = AntiSpamUtils.safeText(distShortName);
		distNotes = AntiSpamUtils.safeText(distNotes);
		
		District district = districtService.findByDistCode(distCode);
		district.setDistCode(distCode);
		district.setDistName(distName);
		district.setParentCode(parentDistCode);
		district.setDistShortName(distShortName);
		district.setLongitude(BigDecimal.valueOf(distLongitude));
		district.setLatitude(BigDecimal.valueOf(distLatitude));
		district.setDistNotes(distNotes);
		// 更新行政区划对象
		districtService.update(district);
		return R.redirect("/admin/basedata/district/list", inv);
	}

	@Get("district/delete/{distCode:[0-9]+}")
	public String district_delete(@Param("distCode") String distCode) {
		distCode = AntiSpamUtils.safeText(distCode);
		// 删除行政区划数据
		districtService.delete(distCode);
		return R.redirect("/admin/basedata/district/list", inv);
	}

	@Get("district/moveUp/{distCode:[0-9]+}")
	public String district_moveUp(@Param("distCode") String distCode) {
		return R.redirect("/admin/basedata/district/list", inv);
	}

	@Get("district/moveDown/{distCode:[0-9]+}")
	public String district_moveDown(@Param("distCode") String distCode) {
		return R.redirect("/admin/basedata/district/list", inv);
	}

}
