package com.rb.fs.service.impl;

import com.rb.fs.dao.ModelDao;
import com.rb.fs.dao.ProDeModelDao;
import com.rb.fs.entity.Model;
import com.rb.fs.entity.ProDeModel;
import com.rb.fs.entity.ReturnBean;
import com.rb.fs.service.ProDeModelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ProDeModelServiceImpl implements ProDeModelService {
    @Autowired
    private ProDeModelDao proDeModelDao;
    @Autowired
    private ModelDao modelDao;
    /**
     * 根据条件查询数据
     * @param proId
     * @return
     */
    @Override
    public ProDeModel findByProNameAndDeviceNameAndModelNameAndFNameAndEffect(int proId, int did, int mid, int fid, int effect) {
        return proDeModelDao.findByProNameAndDeviceNameAndModelNameAndFNameAndEffect(proId,did,mid,fid,effect);
    }

    /**
     * 更新数据
     * @param proId
     * @param did
     * @param mid
     * @param fid
     */
    @Override
    public void updateEffect(int proId, int did, int mid, int fid) {
        proDeModelDao.updateEffect(proId,did,mid,fid,0);
    }

    /**
     * 根据条件查询
     * @param lineId
     * @param did
     * @param mid
     * @param effect
     * @return
     */
    @Override
    public List<ProDeModel> findByProNameAndDeviceNameAndModelNameAndEffect(int lineId, int did, int mid, int effect) {
        return proDeModelDao.findByProNameAndDeviceNameAndModelNameAndEffect(lineId,did,mid,effect);
    }

    /**
     * 根据文件id删除数据
     * @param fid
     */
    @Override
    public void deleteByFid(int fid) {
        proDeModelDao.deleteByFName(fid);
    }

    /**
     * 模糊分页查询文件关联关系
     * @param page
     * @param limit
     * @param file_id
     * @param typeName
     * @return
     */
    public Page<ProDeModel> getPageUpgradeScheduleView(int page, int limit, int file_id,String typeName) {
        Specification<ProDeModel> querySpeci = new Specification<ProDeModel>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList();
                Integer effect=1;
                List<Model> modelList=new ArrayList<>();
                if(typeName!=null) {
                    //型号type模糊查询所有的型号id
                    modelList = modelDao.findByTypeLike("%"+typeName+"%");
                }
                if(modelList.size()!=0) {
                        if (modelList!= null&&modelList.size()>0) {
                            CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("modelName"));
                            for (int i = 0; i < modelList.size(); i++) {
                                in.value(modelList.get(i).getId());//存入值
                            }
                            predicates.add(criteriaBuilder.and(criteriaBuilder.and(in)));
                        }
                        if ((Integer) file_id != null) {
                            predicates.add(criteriaBuilder.equal(root.get("fName"), file_id));
                        }
                        if(effect!=null){
                            predicates.add(criteriaBuilder.equal(root.get("effect"), effect));
                        }
                }else {
                    if ((Integer) file_id != null) {
                        predicates.add(criteriaBuilder.equal(root.get("fName"), file_id));
                    }
                    if(effect!=null){
                        predicates.add(criteriaBuilder.equal(root.get("effect"), effect));
                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pageRequest = new PageRequest(page-1, limit);
        return proDeModelDao.findAll(querySpeci,pageRequest);
    }

    /**
     * 批量删除
     * @param ids
     */
    @Override
    public void delete(String ids) {
        String[] idArray=ids.split(",");
        for(int i=0;i<idArray.length;i++){
            proDeModelDao.delete(Integer.parseInt(idArray[i]),1,0);
        }
    }

    /**
     * 根据生产线id查询文件
     * @param page
     * @param limit
     * @param lineId
     * @return
     */
    @Override
    public ReturnBean getFileByLine(int page, int limit, int lineId) {
        page=(page-1)*limit;
        List<ProDeModel> proDeModelList=proDeModelDao.getFileByLine(page,limit,lineId);
        int count=proDeModelDao.countByLine(lineId);
        ReturnBean returnBean=new ReturnBean(proDeModelList,count);
        return returnBean;
    }

}
