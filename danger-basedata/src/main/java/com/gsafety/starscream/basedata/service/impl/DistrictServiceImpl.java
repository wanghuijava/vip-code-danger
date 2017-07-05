package com.gsafety.starscream.basedata.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsafety.common.utils.PinyinUtils;
import com.gsafety.starscream.basedata.model.District;
import com.gsafety.starscream.basedata.repository.DistrictRepository;
import com.gsafety.starscream.basedata.service.DistrictService;
import com.gsafety.starscream.constant.BasedataConstant;

/**
 * 行政区划Service实现
 * @author chenwenlong
 *
 */
@Service("districtService")
@Transactional
public class DistrictServiceImpl implements DistrictService{

	@Resource
	private DistrictRepository districtRepository;

	/**
	 * 保存行政区划
	 * @param district
	 * @return
	 */
	@Override
	public District save(District district) {
		district.setFullSpelling(PinyinUtils.getFullSpelling(district.getDistName()));
		district.setShortSpelling(PinyinUtils.getShortSpelling(district.getDistName()));
		district = districtRepository.save(district);
//		if(district!=null) {
//			SequenceUtils.setSequenceVal(district.getDistCode());
//		}
		return district;
	}

	/**
	 * 删除行政区划，根据行政区划编码
	 * @param distCode
	 */
	@Override
	public void delete(String distCode) {
		districtRepository.delete(distCode);
	}

	/**
	 * 修改行政区划
	 * @param district
	 * @return
	 */
	@Override
	public District update(District district) {
		district.setFullSpelling(PinyinUtils.getFullSpelling(district.getDistName()));
		district.setShortSpelling(PinyinUtils.getShortSpelling(district.getDistName()));
		return districtRepository.save(district);
	}

	/**
	 * 查询行政区划，根据行政区划编码【主键】
	 * @param distCode
	 * @return
	 */
	@Override
	public District findByDistCode(String distCode) {
		return districtRepository.findOne(distCode);
	}

	/**
	 * 查询行政区划，根据父级行政区划编码查询子级行政区划
	 * @param parentCode
	 * @return
	 */
	@Override
	public List<District> findByParentCode(String parentCode) {
		return districtRepository.findByParentCodeOrderBySortNumAsc(parentCode);
//		return districtRepository.findByParentCodeOrderByDistCodeAsc(parentCode);
	}

	/**
	 * 查询树形行政区划
	 * @param code
	 * @return
	 */
	@Transactional(readOnly=true)
	@Override 
	public List<District> findTree(String code) {
		List<District> districts = findByParentCode(code);
		if(districts==null||districts.size()==0) {
			return null;
		}
		for(District district :districts) {
			district.setChildren(findTree(district.getDistCode()));
		}
		return districts;
	}
	
	/**
	 * 根据根节点名称查询树形行政区划
	 * @param code
	 * @return
	 */
	public List<District> getDistrictCondition(){
		District dis = districtRepository.findByDistName(BasedataConstant.DISTRICT_ROOT_NAME);
		return findTree(dis.getDistCode());
	}
	
}
