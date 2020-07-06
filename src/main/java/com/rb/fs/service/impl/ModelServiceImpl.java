package com.rb.fs.service.impl;

import com.rb.fs.dao.DistributeDao;
import com.rb.fs.dao.ModelDao;
import com.rb.fs.dao.ProDeModelDao;
import com.rb.fs.dao.ProductLineDao;
import com.rb.fs.dto.PreModelDto;
import com.rb.fs.entity.Model;
import com.rb.fs.entity.ProductLine;
import com.rb.fs.service.ModelService;
import com.rb.fs.service.ProductLineService;
import com.rb.fs.util.JudgeIsNull;
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
public class ModelServiceImpl  implements ModelService {

    @Autowired
    private ModelDao modelDao;
    @Autowired
    private ProDeModelDao proDeModelDao;
    @Autowired
    private DistributeDao distributeDao;
    @Autowired
    private ProductLineDao productLineDao;

    /**
     * 删除型号
     * @param ids
     * @return
     */
    @Override
    public void delete(String ids) {
        String[] idss=ids.split(",");
        for (int i = 0; i < idss.length; i++) {
            //if (idss[i] != null && !idss[i].equals("")) {
            if (!StringUtils.isBlank(idss[i])) {
                int id=Integer.parseInt(idss[i]);
                //设置分配记录数据失效
                distributeDao.updateEffectByTid(1,0,id);
                //根据型号名称设置生产线相关信息失效
                proDeModelDao.updateByMName(id,1,0);
                modelDao.delete(id);
            }
        }

    }


    /**
     * 批量添加
     * @param lineId
     */
    @Override
    public void batchAdd(String[] modelNameArray, String[] modelTypeArray, int lineId) {
        for(int i=0;i<modelNameArray.length;i++){
            Model model=new Model(JudgeIsNull.setStringToNull(modelNameArray[i]),JudgeIsNull.setStringToNull(modelTypeArray[i]),lineId);
            modelDao.save(model);
        }
    }

    /**
     * 分页查询
     *
     * @param lineId
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Model> selectAll(int lineId, int page, int limit) {
        page=(page-1)*limit;
        List<Model> models=modelDao.selectPage(page,limit,lineId);
        return models;
    }

    /**
     * 查询数据总数
     * @return
     * @param lineId
     */
    @Override
    public int count(int lineId) {
        return  modelDao.count(lineId);
    }

    /**
     * 根据名称查询数据总数
     *
     * @param typename
     * @param modelname
     * @param lineId
     * @return
     */
    @Override
    public int countByName(String typename, String modelname, int lineId) {
        return modelDao.countByName(typename,modelname,lineId);
    }


    /**
     * 根据线id查询所有
     * @return
     * @param lineid
     */
    @Override
    public List<PreModelDto> selectByLinid(int lineid) {
        List<Model> modelList=modelDao.selectByLinid(lineid);
        List<PreModelDto> preModelDtoList=new ArrayList<>();
        for(Model model:modelList){
            ProductLine productLine=productLineDao.findById(model.getProline_id());
            //model.setType(productLine.getName()+"-"+model.getType());
            PreModelDto preModelDto=new PreModelDto(lineid,model.getId(),model.getName(),productLine.getName()+"-"+model.getType(),productLine.getName());
            preModelDtoList.add(preModelDto);
        }
        return  preModelDtoList;
    }
    /**
     * 根据生产线id和id查询数据
     * @param proId
     * @param i
     * @return
     */
    @Override
    public Model findByLineIdAndId(int proId, int i) {
        return modelDao.findByLineIdAndId(proId,i);
    }

    /**
     * 根据id查询型号
     * @param tname
     * @return
     */
    @Override
    public Model findById(int tname) {
       return modelDao.findById(tname).get();
    }


    @Override
    public Page<Model> getPageUpgradeScheduleView(int page, int limit, String modelname,String typename,Integer lineId) {
        Specification<Model> querySpeci = new Specification<Model>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList();
                if(!StringUtils.isEmpty(modelname)) {
                    predicates.add(criteriaBuilder
                            .like(root.get("name"), "%" + modelname+ "%"));
                }
                if(!StringUtils.isEmpty(typename)){
                    predicates.add(criteriaBuilder
                            .like(root.get("type"), "%" + typename + "%"));
                }
                if(null != lineId){
                    predicates.add(criteriaBuilder.equal(root.get("proline_id"), lineId));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pageRequest = new PageRequest(page-1, limit);
        return modelDao.findAll(querySpeci,pageRequest);
    }


    /**
     * 不分页根据名称模糊查询
     * @param beartype
     * @return
     */
    @Override
    public List<Model> findByTypeLike(String beartype) {
        return modelDao.findByTypeLike("%"+beartype+"%");
    }
}
